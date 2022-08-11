import 'dart:async';
import 'package:flutter_app/models/base.dart';
import 'package:flutter_app/api_net_services/http_util.dart';
import 'package:flutter_app/api_net_services/apis.dart';

import 'package:flutter_app/application.dart';

class HomeVM {
  static Future<BaseResponse> getLogin(
      Map<String, dynamic> param) async {
    return await HttpUtil.getRequest(
        API.login, queryParameters: param, name: "登录");
  }

  static Future<BaseResponse> getLocationList([Map<String, dynamic> param = const {}]) async {
    return await HttpUtil.getRequest(
        API.locationList,queryParameters: param, name: "位置列表");
  }

  static Future<BaseResponse> postLogUpdate([Map<String, dynamic> param = const {}]) async {
    return await HttpUtil.postRequest(API.log_update,data: param,name: "上传信息");
  }
}
