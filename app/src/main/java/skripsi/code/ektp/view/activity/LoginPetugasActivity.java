package skripsi.code.ektp.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import skripsi.code.ektp.controller.ControllerLogin;
import skripsi.code.ektp.R;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.model.PetugasDB;
import skripsi.code.ektp.helper.SessionManager;

public class LoginPetugasActivity extends Activity {

    private static final String TAG = LoginPetugasActivity.class.getSimpleName();
    private EditText
            inputEmail,
            inputPassword,
            inputKode;
    private ProgressDialog
            pDialog;
    private SessionManager
            session;
    private PetugasDB
            db;
    private ControllerLogin
            cLogin;
    private TextView
            buttonLogin,
            buttonAdmin;
    private AnimationDrawable
            animationDrawable;
    private RelativeLayout
            relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_petugas_main);

        cLogin = new ControllerLogin(LoginPetugasActivity.this, UrlConfig.URL_LOGIN_PETUGAS);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputKode = (EditText) findViewById(R.id.kode);
        buttonLogin= (TextView) findViewById(R.id.buttonsign);
        buttonAdmin = (TextView) findViewById(R.id.admin_button);

        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);

        db = new PetugasDB(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String kode = inputKode.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty() && !kode.isEmpty()) {

                    cLogin.checkLoginPetugas(db, session, pDialog, email, password, kode);
                } else {

                    Toast.makeText(getApplicationContext(),"Siliahkan isi kolom login terlebih dahulu!", Toast.LENGTH_LONG).show();
                }
            }

        });

        buttonAdmin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent addIntent = new Intent(getApplicationContext(), LoginAdminActivity.class);
                startActivity(addIntent);
                finish();
            }

        });

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( pDialog!=null && pDialog.isShowing() ){
            pDialog.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }
}
