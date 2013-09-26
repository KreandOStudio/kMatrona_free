package es.kreandos.androidapp.kmatrona_free.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import es.kreandos.androidapp.kmatrona_free.R;
//import es.kreandos.androidapp.ibaby.database.Ficheros;
import es.kreandos.androidapp.kmatrona_free.database.UsuariosBD;
import es.kreandos.androidapp.kmatrona_free.naegele.NaegeleActivity;

public class LoginActivity extends Activity implements OnClickListener {
	public static final String TAG = "LoginActivity";
	private Context estaClase = LoginActivity.this;
	//public static final String TAG = LoginActivity.class.getName(); 
	private EditText usuario;// = (EditText) findViewById(R.id.user);
	private EditText passwrd;// = (EditText) findViewById(R.id.pass);
	String crypto = null;
	String cleartext = null;
	
	UsuariosBD uDB = new UsuariosBD();
	
	@Override
	protected void onRestart() {
		super.onRestart();
		updateDisplay();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
				
		Button login = (Button) findViewById(R.id.login);
		login.setOnClickListener(this);

		Button alta  = (Button) findViewById(R.id.registro);
		alta.setOnClickListener(this);

		Button social= (Button) findViewById(R.id.compartir);
		social.setOnClickListener(this);
		
		usuario = (EditText) findViewById(R.id.user);
		passwrd = (EditText) findViewById(R.id.pass);
       	updateDisplay();
	}
	
	private void updateDisplay() {
		// TODO Auto-generated method stub
		String texto1 = uDB.devuelvePrimerUsuario(estaClase);
		String texto2 = uDB.devuelvePrimerPass(estaClase);
		usuario.setText(texto1);//uDB.devuelvePrimerUsuario(estaClase));
		passwrd.setText(texto2);//uDB.devuelvePrimerPass(estaClase));
		uDB.estableceTodosUsuariosInactivos(estaClase);
	}
	private void borrarCampos() {
		// TODO Auto-generated method stub
		usuario.setText("");
		passwrd.setText("");
	}
	
	public void onClick(View v) {
		//updateDisplay();
		switch(v.getId()) {
			case R.id.login:
				boolean usuarioCorrecto = uDB.existeUsuario(estaClase, 
						usuario.getText().toString(), 
						passwrd.getText().toString());
				if (usuarioCorrecto==true) {
					//Intent intent = new Intent(estaClase, MainActivity.class);
					Intent intent = new Intent(estaClase, NaegeleActivity.class);
					uDB.estableceUsuarioActivo(estaClase, uDB.getCodigo());
					borrarCampos();
					startActivity(intent);					
				} else {
					Toast.makeText(estaClase, "Usuario o contraseña incorrectas.", 
							Toast.LENGTH_LONG).show();					
				}
				break;
			case R.id.registro:
				try {
					Intent intent = new Intent(estaClase, RegistroActivity.class);
					startActivity(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Toast.makeText(LoginActivity.this, "Ha fallado algo!!"+
							"\n"+ e.toString(), Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.compartir:
//		 	   	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//	 	   		builder.setMessage("Por mantenimiento, aun no esta disponible dicha funcion.\n"+
//	 	 				"Disculpe las molestias."+" Gracias.")
//	 	 	           .setTitle("¡¡ATENCION!!")
//	 	 	           .setCancelable(false)
//	 	 	           .setNeutralButton("Aceptar",
//	 	 	                   new DialogInterface.OnClickListener() {
//	 	 	                       public void onClick(DialogInterface dialog, int id) {
//	 	 	                           dialog.cancel();
//	 	 	                       }
//	 	 	                   });
//	 	 	   	AlertDialog alert = builder.create();
//	 	 	   	alert.show();
	 	 	   	Social bSocial = new Social();
	 	 	   	String direccion_app = "https://play.google.com/store/apps/details?id=es.kreandos.androidapp.kmatrona&hl=es";
	 	 	   	bSocial.share(estaClase, "kMatrona_free", direccion_app);
		}
		//finish();
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
