package uz.wiut.mad.touruzbekistan;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherTask extends AsyncTask<String, Void, Double> {
    private static final String API_KEY = "06c7d6512a914b3029937dd444283ff0";
    private final TextView tv;
    HttpURLConnection urlConnection;

    WeatherTask(TextView tv) {
        this.tv = tv;
    }

    @Override
    protected Double doInBackground(String... args) {

        String cityName = args[0];

        Double weather = 0.0;

        try {
            String urlString = String.format(
                    "http://api.openweathermap.org/data/2.5/weather?q=%s,uz&units=%s&appid=%s", cityName, "metric", API_KEY
            );

            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            JSONObject topLevel = new JSONObject(builder.toString());
            JSONObject main = topLevel.getJSONObject("main");

            weather = main.getDouble("temp");

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return weather;
    }

    @Override
    protected void onPostExecute(Double weather) {
        super.onPostExecute(weather);
        tv.setText(String.valueOf(weather));
    }
}
