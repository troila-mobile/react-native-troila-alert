
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

        }else if(Platform.OS === 'android'){

        }
    }

    static toast(
        title:?string,
        icon?:?string
    ):void{
        if(Platform.OS === 'ios'){

        }else if(Platform.OS === 'android'){
            CustomAlertAndroid.toast(title,icon)
        }
    }

    static showLoading(
        title:?string
    ):void{
        if(Platform.OS === 'ios'){

        }else if(Platform.OS === 'android'){
            CustomAlertAndroid.showLoading(title)
        }
    }

    static hideLoading():void{
        if(Platform.OS === 'ios'){

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
            icon: icon || 'none'
        }

    }

    static toast(
        title:?string,
        icon?:?string
    ):void{
        let config = {
            title:title || '',
            icon: icon || 'none'
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

}

export default CustomAlert;
