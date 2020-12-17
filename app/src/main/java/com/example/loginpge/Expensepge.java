package com.example.loginpge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpge.Adapter.Recyclerviewap;
import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Transaction;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Expensepge extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView listview;
    private ArrayList<Transaction> translist;
    private DatabaseHandler db1=new DatabaseHandler(Expensepge.this);
    private Recyclerviewap recycler;
    private EditText title;
    private EditText amout;
    private TextView Date;
    private Button setdate;
    private Button addtrans;
    private ImageView empty;
    private DatePickerDialog datepicker1;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        fab=findViewById(R.id.float1);
        empty=findViewById(R.id.emptyview);
        total=findViewById(R.id.toamount);
        total.setText( "₹" + db1.totalamount());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomsheet=new BottomSheetDialog(Expensepge.this);
                bottomsheet.setContentView(R.layout.bottomdialog);
                bottomsheet.setCanceledOnTouchOutside(true);
                title=bottomsheet.findViewById(R.id.edtx1);
                amout=bottomsheet.findViewById(R.id.edtx2);
                Date=bottomsheet.findViewById(R.id.date1);
                setdate=bottomsheet.findViewById(R.id.setd1);
                addtrans=bottomsheet.findViewById(R.id.adtrans);
                setdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Calendar cal = Calendar.getInstance();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);
                        datepicker1 = new DatePickerDialog(
                                Expensepge.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String date = day + "/" + month + "/" + year;
                                Date.setText(date);
                            }
                        }, year, month, day);
                        datepicker1.show();
                    }
                });
                addtrans.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String Title1=title.getText().toString();
                        String Amount= amout.getText().toString();
                        String date=Date.getText().toString();
                        Transaction trans=new Transaction(Title1,Amount,date);
                        if(Title1.length()==0)
                        {
                          title.setError("Field Required");
                        }
                        if(Amount.length()==0)
                        {
                            amout.setError("Field Required");
                        }
                        if(Title1.length()!=0 && Amount.length()!=0)
                        {
                            db1.Create(trans);
                            bottomsheet.dismiss();
                            Intent i = new Intent(Expensepge.this, Expensepge.class);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            Toast.makeText(Expensepge.this, R.string.expense,Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                bottomsheet.show();
            }


        });

            if(db1.Count()==0){
                empty.setVisibility(View.VISIBLE);
            }
            else{
                translist=new ArrayList<>();
                List<Transaction> translist1=db1.gettrans();
                for(Transaction trans1:translist1){
                    translist.add(trans1);
                }
                listview=findViewById(R.id.recycleview);
                recycler=new Recyclerviewap(Expensepge.this, Expensepge.this,translist);
                listview.setAdapter(recycler);
                listview.setHasFixedSize(true);
                LinearLayoutManager layoutManager=new LinearLayoutManager(Expensepge.this);
                layoutManager.setStackFromEnd(true);
                layoutManager.setReverseLayout(true);
                listview.setLayoutManager(layoutManager);
                empty.setVisibility(View.GONE);
            }
            ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(listview);

    }
    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    Transaction trans=translist.get(position);
                    Transaction trans2=new Transaction(trans.getId(),trans.getTitle(),trans.getAmount(),trans.getDate());
                    db1.Deletedata(trans2);
                    recycler.notifyItemRemoved(position);
                    Intent i = new Intent(Expensepge.this, Expensepge.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    break;

            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Expensepge.this,R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menut,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                alertbox();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    void alertbox(){
        AlertDialog.Builder builder=new AlertDialog.Builder(Expensepge.this);
        if(db1.Count()==0) {
            AlertDialog.Builder builder1=new AlertDialog.Builder(Expensepge.this);
            builder1.setTitle("List empty");
            builder1.setMessage("The Transaction List is Empty");
            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder1.create().show();
        }
        else {
            builder.setTitle("Delete All ?");
            builder.setMessage("Are you sure you want to delete all Expenses History ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db1.deleteall();
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }

    }

}