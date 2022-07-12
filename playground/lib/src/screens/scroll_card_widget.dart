import 'dart:math';

import 'package:flutter/material.dart';

class ScrollCardWidget extends StatelessWidget {
  const ScrollCardWidget({Key? key}) : super(key: key);

  static const routeName = '/scrollcardwidget';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Scrollable Cards')),
      body: Center(child: ScrollCardWidgetApp()),
    );
  }
}

const int count = 5;

class ScrollCardWidgetApp extends StatelessWidget {
  ScrollCardWidgetApp({Key? key}) : super(key: key);

  final double padding = 16;
  final double verticalInsets = 16;
  final double cardAspectRAtio = 12.0 / 16.0;
  late final double widgetAspectRatio = cardAspectRAtio * 1.2;
  final ValueNotifier<double> currentPage = ValueNotifier<double>(count - 1);
  late final PageController controller = PageController(initialPage: count - 1)
    ..addListener(() => currentPage.value = controller.page!);

  @override
  Widget build(BuildContext context) {
    return Stack(children: <Widget>[
      ValueListenableBuilder(
        valueListenable: currentPage,
        builder: (_, double value, __) {
          return AspectRatio(
            aspectRatio: widgetAspectRatio,
            child: LayoutBuilder(
              builder: (context, constraints) {
                var height = constraints.maxHeight;
                var width = constraints.maxWidth;
                var safeHeight = height - 2 * padding;
                var safeWidth = width - 2 * padding;
                var heightOfPrimaryCard = safeHeight;
                var widthOfPrimarpCard = heightOfPrimaryCard * cardAspectRAtio;
                var primaryCardLeft = safeWidth - widthOfPrimarpCard;
                var horizontalInsets = primaryCardLeft / 2;
                List<Widget> cardList = [];
                for (var i = 1; i < count; i++) {
                  var delta = i - value;
                  var cardItem = Positioned.directional(
                    top: padding + verticalInsets * max(-delta, 0.0),
                    bottom: padding + verticalInsets * max(-delta, 0.0),
                    start: padding +
                        max(
                          primaryCardLeft -
                              horizontalInsets * -delta * (delta > 0 ? 20 : 1),
                          0.0,
                        ),
                    textDirection: TextDirection.rtl,
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(16.0),
                      child: AspectRatio(
                        aspectRatio: cardAspectRAtio,
                        child: Placeholder(
                          strokeWidth: 5,
                          color: Colors.primaries[i],
                        ),
                      ),
                    ),
                  );
                  cardList.add(cardItem);
                }
                return Stack(children: cardList);
              },
            ),
          );
        },
      ),
      Positioned.fill(
        child: PageView.builder(
          reverse: true,
          itemCount: count,
          controller: controller,
          physics: const BouncingScrollPhysics(),
          itemBuilder: (_, __) => Container(),
        ),
      ),
    ]);
  }
}
