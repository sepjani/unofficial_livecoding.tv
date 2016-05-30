package com.sepjani.unofficiallivecodingtv.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Valeriy Strucovskiy on 5/27/2016.
 */
public class ViewingKeyModel {

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("viewing_key")
    @Expose
    public String viewingKey;
}
