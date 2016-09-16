package andrescamargo.info.database1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Dre on 6/9/2016.
 */
public class cam extends Activity implements View.OnClickListener, SurfaceHolder.Callback {
    public static final String EXTRA_PHOTO_FILENAME="info.andrescamargo.android.database1.photo_filename";
    private Camera cam;
    private ImageView image;
    private SurfaceView view;
    private SurfaceHolder holder;
    private Button back,take;
    private View v, bar;
    private Context app=this;
    private Intent i;
    private String completeFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic);
        i=new Intent(this,showPreview.class);
        view=(SurfaceView)findViewById(R.id.pic);
        holder=view.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);
        back=(Button)findViewById(R.id.back);
        take=(Button)findViewById(R.id.take);
        bar=(View)findViewById(R.id.bar);
        bar.setVisibility(View.INVISIBLE);
        back.setOnClickListener(this);
        take.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.take:
                if(cam!=null)
                    cam.takePicture(shutterCallback,null,jpegCallback);
                setResult(Activity.RESULT_OK,i);
                break;
        }
    }
    /***********************************************************************************************
    ***********************************************************************************************
    *CAMERA CAPTURE SET UP
    ***********************************************************************************************
    ***********************************************************************************************
    */
    private Camera.ShutterCallback shutterCallback=new Camera.ShutterCallback(){
        public void onShutter(){
            bar.setVisibility(View.VISIBLE);
        }
    };
    private Camera.PictureCallback jpegCallback=new Camera.PictureCallback(){
        public void onPictureTaken(byte[] data, Camera camera){
            File dir_image2,dir_image;

            dir_image2=new File(Environment.getExternalStorageDirectory()+File.separator+"MyCustomFolder");
            dir_image2.mkdirs();
            String filename= "myFirstWeirdPic.jpg";//UUID.randomUUID().toString()+".jpg";
            completeFileName=dir_image2.toString()+filename;
            File file=new File(dir_image2,filename);
            FileOutputStream os=null;
            boolean success=true;

            try{
                os=new FileOutputStream(file);//openFileOutput(filename,Context.MODE_PRIVATE);
                os.write(data);
            }catch(Exception e){
                Toast.makeText(app,"write error",Toast.LENGTH_SHORT).show();
                success=false;
            }finally {
                try{
                    if(os != null)
                        os.close();
                }catch(Exception e){
                    Toast.makeText(app,"close error",Toast.LENGTH_SHORT).show();
                    success=false;
                }
            }
            if(success) {
                Toast.makeText(app, "picture saved", Toast.LENGTH_SHORT).show();

                //Bitmap bitmap = BitmapFactory.decodeFile(getPic());//(Bitmap)i.get("imageTaken");
                //image.setImageBitmap(bitmap);
                // Intent i=new Intent();
                //i.putExtra(EXTRA_PHOTO_FILENAME, filename);
                //  setResult(Activity.RESULT_OK,i);
                //}else
                //   setResult(Activity.RESULT_CANCELED);
            }
            //i=new Intent();//this, showPreview.class);
            i.putExtra(EXTRA_PHOTO_FILENAME, filename);
            finish();
        }
    };

    /***********************************************************************************************
     ***********************************************************************************************
     *CAMERA PREVIEW SET UP
     ***********************************************************************************************
     ***********************************************************************************************
     */
    @Override
    public void onResume(){
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            cam= Camera.open(0);
        else
            cam=Camera.open();
    }
    @Override
    public void onPause(){
        super.onPause();
        if(cam != null)
        {
            cam.release();
            cam=null;
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            if(cam !=null)
                cam.setPreviewDisplay(holder);
        }catch(IOException e){
            Toast.makeText(this,"preview create error",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(cam ==null )return;

        Camera.Parameters parameters=cam.getParameters();
        //Size s=null;
        Size s=getBest(parameters.getSupportedPictureSizes(),width,height);
        parameters.setPreviewSize(s.width, s.height);
        cam.setDisplayOrientation(90);
        //CAMERA CAPTURE PART FOR SETTING PICTURE SIZE
        s=getBest(parameters.getSupportedPictureSizes(),width,height);
        parameters.setPictureSize(s.width,s.height);
        cam.setParameters(parameters);
        try{
            cam.startPreview();

        }catch(Exception e){
            Toast.makeText(this,"change error",Toast.LENGTH_SHORT).show();
            cam.release();
            cam=null;
        }


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(cam !=null)
            cam.stopPreview();
    }

    private Size getBest(List<Size> sizes, int w, int h){
        Size best=sizes.get(0);
        int largestArea=best.width*best.height;
        for(Size s:sizes){
            int area=s.width*s.height;
            if(area > largestArea) {
                best=s;
                largestArea=area;
            }
        }
        return best;
    }

    public String getPic(){
        File image=new File(Environment.getExternalStorageDirectory()+File.separator+"MyCustomFolder","myFirstWeirdPic.jpg");
        return image.getAbsolutePath();
    }


}
