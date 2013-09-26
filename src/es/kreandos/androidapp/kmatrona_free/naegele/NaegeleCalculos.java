package es.kreandos.androidapp.kmatrona_free.naegele;

import java.util.Calendar;
import java.util.Date;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NaegeleCalculos {
/*----- Variables: ----------------------------------------------------------------------------------------------------	
 */
	private int aano;
	
	private int diaPAR;
	private int mesPAR;
	private int yearPAR;
	
	private String fechaProbableParto = "";
	private String semanasGestacion = "";
    private String semanGestPAR = "";
	private long diferencia;
	final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al dia

	private String[] fechasGestaPAR;
	private String[] semanasGestaPAR;
    private String[] programaRecomendado = {
    		"Consulta matrona. Abrir D.S.E. (Documento Salud de la Embarazada).",//-//0
    		"Analitica. Cribado Riesgo, Cromosomopatias, etc...",//-----------------//1
    		"1ª Ecografía (Hospital).",//-------------------------------------------//2
    		"Urocultivo.",//--------------------------------------------------------//3
    		"Control Embarazo (C.S.).",//-------------------------------------------//4
    		"2ª Ecografía (Hospital).",//-------------------------------------------//5
    		"Control Embarazo (O'Sullivan)(C.S.).",//-------------------------------//6
    		"3ª Ecografía (Hospital).",//-------------------------------------------//7
    		"Monitores.",//---------------------------------------------------------//8
    		"Inducción del Parto." //-----------------------------------------------//9
    };
	private String[] parRedu;// = null;
/*----- Fin Variables -------------------------------------------------------------------------------------------------	
 */
//=====================================================================================================================
/*----- Setters: ------------------------------------------------------------------------------------------------------
 */
	public void setAano(int aano) {
		this.aano = aano;
	}
	public void setDiferencia(long dif){
		this.diferencia = dif;
	}
	public String toString(int i) {
		String txt = ""+ i;
		return txt;
	}
	public void setDiaPAR(int diaPAR) {
		this.diaPAR = diaPAR;
	}
	public void setYearPAR(int yearPAR) {
		this.yearPAR = yearPAR;
	}
	public void setMesPAR(int mesPAR) {
		this.mesPAR = mesPAR;
	}
	public void setFechasGestaPAR(String[] fechasGestaPAR) {
		this.fechasGestaPAR = fechasGestaPAR;
	}
	public void setSemanasGestaPAR(String[] semanasGestaPAR) {
		this.semanasGestaPAR = semanasGestaPAR;
	}
	public void setParRedu(String[] parRedu) {
		this.parRedu = parRedu;
	}
/*----- Fin Setters ---------------------------------------------------------------------------------------------------
 */
//=====================================================================================================================

/*----- Getters: ------------------------------------------------------------------------------------------------------
 */
	public int getAano() {
		return aano;
	}
	public long getDiferencia() {
		return diferencia;
	}
	public int getProgreso(){
		int i;
		if (getDiferencia() < 300) {
			i = (int) getDiferencia();
		} else {
			i = 280;
		}
		int p = ((i*100)/280);
		return p; 
	}
	public int getDiaPAR() {
		return diaPAR;
	}
	public int getYearPAR() {
		return yearPAR;
	}
	public int getMesPAR() {
		return mesPAR;
	}
	public String[] getFechasGestaPAR() {
		return fechasGestaPAR;
	}
	public String getFechasGestaPAR(int pos) {
		return fechasGestaPAR[pos];
	}
	public String[] getSemanasGestaPAR() {
		return semanasGestaPAR;
	}
	public String getSemanasGestaPAR(int pos) {
		return semanasGestaPAR[pos];
	}
	public String[] getProgramaRecomendado() {
    	return programaRecomendado;
    }
    public String getProgramaRecomendadoPos(int pos){
    	return programaRecomendado[pos];
    }
	public String[] getParRedu() {
		return parRedu;
	}
	public String getParRedu(int pos){
		return parRedu[pos];
	}
/*----- Fin Getters ---------------------------------------------------------------------------------------------------
 */
//=====================================================================================================================
	
	/*
	 * Desde aqui empieza las funciones de calculo de fecha probable parto:
	 */
		// Establece el nombre del dia.
		public String ObtenerNombreDiaSemana(int diaSemana) {
		    String nombDiaSem = "";
		    switch(diaSemana) {
		        case 1: nombDiaSem = "Domingo"; break;
		        case 2: nombDiaSem = "Lunes"; break;
		        case 3: nombDiaSem = "Martes"; break;
		        case 4: nombDiaSem = "Miercoles"; break;
		        case 5: nombDiaSem = "Jueves"; break;
		        case 6: nombDiaSem = "Viernes"; break;
		        case 7: nombDiaSem = "Sabado"; break;
		        default:
		            nombDiaSem = "La cagastes!"; break;
		    }
		    return nombDiaSem;
		}

	//Establece el nombre del mes.
	public String ObtenerNombreMes(int mes) {
	    String nomMes = "";
	    switch (mes) {
	        case 0: nomMes = "Enero"; break;
	        case 1: nomMes = "Febrero"; break;
	        case 2: nomMes = "Marzo"; break;
	        case 3: nomMes = "Abril"; break;
	        case 4: nomMes = "Mayo"; break;
	        case 5: nomMes = "Junio"; break;
	        case 6: nomMes = "Julio"; break;
	        case 7: nomMes = "Agosto"; break;
	        case 8: nomMes = "Septiembre"; break;
	        case 9: nomMes = "Octubre"; break;
	        case 10: nomMes = "Noviembre"; break;
	        case 11: nomMes = "Diciembre"; break;
	        default: nomMes = "LA CAGASTES!"; break;
	    }
	    return nomMes;
	}

	// Establece si el año es bisiesto o no.
	public boolean EsBisiesto(int anio) {
	    boolean bisiesto = false;
	    if ((( anio % 4 == 0 ) && ( anio % 100 != 0 )) || ( anio % 400 == 0 )) {
	        bisiesto = true;
	    }
	    return bisiesto;
	}

	// Establece cuantos dias tiene cada mes teniendo en cuenta si el año es bisiesto o no.
	public int DiasMes (boolean anioBisiesto, int mes) {
	    int diasMes = 28;
	    switch (mes) {
	        case 0: diasMes = 31; break;
	        case 1:
	        	if(anioBisiesto == true) {
	        		diasMes = 29;}
	        	break;
	        case 2: diasMes = 31; break;
	        case 3: diasMes = 30; break;
	        case 4: diasMes = 31; break;
	        case 5: diasMes = 30; break;
	        case 6: diasMes = 31; break;
	        case 7: diasMes = 31; break;
	        case 8: diasMes = 30; break;
	        case 9: diasMes = 31; break;
	        case 10: diasMes = 30; break;
	        case 11: diasMes = 31; break;
	    }
	    return diasMes;
	}

	public int EstableceMes(int mesecillo, int anioA) {
	    //Establece el mes, es decir, al restarle o ir sumando mes por reestructurar los dias,
	    //  podemos salirnos de los numeros que establecen los meses (0-Enero, 1-Febrero,..., 11-Diciembre).
	    //Si es menor que 0 (Enero) restamos un año y establecemos el mes en el que deberia estar:
	    if (mesecillo < 0) {
	        mesecillo = 11 + mesecillo;
	        anioA--;
	    } else {//si es mayor que 11 (Diciembre) sumamos un año y establecemos el mes en el que deberia estar:
	        if (mesecillo > 11) {
	            mesecillo = mesecillo - 11;
	            anioA++;
	        }
	    }
	    setAano(anioA);
	    return mesecillo;
	}

	public String CalculaDiasNaegele (boolean anioBisiesto, int dia, int mes, int anio){
	    int sumaDiaNaegele = 7;     // dia + 7;
	    int restaMesNaegele = 3;    // mes - 3;
	    int sumaAnioNaegele = 1;    // anio + 1;
	    boolean prueba;
	    sumaDiaNaegele = sumaDiaNaegele + dia;
	    restaMesNaegele= mes - restaMesNaegele;
	    sumaAnioNaegele = sumaAnioNaegele + anio;
	    prueba = EsBisiesto(sumaAnioNaegele);
	    //Meses con 31 dias:
	    if ((mes==0) || (mes==2) || (mes==4) || (mes==6) ||
	        (mes==7) || (mes==9) || (mes==11)){
	        if (sumaDiaNaegele > 31){
	            sumaDiaNaegele = sumaDiaNaegele - 31;
	            restaMesNaegele++;
	        }
	    } else { //Meses con 30 dias:
	        if ((mes==3) || (mes==5) || (mes==8) || (mes==10)) {
	            if (sumaDiaNaegele > 30) {
	                sumaDiaNaegele = sumaDiaNaegele - 30;
	                restaMesNaegele++;
	            }
	        } else {//Si el año siguiente es Bisiesto, febrero tendra 29 dias:
	            if (anioBisiesto==true) {
	                if (sumaDiaNaegele > 29) {
	                    sumaDiaNaegele = sumaDiaNaegele - 29;
	                    restaMesNaegele++;
	                }
	            } else {//si no es Bisiesto, febrero tendra 28 dias:
	                if (sumaDiaNaegele > 28) {
	                    sumaDiaNaegele = sumaDiaNaegele - 28;
	                    restaMesNaegele++;
	                }
	            }
	        }
	    }
	    //Si es menor que 0 (Enero) restamos un año y establecemos el mes en el que deberia estar:
	    if (restaMesNaegele < 0) {
	        restaMesNaegele = 12 + restaMesNaegele;
	        sumaAnioNaegele--;
	    } else {//si es mayor que 11 (Diciembre) sumamos un año y establecemos el mes en el que deberia estar:
	        if (restaMesNaegele > 11) {
	            restaMesNaegele = restaMesNaegele - 12;
	            sumaAnioNaegele++;
	        }
	    }
	   //Si estamos en Febrero y es año bisiesto, comprobamos si la fecha resultante es superior a 29 dias que tiene Febrero:
	    if ((restaMesNaegele==1) && (prueba==true)) {
	        if(sumaDiaNaegele > 29){
	            sumaDiaNaegele = sumaDiaNaegele - 29;//Sacamos los dias de mas que hay que seran los dias del mes siguiente.
	            restaMesNaegele++;//Sumamos un mes mas.
	        }
	    } else {//Si no es bisiesto y estamos en Febrero comprobamos que no sea mayor de 28 dias la fecha resultante:
	        if ((restaMesNaegele==1) && (prueba==false)) {
	            if (sumaDiaNaegele > 28) {
	                sumaDiaNaegele = sumaDiaNaegele - 28;//Sacamos los dias de mas que hay que seran los dias del mes siguiente.
	                restaMesNaegele++;//Sumamos un mes mas.
	            }
	        }
	    }
	   fechaProbableParto=(sumaDiaNaegele + " / " + (restaMesNaegele + 1)+ " (" + 
	    		ObtenerNombreMes(restaMesNaegele) + ") / " + sumaAnioNaegele);
	   return fechaProbableParto;
	}

	public String CalculaSemanasGestacion(Date fechaUltRegla) {
		//Calculamos las semanas de gestacion partiendo de la fecha de ultima regla hasta la fecha actual.
		Date hoy = new Date(); //Fecha de hoy
		diferencia = (hoy.getTime() - fechaUltRegla.getTime() )/MILLSECS_PER_DAY;
		int semanas = (int) (diferencia/7);
		int diasGest= (int) (diferencia%7);
		if (diferencia > 300) {
			semanasGestacion = "Error al introducir la fecha. El parto ya se ha producido.";
		} else {
			semanasGestacion=(diferencia + " dias; " + semanas + " sem." + " +" + diasGest + " dias.");
		}
		return semanasGestacion;
	}

	/*****************************************************************************************
	 * ********************** PROGRAMA ADICIONAL RECOMENDADO: ********************************
	 * ***************************************************************************************
	 */
	public void ProgramaAdicionalRecomendado (int diiia, int meees, int yeeear) {
		String[] fechasGestPAR = new String[42];
		String[] semanaGestPAR = new String[42];
		
		Calendar fur = Calendar.getInstance();
		Calendar fsg = Calendar.getInstance();
		
		fur.set(yeeear, meees, diiia);

		setDiaPAR(diiia);
		setMesPAR(meees);
		setYearPAR(yeeear);
		
		int x = 0;
		while (x < 42) {
			fechasGestPAR[x] = CalculaDiasPAR(EsBisiesto(getYearPAR()), getDiaPAR(), getMesPAR(), getYearPAR());
			fsg.set(getYearPAR(), getMesPAR(), getDiaPAR());
			semanaGestPAR[x] = CalculaSemanasGestacionPAR(fsg.getTime(), fur.getTime());
			x++;
		}
		setFechasGestaPAR(fechasGestPAR);
		setSemanasGestaPAR(semanaGestPAR);
		CalculaProgramRecomReducido();
	}
	
	public String CalculaDiasPAR (boolean anioBisiesto, int diad, int mes, int anio){
	    int sumaDias = 7;
	    int diasPAR = diad + sumaDias;
	    int mesPAR  = mes ;
	    int anioPAR = anio;
	    boolean prueba;
	    prueba = EsBisiesto(anioPAR);
	    //Meses con 31 dias:
	    if ((mes==0) || (mes==2) || (mes==4) || (mes==6) ||
	        (mes==7) || (mes==9) || (mes==11)){
	        if (diasPAR > 31){
	            diasPAR = diasPAR - 31;
	            mesPAR++;
	        }
	    } else { //Meses con 30 dias:
	        if ((mes==3) || (mes==5) || (mes==8) || (mes==10)) {
	            if (diasPAR > 30) {
	                diasPAR = diasPAR - 30;
	                mesPAR++;
	            }
	        } else {//Si el año siguiente es Bisiesto, febrero tendra 29 dias:
	            if (anioBisiesto==true) {
	                if (diasPAR > 29) {
	                    diasPAR = diasPAR - 29;
	                    mesPAR++;
	                }
	            } else {//si no es Bisiesto, febrero tendra 28 dias:
	                if (diasPAR > 28) {
	                    diasPAR = diasPAR - 28;
	                    mesPAR++;
	                }
	            }
	        }
	    }
	    //Si es menor que 0 (Enero) restamos un año y establecemos el mes en el que deberia estar:
	    if (mesPAR < 0) {
	        mesPAR = 12 + mesPAR;
	        anioPAR--;
	    } else {//si es mayor que 11 (Diciembre) sumamos un año y establecemos el mes en el que deberia estar:
	        if (mesPAR > 11) {
	            mesPAR = mesPAR - 12;
	            anioPAR++;
	        }
	    }
	   //Si estamos en Febrero y es año bisiesto, comprobamos si la fecha resultante es superior a 29 dias que tiene Febrero:
	    if ((mesPAR==1) && (prueba==true)) {
	        if(diasPAR > 29){
	            diasPAR = diasPAR - 29;//Sacamos los dias de mas que hay que seran los dias del mes siguiente.
	            mesPAR++;//Sumamos un mes mas.
	        }
	    } else {//Si no es bisiesto y estamos en Febrero comprobamos que no sea mayor de 28 dias la fecha resultante:
	        if ((mesPAR==1) && (prueba==false)) {
	            if (diasPAR > 28) {
	                diasPAR = diasPAR - 28;//Sacamos los dias de mas que hay que seran los dias del mes siguiente.
	                mesPAR++;//Sumamos un mes mas.
	            }
	        }
	    }
	   semanGestPAR=(diasPAR + " / " + (mesPAR + 1)+ " (" + 
	    		ObtenerNombreMes(mesPAR) + ") / " + anioPAR);
	   setDiaPAR(diasPAR);
	   setMesPAR(mesPAR);
	   setYearPAR(anioPAR);
	   return semanGestPAR;
	}

	public String CalculaSemanasGestacionPAR(Date fechaSemanGestPAR, Date fur) {
		//Calculamos las semanas de gestacion para el Programa Adicional Recomendado.
		diferencia = (fechaSemanGestPAR.getTime() - fur.getTime()) / MILLSECS_PER_DAY;
		
		int semanas = (int) (diferencia/7);
		int diasGest= (int) (diferencia%7);
		semanGestPAR=(diferencia + " dias; " + semanas + " sem." + " +" + diasGest + " dias.");
		return semanGestPAR;
	}

    public void CalculaProgramRecomReducido(){
    	String[] semPARRedu = new String[16];
    	String[] fecPARRedu = new String[16];
    	parRedu = new String[16];
    	
    	int y = 0;
    	for(int i = 0; i<getFechasGestaPAR().length; i++){
    		switch(i) {
    		case 4:
    			parRedu[y] = programaRecomendado[0];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 5:
    			parRedu[y] = programaRecomendado[0];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 6:
    			parRedu[y] = programaRecomendado[0];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 7:
    			parRedu[y] = programaRecomendado[0];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 9:
    			parRedu[y] = programaRecomendado[1];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 11:
    			parRedu[y] = programaRecomendado[2];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 13:
    			parRedu[y] = programaRecomendado[3];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 14:
    			parRedu[y] = programaRecomendado[3];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 15:
    			parRedu[y] = programaRecomendado[4];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 19:
    			parRedu[y] = programaRecomendado[5];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 23:
    			parRedu[y] = programaRecomendado[6];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 27:
    			parRedu[y] = programaRecomendado[4];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 31:
    			parRedu[y] = programaRecomendado[7];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 38:
    			parRedu[y] = programaRecomendado[8];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 39:
    			parRedu[y] = programaRecomendado[8];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		case 40:
    			parRedu[y] = programaRecomendado[9];
    			fecPARRedu[y] = getFechasGestaPAR(i);
    			semPARRedu[y] = getSemanasGestaPAR(i);
    			y++;
    			break;
    		}
    	}
    	setParRedu(parRedu);
    	setFechasGestaPAR(fecPARRedu);
    	setSemanasGestaPAR(semPARRedu);
    }
	
	 public static void setListViewHeightBasedOnChildren(ListView listView) {
	        ListAdapter listAdapter = listView.getAdapter();
	        if (listAdapter == null) {
	            // pre-condition
	            return;
	        }
	 
	        int totalHeight = 0;
	        for (int i = 0; i < listAdapter.getCount(); i++) {
	            View listItem = listAdapter.getView(i, null, listView);
	            listItem.measure(0, 0);
	            totalHeight += listItem.getMeasuredHeight();
	        }
	 
	        ViewGroup.LayoutParams params = listView.getLayoutParams();
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	        listView.setLayoutParams(params);
	 }   	
	/*****************************************************************************************
	 * ********************** FIN PROGRAMA ADICIONAL RECOMENDADO. ****************************
	 * ***************************************************************************************
	 */

}
