package org.esiea.pouele_nemmene.emovies;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by adams on 29/01/18.
 */

public class MoviesProvider {


    public MoviesProvider(){
    }

    public static JSONArray getMovies(){
        try {
            InputStream is = new FileInputStream(ContextProvider.getContext().getCacheDir() + "/" + "movies.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            JSONObject json = new JSONObject(new String(buffer, "UTF-8"));

            return json.getJSONArray("shows");
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
