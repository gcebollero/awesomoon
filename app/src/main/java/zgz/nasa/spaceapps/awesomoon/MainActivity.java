package zgz.nasa.spaceapps.awesomoon;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import zgz.nasa.spaceapps.awesomoon.CustomAdapter.DbAdapter;
import zgz.nasa.spaceapps.awesomoon.Fragments.AboutFragment;
import zgz.nasa.spaceapps.awesomoon.Fragments.GaleryFragment;
import zgz.nasa.spaceapps.awesomoon.Fragments.InformationListFragment;
import zgz.nasa.spaceapps.awesomoon.Fragments.QuestionFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //RADAR
    private double sensorRange = 0.6;
    private SensorManager mSensorManager;
    Integer fragmentId;
    Sensor accelerometer;
    Sensor magnetometer;
    Float pitch;
    Float roll;
    Float azimut;
    float[] mGravity;
    float[] mGeomagnetic;
    MediaPlayer mPlayer;
    //RADAR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DbAdapter mDb = new DbAdapter(getApplicationContext());
        mDb.open();

        //RADAR
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.ping);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //RADAR

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            UpdateDB dbupdate = new UpdateDB();
            dbupdate.init(getApplicationContext());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment f = new Fragment();
        Bundle args = new Bundle();
        switch(id){
            case R.id.nav_galeria:
                f = new GaleryFragment();
                break;
            case R.id.nav_informacion:
                f = new InformationListFragment();
                break;
            case R.id.nav_juegos:
                f = new QuestionFragment();
                break;
            case R.id.nav_mapa:
                f = new MapFragment();
                break;
            case R.id.nav_radar:
                f = new CameraFindExtractionFragment();
                break;
            case R.id.nav_update:
                UpdateDB u = new UpdateDB();
                u.init(getApplicationContext());
                Toast.makeText(getApplicationContext(),"Downloading new content...",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_about:
                f = new AboutFragment();
                break;
        }
        f.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, f).addToBackStack(null).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
                pitch = orientation[1];
                roll = orientation[2];
                locateMoon(orientation);
                Log.d("Azimuth: ", String.valueOf(azimut));
                Log.d("Pitch: ", String.valueOf(pitch));
                Log.d("Roll: ", String.valueOf(roll));
            }
        }
    }

    private void locateMoon(float[] orientation) {
        //Añado un error para trabajar con rango de sensores
        float[] moonOrientation = getMoonOrientation();
        float [] distance = new float[3];
        distance[0] = orientation[0] - moonOrientation[0];
        distance[1] = orientation[1] - moonOrientation[1];
        distance[2] = orientation[2] - moonOrientation[2];

        //Se cambia los valores negativos a positivos, es distancia al fin y al cabo
        if (distance[0]<0)
            distance[0]=(distance[0]);

        if (distance[1]<0)
            distance[1]=(distance[1]);

        if (distance[2]<0)
            distance[2]=(distance[2]);

        reproduceSound(distance);
    }

    private float[] getMoonOrientation() {
        float moonOrientation[] = new float[3];
        //azimut
        moonOrientation[0] = 0;
        //pitch
        moonOrientation[1] = 0;
        //roll
        moonOrientation[2] = 0;
        return moonOrientation;
    }

    private void reproduceSound(float[] distance) {
        //Log.d("------", "------");
        //Log.d("0,Azimuth Distance ", String.valueOf(azimut));
        //Log.d("1,Pitch  Distance ", String.valueOf(pitch));
        //Log.d("2,Roll  Distance ", String.valueOf(roll));
        //Log.d("------", "------");
        if (distance[0] < sensorRange &&
                distance[1] < sensorRange &&
                distance[2] < sensorRange){
            this.mPlayer.setLooping(true);
            mPlayer.start();
            //this.getFragmentManager().getFragment;
        } else {
            mPlayer.stop();
        }
    }
}
