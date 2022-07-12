import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:patterns/patterns.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  String _getResult() {
    final patterns = Patterns();
    try {
      return patterns.pat();
      // int sum = patterns.add(100, 200);
      // String cap = patterns.cap('add two numbers');
      // return '$cap: $sum';
    } on PlatformException {
      return 'Error!';
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: const Text('Flutter FFI Demo!')),
        body: Center(
          child: Text(_getResult(), style: const TextStyle(fontSize: 20)),
        ),
      ),
    );
  }
}
