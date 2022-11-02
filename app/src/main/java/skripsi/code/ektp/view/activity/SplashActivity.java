package skripsi.code.ektp.view.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import skripsi.code.ektp.helper.SessionManager;
import skripsi.code.ektp.R;


public class SplashActivity extends AppCompatActivity {

    private SessionManager
            session;
    private static int SPLASH_TIME_OUT = 7000;

    private BroadcastReceiver
            mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);
        setAnimation();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        session = new SessionManager(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!session.isLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, LoginPetugasActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    if(session.getRole().equals("1") || session.getRole() == "1"){

                        Intent intent = new Intent(SplashActivity.this, MainActivityPetugas.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Intent intent = new Intent(SplashActivity.this, MainActivityAdmin.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void setAnimation() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(900);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(900);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(900);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(900);
        animatorSet.start();

        findViewById(R.id.imagelogo2).setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        findViewById(R.id.imagelogo2).startAnimation(anim);

        findViewById(R.id.imagelogo1).setAlpha(1.0F);
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.fade);
        findViewById(R.id.imagelogo1).startAnimation(anim1);
    }
}