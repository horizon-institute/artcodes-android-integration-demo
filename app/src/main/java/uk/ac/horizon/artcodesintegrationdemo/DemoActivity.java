package uk.ac.horizon.artcodesintegrationdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import uk.ac.horizon.artcodes.model.Action;
import uk.ac.horizon.artcodes.model.Experience;
import uk.ac.horizon.artcodes.scanner.ScannerActivity;

public class DemoActivity extends AppCompatActivity
{
    private Context context;

    private Map<String,String> data = new HashMap<>();

    private final int ARTCODE_REQUEST = 2;
    private Experience experience = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.context = this;

        // Load your data from somewhere
        this.data.put("1:1:1:1:2", "Artcode 1");
        this.data.put("1:1:2:4:4", "Artcode 2");
        this.data.put("1:1:1:3:3", "Artcode 3");
        this.data.put("1:1:1:1:5", "Artcode 4");
        this.data.put("1:1:2:3:5", "Artcode 5");
        this.data.put("1:1:1:2:4", "Artcode 6");

        // Create and configure an Artcode experience
        experience = new Experience();
        for (String code : this.data.keySet())
        {
            // Create Actions for the Artcodes you want to scan
            Action action = new Action();
            action.getCodes().add(code);
            experience.getActions().add(action);
        }

        // Set a button to open the Artcodes Scanner
        final FloatingActionButton cameraButton = (FloatingActionButton) findViewById(R.id.fab);
        if (cameraButton != null)
        {
            cameraButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    synchronized (context)
                    {
                        cameraButton.setEnabled(false);

                        // Create and setup the intent that will launch the Artcode scanner
                        Intent intent = new Intent(context, ScannerActivity.class);

                        // Put experience in intent
                        Gson gson = new GsonBuilder().create();
                        intent.putExtra("experience", gson.toJson(experience));

                        // Start artcode reader activity
                        startActivityForResult(intent, ARTCODE_REQUEST);
                    }
                }
            });
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        synchronized (context)
        {
            final FloatingActionButton cameraButton = (FloatingActionButton) findViewById(R.id.fab);
            if (cameraButton != null)
            {
                cameraButton.setEnabled(true);
            }
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == ARTCODE_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                // This is where you will receive a response from the
                // Artcode scanner if an Artcode was found

                // This is the code of the Artcode that was found (e.g. "1:1:1:1:2")
                String code = data.getStringExtra("marker");

                // Do any logic based on the result here, for example display the code in a TextView
                TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
                if (resultTextView != null)
                {
                    resultTextView.setText("Found code " + code + ": " + this.data.get(code) + "!");
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
