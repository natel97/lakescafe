package nathanial.lubitz.lakescafe;

/**
 * Created by Nate on 6/4/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "main.db";
    public static final String TABLE1 = "Heading";
    public static final String COL11 = "HID";//INT ----
    public static final String COL12 = "HName";//TEXT
    public static final String COL13 = "HCreated_At";//DATE
    public static final String COL14 = "HUpdated_At";//DATE
    public static final String COL15 = "HURL";//TEXT
    public static final String TABLE2 = "Section";
    public static final String COL21 = "SID";//INT ----
    public static final String COL22 = "SName";//TEXT
    public static final String COL23 = "SDescription";//TEXT
    public static final String COL24 = "SHeading_ID";//INT ----
    public static final String COL25 = "SCreated_At";//DATE
    public static final String COL26 = "SUpdated_At";//DATE
    public static final String COL27 = "SURL";//TEXT
    public static final String TABLE3 = "Item";
    public static final String COL31 = "IID";//INT ----
    public static final String COL32 = "IName";//TEXT
    public static final String COL33 = "IDescription";//TEXT
    public static final String COL34 = "IPrice";//DOUBLE
    public static final String COL35 = "ISection_ID";//INT ----
    public static final String COL36 = "ICreated_At";//DATE
    public static final String COL37 = "IUpdated_At";//DATE
    public static final String COL38 = "IURL";//TEXT
    public static final String TABLE4 = "SubItem";
    public static final String COL41 = "SIID";//INT ----
    public static final String COL42 = "SIName";//TEXT
    public static final String COL43 = "SIDescription";//TEXT
    public static final String COL44 = "SIPrice";//DOUBLE
    public static final String COL45 = "SIItem_ID";//INT ----
    public static final String COL46 = "SICreated_At";//DATE
    public static final String COL47 = "SIUpdated_At";//DATE
    public static final String COL48 = "SIURL";//TEXT
    public static final String TABLE5 = "SessionVariables";
    public static final String COL51 = "SVID";
    public static final String COL52 = "SVName";
    public static final String COL53 = "SVValue";
    public static final String TABLE6 = "Tables";
    public static final String COL61 = "TID";
    public static final String TABLE7 = "Orders";
    public static final String COL71 = "OrderID";
    public static final String COL72 = "ItemID";
    public static final String COL73 = "TableID";


    public DatabaseHelper(Context cont){
        super(cont, DATABASE_NAME, null,1);
    }
//IN T DA DA TE         IN T T IN DA DA T       IN T T DB IN DA DA T    IN  TE TE DB IN DA DA TE
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE1 + " (" + COL11 + " INTEGER PRIMARY KEY, "+COL12 + " TEXT, " +COL13 + " STRING, " + COL14 + " STRING, " +COL15 + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE2 + " (" + COL21 + " INTEGER PRIMARY KEY, "+COL22 + " TEXT, "+COL23 + " TEXT, " +COL24 + " INTEGER, " +COL25 + " STRING, " +COL26 + " STRING, " + COL27 + " TEXT)");
        db.execSQL("CREATE TABLE " + TABLE3 + " (" + COL31 + " INTEGER PRIMARY KEY, " +COL32 + " TEXT, " +COL33 + " TEXT, " +COL34 + " REAL, " +COL35 + " INTEGER, " +COL36 + " STRING, " +COL37 + " STRING, " + COL38 + " TEXT) ");
        db.execSQL("CREATE TABLE " + TABLE4 + " (" + COL41 + " INTEGER PRIMARY KEY, " +COL42 + " TEXT, " +COL43 + " TEXT, " +COL44 + " REAL, " +COL45 + " INTEGER, " +COL46 + " STRING, " +COL47 + " STRING, " + COL48 + " TEXT) ");
        db.execSQL("CREATE TABLE " + TABLE5 + " (" + COL51 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL52 + " TEXT, " + COL53 + " TEXT);");
        db.execSQL("CREATE TABLE " + TABLE6 + " (" + COL61 + " INTEGER PRIMARY KEY AUTOINCREMENT, DATA TEXT)");
        db.execSQL("CREATE TABLE " + TABLE7 + " (" + COL71 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL72 + " INTEGER, " + COL73 + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_NAME);
        onCreate(db);}

    public void addToHeading(Integer a, String b, String c, String d, String e){
        SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE1 + " VALUES (" + a + ", '"+ b + "', '" + c + "', '" + d + "', '" + e + "');");
    }
    public void newTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE6 + "( DATA ) VALUES('Nothing');");
    }
    public Cursor getTableItems(int table){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT " + COL32 + ", " + COL34 + ", " + COL71 + " FROM " + TABLE3 + ", " + TABLE6 + ", " + TABLE7 + " WHERE " + COL31 + " = " + COL72 + " AND " + COL61 + " = " + COL73 + " AND " + COL61 + " = " + String.valueOf(table),null);
    }
    public Cursor getTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE6, null);
    }
    public Integer getIdFromName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor a = db.rawQuery("SELECT * FROM " + TABLE3 + " WHERE " + COL32 + " like '" + name + "'", null);
        Log.i("Nate", name);
        a.moveToFirst();
        return a.getInt(0);
    }
    public void addToSection(Integer a, String b, String c, Integer d, String e, String f, String g){
        SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE2 + " VALUES (" + a + ", '"+ b + "', '" + c + "', " + d + ", '" + e + "', '" + f + "', '" + g + "' );");
    }

    public void addToItem(Integer a, String b, String c, Double d, Integer e, String f, String g, String h){
        SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE3 + " VALUES (" + a + ", '"+ b + "', '" + c + "', " + d + ", " + e + ", '" + f + "', '" + g + "', '" + h +  "' );");
    }

    public void addToSubItem(Integer a, String b, String c, Double d, Integer e, String f, String g, String h){
        SQLiteDatabase db  = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE4 + " VALUES (" + a + ", '"+ b + "', '" + c + "', " + d + ", " + e + ", '" + f + "', '" + g + "', '" + h +  "');");
    }
    public void removeItemFromTable(Integer a){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE7 + " WHERE " + COL71 + " = " + String.valueOf(a));
    }
    public void removeTable(Integer a){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE6 + " WHERE " + COL61 + " = " + String.valueOf(a));
    }

    public void buttonFix(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL53, "0");
        db.update(TABLE5, cv, COL52 + " = 'CurrentLoc'", new String[]{});
    }
    public void drop() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE1);
        db.execSQL("delete from " + TABLE2);
        db.execSQL("delete from " + TABLE3);
        db.execSQL("delete from " + TABLE4);
        db.execSQL("delete from " + TABLE5);
        db.execSQL("INSERT INTO " + TABLE5 + "(" + COL52 + ", " + COL53 + ") VALUES('Load', '0')");
        db.execSQL("INSERT INTO " + TABLE5 + "(" + COL52 + ", " + COL53 + ") VALUES('CurrentLoc', '0')");
        db.execSQL("INSERT INTO " + TABLE5 + "(" + COL52 + ", " + COL53 + ") VALUES('CurrentTable', '1')");
        try {
            db.execSQL("INSERT INTO " + TABLE5 + "(" + COL52 + ", " + COL53 + ") VALUES('CurrentSection', " + String.valueOf(getCurrentTable()) + ")");
        }
        catch (Exception e) {
            newTable();
            Cursor a = getTables();
            setCurrentTable(a.getInt(0));
            db.execSQL("INSERT INTO " + TABLE5 + "(" + COL52 + ", " + COL53 + ") VALUES('CurrentSection', " + String.valueOf(getCurrentTable()) + ")");
        }
    }


    public Cursor getHeadings(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE1, null);
        return res;
    }
    public void addLoad(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor g = db.rawQuery("SELECT " + COL53 + " from " + TABLE5 + " WHERE " + COL52 + " = 'Load';", null);
        g.moveToFirst();
        String s = g.getString(0);
        Integer i = Integer.parseInt(s);
        String d = String.valueOf(i + 1);
        ContentValues cv = new ContentValues();
        cv.put(COL53, d);
        db.update(TABLE5, cv, COL52 + " = 'Load'", new String[]{});

    }

    public Boolean checkIfLoad(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor g = db.rawQuery("SELECT " + COL53 + " from " + TABLE5 + " WHERE " + COL52 + " = 'Load';", null);
        g.moveToFirst();
        return (g.getInt(0) == 4);
    }
    public Integer perc(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor g = db.rawQuery("SELECT " + COL53 + " from " + TABLE5 + " WHERE " + COL52 + " = 'Load';", null);
        g.moveToFirst();
        return g.getInt(0) * 25;
    }
    public Cursor loadSections(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor g = db.rawQuery("Select * from " + TABLE2, null);
        return g;
    }
    public void clearDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE6);
        db.execSQL("delete from " + TABLE7);
        newTable();
        Cursor a = getTables();
        a.moveToFirst();
        setCurrentTable(a.getInt(0));

    }
    public Cursor getItems(int a){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor d = db.rawQuery("SELECT * FROM " + TABLE3 + " WHERE " + COL35 + " = " + String.valueOf(a),null);
        return d;
    }
    public Integer swapViews(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor g = db.rawQuery("SELECT " + COL53 + " from " + TABLE5 + " WHERE " + COL52 + " = 'CurrentLoc';", null);
        g.moveToFirst();
        Integer a = g.getInt(0);
        a += 1;
        if (a == 2) a = 0;
        ContentValues cv = new ContentValues();
        cv.put(COL53, a);
        db.update(TABLE5, cv, COL52 + " = 'CurrentLoc'", new String[]{});
        return a;
    }
    public Integer getView() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor g = db.rawQuery("SELECT " + COL53 + " from " + TABLE5 + " WHERE " + COL52 + " = 'CurrentLoc';", null);
        g.moveToFirst();
        return g.getInt(0);
    }
    public Integer getCurrentTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor g = db.rawQuery("SELECT " + COL53 + " from " + TABLE5 + " WHERE " + COL52 + " = 'CurrentTable';", null);
        g.moveToFirst();
        return g.getInt(0);
    }
    public void setCurrentTable(Integer a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL53, a);
        db.update(TABLE5, cv, COL52 + " = 'CurrentTable'", new String[]{});
    }
    public Integer getCurrentSection() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor g = db.rawQuery("SELECT " + COL53 + " from " + TABLE5 + " WHERE " + COL52 + " = 'CurrentSection';", null);
        g.moveToFirst();
        return g.getInt(0);
    }
    public void setCurrentSection(Integer a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL53, a);
        db.update(TABLE5, cv, COL52 + " = 'CurrentSection'", new String[]{});
    }
    public void addItemToTable(Integer a){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL72, a);
        cv.put(COL73, getCurrentTable());
        db.insert(TABLE7,null, cv);
    }

}