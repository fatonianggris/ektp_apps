package skripsi.code.ektp.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import skripsi.code.ektp.controller.ControllerKtpPetugas;
import skripsi.code.ektp.model.Ktp;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.BadgeDrawable;
import skripsi.code.ektp.R;

public class DetailKtpActivity extends AppCompatActivity {
    private ControllerKtpPetugas
            cKtp;
    private ProgressDialog
            pDialog;
    private TextView
            nik_ktp,
            nama_ktp,
            tempat_lahir,
            tanggal_lahir,
            alamat_ktp,
            rt,
            rw,
            nomor_hp_ktp,
            nama_provinsi,
            nama_kabupaten,
            kodepos,
            email,
            nomor_rumah_ktp,
            nomor_kantor_ktp,
            nomor_faksimili_ktp,
            pendidikan_akhir,
            jabatan,
            fb,
            ig,
            twitter,
            wa,
            status_nikah,
            jenis_kelamin,
            gol_darah,
            agama,
            pekerjaan,
            ket_image,
            provinsi_asal,
            kabupaten_asal,
            kecamatan_asal,
            kelurahan_asal,
            provinsi_pengurus,
            kabupaten_pengurus,
            kecamatan_pengurus,
            kelurahan_pengurus,
            status_pengurus,
            kategori_pengurus,
            nomor_kta_baru,
            imgPath;
    private String
            foto_ktp,
            foto_pas;

    private ImageView
            img_id,
            img_pas_foto;
    private LinearLayout
            img_ktp_click;

    private AlphaAnimation layoutClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ktp_main);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        cKtp = new ControllerKtpPetugas(DetailKtpActivity.this, UrlConfig.URL_DETAIL_KTP,null);

        img_id = (ImageView) findViewById(R.id.img_ktp);
        img_pas_foto = (ImageView) findViewById(R.id.img_pas_thumb);
        nik_ktp = (TextView) findViewById(R.id.nik_ktp);
        nama_ktp = (TextView) findViewById(R.id.nama_anggota);
        tempat_lahir = (TextView) findViewById(R.id.tempat_lahir);
        tanggal_lahir = (TextView) findViewById(R.id.tanggal_lahir);
        jenis_kelamin = (TextView) findViewById(R.id.jenis_kelamin);
        gol_darah = (TextView) findViewById(R.id.gol_darah);
        alamat_ktp = (TextView) findViewById(R.id.alamat_ktp);
        rt = (TextView) findViewById(R.id.rt);
        rw = (TextView) findViewById(R.id.rw);
        agama = (TextView) findViewById(R.id.agama);
        pendidikan_akhir = (TextView) findViewById(R.id.pendidikan_akhir);
        pekerjaan = (TextView) findViewById(R.id.pekerjaan);
        nomor_hp_ktp = (TextView) findViewById(R.id.nomor_hp_ktp);
        ket_image = (TextView) findViewById(R.id.ket_image);
        kodepos = (TextView) findViewById(R.id.kode_pos);
        email = (TextView) findViewById(R.id.email);
        nomor_rumah_ktp = (TextView) findViewById(R.id.nomor_telp_rumah);
        nomor_kantor_ktp = (TextView) findViewById(R.id.nomor_telp_kantor);
        nomor_faksimili_ktp = (TextView) findViewById(R.id.nomor_faksimili);
        nomor_kta_baru = (TextView) findViewById(R.id.nomor_kta_baru);
        jabatan = (TextView) findViewById(R.id.jabatan);
        fb = (TextView) findViewById(R.id.facebook);
        ig = (TextView) findViewById(R.id.instagram);
        twitter = (TextView) findViewById(R.id.twitter);
        wa = (TextView) findViewById(R.id.whatsapp);
        status_nikah = (TextView) findViewById(R.id.status_nikah);
        status_pengurus = (TextView) findViewById(R.id.status_pengurus);
        kategori_pengurus = (TextView) findViewById(R.id.kategori_pengurus);
        provinsi_asal = (TextView) findViewById(R.id.provinsi_asal);
        kabupaten_asal = (TextView) findViewById(R.id.kabupaten_asal);
        kecamatan_asal = (TextView) findViewById(R.id.kecamatan_asal);
        kelurahan_asal = (TextView) findViewById(R.id.kelurahan_asal);
        provinsi_pengurus = (TextView) findViewById(R.id.provinsi_pengurus);
        kabupaten_pengurus = (TextView) findViewById(R.id.kabupaten_pengurus);
        kecamatan_pengurus = (TextView) findViewById(R.id.kecamatan_pengurus);
        kelurahan_pengurus = (TextView) findViewById(R.id.kelurahan_pengurus);

        nama_provinsi = (TextView) findViewById(R.id.nama_provinsi);
        nama_kabupaten = (TextView) findViewById(R.id.nama_kabupaten);

        img_ktp_click = (LinearLayout) findViewById(R.id.imagejob);

        img_ktp_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(layoutClick);
                displayImageID();
            }
        });

        if(isNetworkAvailable(this)) {
            getDetailJob();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDetailJob(){

        final ArrayList<Ktp> get_ktp = (ArrayList<Ktp>) getIntent().getSerializableExtra("getdetail");

        foto_ktp = get_ktp.get(0).get_img_thumb();
        foto_pas = get_ktp.get(0).get_img_pas_thumb();
        nik_ktp.setText(get_ktp.get(0).get_nik_ktp().toUpperCase());
        nomor_kta_baru.setText(get_ktp.get(0).get_nik_kta_baru().toUpperCase());
        nama_ktp.setText(get_ktp.get(0).get_nama_ktp().toUpperCase());
        tempat_lahir.setText(get_ktp.get(0).get_tempat_lahir().toUpperCase());
        tanggal_lahir.setText(get_ktp.get(0).get_tanggal_lahir().toUpperCase());
        jenis_kelamin.setText(get_ktp.get(0).get_nama_jenis_kelamin().toUpperCase());
        gol_darah.setText(get_ktp.get(0).get_nama_golongan_darah().toUpperCase());
        alamat_ktp.setText(get_ktp.get(0).get_alamat_ktp().toUpperCase());
        rt.setText(get_ktp.get(0).get_rt().toUpperCase());
        rw.setText(get_ktp.get(0).get_rw().toUpperCase());
        agama.setText(get_ktp.get(0).get_nama_agama().toUpperCase());
        pendidikan_akhir.setText(get_ktp.get(0).get_nama_pendidikan_terakhir().toUpperCase());
        pekerjaan.setText(get_ktp.get(0).get_nama_pekerjaan().toUpperCase());
        nomor_hp_ktp.setText(get_ktp.get(0).get_nomor_hp_ktp().toUpperCase());
        ket_image.setText(get_ktp.get(0).get_nama_ktp().toUpperCase());
        kodepos.setText(get_ktp.get(0).get_kodepos().toUpperCase());
        email.setText(get_ktp.get(0).get_email().toUpperCase());
        nomor_rumah_ktp.setText(get_ktp.get(0).get_nomor_rumah_ktp().toUpperCase());
        nomor_kantor_ktp.setText(get_ktp.get(0).get_nomor_kantor_ktp().toUpperCase());
        nomor_faksimili_ktp.setText(get_ktp.get(0).get_nomor_faksimili_ktp().toUpperCase());;
        nomor_kta_baru.setText(get_ktp.get(0).get_nik_kta_baru().toUpperCase());
        jabatan.setText(get_ktp.get(0).get_jabatan().toUpperCase());
        fb.setText(get_ktp.get(0).get_fb().toUpperCase());
        ig.setText(get_ktp.get(0).get_ig().toUpperCase());
        twitter.setText(get_ktp.get(0).get_twitter().toUpperCase());
        wa.setText(get_ktp.get(0).get_wa().toUpperCase());
        status_nikah.setText(get_ktp.get(0).get_nama_status_nikah().toUpperCase());
        status_pengurus.setText(get_ktp.get(0).get_nama_status_pengurus().toUpperCase());
        kategori_pengurus.setText(get_ktp.get(0).get_nama_kategori_pengurus().toUpperCase());
        provinsi_asal.setText(get_ktp.get(0).get_provinsi_asal().toUpperCase());
        kabupaten_asal.setText(get_ktp.get(0).get_kabupaten_asal().toUpperCase());
        kecamatan_asal.setText(get_ktp.get(0).get_kecamatan_asal().toUpperCase());
        kelurahan_asal.setText(get_ktp.get(0).get_kelurahan_asal().toUpperCase());
        provinsi_pengurus.setText(get_ktp.get(0).get_provinsi_pengurus().toUpperCase());
        kabupaten_pengurus.setText(get_ktp.get(0).get_kabupaten_pengurus().toUpperCase());
        kecamatan_pengurus.setText(get_ktp.get(0).get_kecamatan_pengurus().toUpperCase());
        kelurahan_pengurus.setText(get_ktp.get(0).get_kelurahan_pengurus().toUpperCase());

        nama_provinsi.setText(get_ktp.get(0).get_provinsi_asal().toUpperCase());
        nama_kabupaten.setText(get_ktp.get(0).get_kabupaten_asal().toUpperCase());

        final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(0xff0000b3)
                        .text1(get_ktp.get(0).get_provinsi_asal().toUpperCase())
                        .textSize(spToPixels(12))
                        .build();
        SpannableString nm_alias = new SpannableString(TextUtils.concat(drawable.toSpannable()));
        nama_provinsi.setText(nm_alias);

        final BadgeDrawable drawable2 =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(0xff990000)
                        .text1(get_ktp.get(0).get_kabupaten_asal().toUpperCase())
                        .textSize(spToPixels(12))
                        .build();

        SpannableString nm_kel =  new SpannableString(TextUtils.concat(drawable2.toSpannable()));
        nama_kabupaten.setText(nm_kel);

        Glide.with(getApplicationContext()).load(foto_ktp)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_id);

        Glide.with(getApplicationContext()).load(foto_pas)
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(img_pas_foto);
    }

    private static float spToPixels(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    private void displayImageID(){
        final ImageView imageView = new ImageView(getApplicationContext());
        imageView.setPadding(0,25,0,0);
        imageView.setImageResource(R.drawable.no_image);
        Glide.with(getApplicationContext()).load(foto_ktp).asBitmap().fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    imageView.setImageBitmap(bitmap);
            }
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Tampilan Foto")
                .setView(imageView)
                .setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public boolean isNetworkAvailable(AppCompatActivity c) {
        boolean state;
        ConnectivityManager cmg = (ConnectivityManager) c.getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cmg.getActiveNetworkInfo();
        state = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (state) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
