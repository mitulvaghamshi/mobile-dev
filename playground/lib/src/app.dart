import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:playground/src/screens/flutter_air.dart';
import 'package:playground/src/screens/inherited_widget.dart';
import 'package:playground/src/screens/magic_ball_widget.dart';
import 'package:playground/src/screens/scroll_card_widget.dart';
import 'package:playground/src/screens/wiki_search_api.dart';

import 'feature_listview.dart';
import 'settings/settings_controller.dart';
import 'settings/settings_view.dart';

class MyApp extends StatelessWidget {
  const MyApp({Key? key, required this.settingsController}) : super(key: key);

  final SettingsController settingsController;

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: settingsController,
      builder: (BuildContext context, Widget? child) {
        return MaterialApp(
          restorationScopeId: 'app',
          localizationsDelegates: const [
            AppLocalizations.delegate,
            GlobalMaterialLocalizations.delegate,
            GlobalWidgetsLocalizations.delegate,
            GlobalCupertinoLocalizations.delegate,
          ],
          supportedLocales: const [
            Locale('en', ''), // English, no country code
          ],
          onGenerateTitle: (BuildContext context) =>
              AppLocalizations.of(context)!.appTitle,
          theme: ThemeData(),
          darkTheme: ThemeData.dark(),
          themeMode: settingsController.themeMode,
          onGenerateRoute: (RouteSettings routeSettings) {
            return MaterialPageRoute<void>(
              settings: routeSettings,
              builder: (BuildContext context) {
                switch (routeSettings.name) {
                  case SettingsView.routeName:
                    return SettingsView(controller: settingsController);
                  case InharitedWidgetApp.routeName:
                    return const InharitedWidgetApp();
                  case ScrollCardWidget.routeName:
                    return const ScrollCardWidget();
                  case MagicBallWidget.routeName:
                    return const MagicBallWidget();
                  case WikiSearchApiApp.routeName:
                    return const WikiSearchApiApp();
                  case FlutterAir.routeName:
                    return const FlutterAir();
                  case FeatureListView.routeName:
                  default:
                    return const FeatureListView();
                }
              },
            );
          },
        );
      },
    );
  }
}
