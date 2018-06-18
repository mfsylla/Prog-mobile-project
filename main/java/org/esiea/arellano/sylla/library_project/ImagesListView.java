package org.esiea.arellano.sylla.library_project;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

public class ImagesListView extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_list_view);

        // LA BARRE D'OUTILS
       /*
        Toolbar toolbar1 = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Search information");
        toolbar1.setTitleTextColor("#FFFFFFF");

        MaterialSearchView searchView = (MaterialSearchView)findViewById(R.id.search_view);
      */
        ListView list = (ListView) findViewById(R.id.listView1);
        customadapter ca = new customadapter();
        list.setAdapter(ca);

    }

    // Menu Actions
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class customadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = getLayoutInflater();
            convertview = inflater.inflate(R.layout.custom_layout, null);
            TextView tv = (TextView) convertview.findViewById(R.id.textView1);
            TextView tv1 = (TextView) convertview.findViewById(R.id.textView2);
            ImageView image = (ImageView) convertview
                    .findViewById(R.id.imageView1);
            tv.setText(names[position]);
            tv1.setText(locations[position]);
            image.setImageResource(images[position]);

            return convertview;
        }
    }

    String[] names = { "Chronique Marti" ,"Le diable au corps", "Les miserables","Alpha","A New Down","Cent ans de solitude","Game OF throne","La vie devant soi","Le B.gentilhomme","Walking DEAD","Le malade Imaginaire","Ne t'éloigne pas","Le Seigneur des anneaux","Sherlock Holmes" };
    String[] locations = { "MARTIN","RAY RADIGUET","VICTOR HUGO","JASIND WILDER","JOHN MILLER","GARCIA MARQUEZ","GEORGE MARTIN","GARY","MOLIERE","Robert KIRKMAN","MOLIERE","HARLAN COBEN","J.R.R TOLKEN","A.J.LOW" };
    int[] images = { R.drawable.chroniquemarti, R.drawable.lediableducorps, R.drawable.les_miserables, R.drawable.alpha, R.drawable.anewdawn,
            R.drawable.cent_ans_desolitude, R.drawable.gameofthrone, R.drawable.la_vie_devant_soi, R.drawable.le_bougeois_gentilhomme, R.drawable.walking, R.drawable.lemalade_imaginaire,R.drawable.neteloignepas, R.drawable.seigneur_des_anneaux, R.drawable.sherlock_holmes_p};

}
