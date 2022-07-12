import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:playground/src/app_test/models/favorites.dart';
import 'package:playground/src/app_test/screens/favorites.dart';
import 'package:provider/provider.dart';

late Favorites favoritesList;

Widget createFavoriteScreen() {
  return ChangeNotifierProvider<Favorites>(
    create: (context) {
      favoritesList = Favorites();
      return favoritesList;
    },
    child: const MaterialApp(home: FavoritesPage()),
  );
}

void addItems() {
  for (var i = 0; i < 10; i += 2) {
    favoritesList.add(i);
  }
}

void main() {
  group('Favorites page widget test', () {
    testWidgets('Test is ListView shows up', (tester) async {
      await tester.pumpWidget(createFavoriteScreen());
      addItems();
      await tester.pumpAndSettle();
      expect(find.byType(ListView), findsOneWidget);
    });

    testWidgets('Testing remove button', (tester) async {
      await tester.pumpWidget(createFavoriteScreen());
      addItems();
      await tester.pumpAndSettle();
      var totalItems = tester.widgetList(find.byIcon(Icons.close)).length;
      await tester.tap(find.byIcon(Icons.close).first);
      await tester.pumpAndSettle();
      expect(tester.widgetList(find.byIcon(Icons.close)).length, lessThan(totalItems));
      expect(find.text('Removed from favorites.'), findsOneWidget);
    });
  });
}
