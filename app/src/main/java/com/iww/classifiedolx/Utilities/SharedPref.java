package com.iww.classifiedolx.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    Context activity;

    public SharedPref(Context activity) {
        this.activity = activity;
    }



    public void setUserId(String userId) {
        SharedPreferences prefs = activity.getSharedPreferences("userId", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("userId", userId);
        editor.commit();
    }

    public String getUserId() {
        SharedPreferences prefs = activity.getSharedPreferences("userId", activity.MODE_PRIVATE);
        String userId= prefs.getString("userId", "");
        return userId;
    }


    public void setEmail(String email) {
        SharedPreferences prefs = activity.getSharedPreferences("email", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail() {
        SharedPreferences prefs = activity.getSharedPreferences("email", activity.MODE_PRIVATE);
        String email= prefs.getString("email", "");
        return email;
    }
    public void setName(String name) {
        SharedPreferences prefs = activity.getSharedPreferences("name", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.commit();
    }

    public String getName() {
        SharedPreferences prefs = activity.getSharedPreferences("name", activity.MODE_PRIVATE);
        String name= prefs.getString("name", "");
        return name;
    }

    public void setPassword(String password) {
        SharedPreferences prefs = activity.getSharedPreferences("password", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", password);
        editor.commit();
    }

    public String getPassword() {
        SharedPreferences prefs = activity.getSharedPreferences("password", activity.MODE_PRIVATE);
        String password= prefs.getString("password", "");
        return password;
    }

    public void setPhone(String phone) {
        SharedPreferences prefs = activity.getSharedPreferences("phone", activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phone", phone);
        editor.commit();
    }

    public String getPhone() {
        SharedPreferences prefs = activity.getSharedPreferences("phone", activity.MODE_PRIVATE);
        String phone= prefs.getString("phone", "");
        return phone;
    }
}
