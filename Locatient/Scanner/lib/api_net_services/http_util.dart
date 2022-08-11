
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_app/api_net_services/apis.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'package:flutter_app/models/base.dart';
import 'package:flutter_app/utils/common_utils.dart';
import 'dart:io';
import 'dart:math';
import 'package:flutter/services.dart';

// 使用单例模式(只被实例化一次)
class HttpUtil {
  static HttpUtil instance;
  static Dio dio;
  BaseOptions options;
  CancelToken cancelToken = new CancelToken();

  //判断是否重新实例
  static HttpUtil getInstance() {
    if (null == instance) instance = new HttpUtil();
    return instance;
  }

  /*
   * Constructor (initial configuration)
   */
  HttpUtil() {
    //BaseOptions、Options、RequestOptions 都可以配置参数，优先级别依次递增，且可以根据优先级别覆盖参数
    dio = new Dio()
      ..options = BaseOptions(
          //请求基地址,可以包含子路径
          baseUrl: API.BASE_URL,
          //连接服务器超时时间，单位是毫秒.
          connectTimeout: 30000,
          //响应流上前后两次接受到数据的间隔，单位为毫秒。
          receiveTimeout: 30000)
      ..interceptors
          .add(InterceptorsWrapper(onRequest: (RequestOptions options) {
        Map<String, dynamic> headerMap = Map<String, dynamic>();
        if (!CommonUtils.isEmpty(options.headers["token"])) {
          headerMap.putIfAbsent('apikey', () => options.headers["token"]);
          headerMap.putIfAbsent('Authorization', () => options.headers["token"]);
        } else {
          // final token = UserManager.getUserToken();
          // if (!CommonUtils.isEmpty(token)) {
          headerMap.putIfAbsent('apikey', () => "86a7c035-bd63-4d1a-bd29-8c57d8a25dd3");
          headerMap.putIfAbsent('Authorization', () => "86a7c035-bd63-4d1a-bd29-8c57d8a25dd3");
          // }
        }
        headerMap.putIfAbsent("timestamp",()=> currentTimeMillis());
        headerMap.putIfAbsent("platform",()=> currentPlatform());
        headerMap.putIfAbsent("rand",()=> randStr());
        headerMap.putIfAbsent('sign', () => rsaSign(headerMap));
        options.headers.addAll(headerMap);
        return options;
      }, onResponse: (Response response) {
          LogUtil.d(
        "====== 网络请求 - ${CommonUtils.safeStr(response.request.extra["name"])} ======  \n "
        "*** Request *** \n "
        "${response.request.method} ${response.request.uri} \n "
        "header ${response.request.headers} \n "
        "data ${response.request.data} \n "
        "queryParameters ${response.request.queryParameters} \n "
        "*** Response *** \n "
        "${LogUtil.convert(response.data)} \n ");


        if(response.data["code"] != null && response.data["code"] != "100") {
          // print("showToastFlg === response ${response.request.extra["showToastFlg"]} ~~~~ ${response.request.uri} ~~~ ${CommonUtils.safeStr(response.request.extra["name"])}");
          // 提示
          if (CommonUtils.safeStr(response.request.extra["showToastFlg"], true)) {
            showToast(response);
          }
        }
//        rsaTest();
        return response; // continue
      }, onError: (DioError e) {
        LogUtil.d(
        "====== 网络请求 - 错误 - ${CommonUtils.safeStr(e.request.extra["name"])} ======  \n "
        "*** Request *** \n "
        "${e.request.method} ${e.request.uri} \n "
        "header ${e.request.headers} \n "
        "data ${e.request.data} \n "
        "queryParameters ${e.request.queryParameters} \n "
        "*** Response *** \n "
        "${e.response?.data} \n }");

        return e; //continue
      }));
  }

  /*
   * get request
   */
  static Future<BaseResponse> getRequest(
    url, {
    queryParameters,
    Options options,
    cancelToken,
    String name,
    BuildContext showLoadingContext,
    bool showToast = true,
        bool needErrorResponse = false,
  }) async {
    Response response;
    // Depending on the presence of showLoadingContext 动态加载loading
    loadingDialog(showLoadingContext);
    //Set the request name for logging, such as the login interface  - name = login -
    if (options == null) {
      options = Options(extra: {"name": CommonUtils.safeStr(name),"showToastFlg":showToast});
    } else if (!options.extra.containsKey("name")) {
      options.extra.putIfAbsent("name", () => CommonUtils.safeStr(name));
      options.extra.putIfAbsent("showToastFlg", () => showToast);
    }

    try {
      response = await dio.get(url,
          queryParameters: queryParameters,
          options: options,
          cancelToken: cancelToken);
    } on DioError catch (e) {
      loadingDialog(showLoadingContext, hidden: true);
      if (showToast) {
        formatError(e);
      }
      if(needErrorResponse) {
        response = e.response;
      }
    }
    loadingDialog(showLoadingContext, hidden: true);
    var res = BaseResponse.fromJson(response.data);
    return Future.value(res);
  }

  /*
   * post request
   */
  static Future<BaseResponse> postRequest(
    url, {
    data,
    Options options,
    parameter,
    cancelToken,
    String name,
    BuildContext showLoadingContext,
    bool showToast = true,
        bool needErrorResponse = false,
      }) async {
    Response response;
    // Depending on the presence of showLoadingContext 动态加载loading
    loadingDialog(showLoadingContext);
    // Set the request name for logging, such as the login interface  - name = login -
    if (options == null) {
      options = Options(extra: {"name": CommonUtils.safeStr(name),"showToastFlg":showToast});
    } else if (!options.extra.containsKey("name")) {
      options.extra.putIfAbsent("name", () => CommonUtils.safeStr(name));
      options.extra.putIfAbsent("showToastFlg", () => showToast);
    }

    try {
      response = await dio.post(url,
          data: data,
          queryParameters: parameter,
          options: options,
          cancelToken: cancelToken);
    } on DioError catch (e) {
      loadingDialog(showLoadingContext, hidden: true);
      if (showToast) {
        formatError(e);
      }
      if(needErrorResponse) {
        response = e.response;
      }
    }

    loadingDialog(showLoadingContext, hidden: true);
    var res = BaseResponse.fromJson(response.data);
    return Future.value(res);
  }

  // loading
  static loadingDialog(BuildContext showLoadingContext, {bool hidden = false}) {
    if (showLoadingContext != null) {
      if (hidden == true) {
        print("LoadingDialogHidden");
      } else {
        print("LoadingDialogShow");
      }
    }
  }

  /*
   * error统一处理
   */
  static void formatError(DioError e) {
    var typeStr = "";
    if (e.type == DioErrorType.CONNECT_TIMEOUT) {
      typeStr =  "连接超时 -";
      print("连接超时 - ${e}");
    } else if (e.type == DioErrorType.SEND_TIMEOUT) {
      typeStr =  "请求超时 -";
      print("请求超时 - ${e}");
    } else if (e.type == DioErrorType.RECEIVE_TIMEOUT) {
      typeStr =  "响应超时 -";
      print("响应超时 - ${e}");
    } else if (e.type == DioErrorType.RESPONSE) {
      typeStr =  "出现异常404 503 -";
      print("出现异常404 503 - ${e}");
    } else if (e.type == DioErrorType.CANCEL) {
      typeStr =  "请求取消 -";
      print("请求取消 - ${e}");
    } else {
      typeStr =  "未知错误 - ${e}";
      print("未知错误 - ${e}");
    }

    if(e.response != null && e.response.data != null && (e.response.data["code"] == 2)) {
      print("登录失效");
      reLogin();
    }

    // 提示
    showToast(e.response,typeStr);
  }

  /*
   * 提示
   */
 static void showToast(Response response,[String typeStr = ""]) {
      Fluttertoast.showToast(msg: typeStr + (response != null ? response.data["msg"] ??
          "服务器异常，code：" + response.statusCode.toString() : "网络请求异常！"),gravity:ToastGravity.CENTER);
  }

  /*
   * 登录失效 重新登录
   */
  static void reLogin() async {
  }

  /*
   * 取消请求
   *
   * 同一个cancel token 可以用于多个请求，当一个cancel token取消时，所有使用该cancel token的请求都会被取消。
   * 所以参数可选
   */
  void cancelRequests(CancelToken token) {
    token.cancel("cancelled");
  }

static String rsaSign(Map<String, dynamic> header) {
  String signStr = "appId=eedfefe&";
Map<String, dynamic> tempHeader = Map<String, dynamic>();
tempHeader.addAll(header);
//print("tempHeader ${tempHeader}");
//tempHeader.remove("content-type");
//  print("tempHeader -  ${tempHeader}");
//  print("header -  ${header}");

  sortMap(tempHeader).forEach((k,v){
    String kvStr = k+"="+v.toString()+"&";
    signStr = signStr + kvStr;//类型不一样的时候就toString()
  });
  return signStr;
}

static Map<String, dynamic> sortMap(map){
  List<String> keys = map.keys.toList();
  // key排序
  keys.sort((a, b) {
    List<int> al = a.codeUnits;
    List<int> bl = b.codeUnits;
    for (int i = 0; i < al.length; i++) {
      if (bl.length <= i) return 1;
      if (al[i] > bl[i]) {
        return 1;
      } else if (al[i] < bl[i]) return -1;
    }
    return 0;
  });

  //new一个map按照keys的顺序将原先的map数据取出来就可以了。
  var treeMap = Map<String, dynamic>();
  keys.forEach((element) {
    treeMap[element] = map[element];
  });

  return treeMap;
}
   // timestamp
  static int currentTimeMillis() {
    return new DateTime.now().millisecondsSinceEpoch;
  }

  static String currentPlatform() {
    return Platform.isIOS ? "iOS" : "Android";
  }

  /// dart  generate specific random number
  static String randStr() {
    String alphabet = 'qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM';
    int strlenght = 30;

    /// lengenth of string
    String rand = '';
    for (var i = 0; i < strlenght; i++) {
      rand = rand + alphabet[Random().nextInt(alphabet.length)];
    }

    return rand;
  }
}
