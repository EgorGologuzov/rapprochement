package com.nti.rapprochement.components;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.nti.rapprochement.MainActivity;
import com.nti.rapprochement.R;
import com.nti.rapprochement.data.Camera;
import com.nti.rapprochement.models.RecordMultiMode;
import com.nti.rapprochement.utils.ViewsUtils;

import java.util.Arrays;

public class RModeInputGestureVF extends ViewFactoryBase {
    private final RecordMultiMode model;

    public RModeInputGestureVF(RecordMultiMode model) {
        this.model = model;
    }

    @Override
    public View create(ViewGroup parent) {
        View view = ViewsUtils.createView(R.layout.rmode_input_gesture, parent);
        return view;
    }
}
