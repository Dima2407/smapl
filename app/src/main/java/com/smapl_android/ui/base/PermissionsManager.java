package com.smapl_android.ui.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class PermissionsManager {

    private AtomicInteger permissionRequestId = new AtomicInteger();

    private Map<Integer, SimpleOnPermissionsRequestListener> permissionRunners = new HashMap<>();

    public final boolean hasPermissions(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {

                return false;
            }
        }
        return true;
    }


    public final void runWithPermissions(Activity activity, OnPermissionsRequestListener listener, String[] permissionsMandatory, String[] permissionsOptional) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        fillPermissionsToRequest(activity, permissionsMandatory, permissionsToRequest);
        if (permissionsOptional != null) {
            fillPermissionsToRequest(activity, permissionsOptional, permissionsToRequest);
        }
        if (permissionsToRequest.size() != 0) {
            int requestId = permissionRequestId.getAndIncrement();
            permissionRunners.put(requestId, new SimpleOnPermissionsRequestListener(permissionsMandatory, listener));
            ActivityCompat.requestPermissions(activity,
                    permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                    requestId);
        } else {
            listener.onSuccess();
        }
    }

    void fillPermissionsToRequest(Activity activity, String[] permissions, List<String> permissionsToRequest) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {

                permissionsToRequest.add(permission);
            }
        }
    }

    final void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            SimpleOnPermissionsRequestListener runnable = permissionRunners.get(requestCode);
            if (runnable != null) {
                runnable.process(permissions, grantResults);
            }
        } finally {
            permissionRunners.remove(requestCode);
        }
    }

    private static class SimpleOnPermissionsRequestListener {

        private final OnPermissionsRequestListener listener;
        private List<String> mandatory;

        private SimpleOnPermissionsRequestListener(String[] mandatory, OnPermissionsRequestListener listener) {
            this.mandatory = Arrays.asList(mandatory);
            this.listener = listener;
        }

        private SimpleOnPermissionsRequestListener(OnPermissionsRequestListener listener) {
            this.listener = listener;
        }

        private void process(String[] permissions, int[] grantResults) {
            for (int i = 0; i < grantResults.length; i++) {
                int grantResult = grantResults[i];
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    String permission = permissions[i];
                    if (mandatory.contains(permission)) {
                        if (listener != null) {
                            listener.onFail();
                        }
                        return;
                    }
                }
            }
            if (listener != null) {
                listener.onSuccess();
            }
        }

    }
}
