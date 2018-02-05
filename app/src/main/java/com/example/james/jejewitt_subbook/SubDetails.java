package com.example.james.jejewitt_subbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 *Class SubDetails
 *
 *functionality:
 * has 3 main features
 * displays information from a row
 * allows to delete this informatin
 * allows to edit row infomraiton
 *
 */

public class SubDetails extends AppCompatActivity {
    private String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_details);

        //get data for displaying
        this.position = getIntent().getStringExtra("messagePosition");
        String messageName = getIntent().getStringExtra("messageName");
        String messageDate = getIntent().getStringExtra("messageDate");
        String messageAmmount = getIntent().getStringExtra("messageAmmount");
        String messageComment = getIntent().getStringExtra("messageComment");

        //init editText views
        EditText textName = (EditText) findViewById(R.id.subDetailName);
        EditText textDate = (EditText) findViewById(R.id.subDetailDate);
        EditText textAmmount = (EditText) findViewById(R.id.subDetailAmmount);
        EditText textComment = (EditText) findViewById(R.id.subDetailComment);

        //set data in editText
        textName.setText(messageName,TextView.BufferType.EDITABLE);
        textDate.setText(messageDate,TextView.BufferType.EDITABLE);
        textAmmount.setText(messageAmmount,TextView.BufferType.EDITABLE);
        textComment.setText(messageComment,TextView.BufferType.EDITABLE);

        GetData();//handles button for edit
        deleteData();//handles delete button
    }

    private void GetData(){
        Button btn = (Button) findViewById(R.id.detEdit);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean error = false;               //error handling if this is True, dont do anything

                //getting data from editted text
                EditText edit1 = (EditText) findViewById(R.id.subDetailName);
                String message1 = edit1.getText().toString();
                EditText edit2 = (EditText) findViewById(R.id.subDetailDate);
                String message2 = edit2.getText().toString();
                EditText edit3 = (EditText) findViewById(R.id.subDetailAmmount);
                String message3 = edit3.getText().toString();
                EditText edit4 = (EditText) findViewById(R.id.subDetailComment);
                String message4 = edit4.getText().toString();

                //error handling for input data
                if ((message1.length())==0 ||  (message1.length())> 20){ //check if name too long
                    error = true;
                }
                try{ //check if amount is a number
                    Double.valueOf(message3);
                    if (Double.valueOf(message3)<0){ //check if negative
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
                if ((message1.length())==0 ||  (message1.length())> 30){//check if comment too long
                    error = true;
                }
                if (error == false) {//if there are no errors
                    Intent intent = new Intent();
                    intent.putExtra("editPositionBack", position);  //possible error
                    intent.putExtra("editNameBack", message1);
                    intent.putExtra("editDateBack", message2);
                    intent.putExtra("editAmmountBack", message3);
                    intent.putExtra("editCommentBack", message4);
                    setResult(Activity.RESULT_OK, intent);

                    finish();
                }
            }

        });

    }

    private void deleteData(){//handles delete button
        Button btn2 = (Button) findViewById(R.id.detDelete);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent();
                intent.putExtra("editPositionBack", "-1");  //flag to let main know that delete is requested
                intent.putExtra("deletePosition", position);  //send delete position

                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        });
    }
}
