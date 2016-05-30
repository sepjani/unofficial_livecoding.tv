package com.sepjani.unofficiallivecodingtv.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Valeriy Strucovskiy on 5/29/2016.
 */
public class SchedulePageModel {

    @SerializedName("count")
    @Expose
    public Long count;

    @SerializedName("next")
    @Expose
    public String next;

    @SerializedName("previous")
    @Expose
    public String previous;

    @SerializedName("results")
    @Expose
    public List<ScheduleModel> results;
}
