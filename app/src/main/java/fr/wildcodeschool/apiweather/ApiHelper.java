package fr.wildcodeschool.apiweather;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiHelper {

    private final static String API_URL = "https://api.openweathermap.org/data/2.5/";
    private final static String APIKEY = "7538d4f273fbca1779fdd5aaab2a50f4";
    private static final String TAG = "DB_WEATHER";

    public static void getWeather(Context context, String city, final WeatherResponse listener) {
        String url = String.format("%sweather?q=%s&APPID=%s", API_URL, city, APIKEY);

// Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Process the JSON
                        try {
                            // Get the JSON array
                            JSONArray weathers = response.getJSONArray("weather");

                            // Loop through the array elements
                            for (int i = 0; i < weathers.length(); i++) {
                                // Get current json object
                                JSONObject weather = weathers.getJSONObject(i);

                                // Get the current student (json object) data
                                String description = weather.getString("description");
                                String result = String.format("%s : %s\n", String.valueOf(i), description);
                                listener.onSuccess(result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(e.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getLocalizedMessage());
                        listener.onError(error.getLocalizedMessage());
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    public static void getForecast(Context context, String city, final ForecastResponse listener) {
        String url = String.format("%sforecast?q=%s&APPID=%s", API_URL, city, APIKEY);

// Initialize a new RequestQueue instance
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        // Initialize a new JsonObjectRequest instance
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Process the JSON
                        try {
                            // Get the JSON array
                            JSONArray list = response.getJSONArray("list");
                            ArrayList<String> weatherResult = new ArrayList<>();
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject element = list.getJSONObject(i);
                                JSONArray weathers = element.getJSONArray("weather");

                                // Loop through the array elements
                                for (int j = 0; j < weathers.length(); j++) {
                                    // Get current json object
                                    JSONObject weather = weathers.getJSONObject(j);

                                    // Get the current student (json object) data
                                    String description = weather.getString("description");
                                    String result = String.format("%s : %s\n", String.valueOf(i), description);
                                    weatherResult.add(result);
                                }
                            }
                            listener.onSuccess(weatherResult);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError(e.getLocalizedMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getLocalizedMessage());
                        listener.onError(error.getLocalizedMessage());
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    interface WeatherResponse {

        void onSuccess(String result);
        void onError(String error);
    }

    interface ForecastResponse {

        void onSuccess(ArrayList<String> result);
        void onError(String error);
    }
}
