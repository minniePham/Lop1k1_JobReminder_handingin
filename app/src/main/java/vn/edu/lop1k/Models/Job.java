package vn.edu.lop1k.Models;

import java.io.Serializable;

public class Job implements Serializable {
    public String TieuDe;
    public String NoiDung;
    public String ThoiGianBatDau;
    public String ThoiGianKeThuc;
    public  int TrangThai;
    public  int lapLai;

    public Job() {
    }

    public Job(String tieuDe, String noiDung, String thoiGianBatDau, String thoiGianKeThuc, int trangThai, int lapLai) {
        TieuDe = tieuDe;
        NoiDung = noiDung;
        ThoiGianBatDau = thoiGianBatDau;
        ThoiGianKeThuc = thoiGianKeThuc;
        TrangThai = trangThai;
        this.lapLai = lapLai;
    }

    public String getTieuDe() {
        return TieuDe;
    }

    public void setTieuDe(String tieuDe) {
        TieuDe = tieuDe;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public String getThoiGianBatDau() {
        return ThoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        ThoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKeThuc() {
        return ThoiGianKeThuc;
    }

    public void setThoiGianKeThuc(String thoiGianKeThuc) {
        ThoiGianKeThuc = thoiGianKeThuc;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public int getLapLai() {
        return lapLai;
    }

    public void setLapLai(int lapLai) {
        this.lapLai = lapLai;
    }
}
