package es.kreandos.androidapp.kmatrona_free.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import es.kreandos.androidapp.kmatrona_free.R;
import es.kreandos.androidapp.kmatrona_free.database.UsuariosBD;
import es.kreandos.androidapp.kmatrona_free.krypto.SimpleKrypto;

public class RegistroActivity extends Activity implements OnClickListener {
	private EditText usuario;
	private EditText email;
	private EditText pass;
	private EditText otroDato;
	private Context estaClase = RegistroActivity.this;
	private String krypPass;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		
		usuario 	= (EditText) findViewById(R.id.EditTextRegistroUsuario); 
		email	 	= (EditText) findViewById(R.id.EditTextRegistroEmail);
		pass	 	= (EditText) findViewById(R.id.EditTextRegistroPass);
		otroDato	= (EditText) findViewById(R.id.EditTextRegistrOtroDato);
		
		otroDato.setText(getDeviceId() +"");
		//otroDato.setText("abcd");
		
		Button alta  = (Button) findViewById(R.id.registro);
		alta.setOnClickListener(this);
		
	}

	private String getDeviceId() {
		// TODO Auto-generated method stub
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId(); // getDeviceId() Obtiene el imei		return null;
		return imei;
	}

	public void onClick(View arg0) {
 	   	AlertDialog.Builder builder = new AlertDialog.Builder(this);

 	   	switch(arg0.getId()){
 	   	case(R.id.registro):
 	   		/*
 	   		 * ************** REGISTRO: ************************************************************
 	   		 */
 	   		UsuariosBD uBD = new UsuariosBD();

 	   		try {
				setKrypPass(SimpleKrypto.encrypt(otroDato.getText().toString(), pass.getText().toString()));
 	   			//setKrypPass(SimpleKrypto.encrypt("abcd", pass.getText().toString()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

 	   		if(uBD.existeUsuario(estaClase, usuario.getText().toString(), pass.getText().toString())) {//getKrypPass())) {
 	 	   		builder.setMessage("El usuario existe.\n"+
 	 	 				"Introduzca usuario nuevo."+" Gracias.")
 	 	 	           .setTitle("¡¡ATENCION!!")
 	 	 	           .setCancelable(false)
 	 	 	           .setNeutralButton("Aceptar",
 	 	 	                   new DialogInterface.OnClickListener() {
 	 	 	                       public void onClick(DialogInterface dialog, int id) {
 	 	 	                           dialog.cancel();
 	 	 	                       }
 	 	 	                   });
 	 	 	   		AlertDialog alert = builder.create();
 	 	 	   		alert.show();
 	 	 	   		borrarCampos();
 	   		} else {
 	   			uBD.creamosBD(RegistroActivity.this, uBD.devuelveUltimoCodigo(this), 
 	   				usuario.getText().toString(), 
 	   				email.getText().toString(), 
 	   				getKrypPass(),
 	   				otroDato.getText().toString());
 	   			Toast.makeText(RegistroActivity.this, "Usuario: "+ usuario.getText().toString() 
					+", creado correctamente.", 
					Toast.LENGTH_LONG).show();
 	   			borrarCampos();
 	   		}
 	   		break;
 	   		/*
 	   		 * ************** FIN REGISTRO. ********************************************************
 	   		 */
 	   	default:
 	   		builder.setMessage("Por mantenimiento, aun no esta disponible dicha función.\n"+
 				"Disculpe las molestias."+" Gracias.")
 	           .setTitle("¡¡ATENCION!!")
 	           .setCancelable(false)
 	           .setNeutralButton("Aceptar",
 	                   new DialogInterface.OnClickListener() {
 	                       public void onClick(DialogInterface dialog, int id) {
 	                           dialog.cancel();
 	                       }
 	                   });
 	   		AlertDialog alert = builder.create();
 	   		alert.show();
 	   	}
 	   	finish();
	}

	private void borrarCampos() {
		// TODO Auto-generated method stub
		usuario.setText("");
		email.setText("");
		pass.setText("");
		otroDato.setText("");
	}

	public String getKrypPass() {
		return krypPass;
	}

	public void setKrypPass(String krypPass) {
		this.krypPass = krypPass;
	}

}