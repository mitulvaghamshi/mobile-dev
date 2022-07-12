import 'package:flutter/material.dart';
import 'package:playground/src/app_test/models/favorites.dart';
import 'package:playground/src/app_test/screens/favorites.dart';
import 'package:playground/src/app_test/screens/home.dart';
import 'package:provider/provider.dart';

void main() => runApp(const TestingApp());

class TestingApp extends StatelessWidget {
  const TestingApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<Favorites>(
      create: (context) => Favorites(),
      child: MaterialApp(
        title: 'Testing Sample',
        initialRoute: HomePage.routeName,
        routes: {
          HomePage.routeName: (_) => const HomePage(),
          FavoritesPage.routeName: (_) => const FavoritesPage(),
        },
      ),
    );
  }
}
