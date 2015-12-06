package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model;

/**
 * Created by Lahiru on 06/12/2015.:)
 */
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "130683X";
    public static final String ACCOUNTS_TABLE_NAME = "account";
    public static final String ACCOUNTS_COLUMN_ACCNO = "accountNo";
    public static final String ACCOUNTS_COLUMN_BANK = "bankName";
    public static final String ACCOUNTS_COLUMN_NAME = "accountHolderName";
    public static final String ACCOUNTS_COLUMN_BAL = "balance";

    public static final String TRANSACTIONS_TABLE_NAME = "transactions";
    public static final String TRANSACTIONS_COLUMN_ACCNO = "accountNo";
    public static final String TRANSACTIONS_COLUMN_TYPE = "expenseType";
    public static final String TRANSACTIONS_COLUMN_AMOUNT = "amount";
    public static final String TRANSACTIONS_COLUMN_DATE = "date";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String accountCreateQuery=
        "CREATE TABLE IF NOT EXISTS `"+ACCOUNTS_TABLE_NAME+"` ("+
        " `accountNo` varchar(10) NOT NULL,"+
        " `bankName` varchar(20) NOT NULL,"+
        " `accountHolderName` varchar(30) NOT NULL,"+
        " `balance` decimal(10,2) NOT NULL,"+
        " PRIMARY KEY (`accountNo`)"+
        ");";

        String transactionCreateQuery=
        "CREATE TABLE IF NOT EXISTS `"+TRANSACTIONS_TABLE_NAME+"` ("+
        " `accountNo` varchar(10) NOT NULL,"+
        " `expenseType` varchar(10) NOT NULL,"+
        " `amount` decimal(10,2) NOT NULL,"+
        " `date` varchar(15) NOT NULL"+
        ");";

        db.execSQL(accountCreateQuery);
        db.execSQL(transactionCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ACCOUNTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TRANSACTIONS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertAccount (Account acc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNTS_COLUMN_NAME, acc.getAccountHolderName());
        contentValues.put(ACCOUNTS_COLUMN_ACCNO, acc.getAccountNo());
        contentValues.put(ACCOUNTS_COLUMN_BANK, acc.getBankName());
        contentValues.put(ACCOUNTS_COLUMN_BAL, acc.getBalance());
        db.insert(ACCOUNTS_TABLE_NAME, null, contentValues);

        return true;
    }

    public boolean insertTransaction (Transaction tc)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSACTIONS_COLUMN_DATE, tc.getDate());
        contentValues.put(TRANSACTIONS_COLUMN_ACCNO, tc.getAccountNo());
        contentValues.put(TRANSACTIONS_COLUMN_TYPE, tc.getExpenseType());
        contentValues.put(TRANSACTIONS_COLUMN_AMOUNT, tc.getAmount());
        db.insert(TRANSACTIONS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateBalance (String accNo,Double value,boolean dec)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String operator=" + ";
        if(dec)operator=" - ";
        db.execSQL("UPDATE "+ACCOUNTS_TABLE_NAME+" SET "+ACCOUNTS_COLUMN_BAL+" = "+ACCOUNTS_COLUMN_BAL+operator+value.toString()+" where "+ACCOUNTS_COLUMN_ACCNO+"='"+accNo+"';");
        return true;
    }

    public Integer deleteAccount (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ACCOUNTS_TABLE_NAME,
                ACCOUNTS_COLUMN_ACCNO+" = ? ",
                new String[] { id });
    }

    public ArrayList<Account> getAllAccounts()
    {
        ArrayList<Account> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ACCOUNTS_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Account(res.getString(0),res.getString(1),res.getString(2),res.getDouble(3)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Transaction> getAllTransactions()
    {
        ArrayList<Transaction> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TRANSACTIONS_TABLE_NAME, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Transaction(res.getString(3),res.getString(0),res.getString(1),res.getDouble(2)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Transaction> getAllTransactionsLimit(int limit)
    {
        ArrayList<Transaction> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TRANSACTIONS_TABLE_NAME+" limit "+limit, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(new Transaction(res.getString(3),res.getString(0),res.getString(1),res.getDouble(2)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllAccountNumbers()
    {
        ArrayList<String> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+ACCOUNTS_COLUMN_ACCNO+" from "+ACCOUNTS_TABLE_NAME+";", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(0));
            res.moveToNext();
        }
        return array_list;
    }

    public Account getAccount(String accountNo)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ACCOUNTS_TABLE_NAME + " where "+ACCOUNTS_COLUMN_ACCNO + "="+accountNo, null );
        res.moveToFirst();

        return new Account(res.getString(0),res.getString(1),res.getString(2),res.getDouble(3));
    }
}