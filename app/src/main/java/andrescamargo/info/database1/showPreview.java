package andrescamargo.info.database1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Dre on 6/16/2016.
 */
public class showPreview extends Activity{
    public static final String EXTRA_PHOTO_FILENAME="info.andrescamargo.android.database1.photo_filename";
    private ImageView image;
    private Button back,submit;
    private Intent i;
    private String imageName;
    private Context context=this;
    private String name="image";
    private static int number=0;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i=new Intent(this,cam.class);
        setContentView(R.layout.showpreview);
        image=(ImageView)findViewById(R.id.imageTaken);
        back=(Button)findViewById(R.id.back);
        submit=(Button)findViewById(R.id.submit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,name+number,Toast.LENGTH_SHORT).show();

                //has image path
                File imageToSend=new File(Environment.getExternalStorageDirectory()+File.separator+"MyCustomFolder"+File.separator+"myFirstWeirdPic.jpg");
                BackgroundTask backgroundtask=new BackgroundTask();
                backgroundtask.execute(name+number,imageToSend.getAbsolutePath());
                number++;

                //delete image after uploading
                //imageToSend.delete();
            }
        });
        //Toast.makeText(this,"show it!",Toast.LENGTH_SHORT).show();
        startActivityForResult(i, 1);
    }

    public String getPic(String pic){
        File image=new File(Environment.getExternalStorageDirectory()+File.separator+"MyCustomFolder",pic);
        Toast.makeText(this,image.toString(),Toast.LENGTH_SHORT).show();
        return image.getAbsolutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode ==1 && resultCode == Activity.RESULT_OK){
            //Toast.makeText(this,"backkk",Toast.LENGTH_SHORT).show();
            imageName=Environment.getExternalStorageDirectory()+File.separator+"MyCustomFolder/"+data.getExtras().getString(EXTRA_PHOTO_FILENAME);
            bitmap = BitmapFactory.decodeFile(getPic(data.getExtras().getString(EXTRA_PHOTO_FILENAME))); //(Bitmap)i.get("imageTaken");
            image.setImageBitmap(bitmap);
            image.setRotation(90);

        }
        else{
            Toast.makeText(this,"Error in return"+resultCode,Toast.LENGTH_SHORT).show();
        }
    }
    /****************BACKGROUND JOB FOR SAVING IMAGE INTO DATABASE**************************/
    class BackgroundTask extends AsyncTask<String,Void,String> {

        //private ProgressDialog dialog;
        String add_user_url,get_user_url;

        // public BackgroundTask(){
        // dialog=ProgressDialog.show(Register.this,"Adding","Please wait...");
        //}

        @Override
        protected void onPreExecute() {
            add_user_url="http://www.andrescamargo.info/upload.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String name,image;
            File imageToSend;
            name=params[0];
            image=params[1];

            imageToSend=new File(image);
            try {
                URL url = new URL(add_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                //


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); //compress to which format you want.
                byte[] byte_arr = stream.toByteArray();
                String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

                //

                StringBuilder data =new StringBuilder( URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")+ "&" +
                        URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image_str, "UTF-8"));


                bufferedWriter.write(data.toString());
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                httpURLConnection.disconnect();
                //displayEcho(url);

                return "image was added!";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            //dialog.dismiss();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }



}
