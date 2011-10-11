package ul.ceids.db;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Prueba_BaseActivity extends Activity {

	
	//Es la clase que va manejar base de datos
	
	private AlumnoDAO dbManager;
	
	
	//Los views del xml
	
	private TextView tv_text;
	private EditText et_codigo;
	private EditText et_nombre;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.dbManager = new AlumnoDAO(this);
        
        //Instanciar los componentes del xml
        
        this.tv_text = (TextView) findViewById(R.id.tv_text); 
        this.et_codigo = (EditText) findViewById(R.id.et_codigo);
        this.et_nombre = (EditText) findViewById(R.id.et_nombre);
        
    }
    
    //Los métodos para el onClick. Todos deben contener como parámetro View
    
    public void insert(View v){
    	    	  
    	int codigo;
    	
    	//El usuario puede ingresar una letra en vez de número
    	
    	try{
    		codigo = Integer.parseInt(et_codigo.getText().toString());
    	}catch(Exception e){
    		codigo = 0;
    	}
    	
    	String nombre = et_nombre.getText().toString();
    	
    	long success = dbManager.insert(new AlumnoDTO(codigo, nombre));
    	
    	if(success == -1){
    		
    		Toast.makeText(this, "Error, no se ha podido ingresar datos", Toast.LENGTH_LONG).show();
    		
    	}
    	    	
    }
    
    public void select(View v){
    	
    	int codigo;
    	
    	//El usuario puede ingresar una letra en vez de número
    	
    	try{
    		codigo = Integer.parseInt(et_codigo.getText().toString());
    	}catch(Exception e){
    		codigo = 0;
    	}
    	
    	ArrayList<AlumnoDTO> alumnos = dbManager.select(codigo);
    	
    	
    	if(alumnos.size() == 0){
    		
    		//No ha encontrado ningún alumno con el código ingresado
    		
    		Toast.makeText(this, "Error, no se ha podido encontrar datos", Toast.LENGTH_LONG).show();
    		
    		return;
    		
    	}
    	
    	String s = "";
    	
    	for(AlumnoDTO a: alumnos){
    		
    		s += "Código: "+a.getCodigo()+" / Nombre: "+a.getNombre()+'\n';
    	}
    	    	
    	tv_text.setText(s);
    	
    }
    
    public void delete(View v){
    	
    	int codigo;
    	
    	//El usuario puede ingresar una letra en vez de número
    	
    	try{
    		codigo = Integer.parseInt(et_codigo.getText().toString());
    	}catch(Exception e){
    		codigo = 0;
    	}
    	
    	String nombre = et_nombre.getText().toString();
    	
    	AlumnoDTO alumno = new AlumnoDTO(codigo, nombre);
    	
    	int success = this.dbManager.delete(alumno);
    	
    	
    	if(success == 0){
    		
    		Toast.makeText(this, "Error, no se ha podido eliminar datos", Toast.LENGTH_LONG).show();
    		
    	}
    	
    	
    }
    
    public void update(View v){
    	
    	int codigo;
    	
    	//El usuario puede ingresar una letra en vez de número
    	
    	try{
    		codigo = Integer.parseInt(et_codigo.getText().toString());
    	}catch(Exception e){
    		codigo = 0;
    	}
    	
    	String nombre = et_nombre.getText().toString();
    	
    	AlumnoDTO alumno = new AlumnoDTO(codigo, nombre);
    	
    	int success = this.dbManager.update(alumno);
    	
    	
    	if(success == 0){
    		
    		Toast.makeText(this, "Error, no se ha podido actualizar datos", Toast.LENGTH_LONG).show();
    		
    	}    	
    	
    }
    
    @Override
    protected void onPause() {
    	//Cerrar la base de datos
    	this.dbManager.close();
    	super.onPause();
    }
    
    @Override
    protected void onDestroy() {
    	//Cerrar la base de datos
    	this.dbManager.close();
    	super.onDestroy();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	//Abrir la base de datos
    	this.dbManager = new AlumnoDAO(this);
    }
    
}