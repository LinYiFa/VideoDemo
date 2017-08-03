package test.demo.video.com.videodemo;

import android.Manifest;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Button start; // 开始录制按钮
    private Button stop; // 停止录制按钮
    private MediaRecorder mediarecorder; // 录制视频的类
    private SurfaceView surfaceview; // 显示视频的控件
    // 用来显示视频的一个接口
    private SurfaceHolder surfaceHolder;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        // 设置横屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 选择支持半透明模式,在有surfaceview的activity中使用。
        getWindow().setFormat(PixelFormat.TRANSLUCENT);*/
        setContentView(R.layout.activity_main);
        init();
        requestPermission();
    }

    private void init() {
        start = (Button) this.findViewById(R.id.start);
        stop = (Button) this.findViewById(R.id.stop);
        start.setOnClickListener(new TestVideoListener());
        stop.setOnClickListener(new TestVideoListener());
        surfaceview = (SurfaceView) this.findViewById(R.id.surfaceview);
        SurfaceHolder holder = surfaceview.getHolder();// 取得holder
        holder.addCallback(this); // holder加入回调接口
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void requestPermission() {
        String [] permission = new String[] {
                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        Utils.checkSystemPermission(this, MainActivity.this, permission, 1011);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 请求动态权限结果回调

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    class TestVideoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start:
                    mediarecorder = new MediaRecorder();// 创建mediarecorder对象
                    // 设置录制视频源为Camera(相机)
                    mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
                    mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    // 设置音频记录的音频源
                    mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    // 设置录制的视频编码h263 h264
                    mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                    // 设置音频记录的编码格式
                    mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
                    mediarecorder.setVideoSize(640, 480);
                    // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
                    mediarecorder.setVideoFrameRate(18);
                    // 设置视频的预览界面
                    mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
                    //设置最大录制时间
                    // mediarecorder.setMaxDuration(1000*10);
                    // 设置视频文件输出的路径
                    mediarecorder.setOutputFile("/sdcard/love.3gp");
                    try {
                        // 准备录制
                        mediarecorder.prepare();
                        // 开始录制
                        mediarecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                case R.id.stop:
                    if (mediarecorder != null) {
                        // 停止录制
                        mediarecorder.stop();
                        // 释放资源
                        mediarecorder.release();
                        mediarecorder = null;
                    }
                    break;
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 将holder，这个holder为开始在oncreat里面取得的holder，将它赋给surfaceHolder
        surfaceHolder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // surfaceDestroyed的时候同时对象设置为null
        surfaceview = null;
        surfaceHolder = null;
        mediarecorder = null;
    }
}
