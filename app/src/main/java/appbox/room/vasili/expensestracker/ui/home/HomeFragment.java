package appbox.room.vasili.expensestracker.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import appbox.room.vasili.expensestracker.ExpensesTrackerApplication;
import appbox.room.vasili.expensestracker.R;
import appbox.room.vasili.expensestracker.adapters.Adapter;
import appbox.room.vasili.expensestracker.databinding.FragmentHomeBinding;
import appbox.room.vasili.expensestracker.model.Receipt;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModel;
import appbox.room.vasili.expensestracker.ui.main.MainActivityViewModelFactory;


public class HomeFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private HomeViewModelFactory mViewModelFactory;
    private HomeViewModel homeViewModel;

    private FragmentHomeBinding binding;

    private Adapter adapter;

    private TextView receiptDateTextview;

    private Date dateOfReceipt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mViewModelFactory= new HomeViewModelFactory(((ExpensesTrackerApplication) getActivity().getApplication()).getRepository());
        homeViewModel = new ViewModelProvider(this, mViewModelFactory).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        adapter=new Adapter();
        adapter.setOnReceiptClickListener(new Adapter.onReceiptClickListener() {
            @Override
            public void onReceiptClick(Receipt receipt) {
                updateReceipt(receipt);
            }
        });

        adapter.setOnDeleteClickListener(new Adapter.onDeleteClickListener() {
            @Override
            public void onDeleteClick(Receipt receipt) {
                showDeleteDialog(receipt);
            }
        });

        binding.recyclerviewReceiptList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewReceiptList.setHasFixedSize(true);
        binding.recyclerviewReceiptList.setAdapter(adapter);

        homeViewModel.getCurrentYearReceipts().observe(getViewLifecycleOwner(), new Observer<List<Receipt>>() {
            @Override
            public void onChanged(List<Receipt> receipts) {
                adapter.setReceiptsList(receipts);
            }
        });

        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar= Calendar.getInstance();
        String formattedDate= sdf.format(calendar.getTime());
        binding.textDate.setText(formattedDate);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateReceipt(Receipt receipt){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_add_receipt);
        dialog.setCancelable(true);
        EditText editPrice=dialog.findViewById(R.id.edittext_price);
        editPrice.setText(String.valueOf(receipt.price));
        TextView receiptDateTextview=dialog.findViewById(R.id.textview_receipt_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(receipt.date);
        receiptDateTextview.setText(formattedDate);
        EditText editInfo=dialog.findViewById(R.id.edittext_info);
        editInfo.setText(receipt.info);
        ImageButton button_datepicker =dialog.findViewById(R.id.button_date_picker);
        button_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog picker = new DatePickerDialog(
                        getContext(),
                        HomeFragment.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                picker.show();

            }
        });
        Button button_save =dialog.findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPrice.getText().toString().length()>0){
                    homeViewModel.update(receipt.date,
                            Double.parseDouble(editPrice.getText().toString()),
                            editInfo.getText().toString(),
                            receipt.receiptId);
                    dialog.cancel();
                }else{
                    Toast.makeText(getContext(),"Please make sure you give a value", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button button_cancel =dialog.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void showDeleteDialog(Receipt receipt) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Do you want to delete the receipt?");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                homeViewModel.delete(receipt);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        dateOfReceipt = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(dateOfReceipt);
        receiptDateTextview.setVisibility(View.VISIBLE);
        receiptDateTextview.setText(formattedDate);
    }
}