package org.esiea.pouele_nemmene.emovies;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class GetMoviesService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_GET_ALL_MOVIES= "org.esiea.pouele_nemmene.emovies.action.GET_ALL_MOVIES";

    public GetMoviesService() {
        super("GetMoviesServices");
    }

    /**
     * Starts this service to perform action GET_ALL_MOVIES with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionMOVIES(Context context) {
        Intent intent = new Intent(context, GetMoviesService.class);
        intent.setAction(ACTION_GET_ALL_MOVIES);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ALL_MOVIES.equals(action)) {
                this.handleActionMOVIES();
            }
        }
    }

    /**
     * Handle action GET_ALL_MOVIES in the provided background thread with the provided
     * parameters.
     */
    private void handleActionMOVIES() {
        // throw new UnsupportedOperationException("Not yet implemented");

        Log.d("New Thread", "Thread service name: "+ Thread.currentThread().getName());

        URL url = null;

        try{
            url = new URL("http://api.betaseries.com/shows/list?fields=id,title,genres,length,description,seasons,episodes,creation,language,images.poster&limit=100&v=3.0&key=280c3df53787");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                this.copyInputStreamToFile(conn.getInputStream(),
                        new File(getCacheDir(), "movies.json"));
                Log.d("Downloaded", "Movies json downloaded");

                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.MOVIES_UPDATE));
            }

        } catch (MalformedURLException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream in, File file){
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
