package skripsi.code.ektp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TONY on 1/13/2017.
 */

public class Petugas implements Parcelable {

    public String id_petugas;
    public String id_admin;
    public String nama_petugas;
    public String nomor_ktp;
    public String email_petugas;
    public String nomor_hp;
    public String alamat_petugas;
    public String kode_petugas;
    public String img;
    public String img_thumb;
    public String tanggal_post;
    public String status_data;
    public String license;
    public String img_name;
    public String provinsi;
    public String kabupaten;
    public String password;
    public String path;

    public Petugas(){

    }

    public void set_path(String path) {
        this.path=path;
    }
    public String get_path(){ return path;}

    public void set_password(String password) {
        this.password=password;
    }
    public String get_password(){ return password;}

    public void set_provinsi(String provinsi) {
        this.provinsi=provinsi;
    }
    public String get_provinsi(){ return provinsi;}

    public void set_kabupaten(String kabupaten) {
        this.kabupaten=kabupaten;
    }
    public String get_kabupaten(){ return kabupaten;}

    public void set_id_petugas(String id_petugas) {
        this.id_petugas=id_petugas;
    }
    public String get_id_petugas(){ return id_petugas;}

    public void set_id_admin(String id_admin) {
        this.id_admin=id_admin;
    }
    public String get_id_admin(){ return id_admin;}

    public void set_nama_petugas(String nama_petugas) {
        this.nama_petugas=nama_petugas;
    }
    public String get_nama_petugas(){ return nama_petugas;}

    public void set_nomor_ktp(String nomor_ktp) {
        this.nomor_ktp=nomor_ktp;
    }
    public String get_nomor_ktp(){ return nomor_ktp;}

    public void set_email_petugas(String email_petugas) {
        this.email_petugas=email_petugas;
    }
    public String get_email_petugas(){ return email_petugas;}

    public void set_nomor_hp(String nomor_hp) {
        this.nomor_hp=nomor_hp;
    }
    public String get_nomor_hp(){ return nomor_hp;}

    public void set_alamat_petugas(String alamat_petugas) {
        this.alamat_petugas=alamat_petugas;
    }
    public String get_alamat_petugas(){ return alamat_petugas;}

    public void set_kode_petugas(String kode_petugas) {
        this.kode_petugas=kode_petugas;
    }
    public String get_kode_petugas(){ return kode_petugas;}

    public void set_img(String img) {
        this.img=img;
    }
    public String get_img(){ return img;}

    public void set_img_thumb(String img_thumb) {
        this.img_thumb=img_thumb;
    }
    public String get_img_thumb(){ return img_thumb;}

    public void set_tanggal_post(String tanggal_post) {
        this.tanggal_post=tanggal_post;
    }
    public String get_tanggal_post(){ return tanggal_post;}

    public void set_status_data(String status_data) {
        this.status_data=status_data;
    }
    public String get_status_data(){ return status_data;}

    public void set_license(String license) {
        this.license=license;
    }
    public String get_license(){ return license;}

    public void set_img_name(String img_name) {
        this.img_name=img_name;
    }
    public String get_img_name(){ return img_name;}


    public Petugas(Parcel in){

        this.id_petugas=in.readString();
        this.id_admin=in.readString();
        this.nama_petugas=in.readString();
        this.nomor_ktp=in.readString();
        this.email_petugas=in.readString();
        this.nomor_hp=in.readString();
        this.alamat_petugas=in.readString();
        this.kode_petugas=in.readString();
        this.img=in.readString();
        this.img_thumb=in.readString();
        this.tanggal_post=in.readString();
        this.status_data=in.readString();
        this.license=in.readString();
        this.provinsi=in.readString();
        this.kabupaten=in.readString();
        this.img_name=in.readString();
        this.path=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id_petugas);
        dest.writeString(id_admin);
        dest.writeString(nama_petugas);
        dest.writeString(nomor_ktp);
        dest.writeString(email_petugas);
        dest.writeString(nomor_hp);
        dest.writeString(alamat_petugas);
        dest.writeString(kode_petugas);
        dest.writeString(img);
        dest.writeString(img_thumb);
        dest.writeString(tanggal_post);
        dest.writeString(status_data);
        dest.writeString(license);
        dest.writeString(provinsi);
        dest.writeString(kabupaten);
        dest.writeString(img_name);
        dest.writeString(path);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Petugas createFromParcel(Parcel in) {
            return new Petugas(in);
        }

        public Petugas[] newArray(int size) {
            return new Petugas[size];
        }
    };
}
