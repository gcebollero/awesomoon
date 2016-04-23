package zgz.nasa.spaceapps.awesomoon;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by novales35 on 23/04/16.
 */
public class UpdateDB {
    private DbAdapter db;

    public UpdateDB(){
    }
    public void init(Context context){
        db = new DbAdapter(context);
        new DownloadUpdates().execute("https://raw.githubusercontent.com/novales35/awesomoon/master/bd.json");
    }
    private void parseJson(String json){
        if(!db.isOpen()) db.open();
        try {
            JSONObject updates = new JSONObject(json);
            JSONArray preguntas = updates.getJSONArray(db.DATABASE_TABLE_PREGUNTA);
            for(int i = 0; i < preguntas.length();i++){
                JSONObject j = preguntas.getJSONObject(i);
                int id = j.getInt(db.KEY_IDPREG);
                String texto = j.getString(db.Texto);
                String correcta = j.getString(db.Correcta);
                String e1 = j.getString(db.Error1);
                String e2 = j.getString(db.Error2);
                String e3 = j.getString(db.Error3);
                //TODO INSERCCION
            }
            preguntas=null; //liberamos memoria
            JSONArray informacion = updates.getJSONArray(db.DATABASE_TABLE_INFORMACION);
            for(int i = 0; i < informacion.length();i++){
                JSONObject j = informacion.getJSONObject(i);
                int id = j.getInt(db.KEY_IDINFO);
                String titulo = j.getString(db.Titulo);
                String cuerpo = j.getString(db.Cuerpo);
                //todo insert
            }
            informacion=null;
            JSONArray multimedia = updates.getJSONArray(db.DATABASE_TABLE_MULTIMEDIA);
            for(int i = 0; i < multimedia.length();i++){
                JSONObject j = multimedia.getJSONObject(i);
                String tipo = j.getString(db.Tipo);
                String uri = j.getString(db.URI);
                int dur = j.getInt(db.Duracion);
                db.insertMultimedia(tipo, uri, dur);

                Log.d("updater","inserted multimedia item");
            }
            multimedia=null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // DownloadUpdates AsyncTask
    private class DownloadUpdates extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... URL) {

            String url = URL[0];
            String json;
            try {
                // Download json from URL
                InputStream input = new java.net.URL(url).openStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(input,"UTF-8"),8);
                StringBuilder sb = new StringBuilder();
                String line = "";
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                json = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                json=null;
            }
            return json;
        }

        protected void onPostExecute(String jsonResult) {
            parseJson(jsonResult);
        }
    }
}
