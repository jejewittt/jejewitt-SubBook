/*
 * Citations of used material
 *
 * https://appsandbiscuits.com/listview-tutorial-android-12-ccef4ead27cc
 * https://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android
 * https://www.youtube.com/watch?v=gh4nX-m6BEo
 * https://stackoverflow.com/questions/10349061/android-default-value-in-edittext
 * https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
 */

package com.example.james.jejewitt_subbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 * MainActivity class
 *
 * functionality:
 * loads file data with loadData()
 * sets listview
 * handles add button
 * handles returned values from add, and subDetails
 * saves data with update()
 * updates data with deleteData() and updateData()
 * handles textview that deals with total account numbers (refreshAccounts())
 */

public class MainActivity extends AppCompatActivity {
    ArrayList<Subscription> sub;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sub= loadData();   // load data from file

        //setup interface
        setupList();
        refreshAccounts();
        addButton();
    }

    private void addButton(){//handles going to AddSub activity
        Button btn = (Button)findViewById(R.id.addNew);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, AddSub.class),1);

            }
        });
    }

    private void setupList(){ //sets up for handling list and list updating
        //create adapter and list for list of subscriptions
        SubAdapter subAdapter = new SubAdapter(this, sub);
        listView = (ListView) findViewById(R.id.SubList);
        listView.setAdapter(subAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, SubDetails.class);
                String messageName = sub.get(position).getName();
                String messageDate =  sub.get(position).getDate();
                String messageAmmount = sub.get(position).getAmmount();
                String messageComment = sub.get(position).getComment();

                intent.putExtra("messagePosition", Integer.toString(position));
                intent.putExtra("messageName", messageName);
                intent.putExtra("messageDate", messageDate);
                intent.putExtra("messageAmmount", messageAmmount);
                intent.putExtra("messageComment", messageComment);
                startActivityForResult(intent,2);
            }
        });
    }

    private void refreshAccounts(){//calculate and display subscription cost
        double total = 0;

        for (Subscription s: sub){//for each subscription
            total += Double.parseDouble(s.getAmmount());  //summing up the monthly cost of all subs
        }
        String totalString = "Monthly Cost: "+String.format("%.2f", total)+"$"; //display text
        TextView accounts = (TextView)findViewById(R.id.accounting);
        accounts.setText(totalString);
    }

    private void deleteData(int position){ //delete item from sub
        sub.remove(position);
        refreshAccounts();
        update();
    }

    private void updateData(String name, String Date, String ammount, String Comment, int position){//update item from sub

        if (position == -1){//append to end
            sub.add(new Subscription(name,Comment,Date,Double.parseDouble(ammount)));
        }else{
            sub.get(position).setName(name);
            sub.get(position).setDate(Date);
            sub.get(position).setComment(Comment);
            sub.get(position).setAmmount(Double.parseDouble(ammount));
        }
        refreshAccounts();
        update();
    }

    private void update(){ //update the file and array adapter
        try {//update file
            FileOutputStream fos = openFileOutput("file.txt",Context.MODE_PRIVATE);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(sub);
            oos.close();
            fos.close();
        }
        catch(IOException ex){
            System.out.println("Cannot perform input. Class not found. 4" + ex); ex.printStackTrace();
        }
        SubAdapter subAdapter = new SubAdapter(this, sub); //update adapter
        listView = (ListView) findViewById(R.id.SubList);
        listView.setAdapter(subAdapter);
        subAdapter.notifyDataSetChanged();
    }

    private ArrayList<Subscription> loadData() { //load data from file
        ArrayList<Subscription> recSub;
        try  {  //try to read file
            //deserialize the List
            FileInputStream fis = openFileInput("file.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            recSub = (ArrayList<Subscription>) ois.readObject();
            ois.close();
            fis.close();
            return recSub;
        } catch (ClassNotFoundException ex) {
            System.out.println("Cannot perform input. Class not found.");

        } catch (IOException ex) {
            System.out.println("Cannot perform input. 3");
        }
        ArrayList<Subscription> subError = new ArrayList<Subscription>();//init empty ArrayList<Subscription> if error
        return subError;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){  //handles return values from addSub and SubDetails
        if(requestCode==1){  //add to the end
            if (resultCode == Activity.RESULT_OK){
                String name = data.getStringExtra("name");
                String date = data.getStringExtra("date");
                String ammount = data.getStringExtra("ammount");
                String comment = data.getStringExtra("comment");
                System.out.println(name+ " "+ date+ " "+ammount+ " "+comment);
                updateData(name,date,ammount,comment,-1);
            }
        }else if (requestCode==2){ //edit or delete
            if (resultCode == Activity.RESULT_OK){
                String position = data.getStringExtra("editPositionBack"); //position in string form

                if (Integer.valueOf(position)==-1){// if position is -1 delete
                    String deletePositon = data.getStringExtra("deletePosition"); //keeps track of position for delete
                    System.out.println(deletePositon);System.out.println(" a");System.out.println("a");System.out.println("a");
                    deleteData(Integer.valueOf(deletePositon));
                }else{
                    String name = data.getStringExtra("editNameBack");
                    String date = data.getStringExtra("editDateBack");
                    String ammount = data.getStringExtra("editAmmountBack");
                    String comment = data.getStringExtra("editCommentBack");
                    System.out.println(name+ " "+ date+ " "+ammount+ " "+comment);
                    updateData(name,date,ammount,comment,Integer.valueOf(position));
                }

            }
        }
    }

}
