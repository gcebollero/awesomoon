package zgz.nasa.spaceapps.awesomoon;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import zgz.nasa.spaceapps.awesomoon.Factorys.QuestionFactory;
import zgz.nasa.spaceapps.awesomoon.Tipes.Question;


/**
 * Fragmento para el contenido principal
 */
public class QuestionFragment extends Fragment {
    /**
     * Este argumento del fragmento representa el título de cada
     * sección
     */
    public static final String ARG_LAYOUT = "Layout";


    private static float score = 0;
    private static int selected=1,correct=-1;
    TextView text;
    RadioButton rd1;
    RadioButton rd2;
    RadioButton rd3;
    RadioButton rd4;
    private QuestionFactory qf;
    public QuestionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_row, container, false);
        DbAdapter db = new DbAdapter(getContext());
        if(!db.isOpen())db.open();
        qf = new QuestionFactory(db);
        final RatingBar stars = (RatingBar) view.findViewById(R.id.ratingBar);
        stars.setRating(score);
        Drawable drawable = stars.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#FFCC00"), PorterDuff.Mode.SRC_ATOP);
        Button b = (Button) view.findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(selected==correct){
                    score+=1;
                }else{
                    score-=0.5;
                }
                if(score<0) score=0;
                if (score>5)score=5;
                if(score==5){
                    Toast.makeText(getContext(),"Hurray! Maximum score!",Toast.LENGTH_SHORT).show();
                }
                stars.setRating(score);
                changeQuestion();
            }
        });
        rd1 = (RadioButton) view.findViewById(R.id.radio_p1);
        rd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               selected=0;
            }
        });
        rd2 = (RadioButton) view.findViewById(R.id.radio_p2);
        rd2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected=1;
            }
        });
        rd3 = (RadioButton) view.findViewById(R.id.radio_p3);
        rd3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected=2;
            }
        });
        rd4 = (RadioButton) view.findViewById(R.id.radio_p4);
        rd4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected=3;
            }
        });
        text = (TextView) view.findViewById(R.id.question);
        changeQuestion();


        return view;
    }
    private void changeQuestion(){
        rd1.setChecked(true);
        Question q = qf.getRandomQuestion();
        Random rnd = new Random();
        ArrayList<RadioButton> sec = new ArrayList<>();
        sec.add(rd1);
        sec.add(rd2);
        sec.add(rd3);
        sec.add(rd4);
        correct = rnd.nextInt(sec.size());
        text.setText(q.getText());
        sec.remove(correct).setText(q.getCorrect());
        sec.remove(rnd.nextInt(sec.size())).setText(q.getError1());
        sec.remove(rnd.nextInt(sec.size())).setText(q.getError2());
        sec.remove(rnd.nextInt(sec.size())).setText(q.getError3());
    }
}
