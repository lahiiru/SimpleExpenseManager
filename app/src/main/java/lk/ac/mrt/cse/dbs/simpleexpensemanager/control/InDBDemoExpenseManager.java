package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InDBAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InDBTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DBHelper;

/**
 * Created by Lahiru on 06/12/2015.:)
 */
public class InDBDemoExpenseManager extends ExpenseManager {
    private final Context context;

    public InDBDemoExpenseManager(Context context){
        this.context=context;
        setup();
    }
    @Override
    public void setup() {
        /*** Begin generating dummy data for In-DB implementation ***/
        DBHelper dbhelper=new DBHelper(this.context);

        TransactionDAO InDBTransactionDAO = new InDBTransactionDAO(dbhelper);
        setTransactionsDAO(InDBTransactionDAO);

        AccountDAO InDBAccountDAO = new InDBAccountDAO(dbhelper);
        setAccountsDAO(InDBAccountDAO);

        // dummy data
        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);

        /*** End ***/
    }
}
