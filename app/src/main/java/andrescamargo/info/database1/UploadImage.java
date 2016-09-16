package andrescamargo.info.database1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Dre on 6/3/2016.
 */
public class UploadImage  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {

        //private ProgressDialog dialog;
        String add_user_url,get_user_url;

        // public BackgroundTask(){
        // dialog=ProgressDialog.show(Register.this,"Adding","Please wait...");
        //}

        @Override
        protected void onPreExecute() {
            add_user_url="http://www.andrescamargo.info/uploadImagesMYSQL.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String image;

            image=params[0];
            try {
                URL url = new URL(add_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = URLEncoder.encode("image1", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                httpURLConnection.disconnect();

                return "Image was added!";

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

    public void onClick(View v) {
        //firstName=fname.getText().toString();
        //lastName=lname.getText().toString();
        //userAge=age.getText().toString();
        //userusername=username.getText().toString();
        //userpassword=password.getText().toString();


        BackgroundTask backgroundtask=new BackgroundTask();
        backgroundtask.execute();//firstName, lastName, userAge, userusername, userpassword);

        finish();
    }
}
