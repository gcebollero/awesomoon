package zgz.nasa.spaceapps.awesomoon.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zgz.nasa.spaceapps.awesomoon.CustomAdapter.InfoAdapter;
import zgz.nasa.spaceapps.awesomoon.Factorys.InfoFactory;
import zgz.nasa.spaceapps.awesomoon.R;
import zgz.nasa.spaceapps.awesomoon.Tipes.Information;

/**
 * Created by dani on 23/04/16.
 */
public class InformationFragment extends Fragment{

    private ListView lista;
    private DbAdapter mdb;
    private InfoAdapter adaptador;
    private List<Information> dates_info = new ArrayList<Information>();
    private static String selectedInfo = null;
    private View vista;
    public InformationFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_info, container, false);
        getActivity().setTitle("Playlists");

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_informacion);

        //Montar listado de playlist
        montarListView(view);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Information selected = (Information) pariente.getItemAtPosition(posicion);
                Bundle args = new Bundle();
//                args.putString(SongsFragment.ARG_PLAYLIST, elegido.getTituloPlaylist());
//                SongsFragment f = new SongsFragment();
//                f.setArguments(args);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.content_frame, f).addToBackStack(null).commit();

            }
        });
        vista=view;
        return view;
    }

    /**
     * Incluye en el listview el contenido de la base de datos
     * @param view
     */
    private void montarListView(View view){

        //Obtener todos los datos sobre las playlist existentes
        //mdb = new DbAdapter(getContext());
        InfoFactory plf = new InfoFactory(/*mdb*/);
        dates_info = plf.getAllPlaylist();
        //Creacion de lista en el listview
        lista = (ListView) view.findViewById(R.id.listview_playlist);

        lista.setAdapter(adaptador = new InfoAdapter(view.getContext(), R.layout.entry_info, (ArrayList<?>) info_playlist) {

            /*
            * COMPRUEBA QUE EXISTEN LOS ITEMS CORRECTOS ANTES DE INSERTAR CUALQUIER ELEMENTO
            * Una vez realizada la comprobacion inserta la informacion
            */
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {

                    ImageView imagen_info = (ImageView) view.findViewById(R.id.imgeView_img_info);
                    if (imagen_info != null)
                        imagen_info.setImageResource(((Information) entrada).getIdImageInfo());

                    TextView titulo_info = (TextView) view.findViewById(R.id.textView_info_titulo);
                    if (titulo_info != null)
                        titulo_info.setText(((Information) entrada).getTitleInfo());
                }
            }

        });
    }

}
