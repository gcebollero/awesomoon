package zgz.nasa.spaceapps.awesomoon.Fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import zgz.nasa.spaceapps.awesomoon.CustomAdapter.DbAdapter;
import zgz.nasa.spaceapps.awesomoon.Factorys.QuestionFactory;
import zgz.nasa.spaceapps.awesomoon.R;

/**
 * Created by Alba on 24/04/2016.
 */
public class YourWeightFragment extends Fragment {


    public static final double comp = 0.166;
    EditText et1;
    TextView result;
    public YourWeightFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weight_layout, container, false);
        getActivity().setTitle("Your weight on the moon");
        Button b = (Button) view.findViewById(R.id.btw);
        et1=(EditText) view.findViewById(R.id.editW);
        result = (TextView) view.findViewById(R.id.resultw);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                String aux=et1.getText().toString();
                double weight=Double.parseDouble(aux);
                double moonWeight= weight*comp;
                result.setText(String.format("%.2f", moonWeight));


            }
        });
        return view;
    }

}