package appbox.room.vasili.expensestracker.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import appbox.room.vasili.expensestracker.model.Receipt;
import io.reactivex.Single;

public class Repository {

    private final ReceiptDao receiptDao;

    public Repository(ReceiptDao dao){
        receiptDao = dao;
    }

    public void insert(Receipt receipt) {
        ReceiptDatabase.databaseWriteExecutor.execute(() -> {
            receiptDao.insert(receipt);
        });
    }

    public void update(Receipt receipt) {
        ReceiptDatabase.databaseWriteExecutor.execute(() -> {
            receiptDao.update(receipt);
        });
    }

    public void delete(Receipt receipt) {
        ReceiptDatabase.databaseWriteExecutor.execute(() -> {
            receiptDao.delete(receipt);
        });
    }
    public Single<List<Integer>> getYearsWithReceipts() {
        return receiptDao.getYearsWithReceipts();
    }

    public LiveData<List<Receipt>> getCurrentYearReceipts() {
        return receiptDao.getCurrentYearReceipts();
    }

    public Single<Double> getYearSumOfReceipts(String year) {
       return  receiptDao.getYearSumOfReceipts(year);
    }

    public LiveData<Double> getMonthSumOfReceipts(String month) {
        return receiptDao.getMonthSumOfReceipts(month);
    }

    public LiveData<List<Receipt>> getMonthReceipts(String month) {
        return receiptDao.getMonthReceipts(month);
    }

}
