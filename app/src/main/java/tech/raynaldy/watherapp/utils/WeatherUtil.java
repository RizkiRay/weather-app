package tech.raynaldy.watherapp.utils;

import java.util.Locale;

import tech.raynaldy.watherapp.R;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/5/21.
 */

public class WeatherUtil {
    private final static Double CONST_K_CELC = 273.15;
    private final static Double CONST_K_FAH = 459.67;
    private final static Double CONST_K_FAH_DIV = 1.8;

    /*
    2xx - thunderstorm
    3xx - drizzle
    5xx - rain
    511 - sleet
    52x & 53x - shower rain
    6xx - snow
    7xx - fog
    800 - clear
    8xx - cloudy
     */

    public static Integer KelvinToCelcius(Double k){
        Double c = k - CONST_K_CELC;
        return c.intValue();
    }

    public static Integer KelvinToFahrenheit(Double k){
        Double f = (k * CONST_K_FAH_DIV) - CONST_K_FAH;
        return f.intValue();
    }

    public static String getCountryFromID(String id){
        try {
            Locale loc = new Locale("", id);
            return loc.getDisplayCountry();
        } catch (Error e) {
            return "n/a";
        }
    }


    public static int getWeatherImage(int code){
        int id = code / 100;

        if (code == 800) return R.drawable.sun;
        if (code == 511) return R.drawable.sleet;

        switch (id) {
            case 2:
                return R.drawable.storm;
            case 3:
                return R.drawable.rain;
            case 5:
                return R.drawable.rain;
            case 6:
                return R.drawable.snow;
            case 7:
                return R.drawable.fog_day;
            case 8:
                return R.drawable.cloudy2;
            default:
                return R.drawable.cloudy;
        }
    }
}
