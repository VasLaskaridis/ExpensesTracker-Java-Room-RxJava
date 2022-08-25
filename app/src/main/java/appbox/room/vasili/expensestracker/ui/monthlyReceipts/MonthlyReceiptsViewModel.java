package appbox.room.vasili.expensestracker.ui.monthlyReceipts;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import appbox.room.vasili.expensestracker.database.Repository;
import appbox.room.vasili.expensestracker.model.Receipt;

public class MonthlyReceiptsViewModel extends ViewModel {

    private final Repository repository;

    public MonthlyReceiptsViewModel(Repository rep) {

        repository = rep;
    }

    public LiveData<Double> getMonthSumOfReceipts(String month, String year) {
        return repository.getMonthSumOfReceipts(year+"-"+month+"-01");
    }

    public LiveData<List<Receipt>> getMonthReceipts(String month, String year) {
        return repository.getMonthReceipts(year+"-"+month+"-01");
    }

    public void delete(Receipt receipt) { repository.delete(receipt); }
}
