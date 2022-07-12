import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:recipes/data/memory_repository.dart';
import 'package:recipes/api/mock_service.dart';

import 'data/repository.dart';
import 'network/service_interface.dart';
import 'ui/main_screen.dart';

Future<void> main() async {
  // _setupLogging();
  WidgetsFlutterBinding.ensureInitialized();
  // final repository = SqliteRepository();
  // final repository = MoorRepository();
  final repository = MemoryRepository();
  await repository.init();
  runApp(MyApp(repository: repository));
}

// void _setupLogging() {
//   Logger.root.level = Level.ALL;
//   Logger.root.onRecord.listen((rec) {
//     if (kDebugMode) {
//       print('${rec.level.name}: ${rec.time}: ${rec.message}');
//     }
//   });
// }

class MyApp extends StatelessWidget {
  const MyApp({Key? key, required this.repository}) : super(key: key);

  final Repository repository;

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        Provider<Repository>(
          lazy: false,
          create: (_) => repository,
          dispose: (_, Repository repository) => repository.close(),
        ),
        Provider<ServiceInterface>(
          create: (_) => MockService()..create(),
          // create: (_) => RecipeService.create(),
          lazy: false,
        ),
      ],
      child: MaterialApp(
        title: 'Recipes',
        theme: ThemeData(
          brightness: Brightness.light,
          primaryColor: Colors.white,
          primarySwatch: Colors.blue,
          visualDensity: VisualDensity.adaptivePlatformDensity,
        ),
        home: const MainScreen(),
      ),
    );
  }
}
