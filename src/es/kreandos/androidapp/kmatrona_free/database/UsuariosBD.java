package es.kreandos.androidapp.kmatrona_free.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import es.kreandos.androidapp.kmatrona_free.R;
import es.kreandos.androidapp.kmatrona_free.krypto.SimpleKrypto;

public class UsuariosBD extends Activity {
	private SQLiteDatabase db;
	private UsuariosSQLiteHelper usdbh;
	private int codigo;
	/*
	 * CONSTANTES: **************************************************************************************
	 */
	static final int USU_ACTIVO_NO = 0;
	static final int USU_ACTIVO_SI = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	/*
	 * ***************************************************************************************************
	 * "CREATE TABLE Usuarios (codigo INTEGER, nombre TEXT, email TEXT, pass TEXT, otroDato TEXT, usu INTEGER)"
	 */
	}
	public void creamosBD (Context contexto, int cod, String usuario, String email, String pass, String otroDato){
		//Abrimos la base de datos 'DBUsuarios' en modo escritura
		usdbh =	new UsuariosSQLiteHelper(contexto, "DBUsuarios", null, 1);

		db = usdbh.getWritableDatabase();
    
		//Si hemos abierto correctamente la base de datos
		if(db != null)
		{
			ContentValues nuevoRegistro = new ContentValues();
			
			nuevoRegistro.put("codigo", cod);
			nuevoRegistro.put("nombre", usuario);
			nuevoRegistro.put("email", email);
			nuevoRegistro.put("pass", pass);
			nuevoRegistro.put("otroDato", otroDato);
			nuevoRegistro.put("usu", USU_ACTIVO_NO);
			
			db.insert("Usuarios", null, nuevoRegistro);
			
			//Cerramos la base de datos
			db.close();
		}
	}
	
	public int devuelveUltimoCodigo(Context con) {
		usdbh =	new UsuariosSQLiteHelper(con, "DBUsuarios", null, 1);
		db = usdbh.getReadableDatabase();

		int in = 0;
		Cursor contenido = db.rawQuery("Select codigo from Usuarios", null);		
 
		//Ir al primer registro:
		if (contenido.moveToFirst()){
			do {
				in++;
			} while(contenido.moveToNext());
		}
		db.close();
		return in;
	}
	
	public String devuelvePrimerUsuario(Context con) {
		usdbh = new UsuariosSQLiteHelper(con, "DBUsuarios", null, 1);
		db = usdbh.getReadableDatabase();
		String nombre = "No existe.";
		int in = 0;
		Cursor contenido = db.rawQuery("Select nombre from Usuarios", null);
		if (contenido.moveToFirst()) {
			nombre = contenido.getString(in);
		}
		db.close();
		return nombre;
	}
	
	public String devuelvePrimerPass(Context con) {
		usdbh = new UsuariosSQLiteHelper(con, "DBUsuarios", null, 1);
		db = usdbh.getReadableDatabase();
		String pass = "No existe.";
		String oPas = "";
		Cursor c = db.rawQuery("Select pass from Usuarios", null);
		Cursor o = db.rawQuery("Select otroDato from Usuarios", null);
		if (c.moveToFirst()) {
			o.moveToFirst();
			pass = c.getString(0).toString();
			oPas = o.getString(0).toString();
			try {
				oPas = SimpleKrypto.decrypt(oPas, pass);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//String error = "" + e.getCause();
				//Toast.makeText(UsuariosBD.this, "Ha fallado algo!!" 
				//		+ "\n" + error, Toast.LENGTH_LONG).show();
				
			}
		}
		db.close();
		return oPas;
	}
	
	public boolean existeUsuario(Context con, String nomb, String pass) {//, String otro) {
		usdbh = new UsuariosSQLiteHelper(con, "DBUsuarios", null, 1);
		db = usdbh.getReadableDatabase();
		boolean existeNom = false;
		boolean existePas = false;
		boolean existe = false;
		int in = 0;
		int y = 0;
		
		Cursor nom = db.rawQuery("Select nombre from Usuarios", null);
		Cursor pas = db.rawQuery("Select pass from Usuarios", null);
		Cursor otr = db.rawQuery("Select otroDato from Usuarios", null);
		Cursor cod = db.rawQuery("Select usu from Usuarios", null);
		String dec = "";
		
		if (nom.moveToFirst()) {
			do {
				if (nom.getString(nom.getColumnIndex("nombre")).contentEquals(nomb)) {
					existeNom = true; 
					y = in;
				}
				in++;
			} while (nom.moveToNext());
			pas.moveToPosition(y);
			otr.moveToPosition(y);
			cod.moveToPosition(y);
			
			String paswd = pas.getString(pas.getColumnIndex("pass")).toString();
			String otroD = otr.getString(otr.getColumnIndex("otroDato")).toString();
			//int i = cod.getInt(cod.getColumnIndex("usu"));
			//setCodigo(cod.getInt(cod.getColumnIndex("usu")));
			setCodigo(y);
			
			try {
				dec = SimpleKrypto.decrypt(otroD, paswd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (dec.contentEquals(pass)) {
				existePas = true;
			}
		}
		if ((existeNom) && (existePas)) {
			existe = true;
		}
		db.close();
		return existe;
	}
/*
 * **** GETTER y SETTER de Codigo usuario: *************************************************************************
 */
	public int getCodigo(){
		return codigo;
	}
	public void setCodigo(int cod) {
		codigo = cod;
	}
/*
 * ***** Fin de GETTER y SETTER de Codigo usuario. *****************************************************************	
 */
	public String devuelveUsuarioActivo(Context con) {
		usdbh = new UsuariosSQLiteHelper(con, "DBUsuarios", null, 1);
		db = usdbh.getReadableDatabase();
		int i = 0;
		String usuario_activo = "";

		Cursor usu = db.rawQuery("Select usu from Usuarios", null);
		Cursor nom = db.rawQuery("Select nombre from Usuarios", null);
		
		if (usu.moveToFirst()) {
			do {
				if (usu.getInt(usu.getColumnIndex("usu")) == USU_ACTIVO_SI) {//usu.getColumnIndexOrThrow("usu") == USU_ACTIVO_SI) {
					nom.moveToPosition(i);
					usuario_activo = nom.getString(nom.getColumnIndex("nombre")).toString();
				}
				i++;
			} while(usu.moveToNext());
		}
		db.close();
		return usuario_activo;
	}
	
	public void estableceUsuarioActivo(Context con, int cod){
		usdbh = new UsuariosSQLiteHelper(con, "DBUsuarios", null, 1);
		db = usdbh.getWritableDatabase();//getReadableDatabase();
		
		//Establecemos los campos-valores a actualizar
		ContentValues valores = new ContentValues();
		valores.put("usu", USU_ACTIVO_SI);
		 
		//Actualizamos el registro en la base de datos
		db.update("Usuarios", valores, "codigo=" + cod, null);
		db.close();
	}
	
	public void estableceTodosUsuariosInactivos(Context con) {
		usdbh = new UsuariosSQLiteHelper(con, "DBUsuarios", null, 1);
		db = usdbh.getWritableDatabase();//getReadableDatabase();
		int i = 0;
		Cursor usu = db.rawQuery("Select usu from Usuarios", null);
		ContentValues valores = new ContentValues();
		valores.put("usu", USU_ACTIVO_NO);
		
		if (usu.moveToFirst()) {
			do {
				//if (usu.getColumnIndexOrThrow("usu") == USU_ACTIVO_SI) {
				if (usu.getInt(usu.getColumnIndex("usu")) == USU_ACTIVO_SI) {
					db.update("Usuarios", valores, "codigo=" + i, null);
				}
			i++;
			} while (usu.moveToNext());
		}
		db.close();
	}
}