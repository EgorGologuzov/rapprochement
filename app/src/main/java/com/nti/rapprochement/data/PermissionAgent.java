package com.nti.rapprochement.data;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nti.rapprochement.MainActivity;

import java.util.ArrayList;

public class PermissionAgent {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    public static final int RECORD_AUDIO_PERMISSION_REQUEST_CODE = 400;

    private static MainActivity mainActivity;
    private static ArrayList<PermissionAgent> cameraAwaitList;

    public static void init(MainActivity mainActivity) {
        PermissionAgent.mainActivity = mainActivity;
        cameraAwaitList = new ArrayList<>();
    }

    public static void notifyPermissionRequestResult(int requestCode, int resultCode) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            cameraAwaitList.forEach(agent -> {
                if (resultCode == PackageManager.PERMISSION_GRANTED && agent.onCameraGranted != null) {
                    agent.onCameraGranted.run();
                } else if (resultCode == PackageManager.PERMISSION_DENIED && agent.onCameraDenied != null) {
                    agent.onCameraDenied.run();
                }
            });
            cameraAwaitList.clear();
        }
    }

    private Runnable onCameraGranted;
    private Runnable onCameraDenied;

    public void requestPermissionCamera(Runnable onGranted, Runnable onDenied) {
        if (hasPermissionCamera()) {
            if (onGranted != null) {
                onGranted.run();
            }
            return;
        }

        ActivityCompat.requestPermissions(
                mainActivity,
                new String[] { Manifest.permission.CAMERA },
                CAMERA_PERMISSION_REQUEST_CODE
        );

        this.onCameraGranted = onGranted;
        this.onCameraDenied = onDenied;

        cameraAwaitList.add(this);
    }

    public boolean hasPermissionCamera() {
        return ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void dispose() {
        cameraAwaitList.remove(this);
    }
}
