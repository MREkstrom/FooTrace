package com.example.michael.footrace;

import android.graphics.Path;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dan on 11/29/2016.
 */
// simple class that just has one member property as an example
public class Parcelable_Path implements Parcelable {
    private Path _path = new Path();
    private String _pathName;

    public Parcelable_Path(Path path, String name){
        _path = path;
        _pathName = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(_pathName);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Parcelable_Path> CREATOR = new Parcelable.Creator<Parcelable_Path>() {
        public Parcelable_Path createFromParcel(Parcel in) {
            return new Parcelable_Path(in);
        }

        public Parcelable_Path[] newArray(int size) {
            return new Parcelable_Path[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Parcelable_Path(Parcel in) {
        _pathName = in.readString();
    }

    public Path getPath(){
        return _path;
    }

    public String getPathName(){
        return _pathName;
    }
}
