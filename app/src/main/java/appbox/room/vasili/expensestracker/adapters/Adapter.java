package appbox.room.vasili.expensestracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import appbox.room.vasili.expensestracker.R;
import appbox.room.vasili.expensestracker.model.Receipt;

public class Adapter extends RecyclerView.Adapter<Adapter.ReceiptHolder>{

    private List<Receipt> receiptsList =new ArrayList<>();

    private onReceiptClickListener onReceiptClickListener;

    private onDeleteClickListener onDeleteClickListener;

    private Context context;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    @NonNull
    @NotNull
    @Override
    public ReceiptHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.layout_receipt, parent, false);
        ReceiptHolder viewHolder=new ReceiptHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReceiptHolder holder, int position) {
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate= sdf.format(receiptsList.get(position).date);
        holder.receiptDate.setText(formattedDate);
        holder.receiptPrice.setText(""+ receiptsList.get(position).price);
        holder.receiptInfo.setText(receiptsList.get(position).info);
    }

    @Override
    public int getItemCount() {
        return receiptsList.size();
    }

    public void setReceiptsList(List<Receipt> receiptsList){
        this.receiptsList = receiptsList;
        notifyDataSetChanged();
    }

    public interface onReceiptClickListener{
        void onReceiptClick(Receipt receipt);
    }

    public void setOnReceiptClickListener(onReceiptClickListener listener){
        this.onReceiptClickListener=listener;
    }

    public interface onDeleteClickListener{
        void onDeleteClick(Receipt receipt);
    }

    public void setOnDeleteClickListener(onDeleteClickListener listener){
        this.onDeleteClickListener=listener;
    }

    class ReceiptHolder extends RecyclerView.ViewHolder{
        private TextView receiptDate;
        private TextView receiptPrice;
        private TextView receiptInfo;
        private Button receiptDeleteButton;

        public ReceiptHolder(@NonNull View itemView) {
            super(itemView);
            receiptDate =itemView.findViewById(R.id.textview_date);
            receiptPrice =itemView.findViewById(R.id.textview_price);
            receiptInfo =itemView.findViewById(R.id.textview_info);
            receiptDeleteButton =itemView.findViewById(R.id.button_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(onReceiptClickListener!=null && position!= RecyclerView.NO_POSITION){
                        onReceiptClickListener.onReceiptClick(receiptsList.get(position));
                    }
                }
            });
            receiptDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(onDeleteClickListener!=null && position!= RecyclerView.NO_POSITION){
                        onDeleteClickListener.onDeleteClick(receiptsList.get(position));
                    }
                }
            });
        }
    }

}

