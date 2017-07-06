package nathanial.lubitz.lakescafe;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class Tab3 extends Fragment implements View.OnClickListener{
    private View mview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mview =  inflater.inflate(R.layout.tab3, container,false);
        final ListView tItems = (ListView) mview.findViewById(R.id.TItems);
        Double tot = 0.00;
        final Button btn = (Button) mview.findViewById(R.id.Refresh);
        btn.setOnClickListener(this);
        final DatabaseHelper myDB = new DatabaseHelper(getContext());
        ArrayList<String> items = new ArrayList<>();
        final Cursor sect = myDB.getTableItems(myDB.getCurrentTable());
        sect.moveToFirst();
        for(int x = 0; x < sect.getCount(); x++){
            items.add(sect.getString(0) + "\t\t $" + sect.getString(1));
            sect.moveToNext();
        }
        items.add("SubTotal: $" + String.format("%.2f", (tot)));
        items.add("Tax: $" + String.format("%.2f",(tot * 0.07375)));
        items.add("Total: $" + String.format("%.2f", (tot * 1.07375)));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);

        tItems.setAdapter(arrayAdapter);
        tItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > parent.getCount() - 4)
                    return;
                Cursor itemsdb = myDB.getTableItems(myDB.getCurrentTable());
                itemsdb.moveToFirst();
                Double tot = 0.00;
                itemsdb.moveToPosition(position);
                Toast.makeText(getContext(),"Remove: " + itemsdb.getString(0), Toast.LENGTH_SHORT).show();
                myDB.removeItemFromTable(itemsdb.getInt(2));
                Cursor tempCursor = myDB.getTableItems(myDB.getCurrentTable());
                tempCursor.moveToFirst();
                ArrayList<String> newitems = new ArrayList<>();
                for(int x = 0; x < tempCursor.getCount(); x++){
                    newitems.add(tempCursor.getString(0));
                    tot += tempCursor.getDouble(1);
                    tempCursor.moveToNext();
                }
                newitems.add("SubTotal: $" + String.format("%.2f", (tot)));
                newitems.add("Tax: $" + String.format("%.2f",(tot * 0.07375)));
                newitems.add("Total: $" + String.format("%.2f", (tot * 1.07375)));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,newitems);
                myDB.buttonFix();
                tItems.setAdapter(arrayAdapter);
            }});
        return mview;
    }
    @Override
    public void onClick(View v) {
        DatabaseHelper myDB = new DatabaseHelper(getContext());
        ListView tItems = (ListView) mview.findViewById(R.id.TItems);
        switch (v.getId()) {
            case R.id.Refresh:
                ArrayList<String> items = new ArrayList<>();
                final Cursor sect = myDB.getTableItems(myDB.getCurrentTable());
                sect.moveToFirst();
                Double runTotal = 0.00;
                for(int x = 0; x < sect.getCount(); x++){
                    items.add(sect.getString(0) + "\t\t $" + sect.getString(1));
                    runTotal += sect.getDouble(1);
                    sect.moveToNext();
                }
                    items.add("SubTotal: $" + String.format("%.2f", (runTotal)));
                    items.add("Tax: $" + String.format("%.2f",(runTotal * 0.07375)));
                    items.add("Total: $" + String.format("%.2f", (runTotal * 1.07375)));
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,items);
                myDB.buttonFix();
                tItems.setAdapter(arrayAdapter);
                break;
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