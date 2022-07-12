import 'package:flutter/material.dart';

enum DeviceType { mobile, tablet, desktop }

enum DeviceSize {
  // Mobile
  mobileSmall(320, DeviceType.mobile),
  mobileMedium(375, DeviceType.mobile),
  mobileLarge(425, DeviceType.mobile),
  mobileXLarge(600, DeviceType.mobile),
  // Tablet
  tabletSmall(720, DeviceType.tablet),
  tabletMeduim(768, DeviceType.tablet),
  tabletLarge(840, DeviceType.tablet),
  tabletXLarge(960, DeviceType.tablet),
  // Desktop
  desktopSmall(1024, DeviceType.desktop),
  desktopMedium(1280, DeviceType.desktop),
  desktopLarge(1440, DeviceType.desktop),
  desktopXLarge(1600, DeviceType.desktop),
  desktopXXLarge(1920, DeviceType.desktop),
  desktopXXXLarge(2560, DeviceType.desktop),
  desktopUltraWide(6000, DeviceType.desktop);

  const DeviceSize(this.width, this.type);

  final int width;
  final DeviceType type;

  static const int mobileMaxWidth = 480;
  static const int tabletMaxWidth = 768;
  static const int laptopMaxWidth = 1024;

  static DeviceSize getDeviceSize(context) {
    final width = MediaQuery.of(context).size.width;
    final type = getDeviceType(width);
    return values.firstWhere((e) => e.type == type && e.width <= width);
  }

  static DeviceType getDeviceType(width) {
    DeviceType type = DeviceType.mobile;
    if (width > mobileMaxWidth && width <= tabletMaxWidth) {
      type = DeviceType.tablet;
    } else if (width > tabletMaxWidth) {
      type = DeviceType.desktop;
    }
    return type;
  }

  @override
  String toString() => '$name (${width}px)';
}
