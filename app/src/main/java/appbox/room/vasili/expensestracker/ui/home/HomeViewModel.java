package appbox.room.vasili.expensestracker.ui.home;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.List;

import appbox.room.vasili.expensestracker.database.Repository;
import appbox.room.vasili.expensestracker.model.Receipt;

public class HomeViewModel extends ViewModel {

    private final Repository repository;

    private final LiveData<List<Receipt>> currentYearReceipts;

    public HomeViewModel (Repository rep){
        repository = rep;
        currentYearReceipts = repository.getCurrentYearReceipts();
    }

    LiveData<List<Receipt>> getCurrentYearReceipts() {
        return currentYearReceipts;
    }

    public void update(Date date, double price, String info, int id) {
        Receipt receipt=new Receipt(price, date, info);
        receipt.receiptId=id;
        repository.update(receipt);
    }

    public void delete(Receipt receipt) {
        repository.delete(receipt);
    }
}
