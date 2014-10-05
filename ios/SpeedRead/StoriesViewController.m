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
@property (strong, nonatomic) NSMutableData *data;
@property (strong, nonatomic) IBOutlet UITableView *tableView;

@end

@implementation StoriesViewController



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

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return  _exercises.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView
         cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    //create a cell
    UITableViewCell *cell = [[UITableViewCell alloc]
                             initWithStyle:UITableViewCellStyleDefault
                             reuseIdentifier:@"cell"];
    
    // fill it with contnets
    cell.textLabel.text = [_exercises objectAtIndex:indexPath.row];
    // return it
    return cell;
}


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    NSLog(@"stuff is happening");
    [super viewDidLoad];
    NSString *name = [[NSUserDefaults standardUserDefaults] stringForKey:@"username"];
    NSDictionary* parameters = @{@"username": name};
    UNIHTTPJsonResponse* response = [[UNIRest post:^(UNISimpleRequest* request) {
        [request setUrl:@"http://hack.allen.li/api/v1/articles/get.json"];
        [request setParameters:parameters];
    }] asJson];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response {
    [_responseData setLength:0];
}
- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data {
    [_responseData appendData:data];
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error {
    NSLog(@"Connection failed: %@", [error description]);
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection {
    
    NSString *responseString = [[NSString alloc] initWithData:_responseData encoding:NSUTF8StringEncoding];
    
    
    NSDictionary *dictionary = [NSJSONSerialization JSONObjectWithData:[responseString dataUsingEncoding:NSUTF8StringEncoding] options:0 error:nil];
    NSArray *response = [dictionary objectForKey:@"response"];
    
    _exercises = [[NSArray alloc] initWithArray:response];
    [self.tableView reloadData];
}

@end
