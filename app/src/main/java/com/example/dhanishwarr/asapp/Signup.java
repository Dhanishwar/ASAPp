package com.example.dhanishwarr.asapp;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends AppCompatActivity {
    SQLiteDatabase db;
    EditText nam, num, us,pa;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        nam = findViewById(R.id.addname);
        num = findViewById(R.id.addphone);
        us = findViewById(R.id.adduser);
        pa = findViewById(R.id.addpass);
        add = findViewById(R.id.addr);
        db = openOrCreateDatabase
                ("printDB", Context.MODE_PRIVATE, null);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nam.getText().toString().trim().length() == 0 ||
                        us.getText().toString().trim().length() == 0 ||
                        num.getText().toString().trim().length() == 0||
                        pa.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                // Inserting record 
                db.execSQL("INSERT INTO user VALUES('" + us.getText() + "','" + num.getText() +
                        "','" + nam.getText() +"','" + pa.getText() + "');");
                // Checking if no records found 
                Cursor c = db.rawQuery
                        ("SELECT * FROM user",
                                null);
                if (c.getCount() == 0) {
                    showMessage("Error", "No records found");
                    return;
                }
                // Appending records to a string buffer 
                StringBuffer buffer =
                        new StringBuffer();
                while (c.moveToNext())
                {
                    buffer.append("Username: " + c.getString(0) + "\n");
                    buffer.append("num: " + c.getString(1) + "\n");
                    buffer.append("Name: " + c.getString(2) + "\n");
                    buffer.append("Password: " + c.getString(3) + "\n\n");
                }
                // Displaying all records 
                showMessage("Student Details", buffer.toString());
                clearText();
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

        us.setText("");
        num.setText("");
        nam.setText("");
        pa.setText("");
        nam.requestFocus();
    }
}
