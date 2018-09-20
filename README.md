
# react-native-troila-alert

## Getting started

`$ npm install react-native-troila-alert --save`

### Mostly automatic installation

`$ react-native link react-native-troila-alert`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-troila-alert` and add `RNTroilaAlert.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNTroilaAlert.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

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


## Usage
```javascript
import RNTroilaAlert from 'react-native-troila-alert';

// TODO: What to do with the module?
RNTroilaAlert;
```
  