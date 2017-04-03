package com.example.vinayg.sooop.model;
import com.google.gson.annotations.SerializedName;

import org.immutables.value.Value;

import java.util.List;

/**
 * Created by vinay.g.
 */
@Value.Immutable
public abstract class MoviesResponse {

    public abstract int page();
    @SerializedName("results")
    public abstract List<Movie> getResults();
    public abstract int total_results();
    public abstract int total_pages();


}
