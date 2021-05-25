package com.example.weather;

public class WeatherReportModel {
//    "id":6570875651358720,
//            "weather_state_name":"Heavy Cloud",
//            "weather_state_abbr":"hc",
//            "wind_direction_compass":"SSE",
//            "created":"2021-04-14T09:31:06.946334Z",
//            "applicable_date":"2021-04-18",
//            "min_temp":4.095000000000001,
//            "max_temp":14.754999999999999,
//            "the_temp":13.25,
//            "wind_speed":3.1515112764946056,
//            "wind_direction":151.36133837505733,
//            "air_pressure":1025.0,
//            "humidity":47,
//            "visibility":13.84446084864392,
//            "predictability":71
    private int humidity;
    private String weather_state_name;
    private String applicable_date;
    private float max_temp,min_temp,the_temp,wind_speed,wind_direction;

    public WeatherReportModel( String weather_state_name, int humidity,String applicable_date, float max_temp, float min_temp, float the_temp, float wind_speed, float wind_direction) {
        this.weather_state_name = weather_state_name;
        this.humidity = humidity;



        this.applicable_date = applicable_date;
        this.max_temp = max_temp;
        this.min_temp = min_temp;
        this.the_temp = the_temp;
        this.wind_speed = wind_speed;
        this.wind_direction = wind_direction;

    }

    @Override
    public String toString() {
        return "WeatherReportModel{" +

                ", humidity=" + humidity +
                ", weather_state_name='" + weather_state_name + '\'' +
                ", applicable_date='" + applicable_date + '\'' +
                ", max_temp=" + max_temp +
                ", min_temp=" + min_temp +
                ", the_temp=" + the_temp +
                ", wind_speed=" + wind_speed +
                ", wind_direction=" + wind_direction +
                '}';
    }



    public int getHumidity() {
        return humidity;
    }


    public String getWeather_state_name() {
        return weather_state_name;
    }





    public String getApplicable_date() {
        return applicable_date;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public float getWind_speed() {
        return wind_speed;
    }

    public float getWind_direction() {
        return wind_direction;
    }




    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }


    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }



    public void setApplicable_date(String applicable_date) {
        this.applicable_date = applicable_date;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public void setThe_temp(float the_temp) {
        this.the_temp = the_temp;
    }

    public void setWind_speed(float wind_speed) {
        this.wind_speed = wind_speed;
    }

    public void setWind_direction(float wind_direction) {
        this.wind_direction = wind_direction;
    }


}
