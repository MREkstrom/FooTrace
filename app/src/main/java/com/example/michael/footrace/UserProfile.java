package com.example.michael.footrace;


/**
 * Created by Dan on 12/1/2016.
 */
public class UserProfile {
    private String _displayName;
    private String _emailAddress;
    private int _red;
    private int _green;
    private int _blue;

    public UserProfile(String name, String email) {
        _displayName = name;
        _emailAddress = email;
        _red = 0;
        _green = 0;
        _blue = 0;
    }
}
