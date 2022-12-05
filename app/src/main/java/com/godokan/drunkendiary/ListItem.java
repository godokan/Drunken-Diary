package com.godokan.drunkendiary;

public class ListItem {
    private final String id;
    private final String dtype;
    private final String name;
    private final String memo;
    private final String date;
    private final String time;

    public String getDtype() {return dtype;}

    public String getName() {return name;}

    public String getMemo() {return memo;}

    public String getDate() {return date;}

    public String getTime() {return time;}

    public String getId() {return id;}

    public ListItem(String dtype, String name, String memo, String date, String time, String no) {
        this.dtype = dtype;
        this.name = name;
        this.memo = memo;
        this.date = date;
        this.time = time;
        this.id = no;
    }
}
