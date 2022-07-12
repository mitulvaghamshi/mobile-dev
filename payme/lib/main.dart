import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart' show Firebase;
import 'package:flutter/material.dart';
import 'package:payme/src/signin.dart';

Future<void> _initFirebase({bool withEmulators = false}) async {
  await Firebase.initializeApp();
  if (withEmulators) {
    _useEmulator();
  }
}

Future<void> _useEmulator() async {
  const String host = '192.168.0.56';
  await FirebaseAuth.instance.useAuthEmulator(host, 9099);
  FirebaseFirestore.instance.useFirestoreEmulator(host, 8080);
}

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await _initFirebase(withEmulators: true);
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: const SignIn(),
      darkTheme: ThemeData.dark(),
      theme: ThemeData(primarySwatch: Colors.deepPurple),
    );
  }
}
