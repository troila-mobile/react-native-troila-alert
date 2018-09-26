/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View, TouchableOpacity} from 'react-native';

import CustomAlert from 'react-native-troila-alert'

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
                <TouchableOpacity onPress={()=>CustomAlert.toast("测试底部文字")}>
                    <Text style={styles.welcome}>弹出底部TOAST</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={()=>CustomAlert.toast("测试底部文字","success")}>
                    <Text style={styles.welcome}>弹出TOAST && ICON</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={()=>{
                    CustomAlert.showLoading("正在加载中...");
                    setTimeout(()=>{
                        CustomAlert.hideLoading()
                    },3000)
                }}>
                    <Text style={styles.welcome}>弹出LOADING</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={()=>CustomAlert.alert("标题","测试弹出框内容文字特别多啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊","warning",[{text:"确认",onPress:()=>{
                        console.log("confirmed")
                    }},{text:"取消",onPress:()=>{
                        CustomAlert.toast("点击取消","fail")
                    }}])}>
                    <Text style={styles.welcome}>弹出ALERT</Text>
                </TouchableOpacity>
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
