package skripsi.code.ektp.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.microblink.MicroblinkSDK;
import com.microblink.activity.DocumentScanActivity;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.image.Image;
import com.microblink.intent.IntentDataTransferMode;
import com.microblink.uisettings.ActivityRunner;
import com.microblink.uisettings.BlinkIdUISettings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import skripsi.code.ektp.R;
import skripsi.code.ektp.data.UrlConfig;


public class ScanMicroblinkActivity extends AppCompatActivity {
    //  Blink ID
    private BlinkIdCombinedRecognizer recognizer;
    private RecognizerBundle recognizerBundle;

    private static final int SCAN_ACTIVITY_REQ_CODE = 234;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    private static final String LOG_TAG = "ScanActivity";

    private ImageView
            imgPreviewID,
            imgPreviewPasFoto;
    private Vibrator
            vib;
    private Animation
            animShake;
    private EditText
            nik,
            nama,
            tempat_lahir,
            tanggal_lahir,
            jenis_kelamin,
            gol_darah,
            alamat,
            rt,
            rw,
            provinsi,
            kabupaten,
            kecamatan,
            kelurahan,
            agama,
            status_kawin,
            pekerjaan,
            kewarganegaraan;
    private LinearLayout
            konfirmasi_data;
    private String
            nama_provinsi="",
            nama_kabupaten="",
            nama_kecamatan="",
            nama_kelurahan="",
            nama_pekerjaan="",
            id_provinsi_asal="",
            id_kabupaten_asal="",
            id_kecamatan_asal="",
            id_kelurahan_asal="",
            id_pekerjaan_anggota="";

    public String
            pasFotoName,
            ktpImgName;

    public boolean status_get = false;

    private ProgressDialog
            pDialog;

    private String tes;

    private MediaPlayer mMediaPlayer = null;

    private ByteArrayOutputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_scan);
        MicroblinkSDK.setLicenseKey("sRwAAAARc2tyaXBzaS5jb2RlLmVrdHD8XmUeS9dATwTCaPHXOu6e3Vx3kpjdEsXsI6uJIk0HZS75tNQAwma4zwg9BFpNv4UCyo6fSSUuwxtSDo4C5PP2KiU/T3umzpEmPmWgQrwdvpTPlfrGNLOx5kQX8PVg3eU/4S493LFdInkyaV7/ow8LrHdTFWxTk4Ih6vh8nZxzNCR6t4x2H9I2hSg80PLtVUgyAq5zn13XkwjXZHaHVM7+gYSF5UmCLoU7DdqGQybjJpk0TmRj3uE419cIdvChiPNSB9NhgyH6N08=", this);
        MicroblinkSDK.setIntentDataTransferMode(IntentDataTransferMode.PERSISTED_OPTIMISED);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        // create recognizer
        recognizer = new BlinkIdCombinedRecognizer();
        // set to true to obtain images containing full document
        recognizer.setReturnFullDocumentImage(true);
        recognizer.setReturnFaceImage(true);

        recognizerBundle = new RecognizerBundle(recognizer);

        BlinkIdUISettings uiSettings = new BlinkIdUISettings(recognizerBundle);
        //enable capturing success frame in full camera resolution
        uiSettings.enableHighResSuccessFrameCapture(true);
        ActivityRunner.startActivityForResult(this, SCAN_ACTIVITY_REQ_CODE, uiSettings);

        stream = new ByteArrayOutputStream();
        byte[] byteArray = stream.toByteArray();

        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        imgPreviewPasFoto = (ImageView) findViewById(R.id.foto);
        imgPreviewID = (ImageView) findViewById(R.id.ktp);

        nik = (EditText) findViewById(R.id.nik_ktp);
        nama = (EditText)findViewById(R.id.nama_anggota);
        tempat_lahir = (EditText) findViewById(R.id.tempat_lahir);
        tanggal_lahir = (EditText) findViewById(R.id.tanggal_lahir);
        jenis_kelamin= (EditText) findViewById(R.id.jenis_kelamin);
        gol_darah= (EditText) findViewById(R.id.gol_darah);
        alamat= (EditText) findViewById(R.id.alamat);
        rt= (EditText) findViewById(R.id.rt);
        rw= (EditText) findViewById(R.id.rw);
        provinsi = (EditText) findViewById(R.id.provinsi);
        kabupaten= (EditText) findViewById(R.id.kabupaten);
        kecamatan= (EditText) findViewById(R.id.kecamatan);
        kelurahan= (EditText) findViewById(R.id.kelurahan);
        agama = (EditText) findViewById(R.id.agama);
        status_kawin= (EditText) findViewById(R.id.status_kawin);
        pekerjaan = (EditText) findViewById(R.id.pekerjaan);
        kewarganegaraan= (EditText) findViewById(R.id.kewarganegaraan);

        konfirmasi_data = (LinearLayout) findViewById(R.id.konfirmasi_data);

        konfirmasi_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(checkForm()) {
//                    pDialog.setMessage("Mengambil data...");
//                    pDialog.show();
//                    setUrlProvinsi(UrlConfig.URL_GET_NAMA_PROVINSI,provinsi.getText().toString().toLowerCase().replaceAll("\\s+",""));
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Masukan data yang sesuai", Toast.LENGTH_SHORT).show();
//                }
                pDialog.setMessage("Mengambil data...");
                pDialog.show();
                setUrlProvinsi(pDialog,UrlConfig.URL_GET_NAMA_PROVINSI,provinsi.getText().toString().toLowerCase().replaceAll("\\s+",""));

            }
        });

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void send(){
        pDialog.dismiss();
        Intent addIntent = new Intent(getApplicationContext(), AddScanKtpActivity.class);
        addIntent.putExtra("nik", nik.getText().toString());
        addIntent.putExtra("nama", nama.getText().toString());
        addIntent.putExtra("tempat_lahir", tempat_lahir.getText().toString());
        addIntent.putExtra("tanggal_lahir", tanggal_lahir.getText().toString());
        addIntent.putExtra("jenis_kelamin", jenis_kelamin.getText().toString());
        addIntent.putExtra("gol_darah", gol_darah.getText().toString());
        addIntent.putExtra("alamat", alamat.getText().toString());
        addIntent.putExtra("rt", rt.getText().toString());
        addIntent.putExtra("rw", rw.getText().toString());
        addIntent.putExtra("provinsi", nama_provinsi);
        addIntent.putExtra("kabupaten", nama_kabupaten);
        addIntent.putExtra("kecamatan", nama_kecamatan);
        addIntent.putExtra("kelurahan", nama_kelurahan);
        addIntent.putExtra("agama", agama.getText().toString());
        addIntent.putExtra("status_kawin", status_kawin.getText().toString());
        addIntent.putExtra("pekerjaan", nama_pekerjaan);
        addIntent.putExtra("kewarganegaraan", kewarganegaraan.getText().toString());
        addIntent.putExtra("id_provinsi", id_provinsi_asal);
        addIntent.putExtra("id_kabupaten", id_kabupaten_asal);
        addIntent.putExtra("id_kecamatan", id_kecamatan_asal);
        addIntent.putExtra("id_kelurahan", id_kelurahan_asal);
        addIntent.putExtra("id_pekerjaan", id_pekerjaan_anggota);
        addIntent.putExtra("pas_foto", pasFotoName);
        addIntent.putExtra("ktp_img", ktpImgName);
        startActivity(addIntent);
        finish();
    }

    private void startScanning() {
        BlinkIdUISettings uiSettings = new BlinkIdUISettings(recognizerBundle);
        //enable capturing success frame in full camera resolution
        ActivityRunner.startActivityForResult(this, SCAN_ACTIVITY_REQ_CODE, uiSettings);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // onActivityResult is called whenever we are returned from activity started
        // with startActivityForResult. We need to check request code to determine
        // that we have really returned from BlinkID activity.
        if (requestCode != SCAN_ACTIVITY_REQ_CODE) {
            return;
        }

        if (resultCode != DocumentScanActivity.RESULT_OK || data == null) {
            // if BlinkID activity did not return result, user has probably
            // pressed Back button and cancelled scanning
            Toast.makeText(this, "Scan cancelled!", Toast.LENGTH_SHORT).show();
            return;
        }

        recognizerBundle.loadFromIntent(data);

        BlinkIdCombinedRecognizer.Result result = recognizer.getResult();

        nik.setText(result.getDocumentNumber());
        nama.setText(result.getFullName());
        tempat_lahir.setText(result.getPlaceOfBirth());
        tanggal_lahir.setText(result.getDateOfBirth().getOriginalDateString());
        jenis_kelamin.setText(result.getSex());
        alamat.setText(result.getAddress());
        provinsi.setText(getProvinsi(result.getAdditionalAddressInformation()));
        kabupaten.setText(getKabupaten(result.getAdditionalAddressInformation()));
        agama.setText(result.getReligion());
        status_kawin.setText(result.getMaritalStatus());
        pekerjaan.setText(result.getProfession());
        kewarganegaraan.setText(result.getNationality());

        storeImagePasFoto(result.getDocumentNumber()+"_pas_foto", result.getFaceImage());
        storeImageKtp(result.getDocumentNumber()+"_ktp_foto", result.getFullDocumentFrontImage());

        mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
        mMediaPlayer.start();

    }

    public void setUrlProvinsi(final ProgressDialog p, String url, String nama_prov){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+nama_prov,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("status");

                            if(error){

                                JSONObject j = jObj.getJSONObject("get_provinsi");

                                String id_provinsi = j.getString("id");
                                String nama_prov = j.getString("nama");

                                if (id_provinsi != null) {
                                    id_provinsi_asal = id_provinsi;
                                    nama_provinsi = nama_prov;
                                    setUrlKabupaten(p,UrlConfig.URL_GET_NAMA_KABUPATEN, id_provinsi_asal, kabupaten.getText().toString().toLowerCase());
                                }

                            } else {
                                p.dismiss();
                                String msg = jObj.getString("message");
                                Toast.makeText(ScanMicroblinkActivity.this,msg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            p.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ScanMicroblinkActivity.this);
        requestQueue.add(stringRequest);
    }

    public void setUrlKabupaten(final ProgressDialog p, String url, String id_prov, String nama_kab){

        StringRequest stringRequest = new StringRequest(url+id_prov+"/nama_kabupaten/"+nama_kab,
                 new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("status");

                            if(error) {

                                JSONObject j = jObj.getJSONObject("get_kabupaten");

                                String id_kabupaten = j.getString("id");
                                String nama_kab = j.getString("nama");

                                if (id_kabupaten != null) {
                                    id_kabupaten_asal = id_kabupaten;
                                    nama_kabupaten = nama_kab;
                                    setUrlKecamatan(p,UrlConfig.URL_GET_NAMA_KECAMATAN, id_provinsi_asal, id_kabupaten_asal, kecamatan.getText().toString().toLowerCase());
                                }
                            }else {
                                p.dismiss();
                                String msg = jObj.getString("message");
                                Toast.makeText(ScanMicroblinkActivity.this,msg, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            p.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ScanMicroblinkActivity.this);
        requestQueue.add(stringRequest);
    }


    public void setUrlKecamatan(final ProgressDialog p,String url, String id_prov,String id_kab, String nama){

        StringRequest stringRequest = new StringRequest(url+id_prov+"/id_kabupaten/"+id_kab+"/nama_kecamatan/"+nama,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("status");


                            if(error) {

                                JSONObject j =jObj.getJSONObject("get_kecamatan");

                                String id_kecamatan = j.getString("id");
                                String nama_kec = j.getString("nama");

                                if (id_kecamatan != null) {
                                    id_kecamatan_asal = id_kecamatan;
                                    nama_kecamatan = nama_kec;
                                    setUrlKelurahan(p, UrlConfig.URL_GET_NAMA_KELURAHAN, id_provinsi_asal, id_kabupaten_asal, id_kecamatan_asal, kelurahan.getText().toString().toLowerCase());

                                }
                            }else {
                                pDialog.dismiss();
                                String msg = jObj.getString("message");
                                Toast.makeText(ScanMicroblinkActivity.this,msg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            p.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ScanMicroblinkActivity.this);
        requestQueue.add(stringRequest);
    }

    public void setUrlKelurahan(final ProgressDialog p, String url,String id_prov,String id_kab, String id_kec, String nama){

        StringRequest stringRequest = new StringRequest(url+id_prov+"/id_kabupaten/"+id_kab+"/id_kecamatan/"+id_kec+"/nama_kelurahan/"+nama,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("status");

                            if(error) {

                                JSONObject j =jObj.getJSONObject("get_kelurahan");

                                String id_kelurahan = j.getString("id");
                                String nama_kel = j.getString("nama");

                                if(id_kelurahan!=null){
                                    p.dismiss();
                                    id_kelurahan_asal = id_kelurahan;
                                    nama_kelurahan = nama_kel;
                                    if(id_provinsi_asal!="" && id_kabupaten_asal!="" && id_kecamatan_asal!="" && id_kelurahan_asal!="" && id_pekerjaan_anggota==""){
                                        send();
                                    }
                                    setUrlPekerjaan(p,UrlConfig.URL_GET_NAMA_PEKERJAAN, pekerjaan.getText().toString().toLowerCase().replaceAll("\\s+",""));
                                }

                            }else {
                                p.dismiss();
                                String msg = jObj.getString("message");
                                Toast.makeText(ScanMicroblinkActivity.this,msg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            p.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ScanMicroblinkActivity.this);
        requestQueue.add(stringRequest);
    }

    public void setUrlPekerjaan(final ProgressDialog p,String url,final String nama){

        StringRequest stringRequest = new StringRequest(url+nama,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("status");

                            if(error){

                                JSONObject j = jObj.getJSONObject("get_pekerjaan");

                                String id_pekerjaan = j.getString("id");
                                String nama_pk = j.getString("job");

                                if(id_pekerjaan!=null) {
                                    p.dismiss();
                                    id_pekerjaan_anggota = id_pekerjaan;
                                    nama_pekerjaan = nama_pk;
                                    if(id_provinsi_asal!="" && id_kabupaten_asal!="" && id_kecamatan_asal!="" && id_kelurahan_asal!="" && id_pekerjaan_anggota!=""){
                                        send();
                                    }
                                }

                            } else {
                                p.dismiss();
                                String msg = jObj.getString("message");
                                Toast.makeText(ScanMicroblinkActivity.this,msg, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            p.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.dismiss();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ScanMicroblinkActivity.this);
        requestQueue.add(stringRequest);
    }


    public String getProvinsi(String prov){

        String raw[] = prov.split("\\r?\\n");
        String[] splited = raw[0].split("\\s+");
        String[] modifiedArray = Arrays.copyOfRange(splited, 1, splited.length);

        prov = TextUtils.join(" ", modifiedArray);

        return prov;
    }

    public String getKabupaten(String kab){

        String raw[] = kab.split("\\r?\\n");
        String[] splited = raw[1].split("\\s+");
        String[] modifiedArray = Arrays.copyOfRange(splited, 1, splited.length);

        kab = TextUtils.join(" ", modifiedArray);

        return kab;
    }


    private void onScanCanceled() {
        Toast.makeText(this, "Scan cancelled!", Toast.LENGTH_SHORT).show();
    }

    private void storeImagePasFoto(String imageName, @Nullable Image image) {
        if (image == null) {
            return;
        }

        String output = createImagesFolder();
        String filename = output + "/" + imageName + ".jpg";
        Bitmap bitmap = image.convertToBitmap();

        if (bitmap.getWidth() >= bitmap.getHeight()){

            bitmap = Bitmap.createBitmap(
                    bitmap,
                    bitmap.getWidth()/2 - bitmap.getHeight()/2,
                    0,
                    bitmap.getHeight(),
                    bitmap.getHeight()
            );

        }else{

            bitmap = Bitmap.createBitmap(
                    bitmap,
                    0,
                    bitmap.getHeight()/2 - bitmap.getWidth()/2,
                    bitmap.getWidth(),
                    bitmap.getWidth()
            );
        }

//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        pasFotoName = imageName;

        imgPreviewPasFoto.setImageBitmap(bitmap);

        if (bitmap == null) {
            Log.e(LOG_TAG, "Bitmap is null");
            return;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            if (!success) {
                Log.e(LOG_TAG, "Failed to compress bitmap!");
                try {
                    fos.close();
                } catch (IOException ignored) {
                } finally {
                    fos = null;
                }
                new File(filename).delete();
            }
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "Failed to save image " + e.toString());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {
                }
            }
        }
        // after this line, image gets disposed. If you want to save it
        // for later, you need to clone it with image.clone()
    }


    private void storeImageKtp(String imageName, @Nullable Image image) {
        if (image == null) {
            return;
        }

        String output = createImagesFolder();
        String filename = output + "/" + imageName + ".jpg";
        Bitmap bitmap = image.convertToBitmap();
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        ktpImgName = imageName;

        imgPreviewID.setImageBitmap(bitmap);

        if (bitmap == null) {
            Log.e(LOG_TAG, "Bitmap is null");
            return;
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            boolean success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            if (!success) {
                Log.e(LOG_TAG, "Failed to compress bitmap!");
                try {
                    fos.close();
                } catch (IOException ignored) {
                } finally {
                    fos = null;
                }
                new File(filename).delete();
            }
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "Failed to save image " + e.toString());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ignored) {
                }
            }
        }
        // after this line, image gets disposed. If you want to save it
        // for later, you need to clone it with image.clone()
    }


    @NonNull
    private String createImagesFolder() {
        // we will save images to 'myImages' folder on external storage
        String imagesDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myImages";
        File f = new File(imagesDirPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        return imagesDirPath;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted
                startScanning();
            } else {
                // permission denied
                Toast.makeText(this, "Write external storage permission is required!", Toast.LENGTH_SHORT).show();
            }
        }
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

    @SuppressLint("ResourceType")
    private boolean checkForm() {

        boolean set = true;

        if(status_kawin.getText().toString().toLowerCase().replaceAll("\\s+","").equals("belumkawin")){

        }else if(status_kawin.getText().toString().toLowerCase().replaceAll("\\s+","").equals("kawin")){

        }else if(status_kawin.getText().toString().toLowerCase().replaceAll("\\s+","").equals("ceraihidup")){

        }else if(status_kawin.getText().toString().toLowerCase().replaceAll("\\s+","").equals("ceraimati")){

        } else {
            Toast.makeText(this, "Inputan Harus (Belum Kawin, Kawin, Cerai Hidup, Cerai Mati, Lainnya)!", Toast.LENGTH_SHORT).show();
            status_kawin.setAnimation(animShake);
            status_kawin.startAnimation(animShake);
            vib.vibrate(120);
            set=false;
        }

        if(agama.getText().toString().toLowerCase().replaceAll("\\s+","").equals("islam")){

        }else if(agama.getText().toString().toLowerCase().replaceAll("\\s+","").equals("kristen")){

        }else if(agama.getText().toString().toLowerCase().replaceAll("\\s+","").equals("hindu")){

        }else if(agama.getText().toString().toLowerCase().replaceAll("\\s+","").equals("budha")){

        }else if(agama.getText().toString().toLowerCase().replaceAll("\\s+","").equals("lainnya")) {

        }else {
            Toast.makeText(this, "Inputan Harus (Islam, Kristen, Hindu, Budha, Lainnya)!", Toast.LENGTH_SHORT).show();
            agama.setAnimation(animShake);
            agama.startAnimation(animShake);
            vib.vibrate(120);
            set=false;
        }

        if(gol_darah.getText().toString().replaceAll("\\s+","").toLowerCase().equals("a")){

        }else if(gol_darah.getText().toString().replaceAll("\\s+","").toLowerCase().equals("b")){

        }else if(gol_darah.getText().toString().replaceAll("\\s+","").toLowerCase().equals("ab")){

        }else if(gol_darah.getText().toString().replaceAll("\\s+","").toLowerCase().equals("o")){

        }else if(gol_darah.getText().toString().replaceAll("\\s+","").toLowerCase().equals("lainnya")){

        } else {
            Toast.makeText(this, "Inputan Harus (A, B, AB, O, Lainnya)!", Toast.LENGTH_SHORT).show();
            gol_darah.setAnimation(animShake);
            gol_darah.startAnimation(animShake);
            vib.vibrate(120);
            set=false;
        }

        if (nik.getText().toString().trim().isEmpty()) {
            nik.setError(getString(R.string.err_msg_nik_ktp));
            nik.setAnimation(animShake);
            nik.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            nik.clearAnimation();
        }

        if (nama.getText().toString().trim().isEmpty()) {
            nama.setError(getString(R.string.err_msg_nama_ktp));
            nama.setAnimation(animShake);
            nama.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            nama.clearAnimation();
        }

        if (jenis_kelamin.getText().toString().trim().isEmpty()) {
            jenis_kelamin.setError(getString(R.string.err_msg_jenis_kelamin));
            jenis_kelamin.setAnimation(animShake);
            jenis_kelamin.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            jenis_kelamin.clearAnimation();
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


        if (gol_darah.getText().toString().trim().isEmpty()) {
            gol_darah.setError(getString(R.string.err_msg_gol_darah));
            gol_darah.setAnimation(animShake);
            gol_darah.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            gol_darah.clearAnimation();
        }


        if (alamat.getText().toString().trim().isEmpty()) {
            alamat.setError(getString(R.string.err_msg_alamat_ktp));
            alamat.setAnimation(animShake);
            alamat.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            alamat.clearAnimation();
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

        if (provinsi.getText().toString().trim().isEmpty()) {
            provinsi.setError(getString(R.string.err_msg_provinsi_asal));
            provinsi.setAnimation(animShake);
            provinsi.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            provinsi.clearAnimation();
        }

        if (kabupaten.getText().toString().trim().isEmpty()) {
            kabupaten.setError(getString(R.string.err_msg_kabupaten_asal));
            kabupaten.setAnimation(animShake);
            kabupaten.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            kabupaten.clearAnimation();
        }

        if (kecamatan.getText().toString().trim().isEmpty()) {
            kecamatan.setError(getString(R.string.err_msg_kecamatan_asal));
            kecamatan.setAnimation(animShake);
            kecamatan.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            kecamatan.clearAnimation();
        }

        if (kelurahan.getText().toString().trim().isEmpty()) {
            kelurahan.setError(getString(R.string.err_msg_kelurahan_asal));
            kelurahan.setAnimation(animShake);
            kelurahan.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            kelurahan.clearAnimation();
        }

        if (agama.getText().toString().trim().isEmpty()) {
            agama.setError(getString(R.string.err_msg_agama));
            agama.setAnimation(animShake);
            agama.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            agama.clearAnimation();
        }

        if (status_kawin.getText().toString().trim().isEmpty()) {
            status_kawin.setError(getString(R.string.err_msg_status_kawin));
            status_kawin.setAnimation(animShake);
            status_kawin.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            status_kawin.clearAnimation();
        }

        if (pekerjaan.getText().toString().trim().isEmpty()) {
            pekerjaan.setError(getString(R.string.err_msg_pekerjaan));
            pekerjaan.setAnimation(animShake);
            pekerjaan.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            pekerjaan.clearAnimation();
        }

        if (kewarganegaraan.getText().toString().trim().isEmpty()) {
            kewarganegaraan.setError(getString(R.string.err_msg_kode_pos));
            kewarganegaraan.setAnimation(animShake);
            kewarganegaraan.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else{
            kewarganegaraan.clearAnimation();
        }

        return set;
    }

}