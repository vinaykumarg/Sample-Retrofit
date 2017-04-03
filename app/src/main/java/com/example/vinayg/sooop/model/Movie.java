package com.example.vinayg.sooop.model;

import org.immutables.value.Value;

import java.util.List;

/**
 * Created by vinay.g.
 */


@Value.Immutable
public abstract class Movie {
    public abstract String poster_path();
    public abstract boolean adult();
    public abstract String overview();
    public abstract String release_date();
    public abstract List<Integer> genre_ids();
    public abstract Integer id();
    public abstract String original_title();
    public abstract String original_language();
    public abstract String title();
    public abstract String backdrop_path();
    public abstract Double popularity();
    public abstract Integer vote_count();
    public abstract Boolean video();
    public abstract Double vote_average();

}
