package com.bttm.booktothemoon;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class RadarActivity extends AppCompatActivity  implements SensorEventListener {
    //Datos
    private double sensorRange = 0.6;
    private SensorManager mSensorManager;
    Integer fragmentId;
    Sensor accelerometer;
    Sensor magnetometer;
    Float pitch;
    Float roll;
    Float azimut;
    MediaPlayer mPlayer;

    //vista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mPlayer = MediaPlayer.create(this.getApplicationContext(), R.raw.ping);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //mPlayer.setLooping(true);

        setContentView(R.layout.activity_radar);

        Fragment cameraFragment = new CameraFindExtractionFragment();

        fragmentId = getFragmentManager().beginTransaction().add(R.id.mainLayout, new CameraFindExtractionFragment()).commit();
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    private void getSensorData() {
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    float[] mGravity;
    float[] mGeomagnetic;

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
