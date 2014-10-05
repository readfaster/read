package io.calhax.quickread;

import android.os.AsyncTask;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by james on 10/4/14.
 */
public class POST extends AsyncTask<String, Void, String> {
    String output;
    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        postData(params[0]);
        return output;
    }

    public String postData(String username) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "http://hack.allen.li/api/v1/articles/get.json");


        // Add your data
        BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("username", username);
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(usernameBasicNameValuePair);

        try {
            // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
            //This is typically useful while sending an HTTP POST request.
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

            // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
            httppost.setEntity(urlEncodedFormEntity);

            try {
                // HttpResponse is an interface just like HttpPost.
                //Therefore we can't initialize them
                HttpResponse httpResponse = httpclient.execute(httppost);

                // According to the JAVA API, InputStream constructor do nothing.
                //So we can't initialize InputStream although it is not an interface
                InputStream inputStream = httpResponse.getEntity().getContent();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();

                String bufferedStrChunk = null;

                while((bufferedStrChunk = bufferedReader.readLine()) != null){
                    stringBuilder.append(bufferedStrChunk);
                }
                output = stringBuilder.toString();
                //System.out.println(stringBuilder.toString());
                return stringBuilder.toString();

            } catch (ClientProtocolException cpe) {
                System.out.println("First Exception caz of HttpResponese :" + cpe);
                cpe.printStackTrace();
            } catch (IOException ioe) {
                System.out.println("Second Exception caz of HttpResponse :" + ioe);
                ioe.printStackTrace();
            }

        } catch (UnsupportedEncodingException uee) {
            System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
            uee.printStackTrace();
        }
        return null;
    }

}
