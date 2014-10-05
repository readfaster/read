package io.calhax.quickreadmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends Activity {
    public final static String EXTRA_MESSAGE = "USERNAME";
    public final static String EXTRA_MESSAGE1 = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme);
        setContentView(R.layout.activity_login);
        UserEmailFetcher fetch = new UserEmailFetcher();
        String username = fetch.getEmail(this);
        EditText editText = (EditText) findViewById(R.id.email_address);
        editText.setText(username);

        final Button button = (Button) findViewById(R.id.button); //login
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(view);
            }
        });
        final Button button2 = (Button) findViewById(R.id.button2); //create
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage2(view);
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, MListActivity.class);
        EditText editText = (EditText) findViewById(R.id.email_address);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_MESSAGE1, "true");
        startActivity(intent);
    }

    public void sendMessage2(View view) {
        Intent intent = new Intent(this, MListActivity.class);
        EditText editText = (EditText) findViewById(R.id.email_address);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_MESSAGE1, "true");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
