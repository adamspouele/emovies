package org.esiea.pouele_nemmene.emovies;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class SearchableActivity extends AppCompatActivity {

    String searchUrl = "https://api.betaseries.com/search/all?query=<Nom du film>&limit=<un entier>&v=3.0&key=280c3df53787";
    URL url;
    JSONArray results;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
            Log.d("RECHERCHER : ", query.toString());
            this.search(this.getSearchUrl(query));
        }

        searchView = (SearchView)findViewById(R.id.search_movie);

        // searchView.setActivity(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // search(getSearchUrl(query));
                Log.d("Query : ", getSearchUrl(query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // search(getSearchUrl(newText));
                Log.d("Query : ", getSearchUrl(newText));
                return true;
            }
        });
    }

    public void search(String queryUrl){
        try{
            // url = new URL("http://api.betaseries.com/shows/list?fields=id,title,genres,length,description,seasons,episodes,creation,language,images.poster&limit=100&v=3.0&key=280c3df53787");
            url = new URL(queryUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {

                BufferedReader r = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sbuilder = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    sbuilder.append(line);
                }

                try {
                    results = new JSONArray(sbuilder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.MOVIES_UPDATE));
            }

        } catch (MalformedURLException e ) {
            // .printStackTrace();
            Log.d("ERROR MALFORMED URL : ", e.getMessage());
        } catch (IOException e) {
            // e.printStackTrace();
            Log.d("ERROR : ", e.getMessage());
        }
    }

    public String getSearchUrl(String query){
        Log.d("IN : ", query);

        return "https://api.betaseries.com/search/all?query="+query+"&limit=5&v=3.0&key=280c3df53787";
    }
}


/*class CustomSearchView extends SearchView{

    public Activity activity;

    public CustomSearchView(Context context) {
        super(context);
    }

    public void setActivity(Activity _act){
        this.activity = _act;
    }

    public interface OnQueryTextListener {
        boolean onQueryTextSubmit(String var1);

        boolean onQueryTextChange(String var1);
    }
}*/
