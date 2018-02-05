package com.example.james.jejewitt_subbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
* AddSub Class
* functionality: Activated when Add button is pressed in main
* Takes inputs from user to add a new subscription to the list
* returns data on close
*
* checks for errors by ensuring cases of data are correct
* if they are not correct it does nothing (hints are given as hints to show the format)
*
 */


public class AddSub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub);

        getData();
    }

    private void getData(){ //if button pressed get data, ensure it is correct, and send back to main activity
        Button btn = (Button) findViewById(R.id.addButton);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean error = false;         //error handling if this is True, don't do anything
                EditText edit1 = (EditText) findViewById(R.id.addName);
                String message1 = edit1.getText().toString();
                EditText edit2 = (EditText) findViewById(R.id.addDate);
                String message2 = edit2.getText().toString();
                EditText edit3 = (EditText) findViewById(R.id.addAmmount);
                String message3 = edit3.getText().toString();
                EditText edit4 = (EditText) findViewById(R.id.addComment);
                String message4 = edit4.getText().toString();

                if ((message1.length())==0 ||  (message1.length())> 30){
                    error = true;
                }

                try{
                    Double.valueOf(message3);
                    if (Double.valueOf(message3) < 0) {
                        error = true;
                    }
                }catch(NumberFormatException ex){
                    error = true;
                }
 
                if (!message2.isEmpty()) {   //check if empty to prevent having an error for an empty date
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date dateTest = sdf.parse(message2);
                    } catch (ParseException ex) {
                        error = true;
                    }
                }
                if ((message1.length())==0 ||  (message1.length())> 30){
                    error = true;
                }
                if (!error){  //if no errors, send information back
                    Intent intent = new Intent();
                    intent.putExtra("name", message1);
                    intent.putExtra("date", message2);
                    intent.putExtra("ammount", message3);
                    intent.putExtra("comment", message4);

                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }

        });

    }
}
