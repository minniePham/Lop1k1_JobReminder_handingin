package vn.edu.lop1k.lop1k1_jobreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.edu.lop1k.Models.Job;

import static vn.edu.lop1k.lop1k1_jobreminder.MainActivity.DATABASE_NAME;

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME,null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Job> getAllJob(int isenable) {
        List<Job> Jobs = new ArrayList<>();
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Job where IsEnable = "+isenable, null);
        while (cursor.moveToNext()) {
            String tieuDe = cursor.getString(0);
            String noiDung = cursor.getString(1);
            String thoiGianBatDau = cursor.getString(2);
            String thoiGianKetThuc = cursor.getString(3);
            Integer trangThai = cursor.getInt(4);
            Integer lapLai=cursor.getInt(5);
            Integer isEnable=cursor.getInt(6);
            Integer id=cursor.getInt(7);
            Job job = new Job( tieuDe, noiDung, thoiGianBatDau, thoiGianKetThuc,trangThai,lapLai,isEnable,id);
            Jobs.add(job);
        }
        return Jobs;
    }
    public long addJob(Job job){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("TieuDe",job.TieuDe);
        values.put("NoiDung",job.NoiDung);
        values.put("ThoiGianBatDau",job.ThoiGianBatDau);
        values.put("ThoiGianKetThuc",job.ThoiGianKeThuc);
        values.put("TrangThai",job.TrangThai);
        values.put("LapLai",job.lapLai);
        values.put("IsEnable",job.isEnabled);
        long kq=database.insert("Job",null,values);
        return  kq;
    }
    public  long updateJob(Job job)
    {
        SQLiteDatabase database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("TieuDe",job.TieuDe);
        values.put("NoiDung",job.NoiDung);
        values.put("ThoiGianBatDau",job.ThoiGianBatDau);
        values.put("ThoiGianKetThuc",job.ThoiGianKeThuc);
        values.put("TrangThai",job.TrangThai);
        values.put("LapLai",job.lapLai);
        values.put("IsEnable",1);
        long kq= database.update("Job",values,"TieuDe=?",new String[]{job.getTieuDe()});
        getAllJob(1);
        return kq;
    }
    public  long deleteJob(Job job)
    {
        SQLiteDatabase database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("TieuDe",job.TieuDe);
        values.put("NoiDung",job.NoiDung);
        values.put("ThoiGianBatDau",job.ThoiGianBatDau);
        values.put("ThoiGianKetThuc",job.ThoiGianKeThuc);
        values.put("TrangThai",job.TrangThai);
        values.put("LapLai",job.lapLai);
        values.put("IsEnable",0);
        long kq= database.update("Job",values,"TieuDe=?",new String[]{job.getTieuDe()});
        getAllJob(1);
        return kq;
    }
}
