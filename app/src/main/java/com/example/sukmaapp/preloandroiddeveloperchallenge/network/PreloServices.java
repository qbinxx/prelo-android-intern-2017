package com.example.sukmaapp.preloandroiddeveloperchallenge.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sukmaapp.preloandroiddeveloperchallenge.utils.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sogeking on 10/28/2017.
 */

public class PreloServices {
    private String baseUrl = "https://dev.prelo.id/";

    private String loginUrl = "api/auth/login";
    private String loveListUrl = "api/me/lovelist";

    public interface VolleyCallback{
        void onSuccess(String result);
    }

    /**
     * Deskripsi:
     * Melakukan Request HTTP Post ke PreloService dengan param username_or_email dan password
     * Hasil request dikembalikan melalui Objek VolleyCallback yang akan ditindak lanjuti di Activity Login
     */

    public void login(String username,String password,final VolleyCallback callback){
        HashMap<String, String> params = new HashMap<>();
        params.put("username_or_email", username);
        params.put("password", password);

        JSONObject param = new JSONObject(params);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                baseUrl+loginUrl, param,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("LOGIN",response.toString());
                        callback.onSuccess(response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR_LOGIN",error.toString());
                        String response = convertErrorToString(error);
                        callback.onSuccess(response);
                    }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    public void getLovelist(final String token, final VolleyCallback callback) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                baseUrl+loveListUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GetLoveList",error.toString());
                String response = convertErrorToString(error);
                callback.onSuccess(response);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Token "+token);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }

        };
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                7000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    /**
     * Deskripsi :  Konversi error ke string berdasarkan jenis error yang diterima
     * @param error Objek error yang ingin dikonversi
     * @return String yang telah terkonversi sesuai jenis errornya
     */
    private String convertErrorToString(VolleyError error){
        String errorMessage = null;

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){

            errorMessage = new String(response.data);
            errorMessage = trimMessage(errorMessage, "_message");

        }else{
            Log.d("ERROR",error.toString());
            if (error instanceof NetworkError) {
                errorMessage = "Network problem error";
            } else if (error instanceof ServerError) {
                errorMessage = "Server problem error";
            } else if (error instanceof AuthFailureError) {
                errorMessage = "AuthFailure problem error";
            } else if (error instanceof ParseError) {
                errorMessage = "Parse error";
            } else if (error instanceof NoConnectionError) {
                errorMessage = "NoConnection error";
            } else if (error instanceof TimeoutError) {
                errorMessage = "TimeOut error";
            } else {
                errorMessage = "Unknown error";
            }
        }

        return errorMessage;
    }

    /**
     * Deskripsi : Mengambil pesan error yg spesifik berdasarkan key dari response server
     * @param errorMessage errorMessage yang diterima dari server
     * @param key key yg berisi pesan error tertentu
     * @return String dimana sesuai dengan param key yg diambil dari response
     */

    private String trimMessage(String errorMessage, String key){
        String trimmedString;

        try{
            JSONObject obj = new JSONObject(errorMessage);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
}
