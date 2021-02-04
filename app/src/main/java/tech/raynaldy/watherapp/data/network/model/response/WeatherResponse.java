package tech.raynaldy.watherapp.data.network.model.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse{

	@SerializedName("visibility")
	private int visibility;

	@SerializedName("timezone")
	private int timezone;

	@SerializedName("main")
	private Main main;

	@SerializedName("clouds")
	private Clouds clouds;

	@SerializedName("sys")
	private Sys sys;

	@SerializedName("dt")
	private int dt;

	@SerializedName("coord")
	private Coord coord;

	@SerializedName("weather")
	private List<WeatherItem> weather;

	@SerializedName("name")
	private String name;

	@SerializedName("cod")
	private int cod;

	@SerializedName("id")
	private int id;

	@SerializedName("base")
	private String base;

	@SerializedName("wind")
	private Wind wind;

	public int getVisibility(){
		return visibility;
	}

	public int getTimezone(){
		return timezone;
	}

	public Main getMain(){
		return main;
	}

	public Clouds getClouds(){
		return clouds;
	}

	public Sys getSys(){
		return sys;
	}

	public int getDt(){
		return dt;
	}

	public Coord getCoord(){
		return coord;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public String getName(){
		return name;
	}

	public int getCod(){
		return cod;
	}

	public int getId(){
		return id;
	}

	public String getBase(){
		return base;
	}

	public Wind getWind(){
		return wind;
	}

	public class Clouds{

		@SerializedName("all")
		private int all;

		public int getAll(){
			return all;
		}
	}
	public class Coord{

		@SerializedName("lon")
		private double lon;

		@SerializedName("lat")
		private double lat;

		public double getLon(){
			return lon;
		}

		public double getLat(){
			return lat;
		}
	}
	public class Main{

		@SerializedName("temp")
		private double temp;

		@SerializedName("temp_min")
		private double tempMin;

		@SerializedName("humidity")
		private int humidity;

		@SerializedName("pressure")
		private int pressure;

		@SerializedName("feels_like")
		private double feelsLike;

		@SerializedName("temp_max")
		private double tempMax;

		public double getTemp(){
			return temp;
		}

		public double getTempMin(){
			return tempMin;
		}

		public int getHumidity(){
			return humidity;
		}

		public int getPressure(){
			return pressure;
		}

		public double getFeelsLike(){
			return feelsLike;
		}

		public double getTempMax(){
			return tempMax;
		}
	}
	public class Sys{

		@SerializedName("country")
		private String country;

		@SerializedName("sunrise")
		private int sunrise;

		@SerializedName("sunset")
		private int sunset;

		@SerializedName("id")
		private int id;

		@SerializedName("type")
		private int type;

		public String getCountry(){
			return country;
		}

		public int getSunrise(){
			return sunrise;
		}

		public int getSunset(){
			return sunset;
		}

		public int getId(){
			return id;
		}

		public int getType(){
			return type;
		}
	}
	public class WeatherItem{

		@SerializedName("icon")
		private String icon;

		@SerializedName("description")
		private String description;

		@SerializedName("main")
		private String main;

		@SerializedName("id")
		private int id;

		public String getIcon(){
			return icon;
		}

		public String getDescription(){
			return description;
		}

		public String getMain(){
			return main;
		}

		public int getId(){
			return id;
		}
	}
	public class Wind{

		@SerializedName("deg")
		private int deg;

		@SerializedName("speed")
		private double speed;

		public int getDeg(){
			return deg;
		}

		public double getSpeed(){
			return speed;
		}
	}
}