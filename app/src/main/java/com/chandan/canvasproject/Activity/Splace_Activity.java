package com.chandan.canvasproject.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.chandan.canvasproject.R;
import com.chandan.canvasproject.database.DataBaseHelper;
import com.chandan.canvasproject.entity.Versioninfo;
import com.chandan.canvasproject.utility.CommonPref;
import com.chandan.canvasproject.utility.MJsonParsor;
import com.chandan.canvasproject.utility.MarshmallowPermission;
import com.chandan.canvasproject.utility.Utiilties;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Splace_Activity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo,slogan;

    Context context;
    MarshmallowPermission permission;
    long isDataDownloaded = -1;
    DataBaseHelper localDBHelper;
    public static SharedPreferences prefs;
    String version = null;
    boolean initse;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image=findViewById(R.id.img_logo);
        logo=findViewById(R.id.tv_logo);
        slogan=findViewById(R.id.tv_slogan);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);


        localDBHelper = new DataBaseHelper(Splace_Activity.this);
        try {
            localDBHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try {

            localDBHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }

    }



    private class CheckUpdate extends AsyncTask<Void, Void, Versioninfo> {


        CheckUpdate() {

        }


        @Override
        protected void onPreExecute() {

        }

        @SuppressLint("MissingPermission")
        @Override
        protected Versioninfo doInBackground(Void... Params) {

            TelephonyManager tm = null;
            String imei = null;

            permission = new MarshmallowPermission(Splace_Activity.this, Manifest.permission.READ_PHONE_STATE);
            if (permission.result == -1 || permission.result == 0) {
                try {
                    tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    if (tm != null)
                        if (ActivityCompat.checkSelfPermission(Splace_Activity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            return null;
                        }
                    imei = tm.getDeviceId();

                }catch(Exception e){}
            }


            try {
                version = getPackageManager().getPackageInfo(getPackageName(),
                        0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String res=null;
            //text_ver.setText("App Version - " + version + " ( " + imei + " )");
            try{
                JSONObject jsonObject=new JSONObject();
                jsonObject.accumulate("appDetailsVer",""+version);
                //jsonObject.accumulate("appDetailsVer","1.1");
                //   res= WebHandler.callByPost(jsonObject.toString(), Urls_this_pro.APP_VERSION_CHECK);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            Versioninfo versioninfo = MJsonParsor.parseCheckVersion(res);
            return versioninfo;

        }

        @Override
        protected void onPostExecute(final Versioninfo versioninfo) {

            final AlertDialog.Builder ab = new AlertDialog.Builder(Splace_Activity.this);
            ab.setCancelable(false);
            if (versioninfo != null) {

                CommonPref.setCheckUpdate(getApplicationContext(), System.currentTimeMillis());

                if (versioninfo.getAppDetailsAadminmsg().trim().length() > 0
                        && !versioninfo.getAppDetailsAadminmsg().trim()
                        .equalsIgnoreCase("")) {
                    showDailog(ab, versioninfo);
                } else {
                    showDailog(ab, versioninfo);
                }
            } else {
                Toast.makeText(Splace_Activity.this, "Update Your App or Server Error !", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void showDailog(AlertDialog.Builder ab,final Versioninfo versioninfo) {

        if (isVerUpdated(versioninfo.getAppDetailsVer())) {

            if (versioninfo.getAppDetailsPriority().equals("Low")) {

                start();
            } else if (versioninfo.getAppDetailsPriority().equals("ignore")) {

                ab.setTitle(versioninfo.getAppDetailsUpdatetitle());
                ab.setMessage(versioninfo.getAppDetailsUpdatemsg());

                ab.setPositiveButton(getResources().getString(R.string.update),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                Intent launchIntent = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package
                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri
                                        .parse("market://details?id="
                                                + getApplicationContext()
                                                .getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppDetailsAppurl())));
                                    finish();
                                }

                                dialog.dismiss();
                            }

                        });
                ab.setNegativeButton(getResources().getString(R.string.ignore),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                dialog.dismiss();

                                start();
                            }

                        });

                ab.show();

            }
            else if (versioninfo.getAppDetailsPriority().equals("High")) {
                ab.setTitle(versioninfo.getAppDetailsUpdatetitle());
                ab.setMessage(versioninfo.getAppDetailsUpdatemsg());
                ab.setPositiveButton(getResources().getString(R.string.update),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Intent myWebLink = new Intent(
                                        Intent.ACTION_VIEW);
                                myWebLink.setData(Uri.parse(versioninfo
                                        .getAppDetailsAppurl()));
                                startActivity(myWebLink);

                                dialog.dismiss();

                                start();
                            }
                        });
                ab.show();
            }
        } else {

            start();
        }

    }

    private boolean isVerUpdated(String current_ver_ser) {
        boolean flag=false;
        if (Float.parseFloat(current_ver_ser)>Float.parseFloat(version))flag=true;
        else flag=false;
        return flag;
    }


    private void start() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splace_Activity.this, Login_Activity.class);
                   Pair[] pairs=new Pair[2];
                    pairs[0]=new Pair<View,String>(image,"logo_image");
                    pairs[1]=new Pair<View,String>(logo,"logo_txt");
                    ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(Splace_Activity.this,pairs);
                    startActivity(intent,options.toBundle());
               //startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    protected void checkOnline() {
        // TODO Auto-generated method stub
        super.onResume();

        boolean net=false;

        MarshmallowPermission permission=new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);
        if(permission.result==-1 || permission.result==0)  net= Utiilties.isOnline(Splace_Activity.this);

        if (!net) {

            AlertDialog.Builder ab = new AlertDialog.Builder(
                    Splace_Activity.this);
            ab.setMessage(getResources().getString(R.string.no_internet_connection));
            ab.setPositiveButton(getResources().getString(R.string.turnon_internet),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int whichButton) {
                            Intent I = new Intent(
                                    android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });

            ab.show();

        } else {


            //  new CheckUpdate().execute();
            start();

        }
    }

    private boolean checkAndRequestPermissions() {

        //   int camra = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int write_external = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int phonestate = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();

      /*  if (camra != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }*/
        if (write_external != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (phonestate != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }

        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    init();
                    if (initse == false) init();
                } else {
                    //Toast.makeText(this, "Please enable all permissions !", Toast.LENGTH_SHORT).show();
                    if (initse == false) init();
                }
                break;
        }
    }
    private void init() {
        initse=true;
        DataBaseHelper db = new DataBaseHelper(this);
        try {
            db.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;

        }

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;

        checkOnline();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Call some material design APIs here
            if (checkAndRequestPermissions()) {
                if (initse==false) init();

            }

        } else {

            if (initse==false) init();
        }
        checkOnline();
    }


}