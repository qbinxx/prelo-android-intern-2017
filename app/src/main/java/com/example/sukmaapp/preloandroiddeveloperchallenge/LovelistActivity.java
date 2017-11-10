package com.example.sukmaapp.preloandroiddeveloperchallenge;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.sukmaapp.preloandroiddeveloperchallenge.adapter.LoveAdapter;
import com.example.sukmaapp.preloandroiddeveloperchallenge.models.Love;
import com.example.sukmaapp.preloandroiddeveloperchallenge.network.PreloServices;
import com.example.sukmaapp.preloandroiddeveloperchallenge.utils.DividerItemDecoration;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LovelistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LoveAdapter loveAdapter;
    private List<Love> loveList = new ArrayList<>();

    public String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lovelist);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        token = getIntent().getStringExtra("token");

        recyclerView = (RecyclerView) findViewById(R.id.rv_love_list);
//        loveAdapter = new LoveAdapter(loveList);

        getLoveList();
    }

    private void getLoveList(){
        PreloServices preloServices = new PreloServices();

        preloServices.getLovelist(token, new PreloServices.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray arrayData = jsonObject.getJSONArray("_data");
                    for (int i=0; i<arrayData.length();i++){
                        Love love = new Love();
                        love.setName(arrayData.getJSONObject(i).getString("name"));
                        love.setPrice(arrayData.getJSONObject(i).getDouble("price"));
                        love.setDisplay_picts(arrayData.getJSONObject(i).getJSONArray("display_picts").getString(0));

                        loveList.add(love);
                    }

                    loveAdapter = new LoveAdapter(loveList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL_LIST));
                    recyclerView.setAdapter(loveAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
