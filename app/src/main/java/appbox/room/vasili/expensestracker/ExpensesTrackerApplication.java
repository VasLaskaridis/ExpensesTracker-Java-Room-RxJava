package appbox.room.vasili.expensestracker;

import android.app.Application;

import appbox.room.vasili.expensestracker.database.ReceiptDao;
import appbox.room.vasili.expensestracker.database.ReceiptDatabase;
import appbox.room.vasili.expensestracker.database.Repository;

public class ExpensesTrackerApplication extends Application {

    public static Repository repository;

    public static ReceiptDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = ReceiptDatabase.getInstance(this);
        repository=new Repository(db.receiptDao());
    }

    public Repository getRepository(){return repository;}

}
