
#import "RNTroilaAlert.h"
#import "TRCustomAlert.h"
@implementation RNTroilaAlert

//解析json
-(NSDictionary *)jsonSerWithStr:(NSString *)jsonStr{
    NSData *JSONData = [jsonStr dataUsingEncoding:NSUTF8StringEncoding];
   return [NSJSONSerialization JSONObjectWithData:JSONData options:NSJSONReadingMutableLeaves error:nil];
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE(RNTroilaAlert)
RCT_EXPORT_METHOD(showLoading:(NSString*)msg){
    dispatch_async(dispatch_get_main_queue(), ^{
        [TRCustomAlert showLoadingWithMessage:msg];
    });
}


RCT_EXPORT_METHOD(hideLoading){
    dispatch_async(dispatch_get_main_queue(), ^{
        [TRCustomAlert dissmis];
    });
}
//简单提醒
RCT_EXPORT_METHOD(toast:(NSDictionary*)dictMsg){
    dispatch_async(dispatch_get_main_queue(), ^{
        //title ''   icon none
        NSString *title=dictMsg[@"title"];//默认为 ''
        NSString *icon=dictMsg[@"icon"];//默认为 none,成功 success，失败 failure，警告，warning
        
        if ([icon isEqualToString:@"success"]) {
            [TRCustomAlert showSuccessWithMessage:title];
        }else if ([icon isEqualToString:@"warning"]) {
//            [TRCustomAlert showwa:title];
        }else if ([icon isEqualToString:@"failure"]) {
            [TRCustomAlert showErrorWithMessage:title];
        }
        

    });
}




@end
  
