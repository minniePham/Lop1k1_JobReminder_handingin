package vn.edu.lop1k.lop1k1_jobreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import vn.edu.lop1k.lop1k1_jobreminder.ui.gallery.GalleryFragment;



public class AlarmScreenActivity extends Activity {
    private MediaPlayer mMedia;
    private PowerManager.WakeLock mWakelock;
    private static int WAKELOCK_TIME = 60000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);
        String name=this.getIntent().getStringExtra("name");
        String time=this.getIntent().getStringExtra("time");
        String [] spittime=time.split(" ");
        String [] gio=spittime[1].split(":");
        //String phut=this.getIntent().getStringExtra("phut");
       //String trangthai=this.getIntent().getStringExtra("trangthai");
        TextView txtTieuDe=findViewById(R.id.alarm_screen_name);
        txtTieuDe.setText(name);
        TextView txtTime=findViewById(R.id.alarm_screen_time);
        txtTime.setText(String.format("%02d : %02d",Integer.parseInt(gio[1]),Integer.parseInt( gio[0])));
        Button dismissBtn = (Button)this.findViewById(R.id.alarm_screen_dismiss);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlarmScreenActivity.this.mMedia.stop();
                AlarmScreenActivity.this.finish();


            }

        });
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            this.mMedia = MediaPlayer.create(this, R.raw.nhacchuong);
            this.mMedia.setLooping(true);
            this.mMedia.start();
        }
        /*Runnable releaseWakelock = new Runnable() {
            public void run() {
                AlarmScreenActivity.this.getWindow().clearFlags(2097152);
                AlarmScreenActivity.this.getWindow().clearFlags(128);
                AlarmScreenActivity.this.getWindow().clearFlags(524288);
                AlarmScreenActivity.this.getWindow().clearFlags(4194304);
                if (AlarmScreenActivity.this.mWakelock != null && AlarmScreenActivity.this.mWakelock.isHeld()) {
                    AlarmScreenActivity.this.mWakelock.release();
                }

            }
        };
        (new Handler()).postDelayed(releaseWakelock, (long)WAKELOCK_TIME);*/

    }
}
