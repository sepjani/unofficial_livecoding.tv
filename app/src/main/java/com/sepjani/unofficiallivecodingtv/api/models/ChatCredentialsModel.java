package com.sepjani.unofficiallivecodingtv.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Valeriy Strucovskiy on 6/2/2016.
 */

public class ChatCredentialsModel {

    @SerializedName("user")
    @Expose
    public String userLink;
    @SerializedName("jid")
    @Expose
    public String jid;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("is_staff")
    @Expose
    public Boolean isStaff;

}