package appbox.room.vasili.expensestracker.ui.search;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.util.List;

import appbox.room.vasili.expensestracker.database.Repository;
import io.reactivex.Single;

public class SearchViewModel extends ViewModel {

    private final Repository repository;

    public SearchViewModel(Repository rep){
        repository = rep;
    }

    public Single<List<Integer>> getYearsWithReceipts() {
        return repository.getYearsWithReceipts();
    }

    public Single<Double> getYearSumOfReceipts(String year) {
        return repository.getYearSumOfReceipts(year+"-01"+"-01");
    }
}