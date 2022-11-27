package com.example.drunkendiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView dateView, sqlNum;
    Button btnDetail;
    String sql = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Drunken Diary");

        calendarView = (CalendarView) findViewById(R.id.cv1);
        dateView = (TextView) findViewById(R.id.dateView);
        sqlNum = (TextView) findViewById(R.id.sqlNum);
        btnDetail = (Button) findViewById(R.id.btnDetail);

        Date toDay = new Date(calendarView.getDate());
        DateManager dateManager = new DateManager();
        final String[] date = {dateManager.makeStringDate(toDay)};
        dateView.setText(date[0]);

        DrunkenDbHelper helper = DrunkenDbHelper.getInstance(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        updateCount(db,date[0]);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                date[0] = year + "/" + (month+1) + "/" + dayOfMonth;
                dateView.setText(date[0]);
                updateCount(db,date[0]);
            }
        });

        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailAccess.class);
                intent.putExtra("Date", date[0]);
                startActivity(intent);
            }
        });
    }

    void updateCount(SQLiteDatabase db, String date){
        String count = "";
        try {
            sql = "select count(*) from "+TableInfo.TABLE_NAME+" where "+TableInfo.COLUMN_NAME_DATE+" = ?";
            Cursor resultSet = db.rawQuery(sql, new String[]{date});
            while (resultSet.moveToNext()) {
                count = resultSet.getString(0);
            }
            resultSet.close();
        } catch (Exception e) {e.printStackTrace();}
        sqlNum.setText(count);
    }
}