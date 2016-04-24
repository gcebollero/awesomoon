package zgz.nasa.spaceapps.awesomoon.Fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zgz.nasa.spaceapps.awesomoon.CustomAdapter.DbAdapter;
import zgz.nasa.spaceapps.awesomoon.CustomAdapter.InfoAdapter;
import zgz.nasa.spaceapps.awesomoon.Factorys.InfoFactory;
import zgz.nasa.spaceapps.awesomoon.R;
import zgz.nasa.spaceapps.awesomoon.Tipes.Information;

/**
 * Created by dani on 24/04/16.
 */
public class InfoContentFragment extends Fragment {

    private ListView lista;
    private DbAdapter mdb;
    private InfoAdapter adaptador;
    private List<Information> dates_info = new ArrayList<Information>();
    private static String selectedInfo = null;
    private View vista;
    public static final int ARG_INFO = 0;

    public InfoContentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_info_content, container, false);

//        if(!mdb.isOpen()){
//            mdb.open();
//        }
//        String titulo = String.valueOf(mdb.getTitleInformation(ARG_INFO));
        getActivity().setTitle("titulo");

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_informacion);

        //Montar listado de playlist
        montarContenido(view);

        vista=view;
        return view;
    }

    /**
     * Incluye en el listview el contenido de la base de datos
     * @param view
     */
    private void montarContenido(View view){

        //Obtener todos los datos sobre las playlist existentes
        mdb = new DbAdapter(getContext());
        TextView body_info = (TextView) view.findViewById(R.id.textView_info_content);

        if(!mdb.isOpen()){
            mdb.open();
        }
        body_info.setText((CharSequence) mdb.getBodyInformation(ARG_INFO));
        
    }


}
