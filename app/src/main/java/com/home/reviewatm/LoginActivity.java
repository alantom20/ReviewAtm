package com.home.reviewatm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CAMERA = 5;
    private EditText edUser;
    private EditText edPasswd;
    private CheckBox cbRemember;
    private Intent helloService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //camera();

        //settingsTest();
        findViews();
        new TestTask().execute("http://tw.yahoo.com/");
        //Fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container_news,NewFragment.getInstance());
        transaction.commit();
        //Services
        helloService = new Intent(this,HelloService.class);
        helloService.putExtra("NAME","T1");
        startService(helloService);
        helloService.putExtra("NAME","T2");
        startService(helloService);
        helloService.putExtra("NAME","T3");
        startService(helloService);


    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive:Hello " +  intent.getAction());
        }
    };
    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(HelloService.ACTION_HELLO_DONE);
        registerReceiver(broadcastReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
      //  stopService(helloService);
        unregisterReceiver(broadcastReceiver);

    }

    public class TestTask extends AsyncTask<String,Void,Integer>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            Toast.makeText(LoginActivity.this,"onPreExecute",Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d(TAG, "onPostExecute: ");
            Toast.makeText(LoginActivity.this,"onPostExecute: " + integer,Toast.LENGTH_LONG).show();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int data = 0;
            try {
                URL url = new URL(strings[0]);
                data = url.openStream().read();
                Log.d(TAG, "doInBackground: " + data);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
    }

    private void findViews() {
        edUser = findViewById(R.id.userid);
        edPasswd = findViewById(R.id.passwd);
        cbRemember = findViewById(R.id.cb_rem_userid);
        cbRemember.setChecked(getSharedPreferences("atm",MODE_PRIVATE).getBoolean("REMEMBER_USER_ID",false));
        cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                getSharedPreferences("atm",MODE_PRIVATE).edit()
                        .putBoolean("REMEMBER_USER_ID",b)
                        .apply();

            }
        });

        String userid = getSharedPreferences("atm", MODE_PRIVATE)
                .getString("USERID", "");
        edUser.setText(userid);
    }

    private void settingsTest() {
        getSharedPreferences("atm",MODE_PRIVATE)
                .edit()
                .putInt("LEVEL",3)
                .putString("name","peter")
                .commit();
        int level = getSharedPreferences("atm",MODE_PRIVATE)
                .getInt("LEVEL",0);

        Log.d(TAG, "onCreate: " + level);
    }

    private void camera() {
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permission == PackageManager.PERMISSION_GRANTED){
            //tackPhoto();
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_CAMERA){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                tackPhoto();

            }
        }
    }

    private void tackPhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }


    public void login(View view){
        final String userid = edUser.getText().toString();
        final String passwd = edPasswd.getText().toString();
        FirebaseDatabase.getInstance().getReference("users").child(userid).child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pw = (String) snapshot.getValue();
                        boolean remember= getSharedPreferences("atm",MODE_PRIVATE)
                                .getBoolean("REMEMBER_USER_ID",false);
                        if(pw.equals(passwd)){
                                String uid =(remember)?userid:"";     //是否記住帳號,false的話將設定為空字串
                                getSharedPreferences("atm", MODE_PRIVATE)
                                        .edit()
                                        .putString("USERID", uid)
                                        .apply();

                            setResult(RESULT_OK);
                            finish();
                        }else{
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("登入結果")
                                    .setMessage("登入失敗")
                                    .setPositiveButton("OK",null)
                                    .show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//        if("jack".equals(userid) && "1234".equals(passwd)){
//
//        }

    }
    public void quit(View view){

    }
}