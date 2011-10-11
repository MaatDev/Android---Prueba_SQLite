package ul.ceids.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AlumnoDAO {
	
	/*
	 *Esta clase es manejadora para manipular base de datos SQLite
	 * 
	 * 
	 */
	
	//Nombre para mostrar el el LogCat
	private final String TAG = getClass().getSimpleName();
		
	//Para ver la consola de android ir a Windows/Show View/Log Cat o en Others...
	
	private MyDBHelper myHelper;
	private SQLiteDatabase myManager;
	
	//Nombre de la base de datos
	private final String db_name="UL";
	
	//Versión para la base de datos
	private final int db_version=1;	
	
	//Nombre de columna
	private final String column_cod = "codigo";	
	private final String column_nombre="nombre";
	
	//Nombre de la tabla
	
	private final String table_alumno="alumno";

	
	public AlumnoDAO(Context context){
		
		Log.v(TAG, "Estoy en el constructor");
		
		//Inicializar el helpder
		this.myHelper = new MyDBHelper(context, db_name, null, db_version);
		
		//Obtener la base de datos
		this.myManager = this.myHelper.getWritableDatabase();
		
	}
	
	//Método para cerrar la conexión a base de datos
	
	public void close(){
		
		myManager.close();
		myHelper.close();
		
	}	
	
	//Método para isertar datos
	
	public long insert(AlumnoDTO alumno){

		Log.v(TAG, "Estoy en insert");
		
		//Contenedor de valores a manipular en la base de datos
		
		ContentValues cv = new ContentValues();
		
		//Si el código del alumno es 0 o el EditText fue vacío, usará el autoincrement por defecto
				
		if(alumno.getCodigo() != 0){
			
			cv.put(column_cod, alumno.getCodigo());
			
		}else{
			
			//Usar autoincrement
			
		}
		
		cv.put(column_nombre, alumno.getNombre());
		
		//Devuelve el id de la fila insertada
		
		return this.myManager.insert(table_alumno, null, cv); 
					
		//otra forma de ejecutar el query
		
//		return this.myManager.execSQL("insert into table alumno values("+"column_cod"+","+"column_nombre)");
		
	}
	
	//Método para el cursor
	
	public ArrayList<AlumnoDTO> select(int cod){
		
		Log.v(TAG, "Estoy en select");
		
		//Devolver todos los alumnos si el código es 0
		
		//Listas de nombres de columnas
		
		String [] s = {column_cod, column_nombre};		
	
		ArrayList<AlumnoDTO> alumnos = new ArrayList<AlumnoDTO>();
		
		AlumnoDTO alumno;
		

		Cursor cursor;	
	
			
		if(cod == 0){
				
			//Devuelve todos los alumnos
			
			cursor = this.myManager.query(table_alumno, s, null, null, null, null, null);
//			cursor = this.myManager.query(table_alumno, null, null, null, null, null, null); es como si fuera: select * from alumno;	
			
		}else{
			
			//Devuelve el código del alumno específico
			
			cursor = this.myManager.query(table_alumno, s, column_cod+"=?", new String[]{String.valueOf(cod)}, null, null, null);
			
		}
		
		//Los índices de las columnas de la tabla alumno
			
			int indexCodigo = cursor.getColumnIndex(column_cod);
			int indexNombre = cursor.getColumnIndex(column_nombre);

		while(cursor.moveToNext()){
				
			//Inicializar al alumno con el constructor
			
			alumno = new AlumnoDTO(cursor.getInt(indexCodigo),cursor.getString(indexNombre));
			
			alumnos.add(alumno);
			
		}
		
		cursor.close();

						
		return alumnos;
	}
	
	//Método para eliminar
	
	public int delete(AlumnoDTO alumno){
		
		Log.v(TAG, "Estoy en delete");
		
		//Eliminación por código
		
		if(alumno.getCodigo() != 0){
			
			//Deveulve 0 si no elimino nada
						
			return this.myManager.delete(table_alumno, column_cod+"="+alumno.getCodigo(),null);
			
		}else{
			
			//Deveulve 0 si no elimino nada
			
			String [] a = {alumno.getNombre()};
			
			Log.v(TAG, "estoy en nombre");
			
			return this.myManager.delete(table_alumno, column_nombre+"=?", a);
			
		}
		
	}
	
	public int update(AlumnoDTO alumno){
		
		Log.v(TAG, "Estoy en upgrade");
		
		//Contenedor de valores a manipular en la base de datos
		
		ContentValues cv = new ContentValues();
		
		cv.put(column_nombre, alumno.getNombre());
		
		String [] s = {String.valueOf(alumno.getCodigo())};
				
		//Devuelve 0 si no actualizo datos
		
		return this.myManager.update(table_alumno, cv, column_cod+"=?", s);		
		
	}
	
}
