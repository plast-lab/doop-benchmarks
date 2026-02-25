package com.example.george.testandroidapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private String field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test_getNameNative();
    }

    private void test_getNameNative() {
        try {
            Class<?> c = Class.forName("java.lang.Class");
            java.lang.reflect.Method m = c.getDeclaredMethod("getNameNative", Void.class);
            Object[] args = new Object[] { };
            String s = (String)m.invoke(c, args);
            field = s;
        } catch (Exception ex) {
            Log.i(TAG, "Exception: " + ex.getMessage());
        }
    }
}
