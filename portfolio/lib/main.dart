import 'dart:math';

import 'package:flutter/material.dart';
import 'package:portfolio/home.dart';
import 'package:portfolio/utils/theme.dart';

void main() => runApp(const PortfolioApp());

class PortfolioApp extends StatelessWidget {
  const PortfolioApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final ThemeController controller = ThemeController();
    return AnimatedBuilder(
      animation: controller,
      builder: (context, child) {
        return MaterialApp(
          debugShowCheckedModeBanner: false,
          home: child,
          title: 'Portfolio',
          themeMode: controller.themeMode,
          theme: ThemeData(
            fontFamily: 'Merriweather',
            primarySwatch: _themeColor,
          ),
          darkTheme: ThemeData(
            fontFamily: 'Merriweather',
            primarySwatch: _themeColor,
            brightness: Brightness.dark,
          ),
        );
      },
      child: const Home(),
    );
  }
}

final _themeColor = [
  Colors.blue,
  Colors.green,
  Colors.deepOrange,
  Colors.amber,
  Colors.pink,
][Random().nextInt(5)];
