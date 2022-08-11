import 'package:flutter/material.dart';
// 生成二维码插件引用
import 'package:qr_flutter/qr_flutter.dart';
// 本地存储插件引用
import 'package:shared_preferences/shared_preferences.dart';
import 'utils/sp_util.dart';
import 'home_vm.dart';
import 'package:flutter_app/models/base.dart';
import 'package:flutter_app/utils/common_utils.dart';

class LocationListPage extends StatefulWidget {
  LocationListPage(this.didselectBlock);
  Function didselectBlock;

  @override
  _locationListPageState createState() => _locationListPageState();
}

class _locationListPageState extends State<LocationListPage> {
  List locationList = List();
  Map<String, dynamic> selectLocation = {};

  @override
  void initState() {
    super.initState();
    selectLocation = SpUtil.getObject("kSelectLocation") ?? {};
    getlocationList();
  }

  // 获取地址数据
  void getlocationList() async {
    BaseResponse base = await HomeVM.getLocationList();
    if (!CommonUtils.isEmpty(base.locations)) {
      print("object");
      locationList = base.locations;
      setState(() {});
    }
    setState(() {
    });
  }

  Widget setupListItem(index) {
    return Container(
        padding: EdgeInsets.all(15),
        margin: EdgeInsets.only(left: 10, right: 10, top: 10),
        decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.all(Radius.circular(10)),
        ),
        child: Row( // 纵向布局组件
          children: [
            Text('Location：${locationList[index]["roomName"]}'),
            Spacer(),
            if(locationList[index]["roomName"] == selectLocation["roomName"])(
            Icon(Icons.check,color: Colors.blue,)
            )
          ],
        )
    );
  }

  TextEditingController _vc = TextEditingController(text: "");

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          elevation:0,
          centerTitle: true,
          title: new Text("The list of positions",
            style: new TextStyle(color: Colors.white),
          ),
        ),
        body: ListView.separated(
          itemCount: locationList.length,
          itemBuilder: (context, index) {
            return GestureDetector(
              behavior: HitTestBehavior.translucent,
              onTap: (){
                selectLocation = locationList[index];
                widget.didselectBlock(selectLocation);
                SpUtil.putObject("kSelectLocation", selectLocation);
                Navigator.pop(context);
              },
              child: setupListItem(index),
            );
          },
          separatorBuilder: (context, index) {
            return SizedBox();
          },
        )
    );
  }
}
