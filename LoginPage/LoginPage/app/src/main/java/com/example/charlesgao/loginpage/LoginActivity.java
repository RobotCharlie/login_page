package com.example.charlesgao.loginpage;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {

    private EditText et_LoginName;
    private EditText et_LoginPassword;
    private Button bt_Login;
    private Button bt_Reset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialization
        init();

        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginName = et_LoginName.getText().toString();
                String loginPassword = et_LoginPassword.getText().toString();
                Toast.makeText(v.getContext(),
                        "loginName is "+loginName+"; loginPassword is "+loginPassword,
                        Toast.LENGTH_LONG).show();
            }
        });

        bt_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_LoginName.setText("");
                et_LoginPassword.setText("");
            }
        });
    }

    private void init(){
        et_LoginName = (EditText)findViewById(R.id.id_et_login_name);
        et_LoginPassword = (EditText)findViewById(R.id.id_et_login_password);
        bt_Login = (Button)findViewById(R.id.id_bt_login);
        bt_Reset = (Button)findViewById(R.id.id_bt_reset);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
