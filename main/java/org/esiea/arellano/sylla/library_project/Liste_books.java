package org.esiea.arellano.sylla.library_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.esiea.arellano.sylla.library_project.model.AndroidVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Liste_books extends AppCompatActivity implements LoadJSONTask.Listener, AdapterView.OnItemClickListener{

    private ListView mListView;

    public static final String URL = "http://86.64.78.35/data_books/liste_books.json";

    private List<HashMap<String, String>> mAndroidMapList = new ArrayList<>();

    private static final String KEY_TITRE = "Titre";
    private static final String KEY_AUTEUR = "Auteur";
    private static final String KEY_EDITORIAL = "Edition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_books_json);

        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(URL);

    }

    @Override
    public void onLoaded(List<AndroidVersion> androidList) {

        for (AndroidVersion android : androidList) {

            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_TITRE, android.getTitre());
            map.put(KEY_AUTEUR, android.getAuteur());
            map.put(KEY_EDITORIAL, android.getEditorial());

            mAndroidMapList.add(map);
        }

        loadListView();
    }

    @Override
    public void onError() {

        Toast.makeText(this, "Error ! bad network connection", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(this, mAndroidMapList.get(i).get(KEY_TITRE),Toast.LENGTH_LONG).show();
    }

    private void loadListView() {

        ListAdapter adapter = new SimpleAdapter(Liste_books.this, mAndroidMapList, R.layout.list_item,
                new String[] { KEY_TITRE, KEY_AUTEUR, KEY_EDITORIAL },
                new int[] { R.id.version,R.id.name, R.id.api });

        mListView.setAdapter(adapter);
    }
}
