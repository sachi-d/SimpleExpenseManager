package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

/**
 * Created by Sachi on 12/5/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper  {

    public static final String DATABASE_NAME = "130094R.db";

    public static final String TABLE_EXPENSES = " Expenses ";
    public static final String TABLE_ACCOUNT_DETAILS = " Account_Details ";
    public static final String TABLE_TRANSACTION_DETAILS = " Transaction_Details ";
    public static final String ACCOUNT_NO  = " Account_No ";
    public static final String ACCOUNT_HOLDER_NAME = " AccountHolderName ";
    public static final String BANK_NAME= " Bank_Name ";
    public static final String BALANCE= " Balance ";
    public static final String TRANSACTION_DATE= " Transaction_Date ";
    public static final String AMOUNT= " Amount ";
    public static final String EXPENSE_TYPE= " Expense_Type ";

    private HashMap hp;
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto?generated method stub
        db.execSQL("create table " + TABLE_ACCOUNT_DETAILS + "("
                + ACCOUNT_NO + "text primary key, "
                +ACCOUNT_HOLDER_NAME  + " text not null, "
                + BANK_NAME+ " text not null, "
                +BALANCE+" double not null);");
        db.execSQL("create table " + TABLE_TRANSACTION_DETAILS + "( " +
                " tran_id int AUTO_INCREMENT primary key,"
                + ACCOUNT_NO + "text, "
                +TRANSACTION_DATE  + " long not null , "
                + AMOUNT+ " text not null, "
                +EXPENSE_TYPE+" text not null );");     //,  FOREIGN KEY(ACCOUNT_NO) REFERENCES ACCOUNT_DETAILS(ACCOUNT_NO)
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto?generated method stub
        db.execSQL("DROP TABLE IF EXISTS Expenses");
        onCreate(db);
    }
    public boolean insertContact (String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts", null, contentValues);
        return true;
    }
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }
    /*public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }*/
    public boolean updateContact (Integer id, String name, String phone, String email, String street)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
   /* public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();
//hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from contacts", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }*/

}
