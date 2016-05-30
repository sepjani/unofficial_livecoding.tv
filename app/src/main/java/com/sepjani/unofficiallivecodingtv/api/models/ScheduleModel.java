package com.sepjani.unofficiallivecodingtv.api.models;

/**
 * Created by Valeriy Strucovskiy on 5/29/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScheduleModel {

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("livestream")
    @Expose
    public String livestream;
    @SerializedName("coding_category")
    @Expose
    public String codingCategory;
    @SerializedName("difficulty")
    @Expose
    public String difficulty;
    @SerializedName("start_time")
    @Expose
    public String startTime;
    @SerializedName("start_time_original_timezone")
    @Expose
    public String startTimeOriginalTimezone;
    @SerializedName("original_timezone")
    @Expose
    public String originalTimezone;
    @SerializedName("is_featured")
    @Expose
    public Boolean isFeatured;
    @SerializedName("is_recurring")
    @Expose
    public Boolean isRecurring;

}