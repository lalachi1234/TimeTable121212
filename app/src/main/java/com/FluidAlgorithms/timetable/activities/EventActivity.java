package com.FluidAlgorithms.timetable.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import com.FluidAlgorithms.timetable.R;
import com.FluidAlgorithms.timetable.utils.SqlDb;


public class EventActivity extends AppCompatActivity {

    private SqlDb dbHandler;
    private EditText editText;
    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        editText = findViewById(R.id.editText);
        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + Integer.toString(month) + dayOfMonth;
                ReadDatabase(view);
            }
        });

        try{
            dbHandler = new SqlDb(this,"CalendarDatabase", null, 1 );
            sqLiteDatabase = dbHandler.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE EventCalendar (Date TEXT, Event TEXT)");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void InsertDatabase (View view) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", selectedDate);
        contentValues.put ("Event", editText.getText().toString());
        sqLiteDatabase.insert("EventCalendar", null, contentValues);




    }

public void ReadDatabase (View view) {
        String query = "Select Event from EventCalendar where Data =" + selectedDate;
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            editText.setText(cursor.getString(0));

        } catch (Exception e) {
            e.printStackTrace();
            editText.setText("");
        }


}



    }





