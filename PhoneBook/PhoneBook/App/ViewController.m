//
//  ViewController.m
//  HelloObjectiveC
//
//  Created by Mitul Vaghamshi on 2022-04-17.
//

#import "ViewController.h"
#import "DBManager.h"
#import "Utils.h"

@interface ViewController ()

@property Utils * utils;
@property GhostProtocol *protocol;

@end

@implementation ViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self) {
        NSLog(@"Inside initializer block...");
    }
    
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    _utils = [[Utils alloc]init];
    [_utils initialize];
    
    _protocol = [[GhostProtocol alloc]init];
    _protocol.delegate = self;
    [_progressLabel setText:@"Processing..."];
    [_protocol startProcess];
}

- (void)processCompleted {
    [_progressLabel setText:@"Process Completed!"];
}

- (IBAction)saveContact:(UIButton *)sender {
    BOOL isSuccess = NO;
    NSString *alertMsg = @"Unable to save contact.";
    
    if (_nameField.text.length > 0 && _phoneField.text.length > 0) {
        isSuccess = [[DBManager getSharedInstance] saveContactWithName:_nameField.text andNumber:_phoneField.text];
    } else {
        alertMsg = @"Name or Number cannot be empty!";
    }
    
    if (isSuccess == YES) {
        alertMsg = @"Contact saved Successfully!";
    }
    
    UIAlertController* alert = [UIAlertController alertControllerWithTitle:@"SQLite Alert" message:alertMsg preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction* defaultAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {}];
    [alert addAction:defaultAction];
    
    [self presentViewController:alert animated:YES completion:nil];
}

- (IBAction)searchContact:(UIButton *)sender {
    if (_findField.text.length > 0) {
        NSDictionary *contactsDict = [[DBManager getSharedInstance]findByName:_findField.text];
        
        if (contactsDict == nil) {
            UIAlertController* alert = [UIAlertController alertControllerWithTitle:@"Search Result" message:@"No result found." preferredStyle:UIAlertControllerStyleAlert];
            UIAlertAction* defaultAction = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction *action) {}];
            [alert addAction:defaultAction];
            
            [self presentViewController:alert animated:YES completion:nil];
        } else {
            NSArray *data = [contactsDict valueForKey:@"SearchResult"];
            NSString *resultString = [[NSString alloc] initWithFormat:@"Name: %@, Phone: %@", data[1], data[2]];
            _searchResult.text = resultString;
        }
    }
}

- (IBAction)calculateArea:(UIButton *)sender {
    CGFloat area = [_utils findAreaOfCircleWthRadius:12.5];
    NSString *areaString = [NSString stringWithFormat:@"Area of a Circle is: %f", area];
    _areaLabel.text = areaString;
    [_utils saveData:areaString];
    _arraySizeLabel.text = [NSString stringWithFormat:@"You have pressed the button %lu time(s)", [_utils getCount]];
}

@end
