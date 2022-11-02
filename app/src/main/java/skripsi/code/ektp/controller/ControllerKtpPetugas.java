package skripsi.code.ektp.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import skripsi.code.ektp.adapter.KtpPetugasListAdapter;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.AppRequest;
import skripsi.code.ektp.model.Ktp;
import skripsi.code.ektp.view.activity.DetailKtpActivity;
import skripsi.code.ektp.view.activity.EditKtpActivity;
import skripsi.code.ektp.view.fragment.ListKtpPetugasFragment;

/**
 * Created by TONY on 1/2/2017.
 */

public class ControllerKtpPetugas {
    private Context mContext;
    private String urlKtp;
    private ArrayList<Ktp> ktpList = new ArrayList<Ktp>();
    private SwipeRefreshLayout list;
    private KtpPetugasListAdapter adapters;
    private String getdata = "getdataktp";
    private int limit = 3;
    private String idpNode, idrNode;

    private static final int REQUEST_ACTION = 100;
    private static final int REQUEST_OK = 200;

    public ControllerKtpPetugas(Context c, String url, SwipeRefreshLayout lst) {
        this.mContext= c;
        this.urlKtp = url;
        this.list = lst;
    }

    public void setAdapter(KtpPetugasListAdapter adt){
        this.adapters=adt;
    }
    public KtpPetugasListAdapter getAdapter(){
        return adapters;
    }

    public void getListKtpPetugasAll(final String idp){

        getList().clear();
        list.setRefreshing(true);
        String url = urlKtp +idp+"/limit/"+limit;
        idpNode=idp;

        AppRequest.getInstance().getRequestQueue().getCache().remove(url);
        StringRequest req = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");

                    if(!error){
                        String msg = jObj.getString("message");
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    } else {

                        JSONArray jsonAr = jObj.getJSONArray("result");

                        int no = 1;
                        for (int i = 0; i < jsonAr.length(); i++) {

                            JSONObject jobObj = jsonAr.getJSONObject(i);

                            String id_ktp_value = jobObj.getString("id_ktp");
                            String id_admin_value = jobObj.getString("id_admin");
                            String id_petugas_value = jobObj.getString("id_petugas");
                            String id_asal_value = jobObj.getString("id_asal");
                            String nik_ktp_value = jobObj.getString("nik_ktp");
                            String nama_ktp_value = jobObj.getString("nama_ktp");
                            String tempat_lahir_value = jobObj.getString("tempat_lahir");
                            String tanggal_lahir_value = jobObj.getString("tanggal_lahir");
                            String jenis_kelamin_value = jobObj.getString("jenis_kelamin");
                            String gol_darah_value = jobObj.getString("gol_darah");
                            String alamat_ktp_value = jobObj.getString("alamat_ktp");
                            String rt_value = jobObj.getString("rt");
                            String rw_value = jobObj.getString("rw");
                            String agama_value = jobObj.getString("agama");
                            String status_nikah_value = jobObj.getString("status_nikah");
                            String pekerjaan_value = jobObj.getString("pekerjaan");
                            String nomor_hp_ktp_value = jobObj.getString("nomor_hp_ktp");
                            String image_id = UrlConfig.MAIN_URL + jobObj.getString("img");
                            String img_id_thumb = UrlConfig.MAIN_URL + jobObj.getString("img_thumb");
                            String kodepos_value = jobObj.getString("kodepos");
                            String pend_terakhir_value = jobObj.getString("pend_terakhir");
                            String email_value = jobObj.getString("email");
                            String nomor_rumah_ktp_value = jobObj.getString("nomor_rumah_ktp");
                            String nomor_kantor_ktp_value = jobObj.getString("nomor_kantor_ktp");
                            String nomor_faksimili_ktp_value = jobObj.getString("nomor_faksimili_ktp");
                            String tanggal_daftar_value = jobObj.getString("tanggal_daftar");
                            String nik_kta_baru_value = jobObj.getString("nik_kta_baru");
                            String pengurus_value = jobObj.getString("pengurus");
                            String kategori_pengurus_value = jobObj.getString("kategori");
                            String jabatan_value = jobObj.getString("jabatan");
                            String wilayah_pengurus_value = jobObj.getString("wilayah_pengurus");
                            String fb_value = jobObj.getString("facebook");
                            String twitter_value = jobObj.getString("twitter");
                            String ig_value = jobObj.getString("instagram");
                            String wa_value = jobObj.getString("whatsapp");
                            String image_pas = UrlConfig.MAIN_URL + jobObj.getString("img_pas");
                            String image_pas_thumb = UrlConfig.MAIN_URL + jobObj.getString("img_pas_thumb");
                            String provinsi_asal_value = jobObj.getString("provinsi_asal");
                            String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                            String kecamatan_asal_value = jobObj.getString("kecamatan_asal");
                            String kelurahan_asal_value = jobObj.getString("kelurahan_asal");
                            String provinsi_pengurus_value = jobObj.getString("provinsi_pengurus");
                            String kabupaten_pengurus_value = jobObj.getString("kabupaten_pengurus");
                            String kecamatan_pengurus_value = jobObj.getString("kecamatan_pengurus");
                            String kelurahan_pengurus_value = jobObj.getString("kelurahan_pengurus");
                            String tanggal_post = jobObj.getString("tanggal_post");
                            String nama_admin = jobObj.getString("nama_admin");
                            String nama_petugas = jobObj.getString("nama_petugas");
                            String nama_pekerjaan_value = jobObj.getString("nama_pekerjaan");
                            String status_mutasi_value = jobObj.getString("status_mutasi");

                            final Ktp ktp = new Ktp();

                            ktp.set_id_ktp(id_ktp_value);
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
                            ktp.set_img_thumb(img_id_thumb);
                            ktp.set_kodepos(kodepos_value);
                            ktp.set_pend_terakhir(pend_terakhir_value);
                            ktp.set_email(email_value);
                            ktp.set_nomor_rumah_ktp(nomor_rumah_ktp_value);
                            ktp.set_nomor_kantor_ktp(nomor_kantor_ktp_value);
                            ktp.set_nomor_faksimili_ktp(nomor_faksimili_ktp_value);
                            ktp.set_tanggal_daftar(tanggal_daftar_value);
                            ktp.set_nik_kta_baru(nik_kta_baru_value);
                            ktp.set_pengurus(pengurus_value);
                            ktp.set_kategori_pengurus(kategori_pengurus_value);
                            ktp.set_jabatan(jabatan_value);
                            ktp.set_wilayah_pengurus(wilayah_pengurus_value);
                            ktp.set_fb(fb_value);
                            ktp.set_twitter(twitter_value);
                            ktp.set_ig(ig_value);
                            ktp.set_wa(wa_value);
                            ktp.set_img_pas(image_pas);
                            ktp.set_img_pas_thumb(image_pas_thumb);
                            ktp.set_provinsi_asal(provinsi_asal_value);
                            ktp.set_kabupaten_asal(kabupaten_asal_value);
                            ktp.set_kecamatan_asal(kecamatan_asal_value);
                            ktp.set_kelurahan_asal(kelurahan_asal_value);
                            ktp.set_provinsi_pengurus(provinsi_pengurus_value);
                            ktp.set_kabupaten_pengurus(kabupaten_pengurus_value);
                            ktp.set_kecamatan_pengurus(kecamatan_pengurus_value);
                            ktp.set_kelurahan_pengurus(kelurahan_pengurus_value);
                            ktp.set_nama_admin(nama_admin);
                            ktp.set_nama_petugas(nama_petugas);
                            ktp.set_tanggal_post(tanggal_post);
                            ktp.set_status_mutasi(status_mutasi_value);

                            ktpList.add(0, ktp);

                            if (no >= limit)
                                limit = limit + 4;
                            no++;

                        }
                    }
                    getAdapter().notifyDataSetChanged();
                    list.setRefreshing(false);

                } catch (JSONException e) {

                    Log.e("ERORRRR",e.getMessage().toString());
                    Toast.makeText(mContext,  "Data kosong, isi data terlebih dahulu..", Toast.LENGTH_LONG).show();
                    list.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,  "JSON Parsing error: "+ error.getMessage(), Toast.LENGTH_LONG).show();
                list.setRefreshing(false);
            }
        });
        AppRequest.getInstance().addToRequestQueue(req,getdata);
    }


    public void getListKtpPetugasCacheAll(final String idp) {

        getList().clear();
        list.setRefreshing(true);
        idpNode=idp;
        String url = urlKtp +idp+"/limit/"+limit;
        Cache cache = AppRequest.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);

        if(entry != null){
            try {
                String data = new String(entry.data);
                JSONObject jObj = new JSONObject(data);
                JSONArray jsonAr = jObj.getJSONArray("result");
                int no =1;
                for (int i = 0; i < jsonAr.length(); i++) {

                    JSONObject jobObj = jsonAr.getJSONObject(i);

                    String id_ktp_value = jobObj.getString("id_ktp");
                    String id_admin_value = jobObj.getString("id_admin");
                    String id_petugas_value = jobObj.getString("id_petugas");
                    String id_asal_value = jobObj.getString("id_asal");
                    String nik_ktp_value = jobObj.getString("nik_ktp");
                    String nama_ktp_value = jobObj.getString("nama_ktp");
                    String tempat_lahir_value = jobObj.getString("tempat_lahir");
                    String tanggal_lahir_value = jobObj.getString("tanggal_lahir");
                    String jenis_kelamin_value = jobObj.getString("jenis_kelamin");
                    String gol_darah_value = jobObj.getString("gol_darah");
                    String alamat_ktp_value = jobObj.getString("alamat_ktp");
                    String rt_value = jobObj.getString("rt");
                    String rw_value = jobObj.getString("rw");
                    String agama_value = jobObj.getString("agama");
                    String status_nikah_value = jobObj.getString("status_nikah");
                    String pekerjaan_value = jobObj.getString("pekerjaan");
                    String nomor_hp_ktp_value = jobObj.getString("nomor_hp_ktp");
                    String image_id = UrlConfig.MAIN_URL + jobObj.getString("img");
                    String img_id_thumb = UrlConfig.MAIN_URL + jobObj.getString("img_thumb");
                    String kodepos_value = jobObj.getString("kodepos");
                    String pend_terakhir_value = jobObj.getString("pend_terakhir");
                    String email_value = jobObj.getString("email");
                    String nomor_rumah_ktp_value = jobObj.getString("nomor_rumah_ktp");
                    String nomor_kantor_ktp_value = jobObj.getString("nomor_kantor_ktp");
                    String nomor_faksimili_ktp_value = jobObj.getString("nomor_faksimili_ktp");
                    String tanggal_daftar_value = jobObj.getString("tanggal_daftar");
                    String nik_kta_baru_value = jobObj.getString("nik_kta_baru");
                    String pengurus_value = jobObj.getString("pengurus");
                    String kategori_pengurus_value = jobObj.getString("kategori");
                    String jabatan_value = jobObj.getString("jabatan");
                    String wilayah_pengurus_value = jobObj.getString("wilayah_pengurus");
                    String fb_value = jobObj.getString("facebook");
                    String twitter_value = jobObj.getString("twitter");
                    String ig_value = jobObj.getString("instagram");
                    String wa_value = jobObj.getString("whatsapp");
                    String image_pas = UrlConfig.MAIN_URL + jobObj.getString("img_pas");
                    String image_pas_thumb = UrlConfig.MAIN_URL + jobObj.getString("img_pas_thumb");
                    String provinsi_asal_value = jobObj.getString("provinsi_asal");
                    String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                    String kecamatan_asal_value = jobObj.getString("kecamatan_asal");
                    String kelurahan_asal_value = jobObj.getString("kelurahan_asal");
                    String provinsi_pengurus_value = jobObj.getString("provinsi_pengurus");
                    String kabupaten_pengurus_value = jobObj.getString("kabupaten_pengurus");
                    String kecamatan_pengurus_value = jobObj.getString("kecamatan_pengurus");
                    String kelurahan_pengurus_value = jobObj.getString("kelurahan_pengurus");
                    String tanggal_post = jobObj.getString("tanggal_post");
                    String nama_admin = jobObj.getString("nama_admin");
                    String nama_petugas = jobObj.getString("nama_petugas");
                    String nama_pekerjaan_value = jobObj.getString("nama_pekerjaan");
                    String status_mutasi_value = jobObj.getString("status_mutasi");

                    final Ktp ktp = new Ktp();

                    ktp.set_id_ktp(id_ktp_value);
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
                    ktp.set_img_thumb(img_id_thumb);
                    ktp.set_kodepos(kodepos_value);
                    ktp.set_pend_terakhir(pend_terakhir_value);
                    ktp.set_email(email_value);
                    ktp.set_nomor_rumah_ktp(nomor_rumah_ktp_value);
                    ktp.set_nomor_kantor_ktp(nomor_kantor_ktp_value);
                    ktp.set_nomor_faksimili_ktp(nomor_faksimili_ktp_value);
                    ktp.set_tanggal_daftar(tanggal_daftar_value);
                    ktp.set_nik_kta_baru(nik_kta_baru_value);
                    ktp.set_pengurus(pengurus_value);
                    ktp.set_kategori_pengurus(kategori_pengurus_value);
                    ktp.set_jabatan(jabatan_value);
                    ktp.set_wilayah_pengurus(wilayah_pengurus_value);
                    ktp.set_fb(fb_value);
                    ktp.set_twitter(twitter_value);
                    ktp.set_ig(ig_value);
                    ktp.set_wa(wa_value);
                    ktp.set_img_pas(image_pas);
                    ktp.set_img_pas_thumb(image_pas_thumb);
                    ktp.set_provinsi_asal(provinsi_asal_value);
                    ktp.set_kabupaten_asal(kabupaten_asal_value);
                    ktp.set_kecamatan_asal(kecamatan_asal_value);
                    ktp.set_kelurahan_asal(kelurahan_asal_value);
                    ktp.set_provinsi_pengurus(provinsi_pengurus_value);
                    ktp.set_kabupaten_pengurus(kabupaten_pengurus_value);
                    ktp.set_kecamatan_pengurus(kecamatan_pengurus_value);
                    ktp.set_kelurahan_pengurus(kelurahan_pengurus_value);
                    ktp.set_nama_admin(nama_admin);
                    ktp.set_nama_petugas(nama_petugas);
                    ktp.set_tanggal_post(tanggal_post);
                    ktp.set_nama_pekerjaan(nama_pekerjaan_value);
                    ktp.set_status_mutasi(status_mutasi_value);

                    ktpList.add(0, ktp);
                    if (no >= limit)
                        limit = limit+4;
                    no++;
                }
                getAdapter().notifyDataSetChanged();
                list.setRefreshing(false);

            } catch (JSONException e) {
                Toast.makeText(mContext,  "JSON error: "+ e.getMessage(), Toast.LENGTH_LONG).show();
                list.setRefreshing(false);
            }
        }else{
            Toast.makeText(mContext,  "This last data get from cache", Toast.LENGTH_LONG).show();
            list.setRefreshing(false);
        }
    }

    public List<Ktp> getList() {
        return ktpList;
    }

    public void createKtp(final ProgressDialog pDialog, final Ktp ktp){

        pDialog.setMessage("Mengirimkan data ...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, UrlConfig.URL_ADD_KTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");
                    String msg = jObj.getString("message");

                    if (error) {
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                        Intent addIntent = new Intent(mContext, ListKtpPetugasFragment.class);
                        ((Activity) mContext).setResult(REQUEST_OK,addIntent);
                        ((Activity) mContext).finish();
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }

                }
                catch (JSONException e) {
                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("EROR" , e.getMessage());
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("EROR" , error.getMessage());
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("id_admin", ktp.get_id_admin());
                params.put("nama_petugas", ktp.get_id_petugas());
                params.put("id_asal", ktp.get_id_asal());
                params.put("nama_ktp", ktp.get_nama_ktp());
                params.put("nik_ktp", ktp.get_nik_ktp());
                params.put("tempat_lahir", ktp.get_tempat_lahir());
                params.put("tanggal_lahir",  ktp.get_tanggal_lahir());
                params.put("jenis_kelamin", ktp.get_jenis_kelamin());
                params.put("gol_darah",  ktp.get_gol_darah());
                params.put("email", ktp.get_email());
                params.put("alamat_ktp",  ktp.get_alamat_ktp());
                params.put("rt", ktp.get_rt());
                params.put("rw", ktp.get_rw());
                params.put("kodepos", ktp.get_kodepos());
                params.put("agama", ktp.get_agama());
                params.put("pend_terakhir", ktp.get_pend_terakhir());
                params.put("status_nikah", ktp.get_status_nikah());
                params.put("pekerjaan", ktp.get_pekerjaan());
                params.put("nomor_rumah_ktp", ktp.get_nomor_rumah_ktp());
                params.put("nomor_hp_ktp", ktp.get_nomor_hp_ktp());
                params.put("nomor_kantor_ktp", ktp.get_nomor_kantor_ktp());
                params.put("nomor_faksimili_ktp", ktp.get_nomor_faksimili_ktp());
                params.put("img", ktp.get_img());
                params.put("img_thumb", ktp.get_img_thumb());
                params.put("nama_img_ktp", ktp.get_nama_img_ktp());
                params.put("nama_img_pas_foto", ktp.get_nama_img_pas_foto());
                params.put("img_pas", ktp.get_img_pas());
                params.put("img_pas_thumb", ktp.get_img_pas_thumb());
                params.put("tanggal_daftar", ktp.get_tanggal_daftar());
                params.put("nik_kta_lama", ktp.get_nik_kta_lama());
                params.put("pengurus", ktp.get_pengurus());
                params.put("kategori", ktp.get_kategori_pengurus());
                params.put("jabatan", ktp.get_jabatan());
                params.put("wilayah_pengurus", ktp.get_wilayah_pengurus());
                params.put("facebook", ktp.get_fb());
                params.put("twitter", ktp.get_twitter());
                params.put("instagram", ktp.get_ig());
                params.put("whatsapp", ktp.get_wa());

                Log.e("data",params.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                return params;
            }
        };
        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }

    public void deleteKtp(final ProgressDialog pDialog, final String idk){
        pDialog.setMessage("Menghapus data...");
        pDialog.show();
        list.setRefreshing(true);
        StringRequest strReq = new StringRequest(Request.Method.POST, UrlConfig.URL_DELETE_KTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");
                    String msg = jObj.getString("message");
                    if (error) {
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }
                    getListKtpPetugasAll(idpNode);
                    list.setRefreshing(false);

                } catch (JSONException e) {
                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    list.setRefreshing(false);
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,error.getMessage(), Toast.LENGTH_LONG).show();
                list.setRefreshing(false);
                pDialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_ktp", idk);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                return params;
            }
        };
        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }

    public void getDetailKtp(final ProgressDialog pDialog, final String idk){

        pDialog.setMessage("Mengambil data...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET, UrlConfig.URL_DETAIL_KTP + idk, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");

                    if (error) {
                        pDialog.dismiss();
                        final ArrayList<Ktp> list = new ArrayList<Ktp>();

                        JSONObject jobObj = jObj.getJSONObject("detail_ktp");

                        String id_ktp_value = jobObj.getString("id_ktp");
                        String id_admin_value = jobObj.getString("id_admin");
                        String id_petugas_value = jobObj.getString("id_petugas");
                        String id_asal_value = jobObj.getString("id_asal");
                        String nik_ktp_value = jobObj.getString("nik_ktp");
                        String nama_ktp_value = jobObj.getString("nama_ktp");
                        String tempat_lahir_value = jobObj.getString("tempat_lahir");
                        String tanggal_lahir_value = jobObj.getString("tanggal_lahir");
                        String jenis_kelamin_value = jobObj.getString("jenis_kelamin");
                        String gol_darah_value = jobObj.getString("gol_darah");
                        String alamat_ktp_value = jobObj.getString("alamat_ktp");
                        String rt_value = jobObj.getString("rt");
                        String rw_value = jobObj.getString("rw");
                        String agama_value = jobObj.getString("agama");
                        String status_nikah_value = jobObj.getString("status_nikah");
                        String pekerjaan_value = jobObj.getString("pekerjaan");
                        String nomor_hp_ktp_value = jobObj.getString("nomor_hp_ktp");
                        String image_id = UrlConfig.MAIN_URL + jobObj.getString("img");
                        String img_id_thumb = UrlConfig.MAIN_URL + jobObj.getString("img_thumb");
                        String kodepos_value = jobObj.getString("kodepos");
                        String pend_terakhir_value = jobObj.getString("pend_terakhir");
                        String email_value = jobObj.getString("email");
                        String nomor_rumah_ktp_value = jobObj.getString("nomor_rumah_ktp");
                        String nomor_kantor_ktp_value = jobObj.getString("nomor_kantor_ktp");
                        String nomor_faksimili_ktp_value = jobObj.getString("nomor_faksimili_ktp");
                        String tanggal_daftar_value = jobObj.getString("tanggal_daftar");
                        String nik_kta_baru_value = jobObj.getString("nik_kta_baru");
                        String pengurus_value = jobObj.getString("pengurus");
                        String kategori_pengurus_value = jobObj.getString("kategori");
                        String jabatan_value = jobObj.getString("jabatan");
                        String wilayah_pengurus_value = jobObj.getString("wilayah_pengurus");
                        String fb_value = jobObj.getString("facebook");
                        String twitter_value = jobObj.getString("twitter");
                        String ig_value = jobObj.getString("instagram");
                        String wa_value = jobObj.getString("whatsapp");
                        String image_pas = UrlConfig.MAIN_URL + jobObj.getString("img_pas");
                        String image_pas_thumb = UrlConfig.MAIN_URL + jobObj.getString("img_pas_thumb");
                        String provinsi_asal_value = jobObj.getString("provinsi_asal");
                        String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                        String kecamatan_asal_value = jobObj.getString("kecamatan_asal");
                        String kelurahan_asal_value = jobObj.getString("kelurahan_asal");
                        String provinsi_pengurus_value = jobObj.getString("provinsi_pengurus");
                        String kabupaten_pengurus_value = jobObj.getString("kabupaten_pengurus");
                        String kecamatan_pengurus_value = jobObj.getString("kecamatan_pengurus");
                        String kelurahan_pengurus_value = jobObj.getString("kelurahan_pengurus");
                        String tanggal_post = jobObj.getString("tanggal_post");
                        String nama_admin = jobObj.getString("nama_admin");
                        String nama_petugas = jobObj.getString("nama_petugas");
                        String nama_pekerjaan_value = jobObj.getString("nama_pekerjaan");
                        String status_mutasi_value = jobObj.getString("status_mutasi");

                        final Ktp ktp= new Ktp();

                        ktp.set_id_ktp(id_ktp_value);
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
                        ktp.set_img_thumb(img_id_thumb);
                        ktp.set_kodepos(kodepos_value);
                        ktp.set_pend_terakhir(pend_terakhir_value);
                        ktp.set_email(email_value);
                        ktp.set_nomor_rumah_ktp(nomor_rumah_ktp_value);
                        ktp.set_nomor_kantor_ktp(nomor_kantor_ktp_value);
                        ktp.set_nomor_faksimili_ktp(nomor_faksimili_ktp_value);
                        ktp.set_tanggal_daftar(tanggal_daftar_value);
                        ktp.set_nik_kta_baru(nik_kta_baru_value);
                        ktp.set_pengurus(pengurus_value);
                        ktp.set_kategori_pengurus(kategori_pengurus_value);
                        ktp.set_jabatan(jabatan_value);
                        ktp.set_wilayah_pengurus(wilayah_pengurus_value);
                        ktp.set_fb(fb_value);
                        ktp.set_twitter(twitter_value);
                        ktp.set_ig(ig_value);
                        ktp.set_wa(wa_value);
                        ktp.set_img_pas(image_pas);
                        ktp.set_img_pas_thumb(image_pas_thumb);
                        ktp.set_provinsi_asal(provinsi_asal_value);
                        ktp.set_kabupaten_asal(kabupaten_asal_value);
                        ktp.set_kecamatan_asal(kecamatan_asal_value);
                        ktp.set_kelurahan_asal(kelurahan_asal_value);
                        ktp.set_provinsi_pengurus(provinsi_pengurus_value);
                        ktp.set_kabupaten_pengurus(kabupaten_pengurus_value);
                        ktp.set_kecamatan_pengurus(kecamatan_pengurus_value);
                        ktp.set_kelurahan_pengurus(kelurahan_pengurus_value);
                        ktp.set_nama_admin(nama_admin);
                        ktp.set_nama_petugas(nama_petugas);
                        ktp.set_tanggal_post(tanggal_post);
                        ktp.set_nama_pekerjaan(nama_pekerjaan_value);
                        ktp.set_status_mutasi(status_mutasi_value);

                        list.add(ktp);

                        Intent intent = new Intent(mContext, DetailKtpActivity.class);
                        intent.putExtra("getdetail", list);
                        mContext.startActivity(intent);

                    } else {
                        String msg = jObj.getString("message");
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        });
        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }

    public void getEditKtp(final ProgressDialog pDialog, final String idk, final String stat){

        pDialog.setMessage("Mengambil data...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.GET, UrlConfig.URL_EDIT_KTP + idk, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");

                    if (error) {
                        pDialog.dismiss();
                        final ArrayList<Ktp> list = new ArrayList<Ktp>();

                        JSONObject jobObj = jObj.getJSONObject("detail_ktp");

                        String id_ktp_value = jobObj.getString("id_ktp");
                        String id_admin_value = jobObj.getString("id_admin");
                        String id_petugas_value = jobObj.getString("id_petugas");
                        String id_asal_value = jobObj.getString("id_asal");
                        String nik_ktp_value = jobObj.getString("nik_ktp");
                        String nama_ktp_value = jobObj.getString("nama_ktp");
                        String tempat_lahir_value = jobObj.getString("tempat_lahir");
                        String tanggal_lahir_value = jobObj.getString("tanggal_lahir");
                        String jenis_kelamin_value = jobObj.getString("jenis_kelamin");
                        String gol_darah_value = jobObj.getString("gol_darah");
                        String alamat_ktp_value = jobObj.getString("alamat_ktp");
                        String rt_value = jobObj.getString("rt");
                        String rw_value = jobObj.getString("rw");
                        String agama_value = jobObj.getString("agama");
                        String status_nikah_value = jobObj.getString("status_nikah");
                        String pekerjaan_value = jobObj.getString("pekerjaan");
                        String nomor_hp_ktp_value = jobObj.getString("nomor_hp_ktp");
                        String image_id = jobObj.getString("img");
                        String img_id_thumb = jobObj.getString("img_thumb");
                        String kodepos_value = jobObj.getString("kodepos");
                        String pend_terakhir_value = jobObj.getString("pend_terakhir");
                        String email_value = jobObj.getString("email");
                        String nomor_rumah_ktp_value = jobObj.getString("nomor_rumah_ktp");
                        String nomor_kantor_ktp_value = jobObj.getString("nomor_kantor_ktp");
                        String nomor_faksimili_ktp_value = jobObj.getString("nomor_faksimili_ktp");
                        String tanggal_daftar_value = jobObj.getString("tanggal_daftar");
                        String nik_kta_baru_value = jobObj.getString("nik_kta_baru");
                        String pengurus_value = jobObj.getString("pengurus");
                        String kategori_pengurus_value = jobObj.getString("kategori");
                        String jabatan_value = jobObj.getString("jabatan");
                        String wilayah_pengurus_value = jobObj.getString("wilayah_pengurus");
                        String fb_value = jobObj.getString("facebook");
                        String twitter_value = jobObj.getString("twitter");
                        String ig_value = jobObj.getString("instagram");
                        String wa_value = jobObj.getString("whatsapp");
                        String image_pas = jobObj.getString("img_pas");
                        String image_pas_thumb = jobObj.getString("img_pas_thumb");
                        String provinsi_asal_value = jobObj.getString("provinsi_asal");
                        String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                        String kecamatan_asal_value = jobObj.getString("kecamatan_asal");
                        String kelurahan_asal_value = jobObj.getString("kelurahan_asal");
                        String provinsi_pengurus_value = jobObj.getString("provinsi_pengurus");
                        String kabupaten_pengurus_value = jobObj.getString("kabupaten_pengurus");
                        String kecamatan_pengurus_value = jobObj.getString("kecamatan_pengurus");
                        String kelurahan_pengurus_value = jobObj.getString("kelurahan_pengurus");
                        String tanggal_post = jobObj.getString("tanggal_post");
                        String nama_admin = jobObj.getString("nama_admin");
                        String nama_petugas = jobObj.getString("nama_petugas");
                        String nama_pekerjaan_value = jobObj.getString("nama_pekerjaan");

                        final Ktp ktp= new Ktp();

                        ktp.set_id_ktp(id_ktp_value);
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
                        ktp.set_img_thumb(img_id_thumb);
                        ktp.set_kodepos(kodepos_value);
                        ktp.set_pend_terakhir(pend_terakhir_value);
                        ktp.set_email(email_value);
                        ktp.set_nomor_rumah_ktp(nomor_rumah_ktp_value);
                        ktp.set_nomor_kantor_ktp(nomor_kantor_ktp_value);
                        ktp.set_nomor_faksimili_ktp(nomor_faksimili_ktp_value);
                        ktp.set_tanggal_daftar(tanggal_daftar_value);
                        ktp.set_nik_kta_baru(nik_kta_baru_value);
                        ktp.set_pengurus(pengurus_value);
                        ktp.set_kategori_pengurus(kategori_pengurus_value);
                        ktp.set_jabatan(jabatan_value);
                        ktp.set_wilayah_pengurus(wilayah_pengurus_value);
                        ktp.set_fb(fb_value);
                        ktp.set_twitter(twitter_value);
                        ktp.set_ig(ig_value);
                        ktp.set_wa(wa_value);
                        ktp.set_img_pas(image_pas);
                        ktp.set_img_pas_thumb(image_pas_thumb);
                        ktp.set_provinsi_asal(provinsi_asal_value);
                        ktp.set_kabupaten_asal(kabupaten_asal_value);
                        ktp.set_kecamatan_asal(kecamatan_asal_value);
                        ktp.set_kelurahan_asal(kelurahan_asal_value);
                        ktp.set_provinsi_pengurus(provinsi_pengurus_value);
                        ktp.set_kabupaten_pengurus(kabupaten_pengurus_value);
                        ktp.set_kecamatan_pengurus(kecamatan_pengurus_value);
                        ktp.set_kelurahan_pengurus(kelurahan_pengurus_value);
                        ktp.set_nama_admin(nama_admin);
                        ktp.set_nama_petugas(nama_petugas);
                        ktp.set_tanggal_post(tanggal_post);
                        ktp.set_nama_pekerjaan(nama_pekerjaan_value);

                        list.add(ktp);

                        Intent intent = new Intent(mContext, EditKtpActivity.class);
                        intent.putExtra("getedit", list);

                        mContext.startActivity(intent);
                        Log.e("edit",jobObj.toString());
                    } else {
                        pDialog.dismiss();
                        String msg = jObj.getString("message");
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.e("edit",e.getMessage().toString());
                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("edit", error.getMessage().toString());
                Toast.makeText(mContext,error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        });
        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }

    public void updateKtp(final ProgressDialog pDialog, final Ktp ktp){
        pDialog.setMessage("Mengubah ktp ...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, urlKtp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");
                    String msg = jObj.getString("message");
                    if (error) {
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                        Intent addIntent = new Intent(mContext, ListKtpPetugasFragment.class);
                        ((Activity) mContext).setResult(REQUEST_OK,addIntent);
                        ((Activity) mContext).finish();
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }

                }
                catch (JSONException e) {

                    Toast.makeText(mContext, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    pDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mContext,error.getMessage(), Toast.LENGTH_LONG).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_ktp", ktp.get_id_ktp());
                params.put("id_admin", ktp.get_id_admin());
                params.put("nama_petugas", ktp.get_id_petugas());
                params.put("id_asal", ktp.get_id_asal());
                params.put("nama_ktp", ktp.get_nama_ktp());
                params.put("nik_ktp", ktp.get_nik_ktp());
                params.put("tempat_lahir", ktp.get_tempat_lahir());
                params.put("tanggal_lahir",  ktp.get_tanggal_lahir());
                params.put("jenis_kelamin", ktp.get_jenis_kelamin());
                params.put("gol_darah",  ktp.get_gol_darah());
                params.put("email", ktp.get_email());
                params.put("alamat_ktp",  ktp.get_alamat_ktp());
                params.put("rt", ktp.get_rt());
                params.put("rw", ktp.get_rw());
                params.put("kodepos", ktp.get_kodepos());
                params.put("agama", ktp.get_agama());
                params.put("pend_terakhir", ktp.get_pend_terakhir());
                params.put("status_nikah", ktp.get_status_nikah());
                params.put("pekerjaan", ktp.get_pekerjaan());
                params.put("nomor_rumah_ktp", ktp.get_nomor_rumah_ktp());
                params.put("nomor_hp_ktp", ktp.get_nomor_hp_ktp());
                params.put("nomor_kantor_ktp", ktp.get_nomor_kantor_ktp());
                params.put("nomor_faksimili_ktp", ktp.get_nomor_faksimili_ktp());
                params.put("img", ktp.get_img());
                params.put("img_thumb", ktp.get_img_thumb());
                params.put("nama_img_ktp", ktp.get_nama_img_ktp());
                params.put("nama_img_pas_foto", ktp.get_nama_img_pas_foto());
                params.put("img_pas", ktp.get_img_pas());
                params.put("img_pas_thumb", ktp.get_img_pas_thumb());
                params.put("nik_kta_lama", ktp.get_nik_kta_lama());
                params.put("pengurus", ktp.get_pengurus());
                params.put("kategori", ktp.get_kategori_pengurus());
                params.put("jabatan", ktp.get_jabatan());
                params.put("wilayah_pengurus", ktp.get_wilayah_pengurus());
                params.put("facebook", ktp.get_fb());
                params.put("twitter", ktp.get_twitter());
                params.put("instagram", ktp.get_ig());
                params.put("whatsapp", ktp.get_wa());
                params.put("nama_img_id", ktp.get_nama_img_id());
                params.put("nama_img_pas", ktp.get_nama_img_pas());
                Log.e("param",params.toString());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                Log.e("param",params.toString());
                params.put("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
                return params;
            }

        };
        AppRequest.getInstance().addToRequestQueue(strReq, getdata);
    }

}
