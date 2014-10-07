//
//  RegisterViewController.m
//  SpeedRead
//
//  Created by Paul on 10/4/14.
//  Copyright (c) 2014 Aperture Engineering. All rights reserved.
//

#import "RegisterViewController.h"
#import "HomeViewController.h"
#import "UNIRest.h"

@interface RegisterViewController ()
@property (weak, nonatomic) IBOutlet UITextField *username;
@property (strong, nonatomic) IBOutlet UIButton *nextButton;
@property (strong, nonatomic) IBOutlet UIButton *backButton;
@property (strong, nonatomic) IBOutlet UIButton *regButton;

- (IBAction)updateEditor:(id)sender;
- (IBAction)hideKeyboard:(id)sender;
@end

@implementation RegisterViewController 


- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)updateEditor:(id)sender {

    NSDictionary* parameters = @{@"username": _username.text};
    UNIHTTPJsonResponse* response = [[UNIRest post:^(UNISimpleRequest* request) {
        [request setUrl:@"http://hack.allen.li/api/v1/add_user.json"];
        [request setParameters:parameters];
    }] asJson];
    int code = [response code];
    NSLog(@"%d", code);
    if (code == 200) {
        NSLog(@"Success");
        [[NSUserDefaults standardUserDefaults] setValue:_username.text forKey:@"username"];
        [[NSUserDefaults standardUserDefaults] synchronize];
        [((HomeViewController *)self.presentingViewController).signup setHidden:true];
        [((HomeViewController *)self.presentingViewController).nextButton setHidden:false];
        [_nextButton setHidden:false];
        [_backButton setHidden:true];
        [_regButton setHidden:true];
        
        NSMutableString *success = [NSMutableString stringWithString:@"Your username is "];
        [success appendString:_username.text];
        UIAlertView *alertDialog;
        alertDialog = [[UIAlertView alloc]
                       initWithTitle:@"Congrats"
                       message:success
                       delegate:nil
                       cancelButtonTitle:@"Ok"
                       otherButtonTitles:nil];
        [alertDialog show];
    }
    else {
        [self doAlert:sender];
    }
}
- (IBAction)doAlert:(id)sender {
    UIAlertView *alertDialog;
    alertDialog = [[UIAlertView alloc]
                   initWithTitle:@"Username Taken"
                   message:@"Please choose another name."
                   delegate:nil
                   cancelButtonTitle:@"Ok"
                   otherButtonTitles:nil];
    [alertDialog show];
}

- (IBAction)hideKeyboard:(id)sender {
    [_username resignFirstResponder];
}

@end