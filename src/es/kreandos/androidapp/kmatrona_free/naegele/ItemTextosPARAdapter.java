package es.kreandos.androidapp.kmatrona_free.naegele;

import java.util.ArrayList;
import es.kreandos.androidapp.kmatrona_free.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemTextosPARAdapter extends BaseAdapter {
		  protected Activity activity;
		  protected ArrayList<TextosPAR> items;
		         
		  public ItemTextosPARAdapter(Activity activity, ArrayList<TextosPAR> items) {
		    this.activity = activity;
		    this.items = items;
		  }
		 
		  @Override
		  public int getCount() {
		    return items.size();
		  }
		 
		  @Override
		  public Object getItem(int position) {
		    return items.get(position);
		  }
		 
		  //@Override
		  //public long getItemId(int position) {
		  //  return items.get(position);//.getId();
		  //}
		 
		  @Override
		  public View getView(int position, View contentView, ViewGroup parent) {
		    View vi=contentView;
		         
		    if(contentView == null) {
		      LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		      vi = inflater.inflate(R.layout.lista_progr_gest, null);
		    }
		             
		    TextosPAR item = items.get(position);
		         
		    TextView nombre = (TextView) vi.findViewById(R.id.LblTitulo);
		    nombre.setText(item.getTitulo());
		         
		    TextView tipo = (TextView) vi.findViewById(R.id.LblSubTitulo);
		    tipo.setText(item.getSubtitulo());
		 
		    return vi;
		  }

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
}
