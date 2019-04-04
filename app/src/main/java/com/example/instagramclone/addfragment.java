package com.example.instagramclone;


import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class addfragment extends Fragment {


    public addfragment() {
        // Required empty public constructor
    }

    TextInputEditText ingambar, inCaption, inUser;
    Button btnSimpan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_addfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inCaption = view.findViewById(R.id.incaption);
        ingambar = view.findViewById(R.id.ingambar);
        inUser = view.findViewById(R.id.inuser);
        btnSimpan = view.findViewById(R.id.btnsimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private Map<String, String> insertData() {
        if (TextUtils.isEmpty(Objects.requireNonNull(ingambar.getText()).toString())){
            ingambar.setError("Gambar harus diisi");
            ingambar.requestFocus();
        }else if (TextUtils.isEmpty(Objects.requireNonNull(inUser.getText()).toString())){
            inUser.setError("User harus diisi");
            inUser.requestFocus();
        }else {
            String URL = "https://webalizainal.000webhostapp.com/";

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Menyimpan data");
            progressDialog.setMessage("Tunggu sebentar ...");
            progressDialog.show();

            StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            Log.d("Log","onResponse:" + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String hasil = jsonObject.getString("hasil");
                                String pesan = jsonObject.getString("pesan");
                                if (hasil.equalsIgnoreCase("true")){
                                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_LONG).show();
                                    ((MainActivity) Objects.requireNonNull(getActivity())).openFragment(new HomeFragment());
                                }else {
                                    Toast.makeText(getActivity(), pesan, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("log", "onResponse:" + e.getMessage());
                            }
                        }
                    }); new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Terjadi kesalahan, coba lagi", Toast.LENGTH_LONG).show();
                    Log.e("log", "onErrorResponse: " + error.getMessage() );
                }
            }) {
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> parameter = new HashMap<>();
                    parameter.put("gambar", ingambar.getText().toString());
                    parameter.put("caption", Objects.requireNonNull(inCaption.getText()).toString());
                    parameter.put("iduser", inUser.getText().toString());

                    return parameter;
            }
        }

            RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
            requestQueue.add(jsonObjectRequest);
    }
}
