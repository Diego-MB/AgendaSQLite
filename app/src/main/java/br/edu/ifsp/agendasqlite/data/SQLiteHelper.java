package br.edu.ifsp.agendasqlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agenda.db";
    static final String TABLE_NAME ="contatos";

    static final String KEY_ID = "id";
    static final String KEY_NOME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITO = "favorito";

    private static final int DATABASE_VERSION = 2;

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                                               + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                               + KEY_NOME + " TEXT, "
                                               + KEY_FONE + " TEXT, "
                                               + KEY_EMAIL + " TEXT);" ;


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (newVersion){
            case 2:
                recuperaDados(db);
                break;
            case 3:
                Log.w(db.getClass().getName(),
                        "Atualizando database da versão " + oldVersion + " para "
                                + newVersion + ", etapa 3");
                break;
            case 4:
                Log.w(db.getClass().getName(),
                        "Atualizando database da versão " + oldVersion + " para "
                                + newVersion + ", etapa 4");
                break;
        }



    }

    public void recuperaDados(SQLiteDatabase db){
        //Renomeia a tabela atual
        String sqlite = "ALTER TABLE "+TABLE_NAME+" RENAME TO "+TABLE_NAME+"_temp";
        db.execSQL(sqlite);

        //Cria novamente a tabela
        String sqlite2 = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NOME + " TEXT, "
                + KEY_FONE + " TEXT, "
                + KEY_EMAIL + " TEXT,"
                + KEY_FAVORITO + " INTEGER);";
        db.execSQL(sqlite2);

        //Recupera as informações da tabela temp
        String sqlite3 = "INSERT INTO "+TABLE_NAME+" ("+KEY_NOME+","+KEY_FONE+","+KEY_EMAIL+") " +
                "SELECT " +KEY_NOME+", "+KEY_FONE+", "+KEY_EMAIL+" FROM "+TABLE_NAME+"_temp";
        db.execSQL(sqlite3);

        //Apaga a tabela temporaria
        String sqlite4 = "DROP TABLE "+TABLE_NAME+"_temp;";
        db.execSQL(sqlite4);
    }
}
