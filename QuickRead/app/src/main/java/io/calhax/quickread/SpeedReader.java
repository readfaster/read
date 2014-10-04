package io.calhax.quickread;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.Handler;


public class SpeedReader extends Activity {

    TextView flash_me;
    int WPM=600;
    int index = 0;
    String[] words = "This is a test at 600 words per minute of the glass speed reading app".split(" ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_reader);
        flash_me = (TextView)findViewById(R.id.flash);
        //flash_me.setText("EXCEEDINGMAXCHARLIMIT");
    }

    private Handler handle = new Handler();
    private Runnable timer = new Runnable() {
        @Override
        public void run() {
            if (index < words.length) {
                flash_me.setText(words[index]);
                index++;
            }
            handle.postDelayed(timer, 60000/WPM);
        }
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
