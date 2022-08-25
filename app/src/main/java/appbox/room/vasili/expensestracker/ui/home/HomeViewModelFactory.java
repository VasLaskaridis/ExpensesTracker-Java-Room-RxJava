package appbox.room.vasili.expensestracker.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import appbox.room.vasili.expensestracker.database.Repository;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModel;

public class HomeViewModelFactory implements ViewModelProvider.Factory{

    private final Repository repository;

    public HomeViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
