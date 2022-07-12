import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:playground/src/app_test/main.dart';
import 'package:integration_test/integration_test.dart';

void main() {
  group('Testing App Performance Tests', () {
    final binding = IntegrationTestWidgetsFlutterBinding.ensureInitialized();
    binding.framePolicy = LiveTestWidgetsFlutterBindingFramePolicy.fullyLive;

    testWidgets('Scrolling test', (tester) async {
      await tester.pumpWidget(const TestingApp());
      final listFinder = find.byType(ListView);
      await binding.watchPerformance(() async {
        await tester.fling(listFinder, const Offset(0, -500), 10000);
        await tester.pumpAndSettle();
        await tester.fling(listFinder, const Offset(0, 500), 10000);
        await tester.pumpAndSettle();
      }, reportKey: 'scrolling_summery');
    });

    testWidgets('Favorite operation test', (tester) async {
      await tester.pumpWidget(const TestingApp());

      final iconKeys = [
        'icon_0',
        'icon_1',
        'icon_2',
        'icon_3',
      ];

      for (var icon in iconKeys) {
        await tester.tap(find.byKey(ValueKey(icon)));
        await tester.pumpAndSettle(const Duration(seconds: 1));
        expect(find.text('Added to favorites.'), findsOneWidget);
      }

      await tester.tap(find.text('Favorites'));
      await tester.pumpAndSettle();

      final removeIconKey = [
        'remove_icon_0',
        'remove_icon_1',
        'remove_icon_2',
        'remove_icon_3',
      ];

      for (var icon in removeIconKey) {
        await tester.tap(find.byKey(ValueKey(icon)));
        await tester.pumpAndSettle(const Duration(seconds: 1));
        expect(find.text('Removed from favorites.'), findsOneWidget);
      }
    });
  });
}
