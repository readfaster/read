package io.calhax.quickread;

import com.google.android.glass.app.Card;
import com.google.android.glass.media.Sounds;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An {@link Activity} showing a tuggable "Hello World!" card.
 * <p>
 * The main content view is composed of a one-card {@link CardScrollView} that provides tugging
 * feedback to the user when swipe gestures are detected.
 * If your Glassware intends to intercept swipe gestures, you should set the content view directly
 * and use a {@link com.google.android.glass.touchpad.GestureDetector}.
 * @see <a href="https://developers.google.com/glass/develop/gdk/touch">GDK Developer Guide</a>
 */
public class MainActivity extends Activity {

    /** {@link CardScrollView} to use as the main content view. */
    private CardScrollView mCardScroller;

    private List<CardBuilder> mCards;
    private  CustomCardScrollAdapter mAdapter;
    JSONObject js1n;
    ArrayList<String> adapt;
    ArrayList<String> contents;
    ArrayList<String> dates;

    String pass;
    public final static String EXTRA_MESSAGE = "ARTICLE_TEXT";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        //mView = buildView();
        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        String dataHttp = null;
        adapt = new ArrayList<String>();
        contents = new ArrayList<String>();
        dates = new ArrayList<String>();

        //POST user and pass to server
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
                String cont = u.getString("text");
                String dat = u.getString("date");
                adapt.add(title);
                contents.add(cont);
                dates.add(dat);
            }
        }
        catch (Exception e) {
            //malformed json from serv3r
            e.printStackTrace();
        }

        System.out.println(adapt.toString());
        System.out.println(contents.toString());

        createCards();

        mAdapter = new CustomCardScrollAdapter();
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
        setContentView(mCardScroller);

        mCardScroller.activate();
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Plays disallowed sound to indicate that TAP actions are not supported.
                //AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                //am.playSoundEffect(Sounds.DISALLOWED);
                pass = contents.get(position);
                sendMessage(view);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }

    private void createCards() {
        mCards = new ArrayList<CardBuilder>();
        System.out.println("adapt size " + adapt.size());
        for (int i = 0; i < adapt.size(); i++) {
            System.out.println("GETTING FROM ARRAY " + adapt.get(i));
            mCards.add(new CardBuilder(this, CardBuilder.Layout.TEXT).setText(adapt.get(i)).setFootnote(dates.get(i)));

        }
    }/*
    private void createCards() {
        mCards = new ArrayList<CardBuilder>();

        mCards.add(new CardBuilder(this, CardBuilder.Layout.TEXT)
        .setText("This card has a footer.")
        .setFootnote("I'm the footer!"));

        mCards.add(new CardBuilder(this, CardBuilder.Layout.CAPTION)
        .setText("This card has a puppy background image.")
        .setFootnote("How can you resist?"));

        mCards.add(new CardBuilder(this, CardBuilder.Layout.COLUMNS)
        .setText("This card has a mosaic of puppies.")
        .setFootnote("Aren't they precious?"));
    }*/

    public void sendMessage(View view) {
        Intent intent = new Intent(this, SpeedReader.class);
        intent.putExtra(EXTRA_MESSAGE, pass);
        startActivity(intent);
    }

    private class CustomCardScrollAdapter extends CardScrollAdapter {
        @Override
        public int getPosition(Object item) {
            return mCards.indexOf(item);
        }

        @Override
        public int getCount() {
            return mCards.size();
        }

        @Override
        public Object getItem(int position) {
            return mCards.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return CardBuilder.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position){
            return mCards.get(position).getItemViewType();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return mCards.get(position).getView(convertView, parent);
        }
    }

}
