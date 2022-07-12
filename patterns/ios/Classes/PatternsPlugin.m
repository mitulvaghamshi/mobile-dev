#import "PatternsPlugin.h"
#if __has_include(<patterns/patterns-Swift.h>)
#import <patterns/patterns-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "patterns-Swift.h"
#endif

@implementation PatternsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPatternsPlugin registerWithRegistrar:registrar];
}
@end
