import 'dart:async';
import 'package:flutter_app/models/base.dart';
import 'http_util.dart';
import 'package:dio/dio.dart';

class NetUtils {
  static Future<Map<dynamic, dynamic>> post(String url, dynamic body,
      {Map<String, dynamic> queryParameters,
      String contentType,
      Map headers}) async {
    var response;
    try {
      response = await HttpUtil.dio.post(
        url,
        data: body,
        queryParameters: queryParameters,
        options: Options(
            contentType: contentType,
            headers: headers == null ? headers : Map.from(headers)),
      );
    } on DioError catch (e) {
      HttpUtil.formatError(e);
    }
    return response.data;
    // var res = BaseResponse.fromJson(response.data);
    // return Future.value(res);
  }

  static Future<BaseResponse> get(String url,
      {Map<String, dynamic> queryParameters,
      String contentType,
      Map headers}) async {
    var res;
    try {
      var response = await HttpUtil.dio.get(url,
          queryParameters: queryParameters,
          options: Options(
              contentType: contentType,
              headers: headers == null ? headers : Map.from(headers)));
      res = BaseResponse.fromJson(response.data);
    } on DioError catch (e) {
      HttpUtil.formatError(e);
    }
    return Future.value(res);
  }

  static Future<BaseResponse> postRestful(String url, dynamic body,
      {Map<String, dynamic> queryParameters,
      String contentType,
      Map headers}) async {
    var res;
    try {
      var response = await HttpUtil.dio.post(
        url,
        data: body,
        queryParameters: queryParameters,
        options: Options(
            contentType: contentType,
            headers: headers == null ? headers : Map.from(headers)),
      );
      res = BaseResponse.fromJson(response.data);
    } on DioError catch (e) {
      HttpUtil.formatError(e);
    }
    return Future.value(res);
  }
}
