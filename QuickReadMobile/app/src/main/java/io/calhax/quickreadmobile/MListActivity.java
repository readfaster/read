package io.calhax.quickreadmobile;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MListActivity extends ListActivity {

    JSONObject js1n;
    ArrayList<String> adapt;
    ArrayList<String> dates;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        String justALogin = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE1);


        if (justALogin != "true") {
            //register using POSTs
        }
        //then GET list always

        String dataHttp = null;
        adapt = new ArrayList<String>();
        dates = new ArrayList<String>();

        UserEmailFetcher fetch = new UserEmailFetcher();
        String username = fetch.getEmail(this);
        System.out.println(username);
        try {
            new POST().execute("username", username);
            new POST().execute("password", "a24cws435");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //now we're authorized, go!
        try{
            dataHttp = new JSONParsee().execute("http://m.uploadedit.com/b041/1412483757410.txt").get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(dataHttp);

        try {
            js1n = new JSONObject(dataHttp);
            JSONArray js0n = js1n.getJSONArray("articles");
            for (int i=0; i<js0n.length(); i++) {
                JSONObject u = js0n.getJSONObject(i);
                System.out.println("U IS " + u);
                String title = u.getString("title");
                System.out.println("TITLE IS " + title);
                String dat = u.getString("date");
                adapt.add(title);
                dates.add(dat);
            }
        }
        catch (Exception e) {
            //malformed json from serv3r
            e.printStackTrace();
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, adapt);

        setContentView(R.layout.activity_list);
        //Outline
        int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
        Outline outline = new Outline();
        outline.setOval(0, 0, size, size);
        findViewById(R.id.fab).setOutline(outline);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
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
