package skripsi.code.ektp.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import fr.ganfra.materialspinner.MaterialSpinner;
import skripsi.code.ektp.controller.ControllerKtpPetugas;
import skripsi.code.ektp.controller.ControllerPekerjaan;
import skripsi.code.ektp.controller.ControllerWilayahKabupaten;
import skripsi.code.ektp.controller.ControllerWilayahKecamatan;
import skripsi.code.ektp.controller.ControllerWilayahKelurahan;
import skripsi.code.ektp.controller.ControllerWilayahProvinsi;
import skripsi.code.ektp.helper.DatePickerFragment;
import skripsi.code.ektp.model.Ktp;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.BadgeDrawable;
import skripsi.code.ektp.helper.UriHelper;
import skripsi.code.ektp.R;
import skripsi.code.ektp.model.PetugasDB;
import skripsi.code.ektp.view.fragment.ListKtpPetugasFragment;


public class AddScanKtpActivity extends AppCompatActivity {

    private Vibrator
            vib;
    private Animation
            animShake;
    private FloatingActionButton
            saveData;
    private EditText
            nik_ktp,
            nama_ktp,
            tempat_lahir,
            tanggal_lahir,
            alamat_ktp,
            rt,
            rw,
            nomor_hp_ktp,
            imgPathID,
            imgPasFotoID,
            kodepos,
            email,
            nomor_rumah_ktp,
            nomor_kantor_ktp,
            nomor_faksimili_ktp,
            nik_kta_lama,
            jabatan,
            fb,
            ig,
            twitter,
            wa;

    private ArrayAdapter<String>
            provinsi_adapter,
            kabupaten_adapter,
            kecamatan_adapter,
            kelurahan_adapter,

    provinsi_pengurus_adapter,
            kabupaten_pengurus_adapter,
            kecamatan_pengurus_adapter,
            kelurahan_pengurus_adapter,

    pekerjaan_anggota_adapter,
            agama_adapter,
            anggota_pengurus_adapter,
            kategori_pengurus_adapter,
            pendidikan_terakhir_adapter,
            gol_darah_adapter;

    private RadioGroup
            jenis_kelamin,
            status_nikah;

    private RadioButton
            rb_jenis_kelamin;

    private MaterialSpinner
            provinsi,
            kabupaten,
            kecamatan,
            kelurahan,
            provinsi_pengurus,
            kabupaten_pengurus,
            kecamatan_pengurus,
            kelurahan_pengurus,
            pekerjaan_anggota,
            golongan_darah,
            status_pengurus,
            kategori_pengurus,
            pendidikan_terakhir,
            agama;

    private String
            id_provinsi_asal,
            id_kabupaten_asal,
            id_kecamatan_asal,
            id_kelurahan_asal,
            id_pekerjaan_anggota,
            id_petugas,
            id_admin,
            id_jenis_kelamin,
            id_status_nikah,
            id_agama,
            id_gol_darah,
            id_kategori_pengurus,
            id_status_pengurus,
            id_pendidikan_terakhir,
            nama_petugas,
            getPathID,
            getPathPasFoto,
            tanggal_daftar,
            wilayah_pengurus,
            barcode,
            nik_kta_baru,
            status_mutasi;

    private String
            nama_provinsi="",
            nama_kabupaten="",
            nama_kecamatan="",
            nama_kelurahan="",
            id_provinsi_pengurus="",
            id_kabupaten_pengurus="",
            id_kecamatan_pengurus="",
            id_kelurahan_pengurus="";

    private ControllerKtpPetugas
            cKtp;
    private ControllerPekerjaan
            cPekerjaan;

    private ControllerWilayahProvinsi
            cProvinsi,
            cProvinsiPengurus;
    private ControllerWilayahKabupaten
            cKabupaten,
            cKabupatenPengurus;
    private ControllerWilayahKecamatan
            cKecamatan,
            cKecamatanPengurus;
    private ControllerWilayahKelurahan
            cKelurahan,
            cKelurahanPengurus;

    private ProgressDialog
            pDialog;

    private static final int CAMERA_CAPTURE_IMAGE_ID_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE_PAS_FOTO_REQUEST_CODE = 300;

    private static final int IMAGE_GALLERY_ID = 200;
    private static final int IMAGE_GALLERY_PAS_FOTO = 400;

    private static final int REQUEST_OK = 200;

    private Uri
            fileUri;
    private UriHelper
            getUri;
    private Button
            btnImageID,
            btnImagePasFoto;
    private TextView
            petugas;
    private Dialog
            dialog;
    private Bitmap
            bitmapID,
            bitmapPasfoto;
    private ImageView
            imgPreviewID,
            imgPreviewPasFoto;
    private PetugasDB
            db;
    private DatePickerFragment
            tgl_lahir_picker;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private String[] listAgama = {"Islam", "Kristen", "Hindu", "Budha", "Lainnya"};
    private final int[] IDAgama = {1,2,3,4,5};

    private String[] listGolonganDarah = {"A", "B", "AB", "O", "Lainnya"};
    private final int[] IDDarah = {1,2,3,4,5};

    private String[] listAnggotaPengurus = {"YA", "TIDAK"};
    private final int[] IDPengurus = {1,2};

    private String[] listKategoriPengurus = {"DPP", "DPD", "DPC", "PAC", "RANTING"};
    private final int[] IDKategoriPengurus = {1,2,3,4,5};

    private String[] listPendidikanTerakhir = {"TIDAK SEKOLAH", "SD", "SLTP", "SLTA", "D-I/D-II", "D-III", "D-IV/S1", "S2/S3"};
    private final int[] IDPendidikanTerakhir= {1,2,3,4,5,6,7,8};

    private static final String id_petugas_db = "id_petugas";
    private static final String id_admin_db = "id_admin";
    private static final String nama_petugas_db = "nama_petugas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scan_ktp_main);

        db = new PetugasDB(AddScanKtpActivity.this);
        HashMap<String, String> user = db.getUserDetails();
        id_petugas = user.get(id_petugas_db);
        id_admin = user.get(id_admin_db);
        nama_petugas = user.get(nama_petugas_db);

        cKtp = new ControllerKtpPetugas(AddScanKtpActivity.this,UrlConfig.URL_ADD_KTP,null);
        cPekerjaan = new ControllerPekerjaan(AddScanKtpActivity.this, UrlConfig.URL_GET_PEKERJAAN);

        cProvinsi = new ControllerWilayahProvinsi(AddScanKtpActivity.this, UrlConfig.URL_GET_PROVINSI);

        cProvinsiPengurus = new ControllerWilayahProvinsi(AddScanKtpActivity.this, UrlConfig.URL_GET_PROVINSI);

        agama_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, listAgama);
        gol_darah_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, listGolonganDarah);
        kategori_pengurus_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, listKategoriPengurus);
        anggota_pengurus_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, listAnggotaPengurus);
        pendidikan_terakhir_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, listPendidikanTerakhir);

        pekerjaan_anggota_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cPekerjaan.getList());

        provinsi_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cProvinsi.getList());
        provinsi_pengurus_adapter= new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cProvinsiPengurus.getList());

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        getUri = new UriHelper(this);

        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        nik_ktp = (EditText) findViewById(R.id.nik_ktp);
        nama_ktp = (EditText) findViewById(R.id.nama_anggota);
        tempat_lahir = (EditText) findViewById(R.id.tempat_lahir);
        tanggal_lahir = (EditText) findViewById(R.id.tanggal_lahir);
        alamat_ktp = (EditText) findViewById(R.id.alamat_ktp);
        rt = (EditText) findViewById(R.id.rt);
        rw = (EditText) findViewById(R.id.rw);
        nomor_hp_ktp = (EditText) findViewById(R.id.nomor_hp_ktp);
        imgPathID = (EditText) findViewById(R.id.imgPathID);
        imgPasFotoID = (EditText) findViewById(R.id.imgPathPasFoto);
        kodepos = (EditText) findViewById(R.id.kode_pos);
        email = (EditText) findViewById(R.id.email);
        nomor_rumah_ktp = (EditText) findViewById(R.id.nomor_telp_rumah);
        nomor_kantor_ktp = (EditText) findViewById(R.id.nomor_telp_kantor);
        nomor_faksimili_ktp = (EditText) findViewById(R.id.nomor_faksimili);
        nik_kta_lama = (EditText) findViewById(R.id.nomor_kta_lama);
        jabatan = (EditText) findViewById(R.id.jabatan);
        jabatan.setEnabled(false);
        fb = (EditText) findViewById(R.id.facebook);
        ig = (EditText) findViewById(R.id.instagram);
        twitter = (EditText) findViewById(R.id.twitter);
        wa = (EditText) findViewById(R.id.whatsapp);

        tgl_lahir_picker = new DatePickerFragment(AddScanKtpActivity.this, R.id.tanggal_lahir);

        petugas = (TextView) findViewById(R.id.petugas);
        getPetugas(nama_petugas);

        imgPreviewID = (ImageView) findViewById(R.id.imgPreviewID);
        imgPreviewPasFoto = (ImageView) findViewById(R.id.imgPreviewPasFoto);

        jenis_kelamin = (RadioGroup) findViewById(R.id.jenis_kelamin);
        status_nikah = (RadioGroup) findViewById(R.id.status_nikah);

        golongan_darah = (MaterialSpinner)findViewById(R.id.golongan_darah);
        golongan_darah.setAdapter(gol_darah_adapter);
        status_pengurus = (MaterialSpinner)findViewById(R.id.status_pengurus);
        status_pengurus.setAdapter(anggota_pengurus_adapter);
        kategori_pengurus = (MaterialSpinner)findViewById(R.id.kategori_pengurus);
        kategori_pengurus.setAdapter(kategori_pengurus_adapter);
        kategori_pengurus.setEnabled(false);
        pendidikan_terakhir = (MaterialSpinner)findViewById(R.id.pendidikan_akhir);
        pendidikan_terakhir.setAdapter(pendidikan_terakhir_adapter);
        agama = (MaterialSpinner)findViewById(R.id.agama);
        agama.setAdapter(agama_adapter);

        pekerjaan_anggota = (MaterialSpinner)findViewById(R.id.pekerjaan);
        pekerjaan_anggota.setAdapter(pekerjaan_anggota_adapter);

        provinsi = (MaterialSpinner)findViewById(R.id.provinsi_asal);
        provinsi.setAdapter(provinsi_adapter);
        kabupaten = (MaterialSpinner)findViewById(R.id.kabupaten_asal);
        //kabupaten.setAdapter(kabupaten_adapter);
        kecamatan = (MaterialSpinner)findViewById(R.id.kecamatan_asal);
        //kecamatan.setAdapter(kecamatan_adapter);
        kelurahan = (MaterialSpinner)findViewById(R.id.kelurahan_asal);
        //kelurahan.setAdapter(kelurahan_adapter);


        provinsi_pengurus = (MaterialSpinner)findViewById(R.id.provinsi_pengurus);
        provinsi_pengurus.setAdapter(provinsi_pengurus_adapter);
        provinsi_pengurus.setEnabled(false);
        kabupaten_pengurus = (MaterialSpinner)findViewById(R.id.kabupaten_pengurus);
        kabupaten_pengurus.setEnabled(false);
        kecamatan_pengurus = (MaterialSpinner)findViewById(R.id.kecamatan_pengurus);
        kecamatan_pengurus.setEnabled(false);
        kelurahan_pengurus = (MaterialSpinner)findViewById(R.id.kelurahan_pengurus);
        kelurahan_pengurus.setEnabled(false);

        btnImageID = (Button) findViewById(R.id.setImageID);
        btnImagePasFoto = (Button) findViewById(R.id.setImagePasFoto);
        saveData = (FloatingActionButton) findViewById(R.id.fab);

        jenis_kelamin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb_jenis_kelamin = (RadioButton) group.findViewById(checkedId);
                switch(checkedId){
                    case R.id.laki_laki:
                        id_jenis_kelamin="1";
                        Toast.makeText(getBaseContext(), rb_jenis_kelamin.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.perempuan:
                        id_jenis_kelamin="0";
                        Toast.makeText(getBaseContext(), rb_jenis_kelamin.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        final RadioButton rb_st0 = (RadioButton) findViewById(R.id.belum_menikah);
        final RadioButton rb_st1 = (RadioButton) findViewById(R.id.menikah);
        final RadioButton rb_st2 = (RadioButton) findViewById(R.id.cerai_hidup);
        final RadioButton rb_st3 = (RadioButton) findViewById(R.id.cerai_mati);

        rb_st0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                id_status_nikah="0";
                rb_st1.setChecked(false);
                rb_st2.setChecked(false);
                rb_st3.setChecked(false);
                Toast.makeText(getBaseContext(), id_status_nikah.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        rb_st1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                id_status_nikah="1";
                rb_st0.setChecked(false);
                rb_st2.setChecked(false);
                rb_st3.setChecked(false);
                Toast.makeText(getBaseContext(), id_status_nikah.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        rb_st2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                id_status_nikah="2";
                rb_st0.setChecked(false);
                rb_st1.setChecked(false);
                rb_st3.setChecked(false);
                Toast.makeText(getBaseContext(), id_status_nikah.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        rb_st3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                id_status_nikah="3";
                rb_st0.setChecked(false);
                rb_st1.setChecked(false);
                rb_st2.setChecked(false);
                Toast.makeText(getBaseContext(), id_status_nikah.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        pekerjaan_anggota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_pekerjaan_anggota = cPekerjaan.getId(position);
                }  else {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


        provinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_provinsi_asal = cProvinsi.getId(position);
                    cKabupaten = new ControllerWilayahKabupaten(AddScanKtpActivity.this, UrlConfig.URL_GET_KABUPATEN + id_provinsi_asal);
                    kabupaten_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKabupaten.getList());
                    kabupaten.setAdapter(kabupaten_adapter);
                }  else {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        kabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_kabupaten_asal = cKabupaten.getId(position);
                    cKecamatan = new ControllerWilayahKecamatan(AddScanKtpActivity.this, UrlConfig.URL_GET_KECAMATAN + id_provinsi_asal + "/id_kabupaten/" + id_kabupaten_asal);
                    kecamatan_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKecamatan.getList());
                    kecamatan.setAdapter(kecamatan_adapter);
                }  else {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        kecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_kecamatan_asal = cKecamatan.getId(position);
                    cKelurahan = new ControllerWilayahKelurahan(AddScanKtpActivity.this, UrlConfig.URL_GET_KELURAHAN + id_provinsi_asal + "/id_kabupaten/" + id_kabupaten_asal + "/id_kecamatan/" + id_kecamatan_asal);
                    kelurahan_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKelurahan.getList());
                    kelurahan.setAdapter(kelurahan_adapter);
                }  else {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        kelurahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_kelurahan_asal = cKelurahan.getId(position);
                }  else {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


        provinsi_pengurus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_provinsi_pengurus = cProvinsiPengurus.getId(position);

                    cKabupatenPengurus = new ControllerWilayahKabupaten(AddScanKtpActivity.this, UrlConfig.URL_GET_KABUPATEN + id_provinsi_pengurus);
                    kabupaten_pengurus_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKabupatenPengurus.getList());
                    kabupaten_pengurus.setAdapter(kabupaten_pengurus_adapter);
                    kabupaten_pengurus.setSelection(0);
                }  else {
                    id_provinsi_pengurus="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                id_provinsi_pengurus="";
            }
        });
        kabupaten_pengurus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_kabupaten_pengurus = cKabupatenPengurus.getId(position);

                    cKecamatanPengurus = new ControllerWilayahKecamatan(AddScanKtpActivity.this, UrlConfig.URL_GET_KECAMATAN + id_provinsi_pengurus + "/id_kabupaten/" + id_kabupaten_pengurus);
                    kecamatan_pengurus_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKecamatanPengurus.getList());
                    kecamatan_pengurus.setAdapter(kecamatan_pengurus_adapter);
                    kecamatan_pengurus.setSelection(0);
                }  else {
                    id_kabupaten_pengurus="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                id_kabupaten_pengurus="";
            }
        });
        kecamatan_pengurus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_kecamatan_pengurus = cKecamatanPengurus.getId(position);

                    cKelurahanPengurus = new ControllerWilayahKelurahan(AddScanKtpActivity.this, UrlConfig.URL_GET_KELURAHAN + id_provinsi_pengurus + "/id_kabupaten/" + id_kabupaten_pengurus + "/id_kecamatan/" + id_kecamatan_pengurus);
                    kelurahan_pengurus_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKelurahanPengurus.getList());
                    kelurahan_pengurus.setAdapter(kelurahan_pengurus_adapter);
                    kelurahan_pengurus.setSelection(0);
                }  else {
                    id_kecamatan_pengurus="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                id_kecamatan_pengurus="";
            }
        });
        kelurahan_pengurus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position>=0){
                    id_kelurahan_pengurus = cKelurahanPengurus.getId(position);
                }  else {
                    id_kelurahan_pengurus="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                id_kelurahan_pengurus="";
            }
        });


        golongan_darah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position >= 0){
                    int id_d = IDDarah[position];
                    id_gol_darah = Integer.toString(id_d);
                }else {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        status_pengurus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position >= 0){
                    int id_d = IDPengurus[position];
                    id_status_pengurus = Integer.toString(id_d);

                    if(id_d==1){
                        kategori_pengurus.setEnabled(true);
                        jabatan.setEnabled(true);
                    }else{
                        kategori_pengurus.setSelection(0);
                        provinsi_pengurus.setSelection(0);
                        kabupaten_pengurus.setSelection(0);
                        kecamatan_pengurus.setSelection(0);
                        kelurahan_pengurus.setSelection(0);
                        provinsi_pengurus.setEnabled(false);
                        kabupaten_pengurus.setEnabled(false);
                        kecamatan_pengurus.setEnabled(false);
                        kelurahan_pengurus.setEnabled(false);
                        kategori_pengurus.setEnabled(false);
                        jabatan.setText("");
                        jabatan.setEnabled(false);
                    }
                }
                else {
                    id_status_pengurus="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                id_status_pengurus="";
            }
        });

        kategori_pengurus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position >= 0){
                    int id_d = IDKategoriPengurus[position];
                    id_kategori_pengurus = Integer.toString(id_d);

                    if(id_d==1){
                        provinsi_pengurus.setEnabled(false);
                        kabupaten_pengurus.setEnabled(false);
                        kecamatan_pengurus.setEnabled(false);
                        kelurahan_pengurus.setEnabled(false);
                        provinsi_pengurus.setSelection(0);
                        kabupaten_pengurus.setSelection(0);
                        kecamatan_pengurus.setSelection(0);
                        kelurahan_pengurus.setSelection(0);
                    } else if(id_d==2){
                        provinsi_pengurus.setEnabled(true);
                        kabupaten_pengurus.setEnabled(false);
                        kecamatan_pengurus.setEnabled(false);
                        kelurahan_pengurus.setEnabled(false);
                        kabupaten_pengurus.setSelection(0);
                        kecamatan_pengurus.setSelection(0);
                        kelurahan_pengurus.setSelection(0);
                    } else if(id_d==3){
                        provinsi_pengurus.setEnabled(true);
                        kabupaten_pengurus.setEnabled(true);
                        kecamatan_pengurus.setEnabled(false);
                        kelurahan_pengurus.setEnabled(false);
                        kecamatan_pengurus.setSelection(0);
                        kelurahan_pengurus.setSelection(0);
                    } else if(id_d==4){
                        provinsi_pengurus.setEnabled(true);
                        kabupaten_pengurus.setEnabled(true);
                        kecamatan_pengurus.setEnabled(true);
                        kelurahan_pengurus.setEnabled(false);
                        kelurahan_pengurus.setSelection(0);
                    } else if(id_d==5){
                        provinsi_pengurus.setEnabled(true);
                        kabupaten_pengurus.setEnabled(true);
                        kecamatan_pengurus.setEnabled(true);
                        kelurahan_pengurus.setEnabled(true);
                    } else {
                        provinsi_pengurus.setEnabled(false);
                        kabupaten_pengurus.setEnabled(false);
                        kecamatan_pengurus.setEnabled(false);
                        kelurahan_pengurus.setEnabled(false);
                        provinsi_pengurus.setSelection(0);
                        kabupaten_pengurus.setSelection(0);
                        kecamatan_pengurus.setSelection(0);
                        kelurahan_pengurus.setSelection(0);
                    }
                }
                else {
                    id_kategori_pengurus="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                id_kategori_pengurus="";
            }
        });

        pendidikan_terakhir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position >= 0){
                    int id_d = IDPendidikanTerakhir[position];
                    id_pendidikan_terakhir = Integer.toString(id_d);
                }
                else {
                    id_pendidikan_terakhir="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                id_pendidikan_terakhir="";
            }
        });

        agama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position >= 0){
                    int id_a = IDAgama[position];
                    id_agama = Integer.toString(id_a);
                }else {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        btnImageID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialogImgID();
            }
        });

        btnImagePasFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialogPasFoto();
            }
        });

        imgPreviewID.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if(bitmapID!=null){
                    displayImageID();
                } else {
                    dialogImageID();
                }
            }
        });

        imgPreviewPasFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                if(bitmapPasfoto!=null){
                    displayImagePas();
                } else {
                    dialogImagePasFoto();
                }
            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkForm()){
                    if(isNetworkAvailable(AddScanKtpActivity.this)) {
                        saveData();
                        Intent addIntent = new Intent(AddScanKtpActivity.this, ListKtpPetugasFragment.class);
                        setResult(REQUEST_OK, addIntent);
                        finish();
                    } else {
                        Toast.makeText(AddScanKtpActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Masukan data yang sesuai dengan benar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Intent intent = getIntent();
        final String nik_intent = intent.getStringExtra("nik").replaceAll("\\s+","");
        final String nama_intent = intent.getStringExtra("nama");
        final String tempat_lahir_intent = intent.getStringExtra("tempat_lahir");
        final String tanggal_lahir_intent = intent.getStringExtra("tanggal_lahir").replaceAll("\\s+","").replace("-","/");
        final String jenis_kelamin_intent = intent.getStringExtra("jenis_kelamin").replaceAll("\\s+","");
        final String gol_darah_intent = intent.getStringExtra("gol_darah").replaceAll("\\s+","");
        final String alamat_intent = intent.getStringExtra("alamat").replaceAll("\\s+","");
        final String rt_intent = intent.getStringExtra("rt").replaceAll("\\s+","");
        final String rw_intent = intent.getStringExtra("rw").replaceAll("\\s+","");
        final String provinsi_intent = intent.getStringExtra("provinsi");
        final String kabupaten_intent = intent.getStringExtra("kabupaten");
        final String kecamatan_intent = intent.getStringExtra("kecamatan");
        final String kelurahan_intent = intent.getStringExtra("kelurahan");
        final String agama_intent = intent.getStringExtra("agama").replaceAll("\\s+","");
        final String status_kawin_intent = intent.getStringExtra("status_kawin").replaceAll("\\s+","");
        final String pekerjaan_intent = intent.getStringExtra("pekerjaan");
        final String kewarganegaraan_intent = intent.getStringExtra("kewarganegaraan");
        final String id_provinsi_intent = intent.getStringExtra("id_provinsi");
        final String id_kabupaten_intent = intent.getStringExtra("id_kabupaten");
        final String id_kecamatan_intent = intent.getStringExtra("id_kecamatan");
        final String id_kelurahan_intent = intent.getStringExtra("id_kelurahan");
        final String id_pekerjaan_intent = intent.getStringExtra("id_pekerjaan");
        final String pas_foto_intent = intent.getStringExtra("pas_foto");
        final String ktp_img_intent = intent.getStringExtra("ktp_img");

        String path_pas = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myImages/" + pas_foto_intent + ".jpg";
        bitmapPasfoto = BitmapFactory.decodeFile(path_pas);

        String path_id = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myImages/" + ktp_img_intent + ".jpg";
        bitmapID = BitmapFactory.decodeFile(path_id);

        imgPreviewID.setImageBitmap(bitmapID);
        imgPreviewPasFoto.setImageBitmap(bitmapPasfoto);

        nik_ktp.setText(nik_intent);
        nama_ktp.setText(nama_intent);
        tempat_lahir.setText(tempat_lahir_intent);
        tanggal_lahir.setText(tanggal_lahir_intent);
        alamat_ktp.setText(alamat_intent);
        rt.setText(rt_intent);
        rw.setText(rw_intent);

        if(id_provinsi_intent!=null){

            provinsi.setHint(provinsi_intent);
            id_provinsi_asal = id_provinsi_intent;
        }
        if(id_kabupaten_intent!=null){
            cKabupaten = new ControllerWilayahKabupaten(AddScanKtpActivity.this, UrlConfig.URL_GET_KABUPATEN + id_provinsi_asal);
            kabupaten_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKabupaten.getList());
            kabupaten.setAdapter(kabupaten_adapter);

            id_kabupaten_asal = id_kabupaten_intent;
            kabupaten.setHint(kabupaten_intent);
        }
        if(id_kecamatan_intent!=null){
            cKecamatan = new ControllerWilayahKecamatan(AddScanKtpActivity.this, UrlConfig.URL_GET_KECAMATAN + id_provinsi_asal + "/id_kabupaten/" + id_kabupaten_asal);
            kecamatan_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKecamatan.getList());
            kecamatan.setAdapter(kecamatan_adapter);

            id_kecamatan_asal = id_kecamatan_intent;
            kecamatan.setHint(kecamatan_intent);
        }
        if(id_kelurahan_intent!=null){
            cKelurahan = new ControllerWilayahKelurahan(AddScanKtpActivity.this, UrlConfig.URL_GET_KELURAHAN + id_provinsi_asal + "/id_kabupaten/" + id_kabupaten_asal + "/id_kecamatan/" + id_kecamatan_asal);
            kelurahan_adapter = new ArrayAdapter<String>(AddScanKtpActivity.this, R.layout.list_dropdown, cKelurahan.getList());
            kelurahan.setAdapter(kelurahan_adapter);

            id_kelurahan_asal = id_kelurahan_intent;
            kelurahan.setHint(kelurahan_intent);
        }
        if(id_pekerjaan_intent!=null){
            id_pekerjaan_anggota = id_pekerjaan_intent;
            pekerjaan_anggota.setHint(pekerjaan_intent);
            Toast.makeText(getApplicationContext(),
                    kelurahan_intent,
                    Toast.LENGTH_LONG).show();
        }

        RadioButton jk0 = (RadioButton) findViewById(R.id.perempuan);
        RadioButton jk1 = (RadioButton) findViewById(R.id.laki_laki);
        if(jenis_kelamin_intent.toLowerCase().equals("perempuan") || jenis_kelamin_intent.toLowerCase().equals("wanita")){
            jk0.setChecked(true);
            id_jenis_kelamin="0";
        }else if(jenis_kelamin_intent.toLowerCase().equals("laki-laki") || jenis_kelamin_intent.toLowerCase().equals("pria")){
            jk1.setChecked(true);
            id_jenis_kelamin="1";
        }

        RadioButton sn0 = (RadioButton) findViewById(R.id.belum_menikah);
        RadioButton sn1 = (RadioButton) findViewById(R.id.menikah);
        RadioButton sn2 = (RadioButton) findViewById(R.id.cerai_hidup);
        RadioButton sn3 = (RadioButton) findViewById(R.id.cerai_mati);

        if(status_kawin_intent.toLowerCase().equals("belumkawin")){
            sn0.setChecked(true);
            id_status_nikah="0";
        }else if(status_kawin_intent.toLowerCase().equals("kawin")){
            sn1.setChecked(true);
            id_status_nikah="1";
        }else if(status_kawin_intent.toLowerCase().equals("ceraihidup")){
            sn2.setChecked(true);
            id_status_nikah="2";
        }else if(status_kawin_intent.toLowerCase().equals("ceraimati")){
            sn3.setChecked(true);
            id_status_nikah="3";
        }

        if(agama_intent.toLowerCase().equals("islam")){
            agama.setHint("Islam");
            id_agama="1";
        }else if(agama_intent.toLowerCase().equals("kristen")){
            agama.setHint("Kristen");
            id_agama="2";
        }else if(agama_intent.toLowerCase().equals("hindu")){
            agama.setHint("Hindu");
            id_agama="3";
        }else if(agama_intent.toLowerCase().equals("budha")){
            agama.setHint("Budha");
            id_agama="4";
        } else {
            agama.setHint("Lainnya");
            id_agama ="5";
        }

        if(gol_darah_intent.toLowerCase().equals("a")){
            golongan_darah.setHint("A");
            id_gol_darah="1";
        }else if(gol_darah_intent.toLowerCase().equals("b")){
            golongan_darah.setHint("B");
            id_gol_darah="2";
        }else if(gol_darah_intent.toLowerCase().equals("ab")){
            golongan_darah.setHint("AB");
            id_gol_darah="3";
        }else if(gol_darah_intent.toLowerCase().equals("o")){
            golongan_darah.setHint("O");
            id_gol_darah="4";
        } else {
            golongan_darah.setHint("Lainnya");
            id_gol_darah="5";
        }

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void displayImageID(){
        final ImageView imageView = new ImageView(getApplicationContext());
        imageView.setPadding(0,25,0,0);
        imageView.setImageBitmap(bitmapID);

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

    private void displayImagePas(){
        final ImageView imageView = new ImageView(getApplicationContext());
        imageView.setPadding(0,25,0,0);
        imageView.setImageBitmap(bitmapPasfoto);
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


    private void getPetugas(String stat) {
        final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_WITH_TWO_TEXT_COMPLEMENTARY)
                        .badgeColor(0xff990000)
                        .text1("PETUGAS")
                        .text2(stat)
                        .build();
        SpannableString spanStatus = new SpannableString(TextUtils.concat(drawable.toSpannable()));
        petugas.setText(spanStatus);
    }

    private void showDialogImgID() {

        dialog = new Dialog(this);
        dialog.setTitle("Pilih");
        dialog.setContentView(R.layout.dialog_menu);
        Button foto_img_id = (Button)dialog.findViewById(R.id.camera);

        foto_img_id.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                captureImageID();
            }
        });

        Button browser_btn = (Button)dialog.findViewById(R.id.gallery);
        browser_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getGalleryID();
            }
        });
        Button cancel_btn = (Button)dialog.findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showDialogPasFoto() {

        dialog = new Dialog(this);
        dialog.setTitle("Pilih");
        dialog.setContentView(R.layout.dialog_menu);
        Button foto_pas_foto = (Button)dialog.findViewById(R.id.camera);

        foto_pas_foto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                captureImagePasFoto();
            }
        });

        Button browser_btn = (Button)dialog.findViewById(R.id.gallery);
        browser_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getGalleryPasFoto();
            }
        });
        Button cancel_btn = (Button)dialog.findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void dialogImageID(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 100;
        bitmapID = BitmapFactory.decodeFile(getPathID, options);
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setPadding(0,25,0,0);

        if(bitmapID == null){
            imageView.setImageResource(R.drawable.ic_action_maps_satellite);
        }else {
            imageView.setImageBitmap(bitmapID);
        }

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

    private void dialogImagePasFoto(){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 100;
        bitmapPasfoto = BitmapFactory.decodeFile(getPathPasFoto, options);
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setPadding(0,25,0,0);

        if(bitmapPasfoto == null){
            imageView.setImageResource(R.drawable.ic_action_image_photo);
        }else {
            imageView.setImageBitmap(bitmapPasfoto);
        }

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

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            return true;
        } else {
            return false;
        }
    }

    private void captureImageID() {
        dialog.dismiss();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getUri.getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_ID_REQUEST_CODE);
    }

    private void captureImagePasFoto() {
        dialog.dismiss();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getUri.getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_PAS_FOTO_REQUEST_CODE);
    }


    private void getGalleryID() {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_GALLERY_ID);
    }

    private void getGalleryPasFoto() {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_GALLERY_PAS_FOTO);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(new Date());
        String new_file_name = nik_ktp.getText()+"_"+nama_ktp.getText()+"_"+date;

        if (requestCode == CAMERA_CAPTURE_IMAGE_ID_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                imgPathID.setText(new_file_name.replace(" ","_")+".jpg");
                getPathID =fileUri.getPath();
                previewImageID();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "Silahkan memfoto terlebih dahulu", Toast.LENGTH_SHORT)
                        .show();
                imgPathID.setText(null);
            } else {
                imgPathID.setText(null);
                Toast.makeText(getApplicationContext(),
                        "Sorry! Camera not responding", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == IMAGE_GALLERY_ID) {

            if (resultCode == RESULT_OK) {
                imgPathID.setText(new_file_name.replace(" ","_")+".jpg");
                Uri filePath = data.getData();
                getPathID = getUri.getPath(filePath);
                previewImageID();
            } else if (resultCode == RESULT_CANCELED) {
                imgPathID.setText(null);
                Toast.makeText(getApplicationContext(),
                        "Silahkan pilih foto terlebih dahulu", Toast.LENGTH_SHORT)
                        .show();
            } else {
                imgPathID.setText(null);
                Toast.makeText(getApplicationContext(),
                        "Sorry! Gallery not responding", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == CAMERA_CAPTURE_IMAGE_PAS_FOTO_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                imgPasFotoID.setText(new_file_name.replace(" ","_")+".jpg");
                Uri filePath = data.getData();
                getPathPasFoto = getUri.getPath(filePath);
                previewImagePasFoto();
            } else if (resultCode == RESULT_CANCELED) {
                imgPasFotoID.setText(null);
                Toast.makeText(getApplicationContext(),
                        "Silahkan pilih foto terlebih dahulu", Toast.LENGTH_SHORT)
                        .show();
            } else {
                imgPasFotoID.setText(null);
                Toast.makeText(getApplicationContext(),
                        "Sorry! Gallery not responding", Toast.LENGTH_SHORT)
                        .show();
            }
        }   else if (requestCode == IMAGE_GALLERY_PAS_FOTO) {

            if (resultCode == RESULT_OK) {
                imgPasFotoID.setText(new_file_name.replace(" ","_")+".jpg");
                Uri filePath = data.getData();
                getPathPasFoto = getUri.getPath(filePath);
                previewImagePasFoto();
            } else if (resultCode == RESULT_CANCELED) {
                imgPasFotoID.setText(null);
                Toast.makeText(getApplicationContext(),
                        "Silahkan pilih foto terlebih dahulu", Toast.LENGTH_SHORT)
                        .show();
            } else {
                imgPasFotoID.setText(null);
                Toast.makeText(getApplicationContext(),
                        "Sorry! Gallery not responding", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    private void previewImageID() {
        imgPreviewID.setVisibility(View.VISIBLE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize= 50;
        bitmapID = BitmapFactory.decodeFile(getPathID, options);
        imgPreviewID.setImageBitmap(bitmapID);
    }

    private void previewImagePasFoto() {
        imgPreviewPasFoto.setVisibility(View.VISIBLE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize= 50;
        bitmapPasfoto = BitmapFactory.decodeFile(getPathPasFoto, options);
        imgPreviewPasFoto.setImageBitmap(bitmapPasfoto);
    }


    private void saveData() {
        String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault()).format(new Date());
        String tanggal_daftar_value = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        String new_file_name = nik_ktp.getText()+"_"+nama_ktp.getText()+"_"+date;

        String image_id, imageComp_id, image_pas, imageComp_pas, nameImgID, nameImgPas;

        if (bitmapID != null){

            image_id = getUri.getStringOrgImage(bitmapID);
            imageComp_id = getUri.getStringCompImage(bitmapID);
            nameImgID = new_file_name;
        } else {
            image_id = "";
            imageComp_id = "";
            nameImgID = "";
        }

        if (bitmapPasfoto != null){

            image_pas = getUri.getStringOrgImage(bitmapPasfoto);
            imageComp_pas = getUri.getStringCompImage(bitmapPasfoto);
            nameImgPas = new_file_name;
        } else {
            image_pas = "";
            imageComp_pas = "";
            nameImgPas = "";

        }

        final Ktp ktp= new Ktp();

        String id_admin_value = id_admin.trim();
        String id_petugas_value = id_petugas.trim();
        String id_asal_value = id_provinsi_asal.toString()+id_kabupaten_asal.toString()+id_kecamatan_asal.toString()+id_kelurahan_asal.toString();
        String rt_value = rt.getText().toString().trim();
        String rw_value = rw.getText().toString().trim();
        String nik_ktp_value = nik_ktp.getText().toString().trim();
        String nama_ktp_value = nama_ktp.getText().toString().trim();
        String tempat_lahir_value= tempat_lahir.getText().toString().trim();
        String tanggal_lahir_value = tanggal_lahir.getText().toString().trim();
        String jenis_kelamin_value = id_jenis_kelamin.trim();
        String gol_darah_value = id_gol_darah.trim();
        String alamat_ktp_value = alamat_ktp.getText().toString().trim();
        String agama_value = id_agama.trim();
        String status_nikah_value  = id_status_nikah.trim();
        String pekerjaan_value = id_pekerjaan_anggota.toString().trim();
        String nomor_hp_ktp_value = nomor_hp_ktp.getText().toString().trim();
        String kodepos_value = kodepos.getText().toString().trim();
        String pend_terakhir_value = id_pendidikan_terakhir.toString().trim();
        String email_value = email.getText().toString().trim();
        String nomor_rumah_ktp_value = nomor_rumah_ktp.getText().toString().trim();
        String nomor_kantor_ktp_value = nomor_kantor_ktp.getText().toString().trim();
        String nomor_faksimili_ktp_value = nomor_faksimili_ktp.getText().toString().trim();
        String nik_kta_lama_value = nik_kta_lama.getText().toString().trim();
        String pengurus_value = id_status_pengurus.toString().trim();
        String kategori_pengurus_value = id_kategori_pengurus.toString().trim();
        String jabatan_value = jabatan.getText().toString().trim();
        String wilayah_pengurus_value = id_provinsi_pengurus.toString()+id_kabupaten_pengurus.toString()+id_kecamatan_pengurus.toString()+id_kelurahan_pengurus.toString();
        String fb_value = fb.getText().toString().trim();
        String twitter_value = twitter.getText().toString().trim();
        String ig_value = ig.getText().toString().trim();
        String wa_value = wa.getText().toString().trim();

        ktp.set_id_admin(id_admin_value);
        ktp.set_id_petugas(id_petugas_value);
        ktp.set_id_asal(id_asal_value);
        ktp.set_nik_ktp(nik_ktp_value);
        ktp.set_nama_ktp(nama_ktp_value);
        ktp.set_tempat_lahir(tempat_lahir_value);
        ktp.set_tanggal_lahir(tanggal_lahir_value);
        ktp.set_jenis_kelamin(jenis_kelamin_value);
        ktp.set_gol_darah(gol_darah_value);
        ktp.set_alamat_ktp(alamat_ktp_value);
        ktp.set_rt(rt_value);
        ktp.set_rw(rw_value);
        ktp.set_agama(agama_value);
        ktp.set_status_nikah(status_nikah_value);
        ktp.set_pekerjaan(pekerjaan_value);
        ktp.set_nomor_hp_ktp(nomor_hp_ktp_value);
        ktp.set_img(image_id);
        ktp.set_img_thumb(imageComp_id);
        ktp.set_kodepos(kodepos_value);
        ktp.set_pend_terakhir(pend_terakhir_value);
        ktp.set_email(email_value);
        ktp.set_nomor_rumah_ktp(nomor_rumah_ktp_value);
        ktp.set_nomor_kantor_ktp(nomor_kantor_ktp_value);
        ktp.set_nomor_faksimili_ktp(nomor_faksimili_ktp_value);
        ktp.set_tanggal_daftar(tanggal_daftar_value);
        ktp.set_nik_kta_lama(nik_kta_lama_value);
        ktp.set_pengurus(pengurus_value);
        ktp.set_kategori_pengurus(kategori_pengurus_value);
        ktp.set_jabatan(jabatan_value);
        ktp.set_wilayah_pengurus(wilayah_pengurus_value);
        ktp.set_fb(fb_value);
        ktp.set_twitter(twitter_value);
        ktp.set_ig(ig_value);
        ktp.set_wa(wa_value);
        ktp.set_img_pas(image_pas);
        ktp.set_img_pas_thumb(imageComp_pas);
        ktp.set_nama_img_ktp(nameImgID);
        ktp.set_nama_img_pas_foto(nameImgPas);

        cKtp.createKtp(pDialog,ktp);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
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

    @SuppressLint("ResourceType")
    private boolean checkForm() {

        boolean set = true;

        RadioButton rb_jk0 = (RadioButton) findViewById(R.id.laki_laki);
        RadioButton rb_jk1 = (RadioButton) findViewById(R.id.perempuan);
        if (jenis_kelamin.getCheckedRadioButtonId()<=0) {

            rb_jk0.setError("");
            rb_jk1.setError("");
            rb_jk0.setAnimation(animShake);
            rb_jk0.startAnimation(animShake);
            rb_jk1.setAnimation(animShake);
            rb_jk1.startAnimation(animShake);
            vib.vibrate(120);
            set = false;

        } else {
            rb_jk0.setError(null);
            rb_jk0.clearAnimation();
            rb_jk1.setError(null);
            rb_jk1.clearAnimation();
        }

        RadioButton rb_st0 = (RadioButton) findViewById(R.id.belum_menikah);
        RadioButton rb_st1 = (RadioButton) findViewById(R.id.menikah);
        RadioButton rb_st2 = (RadioButton) findViewById(R.id.cerai_hidup);
        RadioButton rb_st3 = (RadioButton) findViewById(R.id.cerai_mati);

        if (id_status_nikah == null) {
            //set error
            rb_st0.setError("");
            rb_st1.setError("");
            rb_st2.setError("");
            rb_st3.setError("");
            //set anim
            rb_st0.setAnimation(animShake);
            rb_st0.startAnimation(animShake);
            rb_st1.setAnimation(animShake);
            rb_st1.startAnimation(animShake);
            rb_st2.setAnimation(animShake);
            rb_st2.startAnimation(animShake);
            rb_st3.setAnimation(animShake);
            rb_st3.startAnimation(animShake);
            vib.vibrate(120);
            set= false;

        } else {
            rb_st0.setError(null);
            rb_st0.clearAnimation();
            rb_st1.setError(null);
            rb_st1.clearAnimation();
            rb_st2.setError(null);
            rb_st2.clearAnimation();
            rb_st3.setError(null);
            rb_st3.clearAnimation();
        }

        if (nik_ktp.getText().toString().trim().isEmpty()) {
            nik_ktp.setError(getString(R.string.err_msg_nik_ktp));
            nik_ktp.setAnimation(animShake);
            nik_ktp.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            nik_ktp.clearAnimation();
        }

        if (nama_ktp.getText().toString().trim().isEmpty()) {
            nama_ktp.setError(getString(R.string.err_msg_nama_ktp));
            nama_ktp.setAnimation(animShake);
            nama_ktp.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            nama_ktp.clearAnimation();
        }

        if (tempat_lahir.getText().toString().trim().isEmpty()) {
            tempat_lahir.setError(getString(R.string.err_msg_tempat_lahir));
            tempat_lahir.setAnimation(animShake);
            tempat_lahir.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            tempat_lahir.clearAnimation();
        }

        if (tanggal_lahir.getText().toString().trim().isEmpty()) {
            tanggal_lahir.setError(getString(R.string.err_msg_tanggal_lahir));
            tanggal_lahir.setAnimation(animShake);
            tanggal_lahir.startAnimation(animShake);
            vib.vibrate(120);
            set=false;
        }else {
            tanggal_lahir.clearAnimation();
        }

        if (alamat_ktp.getText().toString().trim().isEmpty()) {
            alamat_ktp.setError(getString(R.string.err_msg_alamat_ktp));
            alamat_ktp.setAnimation(animShake);
            alamat_ktp.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            nomor_hp_ktp.clearAnimation();
        }

        if (rt.getText().toString().trim().isEmpty()) {
            rt.setError(getString(R.string.err_msg_rt));
            rt.setAnimation(animShake);
            rt.startAnimation(animShake);
            vib.vibrate(120);
            set=false;
        }else{
            rt.clearAnimation();
        }

        if (rw.getText().toString().trim().isEmpty()) {
            rw.setError(getString(R.string.err_msg_rw));
            rw.setAnimation(animShake);
            rw.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            rw.clearAnimation();
        }

        if (id_pekerjaan_anggota==null || id_pekerjaan_anggota=="") {
            pekerjaan_anggota.setError(getString(R.string.err_msg_pekerjaan));
            pekerjaan_anggota.setAnimation(animShake);
            pekerjaan_anggota.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            pekerjaan_anggota.clearAnimation();
        }

        if (id_gol_darah==null || id_gol_darah=="") {
            golongan_darah.setError(getString(R.string.err_msg_gol_darah));
            golongan_darah.setAnimation(animShake);
            golongan_darah.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            golongan_darah.clearAnimation();
        }
        if (id_agama==null || id_agama=="") {
            agama.setError(getString(R.string.err_msg_agama));
            agama.setAnimation(animShake);
            agama.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            agama.clearAnimation();
        }

        if (id_status_pengurus==null || id_status_pengurus=="") {
            status_pengurus.setError(getString(R.string.err_msg_status_pengurus));
            status_pengurus.setAnimation(animShake);
            status_pengurus.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            status_pengurus.clearAnimation();
        }

        if (imgPasFotoID.getText().toString().trim().isEmpty() && bitmapPasfoto==null) {
            imgPasFotoID.setError(getString(R.string.err_msg_pas_foto));
            imgPasFotoID.setAnimation(animShake);
            imgPasFotoID.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            imgPasFotoID.clearAnimation();
        }

        if (id_pendidikan_terakhir==null || id_pendidikan_terakhir=="") {
            pendidikan_terakhir.setError(getString(R.string.err_msg_pendidikan_akhir));
            pendidikan_terakhir.setAnimation(animShake);
            pendidikan_terakhir.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            pendidikan_terakhir.clearAnimation();
        }

        if (kodepos.getText().toString().trim().isEmpty()) {
            kodepos.setError(getString(R.string.err_msg_kode_pos));
            kodepos.setAnimation(animShake);
            kodepos.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            kodepos.clearAnimation();
        }

        if (email.getText().toString().trim().isEmpty()) {
            email.setError(getString(R.string.err_msg_email));
            email.setAnimation(animShake);
            email.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            email.clearAnimation();
        }

        if (id_provinsi_asal==null || id_provinsi_asal=="") {
            provinsi.setError(getString(R.string.err_msg_provinsi_asal));
            provinsi.setAnimation(animShake);
            provinsi.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            provinsi.clearAnimation();
        }
        if (id_kabupaten_asal==null || id_kabupaten_asal=="") {
            kabupaten.setError(getString(R.string.err_msg_kabupaten_asal));
            kabupaten.setAnimation(animShake);
            kabupaten.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            kabupaten.clearAnimation();
        }
        if (id_kecamatan_asal==null || id_kecamatan_asal=="") {
            kecamatan.setError(getString(R.string.err_msg_kecamatan_asal));
            kecamatan.setAnimation(animShake);
            kecamatan.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            kecamatan.clearAnimation();
        }

        if (id_kelurahan_asal==null || id_kelurahan_asal=="") {
            kelurahan.setError(getString(R.string.err_msg_kelurahan_asal));
            kelurahan.setAnimation(animShake);
            kelurahan.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            kelurahan.clearAnimation();
        }


        if (id_kategori_pengurus.equals("2") || id_kategori_pengurus.equals("3") || id_kategori_pengurus.equals("4") || id_kategori_pengurus.equals("5")) {

            if (id_provinsi_pengurus == null || id_provinsi_pengurus == "") {
                provinsi_pengurus.setError(getString(R.string.err_msg_provinsi_pengurus));
                provinsi_pengurus.setAnimation(animShake);
                provinsi_pengurus.startAnimation(animShake);
                vib.vibrate(120);
                set = false;
            } else {
                provinsi_pengurus.clearAnimation();
            }
        }
        if (id_kategori_pengurus.equals("3") || id_kategori_pengurus.equals("4") || id_kategori_pengurus.equals("5")) {

            if (id_kabupaten_pengurus == null || id_kabupaten_pengurus == "") {
                kabupaten_pengurus.setError(getString(R.string.err_msg_kabupaten_pengurus));
                kabupaten_pengurus.setAnimation(animShake);
                kabupaten_pengurus.startAnimation(animShake);
                vib.vibrate(120);
                set = false;
            } else {
                kabupaten_pengurus.clearAnimation();
            }

        }
        if (id_kategori_pengurus.equals("4") || id_kategori_pengurus.equals("5")) {

            if (id_kecamatan_pengurus == null || id_kecamatan_pengurus == "") {
                kecamatan_pengurus.setError(getString(R.string.err_msg_kecamatan_pengurus));
                kecamatan_pengurus.setAnimation(animShake);
                kecamatan_pengurus.startAnimation(animShake);
                vib.vibrate(120);
                set = false;
            } else {
                kecamatan_pengurus.clearAnimation();
            }
        }
        if (id_kategori_pengurus.equals("5")) {

            if (id_kelurahan_pengurus == null || id_kelurahan_pengurus == "") {
                kelurahan_pengurus.setError(getString(R.string.err_msg_kelurahan_pengurus));
                kelurahan_pengurus.setAnimation(animShake);
                kelurahan_pengurus.startAnimation(animShake);
                vib.vibrate(120);
                set = false;
            } else {
                kelurahan_pengurus.clearAnimation();
            }
        }

        if (id_status_pengurus=="1" || id_status_pengurus.equals("1")) {

            if (id_kategori_pengurus == null || id_kategori_pengurus=="") {
                kategori_pengurus.setError(getString(R.string.err_msg_kategori_pengurus));
                kategori_pengurus.setAnimation(animShake);
                kategori_pengurus.startAnimation(animShake);
                vib.vibrate(120);
                set = false;
            } else {
                kategori_pengurus.clearAnimation();
            }

            if (jabatan.getText().toString().trim().isEmpty()) {
                jabatan.setError(getString(R.string.err_msg_kelurahan_asal));
                jabatan.setAnimation(animShake);
                jabatan.startAnimation(animShake);
                vib.vibrate(120);
                set = false;
            } else {
                jabatan.clearAnimation();
            }
        }

        if (imgPathID.getText().toString().trim().isEmpty() && bitmapID==null) {
            imgPathID.setError(getString(R.string.err_msg_foto_id));
            imgPathID.setAnimation(animShake);
            imgPathID.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            imgPathID.clearAnimation();
        }

        return set;
    }

}