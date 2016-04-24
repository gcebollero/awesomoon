package zgz.nasa.spaceapps.awesomoon;

import android.content.Context;

import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CameraFindExtractionFragment extends Fragment {

    private CameraExtraction mCameraExtraction;
    Camera mCamera;
    int mNumberOfCameras;
    int cameraId;
    int rotation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCameraExtraction = new CameraExtraction(
                this.getActivity().getBaseContext(),
                this.getActivity().getWindowManager().getDefaultDisplay().getRotation()
        );

        // Find the total number of cameras available
        mNumberOfCameras = Camera.getNumberOfCameras();

        // Find the ID of the rear-facing ("default") camera
        android.hardware.Camera.CameraInfo cameraInfo = new android.hardware.Camera.CameraInfo();
        for (int i = 0; i < mNumberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)               {
        return mCameraExtraction;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Use mCurrentCamera to select the camera desired to safely restore
        // the fragment after the camera has been changed
        try {
            mCamera = Camera.open(cameraId);
        } catch (Exception e) {
            Log.d("Exception opening", "Exception S");
        }
        mCameraExtraction.setCamera(mCamera);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mCamera != null)
        {
            mCamera.stopPreview();
            mCamera.release();
        }
    }


    // Modo en el que se pinta la cámara: encajada por dentro o saliendo los bordes por fuera.
    public enum CameraViewMode {

        /**
         * Inner mode
         */
        Inner,
        /**
         * Outer mode
         */
        Outer
    }
}