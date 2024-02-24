package uz.wiut.mad.touruzbekistan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NoteFormActivity extends AppCompatActivity {
    int cityId;
    long _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);
        Intent i= getIntent();
        cityId = i.getIntExtra("cityId", R.id.khiva);

        // Populate the fields
        //  1. Get clicked item's primary key value
        _id = i.getLongExtra("noteId", 0);

        //  2. Get the value from the DB
        if (_id > 0) {
            NotesDbHelper dbHelper = new NotesDbHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.query("notes", null, "_id = ?", new String[] {String.valueOf(_id)}, null, null, null);
            if (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                int city_id = cursor.getInt(cursor.getColumnIndex("city_id"));

                //  3. Prefill the EditTexts with these values
                EditText etTitle = findViewById(R.id.title);
                EditText etBody = findViewById(R.id.content);

                etTitle.setText(title);
                etBody.setText(content);
                cityId = city_id;

                // Update the button's text for better UX
                Button btn = findViewById(R.id.button);
                btn.setText("Update");
            }
        }
    }

    public void saveNote(View view) {
        EditText etTitle = findViewById(R.id.title);
        EditText etContent = findViewById(R.id.content);

        NotesDbHelper dbHelper = new NotesDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", etTitle.getText().toString());
        values.put("content", etContent.getText().toString());
        values.put("city_id", cityId);

        long id;
        if (_id > 0) {
            // This is an update operation
            id = db.update("notes", values, "_id = ?", new String[]{String.valueOf(_id)});
        } else {
            // This is an creation operation
            id = db.insert("notes", null, values);
        }
        db.close();

        if (id > 0) {
            finish();
        } else {
            Toast.makeText(this, "There was an error inserting your note!", Toast.LENGTH_LONG).show();
        }
    }
}