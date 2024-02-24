package uz.wiut.mad.touruzbekistan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CityDetailsActivity extends AppCompatActivity {
    int cityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_details);

        // Get a reference to the Intent that launched this activity.
        Intent i = getIntent();

        // Get an extra with a name "cityId" from the Intent if there is any. Otherwise,
        // return `R.id.khiva`.
        cityId = i.getIntExtra("cityId", R.id.khiva);

        TextView tvTitle = findViewById(R.id.title);
        TextView tvDesc = findViewById(R.id.description);
        ImageView ivBanner = findViewById(R.id.banner);

        // Check what was chosen. We will show just a Toast to the user. We will learn on how to
        // find elements in our layout and populate them next week.
        if (cityId == R.id.bukhara) {
            // Bukhara was clicked.
            tvTitle.setText(R.string.bukhara);
            tvDesc.setText(R.string.bukhara_description);
            ivBanner.setImageResource(R.drawable.bukhara);
        } else if (cityId == R.id.samarkand) {
            // Samarkand was clicked
            tvTitle.setText(R.string.samarkand);
            tvDesc.setText(R.string.samarkand_description);
            ivBanner.setImageResource(R.drawable.samarkand);
        } else {
            tvTitle.setText(R.string.khiva);
            tvDesc.setText(R.string.khiva_description);
            ivBanner.setImageResource(R.drawable.khiva);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Read notes from the DB
        NotesDbHelper dbHelper = new NotesDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "notes",
                null,
                "city_id = ?",
                new String[]{String.valueOf(cityId)},
                null,
                null,
                null
        );

        ListView lvNotes = findViewById(R.id.notes);

        NotesAdapter adapter = new NotesAdapter(this, cursor);

        lvNotes.setAdapter(adapter);
    }

    public void openNotesForm(View view) {
        Intent i = new Intent(this, NoteFormActivity.class);
        i.putExtra("cityId", cityId);
        startActivity(i);
    }

    public void deleteNote(View view) {
        Toast.makeText(this, "Delete button clicked: ", Toast.LENGTH_SHORT).show();
    }

    public void jumpToWeatherPage(View view) {
        Intent i = new Intent(this, WeatherActivity.class);

        String name = "Khiva";
        if(cityId == R.id.bukhara)
            name = "Bukhara";
        else if (cityId == R.id.samarkand)
            name = "Samarkand";

        i.putExtra("cityName", name);
        startActivity(i);
    }
}