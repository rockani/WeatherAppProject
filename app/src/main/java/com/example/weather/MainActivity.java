package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button get_CityID,get_WeatherByCityID,get_WeaatherByCityName;
    EditText et_dataInput;
    ListView lv_weatherview;
    ImageView weather_img;
    public String currentWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_CityID = findViewById(R.id.btn_getCityID);
        get_WeatherByCityID = findViewById(R.id.get_WeatherByCityID);
        get_WeaatherByCityName = findViewById(R.id.btn_getWeatherByCityName);
        et_dataInput = findViewById(R.id.et_dataInput);
        lv_weatherview = (ListView) findViewById(R.id.List);
        weather_img = (ImageView) findViewById(R.id.weather_img);
        Date myObj = new Date();
        SimpleDateFormat myDateObj = new SimpleDateFormat("yyyy-MMM-dd");
        String formattedDate = myDateObj.format(myObj);
        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
        get_CityID.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

                 weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListner() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String cityID) {
                        Toast.makeText(MainActivity.this,"Returned an ID of "+cityID,Toast.LENGTH_SHORT).show();
                    }
                });


             }
         });
        get_WeatherByCityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityForecastByID(et_dataInput.getText().toString(),new WeatherDataService.ForecastByIdResponse() {
                    @Override
                    public void onerror(String message) {
                        Toast.makeText(MainActivity.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(ArrayList<WeatherReportModel> report,String Current_Weather) {
                        final ArrayAdapter<WeatherReportModel> arrayAdapter = new ArrayAdapter<WeatherReportModel>(MainActivity.this, android.R.layout.simple_list_item_1, report);
                        lv_weatherview.setAdapter(arrayAdapter);
                        if(Current_Weather=="Light Cloud"){
                            weather_img.setImageResource(R.drawable.cloudy1);
                        }

                        //Toast.makeText(MainActivity.this,"Returned an ID of "+cityID,Toast.LENGTH_SHORT).show();
                    }
                });
               // Toast.makeText(MainActivity.this,"You Clicked me 2.",Toast.LENGTH_SHORT).show();
            }
        });
        get_WeaatherByCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"You typed "+ et_dataInput.getText(),Toast.LENGTH_SHORT).show();
                weatherDataService.getCityID(et_dataInput.getText().toString(), new WeatherDataService.VolleyResponseListner() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this,"Something Wrong here",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String cityID) {

                        //Toast.makeText(MainActivity.this,"Returned an ID of "+cityID,Toast.LENGTH_SHORT).show();
                        weatherDataService.getCityForecastByID(cityID,new WeatherDataService.ForecastByIdResponse() {
                            @Override
                            public void onerror(String message) {
                                Toast.makeText(MainActivity.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(ArrayList<WeatherReportModel> report,String Current_Weather) {
                                final ArrayAdapter<WeatherReportModel> arrayAdapter = new ArrayAdapter<WeatherReportModel>(MainActivity.this, android.R.layout.simple_list_item_1, report);
                                lv_weatherview.setAdapter(arrayAdapter);
                                if(Current_Weather.equals("Light Cloud") ||Current_Weather.equals("Heavy Cloud")){
                                    weather_img.setImageResource(R.drawable.cloudy1);
                                }
                                else if(Current_Weather.equals("Heavy Rain")){
                                    weather_img.setImageResource(R.drawable.heavyrain);
                                }
                                else if(Current_Weather.equals("Hail") || Current_Weather.equals("Sleet")){
                                    weather_img.setImageResource(R.drawable.hailandsleet);
                                }
                                else if(Current_Weather.equals("Light Rain")||Current_Weather.equals("Showers")){
                                    weather_img.setImageResource(R.drawable.rain);
                                }
                                else if(Current_Weather.equals("Clear")){
                                    weather_img.setImageResource(R.drawable.clear2);
                                }
                                else if(Current_Weather.equals("Thunderstorm")){
                                    weather_img.setImageResource(R.drawable.thunderstorm);
                                }

                                //Toast.makeText(MainActivity.this,"Returned an ID of "+cityID,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });


            }
        });
    }
}

//    // Create a List from String Array elements
//    final List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));
//
//    // Create an ArrayAdapter from List
//    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
//            (this, android.R.layout.simple_list_item_1, fruits_list);
//
//// DataBind ListView with items from ArrayAdapter
//        lv.setAdapter(arrayAdapter);
