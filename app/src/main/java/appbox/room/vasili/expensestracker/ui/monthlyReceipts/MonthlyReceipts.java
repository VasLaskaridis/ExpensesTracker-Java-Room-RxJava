package appbox.room.vasili.expensestracker.ui.monthlyReceipts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.List;

import appbox.room.vasili.expensestracker.ExpensesTrackerApplication;
import appbox.room.vasili.expensestracker.adapters.Adapter;
import appbox.room.vasili.expensestracker.databinding.ActivityMonthlyReceiptsBinding;
import appbox.room.vasili.expensestracker.model.Receipt;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModel;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModelFactory;

public class MonthlyReceipts extends AppCompatActivity {

    private MonthlyReceiptsViewModelFactory mViewModelFactory;

    private MonthlyReceiptsViewModel viewModel;

    private ActivityMonthlyReceiptsBinding binding;

    private String month;

    private String year;

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityMonthlyReceiptsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModelFactory= new MonthlyReceiptsViewModelFactory(((ExpensesTrackerApplication) this.getApplication()).getRepository());
        viewModel = new ViewModelProvider(this, mViewModelFactory).get(MonthlyReceiptsViewModel.class);

        month=getIntent().getStringExtra("month");
        binding.textviewMonth.setText(month+": ");
        year=getIntent().getStringExtra("year");

        adapter=new Adapter();
        adapter.setOnDeleteClickListener(new Adapter.onDeleteClickListener() {
            @Override
            public void onDeleteClick(Receipt receipt) {
                showDeleteDialog(receipt);
            }
        });
        binding.recyclerviewReceiptList.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerviewReceiptList.setHasFixedSize(true);
        binding.recyclerviewReceiptList.setAdapter(adapter);

        viewModel.getMonthSumOfReceipts(lettersToNumbers(month),year).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double sum) {
                if(sum!=null){
                    binding.textviewSum.setText(String.valueOf(sum));
                } else{
                    binding.textviewSum.setText("0");
                }
            }
        });

        viewModel.getMonthReceipts(lettersToNumbers(month),year).observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(List<Receipt> receipts) {
                adapter.setReceiptsList(receipts);
            }
        });
    }

    public void showDeleteDialog(Receipt receipt) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Do you want to delete the receipt?");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.delete(receipt);
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(true);
        AlertDialog alert = dialog.create();
        alert.show();
    }

    private String lettersToNumbers(String month){
        if(month.equals("JANUARY")){return "01";}
        else if(month.equals("FEBRUARY")){return "02";}
        else if(month.equals("MARCH")){return "03";}
        else if(month.equals("APRIL")){return "04";}
        else if(month.equals("MAY")){return "05";}
        else if(month.equals("JUNE")){return "06";}
        else if(month.equals("JULY")){return "07";}
        else if(month.equals("AUGUST")){return "08";}
        else if(month.equals("SEPTEMBER")){return "09";}
        else if(month.equals("OCTOBER")){return "10";}
        else if(month.equals("NOVEMBER")){return "11";}
        else {return "12";}
    }
}