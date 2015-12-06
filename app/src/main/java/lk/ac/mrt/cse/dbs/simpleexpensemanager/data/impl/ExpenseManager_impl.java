package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DBHelper;

/**
 * Created by Sachi on 12/5/2015.
 */
public class ExpenseManager_impl extends ExpenseManager {
    private Context context;
    public ExpenseManager_impl(Context context) {
        this.context = context;
        try {
            setup();
        } catch (ExpenseManagerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setup() throws ExpenseManagerException {

        DBHelper dbWrapper = new DBHelper(context);

        TransactionDAO transactionDAO = new InMemoryTransactionDAO();
        setTransactionsDAO(transactionDAO);

        AccountDAO accountDAO = new AccountDAO_impl(dbWrapper);
        setAccountsDAO(accountDAO);
        System.out.println("add started");
        accountDAO.addAccount(new Account("11111", "BOC", "Sachi", 9090909));
        accountDAO.addAccount(new Account("22222","Commercial","Dan",2727272));
        System.out.println("add expired");
    }
}
