# KConnectionCheck
Simple library to check network connection status

Credit to https://stackoverflow.com/users/8187578/kebab-krabby

To detect connection changes, simply add a connection check to your activity's ```onCreate(Bundle savedInstanceState)```. 

# Download
Add this to your project-level build.gradle
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add this to your app-level build.gradle
```gradle
dependencies {
	        implementation 'com.github.kaungkhantsoe:ABUtilLibrary:1.0.0'
	}
```

# Usage
Simple usage
``` java
 ABConnectionUtil.addConnectionCheck(this, this, this);
```

Customize build
```java
ABConnectionUtil.CustomConnectionBuilder builder = new ABConnectionUtil.CustomConnectionBuilder();
        builder.setNoConnectionText("Custom No Connection Text");
        builder.setNoConnectionTextColor(getResources().getColor(android.R.color.holo_red_dark));
        builder.setNoConnectionDrawable(R.drawable.ic_no_connection);
        builder.setHideWhenConnectionRestored(false);
        builder.setDismissText("Close");

ABConnectionUtil.addConnectionCheck(this,
                this,
                this,
                builder
        );
```

Detect status change
```java
@Override
public void onConnectionStatusChange(boolean status) {
// Do something
}
 ```


# CustomBuilder methods
```java
setNoConnectionText(String noConnectionText)
setConnectionRestoredText(String connectionRestoredText)
setNoConnectionDrawable(@DrawableRes int noConnectionDrawable)
setConnectionRestoredDrawable(@DrawableRes int connectionRestoredDrawable)
setHideWhenConnectionRestored(boolean hideWhenConnectionRestored) // Default is true
setNoConnectionTextColor(@ColorInt int noConnectionTextColor)
setConnectionRestoredTextColor(@ColorInt int connectionRestoredTextColor)
setDismissTextColor(@ColorInt int dismissTextColor)
setDismissText(String dismissText)
```

# Screen shots
<img src="https://user-images.githubusercontent.com/32578035/94215894-e7f80f80-ff03-11ea-85cf-3dfe43fb7cb1.jpg" width="300" height="600">
<img src="https://user-images.githubusercontent.com/32578035/94215895-eaf30000-ff03-11ea-865e-815044edb1fc.jpg" width="300" height="600">
