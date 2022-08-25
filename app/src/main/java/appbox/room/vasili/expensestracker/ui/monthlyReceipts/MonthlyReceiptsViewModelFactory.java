package appbox.room.vasili.expensestracker.ui.monthlyReceipts;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import appbox.room.vasili.expensestracker.database.Repository;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModel;

public class MonthlyReceiptsViewModelFactory implements ViewModelProvider.Factory{

    private final Repository repository;

    public MonthlyReceiptsViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MonthlyReceiptsViewModel.class)) {
            return (T) new MonthlyReceiptsViewModel(repository);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
