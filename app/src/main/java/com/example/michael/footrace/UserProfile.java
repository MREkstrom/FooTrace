package com.example.michael.footrace;

/*Contains user profile information (Statistics, settings, etc)*/
public class UserProfile {
    private String _displayName;
    private String _emailAddress;
    private int _red;
    private int _green;
    private int _blue;
    private float _avgScore;
    private float _avgSpeed;
    private float _avgAcc;

    public UserProfile(String name, String email) {
        _displayName = name;
        _emailAddress = email;
        _red = 0;
        _green = 0;
        _blue = 0;
        _avgScore = 0;
        _avgSpeed = 0;
        _avgAcc = 0;
    }

    /*Updates profile when edited by user*/
    public void updateProfile(String name, String email, int red, int green, int blue){
        _displayName = name;
        _emailAddress = email;
        _red = red;
        _green = green;
        _blue = blue;
    }

    /*Updates statistics after a game ends*/
    public void updateStatistics(){
        //TODO: record statistics on game result
    }

    public String get_displayName() {
        return _displayName;
    }

    public String get_emailAddress() {
        return _emailAddress;
    }

    public int get_red() {
        return _red;
    }

    public int get_blue() {
        return _blue;
    }

    public int get_green() {
        return _green;
    }


}

