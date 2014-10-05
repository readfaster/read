package io.calhax.quickreadmobile;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Outline;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MListActivity extends Activity {

    JSONObject js1n;
    ArrayList<String> adapt;
    ArrayList<String> dates;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Read", 0); // 0 - for private mode
        if (!pref.getBoolean("loggd", false)) {
            System.out.println("ENTERED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_list);
        ListView listView = (ListView)findViewById(R.id.listr);
        String message;
        if (pref.getString("id", null) == null) {
            Intent intent = getIntent();
            message = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        }
        else {
            message = pref.getString("id", null);
        }

        System.out.println("my id " + message);

        String userHttp = null;

        //register using POSTs
        try {
            userHttp = new POST().execute(message, "http://hack.allen.li/api/v1/add_user.json").get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (userHttp == "{\"error\":\"none\"}") {
            //successful creation
            //toast it
        }
        else {
            //toast error
            //what does error look like with already-acct?
        }

        //then GET list always

        String dataHttp = null;
        adapt = new ArrayList<String>();
        dates = new ArrayList<String>();

        try {
            dataHttp = new POST().execute(message, "http://hack.allen.li/api/v1/articles/get.json").get();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(dataHttp);

        String[] strry = null;
        try {
            js1n = new JSONObject(dataHttp);
            JSONArray js0n = js1n.getJSONArray("articles");
            strry = new String[js0n.length()];
            for (int i=0; i<js0n.length(); i++) {
                JSONObject u = js0n.getJSONObject(i);
                //System.out.println("U IS " + u);
                String title = u.getString("title");
                //System.out.println("TITLE IS " + title);
                String dat = u.getString("date");
                adapt.add(title);
                strry[i] = title;
                dates.add(dat);
            }
        }
        catch (Exception e) {
            //malformed json from serv3r
            e.printStackTrace();
        }

        if (strry.length == 0) {
            System.out.println("BLANK LAYOUT");
            setContentView(R.layout.empty_list);
            int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
            Outline outline = new Outline();
            outline.setOval(0, 0, size, size);
            findViewById(R.id.fab2).setOutline(outline);

        }
        else {
            System.out.println(strry[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strry);
            //ListView listView = (ListView) findViewById(R.id.listr);

            listView.setAdapter(adapter);

            //setContentView(R.layout.activity_list);
            //Outline
            int size = getResources().getDimensionPixelSize(R.dimen.fab_size);
            Outline outline = new Outline();
            outline.setOval(0, 0, size, size);
            findViewById(R.id.fab).setOutline(outline);
        }


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
