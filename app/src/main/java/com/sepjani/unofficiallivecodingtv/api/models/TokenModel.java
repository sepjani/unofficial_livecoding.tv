package com.sepjani.unofficiallivecodingtv.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public class TokenModel {

    @SerializedName("access_token")
    @Expose
    public String token;

    @SerializedName("token_type")
    @Expose
    public String type;

    @SerializedName("expires_in")
    @Expose
    public long expiresIn;

    @SerializedName("refresh_token")
    @Expose
    public String refreshToken;

    @SerializedName("scope")
    @Expose
    public String scope;
}
