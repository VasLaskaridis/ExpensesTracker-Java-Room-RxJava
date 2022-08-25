package appbox.room.vasili.expensestracker.ui.main;

import android.app.Application;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

import appbox.room.vasili.expensestracker.database.ReceiptDatabase;
import appbox.room.vasili.expensestracker.database.Repository;
import appbox.room.vasili.expensestracker.model.Receipt;

public class MainActivityViewModel extends ViewModel {

    public Repository repository;

    public MainActivityViewModel(Repository rep) {
        repository = rep;
    }

    public void insert(double price, Date date, String info) {
        Receipt receipt;
        if (info != null) {
            receipt = new Receipt(price, date, info);
        } else {
            receipt = new Receipt(price, date, "");
        }
        repository.insert(receipt);
    }
}
