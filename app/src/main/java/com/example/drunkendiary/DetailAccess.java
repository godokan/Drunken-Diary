package com.example.drunkendiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DetailAccess extends Activity {

    EditText editName, editMemo;
    View dialogView;
    String sql = "";
    Cursor resultSet;
    ArrayAdapter<String> adapter;
    ListView listView;
    DateManager dateManager = new DateManager();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailaccess);

        listView = (ListView) findViewById(R.id.listView);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        Intent intent = getIntent();
        final String today = intent.getStringExtra("Date");

        DrunkenDbHelper helper = new DrunkenDbHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            sql = "select "+TableInfo.COLUMN_NAME_DTYPE+","+TableInfo.COLUMN_NAME_DNAME+","+TableInfo.COLUMN_NAME_DATE+","+TableInfo.COLUMN_NAME_TIME+" from " + TableInfo.TABLE_NAME + " where " + TableInfo.COLUMN_NAME_DATE + " = ?";
            resultSet = db.rawQuery(sql, new String[]{today});
        } catch (Exception e) {e.printStackTrace();}
        String[] res = new String[resultSet.getCount()];
        while (resultSet.moveToNext()) {
            String type = resultSet.getString(0);
            String name = resultSet.getString(1);
            String date = resultSet.getString(2);
            String time = resultSet.getString(3);

            res[resultSet.getPosition()] = type + " " + name + " " + date + " - " + time;
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, res);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (View) View.inflate(DetailAccess.this, R.layout.dialoginit,null);
                RadioGroup drinkType = (RadioGroup) dialogView.findViewById(R.id.drinkType);
                editName = dialogView.findViewById(R.id.editName);
                editMemo = dialogView.findViewById(R.id.editMemo);
                AlertDialog.Builder dlg = new AlertDialog.Builder(DetailAccess.this);
                dlg.setTitle("기록 추가");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RadioButton checked = (RadioButton) dialogView.findViewById(drinkType.getCheckedRadioButtonId());
                        try {
                            sql = "insert into "+ TableInfo.TABLE_NAME+
                                    "("+TableInfo.COLUMN_NAME_DTYPE+","+ TableInfo.COLUMN_NAME_DNAME+","+TableInfo.COLUMN_NAME_MEMO+","+TableInfo.COLUMN_NAME_DATE+","+TableInfo.COLUMN_NAME_TIME+
                                    ") values  (?,?,?,?,?)";
                            db.execSQL(sql, new String[]{checked.getText().toString(), editName.getText().toString(), editMemo.getText().toString(), dateManager.getNowDate(), dateManager.getNowTime()});
                            System.out.println("입력 성공");
                        } catch (Exception e) {e.printStackTrace();} finally {
                            Toast.makeText(getApplicationContext(),"기록 추가 완료", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }


}
