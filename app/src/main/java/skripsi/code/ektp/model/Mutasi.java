package skripsi.code.ektp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TONY on 1/13/2017.
 */

public class Mutasi implements Parcelable {

    public String id_mutasi;
    public String id_anggota;
    public String id_admin;
    public String id_region_asal;
    public String id_region_tujuan;
    public String nama_anggota;
    public String nik_ktp;
    public String nomor_hp;
    public String keterangan;
    public String status_pengurus;
    public String status_mutasi;
    public String no_kta_lama;
    public String no_kta_baru;
    public String tgl_pengajuan_mutasi;
    public String tgl_konfirmasi_mutasi;
    public String provinsi_asal;
    public String kabupaten_asal;
    public String provinsi_tujuan;
    public String kabupaten_tujuan;

    public Mutasi(){

    }

    public void set_id_mutasi(String id_mutasi) {
        this.id_mutasi=id_mutasi;
    }
    public String get_id_mutasi(){ return id_mutasi;}

    public void set_id_anggota(String id_anggota) {
        this.id_anggota=id_anggota;
    }
    public String get_id_anggota(){ return id_anggota;}

    public void set_id_admin(String id_admin) {
        this.id_admin=id_admin;
    }
    public String get_id_admin(){ return id_admin;}

    public void set_id_region_asal(String id_region_asal) {
        this.id_region_asal=id_region_asal;
    }
    public String get_id_region_asal(){ return id_region_asal;}

    public void set_id_region_tujuan(String id_region_tujuan) {
        this.id_region_tujuan=id_region_tujuan;
    }
    public String get_id_region_tujuan(){ return id_region_tujuan;}

    public void set_nama_anggota(String nama_anggota) {
        this.nama_anggota=nama_anggota;
    }
    public String get_nama_anggota(){ return nama_anggota;}

    public void set_nik_ktp(String nik_ktp) {
        this.nik_ktp=nik_ktp;
    }
    public String get_nik_ktp(){ return nik_ktp;}

    public void set_nomor_hp(String nomor_hp) {
        this.nomor_hp=nomor_hp;
    }
    public String get_nomor_hp(){ return nomor_hp;}

    public void set_keterangan(String keterangan) {
        this.keterangan=keterangan;
    }
    public String get_keterangan(){ return keterangan;}

    public void set_status_pengurus(String status_pengurus) {
        this.status_pengurus=status_pengurus;
    }
    public String get_status_pengurus(){ return status_pengurus;}

    public void set_status_mutasi(String status_mutasi) {
        this.status_mutasi=status_mutasi;
    }
    public String get_status_mutasi(){ return status_mutasi;}

    public void set_no_kta_lama(String no_kta_lama) {
        this.no_kta_lama=no_kta_lama;
    }
    public String get_no_kta_lama(){ return no_kta_lama;}

    public void set_no_kta_baru(String no_kta_baru) {
        this.no_kta_baru=no_kta_baru;
    }
    public String get_no_kta_baru(){ return no_kta_baru;}

    public void set_tgl_pengajuan_mutasi(String tgl_pengajuan_mutasi) {
        this.tgl_pengajuan_mutasi=tgl_pengajuan_mutasi;
    }
    public String get_tgl_pengajuan_mutasi(){ return tgl_pengajuan_mutasi;}

    public void set_tgl_konfirmasi_mutasi(String tgl_konfirmasi_mutasi) {
        this.tgl_konfirmasi_mutasi=tgl_konfirmasi_mutasi;
    }
    public String get_tgl_konfirmasi_mutasi(){ return tgl_konfirmasi_mutasi;}

    public void set_provinsi_asal(String provinsi_asal) {
        this.provinsi_asal=provinsi_asal;
    }
    public String get_provinsi_asal(){ return provinsi_asal;}

    public void set_kabupaten_asal(String kabupaten_asal) {
        this.kabupaten_asal=kabupaten_asal;
    }
    public String get_kabupaten_asal(){ return kabupaten_asal;}

    public void set_provinsi_tujuan(String provinsi_tujuan) {
        this.provinsi_tujuan=provinsi_tujuan;
    }
    public String get_provinsi_tujuan(){ return provinsi_tujuan;}

    public void set_kabupaten_tujuan(String kabupaten_tujuan) {
        this.kabupaten_tujuan=kabupaten_tujuan;
    }
    public String get_kabupaten_tujuan(){ return kabupaten_tujuan;}


    public String get_nama_status_mutasi(){

        if(status_mutasi.equals("1")){
            status_mutasi = "DIKIRIMKAN";
        }else if(status_mutasi.equals("2")) {
            status_mutasi = "DITERIMA";
        }else if(status_mutasi.equals("3")) {
            status_mutasi = "DITOLAK";
        }
        return status_mutasi;
    }

    public String get_nama_status_pengurus(){
        if(status_pengurus.equals("1")){
            status_pengurus = "YA";
        }else {
            status_pengurus = "TIDAK";
        }
        return status_pengurus;
    }


    public Mutasi(Parcel in){

        this.id_mutasi=in.readString();
        this.id_anggota=in.readString();
        this.id_admin=in.readString();
        this.id_region_asal=in.readString();
        this.id_region_tujuan=in.readString();
        this.nama_anggota=in.readString();
        this.nik_ktp=in.readString();
        this.nomor_hp=in.readString();
        this.keterangan=in.readString();
        this.status_pengurus=in.readString();
        this.status_mutasi=in.readString();
        this.no_kta_lama=in.readString();
        this.no_kta_baru=in.readString();
        this.tgl_pengajuan_mutasi=in.readString();
        this.tgl_konfirmasi_mutasi=in.readString();
        this.provinsi_asal=in.readString();
        this.kabupaten_asal=in.readString();
        this.provinsi_tujuan=in.readString();
        this.kabupaten_tujuan=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id_mutasi);
        dest.writeString(id_anggota);
        dest.writeString(id_admin);
        dest.writeString(id_region_asal);
        dest.writeString(id_region_tujuan);
        dest.writeString(nama_anggota);
        dest.writeString(nik_ktp);
        dest.writeString(nomor_hp);
        dest.writeString(keterangan);
        dest.writeString(status_pengurus);
        dest.writeString(status_mutasi);
        dest.writeString(no_kta_lama);
        dest.writeString(no_kta_baru);
        dest.writeString(tgl_pengajuan_mutasi);
        dest.writeString(tgl_konfirmasi_mutasi);
        dest.writeString(provinsi_asal);
        dest.writeString(kabupaten_asal);
        dest.writeString(provinsi_tujuan);
        dest.writeString(kabupaten_tujuan);
    }

    public static final Creator CREATOR = new Creator() {
        public Mutasi createFromParcel(Parcel in) {
            return new Mutasi(in);
        }

        public Mutasi[] newArray(int size) {
            return new Mutasi[size];
        }
    };
}
