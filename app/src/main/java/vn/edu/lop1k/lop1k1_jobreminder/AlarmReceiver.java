package vn.edu.lop1k.lop1k1_jobreminder;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import vn.edu.lop1k.Models.Job;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String name = intent.getStringExtra("name");
            String time = intent.getStringExtra("time");
           // String phut = intent.getStringExtra("phut");
           // String trangthai = intent.getStringExtra("trangThai");
            Intent myIntent = new Intent(context, AlarmScreenActivity.class);
            myIntent.putExtra("name", name);
            myIntent.putExtra("time", time);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
           // myIntent.putExtra("phut", phut);
           //myIntent.putExtra("trangThai", trangthai);
            // Theo như doc: https://developer.android.com/about/versions/oreo/background-location-limits
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(myIntent);
            } else {
                context.startService(myIntent);
            }*/
        } else {
            Toast.makeText(context, "Intent receiver null", Toast.LENGTH_SHORT).show();
        }
    }


}
