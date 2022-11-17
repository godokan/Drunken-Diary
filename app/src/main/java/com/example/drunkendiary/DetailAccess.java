package com.example.drunkendiary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailAccess extends Activity {
    RadioGroup drinkType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailaccess);
        DateManager dateManager = new DateManager();

        ListView listView = (ListView) findViewById(R.id.listView);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        Intent intent = getIntent();
        final String today = intent.getStringExtra("Date");

        DrunkenDbHelper helper = new DrunkenDbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            String sql = "select dtype,dname,date,time from " + TableInfo.TABLE_NAME + " where " + TableInfo.COLUMN_NAME_DATE + " = ?";
            Cursor resultSet = db.rawQuery(sql, new String[]{today});
            String[] res = new String[resultSet.getCount()];
            while (resultSet.moveToNext()) {
                String type = resultSet.getString(0);
                String name = resultSet.getString(1);
                String date = resultSet.getString(2);
                String time = resultSet.getString(3);

                res[resultSet.getPosition()] = type + " " + name + " " + date + " - " + time;
            }
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, res);
            listView.setAdapter(adapter);
        } catch (Exception e) {System.out.println(e);}

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}
