package skripsi.code.ektp.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Ktp implements Parcelable {
    public String id_ktp;
    public String id_admin;
    public String id_petugas;
    public String id_asal;
    public String nama_admin;
    public String nama_petugas;
    public String rt;
    public String rw;
    public String nik_ktp;
    public String nama_ktp;
    public String tempat_lahir;
    public String tanggal_lahir;
    public String jenis_kelamin;
    public String gol_darah;
    public String alamat_ktp;
    public String agama;
    public String status_nikah;
    public String pekerjaan;
    public String nomor_hp_ktp;
    public String img;
    public String img_thumb;
    public String tanggal_post;
    public String nama_img_ktp;
    public String nama_img_pas_foto;

    public String kodepos;
    public String pend_terakhir;
    public String email;
    public String nomor_rumah_ktp;
    public String nomor_kantor_ktp;
    public String nomor_faksimili_ktp;
    public String tanggal_daftar;
    public String nik_kta_lama;
    public String nik_kta_baru;
    public String pengurus;
    public String kategori_pengurus;
    public String jabatan;
    public String wilayah_pengurus;
    public String fb;
    public String twitter;
    public String ig;
    public String wa;
    public String barcode;
    public String status_mutasi;
    public String img_pas;
    public String img_pas_thumb;

    public String nama_pekerjaan;

    public String provinsi_asal;
    public String kabupaten_asal;
    public String kecamatan_asal;
    public String kelurahan_asal;

    public String provinsi_pengurus;
    public String kabupaten_pengurus;
    public String kecamatan_pengurus;
    public String kelurahan_pengurus;

    public String nama_img_id;
    public String nama_img_pas;


    public Ktp() {

    }

    public void set_nama_img_id(String nama_img_id) {
        this.nama_img_id=nama_img_id;
    }
    public String get_nama_img_id(){ return nama_img_id;}

    public void set_nama_img_pas(String nama_img_pas) {
        this.nama_img_pas=nama_img_pas;
    }
    public String get_nama_img_pas(){ return nama_img_pas;}

    public void set_nama_pekerjaan(String nama_pekerjaan) {
        this.nama_pekerjaan=nama_pekerjaan;
    }
    public String get_nama_pekerjaan(){ return nama_pekerjaan;}

    public void set_provinsi_asal(String provinsi_asal) {
        this.provinsi_asal=provinsi_asal;
    }
    public String get_provinsi_asal(){ return provinsi_asal;}

    public void set_kabupaten_asal(String kabupaten_asal) {
        this.kabupaten_asal=kabupaten_asal;
    }
    public String get_kabupaten_asal(){ return kabupaten_asal;}

    public void set_kecamatan_asal(String kecamatan_asal) {
        this.kecamatan_asal=kecamatan_asal;
    }
    public String get_kecamatan_asal(){ return kecamatan_asal;}

    public void set_kelurahan_asal(String kelurahan_asal) {
        this.kelurahan_asal=kelurahan_asal;
    }
    public String get_kelurahan_asal(){ return kelurahan_asal;}


    public void set_provinsi_pengurus(String provinsi_pengurus) {
        this.provinsi_pengurus=provinsi_pengurus;
    }
    public String get_provinsi_pengurus(){ return provinsi_pengurus;}

    public void set_kabupaten_pengurus(String kabupaten_pengurus) {
        this.kabupaten_pengurus=kabupaten_pengurus;
    }
    public String get_kabupaten_pengurus(){ return kabupaten_pengurus;}

    public void set_kecamatan_pengurus(String kecamatan_pengurus) {
        this.kecamatan_pengurus=kecamatan_pengurus;
    }
    public String get_kecamatan_pengurus(){ return kecamatan_pengurus;}

    public void set_kelurahan_pengurus(String kelurahan_pengurus) {
        this.kelurahan_pengurus=kelurahan_pengurus;
    }
    public String get_kelurahan_pengurus(){ return kelurahan_pengurus;}


    public void set_img_pas(String img_pas) {
        this.img_pas=img_pas;
    }
    public String get_img_pas(){ return img_pas;}

    public void set_img_pas_thumb(String img_pas_thumb) {
        this.img_pas_thumb=img_pas_thumb;
    }
    public String get_img_pas_thumb(){ return img_pas_thumb;}


    public void set_kodepos(String kodepos) {
        this.kodepos=kodepos;
    }
    public String get_kodepos(){ return kodepos;}

    public void set_pend_terakhir(String pend_terakhir) {
        this.pend_terakhir=pend_terakhir;
    }
    public String get_pend_terakhir(){ return pend_terakhir;}

    public void set_email(String email) {
        this.email=email;
    }
    public String get_email(){ return email;}

    public void set_nomor_rumah_ktp(String nomor_rumah_ktp) {
        this.nomor_rumah_ktp=nomor_rumah_ktp;
    }
    public String get_nomor_rumah_ktp(){ return nomor_rumah_ktp;}

    public void set_nomor_kantor_ktp(String nomor_kantor_ktp) {
        this.nomor_kantor_ktp=nomor_kantor_ktp;
    }
    public String get_nomor_kantor_ktp(){ return nomor_kantor_ktp;}

    public void set_nomor_faksimili_ktp(String nomor_faksimili_ktp) {
        this.nomor_faksimili_ktp=nomor_faksimili_ktp;
    }
    public String get_nomor_faksimili_ktp(){ return nomor_faksimili_ktp;}

    public void set_tanggal_daftar(String tanggal_daftar) {
        this.tanggal_daftar=tanggal_daftar;
    }
    public String get_tanggal_daftar(){ return tanggal_daftar;}

    public void set_nik_kta_lama(String nik_kta_lama) {
        this.nik_kta_lama=nik_kta_lama;
    }
    public String get_nik_kta_lama(){ return nik_kta_lama;}

    public void set_nik_kta_baru(String nik_kta_baru) {
        this.nik_kta_baru=nik_kta_baru;
    }
    public String get_nik_kta_baru(){ return nik_kta_baru;}

    public void set_pengurus(String pengurus) {
        this.pengurus=pengurus;
    }
    public String get_pengurus(){ return pengurus;}

    public void set_kategori_pengurus(String kategori_pengurus) {
        this.kategori_pengurus=kategori_pengurus;
    }
    public String get_kategori_pengurus(){ return kategori_pengurus;}

    public void set_jabatan(String jabatan) {
        this.jabatan=jabatan;
    }
    public String get_jabatan(){ return jabatan;}

    public void set_wilayah_pengurus(String wilayah_pengurus) {
        this.wilayah_pengurus=wilayah_pengurus;
    }
    public String get_wilayah_pengurus(){ return wilayah_pengurus;}

    public void set_fb(String fb) {
        this.fb=fb;
    }
    public String get_fb(){ return fb;}

    public void set_twitter(String twitter) {
        this.twitter=twitter;
    }
    public String get_twitter(){ return twitter;}

    public void set_ig(String ig) {
        this.ig=ig;
    }
    public String get_ig(){ return ig;}

    public void set_wa(String wa) {
        this.wa=wa;
    }
    public String get_wa(){ return wa;}

    public void set_barcode(String barcode) {
        this.barcode=barcode;
    }
    public String get_barcode(){ return barcode;}

    public void set_status_mutasi(String status_mutasi) {
        this.status_mutasi=status_mutasi;
    }
    public String get_status_mutasi(){ return status_mutasi;}

    public void set_id_ktp(String id_ktp) {
        this.id_ktp=id_ktp;
    }
    public String get_id_ktp(){ return id_ktp;}

    public void set_id_admin(String id_admin) {
        this.id_admin=id_admin;
    }
    public String get_id_admin(){ return id_admin;}

    public void set_id_asal(String id_asal) {
        this.id_asal=id_asal;
    }
    public String get_id_asal(){ return id_asal;}

    public void set_id_petugas(String id_petugas) {
        this.id_petugas=id_petugas;
    }
    public String get_id_petugas(){ return id_petugas;}

    public void set_nama_admin(String nama_admin) {
        this.nama_admin=nama_admin;
    }
    public String get_nama_admin(){ return nama_admin;}

    public void set_nama_petugas(String nama_petugas) {
        this.nama_petugas=nama_petugas;
    }
    public String get_nama_petugas(){ return nama_petugas;}

    public void set_rt(String rt) {
        this.rt=rt;
    }
    public String get_rt(){ return rt;}

    public void set_rw(String rw) {
        this.rw=rw;
    }
    public String get_rw(){ return rw;}

    public void set_nik_ktp(String nik_ktp) {this.nik_ktp=nik_ktp; }
    public String get_nik_ktp(){ return nik_ktp;}

    public void set_nama_ktp(String nama_ktp) {this.nama_ktp=nama_ktp;}
    public String get_nama_ktp(){ return nama_ktp;}

    public void set_tempat_lahir(String tempat_lahir) {
        this.tempat_lahir=tempat_lahir;
    }
    public String get_tempat_lahir(){ return tempat_lahir;}

    public void set_tanggal_lahir(String tanggal_lahir) {this.tanggal_lahir=tanggal_lahir; }
    public String get_tanggal_lahir(){ return tanggal_lahir;}

    public void set_jenis_kelamin(String jenis_kelamin) {this.jenis_kelamin=jenis_kelamin;}
    public String get_jenis_kelamin(){ return jenis_kelamin;}

    public void set_gol_darah(String gol_darah) {this.gol_darah=gol_darah;}
    public String get_gol_darah(){ return gol_darah;}

    public void set_alamat_ktp(String alamat_ktp) {this.alamat_ktp=alamat_ktp;}
    public String get_alamat_ktp(){ return alamat_ktp;}

    public void set_agama(String agama) {
        this.agama=agama;
    }
    public String get_agama(){ return agama;}

    public void set_status_nikah(String status_nikah) {this.status_nikah=status_nikah; }
    public String get_status_nikah(){ return status_nikah;}

    public void set_pekerjaan(String pekerjaan) {this.pekerjaan=pekerjaan;}
    public String get_pekerjaan(){ return pekerjaan;}

    public void set_nomor_hp_ktp(String nomor_hp_ktp) {this.nomor_hp_ktp=nomor_hp_ktp; }
    public String get_nomor_hp_ktp(){ return nomor_hp_ktp;}

    public void set_img(String img) {this.img=img;}
    public String get_img(){ return img;}

    public void set_img_thumb(String img_thumb) {this.img_thumb=img_thumb; }
    public String get_img_thumb(){ return img_thumb;}

    public void set_tanggal_post(String tanggal_post) {this.tanggal_post=tanggal_post;}
    public String get_tanggal_post(){ return tanggal_post;}

    public void set_nama_img_ktp(String nama_img_ktp) {this.nama_img_ktp =nama_img_ktp;}
    public String get_nama_img_ktp(){ return nama_img_ktp;}

    public void set_nama_img_pas_foto(String nama_img_pas_foto) {this.nama_img_pas_foto =nama_img_pas_foto;}
    public String get_nama_img_pas_foto(){ return nama_img_pas_foto;}

    public String get_nama_jenis_kelamin(){
        if(jenis_kelamin.equals("1")){
            jenis_kelamin = "Laki-Laki";
        }else {
            jenis_kelamin = "Perempuan";
        }
        return jenis_kelamin;
    }

    public String get_nama_status_pengurus(){
        if(pengurus.equals("1")){
            pengurus = "YA";
        }else {
            pengurus = "TIDAK";
        }
        return pengurus;
    }

    public String get_nama_status_mutasi(){

        if(status_mutasi.equals("1") || status_mutasi==""){
            status_mutasi = "PROSES MUTASI";
        }else {
            status_mutasi = "";
        }
        return status_mutasi;
    }

    public String get_nama_kategori_pengurus(){

        if(kategori_pengurus.equals("1")){
            kategori_pengurus = "DPP";
        }else if(kategori_pengurus.equals("2")) {
            kategori_pengurus = "DPD";
        }else if(kategori_pengurus.equals("3")) {
            kategori_pengurus = "DPC";
        }else if(kategori_pengurus.equals("4")) {
            kategori_pengurus = "PAC";
        }else if(kategori_pengurus.equals("5")) {
            kategori_pengurus = "RANTING";
        }
        return kategori_pengurus;
    }

    public String get_nama_pendidikan_terakhir(){

        if(pend_terakhir.equals("1")){
            pend_terakhir = "TIDAK SEKOLAH";
        }else if(pend_terakhir.equals("2")) {
            pend_terakhir = "SD";
        }else if(pend_terakhir.equals("3")) {
            pend_terakhir = "SLTP";
        }else if(pend_terakhir.equals("4")) {
            pend_terakhir = "SLTA";
        }else if(pend_terakhir.equals("5")) {
            pend_terakhir = "D-I/D-II";
        }else if(pend_terakhir.equals("6")) {
            pend_terakhir = "D-III";
        }else if(pend_terakhir.equals("7")) {
            pend_terakhir = "D-IV/S1";
        }else if(pend_terakhir.equals("8")) {
            pend_terakhir = "S2/S3";
        }
        return pend_terakhir;
    }

    public String get_nama_agama(){
        if(agama.equals("1")){
            agama = "Islam";
        }else if(agama.equals("2")){
            agama = "Kristen";
        }else if(agama.equals("3")){
            agama = "Hindu";
        }else if(agama.equals("4")){
            agama = "Budha";
        }else {
            agama = "Lainnya";
        }
        return agama;
    }

    public String get_nama_golongan_darah(){
        if(gol_darah.equals("1")){
            gol_darah = "A";
        }else if(gol_darah.equals("2")){
            gol_darah = "B";
        }else if(gol_darah.equals("3")){
            gol_darah = "AB";
        }else if(gol_darah.equals("4")){
            gol_darah = "O";
        }else {
            gol_darah = "Lainnya";
        }
        return gol_darah;
    }

    public String get_nama_status_nikah(){
        if(status_nikah.equals("0")){
            status_nikah = "BELUM MENIKAH";
        }else if(status_nikah.equals("1")){
            status_nikah = "MENIKAH";
        }else if(status_nikah.equals("2")){
            status_nikah = "CERAI HIDUP";
        }else if(status_nikah.equals("3")){
            status_nikah = "CERAI MATI";
        }else {
            status_nikah = "Lainnya";
        }
        return status_nikah;
    }

    // Parcelling part
    public Ktp(Parcel in){

        this.id_ktp=in.readString();
        this.id_admin=in.readString();
        this.id_asal=in.readString();
        this.id_petugas=in.readString();
        this.nama_admin=in.readString();
        this.nama_petugas=in.readString();
        this.rt=in.readString();
        this.rw=in.readString();
        this.nik_ktp=in.readString();
        this.nama_ktp=in.readString();
        this.tempat_lahir=in.readString();
        this.tanggal_lahir=in.readString();
        this.jenis_kelamin=in.readString();
        this.gol_darah=in.readString();
        this.alamat_ktp=in.readString();
        this.agama=in.readString();
        this.status_nikah=in.readString();
        this.pekerjaan=in.readString();
        this.nomor_hp_ktp=in.readString();
        this.img=in.readString();
        this.img_thumb=in.readString();
        this.tanggal_post=in.readString();
        this.nama_img_ktp =in.readString();
        this.kodepos=in.readString();
        this.pend_terakhir=in.readString();
        this.email=in.readString();
        this.nomor_rumah_ktp=in.readString();
        this.nomor_kantor_ktp=in.readString();
        this.nomor_faksimili_ktp=in.readString();
        this.tanggal_daftar=in.readString();
        this.nik_kta_lama=in.readString();
        this.nik_kta_baru=in.readString();
        this.pengurus=in.readString();
        this.kategori_pengurus=in.readString();
        this.jabatan=in.readString();
        this.wilayah_pengurus=in.readString();
        this.fb=in.readString();
        this.twitter=in.readString();
        this.ig=in.readString();
        this.wa=in.readString();
        this.barcode=in.readString();
        this.status_mutasi=in.readString();
        this.img_pas=in.readString();
        this.img_pas_thumb=in.readString();
        this.nama_pekerjaan=in.readString();
        this.provinsi_asal=in.readString();
        this.kabupaten_asal=in.readString();
        this.kecamatan_asal=in.readString();
        this.kelurahan_asal=in.readString();
        this.provinsi_pengurus=in.readString();
        this.kabupaten_pengurus=in.readString();
        this.kecamatan_pengurus=in.readString();
        this.kelurahan_pengurus=in.readString();
        this.nama_img_id=in.readString();
        this.nama_img_pas=in.readString();
        this.nama_img_pas_foto=in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id_ktp);
        dest.writeString(id_admin);
        dest.writeString(id_asal);
        dest.writeString(id_petugas);
        dest.writeString(nama_admin);
        dest.writeString(nama_petugas);
        dest.writeString(rt);
        dest.writeString(rw);
        dest.writeString(nik_ktp);
        dest.writeString(nama_ktp);
        dest.writeString(tempat_lahir);
        dest.writeString(tanggal_lahir);
        dest.writeString(jenis_kelamin);
        dest.writeString(gol_darah);
        dest.writeString(alamat_ktp);
        dest.writeString(agama);
        dest.writeString(status_nikah);
        dest.writeString(pekerjaan);
        dest.writeString(nomor_hp_ktp);
        dest.writeString(img);
        dest.writeString(img_thumb);
        dest.writeString(tanggal_post);
        dest.writeString(nama_img_ktp);
        dest.writeString(kodepos);
        dest.writeString(pend_terakhir);
        dest.writeString(email);
        dest.writeString(nomor_rumah_ktp);
        dest.writeString(nomor_kantor_ktp);
        dest.writeString(nomor_faksimili_ktp);
        dest.writeString(tanggal_daftar);
        dest.writeString(nik_kta_lama);
        dest.writeString(nik_kta_baru);
        dest.writeString(pengurus);
        dest.writeString(kategori_pengurus);
        dest.writeString(jabatan);
        dest.writeString(wilayah_pengurus);
        dest.writeString(fb);
        dest.writeString(twitter);
        dest.writeString(ig);
        dest.writeString(wa);
        dest.writeString(barcode);
        dest.writeString(status_mutasi);
        dest.writeString(img_pas);
        dest.writeString(img_pas_thumb);
        dest.writeString(nama_pekerjaan);
        dest.writeString(provinsi_asal);
        dest.writeString(kabupaten_asal);
        dest.writeString(kecamatan_asal);
        dest.writeString(kelurahan_asal);
        dest.writeString(provinsi_pengurus);
        dest.writeString(kabupaten_pengurus);
        dest.writeString(kecamatan_pengurus);
        dest.writeString(kelurahan_pengurus);
        dest.writeString(nama_img_id);
        dest.writeString(nama_img_pas);
        dest.writeString(nama_img_pas_foto);

    }

    public static final Creator CREATOR = new Creator() {
        public Ktp createFromParcel(Parcel in) {
            return new Ktp(in);
        }
        public Ktp[] newArray(int size) {
            return new Ktp[size];
        }
    };
}
