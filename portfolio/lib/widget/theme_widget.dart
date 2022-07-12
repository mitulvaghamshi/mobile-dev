import 'package:flutter/material.dart';
import 'package:portfolio/utils/theme.dart';

class ThemeWidget extends StatelessWidget {
  const ThemeWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final ThemeController controller = ThemeController();
    return DropdownButton<ThemeMode>(
      value: controller.themeMode,
      onChanged: controller.updateThemeMode,
      items: const [
        DropdownMenuItem(
          value: ThemeMode.system,
          child: Icon(Icons.sunny_snowing),
        ),
        DropdownMenuItem(
          value: ThemeMode.light,
          child: Icon(Icons.light_mode),
        ),
        DropdownMenuItem(
          value: ThemeMode.dark,
          child: Icon(Icons.dark_mode),
        )
      ],
    );
  }
}
