package com.example.dhanishwarr.asapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    EditText un, pw;
    Button log;
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  db = openOrCreateDatabase
                ("printDB", Context.MODE_PRIVATE, null);
        db.execSQL
                ("CREATE TABLE IF NOT EXISTS " +
                        "user(un VARCHAR," +
                        "Phone VARCHAR,"+
                        "Name VARCHAR,pass VARCHAR);");

        un = findViewById(R.id.un);
        pw = findViewById(R.id.pw);
        log = findViewById(R.id.login);
        txt = findViewById(R.id.signup);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Signup.class);
                startActivity(i);
            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (un.getText().toString().trim().length() == 0 ||
                        pw.getText().toString().trim().length() == 0 ) {
                    showMessage("Error", "Please enter all values");
                    return;
                }

                Cursor c = db.rawQuery("SELECT *" +
                        " FROM user WHERE un='" + un.getText() + "'", null);
                if (c.moveToFirst()) {
                    if(c.getString(0).equals(un.getText().toString()
                    )&&c.getString(3).equals(pw.getText().toString())){
                        Intent i = new Intent(MainActivity.this,Display.class);
                        startActivity(i);

                    }
                    else {

                        showMessage("Error", "Invalid Password \n"+c.getString(0)+"\n"+c.getString(3));
                        clearText();
                    }
                }
                else {
                    showMessage("Error", "Invalid Username");
                    clearText();
                }

            }
        });
    }
    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText(){

        un.setText("");
        pw.setText("");
        un.requestFocus();
    }

}
