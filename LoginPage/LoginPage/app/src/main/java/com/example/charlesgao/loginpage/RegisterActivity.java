package com.example.charlesgao.loginpage;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

    private void init(){
        this.bt_Register = (Button)this.findViewById(R.id.id_bt_register);
        this.bt_Reset = (Button)this.findViewById(R.id.id_bt_reset);
        this.cb_Music = (CheckBox)this.findViewById(R.id.id_cb_music);
        this.cb_PCgame =(CheckBox)this.findViewById(R.id.id_cb_pcgame);
        this.cb_Sports = (CheckBox)this.findViewById(R.id.id_cb_sports);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

    }
}
