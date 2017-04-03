package com.example.vinayg.sooop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vinayg.sooop.model.Movie;

import java.util.List;

/**
 * Created by vinay.g.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private String url = "https://image.tmdb.org/t/p/w500";


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView poster;

        public MovieViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.subtitle);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
            poster = (ImageView)v.findViewById(R.id.poster);
        }
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        String murl = url + movies.get(position).poster_path();
        holder.movieTitle.setText(movies.get(position).title());
        holder.data.setText(movies.get(position).release_date());
        holder.movieDescription.setText(movies.get(position).overview());
        holder.rating.setText(movies.get(position).vote_average().toString());
        Glide
                .with(context)
                .load(murl)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
