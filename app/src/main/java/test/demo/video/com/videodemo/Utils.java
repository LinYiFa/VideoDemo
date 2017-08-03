package test.demo.video.com.videodemo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panghetun on 2017/8/3.
 * ${CLASS}
 */

public class Utils {

    // 多权限请求
    public static boolean checkSystemPermission(Context context, final Activity activity, String[] permission, final int REQUEST_CODE_ASK_PERMISSIONS){
        boolean isShowPermissionRationale = false;
        final List<String> permissionList = new ArrayList<String>();
        for (int i = 0 ; i < permission.length; i++) {
            if (ContextCompat.checkSelfPermission (context, permission[i]) != PackageManager.PERMISSION_GRANTED ){
                permissionList.add(permission[i]);
                if (!ActivityCompat.shouldShowRequestPermissionRationale (activity, permission[i])){
                    isShowPermissionRationale = true;
                }
            }
        }

        if (permissionList.size() > 0 ) {
            if (isShowPermissionRationale) {
                showMessageOKCancel (context, "need permission for app",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat. requestPermissions(activity,
                                        permissionList.toArray( new String[ permissionList.size()]),
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(activity,
                    permissionList.toArray(new String[permissionList.size()]),
                    REQUEST_CODE_ASK_PERMISSIONS);
            return false ;
        }
        return true;
    }

    /**
     * 检测和请求 6.0 系统权限
     */
    public static boolean checkSystemPermission(Context context, final Activity activity, final String permission, final int REQUEST_CODE_ASK_PERMISSIONS){
        int hasPermission = ContextCompat.checkSelfPermission(context, permission);
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale (activity, permission)) {
                showMessageOKCancel (context, "need permission for app",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat. requestPermissions(activity,
                                        new String[] { permission},
                                        REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return false ;
        }
        return true;
    }

    public static void showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
