
# react-native-troila-alert

## Getting started

`$ npm install react-native-troila-alert --save`

### Mostly automatic installation

`$ react-native link react-native-troila-alert`

### Manual installation


#### iOS

1. add `pod 'RNTroilaAlert', :path => '../node_modules/react-native-troila-alert/ios/RNTroilaAlert.podspec'` to your Pod file
2. run `pod install`

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.troila.alert.RNTroilaAlertPackage;` to the imports at the top of the file
  - Add `new RNTroilaAlertPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-troila-alert'
  	project(':react-native-troila-alert').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-troila-alert/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-troila-alert')
  	```
4. add `maven { url 'https://jitpack.io' }` to your project level `build.gradle`


## Usage
```javascript
import CustomAlert from 'react-native-troila-alert';

CustomAlert.toast("test")
CustomAlert.toast("toast with success icon","success")

CustomAlert.showLoading("Loading...")
CustomAlert.hideLoading()

CustomAlert.alert("title","content","warning",[{text:"confirm",onPress:()=>console.log("confirm clicked")}])
```

  