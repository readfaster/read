//
//  HomeViewController.m
//  SpeedRead
//
//  Created by Paul on 10/4/14.
//  Copyright (c) 2014 Aperture Engineering. All rights reserved.
//

#import "HomeViewController.h"

@interface HomeViewController ()

@end

@implementation HomeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.

    if ([[NSUserDefaults standardUserDefaults] valueForKey:@"username"]) {
        NSLog(@"Test");
        NSString *name = [[NSUserDefaults standardUserDefaults] stringForKey:@"username"];
        NSLog(@"%@", name);
        [_nextButton setHidden:false];
        [_signup setHidden:true];

    }
    else {
        [_nextButton setHidden:true];
        [_signup setHidden:false];
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)exitToHere:(UIStoryboardSegue *)sender {
    //Execute this code upon unwinding
    
}

- (IBAction)resetPW:(id)sender {
    NSLog(@"yep");
    [[NSUserDefaults standardUserDefaults] removeObjectForKey:@"username"];
    [_signup setHidden:false];
    [_nextButton setHidden:true];
}
@end
