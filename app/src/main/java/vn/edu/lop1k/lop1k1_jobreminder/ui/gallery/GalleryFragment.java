package vn.edu.lop1k.lop1k1_jobreminder.ui.gallery;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.edu.lop1k.Models.Job;
import vn.edu.lop1k.customerAdapter.JobAdapter;
import vn.edu.lop1k.lop1k1_jobreminder.AlarmReceiver;
import vn.edu.lop1k.lop1k1_jobreminder.MainActivity;
import vn.edu.lop1k.lop1k1_jobreminder.R;



public class GalleryFragment extends ListFragment {
    public  static ArrayList<Job> arrJob;
    public  static Job selectedJob;
   // private static PendingIntent pendingIntent;
    JobAdapter jobAdapter;
    TextView  txtBatDau,txtKetThuc;
    EditText edtTieuDe, edtNoiDung;
    Button btnSetBatDau,btnSetKetThuc,btnLuu;
    RadioButton radTheoPhut,radTheoNgay,radTheoTuan,radKhongCanLap;
    View root;
    Button btnChonNgay,btnChonGio,btnOkTime;
    TextView txtChonNgayBD,txtChonGioBD,txtChonNgayDead,txtChonGioDead;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Date todayd;
    Date today;
    String ngayBatDaud ;
    RadioGroup radioGroup;
    Date ngayBatDau ;
    String ngayDeadlined ;
    Date ngayDead ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    ListView lvJob;
    int namBD,thangBD,ngayBD,gioBD,phutBD;
    int namKT,thangKT,ngayKT,gioKT,phutKT;

    static AlarmManager alarmManager1;
    static PendingIntent pendingIntent;
    Intent intentReceiver;
    SimpleDateFormat formattertime= new SimpleDateFormat("hh:mm");
    Calendar calendar;
    public static  ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();


    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        root= inflater.inflate(R.layout.activity_add, container, false);
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alarmManager1 = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLuu();
            }
        });
        btnSetBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySetBatDau();
            }
        });
        btnSetKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySetKetThuc();
            }
        });
        lvJob.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedJob=jobAdapter.getItem(position);
                if(selectedJob!=null) {
                    edtTieuDe.setText(selectedJob.getTieuDe());
                    edtTieuDe.setEnabled(false);
                    edtNoiDung.findFocus();
                    edtNoiDung.setText(selectedJob.getNoiDung());
                    txtBatDau.setText(selectedJob.getThoiGianBatDau());
                    txtKetThuc.setText(selectedJob.getThoiGianKeThuc());
                    if(selectedJob.lapLai==1)
                    {
                        radTheoPhut.setChecked(true);
                    }
                    if(selectedJob.lapLai==2)
                    {
                        radTheoNgay.setChecked(true);
                    }
                    if(selectedJob.lapLai==3)
                    {
                        radTheoTuan.setChecked(true);
                    }
                    if(selectedJob.lapLai==0)
                    {
                        radKhongCanLap.setChecked(true);
                    }

                }
            }
        });



    }

    private void xuLySetKetThuc() {
        final Dialog timeDialog= new Dialog(getActivity());
        timeDialog.setTitle("Set Deadline");
        timeDialog.setContentView(R.layout.select_time_dialog);
        timeDialog.setCanceledOnTouchOutside(false);
        btnChonNgay=timeDialog.findViewById(R.id.btnNgayDialog);
        btnChonGio=timeDialog.findViewById(R.id.btnGioDialog);
        txtChonNgayBD=timeDialog.findViewById(R.id.txtngaydialog);
        txtChonGioBD=timeDialog.findViewById(R.id.txtGioDialog);
        txtChonNgayDead=timeDialog.findViewById(R.id.txtngaydialog);
        txtChonGioDead=timeDialog.findViewById(R.id.txtGioDialog);
        if(selectedJob!=null) {
            String[] BD = selectedJob.getThoiGianBatDau().split(" ");
            txtChonNgayBD.setText(BD[0]);
            txtChonGioBD.setText(BD[1]);
            String[] KT = selectedJob.getThoiGianKeThuc().split(" ");
            txtChonNgayDead.setText(KT[0]);
            txtChonGioDead.setText(KT[1]);
        }
        btnOkTime=timeDialog.findViewById(R.id.btnOkDead);
        btnOkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    txtKetThuc.setText(txtChonNgayDead.getText() + " " + txtChonGioDead.getText());
                    todayd = java.util.Calendar.getInstance().getTime();
                    today = dateFormat.parse(dateFormat.format(todayd));
                    ngayBatDaud = txtChonNgayBD.getText().toString();
                    ngayBatDau = formatter.parse(ngayBatDaud);
                    ngayDeadlined = txtChonNgayDead.getText().toString();
                    ngayDead = formatter.parse(ngayDeadlined);
                    timeDialog.dismiss();
                }
                catch (ParseException ex) {
                    Log.v("Exception", ex.getLocalizedMessage());
                }
            }
        });
        btnChonGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonGioDead();
            }
        });
        btnChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonNgayDead();
            }
        });
        timeDialog.show();
    }
    private void xuLyChonGioDead() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        gioKT=hourOfDay;
                        phutKT=minute;
                        txtChonGioDead.setText(minute + ":" + hourOfDay);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    private void xuLyChonNgayDead() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        namKT=year;
                        thangKT=monthOfYear;
                        ngayKT=dayOfMonth;

                        txtChonNgayDead.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void xuLySetBatDau() {
        final Dialog timeDialog= new Dialog(getActivity());
        timeDialog.setTitle("Set thời gian");
        timeDialog.setContentView(R.layout.select_time_dialog);
        timeDialog.setCanceledOnTouchOutside(false);
        btnChonNgay=timeDialog.findViewById(R.id.btnNgayDialog);
        btnChonGio=timeDialog.findViewById(R.id.btnGioDialog);
        txtChonNgayBD=timeDialog.findViewById(R.id.txtngaydialog);
        txtChonGioBD=timeDialog.findViewById(R.id.txtGioDialog);
        txtChonNgayDead=timeDialog.findViewById(R.id.txtngaydialog);
        txtChonGioDead=timeDialog.findViewById(R.id.txtGioDialog);
        if(selectedJob!=null) {
            String[] BD = selectedJob.getThoiGianBatDau().split(" ");
            txtChonNgayBD.setText(BD[0]);
            txtChonGioBD.setText(BD[1]);
            String[] KT = selectedJob.getThoiGianKeThuc().split(" ");
            txtChonNgayDead.setText(KT[0]);
            txtChonGioDead.setText(KT[1]);
        }
        btnOkTime=timeDialog.findViewById(R.id.btnOkDead);
        btnOkTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBatDau.setText(txtChonNgayBD.getText()+" "+ txtChonGioBD.getText());
                timeDialog.dismiss();
            }
        });
        btnChonGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonGioBD();
            }
        });
        btnChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonNgayBD();
            }
        });
        timeDialog.show();
    }
    private void xuLyChonNgayBD() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        namBD=year;
                        thangBD=monthOfYear;
                        ngayBD=dayOfMonth;

                        txtChonNgayBD.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                       
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void xuLyChonGioBD() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        gioBD=hourOfDay;
                        phutBD=minute;

                        txtChonGioBD.setText(minute + ":" + hourOfDay);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void xuLyLuu() {
        int i=1;

            if (selectedJob != null) {
                intentReceiver = new Intent(getActivity(), AlarmReceiver.class);
                selectedJob.TieuDe = edtTieuDe.getText().toString();
                selectedJob.NoiDung = edtNoiDung.getText().toString();
                selectedJob.ThoiGianBatDau = txtBatDau.getText().toString();
                selectedJob.ThoiGianKeThuc = txtKetThuc.getText().toString();
                if (today != null) {
                    if (today.before(ngayBatDau)) {
                        selectedJob.TrangThai = -1;
                    }
                    if (today.after(ngayBatDau)) {
                        if (today.before(ngayDead)) {
                            selectedJob.TrangThai = 0;
                        } else {
                            selectedJob.TrangThai = 1;
                        }
                    }
                }
                Calendar calBD = Calendar.getInstance();
                Calendar calKT = Calendar.getInstance();
                calBD.set(namBD, thangBD, ngayBD, gioBD, phutBD,0);
                calKT.set(namKT, thangKT, ngayKT, gioKT, phutKT,0);
                intentReceiver.putExtra("name", selectedJob.TieuDe);
                intentReceiver.putExtra("time", selectedJob.ThoiGianBatDau);
                intentReceiver.putExtra("trangThai", "on");
                intentReceiver.setAction("uniqueCode");
                pendingIntent = PendingIntent.getBroadcast(getActivity(),selectedJob.getId(), intentReceiver, PendingIntent.FLAG_UPDATE_CURRENT);

                if (radTheoPhut.isChecked()) {
                    selectedJob.lapLai = 1;
                    alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, calBD.getTimeInMillis(),
                            1000 * 60 * 2, pendingIntent);
                }
                if (radTheoNgay.isChecked()) {
                    selectedJob.lapLai = 2;
                    alarmManager1.setInexactRepeating(AlarmManager.RTC_WAKEUP, calBD.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY,pendingIntent );

                }
                if (radTheoTuan.isChecked()) {
                    selectedJob.lapLai = 3;
                    alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, calBD.getTimeInMillis(),
                            7*1440*60000, pendingIntent);
                }
                if (radKhongCanLap.isChecked()) {
                    selectedJob.lapLai = 0;
                    alarmManager1.set(AlarmManager.RTC_WAKEUP, calBD.getTimeInMillis(),   pendingIntent);
                }

                long kq = MainActivity.myDatabase.updateJob(selectedJob);
                if (kq > 0) {
                    jobAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "update Thành công", Toast.LENGTH_LONG).show();
                    edtTieuDe.setText("");
                    txtBatDau.setText("");
                    txtKetThuc.setText("");
                    txtChonGioBD.setText("");
                    txtChonNgayBD.setText("");
                    txtChonGioDead.setText("");
                    txtChonNgayDead.setText("");
                    edtNoiDung.setText("");
                    txtBatDau.setText("");
                    txtKetThuc.setText("");
                    radioGroup.clearCheck();
                    edtTieuDe.findFocus();
                } else {
                    Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_LONG).show();
                }

                selectedJob = null;


            } else {
                try {

                    intentReceiver = new Intent(getActivity(), AlarmReceiver.class);
                    Job job1 = new Job();
                    job1.isEnabled = 1;
                    job1.TieuDe = edtTieuDe.getText().toString();
                    job1.NoiDung = edtNoiDung.getText().toString();
                    job1.ThoiGianBatDau = txtBatDau.getText().toString();
                    job1.ThoiGianKeThuc = txtKetThuc.getText().toString();
                    if (today.after(ngayBatDau)) {
                        if (today.before(ngayDead)) {
                            job1.TrangThai = 0;
                        } else {
                            job1.TrangThai = 1;
                        }
                    }
                    intentReceiver.putExtra("name", job1.TieuDe);
                    intentReceiver.putExtra("time", job1.ThoiGianBatDau);
                    intentReceiver.putExtra("trangThai", "on");
                    intentReceiver.setAction("uniqueCode");
                    Calendar calBD = Calendar.getInstance();
                    Calendar calKT = Calendar.getInstance();
                    calBD.set(namBD, thangBD, ngayBD, gioBD, phutBD);
                    calKT.set(namKT, thangKT, ngayKT, gioKT, phutKT);

                    pendingIntent = PendingIntent.getBroadcast(getActivity(),job1.getId(), intentReceiver, PendingIntent.FLAG_UPDATE_CURRENT);
                    if (radTheoPhut.isChecked()) {
                        job1.lapLai = 1;
                        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, calBD.getTimeInMillis(),
                                1000 * 60 * 2, pendingIntent);
                    }
                    if (radTheoNgay.isChecked()) {
                        job1.lapLai = 2;
                        alarmManager1.setInexactRepeating(AlarmManager.RTC_WAKEUP, calBD.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY,pendingIntent );
                    }
                    if (radTheoTuan.isChecked()) {
                        job1.lapLai = 3;
                        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP, calBD.getTimeInMillis(),
                                7*1440*60000, pendingIntent);
                    }
                    if (radKhongCanLap.isChecked()) {
                        job1.lapLai = 0;
                        alarmManager1.set(AlarmManager.RTC_WAKEUP, calBD.getTimeInMillis(),   pendingIntent);
                    }
                    long kq = MainActivity.myDatabase.addJob(job1);

                        if (kq > 0) {
                            lvJob.refreshDrawableState();
                            jobAdapter.notifyDataSetChanged();
                            getListView().requestLayout();
                            arrJob = new ArrayList<Job>();
                            List<Job> jobs = MainActivity.myDatabase.getAllJob(1);
                            arrJob.addAll(jobs);
                            jobAdapter = new JobAdapter(getActivity(), R.layout.item_joblist, arrJob);
                            lvJob.setAdapter(jobAdapter);
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_LONG).show();
                           edtTieuDe.setText("");
                           edtTieuDe.findFocus();
                           txtBatDau.setText("");
                           txtKetThuc.setText("");
                           txtChonGioBD.setText("");
                           txtChonNgayBD.setText("");
                           txtChonGioDead.setText("");
                           txtChonNgayDead.setText("");
                           edtNoiDung.setText("");
                           txtBatDau.setText("");
                           txtKetThuc.setText("");
                           radioGroup.clearCheck();
                            //radKhongCanLap.setChecked(false);
                            //radTheoTuan.setChecked(false);
                            //radTheoNgay.setChecked(false);
                            //radTheoPhut.setChecked(false);
                        } else {
                            Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_LONG).show();
                        }

                }

                catch (Exception ex)
                    {
                        Log.e("error",ex.toString());
                    }
                    }
            }






    public static   void cancelAlarms(Context context) {
       // SQLiteOpenHelper dbHelper = new SQL(context);


                    //PendingIntent pIntent = createPendingIntent(context, alarm);
                   // AlarmManager alarmManager = (AlarmManager)context.getSystemService("alarm");
                  //  intentReceiver.putExtra("trangthai","off");
                   // GalleryFragment gl=;
                    if(pendingIntent!=null) {
                        alarmManager1.cancel(pendingIntent);
                    }
                   //gl.intentReceiver.putExtra("trangThai","off");
                 //  gl.getActivity().sendBroadcast(gl.intentReceiver);




    }


    private void addControls() {
        edtTieuDe=root.findViewById(R.id.edtTieuDe);
        edtNoiDung=root.findViewById(R.id.edtNoiDung);
        txtBatDau=root.findViewById(R.id.txtBatDau);
        txtKetThuc=root.findViewById(R.id.txtDeadline);
        radTheoPhut=root.findViewById(R.id.radLapTheoPhut);
        radTheoNgay=root.findViewById(R.id.radLapTheoNgay);
        radTheoTuan=root.findViewById(R.id.radtheoTuan);
        radKhongCanLap=root.findViewById(R.id.radKhongCanNhac);
        btnSetBatDau=root.findViewById(R.id.btnBauDau);
        btnSetKetThuc=root.findViewById(R.id.btnDeadline);
        radioGroup=root.findViewById(R.id.radGroup);
        btnLuu=root.findViewById(R.id.btnLuuCV);
        lvJob=getListView();
        arrJob= new ArrayList<Job>();
        List<Job> jobs=MainActivity.myDatabase.getAllJob(1);
        arrJob.addAll(jobs);
        jobAdapter= new JobAdapter(getActivity(),R.layout.item_joblist,arrJob);
        lvJob.setAdapter(jobAdapter);
        jobAdapter.setNotifyOnChange(true);


    }




    @Override
    public void onResume() {
        super.onResume();
        addControls();
        jobAdapter.notifyDataSetChanged();
    }
}