package com.sepjani.unofficiallivecodingtv.api.models;

/**
 * Created by Valeriy Strucovskiy on 5/28/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LivestreamerDetails {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("user")
    @Expose
    public String user;
    @SerializedName("user__slug")
    @Expose
    public String userSlug;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("coding_category")
    @Expose
    public String codingCategory;
    @SerializedName("difficulty")
    @Expose
    public String difficulty;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("tags")
    @Expose
    public String tags;
    @SerializedName("is_live")
    @Expose
    public Boolean isLive;
    @SerializedName("viewers_live")
    @Expose
    public Integer viewersLive;
    @SerializedName("viewing_urls")
    @Expose
    public List<String> viewingUrls = new ArrayList<String>();
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;

}