package appbox.room.vasili.expensestracker.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import appbox.room.vasili.expensestracker.model.Receipt;

@Database(entities = Receipt.class, version = 1)
@TypeConverters(DateConverter.class)
public abstract class ReceiptDatabase extends RoomDatabase {

    public abstract ReceiptDao receiptDao();

    private static volatile ReceiptDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ReceiptDatabase getInstance(final Context context){
        if (instance==null) {
            synchronized (ReceiptDatabase.class) {
                instance = Room.databaseBuilder(context, ReceiptDatabase.class, "expenses_tracker_database")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return instance;
    }
}
