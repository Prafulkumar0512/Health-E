package com.example.loginpge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.loginpge.Alarmmanager.AlarmReciever1;
import com.example.loginpge.Alarmmanager.Alarmreciever;
import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Data;
import com.example.loginpge.model.Remind;
import com.example.loginpge.model.Tablelist;
import com.example.loginpge.model.Waterremainder;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class waterremainder extends AppCompatActivity {
    RecyclerView waterlist;
    FloatingActionButton addwater;
    DatabaseHandler db=new DatabaseHandler(waterremainder.this);
    com.example.loginpge.Adapter.Waterremainder water1;
    ImageView emptywater;
    TextView instruct;
    ArrayList<Waterremainder> waterlists;
    private DatePickerDialog datepicker1;
    private Calendar cal;
    private int notid=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterremainder);
        addwater=findViewById(R.id.addremind);
        waterlist=findViewById(R.id.waterlist);
        emptywater=findViewById(R.id.emptywater);
        instruct=findViewById(R.id.instruct);
        addwater.setOnClickListener(new View.OnClickListener() {
            TextView Wdate,Wtime,drink;
            Button setwdate,setwtime,setfinal;
            EditText numml;
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheet=new BottomSheetDialog(waterremainder.this);
                bottomSheet.setContentView(R.layout.bottomdialog4);
                bottomSheet.setCanceledOnTouchOutside(true);
                numml=bottomSheet.findViewById(R.id.numml);
                Wdate=bottomSheet.findViewById(R.id.setwdate);
                Wtime=bottomSheet.findViewById(R.id.setwtime);
                setwdate=bottomSheet.findViewById(R.id.getwdate);
                setwtime=bottomSheet.findViewById(R.id.getwtime);
                setfinal=bottomSheet.findViewById(R.id.setwremaind);
                drink=bottomSheet.findViewById(R.id.drinktime);

                setwdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);
                        datepicker1 = new DatePickerDialog(
                                waterremainder.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String date = day + "/" + month + "/" + year;
                                Wdate.setText(date);
                            }
                        }, year, month, day);
                        datepicker1.show();
                    }
                });


                setwtime.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        int Hour=calendar.get(Calendar.HOUR);
                        final int min=calendar.get(Calendar.MINUTE);
                        boolean is24hourformat= DateFormat.is24HourFormat(waterremainder.this);
                        TimePickerDialog timepicker=new TimePickerDialog(waterremainder.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                cal=Calendar.getInstance();
                                cal.set(Calendar.HOUR,hour);
                                cal.set(Calendar.MINUTE,minute);
                                CharSequence time1=DateFormat.format("hh:mm aa",cal);
                                Wtime.setText(time1);
                            }
                        } , Hour,min,is24hourformat);
                        timepicker.show();
                    }
                });

                setfinal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(numml.length()!=0) {
                            int quantity = Integer.parseInt(numml.getText().toString());
                            String wdate = Wdate.getText().toString();
                            String wtime = Wtime.getText().toString();
                            String Drink = drink.getText().toString();
                            Intent intent=new Intent(waterremainder.this, AlarmReciever1.class);
                            intent.putExtra("notificationid",notid);
                            intent.putExtra("Title",Drink);
                            PendingIntent pintent=PendingIntent.getService(waterremainder.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager alaram= (AlarmManager) getSystemService(ALARM_SERVICE);
                            long timems=cal.getTimeInMillis()-(cal.getTimeInMillis()%60000);
                            alaram.set(AlarmManager.RTC_WAKEUP,timems,pintent);
                            Waterremainder water = new Waterremainder(wtime, Drink, wdate, quantity);
                            db.createwremainder(water);
                            bottomSheet.dismiss();
                            Intent i = new Intent(waterremainder.this, waterremainder.class);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            Toast.makeText(waterremainder.this,"Drink Reminder Added",Toast.LENGTH_SHORT).show();

                        }
                        else{
                            numml.setError("Field Required");
                        }

                    }
                });
                bottomSheet.show();

            }
        });
        if(db.Countwremind()==0){
            List<Data>data=db.getinfo();
            emptywater.setVisibility(View.VISIBLE);
            instruct.setVisibility(View.VISIBLE);
            instruct.setText("Drink 12-15 glass of water daily to maintain Stable Health ");

        }
        else{
            waterlists=new ArrayList<>();
            List<Waterremainder>waterlists1=db.getwremaind();
            for(Waterremainder w:waterlists1){
                waterlists.add(w);
            }
            water1=new com.example.loginpge.Adapter.Waterremainder(waterremainder.this,waterlists,waterremainder.this);
            waterlist.setAdapter(water1);
            waterlist.setHasFixedSize(true);
            waterlist.setLayoutManager(new LinearLayoutManager(waterremainder.this));
            emptywater.setVisibility(View.GONE);
            instruct.setVisibility(View.GONE);
        }
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(waterlist);

    }
    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:

                    Waterremainder waterremainder=waterlists.get(position);
                    Waterremainder waterremainder1=new Waterremainder(waterremainder.getId(),waterremainder.getTime(),waterremainder.getText(),waterremainder.getDate(),waterremainder.getWaterml());
                    Intent intent=new Intent(waterremainder.this,AlarmReciever1.class);
                    PendingIntent pintent=PendingIntent.getService(waterremainder.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pintent);
                    db.Deletewremind(waterremainder);
                    Intent i = new Intent(waterremainder.this, waterremainder.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    break;
                case ItemTouchHelper.RIGHT:
                    Waterremainder waterremainder2=waterlists.get(position);
                    db.Deletewremind1(waterremainder2.getId());
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String date = simpleDateFormat.format(c);
                    Waterremainder waterremainder3=new Waterremainder(waterremainder2.getTime(),waterremainder2.getText(),waterremainder2.getDate(),waterremainder2.getWaterml());
                    Intent intent1=new Intent(waterremainder.this,AlarmReciever1.class);
                    PendingIntent pintent1=PendingIntent.getService(waterremainder.this,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager1=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager1.cancel(pintent1);
                    db.createwrecord(waterremainder3);
                    Intent i1 = new Intent(waterremainder.this, waterremainder.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(i1);
                    overridePendingTransition(0, 0);
                    break;


            }
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(waterremainder.this,R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(waterremainder.this,R.color.green))
                    .addSwipeRightActionIcon(R.drawable.done)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menut,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:
                Alertbox();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    void Alertbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(waterremainder.this);
        if (db.Counttask() == 0) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(waterremainder.this);
            builder1.setTitle("Empty List");
            builder1.setMessage("The Drink Time list is Empty");
            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder1.create().show();
        } else {
            builder.setTitle("Delete All ?");
            builder.setMessage("Are you sure you want to delete all Drink Time ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deletewremind();
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