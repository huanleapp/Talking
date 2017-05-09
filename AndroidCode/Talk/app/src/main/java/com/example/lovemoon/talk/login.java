package com.example.lovemoon.talk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import ConnectWebApi.NetworkConnection;
import ConnectWebApi.ServiceHandler;
import ObjectForWebApi.BasicNameValuePair;
import ObjectForWebApi.NameValuePair;

public class login extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView fbook,acc,sin,sup,sinnp;
    private EditText mal,pswd;
    private String[] taiKhoan;
    private  String str_Email;
    private  String str_Password;

    //Internet Service
    NetworkConnection nw;
    ProgressDialog prgDialog;
    Boolean netConnection = false;
//

    //Login API
    //String loginURL ="http://192.168.1.231:42006/api/values";
    String loginURL ="http://www.muasi389.com/api/Account";
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // connect web api
        nw = new NetworkConnection(getApplicationContext());
        prgDialog = new ProgressDialog(this);
        // Set Cancelable as False
        prgDialog.setCancelable(false);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sup = (TextView)findViewById(R.id.sup);
        sin = (TextView)findViewById(R.id.sin);
        fbook = (TextView)findViewById(R.id.fboook);
        acc = (TextView)findViewById(R.id.act);
        mal = (EditText)findViewById(R.id.mal);
        pswd = (EditText)findViewById(R.id.pswd);
        sinnp = (TextView)findViewById(R.id.sinnp);


        sup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent it = new Intent(login.this, signup.class);
                startActivity(it);
            }
        });
        sinnp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_Email = mal.getText().toString().trim();
                str_Password = pswd.getText().toString().trim();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new LoginOperation().execute();
                    }
                });

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    //-----------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //----------------------------------------------------------------------
    //ham checkLogin nay khong con dung nua
    private boolean checkLogin()
    {
            for (String credential : taiKhoan) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mal.getText().toString().trim())) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(pswd.getText().toString().trim());
                }
            }
        return false;
    }
    private class LoginOperation  extends AsyncTask<String, Void, Void> {

        String status, message;
        @Override
        protected void onPreExecute() {
            // Set Progress Dialog Text
            prgDialog.setMessage("Logging...");
            prgDialog.show();
        }

        @Override
        protected Void doInBackground(String... urls) {

            if(nw.isConnectingToInternet() == true)
            {
                try
                {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("TaiKhoan", str_Email));
                    nameValuePairs.add(new BasicNameValuePair("MatKhau", str_Password));
                    ServiceHandler sh  = new ServiceHandler();
                    String response = sh.makeServiceCall(loginURL, ServiceHandler.POST,
                            nameValuePairs);

                    Log.e("response", response);
                    if(!response.trim().equals("") &&  !response.trim().equals("null")) {
                        JSONObject js = new JSONObject(response);
                        Log.e("show json object", "Tài khoản: "+js.getString("TaiKhoan")+" Mật khẩu: "+js.getString("MatKhau"));
                        status = "Success";
                        Log.e("status", status);
                    }
                    else
                        status = "Fail";

                }catch(Exception ex){
                    ex.printStackTrace();
                }
                netConnection = true;
            }else
            {
                netConnection = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            prgDialog.dismiss();

            if(netConnection == false)
            {
                Toast toast = Toast.makeText(getApplicationContext(),"Internet is not available. Please turn on and try again.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            else
            {
                if(status.contains("Success"))
                {
                    if(getResources().getConfiguration().locale.getLanguage()=="vi")
                        message = "Đăng nhập thành công";
                    else
                        message = "Login Successful";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    Intent i=new Intent(login.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    if(getResources().getConfiguration().locale.getLanguage()=="vi")
                        message = "Tài khoản hoặc mật khẩu không đúng";
                    else
                        message = "Account or Password is invalid";
                    Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            super.onPostExecute(result);
        }
    }
}
