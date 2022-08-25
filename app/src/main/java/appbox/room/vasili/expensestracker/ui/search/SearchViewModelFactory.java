package appbox.room.vasili.expensestracker.ui.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import appbox.room.vasili.expensestracker.database.Repository;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModel;

public class SearchViewModelFactory implements ViewModelProvider.Factory{

    private final Repository repository;

    public SearchViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SearchViewModel.class)) {
            return (T) new SearchViewModel(repository);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
