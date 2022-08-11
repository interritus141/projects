import 'package:flutter/material.dart';
import 'package:flutter_app/common/base/BaseViewModel.dart';


///提供viewModel的widget
///author:liuhc
class ViewModelProvider<T extends BaseViewModel> extends StatefulWidget {
  final T viewModel;
  final Widget child;

  ViewModelProvider({
    @required this.viewModel,
    @required this.child,
  });

  static T of<T extends BaseViewModel>(BuildContext context) {
    ViewModelProvider<T> provider = context.findAncestorWidgetOfExactType();
    return provider.viewModel;
  }

  static Type _typeOf<T>() => T;

  @override
  _ViewModelProviderState createState() => _ViewModelProviderState();
}

class _ViewModelProviderState extends State<ViewModelProvider> {
  @override
  Widget build(BuildContext context) {
    return widget.child;
  }

  @override
  void dispose() {
    
    widget.viewModel.dispose();
    super.dispose();
  }
}