//
//  Utils.m
//  HelloObjectiveC
//
//  Created by Mitul Vaghamshi on 2022-04-17.
//

#import <Foundation/Foundation.h>
#import "Utils.h"

// category declaration
// act as an extension to the original class
// to provide extra functionalities.
@interface Utils ()

@property NSArray * immutableArray;

@property NSMutableArray * mutableArray;

@property NSMutableDictionary * mutableDictionary;

@end

// class/interface implementation
@implementation Utils

// initialize variables
- (void)initialize {
    _immutableArray = [[NSArray alloc]
                       initWithObjects:@"First", @"Second", @"Third", nil];
    _mutableArray = [[NSMutableArray alloc]init];
    _mutableDictionary = [[NSMutableDictionary alloc]init];
}

// add received string to array and dictionary object
- (void)saveData:(NSString *)value {
    [_mutableArray addObject:value];
    [_mutableDictionary setObject:value forKey:@"CIRCLE_AREA"];
}

// return size of the array
- (unsigned long)getCount {
    return _mutableArray.count;
}

// instance method implementation
- (CGFloat)findAreaOfCircleWthRadius:(CGFloat)radius {
    return 3.14 * radius * radius;
}

// static method implementation
+ (void)exampleClassMethod {
    NSLog(@"Hello World!\n");
}

@end
