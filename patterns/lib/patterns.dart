import 'dart:ffi';

import 'package:ffi/ffi.dart';
import 'package:flutter/foundation.dart';

import 'patterns_platform_interface.dart';

typedef _DFAdd = int Function(int, int);
typedef _NFAdd = Int32 Function(Int32, Int32);
typedef _NFCap = Pointer<Utf8> Function(Pointer<Utf8>);
typedef _NFPat = Pointer<Utf8> Function(Pointer<Utf8>);

class Patterns {
  Patterns._() {
    // ffi yet not supported on web
    if (kIsWeb) return;
    // Load library (.so - shared object)
    late final DynamicLibrary dyLib;
    switch (defaultTargetPlatform) {
      case TargetPlatform.android:
      case TargetPlatform.linux:
        dyLib = DynamicLibrary.open('libnative_test.so');
        break;
      case TargetPlatform.iOS:
      case TargetPlatform.macOS:
        dyLib = DynamicLibrary.process();
        break;
      case TargetPlatform.windows:
        dyLib = DynamicLibrary.open('native_test.dll');
        break;
      // this is unknown yet
      case TargetPlatform.fuchsia:
      default:
        break;
    }

    // locate addition function
    _addition = dyLib.lookupFunction<_NFAdd, _DFAdd>('addition');

    // locate capitalize function
    final Pointer<NativeFunction<_NFCap>> capPointer =
        dyLib.lookup<NativeFunction<_NFCap>>('capitalize');
    _capitalize = capPointer.asFunction<_NFCap>();

    // locate pattern function
    final Pointer<NativeFunction<_NFPat>> patPointer =
        dyLib.lookup<NativeFunction<_NFPat>>('pattern');
    _pattern = patPointer.asFunction<_NFPat>();
  }

  static final Patterns _instance = Patterns._();
  factory Patterns() => _instance;

  late final _DFAdd _addition;
  late final _NFCap _capitalize;
  late final _NFPat _pattern;

  Future<String?> getPlatformVersion() {
    return PatternsPlatform.instance.getPlatformVersion();
  }

  int add(int a, int b) => _addition(a, b);

  String cap(String value) {
    final str = value.toNativeUtf8();
    Pointer<Utf8> ptr = _capitalize(str);
    calloc.free(str);
    return ptr.toDartString();
  }

  String pat([String rows = '15']) {
    final str = rows.toNativeUtf8();
    Pointer<Utf8> ptr = _pattern(str);
    calloc.free(str);
    return ptr.toDartString();
  }
}
