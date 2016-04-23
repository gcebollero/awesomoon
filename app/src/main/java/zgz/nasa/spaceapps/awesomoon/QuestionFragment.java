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
import android.widget.Toast;


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
    private static int selected=-1;
    public QuestionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_row, container, false);
        final RatingBar stars = (RatingBar) view.findViewById(R.id.ratingBar);
        stars.setRating(score);
        Drawable drawable = stars.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#FFCC00"), PorterDuff.Mode.SRC_ATOP);
        Button b = (Button) view.findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                score+=0.5;
                if(score>5){
                    score=0;
                }
                stars.setRating(score);
                Toast.makeText(getContext(),""+selected,Toast.LENGTH_SHORT).show();
            }
        });
        RadioButton rd1 = (RadioButton) view.findViewById(R.id.radio_p1);
        rd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               selected=1;
            }
        });
        RadioButton rd2 = (RadioButton) view.findViewById(R.id.radio_p2);
        rd2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected=2;
            }
        });
        RadioButton rd3 = (RadioButton) view.findViewById(R.id.radio_p3);
        rd3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected=3;
            }
        });
        RadioButton rd4 = (RadioButton) view.findViewById(R.id.radio_p4);
        rd4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selected=4;
            }
        });



        return view;
    }

}
