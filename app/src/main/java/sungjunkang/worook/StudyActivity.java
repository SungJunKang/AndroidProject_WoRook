package sungjunkang.worook;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudyActivity extends AppCompatActivity {

    ViewPager viewPager;

    ArrayList<WordList_Item> arrayList;

    Button study_main_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        viewPager = findViewById(R.id.viewPager);
        study_main_btn = findViewById(R.id.study_main_btn);
        String token = getIntent().getStringExtra("token");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final Req apiService = retrofit.create(Req.class);
        Call<WordList_ArrayList> res = apiService.getWordList(token);
        res.enqueue(new Callback<WordList_ArrayList>() {

            public void onResponse(Call<WordList_ArrayList> call, Response<WordList_ArrayList> response) {
                arrayList = response.body().getData();
                CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), arrayList);
                viewPager.setAdapter(pagerAdapter);
                viewPager.setOffscreenPageLimit(3);
            }

            @Override
            public void onFailure(Call<WordList_ArrayList> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                Toast.makeText(getApplicationContext(),"Fail to Load Word List",Toast.LENGTH_LONG).show();
            }
        });

        study_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
