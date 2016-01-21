package com.example.shiozaki.shooting;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class Shooting extends ActionBarActivity {
    public float disp_w,disp_h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        Window window = getWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        WindowManager manager = window.getWindowManager();
        Display disp = manager.getDefaultDisplay();
        disp_w = disp.getWidth();
        disp_h = disp.getHeight();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(new MainLoop(this));
        //setContentView(R.layout.activity_shooting);
    }


/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shooting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
