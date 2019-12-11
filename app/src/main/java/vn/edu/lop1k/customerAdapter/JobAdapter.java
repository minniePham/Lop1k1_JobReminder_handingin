package vn.edu.lop1k.customerAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.edu.lop1k.Models.Job;
import vn.edu.lop1k.lop1k1_jobreminder.MainActivity;
import vn.edu.lop1k.lop1k1_jobreminder.R;
import vn.edu.lop1k.lop1k1_jobreminder.ui.gallery.GalleryFragment;

public class JobAdapter extends ArrayAdapter<Job> {
    Activity context;
    int resource;
    ArrayList<Job> objects;
    public JobAdapter(@NonNull Activity context, int resource, ArrayList<Job> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View customView=inflater.inflate(this.resource,null);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final  Job job= this.objects.get(position);
        TextView tieuDe=customView.findViewById(R.id.txtTieuDe_item);
        TextView noiDung=customView.findViewById(R.id.txtNoiDung_item);
        //TextView thoiGianBatDau=customView.findViewById(R.id.txtBatDau);
       // TextView thoiGianKetThuc=customView.findViewById(R.id.txtDeadline);
        ImageView imgXoa=customView.findViewById(R.id.imgXoa);
        imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                xuLyXoa(job);
            }
        });

        tieuDe.setText(job.getTieuDe());
        noiDung.setText("Bắt đầu : "+ job.getThoiGianBatDau());
        if(job.getTrangThai()==-1)
        {
            tieuDe.setBackgroundColor(Color.RED);
        }
        if(job.getTrangThai()==0)
        {
            tieuDe.setBackgroundColor(Color.YELLOW);
        }
        if(job.getTrangThai()==1)
        {
            tieuDe.setBackgroundColor(Color.GREEN);
        }

        // Time.setText(dateFormat.format(job.getTime()));
        return customView;
    }

    private void xuLyXoa(Job job) {
        long kq= MainActivity.myDatabase.deleteJob(job);
        if(kq>0)
        {
            GalleryFragment.cancelAlarms(getContext());
            Toast.makeText(context,"Thành công",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(context,"Thất bại",Toast.LENGTH_LONG).show();
        }

        GalleryFragment.arrJob.clear();
        GalleryFragment.arrJob.addAll(MainActivity.myDatabase.getAllJob(1));
        notifyDataSetChanged();
    }



}

