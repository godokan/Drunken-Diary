package com.example.drunkendiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class DetailAccess extends Activity {

    EditText editName, editMemo;
    View dialogView;
    String sql = "";
    Cursor resultSet;
    ItemAdapter adapter;
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

        updateList(db, today);
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
                            Toast.makeText(getApplicationContext(),"기록 추가 완료", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {e.printStackTrace(); Toast.makeText(getApplicationContext(),"기록 추가 실패", Toast.LENGTH_LONG).show();}
                        finally {updateList(db, today);}
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }

    ArrayList<String> updateList(SQLiteDatabase db, String today) {
        final ArrayList<String> list = new ArrayList<>();
        try {
            String sql = "select "+TableInfo.COLUMN_NAME_DTYPE+","+TableInfo.COLUMN_NAME_DNAME+","+TableInfo.COLUMN_NAME_MEMO+","+TableInfo.COLUMN_NAME_DATE+","+TableInfo.COLUMN_NAME_TIME+","+TableInfo.COLUMN_NAME_ID+" from " + TableInfo.TABLE_NAME + " where " + TableInfo.COLUMN_NAME_DATE + " = ?";
            Cursor resultSet = db.rawQuery(sql, new String[]{today});
            while (resultSet.moveToNext()) {
                String id = resultSet.getString(0);
                String type = resultSet.getString(1);
                String name = resultSet.getString(2);
                String memo = resultSet.getString(3);
                String date = resultSet.getString(4);
                String time = resultSet.getString(5);
                list.add(id + "☞" + type + "☞"+ name + "☞" + memo + "☞" + date + "☞"+ time);
            }
            resultSet.close();
        } catch (Exception e) {e.printStackTrace();}

        adapter = new ItemAdapter(DetailAccess.this);
        String[][] items = new String[list.size()][];
        for (int i = 0; i < list.size(); i++){
            items[i] = list.get(i).split("☞");
            adapter.addItem(new ListItem(items[i][0],items[i][1]+" / "+items[i][2],items[i][3],items[i][4]));
        }
        listView.setAdapter(adapter);
        return list;
    }
}
