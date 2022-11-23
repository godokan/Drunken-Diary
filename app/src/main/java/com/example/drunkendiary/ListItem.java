package com.example.drunkendiary;

public class ListItem {
    private final String id;
    private final String dtype;
    private final String description;
    private final String date;
    private final String time;

    public String getDtype() {return dtype;}

    public String getDescription() {return description;}

    public String getDate() {return date;}

    public String getTime() {return time;}

    public String getId() {return id;}

    public ListItem(String dtype, String description, String date, String time, String no) {
        this.dtype = dtype;
        this.description = description;
        this.date = date;
        this.time = time;
        this.id = no;
    }
}
