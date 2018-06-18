package org.esiea.arellano.sylla.library_project;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;

public class AllBooksListeView extends AppCompatActivity {


    private long id;

    private DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books_liste_view);

        final Intent intent_liste_amour = new Intent(this, Liste_amour_books.class);
        final Intent intent_liste_books = new Intent(this, Liste_books.class);
        final Intent intent_liste_aventure = new Intent(this, Liste_adventure_books.class);
        final Intent intent_liste_historique = new Intent(this, Liste_historic_books.class);
        final Intent intent_liste_scfi = new Intent(this, Liste_scfi_books.class);
        /** Slide 27 = Ouverture d'une deuximme activity ***/
        Button liste_amour_activity = (Button)findViewById(R.id.button_liste_amour_activity);
        Button liste_books_activity = (Button)findViewById(R.id.button_liste_books_activity);
        Button liste_aventure_activity = (Button)findViewById(R.id.button_liste_aventure_activity);
        Button liste_historique_activity = (Button)findViewById(R.id.button_liste_historique_activity);
        Button liste_scfi_activity = (Button)findViewById(R.id.button_liste_scfi_activity);

        liste_amour_activity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(intent_liste_amour);
            }
        });
        liste_books_activity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(intent_liste_books);
            }
        });
        liste_aventure_activity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(intent_liste_aventure);
            }
        });
        liste_historique_activity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(intent_liste_historique);
            }
        });
        liste_scfi_activity.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(intent_liste_scfi);
            }
        });
    }

    /*********************************************************************************/
    @Override
    protected void onResume()
    {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, intentFilter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(downloadReceiver);
    }

    public void telecharger(View button)
    {
        downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("http://86.64.78.35/data_books/pack_best_livres.zip"));
        request.setTitle("Pack_meilleurs_libres.zip");
        request.setDescription("Pack_meilleurs_libres");

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            //Definition de l'endroit de stockage
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS,System.currentTimeMillis() + "hola.zip");
        }
        //Le téléchargement commence
        id = downloadManager.enqueue(request);
    }



    //muestra las últimas descargas realizadas con el servicio
    public void ver(View button)
    {
        Intent intent = new Intent();
        intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(intent);
    }

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        //gestionamos la finalización de la descarga
        @Override
        public void onReceive(Context context, Intent intent)
        {
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id, 0);
            Cursor cursor = downloadManager.query(query);

            if(cursor.moveToFirst())
            {
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                int reason = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));

                if(status == DownloadManager.STATUS_SUCCESSFUL)
                {
                    //podemos recuperar el fichero descargado
                    ParcelFileDescriptor file = null;
                    try
                    {
                        file = downloadManager.openDownloadedFile(id);
                        Toast.makeText(AllBooksListeView.this,"Fichier enregistré dans le dossier interne 'Download' ",Toast.LENGTH_LONG).show();
                    }
                    catch (FileNotFoundException ex)
                    {
                        Toast.makeText(AllBooksListeView.this,"Exception: " + ex.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                else if(status == DownloadManager.STATUS_FAILED)
                {
                    Toast.makeText(AllBooksListeView.this,"FAILED: " + reason,Toast.LENGTH_LONG).show();
                }
                else if(status == DownloadManager.STATUS_PAUSED)
                {
                    Toast.makeText(AllBooksListeView.this, "PAUSED: " + reason, Toast.LENGTH_LONG).show();
                }
                else if(status == DownloadManager.STATUS_PENDING)
                {
                    Toast.makeText(AllBooksListeView.this, "PENDING: " + reason, Toast.LENGTH_LONG).show();

                }
                else if(status == DownloadManager.STATUS_RUNNING)
                {
                    Toast.makeText(AllBooksListeView.this, "RUNNING: " + reason, Toast.LENGTH_LONG).show();
                }
            }
        }

    };

}
