package com.example.metroapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetroStations implements Parcelable {

@SerializedName("rows")
@Expose
private List<Row> rows = null;


public MetroStations() {
}


public MetroStations(List<Row> rows) {
super();
this.rows = rows;
}

public List<Row> getRows() {
return rows;
}

public void setRows(List<Row> rows) {
this.rows = rows;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.rows);
    }

    protected MetroStations(Parcel in) {
        this.rows = new ArrayList<Row>();
        in.readList(this.rows, Row.class.getClassLoader());
    }

    public static final Parcelable.Creator<MetroStations> CREATOR = new Parcelable.Creator<MetroStations>() {
        @Override
        public MetroStations createFromParcel(Parcel source) {
            return new MetroStations(source);
        }

        @Override
        public MetroStations[] newArray(int size) {
            return new MetroStations[size];
        }
    };
}