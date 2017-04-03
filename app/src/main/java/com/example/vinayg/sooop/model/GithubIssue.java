package com.example.vinayg.sooop.model;

import com.google.gson.annotations.SerializedName;

import org.immutables.value.Value;

/**
 * Created by vinay.g.
 */
@Value.Immutable
public abstract class GithubIssue {
    public abstract String id();
    public abstract String title();
    @SerializedName("comments_url")
    public abstract String commentsUrl();
    @SerializedName("body")
    public abstract String comment();
}
