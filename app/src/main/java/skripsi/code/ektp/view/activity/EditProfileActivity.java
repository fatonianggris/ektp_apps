package skripsi.code.ektp.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import skripsi.code.ektp.controller.ControllerPetugas;
import skripsi.code.ektp.model.Petugas;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.AppRequest;
import skripsi.code.ektp.helper.BadgeDrawable;
import skripsi.code.ektp.helper.ConnectivityReceiver;
import skripsi.code.ektp.model.PetugasDB;
import skripsi.code.ektp.helper.UriHelper;
import skripsi.code.ektp.R;


public class EditProfileActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener   {

    private Vibrator
            vib;
    private Animation
            animShake;
    private EditText
            nama_petugas,
            nomor_ktp,
            email_petugas,
            nomor_hp,
            alamat_petugas,
            inputPassword,
            inputConfPassword;
    private ControllerPetugas
            cProfile;
    private TextView
            status_data,
            tanggal_post;
    private ProgressDialog
            pDialog;
    private FloatingActionButton
            saveData;
    private PetugasDB
            db;
    private Dialog
            dialog;
    private ImageView
            imgProfile;
    private Bitmap
            bitmap;
    private Uri
            fileUri;
    private UriHelper
            getUri;
    private String
            id_petugas,
            getPath,
            foto_ktp,
            foto_ktp_thumb,
            path_foto;

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int IMAGE_GALLERY = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_main);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        cProfile = new ControllerPetugas(EditProfileActivity.this, UrlConfig.URL_UPDATE_PROFILE);

        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        nama_petugas = (EditText) findViewById(R.id.nama_petugas);
        nomor_ktp = (EditText) findViewById(R.id.nomor_ktp);
        email_petugas = (EditText) findViewById(R.id.email_petugas);
        nomor_hp = (EditText) findViewById(R.id.nomor_hp);
        alamat_petugas = (EditText) findViewById(R.id.alamat_petugas);
        inputPassword = (EditText) findViewById(R.id.password);
        inputConfPassword= (EditText) findViewById(R.id.conf_password);

        status_data = (TextView) findViewById(R.id.status_data);
        tanggal_post = (TextView)findViewById(R.id.tanggal_post);

        imgProfile = (ImageView) findViewById(R.id.img_profile);

        db = new PetugasDB(getApplicationContext());

        getUri = new UriHelper(this);

        saveData = (FloatingActionButton) findViewById(R.id.fab);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkForm()){
                    if(isNetworkAvailable(EditProfileActivity.this)) {
                        if(inputPassword.getText().toString().equals(inputConfPassword.getText().toString())){
                            updateData();
                        } else{
                            Toast.makeText(EditProfileActivity.this, "Konfirmasi password salah", Toast.LENGTH_SHORT).show();
                            inputConfPassword.setError(getString(R.string.err_msg_pass_same));
                            inputConfPassword.setAnimation(animShake);
                            inputConfPassword.startAnimation(animShake);
                            vib.vibrate(120);
                        }
                    } else {
                        Toast.makeText(EditProfileActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Masukan data yang sesuai dengan benar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                dialogImage();
            }
        });

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        if(isNetworkAvailable(this)) {
            getDataProfile();
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }

        checkConnection();
    }

    private void getDataProfile(){
        final ArrayList<Petugas> prof = (ArrayList<Petugas>) getIntent().getSerializableExtra("getprofile");
        String status;

        nama_petugas.setText(prof.get(0).get_nama_petugas().toUpperCase());
        nomor_ktp.setText(prof.get(0).get_nomor_ktp());
        email_petugas.setText(prof.get(0).get_email_petugas());
        nomor_hp.setText(prof.get(0).get_nomor_hp());
        alamat_petugas.setText(prof.get(0).get_alamat_petugas());
        id_petugas = prof.get(0).get_id_petugas();
        foto_ktp_thumb = prof.get(0).get_img_thumb();
        foto_ktp = prof.get(0).get_img();
        tanggal_post.setText(prof.get(0).get_nama_petugas()+", dipekerjakan "+prof.get(0).get_tanggal_post());
        path_foto = prof.get(0).get_img();

        if(prof.get(0).get_status_data().equals("1")){
            status = "Aktif";
        }else{
            status = "Non Aktif";
        }
        final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_WITH_TWO_TEXT_COMPLEMENTARY)
                        .badgeColor(0xff0000b3)
                        .text1("STATUS")
                        .text2(status)
                        .build();
        SpannableString spanStatus = new SpannableString(TextUtils.concat(drawable.toSpannable()));
        status_data.setText(spanStatus);

        imgProfile.setImageResource(R.drawable.no_user);
        Glide.with(getApplicationContext()).load(UrlConfig.MAIN_URL+foto_ktp_thumb).asBitmap().fitCenter().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                circleBitmap(bitmap);
            }
        });
    }


    private void circleBitmap(Bitmap bp){

        Bitmap circleBitmap = Bitmap.createBitmap(bp.getWidth(), bp.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bp.getWidth() / 3, bp.getHeight() / 2, bp.getWidth() /1, paint);
        imgProfile.setImageBitmap(circleBitmap);
    }

    private boolean checkForm() {

        boolean set = true;

        if (nama_petugas.getText().toString().trim().isEmpty()) {
            nama_petugas.setError(getString(R.string.err_msg_nama_petugas));
            nama_petugas.setAnimation(animShake);
            nama_petugas.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            nama_petugas.clearAnimation();
        }
        if (nomor_ktp.getText().toString().trim().isEmpty()) {
            nomor_ktp.setError(getString(R.string.err_msg_nomor_ktp));
            nomor_ktp.setAnimation(animShake);
            nomor_ktp.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            nomor_ktp.clearAnimation();
        }
        if (email_petugas.getText().toString().trim().isEmpty()) {
            email_petugas.setError(getString(R.string.err_msg_email_petugas));
            email_petugas.setAnimation(animShake);
            email_petugas.startAnimation(animShake);
            vib.vibrate(120);
            set = false;
        } else {
            email_petugas.clearAnimation();
        }
        if (nomor_hp.getText().toString().trim().isEmpty()) {
            nomor_hp.setError(getString(R.string.err_msg_nomor_hp));
            nomor_hp.setAnimation(animShake);
            nomor_hp.startAnimation(animShake);
            vib.vibrate(120);
            set=false;
        }else {
            nomor_hp.clearAnimation();
        }
        if (alamat_petugas.getText().toString().trim().isEmpty()) {
            alamat_petugas.setError(getString(R.string.err_msg_alamat_petugas));
            alamat_petugas.setAnimation(animShake);
            alamat_petugas.startAnimation(animShake);
            vib.vibrate(120);
            set=false;
        }else {
            alamat_petugas.clearAnimation();
        }
        if (!inputPassword.getText().toString().trim().isEmpty()) {
            if (inputConfPassword.getText().toString().trim().isEmpty()) {
                inputConfPassword.setError(getString(R.string.err_msg_pass_conf));
                inputConfPassword.setAnimation(animShake);
                inputConfPassword.startAnimation(animShake);
                vib.vibrate(120);
                set = false;
            } else {
                inputConfPassword.clearAnimation();
            }
        }else{
            inputConfPassword.clearAnimation();
            inputConfPassword.setError(null);
        }
        return set;
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

    private void dialogImage() {
        dialog = new Dialog(this);
        dialog.setTitle("Pilih");
        dialog.setContentView(R.layout.dialog_menu);
        Button game_btn = (Button)dialog.findViewById(R.id.camera);
        game_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
        Button browser_btn = (Button)dialog.findViewById(R.id.gallery);
        browser_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getGallery();
            }
        });
        Button cancel_btn = (Button)dialog.findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void captureImage() {
        dialog.dismiss();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getUri.getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    private void getGallery() {
        dialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_GALLERY);
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

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getPath=fileUri.getPath();
                previewImage();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "Silahkan mengambil foto terlebih dahulu", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Camera not responding", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == IMAGE_GALLERY) {
            if (resultCode == RESULT_OK) {
                Uri filePath = data.getData();
                getPath = getUri.getPath(filePath);
                previewImage();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "Silahkan pilih foto terlebih dahulu", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Gallery not responding", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void previewImage() {
        imgProfile.setVisibility(View.VISIBLE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize= 8;
        bitmap = BitmapFactory.decodeFile(getPath, options);
        circleBitmap(bitmap);
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

    private void updateData() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String image,imageComp,nameImg,pass;

        if(bitmap!=null){
            image = getUri.getStringOrgImage(bitmap);
            imageComp = getUri.getStringCompImage(bitmap);
            nameImg = "IMG_" + timeStamp;
        } else {
            image = foto_ktp;
            imageComp = foto_ktp_thumb;
            nameImg = "";
        }

        if(inputPassword.getText().toString().trim().isEmpty()){
            pass = "";
        } else{
            pass = inputPassword.getText().toString();
        }

        String id_petugas_value = id_petugas.toString().trim();
        String nama_petugas_value = nama_petugas.getText().toString().trim();
        String nomor_ktp_value = nomor_ktp.getText().toString().trim();
        String email_petugas_value = email_petugas.getText().toString().trim();
        String nomor_hp_value = nomor_hp.getText().toString().trim();
        String alamat_petugas_value = alamat_petugas.getText().toString().trim();
        String password_value = pass;
        String path_foto_value = path_foto.trim();

        Petugas prof = new Petugas();

        prof.set_id_petugas(id_petugas_value);
        prof.set_nama_petugas(nama_petugas_value);
        prof.set_nomor_ktp(nomor_ktp_value);
        prof.set_email_petugas(email_petugas_value);
        prof.set_nomor_hp(nomor_hp_value);
        prof.set_alamat_petugas(alamat_petugas_value);
        prof.set_img(image);
        prof.set_img_thumb(imageComp);
        prof.set_password(password_value);
        prof.set_img_name(nameImg);
        prof.set_path(path_foto_value);

        cProfile.updateProfile(pDialog,prof);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

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

    @Override
    protected void onResume() {
        super.onResume();
        AppRequest.getInstance().setConnectivityListener(this);
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showToast(isConnected);
    }

    private void showToast(boolean isConnected) {
        if (!isConnected) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }
}
