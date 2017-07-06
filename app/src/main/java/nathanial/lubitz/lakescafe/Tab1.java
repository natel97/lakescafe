package nathanial.lubitz.lakescafe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nate on 4/11/2017.
 */

public class Tab1 extends Fragment implements View.OnClickListener{
    private View mview;
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mview =  inflater.inflate(R.layout.tab1, container,false);
        final ListView list = (ListView) mview.findViewById(R.id.List);
        final DatabaseHelper myDB = new DatabaseHelper(getContext());
        myDB.buttonFix();
        ArrayList<String> items = new ArrayList<>();
        final Cursor sect = myDB.loadSections();
        Integer currentLoc = 0;
        sect.moveToFirst();
        for(int x = 0; x < sect.getCount(); x++){
            items.add(sect.getString(1));
            sect.moveToNext();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);

        list.setAdapter(arrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer curView = myDB.getView();

                if (curView == 0){
                    Cursor itemsdb = myDB.getItems(position + 1);
                    itemsdb.moveToFirst();
                    ArrayList<String> stuff = new ArrayList<>();
                    stuff.add("UP");
                    for(int x = 0; x < itemsdb.getCount(); x++){
                        stuff.add(itemsdb.getString(1) + "\t\t $" + itemsdb.getString(3));
                        itemsdb.moveToNext();
                        ArrayAdapter<String> aad = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,stuff);
                        list.setAdapter(aad);
                    }
                    myDB.setCurrentSection(position + 1);
                    myDB.swapViews();
                }
                else{
                    if (position == 0) {
                        sect.moveToFirst();
                        ArrayList<String> ee = new ArrayList<>();
                        for (int x = 0; x < sect.getCount(); x++) {
                            ee.add(sect.getString(1));
                            sect.moveToNext();
                        }
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ee);
                        list.setAdapter(arrayAdapter);
                        myDB.swapViews();
                    }
                    else{
                        Cursor itemsdb = myDB.getItems(myDB.getCurrentSection());
                        itemsdb.moveToFirst();
                        itemsdb.moveToPosition(position - 1);
                        myDB.addItemToTable(itemsdb.getInt(0));
                        Toast.makeText(getContext(), "Added Item To Table " + String.valueOf(myDB.getCurrentTable()), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return mview;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }

    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}