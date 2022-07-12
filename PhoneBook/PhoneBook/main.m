//
//  main.m
//  HelloObjectiveC
//
//  Created by Mitul Vaghamshi on 2022-04-17.
//

#import <UIKit/UIKit.h>
#import "AppDelegate.h"
// import header file
// #import "Utils.h"

int main(int argc, char * argv[]) {
    NSString * appDelegateClassName;
    // create object variable
    // Utils *utils;
    @autoreleasepool {
        // Setup code that might create autoreleased objects goes here.
        appDelegateClassName = NSStringFromClass([AppDelegate class]);
        // allocate memory/instantiate onject
        // utils = [[Utils alloc]init];
    }

    // call static/class method
    // [Utils exampleClassMethod];
    
    // call instance method
    // float area = [utils findAreaOfCircleWthRadius:12.5];
    
    // print message to console
    // printf("Area of circle is: %f", area);

    return UIApplicationMain(argc, argv, nil, appDelegateClassName);
}
