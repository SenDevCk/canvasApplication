package com.chandan.canvasproject.utility;

import android.util.Log;


import com.chandan.canvasproject.entity.Versioninfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.util.List;


/**
 * Created by Bunty Singh on 24-09-2021.
 */
public class WebHandler {
    HttpURLConnection conn;

    public static String callByPost(final String reqJson, String urls) {
        InputStream inputStream = null;
        String result = null;
        try {
            //   HttpClient httpclient = new DefaultHttpClient();
            HttpClient httpclient = getNewHttpClient();
            HttpPost httpPost = new HttpPost(urls);
            StringEntity stringEntity = new StringEntity(reqJson);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            //httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = null;

        } catch (Exception e) {
            e.printStackTrace();
            //Utiilties.writeIntoLog(Log.getStackTraceString(e));
            result = null;

        }
        return result;

    }

 /*   public static String callByPost2(final String reqJson, String urls){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urls);
            //  StringEntity se = new StringEntity(reqJson);
            httpPost.setEntity(new StringEntity(reqJson, HTTP.UTF_8));
            // StringEntity se = new StringEntity(reqJson);
            // httpPost.setEntity(se);
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = null;

        } catch (Exception e) {
            e.printStackTrace();
            result = null;

        }
        return result;

    }*/

    public static String callByPostFormData(List<NameValuePair> nameValuePairs, String urls) {
        String s="";
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpPost httpPost=new HttpPost(urls);

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse httpResponse=  httpClient.execute(httpPost);

            HttpEntity httpEntity=httpResponse.getEntity();
            s= readResponse(httpResponse);

        }
        catch(Exception exception)  {
            exception.printStackTrace();
        }
        return s;

    }

    private static String readResponse(HttpResponse httpResponse) {
        InputStream is=null;
        String return_text="";
        try {
            is=httpResponse.getEntity().getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuffer sb=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }
            return_text=sb.toString();
        } catch (Exception e)
        {

        }
        return return_text;
    }

    public static String callByPostwithoutparameter(String urls) {
        InputStream inputStream = null;
        String result = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urls);
            httpPost.setHeader("Content-type", "text/plain");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = null;

        } catch (Exception e) {
            e.printStackTrace();
            //Utiilties.writeIntoLog(Log.getStackTraceString(e));
            result = null;

        }
        return result;

    }



    public static String callByGet(String urls) {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet=new HttpGet(urls);
            httpGet.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpGet);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
            //Utiilties.writeIntoLog(Log.getStackTraceString(e));
        }
        return result;
    }
    public static String callByGet1(String urls) {
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet=new HttpGet(urls);
            httpGet.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpGet);
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
            //Utiilties.writeIntoLog(Log.getStackTraceString(e));
        }
        return result;
    }




    public static String postwwwURL(final String postParameters, String urls) throws MalformedURLException {
        String res = null;
        try {
            URL urlToRequest = new URL(urls);
            HttpURLConnection urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setFixedLengthStreamingMode(postParameters.getBytes().length);
            //int statusCode = urlConnection.getResponseCode();
            //if (statusCode ==  200) {
            res = convertInputStreamToString(urlConnection.getInputStream());
            //}
        } catch (Exception e) {
            e.printStackTrace();
            //Utiilties.writeIntoLog(Log.getStackTraceString(e));
            return null;
        }
        return res;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static Versioninfo CheckVersion(String imei, String version) {
        Versioninfo versioninfo=null;

        return versioninfo;
    }

    public static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            //HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

}


