package vn.edu.lop1k.lop1k1_jobreminder.ui.gallery;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import vn.edu.lop1k.lop1k1_jobreminder.MainActivity;
import vn.edu.lop1k.lop1k1_jobreminder.R;

public class GalleryFragment extends ListFragment {
    public  static ArrayList<Job> arrJob;
    public  static Job selectedJob;
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
    Date ngayBatDau ;
    String ngayDeadlined ;
    Date ngayDead ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
    ListView lvJob;
    SimpleDateFormat formattertime= new SimpleDateFormat("hh:mm");
    


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
                        txtChonGioBD.setText(minute + ":" + hourOfDay);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void xuLyLuu() {
        try {
            if (selectedJob != null) {
                selectedJob.TieuDe = edtTieuDe.getText().toString();
                selectedJob.NoiDung = edtNoiDung.getText().toString();
                selectedJob.ThoiGianBatDau = txtBatDau.getText().toString();
                selectedJob.ThoiGianBatDau = txtKetThuc.getText().toString();
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
                if (radTheoPhut.isChecked()) {
                    selectedJob.lapLai = 1;
                }
                if (radTheoNgay.isChecked()) {
                    selectedJob.lapLai = 2;
                }
                if (radTheoTuan.isChecked()) {
                    selectedJob.lapLai = 3;
                }
                if (radKhongCanLap.isChecked()) {
                    selectedJob.lapLai = 0;
                }

                long kq = MainActivity.myDatabase.updateJob(selectedJob);
                if (kq > 0) {
                    jobAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_LONG).show();
                    edtTieuDe.setText("");
                    edtNoiDung.setText("");
                    txtBatDau.setText("");
                    txtKetThuc.setText("");
                    radKhongCanLap.setChecked(false);
                    radTheoTuan.setChecked(false);
                    radTheoNgay.setChecked(false);
                    radTheoPhut.setChecked(false);
                } else {
                    Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_LONG).show();
                }

                selectedJob = null;


            } else {
                Job job1 = new Job();
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
                if (radTheoPhut.isChecked()) {
                    job1.lapLai = 1;
                }
                if (radTheoNgay.isChecked()) {
                    job1.lapLai = 2;
                }
                if (radTheoTuan.isChecked()) {
                    job1.lapLai = 3;
                }
                if (radKhongCanLap.isChecked()) {
                    job1.lapLai = 0;
                }
                long kq = MainActivity.myDatabase.addJob(job1);
                if (kq > 0) {
                    lvJob.refreshDrawableState();
                    jobAdapter.notifyDataSetChanged();
                    getListView().requestLayout();
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_LONG).show();
                    edtTieuDe.setText("");
                    edtNoiDung.setText("");
                    txtBatDau.setText("");
                    txtKetThuc.setText("");
                    radKhongCanLap.setChecked(false);
                    radTheoTuan.setChecked(false);
                    radTheoNgay.setChecked(false);
                    radTheoPhut.setChecked(false);
                } else {
                    Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (Exception ex)
        {
            Log.e("error",ex.toString());
        }



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
        btnLuu=root.findViewById(R.id.btnLuuCV);
        lvJob=getListView();
        arrJob= new ArrayList<Job>();
        List<Job> jobs=MainActivity.myDatabase.getAllJob();
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