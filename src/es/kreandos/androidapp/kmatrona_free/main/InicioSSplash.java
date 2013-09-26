package es.kreandos.androidapp.kmatrona_free.main;

import es.kreandos.androidapp.kmatrona_free.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class InicioSSplash extends Activity {

	private static final long SPLASH_DISPLAY_LENGTH = 5000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio_screen_splash);
		Handler handler = new Handler();
		handler.postDelayed(getRunnableStartApp(), SPLASH_DISPLAY_LENGTH);}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_inicio_screen_splash, menu);
		return true;
	}
	/**
	* Metodo en el cual se debe incluir dentro de run(){Tu codigo} el codigo que se quiere realizar una
	* vez ha finalizado el tiempo que se desea mostrar la actividad de splash
	* @return
	*/
	private Runnable getRunnableStartApp(){
		Runnable runnable = new Runnable(){
			public void run(){
				//al interior valida si deben insertarse registros e inserta las estaciones si no existen
				//EstacionesMetroBO estacionesMetroBO = BusinessContext.getBean(EstacionesMetroBO.class);
				//estacionesMetroBO.insertRecordsEstacionesMetro();
				Intent intent = new Intent(InicioSSplash.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		};
		return runnable;
	}
}
