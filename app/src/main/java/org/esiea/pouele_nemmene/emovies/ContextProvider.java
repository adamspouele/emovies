package org.esiea.pouele_nemmene.emovies;

import android.app.Activity;
import android.content.Context;

import org.json.JSONArray;

/**
 * Created by adams on 29/01/18.
 */

public class ContextProvider {

    static Context context;
    static Activity activity;

    // id
    static String loadedMovie;

    public static void setContext(Context c){
        context = c;
    }

    public static Context getContext(){
        return context;
    }

    public static void setCurrentActivity (Activity a){
        activity = a;
    }

    public static Context getCurrentActivity(){
        return activity;
    }

    public static void setLoadedMovieId (String a){
        loadedMovie = a;
    }

    public static String getLoadedMovieId() {
        return loadedMovie;
    }
}
