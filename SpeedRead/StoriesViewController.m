//
//  StoriesViewController.m
//  SpeedRead
//
//  Created by Paul on 10/5/14.
//  Copyright (c) 2014 Aperture Engineering. All rights reserved.
//

#import "StoriesViewController.h"
#import "UNIRest.h"

@interface StoriesViewController ()

@end

@implementation StoriesViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    NSString *name = [[NSUserDefaults standardUserDefaults] stringForKey:@"username"];
    NSDictionary* parameters = @{@"username": name};
    UNIHTTPJsonResponse* response = [[UNIRest post:^(UNISimpleRequest* request) {
        [request setUrl:@"http://hack.allen.li/api/v1/articles/get.json"];
        [request setParameters:parameters];
    }] asJson];
    UNIJsonNode* body = [response body];
    NSData* rawBody = [response rawBody];
    NSLog(rawBody);
    
    
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
