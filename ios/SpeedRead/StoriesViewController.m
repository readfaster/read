//
//  StoriesViewController.m
//  SpeedRead
//
//  Created by Paul on 10/5/14.
//  Copyright (c) 2014 Aperture Engineering. All rights reserved.
//

#import "StoriesViewController.h"
#import <MobileCoreServices/MobileCoreServices.h>
#import "UNIRest.h"


@interface StoriesViewController ()

@property (nonatomic, strong) NSDictionary *JSONStories;
@property (nonatomic, strong) NSArray *SortedStories;
@property (nonatomic, strong) NSString *username;

@end

@implementation StoriesViewController


- (NSInteger) numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section      {
    return [_JSONStories count];
}



- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return _SortedStories[section];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
     UITableViewCell *cell = [tableView
                              dequeueReusableCellWithIdentifier:@"StoryCell"];
     cell.textLabel.text =  _SortedStories[indexPath.row];
     return cell;
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


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    
    NSMutableDictionary *dict = [NSMutableDictionary dictionary];
    [dict setValue:[[NSUserDefaults standardUserDefaults] stringForKey:@"username"] forKey:@"username"];
    UNIHTTPJsonResponse* response = [[UNIRest post:^(UNISimpleRequest* request) {
        [request setUrl:@"http://hack.allen.li/api/v1/articles/get.json"];
        [request setParameters:dict];
    }] asJson];
    UNIJsonNode* body = [response body];
    
    _SortedStories = [body JSONArray];
    _JSONStories = [body JSONObject];
    
    [super viewDidLoad];
}


@end
