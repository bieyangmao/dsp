package dsp;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class echoes a string called from JavaScript.
 */
public class dsp extends CordovaPlugin {
    public static final int RECORD_SYSTEM_VIDEO = 1;



    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            this.reconverIntent(callbackContext);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

	public void reconverIntent(CallbackContext callbackContext) {
		
		  Uri fileUri = Uri.fromFile(getOutputMediaFile());
          Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
          intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);     //限制的录制时长 以秒为单位
//        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1024);        //限制视频文件大小 以字节为单位
//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);      //设置拍摄的质量0~1
//        intent.putExtra(MediaStore.EXTRA_FULL_SCREEN, false);        // 全屏设置

         this.cordova.startActivityForResult(this, intent, 1);
          callbackContext.success(getOutputMediaFile().toString());

    }

    private File getOutputMediaFile() {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
             return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }

}
