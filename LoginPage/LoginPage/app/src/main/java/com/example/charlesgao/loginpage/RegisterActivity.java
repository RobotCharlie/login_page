package com.example.charlesgao.loginpage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import service.ServiceRulesException;
import service.UserService;
import service.UserServiceImplement;

/**
 * Created by CharlesGao on 15-08-13.
 */
public class RegisterActivity extends Activity {

    private EditText et_LoginName;
    private CheckBox cb_PCgame;
    private CheckBox cb_Music;
    private CheckBox cb_Sports;
    private Button bt_Register;
    private Button bt_Reset;

    private static final int FLAG_REGISTER_SUCCESSFUL = 1;
    public static final String REGISTER_FAIL = "REGISTER FAIL";
    private static final String REGISTER_SUCCEED = "REGISTER SUCCEED";
    public static final String REGISTER_NAMEORPASSWORD_WRONG = "REGISTER NAME OR PASSWORD DOES NOT MATCH";
    public static final String REGISTER_REQUEST_TIME_OUT = "REQUEST TIME OUT";
    public static final String REGISTER_RESPONSE_TIME_OUT = "RESPONSE TIME OUT";
    public static final String REGISTER_CONNECT_EXCEPTION = "CAN NOT CONNECT TO SERVER";

    private UserService userService = new UserServiceImplement();
    private static ProgressDialog progressDialog;

    private void init(){
        et_LoginName = (EditText)findViewById(R.id.id_et_login_name);
        bt_Register = (Button)findViewById(R.id.id_bt_register);
        bt_Reset = (Button)findViewById(R.id.id_bt_reset);
        cb_Music = (CheckBox)findViewById(R.id.id_cb_music);
        cb_PCgame =(CheckBox)findViewById(R.id.id_cb_pcgame);
        cb_Sports = (CheckBox)findViewById(R.id.id_cb_sports);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();


        bt_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String loginName = et_LoginName.getText().toString();
                final List<String> registerInterests = new ArrayList<>();
                if (cb_Music.isChecked()) registerInterests.add(cb_Music.getText().toString());
                if (cb_PCgame.isChecked()) registerInterests.add(cb_PCgame.getText().toString());
                if (cb_Sports.isChecked()) registerInterests.add(cb_Sports.getText().toString());

                if(progressDialog ==null){
                    progressDialog = new ProgressDialog(RegisterActivity.this);
                }
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Registering...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            userService.userRegister(loginName, registerInterests);
                            iHandler.sendEmptyMessage(FLAG_REGISTER_SUCCESSFUL);
                        } catch (ConnectTimeoutException e) {
                            e.printStackTrace();
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("errorMsg", REGISTER_REQUEST_TIME_OUT);
                            msg.setData(data);
                            iHandler.sendMessage(msg);
                            Log.d("Exception", REGISTER_REQUEST_TIME_OUT);
                        } catch (ConnectException e) {
                            e.printStackTrace();
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("errorMsg", REGISTER_CONNECT_EXCEPTION);
                            msg.setData(data);
                            iHandler.sendMessage(msg);
                            Log.d("Exception", REGISTER_CONNECT_EXCEPTION);
                        } catch (SocketTimeoutException e) {
                            e.printStackTrace();
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("errorMsg", REGISTER_RESPONSE_TIME_OUT);
                            msg.setData(data);
                            iHandler.sendMessage(msg);
                            Log.d("Exception", REGISTER_RESPONSE_TIME_OUT);
                        } catch (ServiceRulesException e) {
                            e.printStackTrace();
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("errorMsg", e.getMessage());
                            msg.setData(data);
                            iHandler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("errorMsg", REGISTER_FAIL);
                            msg.setData(data);
                            iHandler.sendMessage(msg);
                        }
                    }
                });
                thread.start();
            }


        });

        bt_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_LoginName.setText("");
                cb_Music.setChecked(false);
                cb_PCgame.setChecked(false);
                cb_Sports.setChecked(false);
            }
        });



    }

    private static class IHandler extends android.os.Handler {

        private final WeakReference<Activity> mActivity;

        public IHandler(RegisterActivity registerActivity){
            mActivity = new WeakReference<Activity>(registerActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            // NOW, mActivity have the object on RegisterActivity!!!!!!!
            // Get a object from RegisterActivity

            int flag = msg.what;
            switch (flag){
                case 0:
                    if (progressDialog != null) progressDialog.dismiss();
                    String errorMsg = msg.getData().getSerializable("errorMsg").toString();
                    ((RegisterActivity)(mActivity.get())).showTip(errorMsg);
                    break;

                case FLAG_REGISTER_SUCCESSFUL:
                    if (progressDialog != null) progressDialog.dismiss();
                    ((RegisterActivity)(mActivity.get())).showTip(REGISTER_SUCCEED);
                    break;
                default:
                    break;

            }

        }
    }

    private IHandler iHandler = new IHandler(this);

    public void showTip(String string){

        Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_SHORT).show();
    }

}
