package andrescamargo.info.database1;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class Register extends Activity implements View.OnClickListener{
    EditText fname,lname,age,username,password;
    String firstName,lastName,userAge,userusername,userpassword;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fname=(EditText)findViewById(R.id.Rfname);
        lname=(EditText)findViewById(R.id.Rlname);
        age=(EditText)findViewById(R.id.Rage);
        username=(EditText)findViewById(R.id.Rusername);
        password=(EditText)findViewById(R.id.Rpassword);

        register=(Button)findViewById(R.id.Rregister);
        register.setOnClickListener(this);

    }

    class BackgroundTask extends AsyncTask<String,Void,String> {

        //private ProgressDialog dialog;
        String add_user_url,get_user_url;

        // public BackgroundTask(){
        // dialog=ProgressDialog.show(Register.this,"Adding","Please wait...");
        //}

        @Override
        protected void onPreExecute() {
            add_user_url="http://www.andrescamargo.info/addEntry.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String fname,lname,age,username,password;

            fname=params[0];
            lname=params[1];
            age=params[2];
            username=params[3];
            password=params[4];
            try {
                URL url = new URL(add_user_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = URLEncoder.encode("FirstName", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8") + "&" +
                        URLEncoder.encode("LastName", "UTF-8") + "=" + URLEncoder.encode(lname, "UTF-8") + "&" +
                        URLEncoder.encode("Age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&" +
                        URLEncoder.encode("UserName", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                httpURLConnection.disconnect();

                return "User was added!";

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

    @Override
    public void onClick(View v) {
        firstName=fname.getText().toString();
        lastName=lname.getText().toString();
        userAge=age.getText().toString();
        userusername=username.getText().toString();
        userpassword=password.getText().toString();


        BackgroundTask backgroundtask=new BackgroundTask();
        backgroundtask.execute( firstName, lastName, userAge, userusername, userpassword);

        finish();
    }
}
