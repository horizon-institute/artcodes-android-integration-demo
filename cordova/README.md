# Artcodes Cordova Demo

This is a demo of the Artcodes plugin for Cordova. Opens the Artcode scanner 1 second after the device ready event.

To use:
~~~~
$ cordova prepare
$ cordova build android
$ cordova run android --device
~~~~

# Use the plugin in your project

To add the Artcodes plugin to your Cordova app:
~~~~
$ cordova plugin add https://github.com/horizon-institute/artcodes-cordova.git --save
~~~~
To use:
~~~~
try {
    Artcodes.scan(
        { name: "Test Experience", actions: [{ codes: ["1:2:3:4"] }] }, 
        function (marker) { alert(marker); }
    ); 
} catch (err) { alert(err.message) }
~~~~
