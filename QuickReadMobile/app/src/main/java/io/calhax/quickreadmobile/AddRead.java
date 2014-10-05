package io.calhax.quickreadmobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;


public class AddRead extends Activity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_read);


        dumpIntent(getIntent());
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String out = bundle.get("android.intent.extra.TEXT").toString();


        if (out != null) {
            editText = (EditText) findViewById(R.id.boxen);
            editText.setText(out);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_read, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    public static void dumpIntent(Intent i){

        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.e("hi", "Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.e("hi","[" + key + "=" + bundle.get(key)+"]");
            }
            Log.e("hi","Dumping Intent end");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.donebutton) {
            //POST
            System.out.println("PRESSED");
            try {
                //String httpResponse = new POST().execute("url", toAdd).get();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("Read", 0);
                String username = pref.getString("id", null);
                new POST2().execute(username, editText.getText().toString(), "http://hack.allen.li/api/v1/articles/new.json");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
