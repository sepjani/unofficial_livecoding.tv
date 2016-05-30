package com.sepjani.unofficiallivecodingtv.api;

import com.sepjani.unofficiallivecodingtv.api.models.LiveVideoResult;
import com.sepjani.unofficiallivecodingtv.api.models.LivestreamerDetails;
import com.sepjani.unofficiallivecodingtv.api.models.SchedulePageModel;
import com.sepjani.unofficiallivecodingtv.api.models.UserDetailModel;
import com.sepjani.unofficiallivecodingtv.api.models.VideoOnAirSummaryModel;
import com.sepjani.unofficiallivecodingtv.api.models.ViewingKeyModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public interface ILivecodingAPI {

    @GET("/api/user")
    Call<UserDetailModel>
    getUser();

    @GET("/video/livestream/morroz/thumbnail_250_140")
    Observable<ResponseBody>
    getPreviewImage();

    @GET("/api/v1/user/viewing_key")
    Call<ViewingKeyModel>
    getViewingKey();

    //scheduledbroadcast***************************************************************

    /**
     * List the scheduled broadcasts
     *
     * @return
     */
    @GET("/api/scheduledbroadcast")
    Call<SchedulePageModel>
    getAllScheduledStreams();

    //livestreams***************************************************************

    /**
     * Get all channels that are currently on air
     *
     * @return
     */
    @GET("/api/livestreams/onair")
    Call<VideoOnAirSummaryModel>
    getVideosOnAir();

    /**
     * Lists all channels that had streaming activity
     *
     * @return
     */
    @GET("/api/livestreams")
    Call<LiveVideoResult>
    getLastVideos();

    /**
     * Get livestreamer details
     *
     * @return
     */
    @GET("/api/livestreams/{user__slug}/")
    Call<LivestreamerDetails>
    getStreamerDetails(@Path("user__slug") String nickname);

}
