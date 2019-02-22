
#import "RNTroilaAlert.h"
#import "TRCustomAlert.h"


typedef void (^RNCompleteBlock)(NSInteger index,NSString *title);//回调block
@interface RNTroilaAlert()
@end
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
        [TRCustomAlert showShadeLoadingWithMessage:msg];
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
        NSString *icon=dictMsg[@"icon"];//默认为 none,成功 success，失败 fail，警告，warning
        
        if ([icon isEqualToString:@"success"]) {
            [TRCustomAlert showSuccessWithMessage:title];
        }else if ([icon isEqualToString:@"warning"]) {
            [TRCustomAlert showWarningWithMessage:title];
        }else if ([icon isEqualToString:@"fail"]) {
            [TRCustomAlert showErrorWithMessage:title];
        }else{
            //none
           [TRCustomAlert showBottomMessage:title];
        }
        

    });
}

//自适应底部简单提醒
RCT_EXPORT_METHOD(toastFit:(NSDictionary*)dictMsg){
    dispatch_async(dispatch_get_main_queue(), ^{
        //title ''   icon none
        NSString *title=dictMsg[@"title"];//默认为 ''
        NSString *icon=dictMsg[@"icon"];//默认为 none,成功 success，失败 fail，警告，warning
        
        if ([icon isEqualToString:@"success"]) {
            [TRCustomAlert showSuccessWithMessage:title];
        }else if ([icon isEqualToString:@"warning"]) {
            [TRCustomAlert showWarningWithMessage:title];
        }else if ([icon isEqualToString:@"fail"]) {
            [TRCustomAlert showErrorWithMessage:title];
        }else{
            //none
            [TRCustomAlert showFitBottomMessage:title];
        }
    });
}

//强提示
RCT_EXPORT_METHOD(alert:(NSDictionary*)dictMsg callback:(RCTResponseSenderBlock)callback){
    dispatch_async(dispatch_get_main_queue(), ^{
        
        NSString *title=dictMsg[@"title"];//默认为 ''
        NSString *icon=dictMsg[@"icon"];//默认为 none,成功 success，失败 fail，警告，warning
        NSString *content=dictMsg[@"content"];
        NSString *leftButton=dictMsg[@"leftButton"];
        NSString *rightButton=dictMsg[@"rightButton"];
        
        NSString *leftButtonColor=dictMsg[@"leftButtonColor"];
        NSString *rightButtonColor=dictMsg[@"rightButtonColor"];
        
        NSString *leftButtonSize=dictMsg[@"leftButtonSize"];
        NSString *rightButtonSize=dictMsg[@"rightButtonSize"];
        
        TRCustomAlertStyle style=0;
        if ([icon isEqualToString:@"success"]) {
           style=TRCustomAlertStyleSuccess;
        }else if ([icon isEqualToString:@"warning"]) {
            style=TRCustomAlertStyleWarning;
        }else if ([icon isEqualToString:@"fail"]) {
            style=TRCustomAlertStyleError;
        }else{
            style=TRCustomAlertStyleNone;
        }
        NSMutableArray *arrayBtn=[NSMutableArray array];
        if (leftButton!=nil) {
            [arrayBtn addObject:leftButton];
        }
        if (rightButton!=nil) {
            [arrayBtn addObject:rightButton];
        }
        [TRCustomAlert showAlertWithButtonTitleArray:arrayBtn style:style title:title content:content complete:^(NSInteger index, NSString *title) {
            [TRCustomAlert dissmis];
            callback(@[@(index),title]);
        }];
        //设置样式
        if (leftButtonColor!=nil) {
           [TRCustomAlert setButtonColor:[self colorWithHexString:leftButtonColor] index:0];
        }
        if (leftButtonSize!=nil) {
            [TRCustomAlert setButtonFont:[UIFont systemFontOfSize:[leftButtonSize floatValue]] index:0];
        }
        if (rightButtonColor!=nil) {
            [TRCustomAlert setButtonColor:[self colorWithHexString:rightButtonColor] index:1];
        }
        if (rightButtonSize!=nil) {
            [TRCustomAlert setButtonFont:[UIFont systemFontOfSize:[rightButtonSize floatValue]] index:1];
        }
        
    });
}

//颜色转换
- (UIColor *) colorWithHexString: (NSString *)color
{
    NSString *cString = [[color stringByTrimmingCharactersInSet:[NSCharacterSet whitespaceAndNewlineCharacterSet]] uppercaseString];
    
    // String should be 6 or 8 characters
    if ([cString length] < 6) {
        return [UIColor clearColor];
    }
    // 判断前缀
    if ([cString hasPrefix:@"0X"])
        cString = [cString substringFromIndex:2];
    if ([cString hasPrefix:@"#"])
        cString = [cString substringFromIndex:1];
    if ([cString length] != 6)
        return [UIColor clearColor];
    // 从六位数值中找到RGB对应的位数并转换
    NSRange range;
    range.location = 0;
    range.length = 2;
    //R、G、B
    NSString *rString = [cString substringWithRange:range];
    range.location = 2;
    NSString *gString = [cString substringWithRange:range];
    range.location = 4;
    NSString *bString = [cString substringWithRange:range];
    // Scan values
    unsigned int r, g, b;
    [[NSScanner scannerWithString:rString] scanHexInt:&r];
    [[NSScanner scannerWithString:gString] scanHexInt:&g];
    [[NSScanner scannerWithString:bString] scanHexInt:&b];
    
    return [UIColor colorWithRed:((float) r / 255.0f) green:((float) g / 255.0f) blue:((float) b / 255.0f) alpha:1.0f];
}

@end
  
