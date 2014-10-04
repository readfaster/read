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
    JSONArray articles;
    ArrayList<String> adapt;
    ArrayList<String> contents;
    JSONObject json;

    String pass;
    public final static String EXTRA_MESSAGE = "ARTICLE_TEXT";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //mView = buildView();

        adapt = new ArrayList<String>();

        new JSONParse().execute();


        try {
            articles = json.getJSONArray("article");
            for (int i=0; i<articles.length(); i++) {
                JSONObject u = articles.getJSONObject(i);
                String title = u.getString("title");
                String cont = u.getString("contents");
                adapt.add(title);
                contents.add(cont);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new CustomCardScrollAdapter();

        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(mAdapter);
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
        setContentView(mCardScroller);
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

        for (int i = 0; i < articles.length(); i++) {
            mCards.add(new CardBuilder(this, CardBuilder.Layout.TEXT).setText(adapt.get(i)));

        }
    }

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

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        protected JSONObject doInBackground(String... args) {
            JSONGet js = new JSONGet();
            json =  js.getJSONhttp("http://m.uploadedit.com/b041/1412460692157.txt");
            return json;
        }
    }

}
