package uz.wiut.mad.touruzbekistan;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NotesAdapter extends BaseAdapter {
    private final Cursor cursor;
    private final Context context;
    private final SQLiteDatabase db;

    NotesAdapter(Context context, Cursor cursor) {
        this.cursor = cursor;
        this.context = context;

        NotesDbHelper dbHelper = new NotesDbHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public Cursor getItem(int position) {
        cursor.moveToPosition(position);
        return cursor;
    }

    @Override
    public long getItemId(int position) {
        Cursor cursor = getItem(position);
        return cursor.getLong(cursor.getColumnIndex("_id"));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.notes_item, null);
        }

        final Cursor cursor = getItem(position);

        TextView tvTitle = convertView.findViewById(R.id.titleField);
        tvTitle.setText(cursor.getString(cursor.getColumnIndex("title")));

        TextView tvContent = convertView.findViewById(R.id.contentField);
        tvContent.setText(cursor.getString(cursor.getColumnIndex("content")));

        Button btDelete = convertView.findViewById(R.id.delete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("notes", "_id = ?", new String[]{String.valueOf(getItemId(position))});
            }
        });

        Button btEdit = convertView.findViewById(R.id.edit);
        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NoteFormActivity.class);
                i.putExtra("noteId", getItemId(position));
                context.startActivity(i);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item clicked: "+position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
