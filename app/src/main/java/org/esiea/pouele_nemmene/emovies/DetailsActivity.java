package org.esiea.pouele_nemmene.emovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    JSONArray movies;
    JSONObject currentMovie;
    String movieId;

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
        }
    }
}
