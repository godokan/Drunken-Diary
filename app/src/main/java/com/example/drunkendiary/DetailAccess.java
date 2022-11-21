package com.example.drunkendiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

public class DetailAccess extends Activity {
    RadioGroup drinkType;
    EditText editName, editMemo;
    View dialogView;
    String sql = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailaccess);

        ListView listView = (ListView) findViewById(R.id.listView);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        editName = (EditText) findViewById(R.id.editName);
        editMemo = (EditText) findViewById(R.id.editMemo);

        Intent intent = getIntent();
        final String today = intent.getStringExtra("Date");

        DrunkenDbHelper helper = new DrunkenDbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            sql = "select "+TableInfo.COLUMN_NAME_DTYPE+","+TableInfo.COLUMN_NAME_DNAME+","+TableInfo.COLUMN_NAME_DATE+","+TableInfo.COLUMN_NAME_TIME+" from " + TableInfo.TABLE_NAME + " where " + TableInfo.COLUMN_NAME_DATE + " = ?";
            Cursor resultSet = db.rawQuery(sql, new String[]{today});
            String[] res = new String[resultSet.getCount()];
            while (resultSet.moveToNext()) {
                String type = resultSet.getString(0);
                String name = resultSet.getString(1);
                String date = resultSet.getString(2);
                String time = resultSet.getString(3);

                res[resultSet.getPosition()] = type + " " + name + " " + date + " - " + time;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res);
            listView.setAdapter(adapter);
            resultSet.close();
        } catch (Exception e) {e.printStackTrace();}

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (View) View.inflate(DetailAccess.this, R.layout.dialoginit,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(DetailAccess.this);
                dlg.setTitle("기록 추가");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", null);
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }


}
