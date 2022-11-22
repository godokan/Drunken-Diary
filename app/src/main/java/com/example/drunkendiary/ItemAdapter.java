package com.example.drunkendiary;

import static android.view.View.*;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    ArrayList<ListItem> items = new ArrayList<>();
    Context context;

    public ItemAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    public void addItem(ListItem item){
        items.add(item);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListItem listItem = items.get(i);

        View itemView = inflate(context, R.layout.list_item, null);
        TextView dType = itemView.findViewById(R.id.dtype);
        TextView description = itemView.findViewById(R.id.description);
        TextView date = itemView.findViewById(R.id.date);
        TextView time = itemView.findViewById(R.id.time);

        dType.setText(listItem.getDtype());
        description.setText(listItem.getDescription());
        date.setText(listItem.getDate());
        time.setText(listItem.getTime());

        return itemView;
    }
}
