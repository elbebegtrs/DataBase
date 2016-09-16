package andrescamargo.info.database1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
public class TakePic extends Activity    {
    private ImageView image;
    CameraManager cameraManager =(CameraManager)getSystemService(Context.CAMERA_SERVICE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image=(ImageView)findViewById(R.id.pic);

        //Toast.makeText(TakePic.this,"" , Toast.LENGTH_LONG).show();

        //Intent cam_intent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);//(MediaStore.ACTION_IMAGE_CAPTURE);//
        //startActivityForResult(cam_intent, 0);

    }

    // private File getFile()
    // {
    //     File folder = new File("camera_app");
    //     if(!folder.exists()){
    //         folder.mkdir();
    //     }

    //     File image_file=new File(folder,"cam_image.jpg");

    //     return image_file;
    // }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(TakePic.this, Environment.getExternalStorageDirectory().toString(), Toast.LENGTH_LONG).show();
        //String path=Environment.getExternalStorageDirectory()+"/pic1.jpg";
        if(requestCode == 0 && resultCode==Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }else Toast.makeText(this,"wrong",Toast.LENGTH_SHORT).show();
        //if(resultCode==Activity.RESULT_OK){
        // Uri selectedImg=imageUri;
        // getContentResolver().notifyChange(selectedImg,null);

        //  ImageView imageView2=(ImageView)findViewById(R.id.image);
        //  ContentResolver cr=getContentResolver();
        //  Bitmap bitmap;

        //  try{
        //      bitmap=MediaStore.Images.Media.getBitmap(cr,selectedImg);
        //      imageView2.setImageBitmap(bitmap);
        //      Toast.makeText(MainActivity.this,selectedImg.toString(), Toast.LENGTH_LONG).show();
        //  }catch(Exception e){
        //      e.printStackTrace();
        //  }
        //}

    }
}
