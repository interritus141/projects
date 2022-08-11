import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///
///
/// 常用数据结构体
///
///

///
/// 菜单项
///
class MenuItem {
  int btnId;
  String icon;
  String text;
  bool hightlighted = false;
  MenuItem(this.icon, this.text, this.btnId,{this.hightlighted=false});
}

///功能模式
enum FunctionMode { LISTEN, RECOGNIZE, CORRECT }


/// 显示速度调节器
enum SpeedPickerDisplay { NONE, SPEED, BEATS }


///
///
///
/// 节拍器试图状态
class MetronomeViewState{
  ///有几组指示器
  int indicatorCount = 4;
  ///当前高亮第几组指示器
  int currentIndicator = -1;
  ///管理指示器的状态(某组指示器高亮几个格)
  Map<int,int> indicatorState = Map();
  ///当前速度
  int currentSpeed = 120;
  ///是否显示请拍打
  int showTapTint = 3;
  ///速度对应的名字
  String speedName = 'Allegro';
  /// 是否正在播放
  bool playing = false;
  /// 抽屉内容
  DrawerType drawerType;
  /// 速度变化 <0 0 >0
  int speedChange = 0;
  ///
  ///
  String beatsShow = '4/4';

  ///
  bool timerEnabled = false;
  /// 倒计时
  String timerShow = '03:00';

  /// 细分
  MetronomeBeatsType metronomeBeatsType;

}

enum DrawerType{
  //定时器
  TIMER,
  ///细分
  DIVIDE,
  ///4/4拍
  BEATS,
}

///
/// 节拍器细分数据
class MetronomeBeatsType{
  String img;
  //拍子数
  int ct;
  //分组
  int group;
  MetronomeBeatsType(this.img, this.group,this.ct);

  get showImg => img?.replaceFirst('yfo','yfo-');

}


///
/// 要同步的数据类型
enum OnlineDataType{
  ///光标
  CURSOR,
  ///播放器参数
  PLAYER_PARAM,
  ///打点
  DOT,
  ///卡顿
  UNSKILLED,
  ///提醒
  TIPS,
  /// 播放状态
  PLAYER_STATE,
  ///连接的设备
  CONNECTED_DEVICE,
  ///设备列表
  DEVICES_LIST,
  ///基本信息
  PRIVATE_INFO,

  /// 是否扫描
  STATUS_SCANNING,
  /// 清除红点
  CLEAR_DOTS,

  /// 命令扫描
  CMD_START_SCAN,
  /// 停止
  CMD_STOP_SCAN,
  /// 连接
  CMD_CONNECT,
  /// 断开、连接
  CMD_TOGGLE_STATE,
  /// 关闭蓝牙弹窗
  CMD_CLOSE_BLUE_DIALOG,
  /// 重置播放器
  CMD_RESET_PLAY,
  /// 检查
  CMD_TEST,
  /// 关闭自己的弹窗
  CMD_CLOSE_SELF_DIALOG,
}

/// 角色
enum Role{
  ///主
  Teacher,
  ///从
  Student
}





// Smart key
enum SmartKeyType {
  // 关闭
  Close,
  // 自动
  Auto,
  // 主要
  Main
}

// 主题套装
enum PlayerThemeType {
  // 简约
  Contracted,
  // 炫黑
  CoolBlack,
  // 水晶
  Crystal
}

// 自动视听
enum PlayerAutomaticVideoType {
  // 关闭
  Close,
  // 开启
  Open,
}

// 预备拍
enum PlayerPrepareTakeType {
  // 1小节
  One,
  // 2小节
  Two,
}

// 钢琴混响
enum PlayerPianoReverbType {
  // 关闭
  Close,
  // 开启
  Open,
}

// 纠错模式及时显示标注
enum PlayerErrorMarkedType {
  // 关闭
  Close,
  // 开启
  Open,
}

// 节拍音色
enum PlayerBeatToneType {
  // 音色1
  One,
  // 音色2
  Two,
  // 音色3
  Three,
}

// 光标颜色
enum PlayerCursorColorType {
  // 红
  Red,
  // 蓝
  Blue,
  // 隐藏
  Hidden,
}

// 护眼模式
enum PlayerEyecareType {
  // 白
  White,
  // 绿
  Green,
  // 黄
  Yellow,
}

// 提示弹窗类型
enum ToastType {
  // 敬请期待
  Expect,
  // 已激活
  Activate,
  // 已续期
  Renewal,
  // 已添加到下载队列
  Download,
  // 已删除成功
  Delete,
  // 已修改
  Edt,
  // 已下发作业
  IssuedHomework,
  // 已提醒
  Remind,
  // AI提示已开启
  AI,
  // 已退出登录
  LoginOut,
}
