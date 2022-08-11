import 'package:flutter/material.dart';
import 'package:flutter_app/utils/common_utils.dart';
// 权限插件引用
import 'package:permission_handler/permission_handler.dart';

// 扫码页面引用
import 'scan_page.dart';
// 二维码列表页面
import 'location_list_page.dart';
import 'package:fluttertoast/fluttertoast.dart';

import 'application.dart';
import 'utils/sp_util.dart';
// app main fuction
void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Locatient',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      debugShowCheckedModeBanner:false,
      home: MyHomePage(title: 'Locatient'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Map<String, dynamic> location = Map<String, dynamic>();

  @override
  void initState() {
    super.initState();
    location = SpUtil.getObject("kSelectLocation") ?? {};
  }

  @override
  Widget build(BuildContext context) {
    Application.initAppSetup();
    return Scaffold(
        appBar: AppBar(
          centerTitle: true,
          title: Text(widget.title),
        ),
        body: SingleChildScrollView(
            child:Center(
          child: Column(
            children: <Widget>[
              SizedBox(height: 50,), // Set interval
              // setting
              setupQRView(),
              SizedBox(height: 60,), // Set the interval
              // scan QR code
              setupQRScanView(),
              SizedBox(height: 40,), // Set the interval
            ],
          ),
        )));
  }

  // 设置  视图
  Widget setupQRView() {
    /// 通过Flutter InkWell组件包裹视图 添加点击事件
    return InkWell(
      onTap: () async {
        Navigator.push(context, new MaterialPageRoute(builder: (context) => LocationListPage((item){
          if(!CommonUtils.isEmpty(item)) {
            location = item;
          }
          setState(() {
          });
        })));
      },
      child: Container(
        width: 180,
        height: 160,
        decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.all(Radius.circular(10)),
            boxShadow: [
              BoxShadow(
                  color: Colors.deepPurple,
                  offset: Offset(0.0, 0.0), //阴影y轴偏移量
                  blurRadius: 5, //阴影模糊程度
                  spreadRadius: 5 //阴影扩散程度
              )]
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center, // 设置内容居中
          children: [
            Icon(Icons.location_on_outlined),
            SizedBox(height: 10,),
            Text("Location ${CommonUtils.safeStr(location["roomName"],"Please select")}")
          ],
        ),
      ),
    );
  }

  // 设置 读取二维码 视图
  Widget setupQRScanView() {
    /// 通过Flutter GestureDetector组件包裹视图 添加点击事件
    return GestureDetector(
      onTap: ()async {
        if(CommonUtils.isEmpty(location) || location.length <= 0) {
          Fluttertoast.showToast(msg: "Please select location",gravity:ToastGravity.CENTER);
          return;
        }

        var status = await Permission.camera.request();
        if(status.isGranted){
          Navigator.push(context, new MaterialPageRoute(builder: (context) => ScanPage(selectLocation: location,)));

        }
      },
      child: Container(
        width: 180,
        height: 160,
        decoration: BoxDecoration(
            color: Colors.white,
            borderRadius: BorderRadius.all(Radius.circular(10)),
            boxShadow: [
              BoxShadow(
                  color: Colors.red,
                  offset: Offset(0.0, 0.0), //阴影y轴偏移量
                  blurRadius: 5, //阴影模糊程度
                  spreadRadius: 5 //阴影扩散程度
              )]
        ),
        child: Column( // 纵向布局组件
          mainAxisAlignment: MainAxisAlignment.center, // 设置内容居中
          children: [
            Icon(Icons.qr_code_scanner_outlined),
            SizedBox(height: 10,),
            Text("Qr code scanning")
          ],
        ),
      ),
    );
  }
}


