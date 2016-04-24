package zgz.nasa.spaceapps.awesomoon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Fragmento para el contenido principal
 */
public class AboutFragment extends Fragment {
    /**
     * Este argumento del fragmento representa el título de cada
     * sección
     */
    public static final String ARG_LAYOUT = "Layout";


    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_layout, container, false);
        ImageView logo = (ImageView) view.findViewById(R.id.space_apps_logo);
        logo.setImageResource(R.drawable.space_apps_logo);
        return view;
    }

}
