//
//  GhostProtocol.h
//  HelloObjectiveC
//
//  Created by Mitul Vaghamshi on 2022-04-17.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol GhostProtocolDelegate <NSObject>

@required
- (void)processCompleted;

@end

@interface GhostProtocol : NSObject {
    id <GhostProtocolDelegate> _delegate;
}

@property (strong, nonatomic) id delegate;

- (void)startProcess;

@end

NS_ASSUME_NONNULL_END
