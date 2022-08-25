package appbox.room.vasili.expensestracker.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import appbox.room.vasili.expensestracker.ExpensesTrackerApplication;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModel;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModelFactory;
import appbox.room.vasili.expensestracker.ui.monthlyReceipts.MonthlyReceipts;
import appbox.room.vasili.expensestracker.databinding.FragmentSearchBinding;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends Fragment {

    private SearchViewModelFactory mViewModelFactory;
    private SearchViewModel viewModel;

    private FragmentSearchBinding binding;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mViewModelFactory= new SearchViewModelFactory(((ExpensesTrackerApplication) getActivity().getApplication()).getRepository());
        viewModel = new ViewModelProvider(this, mViewModelFactory).get(SearchViewModel.class);


        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.JANUARY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("JANUARY");
            }
        });
        binding.FEBRUARY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("FEBRUARY");
            }
        });
        binding.MARCH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("MARCH");
            }
        });
        binding.APRIL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("APRIL");
            }
        });
        binding.MAY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("MAY");
            }
        });
        binding.JUNE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("JUNE");
            }
        });
        binding.JULY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("JULY");
            }
        });
        binding.AUGUST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("AUGUST");
            }
        });
        binding.SEPTEMBER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("SEPTEMBER");
            }
        });
        binding.OCTOBER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("OCTOBER");
            }
        });
        binding.NOVEMBER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("NOVEMBER");
            }
        });
        binding.DECEMBER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMonthlyReceipts("DECEMBER");
            }
        });

        mDisposable.add(viewModel.getYearsWithReceipts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(yearlist -> ongetYearWithReceiptsList(yearlist),
                        throwable -> Log.e("Error", "Unable to get year list", throwable)));

        return root;
    }

    private void ongetYearWithReceiptsList(List<Integer> yearlist) {
        List<String> list = new ArrayList<>();
        if (yearlist.size() > 0) {
            for (int i=0;i<yearlist.size();i++) {
                list.add(String.valueOf(yearlist.get(i)));
            }
        }else{
            Calendar calendar=Calendar.getInstance();
            list.add(String.valueOf(calendar.get(Calendar.YEAR)));
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerYears.setAdapter(arrayAdapter);
        binding.spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDisposable.add(viewModel.getYearSumOfReceipts(binding.spinnerYears.getSelectedItem().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(yearSum -> binding.textYearSum.setText(String.valueOf(yearSum)),
                                throwable -> Log.e("Error", "Unable to get year sum", throwable)));
}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void gotoMonthlyReceipts(String month) {
        Intent intent = new Intent(getContext(), MonthlyReceipts.class);
        intent.putExtra("month", month);
        intent.putExtra("year",  binding.spinnerYears.getSelectedItem().toString());
        startActivity(intent);
    }
}