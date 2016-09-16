package andrescamargo.info.database1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/*
* Class Description:    This is the entry point for the app
*                       It implements the View.OnClickListener interface
*                       Override the OnClick function to listen for all
*                       the button click events
*                       It extends the Activity super class to override the
*                       onCreate method,
* Variables:            userName       - EditText   - It wires to the view component of MuserName
*                                                     and it stores the username as a string
 *                      passWord       - EditText   - It wires to the view component of MpassWord
*                                                     and it stores the password as a string
 *                      login          - Button     - It wires to the view component of Mlogin
*                                                     and if click it sends user to the LoggedIn activity
 *                      register       - Button     - It wires to the view component of Mregister
*                                                     and if click it sends user to the Register activity
 *                      UserInfo       - Intent     - It saves data for other activities
 *                      networkMessage - TextView   - It wires to the view component of networkMes
*                                                     and it stores the error network message
* */

public class MainActivity extends Activity implements View.OnClickListener{

    EditText userName,passWord;
    Button login,register;
    Intent userInfo;
    TextView networkMessege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName=(EditText)findViewById(R.id.Musername);
        passWord=(EditText)findViewById(R.id.Mpassword);

        login=(Button)findViewById(R.id.MlogIn);
        login.setOnClickListener(this);

        register=(Button)findViewById(R.id.Mregister);
        register.setOnClickListener(this);

        networkMessege=(TextView)findViewById(R.id.networkMes);

        userInfo=new Intent(this,LoggedIn.class);

        //here we check if the device has network access and displays the networkMesssage accordingly
        if (checkConnectivity())
            networkMessege.setVisibility(View.INVISIBLE);
        else {
            login.setEnabled(false);
            register.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Mregister:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.MlogIn:
                userInfo.putExtra("username", userName.getText().toString());
                userInfo.putExtra("password", passWord.getText().toString());
                startActivity(userInfo);
                userName.setText("");
                passWord.setText("");
                break;
            default:
                Toast.makeText(this,"Nothing shows up",Toast.LENGTH_LONG).show();
                break;

        }
    }
    boolean checkConnectivity(){
        ConnectivityManager connectivityManager= (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        else{
            return false;
        }
    }
}
