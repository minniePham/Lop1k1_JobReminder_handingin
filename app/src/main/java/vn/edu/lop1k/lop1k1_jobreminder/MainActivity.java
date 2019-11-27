package vn.edu.lop1k.lop1k1_jobreminder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import vn.edu.lop1k.Models.Job;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static MyDatabase myDatabase;

    private AppBarConfiguration mAppBarConfiguration;
    public static final String DATABASE_NAME="JobReminder_1k.db";
    public static  final String DB_PATH_SUFFIX="/databases/";
    public  static ListView lvWordList;
    public static SQLiteDatabase database=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copySQLITE();
        myDatabase=new MyDatabase(MainActivity.this,DATABASE_NAME,null,1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
                //Intent intent= new Intent(MainActivity.this, AddActivity.class);
                //startActivityForResult(intent,111);
               // Toast.makeText("hihi",g)
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_viettel, R.id.nav_google,R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void copySQLITE() {
        try
        {
            File dbFile=getDatabasePath(DATABASE_NAME);
            if(! dbFile.exists())
            {
                copyDatabaseFromAsset();
                Toast.makeText(MainActivity.this,"Sao chép thành công",Toast.LENGTH_LONG).show();

            }

        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,ex.toString(),Toast.LENGTH_LONG).show();
            Log.e("LOI",ex.toString());
        }
    }

    private void copyDatabaseFromAsset() {
        try
        {
            InputStream myinput=getAssets().open(DATABASE_NAME);
            String outFileName=getDatabasePath();
            File f= new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists())

                f.mkdir();

            OutputStream myoutput= new FileOutputStream(outFileName);
            byte[] buffer= new byte[1024];
            int length;
            while ((length=myinput.read(buffer))>0)
            {
                myoutput.write(buffer,0,length);
            }
            myoutput.flush();
            myoutput.close();
            myinput.close();

        }
        catch ( Exception ex)
        {
            Log.e("LOI", ex.toString());
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
