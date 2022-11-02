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
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.AppRequest;
import skripsi.code.ektp.helper.BadgeDrawable;
import skripsi.code.ektp.helper.CircleProgressView;
import skripsi.code.ektp.model.PetugasDB;
import skripsi.code.ektp.R;
import skripsi.code.ektp.view.activity.MainActivityPetugas;
import skripsi.code.ektp.view.activity.ScanMicroblinkActivity;

public class HomePetugasFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match

    private AlphaAnimation layoutClick = new AlphaAnimation(1F, 0.8F);
    private SwipeRefreshLayout listSwipeRefreshLayout;
    private CircleProgressView
            mCircleProgressKtp;
    private TextView
            nama_petugas,
            status_data,
            alamat_petugas,
            email_petugas,
            nomor_hp,
            kode,
            nilai_ktp,
            nomor_ktp,
            circle2;
    private int
            jum_ktp;
    private ImageView
            imgProfile;
    private LinearLayout
            listKtp,
            scanKtp;
    private String
            id_pet;
    private PetugasDB
            db;
    private String getdata = "getdatauser";

    private OnFragmentInteractionListener mListener;
    private static final String TAG_KTP = "manual";
    private static final String TAG_SCAN = "scan";
    private static final String id_petugas = "id_petugas";

    public HomePetugasFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_petugas_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        listSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.listSwipeRefreshLayout);
        mCircleProgressKtp = (CircleProgressView) view.findViewById(R.id.circle_ktp_bar);

        nama_petugas = (TextView)view.findViewById(R.id.nama_petugas);
        status_data = (TextView)view.findViewById(R.id.status_data);
        email_petugas = (TextView)view.findViewById(R.id.email_petugas);
        nomor_hp = (TextView)view.findViewById(R.id.nomor_hp);
        kode = (TextView)view.findViewById(R.id.kode);
        alamat_petugas = (TextView)view.findViewById(R.id.alamat_petugas);
        nomor_ktp = (TextView)view.findViewById(R.id.nomor_ktp);
        nilai_ktp = (TextView)view.findViewById(R.id.jml_ktp);
        circle2 = (TextView)view.findViewById(R.id.circle_ktp);

        imgProfile = (ImageView) view.findViewById(R.id.img_profile);

        listKtp = (LinearLayout) view.findViewById(R.id.list_ktp);
        scanKtp = (LinearLayout) view.findViewById(R.id.scan_ktp);

        db = new PetugasDB(getActivity());
        HashMap<String, String> user = db.getUserDetails();
        id_pet = user.get(id_petugas);

        onCreateSwipeToRefresh(listSwipeRefreshLayout);

        listSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                if(isNetworkAvailable(getActivity())) {
                    getDataBerandaPetugas(listSwipeRefreshLayout,id_pet);

                } else {
                    getDataBerandaWorkerCache(listSwipeRefreshLayout,id_pet);
                    Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        listKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(layoutClick);
                MainActivityPetugas activity = (MainActivityPetugas) getActivity();
                activity.replaceFragment(TAG_KTP, 1);
            }
        });

        scanKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addIntent = new Intent(getActivity(), ScanMicroblinkActivity.class);
                startActivity(addIntent);
            }
        });


    }

    private void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout) {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private static float spToPixels(float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
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

    private void getStatus(String stat) {
        String status;
        if(stat.equals("1")){
            status = "Petugas Aktif";
        }else{
            status = "Petugas Non Aktif";
        }
        final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_WITH_TWO_TEXT_COMPLEMENTARY)
                        .badgeColor(0xff0000b3)
                        .text1("STATUS")
                        .text2(status)
                        .textSize(spToPixels(12))
                        .build();
        SpannableString spanStatus = new SpannableString(TextUtils.concat(drawable.toSpannable()));
        status_data.setText(spanStatus);
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
            getDataBerandaPetugas(listSwipeRefreshLayout,id_pet);

        } else {
            getDataBerandaWorkerCache(listSwipeRefreshLayout,id_pet);
            Toast.makeText(getActivity(), "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void getDataBerandaPetugas(final SwipeRefreshLayout s, final String id_petugas){
        s.setRefreshing(true);
        AppRequest.getInstance().getRequestQueue().getCache().remove(UrlConfig.URL_GET_BERANDA_PETUGAS +id_petugas);

        StringRequest strReq = new StringRequest(Request.Method.GET, UrlConfig.URL_GET_BERANDA_PETUGAS +id_petugas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("status");
                    if (error) {

                        imgProfile.setImageResource(R.drawable.no_user);

                        circle2.setText("Data Anggota");

                        JSONObject user = jObj.getJSONObject("beranda");
                        String nama_petugas_value = user.getString("nama_petugas").toString();
                        String status_data_value = user.getString("status_data").toString();
                        String nomor_ktp_value = user.getString("nomor_ktp").toString();
                        String email_petugas_value = user.getString("email_petugas").toString();
                        String nomor_hp_value = user.getString("nomor_hp").toString();
                        String alamat_petugas_value = user.getString("alamat_petugas").toString();
                        String img_value = user.getString("img").toString();
                        String img_thumb_value = user.getString("img_thumb").toString();
                        String kode_value = user.getString("kode_petugas").toString();
                        String jml_ktp_value = user.getString("jml_ktp").toString();

                        jum_ktp = Integer.parseInt(jml_ktp_value);

                        nama_petugas.setText(nama_petugas_value.toUpperCase());
                        status_data.setText(status_data_value);
                        alamat_petugas.setText(alamat_petugas_value);
                        email_petugas.setText(email_petugas_value);
                        nomor_hp.setText(nomor_hp_value);
                        nomor_ktp.setText(nomor_ktp_value);
                        kode.setText(kode_value);
                        nilai_ktp.setText(jml_ktp_value);
                        getStatus(status_data_value);

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

    public void getDataBerandaWorkerCache(final SwipeRefreshLayout s, final String id_petugas) {

        s.setRefreshing(true);
        String url = UrlConfig.URL_GET_BERANDA_PETUGAS +id_petugas;
        Cache cache = AppRequest.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);

        if(entry != null){
            try {
                imgProfile.setImageResource(R.drawable.no_user);

                circle2.setText("Data Anggota");

                String data = new String(entry.data);
                JSONObject jObj = new JSONObject(data);
                JSONObject user = jObj.getJSONObject("beranda");

                String nama_petugas_value = user.getString("nama_petugas").toString();
                String status_data_value = user.getString("status_data").toString();
                String nomor_ktp_value = user.getString("nomor_ktp").toString();
                String email_petugas_value = user.getString("email_petugas").toString();
                String nomor_hp_value = user.getString("nomor_hp").toString();
                String alamat_petugas_value = user.getString("alamat_petugas").toString();
                String img_value = user.getString("img").toString();
                String img_thumb_value = user.getString("img_thumb").toString();
                String kode_value = user.getString("kode_petugas").toString();
                String jml_reg_value = user.getString("jml_reg").toString();
                String jml_ktp_value = user.getString("jml_ktp").toString();

                jum_ktp = Integer.parseInt(jml_ktp_value);

                nama_petugas.setText(nama_petugas_value.toUpperCase());
                status_data.setText(status_data_value);
                alamat_petugas.setText(alamat_petugas_value);
                email_petugas.setText(email_petugas_value);
                nomor_ktp.setText(nomor_ktp_value);
                nomor_hp.setText(nomor_hp_value);
                kode.setText(kode_value);
                nilai_ktp.setText(jml_ktp_value);
                getStatus(status_data_value);

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
