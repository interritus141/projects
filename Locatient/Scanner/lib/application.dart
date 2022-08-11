
import 'api_net_services/http_util.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/utils/common_utils.dart';
import 'utils/sp_util.dart';

class Application {
  static GlobalKey<NavigatorState> key = GlobalKey();

  static HttpUtil httpUtil;
  static SpUtil spUtil;

  static BuildContext rootContext;

  /// 是否开发环境
  static const bool isDev = !const bool.fromEnvironment("dart.vm.product");

  // app初始化设置
  static initAppSetup() async {
    print("app初始化设置 == ");
    // 初始化缓存工具
    // 解决SharedPreferences异步初始化 - 需要同步化操作的问题
    // 后续直接调用SpUtil即可,无需再异步初始化
    spUtil = await SpUtil.getInstance();
    // 初始化网络请求工具
    httpUtil = HttpUtil.getInstance();
    LogUtil.init(title: "LogUtil日志", isDebug: isDev, limitLength: 10000);
  }

}

