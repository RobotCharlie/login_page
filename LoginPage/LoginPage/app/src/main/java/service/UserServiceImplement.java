package service;

import android.content.Entity;
import android.util.Log;
import android.widget.Toast;

import com.example.charlesgao.loginpage.LoginActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by CharlesGao on 15-08-10.
 */
public class UserServiceImplement implements UserService {

    private static final String TAG = "UserServiceImplement";

    @Override
    public void userLogin(String loginName, String loginPassword) throws Exception {

        Log.d(TAG, loginName);
        Log.d(TAG, loginPassword);

////////////////////////////////////////GET METHOD//////////////////////////////////////////////////
//        // Create HttpClient Object
//        HttpClient httpClient = new DefaultHttpClient();
//
//        /**
//         * uri: URLAddress http://localhost:8080/ClientServerProject/login.do
//         *
//         * The pass of params of method GET : URLAddress?paramName1=paramValue1&paramName2=paramValue2&...
//         *
//         * IP Address: your phone and your servlet should be in the same intranet
//         */
//
//        /**
//         * MAKE SURE YOU GIVE THE USES_PERMISSION OF THE INTERNET TO THIS APP
//         */
//
//        String uri = "http://192.168.0.16:8080/ClientServerProject/login.do?LoginName="+loginName+"&LoginPassword="+loginPassword;
//        HttpGet httpGet = new HttpGet(uri);
//        HttpResponse httpResponse = httpClient.execute(httpGet);
//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\GET METHOD\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//


////////////////////////////////////////POST METHOD/////////////////////////////////////////////////

        /**
         * HttpValuePair --> List<NameValuePair> --> HttpEntity --> HttpPost --> HttpClient
         */
        // HttpParams is an interface, so we can only initialize an implemented class for it
        HttpParams httpParams = new BasicHttpParams();
        // Set ContentCharset of the request
        HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
        // Set the Maximum time of Connection time between Client-side
        // and Server-side --> ConnectionTimeoutException
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
        // Set the Maximum time of Response time(Server receive the request info.
        // but can not make the response in a period of time) between Client-side
        // and Server-side --> SocketTimeoutException
        HttpConnectionParams.setSoTimeout(httpParams,3000);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        // Regular one: "80" is the port number
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        // Makes "https" works too
        schemeRegistry.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));
        ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);


        HttpClient httpClient = new DefaultHttpClient(clientConnectionManager, httpParams);
        String uri = "http://192.168.0.16:8080/ClientServerProject/login.do";
        HttpPost httpPost = new HttpPost(uri);
        NameValuePair loginnamePair = new BasicNameValuePair("LoginName", loginName);
        NameValuePair loginpasswordPair = new BasicNameValuePair("LoginPassword", loginPassword);
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(loginnamePair);
        nameValuePairList.add(loginpasswordPair);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, HTTP.UTF_8));
        HttpResponse httpResponse = httpClient.execute(httpPost);


//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\POST METHOD\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\//

        // Response: 200;404;500;406...


        int stateCode = httpResponse.getStatusLine().getStatusCode();
        if (stateCode != HttpStatus.SC_OK){
            throw new ServiceRulesException(LoginActivity.LOGIN_CONNECT_EXCEPTION);
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
