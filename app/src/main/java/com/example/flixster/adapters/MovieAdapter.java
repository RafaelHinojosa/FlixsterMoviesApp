package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Inflate a layout returned inside a ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Populates the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get movie at position
        Movie movie = movies.get(position);
        // Bind the movie data into the view holder
        holder.bind(movie);
    }

    // Returns number of movies
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Context getContext() { return this.context; }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            String placeHolder;

            int radius = 30;
            int margin = 10;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Glide.with(context)
                        .load(movie.getBackdropPath())
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .into(ivPoster);
            }
            else {
                Glide.with(context)
                        .load(movie.getPosterPath())
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .into(ivPoster);
            }
        }

        // HACER ARRAY PARA MAPEAR COLORES CON NUMEROS DE VOTEAVERAGE

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvOverview() {
            return tvOverview;
        }

        public ImageView getIvPoster() {
            return ivPoster;
        }
    }
}
