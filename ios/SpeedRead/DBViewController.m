//
//  DBViewController.m
//  SpeedRead
//
//  Created by Paul on 10/6/14.
//  Copyright (c) 2014 Aperture Engineering. All rights reserved.
//


#import "DBViewController.h"
#import <DropboxSDK/DropboxSDK.h>


@interface DBViewController ()<DBRestClientDelegate>
@property (nonatomic, strong) DBRestClient *restClient;
@property (nonatomic, strong) NSArray *dbuploads;
@property (nonatomic) int counter;

@end

@implementation DBViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    self.restClient = [[DBRestClient alloc] initWithSession:[DBSession sharedSession]];
    self.restClient.delegate = self;
    
    
    _dbuploads = [[NSUserDefaults standardUserDefaults] objectForKey:@"dbuploads"];
    
    
    if ([[NSUserDefaults standardUserDefaults] objectForKey:@"uploadcounter"]) {
         _counter = (int)[[NSUserDefaults standardUserDefaults] objectForKey:@"uploadcounter"];
    }
    else {
        _counter = 0;
    }
    for (id object in _dbuploads) {
        NSString *text = (NSString *)object;
        NSMutableString *filename;
        [filename stringByAppendingString:@"Read-Urls"];
        NSString *number = [NSString stringWithFormat:@"%d", _counter];
        [filename stringByAppendingString:number];
        [filename stringByAppendingString:@".txt"];
        NSString *localDir = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0];
        NSString *localPath = [localDir stringByAppendingPathComponent:filename];
        [text writeToFile:localPath atomically:YES encoding:NSUTF8StringEncoding error:nil];
        
        // Upload file to Dropbox
        NSString *destDir = @"/";
        [self.restClient uploadFile:filename toPath:destDir withParentRev:nil fromPath:localPath];
        
        
        [self.restClient loadMetadata:@"/"];
        _counter++;
        [[NSUserDefaults standardUserDefaults] setInteger:_counter forKey:@"counter"];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
    

}

- (void)restClient:(DBRestClient *)client uploadedFile:(NSString *)destPath
              from:(NSString *)srcPath metadata:(DBMetadata *)metadata {
    NSLog(@"File uploaded successfully to path: %@", metadata.path);
}

- (void)restClient:(DBRestClient *)client uploadFileFailedWithError:(NSError *)error {
    NSLog(@"File upload failed with error: %@", error);
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)didPressLink {
    if (![[DBSession sharedSession] isLinked]) {
        [[DBSession sharedSession] linkFromController:self];
    }
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
