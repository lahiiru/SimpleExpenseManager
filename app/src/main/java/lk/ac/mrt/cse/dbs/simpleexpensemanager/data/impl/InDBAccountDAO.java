package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.DBHelper;

/**
 * Created by Lahiru on 06/12/2015.:)
 */
public class InDBAccountDAO implements AccountDAO {
    private final DBHelper dbhelper;

    public InDBAccountDAO(DBHelper db) {
        this.dbhelper = db;
    }
    @Override
    public List<String> getAccountNumbersList() {
        return dbhelper.getAllAccountNumbers();
    }

    @Override
    public List<Account> getAccountsList() {
        return dbhelper.getAllAccounts();
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        return dbhelper.getAccount(accountNo);
    }

    @Override
    public void addAccount(Account account) {
        dbhelper.insertAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        dbhelper.deleteAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, String expenseType, double amount) throws InvalidAccountException {
        if (!getAccountNumbersList().contains(accountNo)) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        switch (expenseType) {
            case "EXPENSE":
                dbhelper.updateBalance(accountNo,amount,true);
                break;
            case "INCOME":
                dbhelper.updateBalance(accountNo,amount,false);
                break;
        }
    }
}
