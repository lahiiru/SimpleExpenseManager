package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DBHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Lahiru on 06/12/2015.:)
 */
public class InDBTransactionDAO implements TransactionDAO {
    private final DBHelper dbhelper;

    public InDBTransactionDAO(DBHelper db){
        this.dbhelper=db;
    }
    @Override
    public void logTransaction(String date, String accountNo, String expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        dbhelper.insertTransaction(transaction);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        return dbhelper.getAllTransactions();
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        return dbhelper.getAllTransactionsLimit(limit);
    }
}
