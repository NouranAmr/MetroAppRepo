package com.example.metroapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Row implements Parcelable {

@SerializedName("title")
@Expose
private String title;
@SerializedName("destination_Long_Lat")
@Expose
private List<String> destinationLongLat = null;


public Row() {
}


public Row(String title, List<String> destinationLongLat) {
super();
this.title = title;
this.destinationLongLat = destinationLongLat;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public List<String> getDestinationLongLat() {
return destinationLongLat;
}

public void setDestinationLongLat(List<String> destinationLongLat) {
this.destinationLongLat = destinationLongLat;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeStringList(this.destinationLongLat);
    }

    protected Row(Parcel in) {
        this.title = in.readString();
        this.destinationLongLat = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Row> CREATOR = new Parcelable.Creator<Row>() {
        @Override
        public Row createFromParcel(Parcel source) {
            return new Row(source);
        }

        @Override
        public Row[] newArray(int size) {
            return new Row[size];
        }
    };
}