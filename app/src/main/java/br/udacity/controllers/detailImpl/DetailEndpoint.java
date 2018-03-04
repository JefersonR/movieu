package br.udacity.controllers.detailImpl;

import br.udacity.models.response.ReviewsResponse;
import br.udacity.models.response.VideosResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jeferson on 22/11/15.
 */
public interface DetailEndpoint {

    /*
    Request method and URL specified in the annotation
    Callback for the parsed response is the last parameter
    */
    @GET("{id}/videos")
    Call<VideosResponse> getVideos(@Path("id") String movieID);

    @GET("{id}/reviews")
    Call<ReviewsResponse> getReviews(@Path("id") String movieID);


}