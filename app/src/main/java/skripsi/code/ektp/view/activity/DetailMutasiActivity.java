package skripsi.code.ektp.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import skripsi.code.ektp.R;
import skripsi.code.ektp.controller.ControllerKtpMutasi;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.BadgeDrawable;
import skripsi.code.ektp.model.Mutasi;

public class DetailMutasiActivity extends AppCompatActivity {

    private ControllerKtpMutasi
            cMutasi;

    private ProgressDialog
            pDialog;

    private TextView
            nama_anggota,
            nik_ktp,
            nomor_hp,
            keterangan,
            status_pengurus,
            status_mutasi,
            no_kta_lama,
            no_kta_baru,
            tgl_pengajuan_mutasi,
            tgl_konfirmasi_mutasi,
            provinsi_asal,
            kabupaten_asal,
            provinsi_tujuan,
            kabupaten_tujuan;
    private String
            id_status_mutasi;
    private LinearLayout
            konfirmasi_mutasi,
            tolak_mutasi;

    private AlphaAnimation layoutClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mutasi_main);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        cMutasi = new ControllerKtpMutasi(DetailMutasiActivity.this, UrlConfig.URL_GET_DETAIL_MUTASI,null);

        nama_anggota = (TextView) findViewById(R.id.nama_anggota);
        nik_ktp = (TextView) findViewById(R.id.nik_ktp);
        nomor_hp = (TextView) findViewById(R.id.nomor_hp);
        keterangan = (TextView) findViewById(R.id.keterangan);
        status_pengurus = (TextView) findViewById(R.id.pengurus);
        status_mutasi = (TextView) findViewById(R.id.status_mutasi);
        no_kta_lama = (TextView) findViewById(R.id.nomor_kta_lama);
        no_kta_baru = (TextView) findViewById(R.id.nomor_kta_baru);
        tgl_pengajuan_mutasi = (TextView) findViewById(R.id.tanggal_pengajuan_mutasi);
        tgl_konfirmasi_mutasi = (TextView) findViewById(R.id.tanggal_penerimaan_mutasi);
        provinsi_asal = (TextView) findViewById(R.id.provinsi_asal);
        kabupaten_asal = (TextView) findViewById(R.id.kabupaten_asal);
        provinsi_tujuan = (TextView) findViewById(R.id.provinsi_tujuan);
        kabupaten_tujuan = (TextView) findViewById(R.id.kabupaten_tujuan);

        konfirmasi_mutasi = (LinearLayout) findViewById(R.id.konfirmasi_mutasi);
        tolak_mutasi = (LinearLayout) findViewById(R.id.tolak_mutasi);

        Intent intent = getIntent();
        final String id_mutasi = intent.getStringExtra("id_mutasi");
        final String type = intent.getStringExtra("type");

        konfirmasi_mutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(layoutClick);
                konfirmasiDialog(pDialog,id_mutasi);
            }
        });

        tolak_mutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(layoutClick);

            }
        });

        if(isNetworkAvailable(this)) {
            getDetailMutasi();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        if(type.equals("masuk") && id_status_mutasi.equals("1")){

            konfirmasi_mutasi.setVisibility(LinearLayout.VISIBLE);
            tolak_mutasi.setVisibility(LinearLayout.GONE);
        } else {

            konfirmasi_mutasi.setVisibility(LinearLayout.GONE);
            tolak_mutasi.setVisibility(LinearLayout.GONE);
        }

    }

    public void konfirmasiDialog(final ProgressDialog pd, final String mut_id){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Konfirmasi Mutasi...");
        alertDialog.setMessage("Apakah anda yakin mengkonfirmasi anggota ( "+nama_anggota.getText().toString()+" ) ?");
        alertDialog.setIcon(R.drawable.ic_action_action_report_problem);
        alertDialog.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                cMutasi.konfirmasiKtpMutasi(pd, mut_id,"detail");
            }
        });
        alertDialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Dibatalkan", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void getDetailMutasi(){

        final ArrayList<Mutasi> get_mutasi = (ArrayList<Mutasi>) getIntent().getSerializableExtra("getdetail");

        id_status_mutasi = get_mutasi.get(0).get_status_mutasi();

        nama_anggota.setText(get_mutasi.get(0).get_nama_anggota().toUpperCase());
        nik_ktp.setText(get_mutasi.get(0).get_nik_ktp().toUpperCase());
        nomor_hp.setText(get_mutasi.get(0).get_nomor_hp().toUpperCase());
        keterangan.setText(get_mutasi.get(0).get_keterangan().toUpperCase());
        status_pengurus.setText(get_mutasi.get(0).get_nama_status_pengurus().toUpperCase());
        status_mutasi.setText(get_mutasi.get(0).get_status_mutasi().toUpperCase());
        no_kta_lama.setText(get_mutasi.get(0).get_no_kta_lama().toUpperCase());
        no_kta_baru.setText(get_mutasi.get(0).get_no_kta_baru().toUpperCase());
        tgl_pengajuan_mutasi.setText(get_mutasi.get(0).get_tgl_pengajuan_mutasi().toUpperCase());
        tgl_konfirmasi_mutasi.setText(get_mutasi.get(0).get_tgl_konfirmasi_mutasi().toUpperCase());
        provinsi_asal.setText(get_mutasi.get(0).get_provinsi_asal().toUpperCase());
        kabupaten_asal.setText(get_mutasi.get(0).get_kabupaten_asal().toUpperCase());
        provinsi_tujuan.setText(get_mutasi.get(0).get_provinsi_tujuan().toUpperCase());
        kabupaten_tujuan.setText(get_mutasi.get(0).get_kabupaten_tujuan() .toUpperCase());

        final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(android.graphics.Color.parseColor("#36bea6"))
                        .text1(get_mutasi.get(0).get_provinsi_asal().toUpperCase())
                        .textSize(spToPixels(12))
                        .build();
        SpannableString prov_asal = new SpannableString(TextUtils.concat(drawable.toSpannable()));
        provinsi_asal.setText(prov_asal);


        if(get_mutasi.get(0).get_kabupaten_asal().equals("") || get_mutasi.get(0).get_kabupaten_asal()==""){

        } else {
            final BadgeDrawable drawable2 =
                    new BadgeDrawable.Builder()
                            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                            .badgeColor(android.graphics.Color.parseColor("#009efb"))
                            .text1(get_mutasi.get(0).get_kabupaten_asal().toUpperCase())
                            .textSize(spToPixels(12))
                            .build();

            SpannableString kab_asal = new SpannableString(TextUtils.concat(drawable2.toSpannable()));
            kabupaten_asal.setText(kab_asal);
        }

        final BadgeDrawable drawable3 =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(android.graphics.Color.parseColor("#36bea6"))
                        .text1(get_mutasi.get(0).get_provinsi_tujuan().toUpperCase())
                        .textSize(spToPixels(12))
                        .build();
        SpannableString prov_tujuan = new SpannableString(TextUtils.concat(drawable3.toSpannable()));
        provinsi_tujuan.setText(prov_tujuan);

        if(get_mutasi.get(0).get_kabupaten_tujuan().equals("") || get_mutasi.get(0).get_kabupaten_tujuan()==""){

        } else {

            final BadgeDrawable drawable4 =
                    new BadgeDrawable.Builder()
                            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                            .badgeColor(android.graphics.Color.parseColor("#009efb"))
                            .text1(get_mutasi.get(0).get_kabupaten_tujuan().toUpperCase())
                            .textSize(spToPixels(12))
                            .build();

            SpannableString kab_tujuan = new SpannableString(TextUtils.concat(drawable4.toSpannable()));
            kabupaten_tujuan.setText(kab_tujuan);
        }

        final BadgeDrawable drawable6 =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(0xff0000b3)
                        .text1(get_mutasi.get(0).get_nama_status_mutasi().toUpperCase())
                        .textSize(spToPixels(12))
                        .build();
        SpannableString status_mut = new SpannableString(TextUtils.concat(drawable6.toSpannable()));
        status_mutasi.setText(status_mut);

    }

    private static float spToPixels(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
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
