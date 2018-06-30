package sungjunkang.worook;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.speech.tts.TextToSpeech.ERROR;

public class WordList extends AppCompatActivity {

    ArrayList<WordList_Item> wordList_items;
    android.support.v7.app.ActionBar actionBar;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            if(keyCode == KeyEvent.KEYCODE_BACK){
                Intent intent = new Intent(WordList.this, WordBook.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        final String token = getIntent().getStringExtra("token");
        final ListView listView = findViewById(R.id.listView);
        final WordListVIewAdapter listViewAdapter = new WordListVIewAdapter(this);
        Button addBtn = findViewById(R.id.add_word_btn);
        Button testBtn = findViewById(R.id.test_word_btn);
        Button studyBtn = findViewById(R.id.study_word_btn);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final Req apiService = retrofit.create(Req.class);
        Call<WordList_ArrayList> res = apiService.getWordList(token);
        res.enqueue(new Callback<WordList_ArrayList>() {
            @Override
            public void onResponse(Call<WordList_ArrayList> call, Response<WordList_ArrayList> response) {
                wordList_items = response.body().getData();

                for(WordList_Item i : wordList_items){
                    listViewAdapter.addItem(i.getWord_ENG(),i.getWord_KOR(),i.getWord_token(),i.getWordbook_token());
                }

                listView.setAdapter(listViewAdapter);
            }

            @Override
            public void onFailure(Call<WordList_ArrayList> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
                Toast.makeText(getApplicationContext(),"Fail to Load Word List",Toast.LENGTH_LONG).show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WordList.this , AddWordList.class);
                i.putExtra("wordbook_token",token);
                startActivity(i);
            }
        });

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WordList.this, Quiz.class);
                i.putExtra("token",token);
                startActivity(i);
            }
        });

        studyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WordList.this, StudyActivity.class);
                i.putExtra("token", token);
                startActivity(i);
            }
        });

    }
}
