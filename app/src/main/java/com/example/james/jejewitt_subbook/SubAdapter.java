package com.example.james.jejewitt_subbook;


import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


/**
 *Class SubAdapter
 *
 *functionality:
 * breaks down ArrayList<Subscription> to be formatted for the Listview
 * works with sub_row.xml to get formatting
 *
 */

public class SubAdapter extends ArrayAdapter {

        private final Activity context;       //reference the Activity
        private ArrayList<Subscription> sub;  //get sub values

    public SubAdapter(Activity context, ArrayList<Subscription> inSub){

        super(context,R.layout.sub_row , inSub); //init
        this.sub = inSub;
        this.context=context;

    }
    public View getView(int position, View view, ViewGroup parent) {//break down ArrayList<Subscription> into 1 subsciption for sub_row.xml
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.sub_row, null,true);

        //inits textviews for formatting
        TextView nameTextField = (TextView) rowView.findViewById(R.id.subName);
        TextView dateTextField = (TextView) rowView.findViewById(R.id.subDate);
        TextView ammountTextField = (TextView) rowView.findViewById(R.id.subAmmount);
        TextView commentTextField = (TextView) rowView.findViewById(R.id.subComment);


        //sets textviews format
        nameTextField.setText(sub.get(position).getName());
        dateTextField.setText(sub.get(position).getDate()); //check later
        ammountTextField.setText((sub.get(position).getAmmount())+"$");
        commentTextField.setText(sub.get(position).getComment());

        return rowView;

    }
}
