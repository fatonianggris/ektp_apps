package skripsi.code.ektp.controller;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TONY on 12/24/2016.
 */

public class ControllerWilayahKabupaten {

    private ArrayList<String> data= new ArrayList<String>();
    private Context mContext;
    private String url;
    private JSONArray result;

    public ControllerWilayahKabupaten(Context c, String url) {
        this.mContext= c;
        this.url= url;
        setUrl();
    }

    public void setUrl(){

        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {

                            j = new JSONObject(response);
                            result = j.getJSONArray("result");
                            setNamaAdmKabupaten(result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

    public String getId(int position){
        String id="";
        try {

            JSONObject json = result.getJSONObject(position);
            id = json.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return id;
    }

    private void setNamaAdmKabupaten(JSONArray j){

        for(int i=0; i<j.length(); i++){
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                data.add(json.getString("nama")+" ["+json.getString("administratif")+"]");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getList() {
        return data;
    }

}
