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

import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import skripsi.code.ektp.adapter.KtpMutasiListAdapter;
import skripsi.code.ektp.data.UrlConfig;
import skripsi.code.ektp.helper.AppRequest;
import skripsi.code.ektp.model.Ktp;
import skripsi.code.ektp.model.Mutasi;
import skripsi.code.ektp.view.activity.DetailKtpActivity;
import skripsi.code.ektp.view.activity.DetailMutasiActivity;
import skripsi.code.ektp.view.activity.EditKtpActivity;
import skripsi.code.ektp.view.activity.MainActivityAdmin;
import skripsi.code.ektp.view.fragment.ListKtpMutasiKeluarFragment;
import skripsi.code.ektp.view.fragment.ListKtpMutasiMasukFragment;
import skripsi.code.ektp.view.fragment.ListKtpPetugasFragment;

/**
 * Created by TONY on 1/2/2017.
 */

public class ControllerKtpMutasi {
    private Context mContext;
    private String urlKtp;
    private ArrayList<Mutasi> mutasiList = new ArrayList<Mutasi>();
    private SwipeRefreshLayout list;
    private KtpMutasiListAdapter adapters;
    private String getdata = "getdataktp";
    private int limit_masuk = 3;
    private int limit_keluar = 3;
    private String idpNode;

    private static final int REQUEST_ACTION = 100;
    private static final int REQUEST_OK = 200;

    public ControllerKtpMutasi(Context c, String url, SwipeRefreshLayout lst) {
        this.mContext= c;
        this.urlKtp = url;
        this.list = lst;
    }

    public void setAdapter(KtpMutasiListAdapter adt){
        this.adapters=adt;
    }
    public KtpMutasiListAdapter getAdapter(){
        return adapters;
    }

    public void getListMutasiKtpMasuk(final String idp){

        getList().clear();
        list.setRefreshing(true);
        String url = urlKtp +idp+"/limit/"+limit_masuk;
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

                            String id_mutasi_value = jobObj.getString("id_mutasi");
                            String id_anggota_value = jobObj.getString("id_anggota");
                            String id_admin_value = jobObj.getString("id_admin");
                            String id_region_asal_value = jobObj.getString("id_region_asal");
                            String id_region_tujuan_value = jobObj.getString("id_region_tujuan");
                            String nama_anggota_value = jobObj.getString("nama_anggota");
                            String nik_ktp_value = jobObj.getString("nik_ktp");
                            String nomor_hp_value = jobObj.getString("nomor_hp");
                            String keterangan_value = jobObj.getString("keterangan");
                            String status_pengurus_value = jobObj.getString("status_pengurus");
                            String status_mutasi_value = jobObj.getString("status_mutasi");
                            String no_kta_lama_value = jobObj.getString("no_kta_lama");
                            String no_kta_baru_value = jobObj.getString("no_kta_baru");
                            String tgl_pengajuan_mutasi_value = jobObj.getString("tgl_pengajuan_mutasi");
                            String tgl_konfirmasi_mutasi_value = jobObj.getString("tgl_konfirmasi_mutasi");
                            String provinsi_asal_value = jobObj.getString("provinsi_asal");
                            String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                            String provinsi_tujuan_value = jobObj.getString("provinsi_tujuan");
                            String kabupaten_tujuan_value = jobObj.getString("kabupaten_tujuan");

                            final Mutasi mut = new Mutasi();

                            mut.set_id_mutasi(id_mutasi_value);
                            mut.set_id_anggota(id_anggota_value);
                            mut.set_id_admin(id_admin_value);
                            mut.set_id_region_asal(id_region_asal_value);
                            mut.set_id_region_tujuan(id_region_tujuan_value);
                            mut.set_nama_anggota(nama_anggota_value);
                            mut.set_nik_ktp(nik_ktp_value);
                            mut.set_nomor_hp(nomor_hp_value);
                            mut.set_keterangan(keterangan_value);
                            mut.set_status_pengurus(status_pengurus_value);
                            mut.set_status_mutasi(status_mutasi_value);
                            mut.set_no_kta_lama(no_kta_lama_value);
                            mut.set_no_kta_baru(no_kta_baru_value);
                            mut.set_tgl_pengajuan_mutasi(tgl_pengajuan_mutasi_value);
                            mut.set_tgl_konfirmasi_mutasi(tgl_konfirmasi_mutasi_value);
                            mut.set_provinsi_asal(provinsi_asal_value);
                            mut.set_kabupaten_asal(kabupaten_asal_value);
                            mut.set_provinsi_tujuan(provinsi_tujuan_value);
                            mut.set_kabupaten_tujuan(kabupaten_tujuan_value);

                            mutasiList.add(0, mut);

                            if (no >= limit_masuk)
                                limit_masuk = limit_masuk + 4;
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


    public void getListMutasiKtpMasukCacheAll(final String idp) {

        getList().clear();
        list.setRefreshing(true);
        idpNode=idp;

        String url = urlKtp +idp+"/limit/"+limit_masuk;
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

                    String id_mutasi_value = jobObj.getString("id_mutasi");
                    String id_anggota_value = jobObj.getString("id_anggota");
                    String id_admin_value = jobObj.getString("id_admin");
                    String id_region_asal_value = jobObj.getString("id_region_asal");
                    String id_region_tujuan_value = jobObj.getString("id_region_tujuan");
                    String nama_anggota_value = jobObj.getString("nama_anggota");
                    String nik_ktp_value = jobObj.getString("nik_ktp");
                    String nomor_hp_value = jobObj.getString("nomor_hp");
                    String keterangan_value = jobObj.getString("keterangan");
                    String status_pengurus_value = jobObj.getString("status_pengurus");
                    String status_mutasi_value = jobObj.getString("status_mutasi");
                    String no_kta_lama_value = jobObj.getString("no_kta_lama");
                    String no_kta_baru_value = jobObj.getString("no_kta_baru");
                    String tgl_pengajuan_mutasi_value = jobObj.getString("tgl_pengajuan_mutasi");
                    String tgl_konfirmasi_mutasi_value = jobObj.getString("tgl_konfirmasi_mutasi");
                    String provinsi_asal_value = jobObj.getString("provinsi_asal");
                    String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                    String provinsi_tujuan_value = jobObj.getString("provinsi_tujuan");
                    String kabupaten_tujuan_value = jobObj.getString("kabupaten_tujuan");

                    final Mutasi mut = new Mutasi();

                    mut.set_id_mutasi(id_mutasi_value);
                    mut.set_id_anggota(id_anggota_value);
                    mut.set_id_admin(id_admin_value);
                    mut.set_id_region_asal(id_region_asal_value);
                    mut.set_id_region_tujuan(id_region_tujuan_value);
                    mut.set_nama_anggota(nama_anggota_value);
                    mut.set_nik_ktp(nik_ktp_value);
                    mut.set_nomor_hp(nomor_hp_value);
                    mut.set_keterangan(keterangan_value);
                    mut.set_status_pengurus(status_pengurus_value);
                    mut.set_status_mutasi(status_mutasi_value);
                    mut.set_no_kta_lama(no_kta_lama_value);
                    mut.set_no_kta_baru(no_kta_baru_value);
                    mut.set_tgl_pengajuan_mutasi(tgl_pengajuan_mutasi_value);
                    mut.set_tgl_konfirmasi_mutasi(tgl_konfirmasi_mutasi_value);
                    mut.set_provinsi_asal(provinsi_asal_value);
                    mut.set_kabupaten_asal(kabupaten_asal_value);
                    mut.set_provinsi_tujuan(provinsi_tujuan_value);
                    mut.set_kabupaten_tujuan(kabupaten_tujuan_value);

                    mutasiList.add(0, mut);

                    if (no >= limit_masuk)
                        limit_masuk = limit_masuk+4;
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

    public void getListMutasiKtpKeluar(final String idp){

        getList().clear();
        list.setRefreshing(true);
        String url = urlKtp +idp+"/limit/"+limit_keluar;
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
                    }
                    JSONArray jsonAr = jObj.getJSONArray("result");
                    int no =1;
                    for (int i = 0; i < jsonAr.length(); i++) {

                        JSONObject jobObj = jsonAr.getJSONObject(i);

                        String id_mutasi_value = jobObj.getString("id_mutasi");
                        String id_anggota_value = jobObj.getString("id_anggota");
                        String id_admin_value = jobObj.getString("id_admin");
                        String id_region_asal_value = jobObj.getString("id_region_asal");
                        String id_region_tujuan_value = jobObj.getString("id_region_tujuan");
                        String nama_anggota_value = jobObj.getString("nama_anggota");
                        String nik_ktp_value = jobObj.getString("nik_ktp");
                        String nomor_hp_value = jobObj.getString("nomor_hp");
                        String keterangan_value = jobObj.getString("keterangan");
                        String status_pengurus_value = jobObj.getString("status_pengurus");
                        String status_mutasi_value = jobObj.getString("status_mutasi");
                        String no_kta_lama_value = jobObj.getString("no_kta_lama");
                        String no_kta_baru_value = jobObj.getString("no_kta_baru");
                        String tgl_pengajuan_mutasi_value = jobObj.getString("tgl_pengajuan_mutasi");
                        String tgl_konfirmasi_mutasi_value = jobObj.getString("tgl_konfirmasi_mutasi");
                        String provinsi_asal_value = jobObj.getString("provinsi_asal");
                        String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                        String provinsi_tujuan_value = jobObj.getString("provinsi_tujuan");
                        String kabupaten_tujuan_value = jobObj.getString("kabupaten_tujuan");

                        final Mutasi mut = new Mutasi();

                        mut.set_id_mutasi(id_mutasi_value);
                        mut.set_id_anggota(id_anggota_value);
                        mut.set_id_admin(id_admin_value);
                        mut.set_id_region_asal(id_region_asal_value);
                        mut.set_id_region_tujuan(id_region_tujuan_value);
                        mut.set_nama_anggota(nama_anggota_value);
                        mut.set_nik_ktp(nik_ktp_value);
                        mut.set_nomor_hp(nomor_hp_value);
                        mut.set_keterangan(keterangan_value);
                        mut.set_status_pengurus(status_pengurus_value);
                        mut.set_status_mutasi(status_mutasi_value);
                        mut.set_no_kta_lama(no_kta_lama_value);
                        mut.set_no_kta_baru(no_kta_baru_value);
                        mut.set_tgl_pengajuan_mutasi(tgl_pengajuan_mutasi_value);
                        mut.set_tgl_konfirmasi_mutasi(tgl_konfirmasi_mutasi_value);
                        mut.set_provinsi_asal(provinsi_asal_value);
                        mut.set_kabupaten_asal(kabupaten_asal_value);
                        mut.set_provinsi_tujuan(provinsi_tujuan_value);
                        mut.set_kabupaten_tujuan(kabupaten_tujuan_value);

                        mutasiList.add(0, mut);

                        if (no >= limit_keluar)
                            limit_keluar = limit_keluar+4;
                        no++;

                    }
                    getAdapter().notifyDataSetChanged();
                    list.setRefreshing(false);

                } catch (JSONException e) {

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


    public void getListMutasiKtpKeluarCacheAll(final String idp) {

        getList().clear();
        list.setRefreshing(true);
        idpNode=idp;

        String url = urlKtp +idp+"/limit/"+limit_keluar;
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

                    String id_mutasi_value = jobObj.getString("id_mutasi");
                    String id_anggota_value = jobObj.getString("id_anggota");
                    String id_admin_value = jobObj.getString("id_admin");
                    String id_region_asal_value = jobObj.getString("id_region_asal");
                    String id_region_tujuan_value = jobObj.getString("id_region_tujuan");
                    String nama_anggota_value = jobObj.getString("nama_anggota");
                    String nik_ktp_value = jobObj.getString("nik_ktp");
                    String nomor_hp_value = jobObj.getString("nomor_hp");
                    String keterangan_value = jobObj.getString("keterangan");
                    String status_pengurus_value = jobObj.getString("status_pengurus");
                    String status_mutasi_value = jobObj.getString("status_mutasi");
                    String no_kta_lama_value = jobObj.getString("no_kta_lama");
                    String no_kta_baru_value = jobObj.getString("no_kta_baru");
                    String tgl_pengajuan_mutasi_value = jobObj.getString("tgl_pengajuan_mutasi");
                    String tgl_konfirmasi_mutasi_value = jobObj.getString("tgl_konfirmasi_mutasi");
                    String provinsi_asal_value = jobObj.getString("provinsi_asal");
                    String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                    String provinsi_tujuan_value = jobObj.getString("provinsi_tujuan");
                    String kabupaten_tujuan_value = jobObj.getString("kabupaten_tujuan");

                    final Mutasi mut = new Mutasi();

                    mut.set_id_mutasi(id_mutasi_value);
                    mut.set_id_anggota(id_anggota_value);
                    mut.set_id_admin(id_admin_value);
                    mut.set_id_region_asal(id_region_asal_value);
                    mut.set_id_region_tujuan(id_region_tujuan_value);
                    mut.set_nama_anggota(nama_anggota_value);
                    mut.set_nik_ktp(nik_ktp_value);
                    mut.set_nomor_hp(nomor_hp_value);
                    mut.set_keterangan(keterangan_value);
                    mut.set_status_pengurus(status_pengurus_value);
                    mut.set_status_mutasi(status_mutasi_value);
                    mut.set_no_kta_lama(no_kta_lama_value);
                    mut.set_no_kta_baru(no_kta_baru_value);
                    mut.set_tgl_pengajuan_mutasi(tgl_pengajuan_mutasi_value);
                    mut.set_tgl_konfirmasi_mutasi(tgl_konfirmasi_mutasi_value);
                    mut.set_provinsi_asal(provinsi_asal_value);
                    mut.set_kabupaten_asal(kabupaten_asal_value);
                    mut.set_provinsi_tujuan(provinsi_tujuan_value);
                    mut.set_kabupaten_tujuan(kabupaten_tujuan_value);

                    mutasiList.add(0, mut);

                    if (no >= limit_keluar)
                        limit_keluar = limit_keluar+4;
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


    public void getDetailMutasi(final ProgressDialog pDialog, final String idm, final String type){

        pDialog.setMessage("Mengambil data...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET, UrlConfig.URL_GET_DETAIL_MUTASI + idm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");

                    if (error) {
                        pDialog.dismiss();
                        final ArrayList<Mutasi> list = new ArrayList<Mutasi>();
                        JSONObject jobObj = jObj.getJSONObject("detail_mutasi");

                        String id_mutasi_value = jobObj.getString("id_mutasi");
                        String id_anggota_value = jobObj.getString("id_anggota");
                        String id_admin_value = jobObj.getString("id_admin");
                        String id_region_asal_value = jobObj.getString("id_region_asal");
                        String id_region_tujuan_value = jobObj.getString("id_region_tujuan");
                        String nama_anggota_value = jobObj.getString("nama_anggota");
                        String nik_ktp_value = jobObj.getString("nik_ktp");
                        String nomor_hp_value = jobObj.getString("nomor_hp");
                        String keterangan_value = jobObj.getString("keterangan");
                        String status_pengurus_value = jobObj.getString("status_pengurus");
                        String status_mutasi_value = jobObj.getString("status_mutasi");
                        String no_kta_lama_value = jobObj.getString("no_kta_lama");
                        String no_kta_baru_value = jobObj.getString("no_kta_baru");
                        String tgl_pengajuan_mutasi_value = jobObj.getString("tgl_pengajuan_mutasi");
                        String tgl_konfirmasi_mutasi_value = jobObj.getString("tgl_konfirmasi_mutasi");
                        String provinsi_asal_value = jobObj.getString("provinsi_asal");
                        String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                        String provinsi_tujuan_value = jobObj.getString("provinsi_tujuan");
                        String kabupaten_tujuan_value = jobObj.getString("kabupaten_tujuan");

                        final Mutasi mut = new Mutasi();

                        mut.set_id_mutasi(id_mutasi_value);
                        mut.set_id_anggota(id_anggota_value);
                        mut.set_id_admin(id_admin_value);
                        mut.set_id_region_asal(id_region_asal_value);
                        mut.set_id_region_tujuan(id_region_tujuan_value);
                        mut.set_nama_anggota(nama_anggota_value);
                        mut.set_nik_ktp(nik_ktp_value);
                        mut.set_nomor_hp(nomor_hp_value);
                        mut.set_keterangan(keterangan_value);
                        mut.set_status_pengurus(status_pengurus_value);
                        mut.set_status_mutasi(status_mutasi_value);
                        mut.set_no_kta_lama(no_kta_lama_value);
                        mut.set_no_kta_baru(no_kta_baru_value);
                        mut.set_tgl_pengajuan_mutasi(tgl_pengajuan_mutasi_value);
                        mut.set_tgl_konfirmasi_mutasi(tgl_konfirmasi_mutasi_value);
                        mut.set_provinsi_asal(provinsi_asal_value);
                        mut.set_kabupaten_asal(kabupaten_asal_value);
                        mut.set_provinsi_tujuan(provinsi_tujuan_value);
                        mut.set_kabupaten_tujuan(kabupaten_tujuan_value);

                        list.add(mut);

                        Intent intent = new Intent(mContext, DetailMutasiActivity.class);
                        intent.putExtra("id_mutasi", idm);
                        intent.putExtra("type", type);
                        intent.putExtra("getdetail", list);
                        mContext.startActivity(intent);

                    } else {
                        pDialog.dismiss();
                        String msg = jObj.getString("message");
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


    public void getEditKtpMutasi(final ProgressDialog pDialog, final String idk, final String stat){

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
                        final ArrayList<Mutasi> list = new ArrayList<Mutasi>();
                        JSONObject jobObj = jObj.getJSONObject("detail_ktp");

                        String id_mutasi_value = jobObj.getString("id_mutasi");
                        String id_anggota_value = jobObj.getString("id_anggota");
                        String id_admin_value = jobObj.getString("id_admin");
                        String id_region_asal_value = jobObj.getString("id_region_asal");
                        String id_region_tujuan_value = jobObj.getString("id_region_tujuan");
                        String nama_anggota_value = jobObj.getString("nama_anggota");
                        String nik_ktp_value = jobObj.getString("nik_ktp");
                        String nomor_hp_value = jobObj.getString("nomor_hp");
                        String keterangan_value = jobObj.getString("keterangan");
                        String status_pengurus_value = jobObj.getString("status_pengurus");
                        String status_mutasi_value = jobObj.getString("status_mutasi");
                        String no_kta_lama_value = jobObj.getString("no_kta_lama");
                        String no_kta_baru_value = jobObj.getString("no_kta_baru");
                        String tgl_pengajuan_mutasi_value = jobObj.getString("tgl_pengajuan_mutasi");
                        String tgl_konfirmasi_mutasi_value = jobObj.getString("tgl_konfirmasi_mutasi");
                        String provinsi_asal_value = jobObj.getString("provinsi_asal");
                        String kabupaten_asal_value = jobObj.getString("kabupaten_asal");
                        String provinsi_tujuan_value = jobObj.getString("provinsi_tujuan");
                        String kabupaten_tujuan_value = jobObj.getString("kabupaten_tujuan");

                        final Mutasi mut = new Mutasi();

                        mut.set_id_mutasi(id_mutasi_value);
                        mut.set_id_anggota(id_anggota_value);
                        mut.set_id_admin(id_admin_value);
                        mut.set_id_region_asal(id_region_asal_value);
                        mut.set_id_region_tujuan(id_region_tujuan_value);
                        mut.set_nama_anggota(nama_anggota_value);
                        mut.set_nik_ktp(nik_ktp_value);
                        mut.set_nomor_hp(nomor_hp_value);
                        mut.set_keterangan(keterangan_value);
                        mut.set_status_pengurus(status_pengurus_value);
                        mut.set_status_mutasi(status_mutasi_value);
                        mut.set_no_kta_lama(no_kta_lama_value);
                        mut.set_no_kta_baru(no_kta_baru_value);
                        mut.set_tgl_pengajuan_mutasi(tgl_pengajuan_mutasi_value);
                        mut.set_tgl_konfirmasi_mutasi(tgl_konfirmasi_mutasi_value);
                        mut.set_provinsi_asal(provinsi_asal_value);
                        mut.set_kabupaten_asal(kabupaten_asal_value);
                        mut.set_provinsi_tujuan(provinsi_tujuan_value);
                        mut.set_kabupaten_tujuan(kabupaten_tujuan_value);

                        list.add(mut);

                        Intent intent = new Intent(mContext, EditKtpActivity.class);
                        intent.putExtra("getedit", list);
                        mContext.startActivity(intent);

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

    public void updateKtpMutasi(final ProgressDialog pDialog, final Ktp ktp){
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


    public void konfirmasiKtpMutasi(final ProgressDialog pDialog, final String mut, final String status){
        pDialog.setMessage("Konfirmasi mutasi ...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, UrlConfig.URL_GET_KONFIRM_MUTASI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");
                    String msg = jObj.getString("message");

                    if (error) {
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                        getList().clear();

                        if (status.equals("detail")) {
                            Intent addIntent = new Intent(mContext, ListKtpMutasiMasukFragment.class);
                            ((Activity) mContext).setResult(REQUEST_OK,addIntent);
                            ((Activity) mContext).finish();
                        } else {
                            list.setRefreshing(true);
                            getListMutasiKtpMasuk(idpNode);
                            list.setRefreshing(false);
                        }

                    } else {
                        pDialog.dismiss();
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }

                    Log.e("respons",response.toString());
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
                params.put("id_mutasi", mut.toString());
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

    public void deleteKtpMutasi(final ProgressDialog pDialog, final String mut){
        pDialog.setMessage("Delete mutasi ...");
        pDialog.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, UrlConfig.URL_GET_DELETE_MUTASI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("status");
                    String msg = jObj.getString("message");
                    if (error) {
                        Intent addIntent = new Intent(mContext, ListKtpMutasiKeluarFragment.class);
                        ((MainActivityAdmin) mContext).setResult(REQUEST_OK,addIntent);
                        ((MainActivityAdmin) mContext).replaceFragment(MainActivityAdmin.TAG_MUTASI_KELUAR, 3);
                    } else {
                        Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                    }
                    pDialog.dismiss();
                    Log.e("respons",response.toString());
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
                params.put("id_mutasi", mut.toString());
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

    public List<Mutasi> getList() {
        return mutasiList;
    }


}
