package skripsi.code.ektp.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import skripsi.code.ektp.R;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.AppRequest;
import skripsi.code.ektp.helper.BadgeDrawable;
import skripsi.code.ektp.helper.CircleProgressView;
import skripsi.code.ektp.model.AdminDB;
import skripsi.code.ektp.view.activity.MainActivityAdmin;
import skripsi.code.ektp.view.activity.MainActivityPetugas;
import skripsi.code.ektp.view.activity.ScanMicroblinkActivity;

public class HomeAdminFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match

    private AlphaAnimation layoutClick = new AlphaAnimation(1F, 0.8F);
    private CircleProgressView
            mCircleProgressKtp;
    private SwipeRefreshLayout listSwipeRefreshLayout;
    private TextView
            nama_admin,
            role_admin,
            email,
            nomor_hp,
            provinsi_admin,
            kabupaten_admin,
            license_exp,
            nilai_ktp,
            license,
            circle2;
    private int
            jum_ktp;
    private ImageView
            imgProfile;
    private LinearLayout
            listKtp,
            listMutasiMasuk,
            listMutasiKeluar;
    private String
            idref,
            roleadm;
    private AdminDB
            dbAdmin;
    private String getdata = "getdatauser";

    private OnFragmentInteractionListener mListener;

    private static final String TAG_KTP = "manual";
    private static final String TAG_MUTASI_MASUK = "mutasi_masuk";
    private static final String TAG_MUTASI_KELUAR = "mutasi_keluar";
    private static final String TAG_SCAN = "scan";
    private static final String id_ref = "id_ref";
    private static final String role_adm = "role_admin";

    public HomeAdminFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_admin_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.listSwipeRefreshLayout);
        mCircleProgressKtp = (CircleProgressView) view.findViewById(R.id.circle_ktp_bar);

        nama_admin = (TextView)view.findViewById(R.id.nama_admin);
        role_admin = (TextView)view.findViewById(R.id.role_admin);
        email = (TextView)view.findViewById(R.id.email);
        nomor_hp = (TextView)view.findViewById(R.id.nomor_hp);
        provinsi_admin = (TextView)view.findViewById(R.id.provinsi_admin);
        kabupaten_admin = (TextView)view.findViewById(R.id.kabupaten_admin);
        license_exp = (TextView)view.findViewById(R.id.license_exp);
        license = (TextView)view.findViewById(R.id.license);
        nilai_ktp = (TextView)view.findViewById(R.id.jml_ktp);
        circle2 = (TextView)view.findViewById(R.id.circle_ktp);

        imgProfile = (ImageView) view.findViewById(R.id.img_profile);

        listKtp = (LinearLayout) view.findViewById(R.id.list_ktp);
        listMutasiMasuk = (LinearLayout) view.findViewById(R.id.list_mutasi_masuk);
        listMutasiKeluar = (LinearLayout) view.findViewById(R.id.list_mutasi_keluar);

        dbAdmin = new AdminDB(getActivity());
        HashMap<String, String> user = dbAdmin.getUserDetails();
        idref = user.get(id_ref);
        roleadm = user.get(role_adm);

        onCreateSwipeToRefresh(listSwipeRefreshLayout);

        listSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if(isNetworkAvailable(getActivity())) {
                    getDataBerandaAdmin(listSwipeRefreshLayout, idref, roleadm);

                } else {
                    getDataBerandaAdminCache(listSwipeRefreshLayout, idref, roleadm);
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        listKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(layoutClick);
                MainActivityAdmin activity = (MainActivityAdmin) getActivity();
                activity.replaceFragment(TAG_KTP, 1);
            }
        });

        listMutasiMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(layoutClick);
                MainActivityAdmin activity = (MainActivityAdmin) getActivity();
                activity.replaceFragment(TAG_MUTASI_MASUK, 2);
            }
        });

        listMutasiKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(layoutClick);
                MainActivityAdmin activity = (MainActivityAdmin) getActivity();
                activity.replaceFragment(TAG_MUTASI_KELUAR, 3);
            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void circleProgressWorker(){
        int dt_ktp, tot_all;
        tot_all = (jum_ktp + 100);

        if(jum_ktp == 0){
            dt_ktp = 0;
        } else{
            dt_ktp = jum_ktp*100/tot_all;
        }

        mCircleProgressKtp.setInterpolator(new AccelerateDecelerateInterpolator());
        mCircleProgressKtp.setProgressWithAnimation(dt_ktp, 2000);

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

    private void getRegional(String prov, String kab) {
        String kabupaten;

        if(kab.equals("") || kab==""){
            kabupaten = "";

        }else{
            kabupaten = kab;
            final BadgeDrawable drawable2 =
                    new BadgeDrawable.Builder()
                            .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                            .badgeColor(android.graphics.Color.parseColor("#009efb"))
                            .text1(kabupaten.toUpperCase())
                            .textSize(spToPixels(12))
                            .build();
            SpannableString spanStatus2 = new SpannableString(TextUtils.concat(drawable2.toSpannable()));
            kabupaten_admin.setText(spanStatus2);
        }

        final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(android.graphics.Color.parseColor("#36bea6"))
                        .text1(prov.toUpperCase())
                        .textSize(spToPixels(12))
                        .build();
        SpannableString spanStatus = new SpannableString(TextUtils.concat(drawable.toSpannable()));
        provinsi_admin.setText(spanStatus);

    }

    private static float spToPixels(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

    private void getRole(String role_adm) {
        String role;

        if(role_adm.equals("0") || role_adm=="0"){
            role = "Admin Nasional";
        }else if(role_adm.equals("1") || role_adm=="1"){
            role = "Admin Provinsi";
        }else{
            role = "Admin Kabupaten";
        }

        final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(0xff009900)
                        .text1(role.toUpperCase())
                        .textSize(spToPixels(12))
                        .build();
        SpannableString spanStatus = new SpannableString(TextUtils.concat(drawable.toSpannable()));
        role_admin.setText(spanStatus);
    }

    public boolean isNetworkAvailable(Activity c) {
        boolean state;
        ConnectivityManager cmg = (ConnectivityManager) c.getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cmg.getActiveNetworkInfo();
        state = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (state) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRefresh() {

        if(isNetworkAvailable(getActivity())) {
            getDataBerandaAdmin(listSwipeRefreshLayout, idref, roleadm);

        } else {
            getDataBerandaAdminCache(listSwipeRefreshLayout, idref, roleadm);
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void getDataBerandaAdmin(final SwipeRefreshLayout s, final String id_ref, final  String role_adm){
        s.setRefreshing(true);
        AppRequest.getInstance().getRequestQueue().getCache().remove(UrlConfig.URL_GET_BERANDA_ADMIN +id_ref+"/role_admin/"+role_adm);

        StringRequest strReq = new StringRequest(Request.Method.GET, UrlConfig.URL_GET_BERANDA_ADMIN +id_ref+"/role_admin/"+role_adm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("status");
                    if (error) {

                        imgProfile.setImageResource(R.drawable.no_user);

                        circle2.setText("Data Anggota");

                        JSONObject user = jObj.getJSONObject("beranda");

                        String role_admin_value = user.getString("role_admin").toString();
                        String id_ref_value = user.getString("id_ref").toString();
                        String nama_admin_value = user.getString("nama_admin").toString();
                        String email_value = user.getString("email").toString();
                        String nomor_hp_value = user.getString("nomor_hp").toString();
                        String img_value = user.getString("img").toString();
                        String img_thumb_value = user.getString("img_thumb").toString();
                        String license_value = user.getString("license").toString();
                        String license_exp_value = user.getString("license_exp").toString();
                        String create_value = user.getString("create_prev");
                        String read_value = user.getString("read_prev");
                        String update_value = user.getString("update_prev");
                        String delete_value = user.getString("delete_prev");
                        String provinsi_value = user.getString("provinsi").toString();
                        String kabupaten_value = user.getString("kabupaten").toString();
                        String jml_ktp_value = user.getString("jml_ktp").toString();

                        jum_ktp = Integer.parseInt(jml_ktp_value);

                        nama_admin.setText(nama_admin_value.toUpperCase());
                        email.setText(email_value);
                        license.setText(license_value);
                        license_exp.setText(license_exp_value);
                        nomor_hp.setText(nomor_hp_value);
                        nilai_ktp.setText(jml_ktp_value);
                        getRegional(provinsi_value, kabupaten_value);
                        getRole(role_admin_value);

                        Glide.with(getActivity()).load(UrlConfig.MAIN_URL+img_thumb_value).asBitmap().fitCenter().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                    circleBitmap(bitmap);
                            }
                        });

                        circleProgressWorker();
                        s.setRefreshing(false);
                    } else {
                        s.setRefreshing(false);
                        String msg = jObj.getString("message");
                        Toast.makeText(getActivity(),msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    s.setRefreshing(false);
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                s.setRefreshing(false);
                Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        s.setRefreshing(false);
        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }

    public void getDataBerandaAdminCache(final SwipeRefreshLayout s, final String id_ref, final  String role_adm) {
        s.setRefreshing(true);
        String url = UrlConfig.URL_GET_BERANDA_ADMIN +id_ref+"/role_admin/"+role_adm;
        Cache cache = AppRequest.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);

        if(entry != null){
            try {
                imgProfile.setImageResource(R.drawable.no_user);

                circle2.setText("Data Anggota");

                String data = new String(entry.data);
                JSONObject jObj = new JSONObject(data);
                JSONObject user = jObj.getJSONObject("beranda");

                String role_admin_value = user.getString("role_admin").toString();
                String id_ref_value = user.getString("id_ref").toString();
                String nama_admin_value = user.getString("nama_admin").toString();
                String email_value = user.getString("email").toString();
                String nomor_hp_value = user.getString("nomor_hp").toString();
                String img_value = user.getString("img").toString();
                String img_thumb_value = user.getString("img_thumb").toString();
                String license_value = user.getString("license").toString();
                String license_exp_value = user.getString("license_exp").toString();
                String create_value = user.getString("create_prev");
                String read_value = user.getString("read_prev");
                String update_value = user.getString("update_prev");
                String delete_value = user.getString("delete_prev");
                String provinsi_value = user.getString("provinsi").toString();
                String kabupaten_value = user.getString("kabupaten").toString();
                String jml_ktp_value = user.getString("jml_ktp").toString();

                jum_ktp = Integer.parseInt(jml_ktp_value);

                nama_admin.setText(nama_admin_value.toUpperCase());
                email.setText(email_value);
                license.setText(license_value);
                license_exp.setText(license_exp_value);
                nomor_hp.setText(nomor_hp_value);
                nilai_ktp.setText(jml_ktp_value);
                getRegional(provinsi_value,kabupaten_value);
                getRole(role_admin_value);

                Glide.with(getActivity()).load(UrlConfig.MAIN_URL+img_thumb_value).asBitmap().fitCenter().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        if(bitmap==null){
                            imgProfile.setImageResource(R.drawable.no_user);
                        }else {
                            circleBitmap(bitmap);
                        }
                    }
                });

                circleProgressWorker();
                s.setRefreshing(false);
            } catch (JSONException e) {
                s.setRefreshing(false);
                Toast.makeText(getActivity(),  "JSON error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }else{
            s.setRefreshing(false);
            Toast.makeText(getActivity(),  "This last data get from cache", Toast.LENGTH_LONG).show();
        }
    }
}
