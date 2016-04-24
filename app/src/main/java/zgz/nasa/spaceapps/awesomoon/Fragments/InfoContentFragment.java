package zgz.nasa.spaceapps.awesomoon.Fragments;

import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
    private List<Information> content_info = new ArrayList<Information>();
    private static String selectedInfo = null;
    private View vista;
    private String titulo = "Information";
    public static final String ARG_INFO = "vacio";

    public InfoContentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.entry_info_content, container, false);

        //Obtener todos los datos sobre las playlist existentes
        mdb = new DbAdapter(getContext());
        InfoFactory inFa = new InfoFactory(mdb);
        int id = getArguments().getInt(ARG_INFO);
        content_info = inFa.getContentInformation(id);

        String titleInfo = content_info.get(0).getTitleInfo();
        getActivity().setTitle(titleInfo);

        ImageView imagenInfo = (ImageView) view.findViewById(R.id.imgeView_img_content);
        imagenInfo.setImageResource(R.drawable.nasa570x450);

        String bodyInfo = content_info.get(0).getBodyInfo();
        TextView txtBodyInfo = (TextView) view.findViewById(R.id.textView_info_content);
        txtBodyInfo.setText(bodyInfo);

        vista=view;
        return view;
    }


}
