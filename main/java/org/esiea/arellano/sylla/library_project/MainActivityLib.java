package org.esiea.arellano.sylla.library_project;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;

public class MainActivityLib extends AppCompatActivity {

    private DatePickerDialog dpd = null;
    private BookAdapter bookAd;
    public static final String BOOKS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lib);    //activité correspondante

        //Button action listener and TOAST
     /*   Button toastButton = (Button)findViewById(R.id.toastID);
        toastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TOAST
                Toast.makeText(getApplicationContext(),getString(R.string.msg), Toast.LENGTH_LONG).show();
            }
        });*/

        // Affichage de la date
        TextView home =(TextView)findViewById(R.id.tv_home_page);
        String now = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);
        home.setText(now);

        // DatePickerDialog
        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                TextView tv_textView = (TextView) findViewById(R.id.textView);
                tv_textView.setText(i2 + "/" + i1 +"/"+ i);
            }
        };
        dpd= new DatePickerDialog(this,odsl,2018,10,5);

        //Services & Threading
        IntentFilter intentFilter = new IntentFilter(BOOKS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BookUpdate(),intentFilter);
        GetBiersServices.startActionBiers(this);
        /*

        //Recycler View
        RecyclerView rv = findViewById(R.id.rv_book);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bookAd= new BookAdapter(getBooksFromFile());
        rv.setAdapter(bookAd);
        */
    }

    // Menu Actions
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item_share:
                Toast.makeText(this, "Click on 'My library' to see all your books", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.item_delete:
                Toast.makeText(this, "CLick on 'DIALOG_DATE' for more information about this app ", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // DIALONG METHOD
    public void dialogAction(View view) {
        //Toast
        Toast.makeText(getApplicationContext(),getString(R.string.msg),Toast.LENGTH_LONG).show();
        // DatePickerDialog
        dpd.show();
    }

    // test des notifications
    public void notification_test(){
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Validation")
                .setContentText("Votre commande est reservée Sylla")
                .setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notifBuilder.build());
    }

    public void aventureOnClickAction(View view){
        // notification
        notification_test();
    }

    public void AnimOnClick(View view) {
        // les intents
        Intent i = new Intent(this,MainActivity2.class);
        startActivity(i);
    }

    public void mapOnClick(View view) {
        // show LONDON'S MAP
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Londre")));
    }

    // JSONObject
    public JSONArray getBooksFromFile(){
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "books.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            JSONArray array = new JSONObject(new String(buffer, "UTF-8")).getJSONArray("objects");
            return array;
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    // My Library action listener
    public void MyLibrary(View view) {
        //Intent i = new Intent(this,ImagesListView.class);
        Intent i = new Intent(this,AllBooksListeView.class);
        startActivity(i);
    }

    // Best books actionListener
    public void BestBooks(View view){
        Intent i = new Intent(this,ImagesListView.class);
        startActivity(i);
    }

    // Methode BookUpdate
    public class BookUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Log.d("tag",intent.getAction() + "  recu");
            bookAd.setNewBook(getBooksFromFile());
        }
    }
}

