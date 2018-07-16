package fr.wildcodeschool.apiweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forecast = findViewById(R.id.forecast);
        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForecastActivity.class));
            }
        });

        final TextView container = findViewById(R.id.container);
        String cityname = "Toulouse";

        ApiHelper.WeatherResponse listener = new ApiHelper.WeatherResponse() {
            @Override
            public void onSuccess(String result) {
                container.append(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, String.format("Une erreur est survenue : %s", error), Toast.LENGTH_LONG).show();
            }
        };

        ApiHelper.getWeather(this, cityname, listener);
    }
}
