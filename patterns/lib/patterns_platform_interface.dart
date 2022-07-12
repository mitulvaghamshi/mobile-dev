import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'patterns_method_channel.dart';

abstract class PatternsPlatform extends PlatformInterface {
  /// Constructs a PatternsPlatform.
  PatternsPlatform() : super(token: _token);

  static final Object _token = Object();

  static PatternsPlatform _instance = MethodChannelPatterns();

  /// The default instance of [PatternsPlatform] to use.
  ///
  /// Defaults to [MethodChannelPatterns].
  static PatternsPlatform get instance => _instance;
  
  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [PatternsPlatform] when
  /// they register themselves.
  static set instance(PatternsPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
