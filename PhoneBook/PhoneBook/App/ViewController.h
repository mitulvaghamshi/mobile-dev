//
//  ViewController.h
//  HelloObjectiveC
//
//  Created by Mitul Vaghamshi on 2022-04-17.
//

#import <UIKit/UIKit.h>
#import "GhostProtocol.h"
#import "DBManager.h"

@interface ViewController : UIViewController<GhostProtocolDelegate>

@property (weak, nonatomic) IBOutlet UILabel *progressLabel;
@property (weak, nonatomic) IBOutlet UILabel *areaLabel;
@property (weak, nonatomic) IBOutlet UILabel *arraySizeLabel;
@property (weak, nonatomic) IBOutlet UILabel *searchResult;

@property (weak, nonatomic) IBOutlet UITextField *nameField;
@property (weak, nonatomic) IBOutlet UITextField *phoneField;
@property (weak, nonatomic) IBOutlet UITextField *findField;

- (IBAction)calculateArea:(UIButton *)sender;
- (IBAction)searchContact:(UIButton *)sender;
- (IBAction)saveContact:(UIButton *)sender;

@end
