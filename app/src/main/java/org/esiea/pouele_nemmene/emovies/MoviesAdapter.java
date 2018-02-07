package org.esiea.pouele_nemmene.emovies;

import android.content.ContentProvider;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adams on 29/01/18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {


    private JSONArray movies;
    private final Map<Integer, String> movieIds = new HashMap<>();

    public class MovieHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView year;
        public TextView duration;
        public TextView genre;
        public ImageView poster;
        public Button detailsBtn;

        public MovieHolder(View itemView) {

            super(itemView);
            this.title = itemView.findViewById(R.id.movie_rv_element_title);
            this.year = itemView.findViewById(R.id.movie_rv_element_year);
            this.duration = itemView.findViewById(R.id.movie_rv_element_duration);
            this.genre = itemView.findViewById(R.id.movie_rv_element_genre);
            this.poster = itemView.findViewById(R.id.movie_rv_element_poster);
            this.detailsBtn = itemView.findViewById(R.id.show_btn);
        }
    }

    public MoviesAdapter(JSONArray jsonArray) {
        super();
        this.movies = jsonArray;
    }

    public void setNewMovie(JSONArray jsonArray){
        this.movies = jsonArray;
        notifyDataSetChanged();
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_rv_element, null, false);

        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        try {
            String movieId = this.movies.getJSONObject(position).getString("id");
            ContextProvider.setLoadedMovieId(movieId);
            holder.title.setText(this.movies.getJSONObject(position).getString("title"));
            holder.year.setText("Année : " + this.movies.getJSONObject(position).getString("creation"));
            holder.duration.setText("Durée : " + this.movies.getJSONObject(position).getString("length")+" mn");

            movieIds.put(position, movieId);

            try{
                holder.genre.setText(this.movies.getJSONObject(position).getJSONArray("genres").getString(0));

            }catch (Exception e){
                holder.genre.setText("");
            }

            try{
                Glide.with(ContextProvider.getContext()).load(this.movies.getJSONObject(position).getJSONObject("images").getString("poster"))
                        .override(900, 300)
                        .centerCrop()
                        .into(holder.poster);
            }catch (Exception e){
            }

            holder.detailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(ContextProvider.getContext(), DetailsActivity.class);
                    intent.putExtra("movieId", movieIds.get(position)); //Any getter of your class you want
                    ContextProvider.getCurrentActivity().startActivity(intent);
                }
            });

            } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.movies.length();
    }
}
