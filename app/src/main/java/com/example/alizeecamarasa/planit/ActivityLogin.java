package com.example.alizeecamarasa.planit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by alizeecamarasa on 10/03/15.
 */
public class ActivityLogin extends Activity implements View.OnClickListener {

    public static String filename = "Logindata";
    SharedPreferences SP;
    EditText email;
    EditText mdp;
    CheckBox remember_me;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email);
        mdp = (EditText) findViewById(R.id.mdp);
        remember_me = (CheckBox) findViewById(R.id.remember_me);
        Button login = (Button) findViewById(R.id.login);
        Button suscribe = (Button) findViewById(R.id.suscribe);
        login.setOnClickListener(this);
        suscribe.setOnClickListener(this);
        SP = getSharedPreferences(filename, 0);
        String getname = SP.getString("key1","");
        String getpass = SP.getString("key2","");
        email.setText(getname);
        mdp.setText(getpass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String name = email.getText().toString();
                String pass = mdp.getText().toString();
                if (remember_me.isChecked()){
                    SharedPreferences.Editor editit = SP.edit();
                    editit.putString("key1", name);
                    editit.putString("key2",pass);
                    editit.commit();
                }
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;

        }

    }
}
