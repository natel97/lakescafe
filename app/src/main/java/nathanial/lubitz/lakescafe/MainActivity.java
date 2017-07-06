package nathanial.lubitz.lakescafe;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHelper a = new DatabaseHelper(this);
        a.drop();
        final TextView load = (TextView) findViewById(R.id.loadText);
        Download t1 = new Download(this, 0);
        t1.execute("http://lakescafe.herokuapp.com/headings.json");
        Download t2 = new Download(this, 1);
        t2.execute("http://lakescafe.herokuapp.com/sections.json");
        Download t3 = new Download(this, 2);
        t3.execute("http://lakescafe.herokuapp.com/items.json");
        Download t4 = new Download(this, 3);
        t4.execute("http://lakescafe.herokuapp.com/sub_items.json");

        new CountDownTimer(22000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            if(a.checkIfLoad()){
                Toast.makeText(getApplicationContext(), "Finished Downloading! :)", Toast.LENGTH_LONG).show();
                Intent myTent = new Intent(MainActivity.this, tabbed.class);
                startActivity(myTent);
                this.cancel();
            }
            else{
                load.setText("Please Wait! Downloading Content! " + a.perc().toString() + "% Done.");
            }
            }

            @Override
            public void onFinish() {
                if(a.checkIfLoad() == false){
                    Toast.makeText(getApplicationContext(), "Timed Out, Please Try Again Later", Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }
}




class Download extends AsyncTask<String, Void, String>{
    private Context mContext;
    private int ttype;

    public Download(Context context, int type) {
        mContext = context;
        ttype = type;
    }

    private void toastError(String a){
        Toast.makeText(mContext,"Error: " + a, Toast.LENGTH_LONG).show();
    }
    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection;

        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();
            while (data != -1){
                char current = (char) data;

                result += current;

                data = reader.read();
            }
            return result;
        } catch (MalformedURLException e) {
            toastError(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            toastError(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        JSONObject currentobject;

        DatabaseHelper a = new DatabaseHelper(mContext);
        a.addLoad();
        switch (ttype) {
            case 0:
                try {
                    JSONArray obj = new JSONArray(result);
                    for (int x = 0; x < obj.length(); x++) {

                        currentobject = obj.getJSONObject(x);
                        a.addToHeading(
                                currentobject.getInt("id"),
                                currentobject.getString("name"),
                                currentobject.getString("created_at"),
                                currentobject.getString("updated_at"),
                                currentobject.getString("url")
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastError(e.getMessage());
                }
                break;
            case 1:
                try {
                    JSONArray obj = new JSONArray(result);
                    for (int x = 0; x < obj.length(); x++) {

                        currentobject = obj.getJSONObject(x);
                        a.addToSection(
                                currentobject.getInt("id"),
                                currentobject.getString("name"),
                                currentobject.getString("description"),
                                currentobject.getInt("heading_id"),
                                currentobject.getString("created_at"),
                                currentobject.getString("updated_at"),
                                currentobject.getString("url")
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastError(e.getMessage());
                }
                break;
            case 2:
                String asdf;
                Double prc;
                try {
                    JSONArray obj = new JSONArray(result);
                    for (int x = 0; x < obj.length(); x++) {
                        currentobject = obj.getJSONObject(x);
                        asdf = currentobject.getString("price");
                        try {prc = Double.parseDouble(asdf);} catch(Exception e) {prc = 0.00; toastError(e.getMessage());}
                        a.addToItem(
                                currentobject.getInt("id"),
                                currentobject.getString("name"),
                                currentobject.getString("description"),
                                prc,
                                currentobject.getInt("section_id"),
                                currentobject.getString("created_at"),
                                currentobject.getString("updated_at"),
                                currentobject.getString("url")
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastError(e.getMessage());
                }
                break;
            case 3:
                try {
                    JSONArray obj = new JSONArray(result);
                    for (int x = 0; x < obj.length(); x++) {

                        currentobject = obj.getJSONObject(x);
                        a.addToSubItem(
                                currentobject.getInt("id"),
                                currentobject.getString("name"),
                                currentobject.getString("description"),
                                currentobject.getDouble("price"),
                                currentobject.getInt("item_id"),
                                currentobject.getString("created_at"),
                                currentobject.getString("updated_at"),
                                currentobject.getString("url")
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastError(e.getMessage());
                }
                break;
        }
    }
}