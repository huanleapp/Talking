package ConnectWebApi;

import android.util.Log;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;


import ObjectForWebApi.NameValuePair;

/**
 * Created by LoveMoon on 08/05/2017.
 */

public class ServiceHandler {

    String response = "";
    public final static int GET = 1;
    public final static int POST = 2;
    public final static int UPLOAD = 3;

    public ServiceHandler() {
    }

    /*
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */
    public String makeServiceCall(String url, int method) {
        return this.makeServiceCall(url, method, null);
    }

    /*
     * Making service call
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     * */
    public String makeServiceCall(String urll, int method,
                                  List<NameValuePair> params) {
        try {
            // http client
            if(method != UPLOAD && params != null)
                urll += getQuery(params);
            URL url = new URL(urll);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            try {
                //conn.setReadTimeout(10000);
                //conn.setConnectTimeout(15000);
                conn.setDoInput(true);

                // Checking http request method type
                if (method == POST) {
                    Log.e("in POST", "in POST");
                    //conn.setChunkedStreamingMode(0);
                    conn.setRequestMethod("POST");
                    // adding post params
                    if (params != null) {
                        Log.e("in POST params", "in POST params");
                    }
                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((line = br.readLine()) != null) {
                                response += line ;
                            }
                        } else {
                            response = "";

                        }

                    Log.e("url in post service", urll);

                } else if (method == GET) {
                    // appending params to url
                    conn.setRequestMethod("GET");
                    Log.e("in GET", "in GET");
                    if (params != null) {
                        Log.e("in GET params", "in GET params");
                    }
                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((line = br.readLine()) != null) {
                                response += line ;
                            }
                        } else {
                            response = "";

                        }

                    Log.e("url in get service", urll);

                } if (method == UPLOAD) {
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Accept-Charset", "UTF-8");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    Log.e("in UPLOAD", "in UPLOAD");
                    conn.setRequestMethod("POST");
                    //conn.setChunkedStreamingMode(0);
                    // adding post params
                    if (params != null) {
                        Log.e("in UPLOAD params", "in UPLOAD params");
                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getQueryPostFromBody(params));
                        writer.flush();
                        writer.close();
                        os.close();
                    }
                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            response += line ;
                        }
                    } else {
                        response = "";

                    }

                    Log.e("url in upload service", urll);

                }
            }finally {
                conn.disconnect();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return response;

    }
    private String getQueryPostFromBody(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first) {
                first = false;
                result.append("?");
            }
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
