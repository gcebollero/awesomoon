package zgz.nasa.spaceapps.awesomoon.CustomAdapter;

import android.widget.BaseAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dani on 23/04/16.
 */
public abstract class InfoAdapter extends BaseAdapter{
    private ArrayList<?> list_info;
    private int R_layout_IdView;
    private Context contexto;

    public InfoAdapter(Context contexto, int R_layout_IdView, ArrayList<?> list_info) {
        super();
        this.contexto = contexto;
        this.list_info = list_info;
        this.R_layout_IdView = R_layout_IdView;
    }

    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        onEntrada (list_info.get(posicion), view);
        return view;
    }

    @Override
    public int getCount() {

        return list_info.size();
    }

    @Override
    public Object getItem(int posicion) {

        return list_info.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {

        return posicion;
    }

    /** Devuelve cada una de las entradas con cada una de las vistas a la que debe de ser asociada
     * @param entrada La entrada que será la asociada a la view. La entrada es del tipo del paquete/handler
     * @param view View particular que contendrá los datos del paquete/handler
     */
    public abstract void onEntrada (Object entrada, View view);

}
