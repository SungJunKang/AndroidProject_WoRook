package sungjunkang.worook;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.speech.tts.TextToSpeech.ERROR;

public class CardFragment extends Fragment {

    private CardView cardView;

    TextView card_ENG, card_KOR;

    private TextToSpeech myTTS;


    public static Fragment getInstance(int position, WordList_Item item) {
        CardFragment f = new CardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putSerializable("item", item);
        f.setArguments(args);
        return f;
    }

    @SuppressLint("DefaultLocale")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager, container, false);

        WordList_Item bundle = (WordList_Item) getArguments().getSerializable("item");

        cardView = view.findViewById(R.id.cardView);
        card_ENG = view.findViewById(R.id.card_ENG);
        card_KOR = view.findViewById(R.id.card_KOR);

        card_KOR.setText(bundle.getWord_KOR());
        card_ENG.setText(bundle.getWord_ENG());

        card_ENG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTTS.speak(card_ENG.getText().toString(), TextToSpeech.QUEUE_ADD, null, null);
            }
        });

        card_KOR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTTS.speak(card_KOR.getText().toString(), TextToSpeech.QUEUE_ADD, null, null);
            }
        });

        myTTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != ERROR) {
                    myTTS.setLanguage(Locale.KOREA);
                }
            }
        });

        return view;
    }

    public CardView getCardView() {
        return cardView;
    }
}
