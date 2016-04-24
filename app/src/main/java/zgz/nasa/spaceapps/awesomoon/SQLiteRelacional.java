package zgz.nasa.spaceapps.awesomoon;

/**
 * Created by Guillermo on 9/03/16.
 */
public class SQLiteRelacional {

    public static final String DATABASE_NAME = "awesomoon";
    public static final String DATABASE_TABLE_INFORMACION = "INFORMACION";
    public static final String DATABASE_TABLE_MULTIMEDIA = "MULTIMEDIA";
    public static final String DATABASE_TABLE_PREGUNTA = "PREGUNTA";
    public static final String DATABASE_TABLE_PARTIDA = "PARTIDA";
    public static final String DATABASE_TABLE_CONTIENE = "CONTIENE";
    public static final String DATABASE_TABLE_MUESTRA = "MUESTRA";
    public static final String KEY_IDINFO = "ID";
    public static final String KEY_IDMULTI = "ID";
    public static final String KEY_IDPREG = "ID";
    public static final String KEY_IDPART = "ID";
    public static final String Titulo="Titulo"; //TITULO DE INFO.
    public static final String Cuerpo="Cuerpo"; //CUERPO DE INFO.
    public static final String Tipo ="Tipo";    //TIPO MULTIMEDIA
    public static final String INFOURI="INFOURI";   //Multimedia de la info.
    public static final String URI="URI";
    public static final String Duracion ="Duracion";
    public static final String Texto = "Texto";
    public static final String TextoMulti = "TextoMulti"; //Texto multimedia
    public static final String Correcta = "Correcta";
    public static final String Error1 = "Error1";
    public static final String Error2 = "Error2";
    public static final String Error3 = "Error3";
    public static final String Error4 = "Error4";
    public static final String Error5 = "Error5";
    public static final String Puntuacion="Puntuacion";
    public static final String Fecha="Fecha";
    public static final String Juego="Juego";
    public static final String Numero="Numero";


    public static final int DATABASE_VERSION = 1;


    /**
     * Database creation sql statement
     */
    protected static final String DATABASE_CREATE_INFORMACION=
            "create table " + DATABASE_TABLE_INFORMACION + " (" + KEY_IDINFO + " INTEGER PRIMARY KEY , "+ Titulo +" TEXT NOT NULL , "+
                    INFOURI+ " TEXT , " + Cuerpo + " TEXT NOT NULL);";
    protected static final String DATABASE_CREATE_MULTIMEDIA =
            "create table " + DATABASE_TABLE_MULTIMEDIA + " ( "+ KEY_IDMULTI + " INTEGER, "+ Tipo + " TEXT NOT NULL," +
             URI +" TEXT PRIMARY KEY , " +TextoMulti + " TEXT , " + Duracion + " INTEGER NOT NULL );";
    protected static final String DATABASE_CREATE_PREGUNTA =
            "create table " + DATABASE_TABLE_PREGUNTA + " ("+KEY_IDPREG + " INTEGER PRIMARY KEY, " + Texto + " TEXT NOT NULL," +
            Correcta + " TEXT NOT NULL, " + Error1 + " TEXT NOT NULL, " + Error2 + " TEXT NOT NULL, " + Error3 + " TEXT NOT NULL," +
            Error4 + " TEXT, "  + Error5 + " TEXT );";
    protected static final String DATABASE_CREATE_PARTIDA =
            "create table "+ DATABASE_TABLE_PARTIDA + " (" + KEY_IDPART + " INTEGER PRIMARY KEY, " + Puntuacion + " INTEGER NOT NULL, " +
            Fecha + " DATE NOT NULL, " + Juego + " TEXT NOT NULL, " + Numero + " INTEGER NOT NULL );";
    protected static final String DATABASE_CREATE_CONTIENE =
            "create table "+ DATABASE_TABLE_CONTIENE + " (PREGUNTA_ID INTEGER PRIMARY KEY, MULTIMEDIA_ID INTEGER NOT NULL, " +
            "FOREIGN KEY (MULTIMEDIA_ID) REFERENCES MULTIMEDIA(ID), FOREIGN KEY (PREGUNTA_ID) REFERENCES PREGUNTA(ID));";
    protected static final String DATABASE_CREATE_MUESTRA =
            "create table "+ DATABASE_TABLE_MUESTRA + " (INFORMACION_ID INTEGER, MULTIMEDIA_ID INTEGER, " +
            "PRIMARY KEY (INFORMACION_ID,MULTIMEDIA_ID), FOREIGN KEY (INFORMACION_ID) REFERENCES INFORMACION(ID), " +
            "FOREIGN KEY (MULTIMEDIA_ID) REFERENCES MULTIMEDIA(ID)  );";
}

