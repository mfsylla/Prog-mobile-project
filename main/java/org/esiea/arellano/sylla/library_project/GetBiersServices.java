package org.esiea.arellano.sylla.library_project;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetBiersServices extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_get_all_biers = "org.esiea.arellano.sylla.library_project.action.get_all_biers";
    // private static final String ACTION_BAZ = "org.esiea.arellano.sylla.labrary_manager.action.BAZ";

    // TODO: Rename parameters
   /* private static final String EXTRA_PARAM1 = "org.esiea.arellano.sylla.labrary_manager.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "org.esiea.arellano.sylla.labrary_manager.extra.PARAM2";
  */
    public GetBiersServices() {
        super("GetBiersServices");
    }

    /**
     * Starts this service to perform action get_all_biers with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBiers(Context context) {
        Intent intent = new Intent(context, GetBiersServices.class);
        intent.setAction(ACTION_get_all_biers);
        context.startService(intent);
    }


    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
   /* public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetBiersServices.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_get_all_biers.equals(action)) {
                handleActionget_all_biers();
            }
        }
    }

    /**
     * Handle action get_alll_bier in the provided background thread with the provided
     * parameters.
     */
    private void handleActionget_all_biers() {
        Log.i("debug","handling action");
        URL url=null;
        try {
            url=new URL("https://pokeapi.co/api");
            //url=new URL("https://openlibrary.org/dev/docs/api/books");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(),
                        new File(getCacheDir(),"books.json"));
                Log.d("tag","Books downloaded with success!");
                //LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.BOOKS_UPDATE));
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyInputStreamToFile(InputStream in, File file){
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
   /* private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }*/
}
