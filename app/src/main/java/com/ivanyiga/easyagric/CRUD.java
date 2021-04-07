package com.ivanyiga.easyagric;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

public class CRUD extends Application {
    private static CRUD mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static synchronized CRUD getInstance() {
        return mInstance;
    }

    public void showToast(String msg){
        try {
            Toast.makeText(this, msg,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveNewSale(String data){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("sales", data);
        editor.apply();
    }
    public void saveNewExpense(String data){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("expenses", data);
        editor.apply();
    }

    public String getSales(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
        return settings.getString("sales",null);
    }

    public String getExpenses(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("data", 0);
        return settings.getString("expenses",null);
    }
}
