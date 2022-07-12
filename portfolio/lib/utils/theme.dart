import 'package:flutter/material.dart';

class ThemeController with ChangeNotifier {
  ThemeController._internal() : _themeMode = ThemeMode.system;

  static final ThemeController _instance = ThemeController._internal();
  factory ThemeController() => _instance;

  ThemeMode _themeMode;
  ThemeMode get themeMode => _themeMode;

  Future<void> updateThemeMode(ThemeMode? newThemeMode) async {
    if (newThemeMode == null || newThemeMode == _themeMode) return;
    _themeMode = newThemeMode;
    notifyListeners();
  }
}
