package es.kreandos.androidapp.kmatrona_free.database;
/*********************************************************************************************
 * Clase Ficheros:
 * ---------------
 * .-Esta clase sirve para inicializar y guardar datos en los ficheros..
 *********************************************************************************************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.util.Log;

public class Ficheros {
	private static String sFichKrypto = "Krypto.dat";
	
	/*********************************************************************************************
	 * Metodo inicializacionDeFicheros():
	 * @param sFichero Texto que sirve para indicarle el nombre del fichero.
	 * @param fichGeneral Sirve para inicializar el fichero con nombre que contenga sFichero.
	 * @throws IOException 
	 *********************************************************************************************/
	public void inicializadorDeFicheros(Context context, String nomFichero) {
		OutputStreamWriter f;
		try {
			f = new OutputStreamWriter(
					context.getApplicationContext().openFileOutput(
							nomFichero, Context.MODE_APPEND));
			f.append("Pues aun no lo se;");
			f.close();
		} catch (Exception e) {
			try {
				f = new OutputStreamWriter(
						context.getApplicationContext().openFileOutput(
								nomFichero, Context.MODE_PRIVATE));
				f.close();
			} catch (Exception ex) {
				Log.e("Ficheros", "�ERROR! No se puede crear el Fichero.");
			}
		}
	}
	public void inicializacionDeFichero(Context context,
			String txtUser, String txtPass, String txtOther) {
		OutputStreamWriter f;
		try {
			f =	new OutputStreamWriter(
					context.getApplicationContext().openFileOutput(
							sFichKrypto, Context.MODE_APPEND));
			f.append(txtUser +" | "+ txtPass +" | "+ txtOther +"||"+"\n");
			f.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			try {
				f = new OutputStreamWriter(
						context.getApplicationContext().openFileOutput(
								sFichKrypto, Context.MODE_PRIVATE));
				f.close();
			} catch(Exception ex) {
				Log.e("Ficheros","�ERROR! No se puede crear el Fichero.");				
			}
		}
	}


	/*********************************************************************************************
	 * Metodo devuelveFichero():
	 * .-Sirve para "cortar" el fichero usando un identificador (";").
	 *********************************************************************************************/
	public String devuelveFichero(Context context) throws IOException{
		String[] text;
		String sTexto, dText = null, sTexto2="";
		//BufferedReader sFichVen = new BufferedReader(new FileReader(sFichKrypto));
		BufferedReader sFichVen = new BufferedReader(new InputStreamReader(
						context.openFileInput(sFichKrypto)));
		while ((sTexto = sFichVen.readLine())!=null) {
			System.out.println(sTexto);
			sTexto2 = sTexto2 + sTexto;
			} 
		if(sTexto2!=null & !sTexto2.contentEquals("")){
			text = sTexto2.split("||");
			for(int i=0; i<text.length; i++){
				//System.out.println(text[i]);
				//Log.e(text[i]);
				dText = dText + text[i];
			}
			try {
				sFichVen.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dText;
	}
}
