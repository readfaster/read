package io.calhax.quickread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.Handler;
import org.apache.http.client.*;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


public class SpeedReader extends Activity {

    TextView flash_me;
    int WPM=600;
    int index = 0;
    String[] words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String toSplit = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        words = toSplit.split(" ");

        setContentView(R.layout.activity_speed_reader);
        flash_me = (TextView)findViewById(R.id.flash);
        //flash_me.setText("EXCEEDINGMAXCHARLIMIT");

        //now we go GET the json
        //String URL = "http://m.uploadedit.com/b041/14124339688.txt";
    }

    private Handler handle = new Handler();
    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            /*if ( index != words.length && words[index].length() != 0 && (words[index].substring(words[index].length()-1) == "." || words[index].substring(words[index].length()-1) == ",")) {
                System.out.println("here on word " + words[index]);

                flash_me.setText(words[index]);
                index++;
                handle.postDelayed(timer, (60000/WPM)*5);
            }*/
            if (index == 0) {
                flash_me.setText(words[index]);
                index++;
                handle.postDelayed(timer, 2000);
            }
            else if (index < words.length) {
                flash_me.setText(words[index]);
                index++;
                handle.postDelayed(timer, 60000/WPM);
            }
        }
        //TODO add pause at . and ,
    };

    @Override
    public void onResume() {
        super.onResume();
        handle.post(timer);
    }

    @Override
    public void onPause() {
        super.onPause();
        handle.removeCallbacks(timer);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.speed_reader, menu);
        return true;
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
        return super.onOptionsItemSelected(item);
    }
}
