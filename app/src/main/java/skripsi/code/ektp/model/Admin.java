package skripsi.code.ektp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TONY on 1/13/2017.
 */

public class Admin implements Parcelable {

    public String id_admin;
    public String role_admin;
    public String id_ref;
    public String nama_admin;
    public String email;
    public String nomor_hp;
    public String img;
    public String img_thumb;
    public String license;
    public String license_exp;
    public String create;
    public String read;
    public String update;
    public String delete;
    public String provinsi;
    public String kabupaten;
    public String tanggal_post;

    public Admin(){

    }

    public void set_id_admin(String id_admin) {
        this.id_admin=id_admin;
    }
    public String get_id_admin(){ return id_admin;}

    public void set_role_admin(String role_admin) {
        this.role_admin=role_admin;
    }
    public String get_role_admin(){ return role_admin;}

    public void set_id_ref(String id_ref) {
        this.id_ref=id_ref;
    }
    public String get_id_ref(){ return id_ref;}

    public void set_nama_admin(String nama_admin) {
        this.nama_admin=nama_admin;
    }
    public String get_nama_admin(){ return nama_admin;}

    public void set_email(String email) {
        this.email=email;
    }
    public String get_email(){ return email;}

    public void set_nomor_hp(String nomor_hp) {
        this.nomor_hp=nomor_hp;
    }
    public String get_nomor_hp(){ return nomor_hp;}

    public void set_img(String img) {
        this.img=img;
    }
    public String get_img(){ return img;}

    public void set_img_thumb(String img_thumb) {
        this.img_thumb=img_thumb;
    }
    public String get_img_thumb(){ return img_thumb;}

    public void set_license(String license) {
        this.license=license;
    }
    public String get_license(){ return license;}

    public void set_license_exp(String license_exp) {
        this.license_exp=license_exp;
    }
    public String get_license_exp(){ return license_exp;}

    public void set_create(String create) {
        this.create=create;
    }
    public String get_create(){ return create;}

    public void set_read(String read) {
        this.read=read;
    }
    public String get_read(){ return read;}

    public void set_delete(String delete) {
        this.delete=delete;
    }
    public String get_delete(){ return delete;}

    public void set_update(String update) {
        this.update=update;
    }
    public String get_update(){ return update;}

    public void set_provinsi(String provinsi) {
        this.provinsi=provinsi;
    }
    public String get_provinsi(){ return provinsi;}

    public void set_kabupaten(String kabupaten) {
        this.kabupaten=kabupaten;
    }
    public String get_kabupaten(){ return kabupaten;}

    public void set_tanggal_post(String tanggal_post) {
        this.tanggal_post=tanggal_post;
    }
    public String get_tanggal_post(){ return tanggal_post;}


    public Admin(Parcel in){

        this.id_admin=in.readString();
        this.role_admin=in.readString();
        this.id_ref=in.readString();
        this.nama_admin=in.readString();
        this.email=in.readString();
        this.nomor_hp=in.readString();
        this.img=in.readString();
        this.img_thumb=in.readString();
        this.license=in.readString();
        this.license_exp=in.readString();
        this.create=in.readString();
        this.read=in.readString();
        this.update=in.readString();
        this.delete=in.readString();
        this.provinsi=in.readString();
        this.kabupaten=in.readString();
        this.tanggal_post=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id_admin);
        dest.writeString(role_admin);
        dest.writeString(id_ref);
        dest.writeString(nama_admin);
        dest.writeString(email);
        dest.writeString(nomor_hp);
        dest.writeString(img);
        dest.writeString(img_thumb);
        dest.writeString(license);
        dest.writeString(license_exp);
        dest.writeString(create);
        dest.writeString(read);
        dest.writeString(update);
        dest.writeString(delete);
        dest.writeString(provinsi);
        dest.writeString(kabupaten);
        dest.writeString(tanggal_post);

    }

    public static final Creator CREATOR = new Creator() {
        public Admin createFromParcel(Parcel in) {
            return new Admin(in);
        }

        public Admin[] newArray(int size) {
            return new Admin[size];
        }
    };
}
