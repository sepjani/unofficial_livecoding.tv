package com.sepjani.unofficiallivecodingtv.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Valeriy Strucovskiy on 5/28/2016.
 */
public class VideoOnAirSummaryModel {

    @SerializedName("count")
    @Expose
    public String count;

    @SerializedName("next")
    @Expose
    public String next;

    @SerializedName("previous")
    @Expose
    public String previous;

    @SerializedName("results")
    @Expose
    public List<VideoOnAirModel> results;
}
