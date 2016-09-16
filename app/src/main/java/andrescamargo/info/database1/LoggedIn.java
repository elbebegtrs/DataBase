package andrescamargo.info.database1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class LoggedIn extends Activity implements View.OnClickListener{
        String JSON_STRING,json_string;
        TextView title,mes;
        Button out,takePic,uploadPic,getPic;
        String userName;
        String passWord;
        Users user;
        boolean val=false;
        String fname,lname,age,username,password;
        private ProgressDialog dialog;
        private static final int REQUEST_PHOTO=1;
    //Intent i2=new Intent(this,showPreview.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        //title=(TextView)findViewById(R.id.titleName);

        out=(Button)findViewById(R.id.logOut);
        takePic=(Button)findViewById(R.id.takePic);
        uploadPic=(Button)findViewById(R.id.uploadPic);
        getPic=(Button)findViewById(R.id.getPic);
        out.setOnClickListener(this);
        takePic.setOnClickListener(this);
        uploadPic.setOnClickListener(this);
        getPic.setOnClickListener(this);

        userName=getIntent().getExtras().getString("username");
        passWord=getIntent().getExtras().getString("password");

        if(userName.equals("")){
           Toast.makeText(this,"User name can't be left blank",Toast.LENGTH_SHORT).show();finish();
        }else {
            new BackgroundTask().execute(JSON_STRING);
        }
        }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.logOut:
        finish();
        break;
        case R.id.takePic:
            //Intent i=new Intent(this,cam.class);
            startActivity(new Intent(this, showPreview.class));//1
           // startActivityForResult(i, REQUEST_PHOTO);
        break;
        case R.id.uploadPic:

        break;
        case R.id.getPic:

        break;
        }

        //startActivity(new Intent(this,MainActivity.class));
        }

class BackgroundTask extends AsyncTask<String,Void,String> {


    String get_user_url;

    public BackgroundTask(){
       dialog=ProgressDialog.show(LoggedIn.this,"Getting","Please wait...");
    }

    @Override
    protected void onPreExecute()
    {

        get_user_url="http://www.andrescamargo.info/getEntry.php";
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(get_user_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream IS=httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));

            StringBuilder stringBuilder=new StringBuilder();
            while((JSON_STRING= bufferedReader.readLine())!=null){
                stringBuilder.append(JSON_STRING+"\n");
            }

            bufferedReader.close();
            IS.close();
            httpURLConnection.disconnect();

            return stringBuilder.toString().trim();

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
        //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        //TextView tx=(TextView)findViewById(R.id.message);
        //tx.setText(result);
        json_string=result;
        getData();
        dialog.dismiss();
    }
}

    public void getData(){
        JSONObject jsonObj;
        JSONArray jsonArray;
        try{
            jsonObj=new JSONObject(json_string);
            jsonArray=jsonObj.getJSONArray("server_response");
            int count =0;

            while(count < jsonArray.length()){
                JSONObject JO=jsonArray.getJSONObject(count);
                    if (userName.equals(JO.getString("UserName"))) {
                        if (passWord.equals(JO.getString("Password"))) {
                            fname = JO.getString("FirstName");
                            lname = JO.getString("LastName");
                            age = JO.getString("Age");
                            username = JO.getString("UserName");
                            password = JO.getString("Password");
                            Toast.makeText(this, "Password matched", Toast.LENGTH_LONG).show();
                            user = new Users(fname, lname, Integer.parseInt(age), username, password);
                            break;
                        } else {
                            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show();
                            user = new Users("", "", 0, "", "");
                            finish();
                            break;
                        }
                    } else if (count == (jsonArray.length() - 1)) {
                        Toast.makeText(this, "User is not recognized", Toast.LENGTH_LONG).show();
                        user = new Users("", "", 0, "", "");
                        finish();
                    }
                    count++;

            }
        }catch(JSONException e){

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
       // TextView tx=(TextView)findViewById(R.id.message);

      //  tx.setText(user.getFname().toUpperCase());

    }

    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode ==1 && resultCode == Activity.RESULT_OK){
            Toast.makeText(this,"backkk",Toast.LENGTH_SHORT).show();
                //data.getExtras()
                startActivity(data);

        }
    }

}
