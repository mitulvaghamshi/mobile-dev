import 'package:flutter/material.dart';

typedef ScrollWidgetBuilder = Widget Function(ScrollController);

class ScrollerWidget extends StatefulWidget {
  const ScrollerWidget({Key? key, required this.builder}) : super(key: key);

  final ScrollWidgetBuilder builder;

  @override
  State<ScrollerWidget> createState() => _ScrollerWidgetState();
}

class _ScrollerWidgetState extends State<ScrollerWidget> with AutoScroller {
  @override
  void initState() {
    super.initState();
    Future.delayed(const Duration(seconds: 2), () {
      WidgetsBinding.instance.addPostFrameCallback(scrollToEnd);
    });
  }

  @override
  void dispose() {
    disposeScrollController();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) => widget.builder(scrollController);
}

mixin AutoScroller {
  final ScrollController scrollController = ScrollController();

  void scrollToEnd(_) {
    scrollController.animateTo(
      800,
      curve: Curves.bounceInOut,
      duration: const Duration(seconds: 20),
    );
  }

  void disposeScrollController() {
    scrollController.dispose();
  }
}
