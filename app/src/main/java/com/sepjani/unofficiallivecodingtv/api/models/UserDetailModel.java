package com.sepjani.unofficiallivecodingtv.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */

public class UserDetailModel {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("slug")
    @Expose
    public String slug;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("favorite_programming")
    @Expose
    public String favoriteProgramming;
    @SerializedName("favorite_ide")
    @Expose
    public String favoriteIde;
    @SerializedName("favorite_coding_background_music")
    @Expose
    public String favoriteCodingBackgroundMusic;
    @SerializedName("favorite_code")
    @Expose
    public String favoriteCode;
    @SerializedName("years_programming")
    @Expose
    public Integer yearsProgramming;
    @SerializedName("want_learn")
    @Expose
    public String wantLearn;
    @SerializedName("registration_date")
    @Expose
    public String registrationDate;
    @SerializedName("timezone")
    @Expose
    public String timezone;

}