package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;

import java.util.ArrayList;

/**
 * Created by Sachi on 12/5/2015.
 */
public class AccountDAO_impl implements AccountDAO {
    private DBHelper dbHelper;
    private String[] ACCOUNT_TABLE_COLUMNS = { DBHelper.ACCOUNT_NO, DBHelper.ACCOUNT_HOLDER_NAME, DBHelper.BANK_NAME, DBHelper.BALANCE };
    private SQLiteDatabase database;
    public AccountDAO_impl(DBHelper dbWrapper) {
        dbHelper = dbWrapper;
        //   this.accounts = new HashMap<>();
    }

    public void open() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public void close() {
        database.close();
    }
    @Override
    public List getAccountNumbersList() {
        List<String> accountNumbers = new ArrayList<String>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + dbHelper.TABLE_ACCOUNT_DETAILS + "", null);


        while (cursor.moveToNext()) {
            //Account account = new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getDouble(3));
            Account account = parseAccount(cursor);
            accountNumbers.add(account.getAccountNo());
            //cursor.moveToNext();
        }

        cursor.close();
        return accountNumbers;
    }
    private Account parseAccount(Cursor cursor) {
        Account account = new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getDouble(3));
        return account;
    }

    public List getAccountsList() {
        List<Account> accountList = new ArrayList<Account>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + dbHelper.TABLE_ACCOUNT_DETAILS + "", null);

        while (cursor.moveToNext()) {
            //Account account = new Account(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getDouble(3));
            Account account = parseAccount(cursor);
            accountList.add(account);
            //cursor.moveToNext();
        }

        cursor.close();
        return accountList;
    }

  @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        List<Account> myAccountList = this.getAccountsList();
        for(int i=0;i<myAccountList.size();i++) {
            if(myAccountList.get(i).getAccountNo().equals(accountNo))
                return myAccountList.get(i);
        }
        String msg = "Account " + accountNo + " is invalid.";
        throw new InvalidAccountException(msg);
    }

    @Override

    public void addAccount(Account account) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.ACCOUNT_NO,account.getAccountNo());
        values.put(DBHelper.ACCOUNT_HOLDER_NAME,account.getAccountHolderName());
        values.put(DBHelper.BANK_NAME,account.getBankName());
        values.put(DBHelper.BALANCE,account.getBalance());

        database.insert(dbHelper.TABLE_ACCOUNT_DETAILS, null, values);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        String id = accountNo;
        database = dbHelper.getWritableDatabase();
        System.out.println("COMMENT Account deleted with id: " + id);
        database.delete(dbHelper.TABLE_ACCOUNT_DETAILS, dbHelper.ACCOUNT_NO+ " = " + id, null);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        database = dbHelper.getWritableDatabase();
        Double amountValue=this.getAccount(accountNo).getBalance();
        ContentValues cv = new ContentValues();
        cv.put("ACCOUNT_NO",accountNo); //These Fields should be your String values of actual column names
        switch (expenseType) {
            case EXPENSE:
                amount = amountValue-amount;

                break;
            case INCOME:
                amount = amountValue+amount;
                break;
        }
        cv.put("AMOUNT",amount);
        database.update(dbHelper.TABLE_ACCOUNT_DETAILS, cv, "_id "+"="+1, null);
    }
}
