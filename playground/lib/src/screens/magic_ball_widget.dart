import 'dart:convert' show base64;
import 'dart:math' as math;

import 'package:flutter/material.dart';

/// The command-and-control Widget that pulls it all together
class MagicBallWidget extends StatefulWidget {
  /// Default constructor
  const MagicBallWidget({Key? key}) : super(key: key);

  static const routeName = '/magicballwidget';

  @override
  MagicBallWidgetState createState() => MagicBallWidgetState();
}

class MagicBallWidgetState extends State<MagicBallWidget>
    with SingleTickerProviderStateMixin {
  static const restPosition = Offset(0, -0.15);
  static const lightSource = Offset(0, -0.75);

  late AnimationController controller;
  late Animation<double> animation;

  String prediction = 'The\nMAGIC\nBall';
  Offset tapPosition = Offset.zero;
  double wobble = 0;

  /// Ball predictions obfuscated
  final List<String> _predictions = String.fromCharCodes(
    base64.decode(
        'R29vZ2xlfEFwcGxlfE1pY3Jvc29mdHxBbWF6b258U3BhY2VYfFRlc2xhfE5hc2F8TmV0ZmxpeHxNZXRhCg=='),
  ).split('|');

  @override
  void initState() {
    controller = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 300),
      reverseDuration: const Duration(milliseconds: 1500),
    );

    controller.addListener(() => setState(() {}));

    animation = CurvedAnimation(
      parent: controller,
      curve: Curves.easeInOut,
      reverseCurve: Curves.elasticIn,
    );
    super.initState();
  }

  @override
  void dispose() {
    controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final position = Offset.lerp(restPosition, tapPosition, animation.value)!;
    final size = Size.square(MediaQuery.of(context).size.shortestSide);

    return Scaffold(
      appBar: AppBar(title: const Text('Magic Ball')),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Center(
          child: Stack(
            alignment: Alignment.bottomCenter,
            children: [
              _BottomShadow(diameter: size.shortestSide),
              GestureDetector(
                onPanStart: (details) => _start(details.localPosition, size),
                onPanEnd: (_) => _end(),
                onPanUpdate: (details) => _update(details.localPosition, size),
                child: _TheSphere(
                  lightSource: lightSource,
                  diameter: size.shortestSide,
                  child: Transform(
                    origin: size.center(Offset.zero),
                    transform: Matrix4.identity()
                      ..translate(
                        position.dx * size.width / 2,
                        position.dy * size.height / 2,
                      )
                      ..rotateZ(position.direction)
                      ..rotateY(position.distance * math.pi / 2)
                      ..rotateZ(-position.direction)
                      ..scale(0.5 - 0.15 * position.distance),
                    child: _InnerCircle(
                      lightSource: lightSource - position,
                      child: Opacity(
                        opacity: 1 - controller.value,
                        child: Transform.rotate(
                          angle: wobble,
                          child: _InnerTriangle(text: prediction),
                        ),
                      ),
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _start(Offset position, Size size) {
    controller.forward(from: 0);
    _update(position, size);
  }

  void _end() {
    final rand = math.Random();
    wobble = rand.nextDouble() * (wobble.isNegative ? 0.5 : -0.5);
    prediction = _predictions[rand.nextInt(_predictions.length)];
    controller.reverse(from: 1);
  }

  void _update(Offset position, Size size) {
    var tapPosition = Offset((2 * position.dx / size.width) - 1,
        (2 * position.dy / size.height) - 1);
    if (tapPosition.distance > 0.8) {
      tapPosition = Offset.fromDirection(tapPosition.direction, 0.8);
    }
    setState(() => this.tapPosition = tapPosition);
  }
}

/// A Widget which draws the black sphere of an 8-Ball
class _TheSphere extends StatelessWidget {
  /// Default constructor
  const _TheSphere({
    Key? key,
    required this.diameter,
    required this.lightSource,
    required this.child,
  }) : super(key: key);

  /// A widget to draw on the surface of the sphere
  /// (will be WindowOfOpportunity)
  final Widget child;

  /// The diameter of the sphere
  final double diameter;

  /// The position of the light source relative to the sphere
  final Offset lightSource;

  @override
  Widget build(BuildContext context) {
    return Container(
      alignment: Alignment.center,
      width: diameter,
      height: diameter,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        gradient: RadialGradient(
          center: Alignment(lightSource.dx, lightSource.dy),
          colors: const [Colors.grey, Colors.black],
        ),
      ),
      child: child,
    );
  }
}

/// A Widget which draws the shadow of a circlular object
class _BottomShadow extends StatelessWidget {
  /// Default constructor
  const _BottomShadow({Key? key, required this.diameter}) : super(key: key);

  /// The diameter of the circular object being shadowed
  final double diameter;

  @override
  Widget build(BuildContext context) {
    return Transform(
      origin: Offset(0, diameter),
      transform: Matrix4.identity()..rotateX(math.pi / 2.1),
      child: Container(
        width: diameter,
        height: diameter,
        decoration: BoxDecoration(shape: BoxShape.circle, boxShadow: [
          BoxShadow(blurRadius: 25, color: Colors.grey.withOpacity(0.7))
        ]),
      ),
    );
  }
}

/// A Widget which draws a shallow circlular indentation on the surface of
/// SphereOfDestiny, containing a Prediction
class _InnerCircle extends StatelessWidget {
  /// Default constructor
  const _InnerCircle({
    Key? key,
    required this.lightSource,
    required this.child,
  }) : super(key: key);

  /// The position of the light source relative to the window
  final Offset lightSource;

  /// The Prediction itself
  final Widget child;

  @override
  Widget build(BuildContext context) {
    final innerShadowWidth = lightSource.distance * 0.1;
    final portalShadowOffset = Offset.fromDirection(
      math.pi + lightSource.direction,
      innerShadowWidth,
    );

    return Container(
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        gradient: RadialGradient(
          center: Alignment(portalShadowOffset.dx, portalShadowOffset.dy),
          colors: const [Color(0x661F1F1F), Colors.black],
          stops: [1 - innerShadowWidth, 1],
        ),
      ),
      child: child,
    );
  }
}

/// A class that draws an 8-Ball predictive blue triangle containing text
class _InnerTriangle extends StatelessWidget {
  /// Default constructor
  const _InnerTriangle({Key? key, required this.text}) : super(key: key);

  /// The text of the current prediction
  final String text;

  @override
  Widget build(BuildContext context) {
    return CustomPaint(
      painter: _TrianglePainter(),
      child: Container(
        alignment: Alignment.center,
        child: Text(
          text,
          textAlign: TextAlign.center,
          style: const TextStyle(fontSize: 30),
        ),
      ),
    );
  }
}

class _TrianglePainter extends CustomPainter {
  final _gradient = const LinearGradient(colors: [
    Color(0xFFB2DAFF),
    Color(0xFF3787FF),
  ]);

  @override
  void paint(Canvas canvas, Size size) {
    final painter = Paint()
      ..shader = _gradient.createShader(Offset.zero & size)
      ..style = PaintingStyle.fill;

    final w = size.width;
    final h = size.height;
    final path = Path()
      ..moveTo(w * 0.2, h * 0.3)
      ..quadraticBezierTo(w * 0.5, h * 0.1, w * 0.8, h * 0.3)
      ..quadraticBezierTo(w * 0.85, h * 0.6, w * 0.5, h * 0.85)
      ..quadraticBezierTo(w * 0.15, h * 0.6, w * 0.2, h * 0.3)
      ..close();

    canvas.drawPath(path, painter);
  }

  @override
  bool shouldRepaint(_TrianglePainter oldDelegate) => false;

  @override
  bool shouldRebuildSemantics(_TrianglePainter oldDelegate) => false;
}
