package appbox.room.vasili.expensestracker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "receipt_table")
public class Receipt {

        @PrimaryKey(autoGenerate = true)
        public int receiptId=0;

        public double price;

        public Date date;

        public String info;

        public Receipt(double price, Date date, String info){
            this.price=price;
            this.date=date;
            this.info=info;
        }
}
