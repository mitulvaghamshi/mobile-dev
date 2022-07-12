import 'package:cupertino_store/utils/utils.dart';
import 'package:cupertino_store/views/views.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';

class CupertinoStoreApp extends StatelessWidget {
  const CupertinoStoreApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    SystemChrome.setPreferredOrientations(<DeviceOrientation>[
      DeviceOrientation.portraitUp,
      DeviceOrientation.portraitDown,
    ]);
    return const CupertinoApp(
      title: appTitle,
      home: CupertinoStoreHomePage(),
      theme: CupertinoThemeData(brightness: Brightness.light),
    );
  }
}
