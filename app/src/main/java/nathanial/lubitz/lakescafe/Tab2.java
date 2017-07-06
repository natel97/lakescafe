package nathanial.lubitz.lakescafe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

public class Tab2 extends Fragment implements View.OnClickListener{
    private View mview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mview =  inflater.inflate(R.layout.tab2, container,false);
        Button upd = (Button) mview.findViewById(R.id.NewTable);
        final DatabaseHelper myDB = new DatabaseHelper(getContext());
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.newTable();
                 ListView tables = (ListView)mview.findViewById(R.id.Tables);
                 DatabaseHelper myDB = new DatabaseHelper(getContext());
                ArrayList<String> items = new ArrayList<>();
                 Cursor sect = myDB.getTables();
                sect.moveToFirst();
                for(int x = 0; x < sect.getCount(); x++){
                    items.add(sect.getString(0));
                    sect.moveToNext();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);

                tables.setAdapter(arrayAdapter);
            }
        });
        final ListView tables = (ListView)mview.findViewById(R.id.Tables);
        tables.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> items = new ArrayList<>();
                Cursor temp = myDB.getTables();
                temp.moveToPosition(position);
                myDB.removeTable(temp.getInt(0));
                Cursor sect = myDB.getTables();
                sect.moveToFirst();
                for(int x = 0; x < sect.getCount(); x++){
                    items.add(sect.getString(0));
                    sect.moveToNext();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);

                tables.setAdapter(arrayAdapter);
                return false;
            }
        });
        ArrayList<String> items = new ArrayList<>();
        final Cursor sect = myDB.getTables();
        sect.moveToFirst();
        for(int x = 0; x < sect.getCount(); x++){
            items.add(sect.getString(0));
            sect.moveToNext();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);

        tables.setAdapter(arrayAdapter);
        tables.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor temp = myDB.getTables();
                temp.moveToPosition(position);
                myDB.setCurrentTable(temp.getInt(0));
                Toast.makeText(getContext(),"Set table to table " + temp.getString(0), Toast.LENGTH_SHORT).show();
                ArrayList<String> items = new ArrayList<>();
                Cursor sect = myDB.getTables();
                sect.moveToFirst();
                for(int x = 0; x < sect.getCount(); x++){
                    items.add(sect.getString(0));
                    sect.moveToNext();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);

                tables.setAdapter(arrayAdapter);
            }});
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
    public void refresh(){
        DatabaseHelper myDB = new DatabaseHelper(getContext());
        ArrayList<String> items = new ArrayList<>();
        Cursor sect = myDB.getTables();
        sect.moveToFirst();
        for(int x = 0; x < sect.getCount(); x++){
            items.add(sect.getString(0));
            sect.moveToNext();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);
        ListView tables = (ListView) mview.findViewById(R.id.Tables);
        tables.setAdapter(arrayAdapter);
    }
}