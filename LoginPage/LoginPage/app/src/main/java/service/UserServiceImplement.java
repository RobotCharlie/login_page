package service;

import android.util.Log;

/**
 * Created by CharlesGao on 15-08-10.
 */
public class UserServiceImplement implements UserService {

    private static final String TAG = "UserServiceImplement";

    @Override
    public void userLogin(String loginName, String loginPassword) throws Exception {

        Log.d(TAG, loginName);
        Log.d(TAG, loginPassword);

    }
}
