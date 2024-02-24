package uz.wiut.mad.touruzbekistan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpToCityDetailsPage(View view) {
        // Create an instance of Intent class in order to open another activity
        Intent myIntent = new Intent(this, CityDetailsActivity.class);

        // Get an `id` of clicked RelativeLayout. In this case, the `view` argument above
        // is a reference to the clicked view (RelativeLayout in our case).
        int clickedCityId = view.getId();

        // Send clicked RelativeLayout's id attribute with the Intent.
        myIntent.putExtra("cityId", clickedCityId);

        // Ask OS to launch/execute our intent
        startActivity(myIntent);
    }
}