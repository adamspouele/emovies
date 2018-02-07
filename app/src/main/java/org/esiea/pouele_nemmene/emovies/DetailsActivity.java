package org.esiea.pouele_nemmene.emovies;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    JSONArray movies;
    JSONObject currentMovie;
    String movieId;
    Button seasonsBtn;
    Button episodesBtn;
    Button searchMoreBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView movieTitle = (TextView) findViewById(R.id.movie_title);
        TextView movieYear = (TextView) findViewById(R.id.movie_year);
        TextView movieDuration = (TextView) findViewById(R.id.movie_duration);
        TextView movieResume = (TextView) findViewById(R.id.movie_resume);
        TextView movieGenre = (TextView) findViewById(R.id.movie_genre);
        ImageView movieImage = (ImageView) findViewById(R.id.movie_image);

        seasonsBtn = (Button)findViewById(R.id.seasons_btn);
        episodesBtn = (Button)findViewById(R.id.episodes_btn);
        searchMoreBtn = (Button)findViewById(R.id.search_more);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            movieId = null;
        } else {
            movieId = extras.getString("movieId");

            movies = MoviesProvider.getMovies();

            String id;
            for (int i = 0; i < this.movies.length(); i++) {
                try {
                    id = String.valueOf(movies.getJSONObject(i).getInt("id"));
                    if(id.equals(movieId))
                        currentMovie = this.movies.getJSONObject(i);

                }catch (Exception e){

                }
            }

            try {
                movieTitle.setText(currentMovie.getString("title"));
                movieYear.setText(currentMovie.getString("creation"));
                movieDuration.setText(currentMovie.getString("length") + "mn");
                movieGenre.setText(currentMovie.getJSONArray("genres").getString(0));
                movieResume.setText(currentMovie.getString("description"));

                Glide.with(ContextProvider.getContext()).load(currentMovie.getJSONObject("images").getString("poster"))
                        .override(1500, 1000)
                        .into(movieImage);

            }catch (Exception e){
            }

            this.buttonsListeners();
        }

        searchMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("http://www.google.fr/#q="+currentMovie.getString("title"));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }catch (Exception e){
                }
            }
        });
    }

    public void buttonsListeners(){
        seasonsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContextProvider.getContext(), EMoviesDialog.class);
                try {
                    intent.putExtra("message", currentMovie.getString("seasons")+" saison(s)");

                }catch (Exception e){
                }
                ContextProvider.getCurrentActivity().startActivity(intent);
            }
        });

        episodesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContextProvider.getContext(), EMoviesDialog.class);
                try {
                    intent.putExtra("message", currentMovie.getString("episodes")+" episode(s)");

                }catch (Exception e){
                }
                ContextProvider.getCurrentActivity().startActivity(intent);
            }
        });
    }

}
