package appbox.room.vasili.expensestracker.ui.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import appbox.room.vasili.expensestracker.ExpensesTrackerApplication;
import appbox.room.vasili.expensestracker.R;
import appbox.room.vasili.expensestracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private ActivityMainBinding binding;

    private TextView receiptDateTextview;

    private Date dateOfReceipt;

    private MainActivityViewModel viewModel;

    private MainActivityViewModelFactory mViewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mViewModelFactory= new MainActivityViewModelFactory(((ExpensesTrackerApplication) this.getApplication()).getRepository());
        viewModel = new ViewModelProvider(this, mViewModelFactory).get(MainActivityViewModel.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.getMenu().getItem(1).setEnabled(false);
        navView.setBackground(null);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);

        binding.flbtnAddRecept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReceipt();
            }
        });
    }
    public void addReceipt(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_add_receipt);
        dialog.setCancelable(true);
        EditText editPrice=dialog.findViewById(R.id.edittext_price);
        EditText editInfo=dialog.findViewById(R.id.edittext_info);
        receiptDateTextview=dialog.findViewById(R.id.textview_receipt_date);
        receiptDateTextview.setVisibility(View.GONE);
        ImageButton button_date =dialog.findViewById(R.id.button_date_picker);
        button_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog picker = new DatePickerDialog(
                        MainActivity.this,
                        MainActivity.this,
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
                if(editPrice.getText().toString().length()>0 && dateOfReceipt!=null){
                    viewModel.insert(
                            Double.parseDouble(editPrice.getText().toString()),
                            dateOfReceipt,
                            editInfo.getText().toString());
                    dialog.cancel();
                }else{
                    Toast.makeText(MainActivity.this,"Please make sure you have given a date and an amount", Toast.LENGTH_LONG).show();
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