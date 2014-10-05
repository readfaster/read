//
//  HomeViewController.h
//  SpeedRead
//
//  Created by Paul on 10/4/14.
//  Copyright (c) 2014 Aperture Engineering. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface HomeViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIButton *signup;
@property (weak, nonatomic) IBOutlet UIButton *nextButton;
- (IBAction)resetPW:(id)sender;
@end

