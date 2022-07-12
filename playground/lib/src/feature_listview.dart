import 'package:flutter/material.dart';
import 'package:playground/src/screens/flutter_air.dart';
import 'package:playground/src/screens/inherited_widget.dart';
import 'package:playground/src/screens/magic_ball_widget.dart';
import 'package:playground/src/screens/scroll_card_widget.dart';
import 'package:playground/src/screens/wiki_search_api.dart';

import 'settings/settings_view.dart';

class FeatureListView extends StatelessWidget {
  const FeatureListView({Key? key}) : super(key: key);

  static const routeName = '/';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Explore Features'),
        actions: [
          IconButton(
            icon: const Icon(Icons.settings),
            onPressed: () => Navigator.restorablePushNamed(
              context,
              SettingsView.routeName,
            ),
          ),
        ],
      ),
      body: SafeArea(
        child: Column(children: const [
          _ListItem(
            title: 'Inharited Widget',
            route: InharitedWidgetApp.routeName,
          ),
          _ListItem(
            title: 'Scrollable Cards',
            route: ScrollCardWidget.routeName,
          ),
          _ListItem(
            title: 'Magic Ball',
            route: MagicBallWidget.routeName,
          ),
          _ListItem(
            title: 'Wiki Search API',
            route: WikiSearchApiApp.routeName,
          ),
          _ListItem(
            title: 'Responsive Layout',
            route: FlutterAir.routeName,
          ),
        ]),
      ),
    );
  }
}

class _ListItem extends StatelessWidget {
  const _ListItem({Key? key, required this.title, required this.route})
      : super(key: key);

  final String title;
  final String route;

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text(title),
      leading: const Icon(Icons.insert_emoticon),
      onTap: () => Navigator.restorablePushNamed(context, route),
    );
  }
}
