
import { NativeModules, Platform } from 'react-native';

const { RNTroilaAlert } = NativeModules;

export type Buttons = Array<{
    text?:string,
    onPress?:?Function,
    color?:?string,
    fontSize?:?number
}>

class CustomAlert{
    static alert(
        title:?string,
        content?:?string,
        icon?:?string,
        buttons?:Buttons
    ):void{
        if(Platform.OS === 'ios'){
            CustomAlertIos.alert(title,content,icon,buttons)
        }else if(Platform.OS === 'android'){
            CustomAlertAndroid.alert(title,content,icon,buttons)
        }
    }

    static toast(
        title:?string,
        icon?:?string
    ):void{
        if(Platform.OS === 'ios'){
            CustomAlertIos.toast(title,icon)
        }else if(Platform.OS === 'android'){
            CustomAlertAndroid.toast(title,icon)
        }
    }

    static showLoading(
        title:?string
    ):void{
        if(Platform.OS === 'ios'){
            CustomAlertIos.showLoading(title)
        }else if(Platform.OS === 'android'){
            CustomAlertAndroid.showLoading(title)
        }
    }

    static hideLoading():void{
        if(Platform.OS === 'ios'){
            CustomAlertIos.hideLoading()
        }else if(Platform.OS === 'android'){
            CustomAlertAndroid.hideLoading()
        }
    }
}

class CustomAlertAndroid{
    static alert(
        title:?string,
        content?:?string,
        icon?:?string,
        buttons?:Buttons
    ):void{
        let config = {
            title:title || '',
            content:content || '',
            icon: icon || ''
        };
        let validButtons:Buttons = buttons? buttons.slice(0,2):[];
        let leftButton = validButtons.shift();
        let rightButton = validButtons.shift();
        if(leftButton){
            config={...config, leftButton:leftButton.text, leftButtonColor:leftButton.color, leftButtonSize:leftButton.fontSize}
        }
        if(rightButton){
            config={...config, rightButton:rightButton.text, rightButtonColor:rightButton.color, rightButtonSize:rightButton.fontSize}
        }
        RNTroilaAlert.showAlert(
            config,
            (errorMessage) => console.warn(errorMessage),
            (action, buttonKey) =>{
                if (action === 'buttonClicked') {
                    if (buttonKey === -1) {
                        leftButton.onPress && leftButton.onPress();
                    } else if (buttonKey === -2) {
                        rightButton.onPress && rightButton.onPress();
                    }
                }
            }
        )
    }

    static toast(
        title:?string,
        icon?:?string
    ):void{
        let config = {
            title:title || '',
            icon: icon || ''
        };
        RNTroilaAlert.showToast(
            config,
            (errorMessage) => console.warn(errorMessage)
        )
    }

    static showLoading(
        title:?string
    ):void{
        let message = title || '';
        RNTroilaAlert.showLoading(message,(errorMessage) => console.warn(errorMessage))
    }

    static hideLoading():void{
        RNTroilaAlert.hideLoading((errorMessage) => console.warn(errorMessage))
    }
}

class CustomAlertIos {

    static alert(
        title:?string,
        content?:?string,
        icon?:?string,
        buttons?:Buttons
    ):void{
        let config = {
            title:title || '',
            content:content || '',
            icon: icon || 'none'
        };
        let validButtons:Buttons = buttons? buttons.slice(0,2):[];
        let leftButton = validButtons.pop();
        let rightButton = validButtons.pop();
        if(leftButton){
            config={...config, leftButton:leftButton.text, leftButtonColor:leftButton.color, leftButtonSize:leftButton.fontSize}
        }
        if(rightButton){
            config={...config, rightButton:rightButton.text, rightButtonColor:rightButton.color, rightButtonSize:rightButton.fontSize}
        }
        RNTroilaAlert.alert(
            config,
            (action, buttonKey) =>{
                if (action === 0) {
                    leftButton.onPress && leftButton.onPress();
                } else if (action === 1) {
                    rightButton.onPress && rightButton.onPress();
                }
            }
        )
    }

    static toast(
        title:?string,
        icon?:?string
    ):void{
        let config = {
            title:title || '',
            icon: icon || 'none'
        };
        RNTroilaAlert.toast(config)
    }


    static showLoading(
        title:?string
    ):void{
        let message = title || '';
        RNTroilaAlert.showLoading(message)
    }

    static hideLoading():void{
        RNTroilaAlert.hideLoading()
    }
}

export default CustomAlert;
