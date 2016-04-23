package zgz.nasa.spaceapps.awesomoon.Factorys;

import zgz.nasa.spaceapps.awesomoon.Tipes.Information;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dani on 23/04/16.
 */
public class InfoFactory {

    //private DbAdapter db;

    /**
     * Constructor de la clase
     * @param db adaptador de la base de datos
     */
    public InfoFactory(/*DbAdapter db*/){
        //this.db = db;
    }

    private List<Information> extractInformation(Cursor mCursor){
        List<Information> lista = new ArrayList<Information>();
        List<Information> head = new ArrayList<Information>();
        if (mCursor.moveToFirst()) {
            do {
                //Para cada fila de la base de datos, obtenemos todos los campos
                String titleInfo; //= mCursor.getString(mCursor.getColumnIndex(DbAdapter.KEY_NOMBRE_PLAYLIST));
                String imgInfo; //= mCursor.getString(mCursor.getColumnIndex(DbAdapter.KEY_NUM_CANCIONES));


            } while (mCursor.moveToNext());
        }
        //Terminamos de usar el cursor
        mCursor.close();
        head.addAll(lista);
        return (List) head;
    }

    public List<Information> getAllInformation(){
        if(!db.isOpen())
            db.open();
        return extractInformation(db.getAllInformation());
    }

}
