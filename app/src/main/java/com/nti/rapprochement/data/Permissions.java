package com.nti.rapprochement.data;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.nti.rapprochement.MainActivity;

public class Permissions {

    public static final int CAMERA_PERMISSION_REQUEST_CODE = 200;
    public static final int RECORD_AUDIO_PERMISSION_REQUEST_CODE = 400;

    private static MainActivity mainActivity;

    public enum Type { Camera, RecordAudio }

    public enum Result { Granted, Denied }

    public static class RequestResult {
        public final Type type;
        public final Result result;
        public RequestResult(Type type, Result result) {
            this.type = type;
            this.result = result;
        }
    }

    public static void init(MainActivity mainActivity) {
        Permissions.mainActivity = mainActivity;
    }

    public static void requestPermissionCamera() {
        if (!hasPermissionCamera()) {
            ActivityCompat.requestPermissions(
                    mainActivity,
                    new String[] { Manifest.permission.CAMERA },
                    Permissions.CAMERA_PERMISSION_REQUEST_CODE
            );
        }
    }

    public static boolean hasPermissionCamera() {
        return ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissionRecordAudio() {
        if (!hasPermissionRecordAudio()) {
            ActivityCompat.requestPermissions(
                    mainActivity,
                    new String[] { Manifest.permission.RECORD_AUDIO },
                    Permissions.RECORD_AUDIO_PERMISSION_REQUEST_CODE
            );
        }
    }

    public static boolean hasPermissionRecordAudio() {
        return ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static Type typeFromRequestCode(int requestCode) {
        switch (requestCode) {
            case CAMERA_PERMISSION_REQUEST_CODE:
                return Type.Camera;
            case RECORD_AUDIO_PERMISSION_REQUEST_CODE:
                return Type.RecordAudio;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static Result resultFromGrantResults(int result) {
        switch (result) {
            case PackageManager.PERMISSION_GRANTED:
                return Result.Granted;
            case PackageManager.PERMISSION_DENIED:
                return Result.Denied;
            default:
                throw new IllegalArgumentException();
        }
    }
}
