package org.esiea.arellano.sylla.library_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity2 extends AppCompatActivity {

    private BookAdapter bookAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Recycler View
        RecyclerView rv = findViewById(R.id.rv_book);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bookAd= new BookAdapter(getBooksFromFile());
        rv.setAdapter(bookAd);
    }

    public void backOnclickAction(View view) {
        // l'intent de retour sur la page d'accueil
        Intent i = new Intent(this,MainActivityLib.class);
        startActivity(i);
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


    // Methode BookUpdate
    public class BookUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            Log.d("tag",intent.getAction() + "  recu");
            bookAd.setNewBook(getBooksFromFile());
        }
    }
}