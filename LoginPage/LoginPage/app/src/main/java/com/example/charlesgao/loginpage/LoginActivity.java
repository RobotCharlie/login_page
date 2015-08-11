/**
 *  Function: This is a UI layer
 */
package com.example.charlesgao.loginpage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import service.UserService;
import service.UserServiceImplement;


public class LoginActivity extends Activity {

    private EditText et_LoginName;
    private EditText et_LoginPassword;
    private Button bt_Login;
    private Button bt_Reset;
    private static final int FLAG_LOGIN_SUCCESSFUL = 1;
    private static final String LOGIN_FAIL = "LOGIN FAIL";


    private UserService userService = new UserServiceImplement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialization
        init();

        // Handler of Login Button
        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String loginName = et_LoginName.getText().toString();
                final String loginPassword = et_LoginPassword.getText().toString();
                Toast.makeText(v.getContext(),
                        "loginName is "+loginName+"; loginPassword is "+loginPassword,
                        Toast.LENGTH_LONG).show();

                /**
                 * Basic Check of login
                 */

                /**
                 * ATTENTION: All checking process are wrote in sub-thread, *NOT* main thread,
                 * coz you don't want your main thread stuck by an wrong login!!
                 */
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            userService.userLogin(loginName,loginPassword);
                            iHandler.sendEmptyMessage(FLAG_LOGIN_SUCCESSFUL);
                        } catch (Exception e){
                            e.printStackTrace();

                        }
                    }
                });
                thread.start();
            }
        });

        // Handler of Reset Button
        bt_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_LoginName.setText("");
                et_LoginPassword.setText("");
            }
        });

    }

    private static class IHandler extends android.os.Handler{

        private final WeakReference<Activity> mActivity;

        public IHandler(LoginActivity loginActivity){
            mActivity = new WeakReference<Activity>(loginActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            // NOW, mActivity have the object on LoginActivity!!!!!!!
            // Get a object form LoginActivity



        }
    }

    private IHandler iHandler = new IHandler(this);

    private void showTip(Message msg){

        Toast.makeText(this, "msg", Toast.LENGTH_LONG);
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
