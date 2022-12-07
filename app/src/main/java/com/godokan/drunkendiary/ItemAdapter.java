package com.godokan.drunkendiary;

import static android.view.View.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListItem listItem = items.get(i);

        View itemView = inflate(context, R.layout.list_item, null);
        TextView dType = itemView.findViewById(R.id.dtype);
        TextView description = itemView.findViewById(R.id.description);
        TextView date = itemView.findViewById(R.id.date);
        ImageView icon = itemView.findViewById(R.id.icon);

        switch (listItem.getDtype()) {
            case "소주":
                icon.setImageResource(R.drawable.drunken_soju);
                break;
            case "맥주":
                icon.setImageResource(R.drawable.drunken_beer);
                break;
            case "양주":
                icon.setImageResource(R.drawable.drunken_west);
                break;
        }

        dType.setText(listItem.getDtype());
        description.setText(listItem.getName()+" / "+listItem.getMemo());
        date.setText(listItem.getDate());

        return itemView;
    }
}
