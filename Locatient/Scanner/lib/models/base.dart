//library baseresponse;
import 'package:json_annotation/json_annotation.dart';
part 'base.g.dart';
@JsonSerializable()
class BaseResponse<T> {
  String code;
  dynamic locations;
  String message;
  bool get success=>code==0;
  
  BaseResponse();

  factory BaseResponse.fromJson(Map<String, dynamic> json) =>
      _$BaseResponseFromJson(json);

  static Map<String, dynamic> toJson(BaseResponse instance) =>
      _$BaseResponseToJson(instance);
}

@JsonSerializable()
class CommonResponse {
  String code;
  dynamic locations;
  String message;
  
  CommonResponse();

  factory CommonResponse.fromJson(Map<String, dynamic> json) => _$CommonResponseFromJson(json);

  static Map<String, dynamic> toJson(CommonResponse instance) => _$CommonResponseToJson(instance);
}
