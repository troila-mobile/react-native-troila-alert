/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import CustomAlert from './troilaalert';

const instructions = Platform.select({
    ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
    android:
        'Double tap R on your keyboard to reload,\n' +
        'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {
    render() {
        return (
            <View style={styles.container}>
                {/*<TouchableOpacity onPress={()=>{CustomAlert.toast("测试","success")}}>*/}
                <TouchableOpacity onPress={()=>{CustomAlert.toast("网络连接失败，请稍后重试","fail")}}>
                    <Text style={styles.welcome}>弹出Toast && 图标</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={()=>{
                    CustomAlert.showLoading("正在载入中...");
                    setTimeout(()=>{
                        CustomAlert.hideLoading()
                    },3000)
                }}>
                    <Text style={styles.welcome}>弹出Loading</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={()=>{CustomAlert.alert("提示","测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试","fail",[{
                    text:'确认',color:'#DC143C',fontSize:'20',onPress:()=>CustomAlert.toast("成功","success")
                },{
                    text:'取消',onPress:()=>CustomAlert.toast("网络连接失败，请稍后重试","fail")
                }])}}>
                    <Text style={styles.welcome}>弹出Alert</Text>
                </TouchableOpacity>
                <Text style={styles.instructions}>To get started, edit App.js</Text>
                <Text style={styles.instructions}>{instructions}</Text>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    welcome: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
    instructions: {
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
