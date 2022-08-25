package appbox.room.vasili.expensestracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import appbox.room.vasili.expensestracker.model.Receipt;
import io.reactivex.Single;

@Dao
public interface ReceiptDao {
    @Insert
    void  insert(Receipt receipt);

    @Update
    void  update(Receipt receipt);

    @Delete
    void  delete(Receipt receipt);

//    @Query("SELECT * FROM receipt_table")
//    LiveData<List<Receipt>> getAllReceipts();

    @Query("SELECT * FROM receipt_table WHERE " +
            "strftime('%Y', date/1000,'unixepoch','localtime')=strftime('%Y', 'now' )")
    LiveData<List<Receipt>> getCurrentYearReceipts();

    @Query("SELECT SUM(price) FROM receipt_table WHERE " +
            "strftime('%Y', date/1000,'unixepoch','localtime')=strftime('%Y', :year )")
    Single<Double> getYearSumOfReceipts(String year);

    @Query("SELECT SUM(price) FROM receipt_table WHERE " +
            "strftime('%Y-%m', date/1000,'unixepoch','localtime')=strftime('%Y-%m', :month)")
    LiveData<Double> getMonthSumOfReceipts(String month);

    @Query("SELECT * FROM receipt_table WHERE " +
            "strftime('%Y-%m', date/1000,'unixepoch','localtime')=strftime('%Y-%m', :month) ORDER BY date")
    LiveData<List<Receipt>> getMonthReceipts(String month);

    @Query("SELECT DISTINCT  strftime('%Y',date/1000 ,'unixepoch','localtime') FROM receipt_table ORDER BY date")
    Single<List<Integer>> getYearsWithReceipts();
}
