import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:scan/scan.dart';
import 'package:image_picker/image_picker.dart';
import 'home_vm.dart';
import 'package:flutter_app/models/base.dart';
import 'package:flutter_app/utils/common_utils.dart';
import 'utils/common_utils.dart';
import 'utils/date_utils.dart';

/// 扫描视图
class ScanPage extends StatelessWidget {
  ScanPage({Key key,this.selectLocation}) : super(key: key);

  IconData lightIcon = Icons.flash_on;
  final ScanController _controller = ScanController();
  Map<String, dynamic> selectLocation = Map<String, dynamic>();
  // 展示图片结果弹窗
  void showAlertDialog(data,context) {
    showDialog<Null>(
        context: context,
        barrierDismissible: false,
        builder: (BuildContext context) {
          return new AlertDialog(
            title: new Text('Qr code recognition results'),
            //可滑动
            content: new SingleChildScrollView(
              child: new Text(data),
            ),
            actions: <Widget>[
              new FlatButton(
                child: new Text('Upload'),
                onPressed: () {
                  getlocationList(data);
                  Navigator.of(context).pop();
                  _controller.resume();
                },
              ),
              new FlatButton(
                child: new Text('Cancel'),
                onPressed: () {
                  Navigator.of(context).pop();
                  _controller.resume();
                },
              ),
            ],
          );
        });
  }

  // 获取地址数据
  void getlocationList(data) async {
    Map<String, dynamic> param = {
      "qrhash": data,
      "roomName": CommonUtils.safeStr(selectLocation["roomName"]),
      "level": CommonUtils.safeStr(selectLocation["level"]),
      "date":DateUtil.getNowDateStr()
    };
    BaseResponse base = await HomeVM.postLogUpdate(param);
    if (!CommonUtils.isEmpty(base.message)) {
      Fluttertoast.showToast(msg: CommonUtils.safeStr(base.message,"Upload to complete"));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text("Qr code scanning"),
      ),
      body: Stack(
          children: [
            ScanView(
              controller: _controller,
              scanLineColor: Color(0xFF4759DA),
              onCapture: (data) {
                _controller.pause();
                showAlertDialog(data,context);
              },
            ),
            Positioned(
              left: 50,
              bottom: 50,
              child: StatefulBuilder(
                builder: (BuildContext context, StateSetter setState) {
                  return MaterialButton(
                      child: Icon(lightIcon,size: 40,color: Color(0xFF4759DA),),
                      onPressed: (){
                        _controller.toggleTorchMode();
                        if (lightIcon == Icons.flash_on){
                          lightIcon = Icons.flash_off;
                        }else {
                          lightIcon = Icons.flash_on;
                        }
                        setState((){});
                      }
                  );
                },
              ),
            ),
            Positioned(
              right: 50,
              bottom: 50,
              child: MaterialButton(
                  child: Icon(Icons.image,size: 40,color: Color(0xFF4759DA),),
                  onPressed: () async {
                    final pickerImages = await ImagePicker().getImage(source: ImageSource.gallery);

                    if (pickerImages != null) {
                      _controller.pause();
                      String result = await Scan.parse(pickerImages.path);
                      if(result != null){
                        showAlertDialog(result,context);
                      }
                    }
                  }
              ),
            ),
          ]
      ),
    );
  }
}