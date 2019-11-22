package br.edu.ifsp.agendasqlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "agenda.db";
    static final String TABLE_NAME ="contatos";

    static final String KEY_ID = "id";
    static final String KEY_NOME = "nome";
    static final String KEY_FONE = "fone";
    static final String KEY_FONE_2 = "fone2";
    static final String KEY_EMAIL = "email";
    static final String KEY_FAVORITO = "favorito";
    static final String KEY_ANIVERSARIO = "aniversario";

    private static final int DATABASE_VERSION = 3;

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                                               + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                               + KEY_NOME + " TEXT, "
                                               + KEY_FONE + " TEXT, "
                                               + KEY_FONE_2 + " TEXT, "
                                               + KEY_EMAIL + " TEXT, "
                                               + KEY_FAVORITO + " INTEGER DEFAULT 0, "
                                               + KEY_ANIVERSARIO + " TEXT); ";


    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                recuperaDados(db);
            case 2:
                recuperaDados(db);
            case 3:
                recuperaDados(db);
        }
    }

    public void recuperaDados(SQLiteDatabase db){

        //Cria novamente a tabela
        String sqlite2 = "CREATE TABLE " + TABLE_NAME +"_temp" + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NOME + " TEXT, "
                + KEY_FONE + " TEXT, "
                + KEY_FONE_2 + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_FAVORITO + " INTEGER DEFAULT 0, "
                + KEY_ANIVERSARIO + " TEXT); ";
        db.execSQL(sqlite2);

        //Inseri as informações na tabela temp
        String sqlite3 = "INSERT INTO "+TABLE_NAME+"_temp ("+KEY_NOME+","+KEY_FONE+","+KEY_EMAIL+","+KEY_FAVORITO+","+KEY_FONE_2+","+KEY_ANIVERSARIO+") " +
                "SELECT "+KEY_NOME+","+KEY_FONE+","+KEY_EMAIL+","+KEY_FAVORITO+","+KEY_FONE_2+","+KEY_ANIVERSARIO+" FROM "+TABLE_NAME;
        db.execSQL(sqlite3);

        //Limpa a tabela para recebir novos registros
        db.execSQL("DELETE FROM "+TABLE_NAME);

        // Recupera as informações da tabela temp
        String sqlite4 = "INSERT INTO "+TABLE_NAME+" ("+KEY_NOME+","+KEY_FONE+","+KEY_EMAIL+","+KEY_FAVORITO+","+KEY_FONE_2+","+KEY_ANIVERSARIO+") " +
                "SELECT "+KEY_NOME+","+KEY_FONE+","+KEY_EMAIL+","+KEY_FAVORITO+","+KEY_FONE_2+","+KEY_ANIVERSARIO+" FROM "+TABLE_NAME+"_temp";
        db.execSQL(sqlite4);

        //Apaga a tabela temporaria
        String sqlite5 = "DROP TABLE "+TABLE_NAME+"_temp;";
        db.execSQL(sqlite5);


    }
}


