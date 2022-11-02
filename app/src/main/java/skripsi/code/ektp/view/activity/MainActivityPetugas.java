package skripsi.code.ektp.view.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import skripsi.code.ektp.controller.ControllerPetugas;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.AppRequest;
import skripsi.code.ektp.helper.CircleTransform;
import skripsi.code.ektp.helper.ConnectivityReceiver;
import skripsi.code.ektp.helper.firebase.NotificationUtils;
import skripsi.code.ektp.model.PetugasDB;
import skripsi.code.ektp.helper.SessionManager;
import skripsi.code.ektp.R;
import skripsi.code.ektp.view.fragment.HomePetugasFragment;
import skripsi.code.ektp.view.fragment.ListKtpPetugasFragment;


public class MainActivityPetugas extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    private NavigationView
            navigationView;
    private DrawerLayout
            drawer;
    private View
            navHeader;
    private ImageView
            imgNavHeaderBg,
            imgProfile;
    private TextView
            txtName,
            txtWebsite;
    private Toolbar
            toolbar;
    private ControllerPetugas
            cProfile;
    private ProgressDialog
            pDialog;
    private Fragment
            fragment;
    private PetugasDB
            dbPetugas;
    private SessionManager
            session;

    public static int navItemIndex = 0;

    private static final String TAG = MainActivityPetugas.class.getSimpleName();

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private NotificationUtils nu;

    private static final String TAG_HOME = "home";
    public static final String TAG_SCAN = "scan";
    public static final String TAG_KTP = "manual";
    public static String CURRENT_TAG = TAG_HOME;

    private String[] activityTitles;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private static final int REQUEST_EDIT_PROFILE = 100;
    private static final int REQUEST_OK = 200;

    private static final String id_user = "id_user";
    private static final String id_petugas = "id_petugas";
    private static final String nama_petugas = "nama_petugas";
    private static final String email_petugas = "email_petugas";
    private static final String img_thumb = "img_thumb";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_petugas);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        dbPetugas = new PetugasDB(getApplicationContext());

        session = new SessionManager(getApplicationContext());

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles_petugas);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals(UrlConfig.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), "Projek Baru: " + message.toUpperCase(), Toast.LENGTH_LONG).show();
                }
            }
        };

        loadNavHeader();
        checkConnection();
       // displayFirebaseRegId();
        setUpNavigationView();

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppRequest.getInstance().setConnectivityListener(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(UrlConfig.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(UrlConfig.PUSH_NOTIFICATION));

        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showToast(isConnected);
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

    private void loadNavHeader() {
        HashMap<String, String> user = dbPetugas.getUserDetails();

        String name = user.get(nama_petugas);
        String email = user.get(email_petugas);
        String photo = user.get(img_thumb);

        txtName.setText(name);
        txtWebsite.setText(email);

        imgNavHeaderBg.setImageResource(R.drawable.android_bg);

        Glide.with(this).load(UrlConfig.MAIN_URL+photo)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT_PROFILE) {
            if (resultCode == REQUEST_OK) {
                this.recreate();
            }
        }
    }

    private void loadHomeFragment() {

        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {

                fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {

        switch (navItemIndex) {
            case 0:
                HomePetugasFragment homePetugasFragment = new HomePetugasFragment();
                return homePetugasFragment;
            case 1:
                ListKtpPetugasFragment listKtpPetugasFragment = new ListKtpPetugasFragment();
                return listKtpPetugasFragment;
            default:
                return new HomePetugasFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                HashMap<String, String> user = dbPetugas.getUserDetails();
                String idu = user.get(id_user);

                switch (menuItem.getItemId()) {

                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        checkConnection();
                        break;
                    case R.id.nav_ktp:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_KTP;
                        checkConnection();
                        break;
                    case R.id.nav_scan:
                        CURRENT_TAG = TAG_SCAN;
                        startActivity(new Intent(MainActivityPetugas.this, ScanMicroblinkActivity.class));
                        checkConnection();
                        break;
                    case R.id.nav_about_us:
                        startActivity(new Intent(MainActivityPetugas.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        checkConnection();
                        return true;
                    case R.id.nav_setting_profile:
                        HashMap<String, String> get_id = dbPetugas.getUserDetails();
                        String id_pet = get_id.get(id_petugas);
                        cProfile = new ControllerPetugas(MainActivityPetugas.this, UrlConfig.URL_DETAIL_PROFILE);
                        cProfile.getEditProfile(pDialog,id_pet);
                        drawer.closeDrawers();
                        checkConnection();
                        return true;
                    case R.id.logout_user:
                        logoutUser();
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                 super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void replaceFragment(String tag, int navIndex) {
        navItemIndex = navIndex;
        CURRENT_TAG = tag;

        selectNavMenu();
        setToolbarTitle();

        fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }


//    private void displayFirebaseRegId() {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(UrlConfig.SHARED_PREF, 0);
//        String regId = pref.getString("regId", null);
//
//        Log.e(TAG, "Firebase reg id: " + regId);
//
//        HashMap<String, String> user = dbPetugas.getUserDetails();
//        String uid = user.get(id_user);
//        nu.updateRegId(uid,regId);
//    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void logoutUser() {
        session.setLogin(false);
        dbPetugas.deleteUsers();
        startActivity(new Intent(MainActivityPetugas.this, LoginPetugasActivity.class));
        finish();
    }

}
