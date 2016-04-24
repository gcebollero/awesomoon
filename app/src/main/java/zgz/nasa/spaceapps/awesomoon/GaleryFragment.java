package zgz.nasa.spaceapps.awesomoon;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.ArrayList;

import zgz.nasa.spaceapps.awesomoon.CustomAdapter.DbAdapter;


/**
 * Fragmento para el contenido principal
 */
public class GaleryFragment extends Fragment {
    /**
     * Este argumento del fragmento representa el título de cada
     * sección
     */
    public static final String ARG_LAYOUT = "Layout";
    private static ImageView foto;
    ProgressDialog mProgressDialog;
    private ArrayList<Bitmap> cache;
    private ArrayList<String> uris;
    private int posicion = 0;
    float x1,x2;
    float y1,y2;
    public GaleryFragment() {
        cache = new ArrayList<Bitmap>();
        uris = new ArrayList<String>();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.galery_layout, container, false);
        DbAdapter db = new DbAdapter(getContext());
        if(!db.isOpen())db.open();
        Cursor c = db.getAllImages();
        if (c.moveToFirst()) {
            do {
                uris.add(c.getString(c.getColumnIndex(DbAdapter.URI)));
            } while (c.moveToNext());
        }
        new DownloadImage().execute(new String[]{uris.get(posicion),uris.get(posicion+1)});

        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                    {
                        x1 = event.getX();
                        y1 = event.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        x2 = event.getX();
                        y2 = event.getY();
                        //if left to right sweep event on screen
                        if (x1 < x2){
                            if(posicion>0) {
                                posicion--;
                                new DownloadImage().execute(new String[]{uris.get(posicion)});
                            }
                        }
                        // if right to left sweep event on screen
                        if (x1 > x2){
                            if(posicion<uris.size()-1) {
                                posicion++;
                                new DownloadImage().execute(new String[]{uris.get(posicion)});
                            }
                        }
                        break;
                    }
                }
                return true;
            }
        });

        foto = (ImageView) view.findViewById(R.id.galery_pic);
        return view;
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
            foto.setImageBitmap(result);
        }
    }
}