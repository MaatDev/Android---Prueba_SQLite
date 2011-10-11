package ul.ceids.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper{

	private final String TAG = getClass().getSimpleName();
	
	private final String create_table="create table alumno(codigo integer primary key autoincrement, nombre text);";
	private final String drop_table="drop table if exists alumno";
	
	public MyDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
		Log.v(TAG, "Estoy en el constructor");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		Log.v(TAG, "Estoy en onCreate");
		
		//Se ejecutado este método cuando no existe la base de datos en el dispositivo
		
		db.execSQL(create_table);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		Log.v(TAG, "Estoy en onUpgrade");
		
		//Se ejecuta este método cuando hay un cambio de versión
		
		if(newVersion > oldVersion){
			
			db.execSQL(drop_table);
			onCreate(db);	
			
		}		
				
	}

}
