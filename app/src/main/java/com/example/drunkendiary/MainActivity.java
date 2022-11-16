package com.example.drunkendiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    TextView dateView;
    Button btnDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarView = (CalendarView) findViewById(R.id.cv1);
        dateView = (TextView) findViewById(R.id.dateView);
        btnDetail = (Button) findViewById(R.id.btnDetail);

        Date toDay = new Date(calendarView.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d", Locale.KOREA);
        final String[] date = {simpleDateFormat.format(toDay)};
        dateView.setText(date[0]);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                date[0] = year + "/" + (month+1) + "/" + dayOfMonth;
                dateView.setText(date[0]);
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
}