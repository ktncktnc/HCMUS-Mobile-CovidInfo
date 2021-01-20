package com.example.covidnews.Login;

public class User{
    private String _phoneNum;
    private String _fullName;
    private String _password;

    public User(String _phoneNum, String _fullName, String _password){
        this._phoneNum = _phoneNum;
        this._fullName = _fullName;
        this._password = _password;
    }

    public String get_phoneNum(){
        return _phoneNum;
    }

    public String get_fullName(){
        return _fullName;
    }

    public String get_password(){
        return _password;
    }

    public void set_phoneNum(String _phoneNum){
        this._phoneNum = _phoneNum;
    }

    public void set_fullName(String _fullName){
        this._fullName = _fullName;
    }

    public void set_password(String _password){
        this._password = _password;
    }
}
