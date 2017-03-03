Artcodes Integration Demo (Android)
=============

This project is a simple demo of how Artcodes can be included in an Android app.

Artcodes marks a paradigm shift in visual recognition, offering difference to discerning brands. We design visually beautiful images and encode them, resulting in the same interactivity as that of the QR code but with a more visually engaging and playful experience. Images can be the identical with unique codes or the opposite visually unique with identical codes. This interplay offers a new opportunity for visual interaction within, product, packaging, service and interaction design.

The Horizon Aestheticodes applications and libraries are publicly
distributed under [version 3 of the Affero General Public
License](https://gnu.org/licenses/agpl-3.0.txt). If you obtained this
code under that license, then all its terms are applicable.

The same applications and libraries are also available under
alternative license terms negotiated with the Horizon Digital Economy
Research Institute at the University of Nottingham. Contact
<horizon@nottingham.ac.uk> for more details.

------------------------------------
Adding Artcodes to your project
====================================

Step 1: Add artcodes to your build.gradle dependencies:
```gradle
compile 'uk.ac.horizon.artcodes:artcodes-scanner:3.3.0'
```

Step 2: Create an Artcode experience object to pass to the Artcodes scanner.
```java
import uk.ac.horizon.artcodes.model.Experience;
import uk.ac.horizon.artcodes.model.Action;

...

Experience experience = new Experience();
String[] codes = {"1:1:1:1:2", "1:1:2:4:4"};
for (String code : codes)
{
	Action action = new Action();
	action.getCodes().add(code);
	experience.getActions().add(action);
}
```

Step 3: Start the Artcodes scanner activity.
```java
import android.content.Intent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import uk.ac.horizon.artcodes.scanner.ScannerActivity;

...

Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);

// Put experience in intent
Gson gson = new GsonBuilder().create();
intent.putExtra("experience", gson.toJson(experience));

startActivityForResult(intent, ARTCODE_REQUEST);
```
Note: ARTCODE_REQUEST is a int constant (0-65535) you define so you know it's Artcodes returning a result later.

Step 4: Handle the response, implement [onActivityResult](http://developer.android.com/reference/android/app/Activity.html)
```java
protected void onActivityResult(int requestCode, int resultCode, Intent data)
{
  if (requestCode == ARTCODE_REQUEST)
  {
    if (resultCode == RESULT_OK)
    {
		// This is where you will receive a response from the
		// Artcode scanner if an Artcode was found
		// Do any logic based on the result here

		// This is the code of the Artcode that was found (e.g. "1:1:1:1:2")
		String code = data.getStringExtra("marker");
    }
  }
}
```

Note: To access the camera this library will add the "Hardware controls: take pictures and video" permission to your app.
