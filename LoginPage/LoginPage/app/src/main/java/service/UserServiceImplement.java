package service;

import android.util.Log;

import com.example.charlesgao.loginpage.LoginActivity;

/**
 * Created by CharlesGao on 15-08-10.
 */
public class UserServiceImplement implements UserService {

    private static final String TAG = "UserServiceImplement";

    @Override
    public void userLogin(String loginName, String loginPassword) throws Exception {

        Log.d(TAG, loginName);
        Log.d(TAG, loginPassword);

        Thread.sleep(3000);

        if (loginName.equals("charlie") && loginPassword.equals("920612")){
        }else{
            throw new ServiceRulesExecption(LoginActivity.LOGIN_NAMEORPASSWORD_WRONG);
        }
    }
}
