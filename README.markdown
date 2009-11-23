DroidEx: Projecting Android on the Big(ger) Screen
==================================================

DroidEx displays a copy of your attached Android device's screen on
your own development machine's screen. Mostly, this is useful for
presentations, as you can attach an Android device to a notebook attached
to a projector, and your audience can see what is on the device. In
particular, this is good for demonstrations of things that cannot
readily be demonstrated via the emulator, such as GPS access or the
accelerometer.

This version of DroidEx works with the Android 2.0 SDK.

Usage
-----
This version of `droidex` takes 0-1 command-line parameters. The one
possible parameter is a floating-point value representing by how much
to scale the image (e.g., a scale of 1.25 on an HVGA device will give you
a 600x400 projected image). By default, the scale is 1.0, meaning the
project image will be as many pixels as is the physical screen size.

### Linux & OS X

Set your ANDROID_HOME environment variable to point to the base directory
where your Android SDK is installed. Then, just run the `droidex` shell
script.

### Windows

It is probably easiest to just copy the `droidex` shell script to a
`droidex.cmd` batch file, change the classpath separator, replace `$ANDROID_HOME`
with the proper value for your PC, and use the batch file. Also, you will
need to replace `$1` with `%1`.

Dependencies
------------
The Android 2.0 SDK, or at least ddmlib.jar.

Compatibility
-------------
DroidEx is known to work with:
 1.	T-Mobile G1
 2.	Google Ion
 3.	HTC Tattoo
 4.	Motorola DROID

DroidEx is known not to work with:
 1.	HTC Hero (at least early versions distributed by Sprint in the US)
 2.	ARCHOS 5 Android tablet

Version
-------
This is version 1.0, meaning DroidEx has been used a fair bit.

License
-------
The code in this project is licensed under the Apache
Software License 2.0, per the terms of the included LICENSE
file.

Questions
---------
If you have questions regarding the use of this code, please
join and ask them on the [cw-android Google Group][gg]. Be sure to
indicate which application you have questions about.

[gg]: http://groups.google.com/group/cw-android
