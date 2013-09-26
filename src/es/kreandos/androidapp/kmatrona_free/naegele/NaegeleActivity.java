package es.kreandos.androidapp.kmatrona_free.naegele;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import es.kreandos.androidapp.kmatrona_free.database.UsuariosBD;
import es.kreandos.androidapp.kmatrona_free.R;
/**
 * 
 * @author Alvarez Guerrero, Angel Ruben
 * Esta aplicacion consiste que en base a la fecha de ultima regla se obtienen una 
 * serie de distintas fechas y datos..
 *
 */
public class NaegeleActivity extends Activity {
/*----- Variables: ----------------------------------------------------------------------------------------------------	
 */
	private Context estaClase = NaegeleActivity.this;
	private TextView tvMostrar;
	private TextView tvFechaProbableParto;
	private TextView tvSemGestacion;
	private TextView tvProgGesta;
	private int mYear;
	private int mMonth;
	private int mDay;
	private Calendar calendario;
	private NaegeleCalculos calculosNaegele = new NaegeleCalculos();
	private UsuariosBD uBD = new UsuariosBD();
	private String semanasGestacion;
	private String fechaProbableParto;
	private String textoProg;
	private String usuario;
	private ProgressBar bProgreso;
	private DatePickerDialog.OnDateSetListener mDateSetListener =
	    new DatePickerDialog.OnDateSetListener(){
	    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
	        set_mYear(year);
	        set_mMonth(monthOfYear);
	        set_mDay(dayOfMonth);
            updateDisplay();
	    }
	};
	private ListView list;
/*----- Fin Variables -------------------------------------------------------------------------------------------------	
 */
//=====================================================================================================================
/*----- Constantes: ---------------------------------------------------------------------------------------------------	
 */
	static final int DATE_DIALOG_ID = 0;
/*----- Fin Constantes ------------------------------------------------------------------------------------------------	
 */
//=====================================================================================================================
/*----- Setters: ------------------------------------------------------------------------------------------------------
 */
	public void set_mYear(int mYear){
		this.mYear = mYear;
	}
	public void set_mMonth(int mMonth){
		this.mMonth = mMonth;
	}
	public void set_mDay(int mDay){
		this.mDay = mDay;
	}
	public String setSemanasGestacion(String semanasGestacion) {
		this.semanasGestacion = semanasGestacion;
		return semanasGestacion;
	}
	public void setFechaProbableParto(String fechaProbableParto) {
		this.fechaProbableParto = fechaProbableParto;
		//return fechaProbableParto;
	}
	public void setTextoProg(String textoProg) {
		this.textoProg = textoProg;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
/*----- Fin Setters ---------------------------------------------------------------------------------------------------
 */
//=====================================================================================================================
/*----- Getters: ------------------------------------------------------------------------------------------------------
 */
	public int get_mYear(){
		return mYear;
	}
	public int get_mMonth(){
		return mMonth;
	}
	public int get_mDay(){
		return mDay;
	}
	public String getSemanasGestacion() {
		return semanasGestacion;
	}
	public String getFechaProbableParto() {
		return fechaProbableParto;
	}
	public String getTextoProg() {
		return textoProg;
	}
	public String getUsuario() {
		return usuario;
	}
/*----- Fin Getters ---------------------------------------------------------------------------------------------------
 */
//=====================================================================================================================
/**
 * --------------------------------------------------------------------------------------------------------------------
 */
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Buscamos los componentes:
        tvMostrar 				= (TextView) findViewById(R.id.idTextoFecha);
        tvFechaProbableParto 	= (TextView) findViewById(R.id.idTextoFechaProb);
        tvSemGestacion 			= (TextView) findViewById(R.id.idSemGesta);
        tvProgGesta 			= (TextView) findViewById(R.id.idTextProgreso);
        
        TextView tv = (TextView) findViewById(R.id.textViewUsuario);
		setUsuario(uBD.devuelveUsuarioActivo(estaClase));
        tv.setText(getUsuario().toUpperCase());

        list					= (ListView)findViewById(R.id.listSemGest);

        Button bFecha			= (Button) findViewById(R.id.bFecha);
        Button bCalculo 		= (Button) findViewById(R.id.idCalcular);
        
        setTextoProg(tvProgGesta.getText().toString());
        bProgreso = (ProgressBar) findViewById(R.id.progresoParto);

        // Cogemos la fecha actual (?):
        final Calendar c = Calendar.getInstance();
       	set_mYear(c.get(Calendar.YEAR));
       	set_mMonth(c.get(Calendar.MONTH));
       	set_mDay(c.get(Calendar.DAY_OF_MONTH));
       	// Mostramos la fecha:
       	updateDisplay();
       	
        bCalculo.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		calendario = Calendar.getInstance();
        		calendario.set(get_mYear(), get_mMonth(), get_mDay());
        		
        		tvFechaProbableParto.setText((calculosNaegele.CalculaDiasNaegele(calculosNaegele.EsBisiesto(get_mYear()), 
        				get_mDay(), get_mMonth(), get_mYear())));
        		tvSemGestacion.setText(calculosNaegele.CalculaSemanasGestacion(calendario.getTime()));
        		tvProgGesta.setText(getTextoProg() +" "+ (calculosNaegele.toString(calculosNaegele.getProgreso()) +"%"));
        	    bProgreso.setProgress((int) calculosNaegele.getDiferencia());
        	    
        	    /*
        	     * ********* Programa Adicional Recomendado: ***********
        	     * *****************************************************
        	     */
        	    calculosNaegele.ProgramaAdicionalRecomendado(get_mDay(), get_mMonth(), get_mYear());
        	    calculosNaegele.getFechasGestaPAR();
        	    
        	    final ArrayList<TextosPAR> itemsTextosPAR = obtenerItems();
        	    final ItemTextosPARAdapter adaptador = new ItemTextosPARAdapter(NaegeleActivity.this, itemsTextosPAR);
        	    
        	    list.setAdapter(adaptador);
        	    NaegeleCalculos.setListViewHeightBasedOnChildren(list);
        	}
        });
        // Le damos accion a los componentes:
        bFecha.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		showDialog(DATE_DIALOG_ID);
        	}
        });
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapter, View view,
                   int position, long arg) {
        	   AlertDialog.Builder builder = new AlertDialog.Builder(NaegeleActivity.this);
        	   builder.setMessage(calculosNaegele.getSemanasGestaPAR(position) +"\n\n.-" + calculosNaegele.getParRedu(position))
        	           .setTitle(calculosNaegele.getFechasGestaPAR(position))
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
       });
    }
    
	private void updateDisplay() {
		// TODO Auto-generated method stub
        tvMostrar.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                		.append(get_mDay()).append("-")
                        .append(get_mMonth() + 1).append("-")
                        .append(get_mYear()).append(" "));		
	}
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DATE_DIALOG_ID:
	        return new DatePickerDialog(this, mDateSetListener, get_mYear(), get_mMonth(), get_mDay());
	    }
	    return null;
	}
	
/*
 * ******************************************************************************************************************
 * ******************************************************************************************************************	
 */
	 private ArrayList<TextosPAR> obtenerItems() {
		 ArrayList<TextosPAR> items = new ArrayList<TextosPAR>();
 	         
 	     for(int i=0; i < calculosNaegele.getFechasGestaPAR().length; i++){
 	    	 items.add(new TextosPAR(calculosNaegele.getFechasGestaPAR(i), calculosNaegele.getSemanasGestaPAR(i)));
 	     }
   	    return items;	
	 }
}