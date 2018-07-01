package com.gbolissimo.androidnotes;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


}
