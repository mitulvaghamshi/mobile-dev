//
//  GhostProtocol.m
//  HelloObjectiveC
//
//  Created by Mitul Vaghamshi on 2022-04-17.
//

#import <Foundation/Foundation.h>
#import "GhostProtocol.h"

@implementation GhostProtocol

- (void)startProcess {
    [NSTimer
     scheduledTimerWithTimeInterval:5.0
     target:self.delegate
     selector:@selector(processCompleted)
     userInfo:nil
     repeats:NO];
}

@end
