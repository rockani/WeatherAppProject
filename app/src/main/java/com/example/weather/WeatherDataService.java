package com.example.weather;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {
    Context context;
    String cityID;
    String current;
    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    public static final String QUERY_FOR_CITY_FORECAST_BY_ID = "https://www.metaweather.com/api/location/";
    public WeatherDataService(Context context)
    {
        this.context = context;
    }
    public interface VolleyResponseListner
    {
        void onError(String message);
        void onResponse(String cityID );
    }
    public interface ForecastByIdResponse
    {
        void onerror(String message);
        void onResponse(ArrayList<WeatherReportModel> report ,String CurrentWeather);
    }
    public void getCityID(String cityName, VolleyResponseListner volleyResponseListner) {
        String url = QUERY_FOR_CITY_ID + cityName;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                cityID = "";

                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(context, "CityID =" + cityID, Toast.LENGTH_SHORT).show();
                volleyResponseListner.onResponse(cityID);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                volleyResponseListner.onError("Something wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public void getCityForecastByID(String cityID, ForecastByIdResponse forecastByIdResponse) {
        ArrayList<WeatherReportModel> report = new ArrayList<>();
        String url = QUERY_FOR_CITY_FORECAST_BY_ID + cityID;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray Consolidated_weather = response.getJSONArray("consolidated_weather");
                    for(int i=0;i<Consolidated_weather.length();i++)
                    {
                        String weather_state_name=Consolidated_weather.getJSONObject(i).getString("weather_state_name");
                        String applicable_date = Consolidated_weather.getJSONObject(i).getString("applicable_date");
                        int max_temp =(int)Double.parseDouble( Consolidated_weather.getJSONObject(i).getString("max_temp"));
                        int min_temp =(int)Double.parseDouble( Consolidated_weather.getJSONObject(i).getString("min_temp"));
                        int the_temp =(int) Double.parseDouble( Consolidated_weather.getJSONObject(i).getString("the_temp"));
                        int wind_speed=(int)Double.parseDouble( Consolidated_weather.getJSONObject(i).getString("wind_speed"));
                        int wind_direction=(int)Double.parseDouble( Consolidated_weather.getJSONObject(i).getString("wind_direction"));
                        int humidity =(int)Double.parseDouble( Consolidated_weather.getJSONObject(i).getString("humidity"));
                        report.add(new WeatherReportModel(weather_state_name,humidity,applicable_date,max_temp,min_temp,the_temp,wind_speed,wind_direction));
                        if(i==0){
                            current = weather_state_name;
                        }

                    }
                    forecastByIdResponse.onResponse(report,current);
                } catch (JSONException e) {
                    e.printStackTrace();

                }


                //Toast.makeText(context,response.toString(),Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                forecastByIdResponse.onerror("Something wrong");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
