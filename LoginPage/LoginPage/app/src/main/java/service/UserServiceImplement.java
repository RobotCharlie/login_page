package service;

import android.content.Entity;
import android.util.Log;
import android.widget.Toast;

import com.example.charlesgao.loginpage.LoginActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * Created by CharlesGao on 15-08-10.
 */
public class UserServiceImplement implements UserService {

    private static final String TAG = "UserServiceImplement";

    @Override
    public void userLogin(String loginName, String loginPassword) throws Exception {

        Log.d(TAG, loginName);
        Log.d(TAG, loginPassword);

//        Thread.sleep(3000);
//
//        if (loginName.equals("charlie") && loginPassword.equals("920612")){
//        }else{
//            throw new ServiceRulesException(LoginActivity.LOGIN_NAMEORPASSWORD_WRONG);
//        }

        // Create HttpClient Object
        HttpClient httpClient = new DefaultHttpClient();

        /**
         * uri: URLAddress http://localhost:8080/ClientServerProject/login.do
         *
         * The pass of params of method GET : URLAddress?paramName1=paramValue1&paramName2=paramValue2&...
         *
         * IP Address: your phone and your servlet should be in the same intranet
         */
        /**
         * MAKE SURE YOU GIVE THE USES_PERMISSION OF THE INTERNET TO THIS APP
         */
        String uri = "http://192.168.0.16:8080/ClientServerProject/login.do?LoginName="+loginName+"&LoginPassword="+loginPassword;
        HttpGet get = new HttpGet(uri);

        // Response: 200;404;500;406...
        HttpResponse httpResponse = httpClient.execute(get);

        int stateCode = httpResponse.getStatusLine().getStatusCode();
        if (stateCode != HttpStatus.SC_OK){
            throw new ServiceRulesException(LoginActivity.LOGIN_SERVER_CONNECTION_ERROR);
        }

        String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        Log.d(TAG,result);

        if (result.equals("success")){
            Log.d(TAG,"LOGIN SUCCESS");
        } else {
            throw new ServiceRulesException(LoginActivity.LOGIN_NAMEORPASSWORD_WRONG);
        }
    }
}
