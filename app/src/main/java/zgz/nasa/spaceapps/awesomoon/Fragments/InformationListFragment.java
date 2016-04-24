package zgz.nasa.spaceapps.awesomoon.Fragments;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import android.support.v4.app.Fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import zgz.nasa.spaceapps.awesomoon.CustomAdapter.InfoAdapter;
import zgz.nasa.spaceapps.awesomoon.CustomAdapter.DbAdapter;
import zgz.nasa.spaceapps.awesomoon.Factorys.InfoFactory;
import zgz.nasa.spaceapps.awesomoon.R;
import zgz.nasa.spaceapps.awesomoon.Tipes.Information;

/**
 * Created by dani on 23/04/16.
 */
public class InformationListFragment extends Fragment{

    private ListView lista;

    ProgressDialog mProgressDialog;
    private static ImageView imagen_info;

    private DbAdapter mdb;
    private InfoAdapter adaptador;
    private List<Information> dates_info = new ArrayList<Information>();
    private static String selectedInfo = null;
    private View vista;
    public InformationListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_info_list, container, false);
        getActivity().setTitle("Information");

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_informacion);

        //Montar listado de playlist
        montarListView(view);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Information selected = (Information) pariente.getItemAtPosition(posicion);
                Bundle args = new Bundle();

                args.putInt(InfoContentFragment.ARG_INFO,selected.getIdInfo());

                InfoContentFragment f = new InfoContentFragment();
                f.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, f).addToBackStack(null).commit();

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
        mdb = new DbAdapter(getContext());
        InfoFactory plf = new InfoFactory(mdb);
        dates_info = plf.getAllInformation();
        //Creacion de lista en el listview
        lista = (ListView) view.findViewById(R.id.listview_playlist);

        lista.setAdapter(adaptador = new InfoAdapter(view.getContext(), R.layout.entry_info_list, (ArrayList<?>) dates_info) {

            /*
            * COMPRUEBA QUE EXISTEN LOS ITEMS CORRECTOS ANTES DE INSERTAR CUALQUIER ELEMENTO
            * Una vez realizada la comprobacion inserta la informacion
            */
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {

                    imagen_info = (ImageView) view.findViewById(R.id.imgeView_img_info);
                    if (imagen_info != null) {
                        //new DownloadImage().execute(((Information) entrada).getURIImageInfo());
                        imagen_info.setImageResource(R.drawable.nasa570x450);
                    }
                    TextView titulo_info = (TextView) view.findViewById(R.id.textView_info_titulo);
                    if (titulo_info != null)
                        titulo_info.setText(((Information) entrada).getTitleInfo());
                }
            }

        });
    }

    // DownloadImage AsyncTask
    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getContext());
            // Set progressdialog title
            mProgressDialog.setTitle(getContext().getString(R.string.downloading));
            // Set progressdialog message
            mProgressDialog.setMessage(getContext().getString(R.string.wait));
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Close progressdialog
            mProgressDialog.dismiss();
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imagen_info.setImageBitmap(result);
        }
    }

}