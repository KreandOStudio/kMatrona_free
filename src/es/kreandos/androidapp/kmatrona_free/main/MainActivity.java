package es.kreandos.androidapp.kmatrona_free.main;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import es.kreandos.androidapp.kmatrona_free.R;
import es.kreandos.androidapp.kmatrona_free.naegele.NaegeleActivity;

public class MainActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TabHost tabHost = getTabHost(); // Creamos el TabHost de la actividad.
        TabHost.TabSpec spec; // Creamos un recurso para las propiedades de la pestaña.
        Intent intent; // Intent que se utilizara para abrir cada pestaña.
        Resources res = getResources(); // Obtenemos los recursos.
        
        intent = new Intent().setClass(this, NaegeleActivity.class);
        
        spec = tabHost.newTabSpec("Naegele").setIndicator("Calculo Naegele", res.getDrawable(R.drawable.naegele_pest))
        		.setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, TareasSQLiteActivity.class);
        spec = tabHost.newTabSpec("Historia").setIndicator("Historia/Historico", res.getDrawable(R.drawable.historia))
        		.setContent(intent);
        tabHost.addTab(spec);
        //tabHost.getTabWidget().getChildAt(3).setBackgroundColor(color.blue);
        
        // Se lanza crea el intent para abrir la actividad que en realidad es una clase:
        intent = new Intent().setClass(this, Historia.class);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
