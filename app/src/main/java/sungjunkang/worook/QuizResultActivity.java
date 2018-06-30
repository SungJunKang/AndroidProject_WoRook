package sungjunkang.worook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

public class QuizResultActivity extends AppCompatActivity {

    int data_right, data_wrong,data_size;
    TextView right_result, wrong_result;
    ArcProgress arcProgress;
    Button main_btn;
    int progress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        Intent intent = new Intent(getIntent());
        data_right = intent.getIntExtra("right_count",0);
        data_wrong = intent.getIntExtra("wrong_count",0);
        data_size = intent.getIntExtra("data_size", 0);
        Log.d("Start","Start");

        right_result = findViewById(R.id.right_result);
        wrong_result = findViewById(R.id.wrong_result);
        arcProgress = findViewById(R.id.progress_answer);
        main_btn = findViewById(R.id.main_btn);

        arcProgress.setMax(100);
        arcProgress.setProgress(0);


        right_result.append(String.valueOf(data_right));
        wrong_result.append(String.valueOf(data_wrong));

        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent_main = new Intent(QuizResultActivity.this, WordList.class);
//                startActivity(intent_main);
//                finish();
                finish();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    progress = data_right * (100 / data_size);
                    arcProgress.setProgress(progress);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
