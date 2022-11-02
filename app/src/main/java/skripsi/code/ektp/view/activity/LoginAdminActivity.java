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

import skripsi.code.ektp.R;
import skripsi.code.ektp.controller.ControllerLogin;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.SessionManager;
import skripsi.code.ektp.model.AdminDB;
import skripsi.code.ektp.model.PetugasDB;

public class LoginAdminActivity extends Activity {

    private static final String TAG = LoginAdminActivity.class.getSimpleName();
    private EditText
            inputEmail,
            inputPassword;

    private ProgressDialog
            pDialog;
    private SessionManager
            session;
    private AdminDB
            db;
    private ControllerLogin
            cLogin;
    private TextView
            buttonLogin,
            buttonPetugas;
    private AnimationDrawable
            animationDrawable;
    private RelativeLayout
            relativeLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin_main);

        cLogin = new ControllerLogin(LoginAdminActivity.this, UrlConfig.URL_LOGIN_ADMIN);
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        buttonLogin= (TextView) findViewById(R.id.buttonsign);
        buttonPetugas = (TextView) findViewById(R.id.petugas_button);

        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);

        db = new AdminDB(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty() ) {

                    cLogin.checkLoginAdmin(db, session, pDialog, email, password);
                } else {

                    Toast.makeText(getApplicationContext(),"Siliahkan isi kolom login terlebih dahulu!", Toast.LENGTH_LONG).show();
                }
            }

        });

        buttonPetugas.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent addIntent = new Intent(getApplicationContext(), LoginPetugasActivity.class);
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
