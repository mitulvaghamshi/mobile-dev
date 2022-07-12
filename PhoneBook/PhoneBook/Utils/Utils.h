//
//  Utils.h
//  HelloObjectiveC
//
//  Created by Mitul Vaghamshi on 2022-04-17.
//

#import "UIKit/UIKit.h"

#ifndef Utils_h
#define Utils_h

// class/interface declearation
@interface Utils: NSObject

// initialize array and dictionary instances
- (void)initialize;

// add a string to array and dictionary
- (void)saveData:(NSString *)value;

// get the size of the array
- (unsigned long)getCount;

// instance method declearation
- (CGFloat)findAreaOfCircleWthRadius:(CGFloat)radius;

// static/class method declearation
+ (void)exampleClassMethod;

@end

#endif /* Utils_h */
