package sungjunkang.worook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Quiz extends AppCompatActivity {

    ArcProgress arcProgressBar;

    TextView quiz;
    EditText answer;
    Button answer_btn;

    int right_count = 0;
    int wrong_count = 0;
    int i = 0;
    int progress = 0;
    int add_progress = 0;

    int arraylist_size = 0;

    ArrayList<WordList_Item> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Log.d("Start","Hello");

        Intent getIntent_data = new Intent(getIntent());
        String token = getIntent_data.getStringExtra("token");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Req.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        final Req apiService = retrofit.create(Req.class);
        Call<WordList_ArrayList> res = apiService.getWordList(token);

        arcProgressBar = findViewById(R.id.progress);
        quiz = findViewById(R.id.quiz);
        answer = findViewById(R.id.answer);
        answer_btn = findViewById(R.id.answer_btn);

        res.enqueue(new Callback<WordList_ArrayList>() {
            @Override
            public void onResponse(Call<WordList_ArrayList> call, Response<WordList_ArrayList> response) {
                arrayList = response.body().getData();
                arraylist_size = arrayList.size();
                add_progress = 100 / arraylist_size;
                quiz.setText(arrayList.get(i).getWord_KOR());
            }

            @Override
            public void onFailure(Call<WordList_ArrayList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fail to Load Quiz",Toast.LENGTH_LONG).show();
                Log.d("ERROR",t.getMessage());
            }
        });

        arcProgressBar.setProgress(progress);

        answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.getText().toString().equals(arrayList.get(i).getWord_ENG())) {
                    Toast.makeText(getApplicationContext(), "정답입니다!", Toast.LENGTH_SHORT).show();
                    right_count++;
                    i++;
                    Log.d("Code","LOGKAT");

                    if(arraylist_size == i){
                        Intent intent = new Intent(Quiz.this, QuizResultActivity.class);
                        intent.putExtra("right_count", right_count);
                        intent.putExtra("wrong_count", wrong_count);
                        intent.putExtra("data_size", arraylist_size);
                        startActivity(intent);
                        finish();
                    }

                    else{
                        answer.setText("");
                        quiz.setText(arrayList.get(i).getWord_KOR());
                        progress = progress + add_progress;
                        arcProgressBar.setProgress(progress);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "오답입니다.", Toast.LENGTH_SHORT).show();
                    wrong_count++;
                    i++;
                    Log.d("Code","LOGKAT");

                    if(arraylist_size == i){
                        Intent intent = new Intent(Quiz.this, QuizResultActivity.class);
                        intent.putExtra("right_count", right_count);
                        intent.putExtra("wrong_count", wrong_count);
                        intent.putExtra("data_size", arraylist_size);
                        startActivity(intent);
                        finish();
                    }

                    else{
                        answer.setText("");
                        quiz.setText(arrayList.get(i).getWord_KOR());
                        progress = progress + add_progress;
                        arcProgressBar.setProgress(progress);
                    }

                }
            }
        });
    }
}