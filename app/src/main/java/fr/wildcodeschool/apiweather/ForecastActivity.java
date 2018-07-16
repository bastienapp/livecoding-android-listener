package fr.wildcodeschool.apiweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        Button weather = findViewById(R.id.weather);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForecastActivity.this, MainActivity.class));
            }
        });

        final TextView container = findViewById(R.id.container);

        String cityname = "Toulouse";
        ApiHelper.getForecast(this, cityname, new ApiHelper.ForecastResponse() {
            @Override
            public void onSuccess(ArrayList<String> results) {
                for (String result : results) {
                    container.append(result);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ForecastActivity.this, String.format("Une erreur est survenue : %s", error), Toast.LENGTH_LONG).show();
            }
        });
    }
}
