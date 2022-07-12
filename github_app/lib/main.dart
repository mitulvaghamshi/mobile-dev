import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:github_app/src/app.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: const GithubApp(),
      theme: ThemeData.light(),
      darkTheme: ThemeData.dark(),
      title: 'GitHub GraphQL API Client',
      builder: (_, Widget? child) => CupertinoTheme(
        data: const CupertinoThemeData(),
        child: Material(child: child!),
      ),
    );
  }
}
