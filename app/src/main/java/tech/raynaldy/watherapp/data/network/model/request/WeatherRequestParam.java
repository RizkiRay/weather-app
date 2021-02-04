package tech.raynaldy.watherapp.data.network.model.request;

import com.google.gson.annotations.Expose;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/4/21.
 */

public class WeatherRequestParam {
    @Expose
    private Double lat;
    @Expose
    private Double lon;

    public WeatherRequestParam(Double lat, Double lon){
        this.lat = lat;
        this.lon = lon;
    }
}
