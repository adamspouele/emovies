package org.esiea.pouele_nemmene.emovies;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private Toolbar emoviesToolBar;
    final static String MOVIES_UPDATE = "MOVIES_UPDATE";
    RecyclerView emovies_rv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // provide Application context to MoviesProvider class
        ContextProvider.setContext(getApplicationContext());
        ContextProvider.setCurrentActivity(this);

        // initialize ToolBar
        this.emoviesToolBar = (Toolbar) findViewById(R.id.emovies_toolbar);
        setSupportActionBar(emoviesToolBar);

        startServices();

        // index and configure recyclerView
        this.emovies_rv = (RecyclerView) findViewById(R.id.emovies_rv);
        emovies_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        emovies_rv.setAdapter(new MoviesAdapter(MoviesProvider.getMovies()));

        IntentFilter intentFilter = new IntentFilter(MOVIES_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new MovieUpdate(this),intentFilter);
    }

    public void startServices(){
        GetMoviesService.startActionMOVIES(this);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(ContextProvider.getContext(), SearchableActivity.class);
                ContextProvider.getCurrentActivity().startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.emovies_menu, menu);

        return true;
    }
}
