package com.example.loginpge;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.loginpge.Adapter.Billadapter;
import com.example.loginpge.Adapter.Recycleremind;
import com.example.loginpge.Alarmmanager.AlarmReciever1;
import com.example.loginpge.Alarmmanager.Alarmreciever;
import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Remind;
import com.example.loginpge.model.Tablelist;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class billreminder extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FloatingActionButton addremind;
    private RecyclerView remindlist;
    private DatabaseHandler db=new DatabaseHandler(billreminder.this);
    private Spinner billlist;
    private Recycleremind recycleremind;
    String selectedbill;
    private Billadapter billadapter;
    public ArrayList<String> bills;
    private ArrayList<Remind> reminds;
    private String months[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
    private ImageView empty;
    private String text,month3;
    private TextView emptytext;
    private Calendar calendar=Calendar.getInstance();;
    private int notid=1;
    private AlarmManager alaram;
    private DatePickerDialog datepicker1;
    private TimePickerDialog timepicker;
    private int  Day,Month,Year,Hrs,Min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billreminder);
        addremind=findViewById(R.id.addremind);
        remindlist=findViewById(R.id.remindlist);
        empty=findViewById(R.id.payment);
        emptytext=findViewById(R.id.paytext);
        bills=new ArrayList<>();
        bills.add("SELECT A BILL TYPE");
        bills.add("ELECTRICITY BILL");
        bills.add("WATER BILL");
        bills.add("PROPERTY TAX BILL");
        bills.add("GAS BILL");
        bills.add("CREDIT CARD BILL");
        bills.add("BANK BILL");
        bills.add("MEDICAL BILL");
        bills.add("OTHER");

        addremind.setOnClickListener(new View.OnClickListener() {
            EditText billamount,others;
            Button setdate1,setdate2,submit,setmonth,srtime;
            TextView reminderdate,duedate,textmonth,rtime;

            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomsheet=new BottomSheetDialog(billreminder.this);
                bottomsheet.setContentView(R.layout.bottomdialog3);
                bottomsheet.setCanceledOnTouchOutside(true);
                billamount=bottomsheet.findViewById(R.id.billamount);
                setdate1=bottomsheet.findViewById(R.id.setdate1);
                setdate2=bottomsheet.findViewById(R.id.setdate2);
                submit=bottomsheet.findViewById(R.id.submit);
                reminderdate=bottomsheet.findViewById(R.id.textrdate);
                duedate=bottomsheet.findViewById(R.id.textddate);
                billlist=bottomsheet.findViewById(R.id.billlist);
                setmonth=bottomsheet.findViewById(R.id.setmonth);
                srtime=bottomsheet.findViewById(R.id.srtime);
                rtime=bottomsheet.findViewById(R.id.rtime);
                textmonth=bottomsheet.findViewById(R.id.textmonth);
                billadapter=new Billadapter(billreminder.this,bills);
                billlist.setAdapter(billadapter);
                billlist.setOnItemSelectedListener(billreminder.this);

                setdate1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);
                        datepicker1 = new DatePickerDialog(
                                billreminder.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String date = day + "/" + month + "/" + year;
                                reminderdate.setText(date);
                                Day=datePicker.getDayOfMonth();
                                Month=datePicker.getMonth();
                                Year=datePicker.getYear();
                                calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
                            }
                        }, year, month, day);
                        datepicker1.show();
                    }
                });
                srtime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal=Calendar.getInstance();
                        int Hour=cal.get(Calendar.HOUR);
                        final int min=cal.get(Calendar.MINUTE);
                        boolean is24hourformat= DateFormat.is24HourFormat(billreminder.this);
                        timepicker=new TimePickerDialog(billreminder.this, new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                calendar.set(Calendar.HOUR,hour);
                                calendar.set(Calendar.MINUTE,minute);
                                CharSequence time1=DateFormat.format("hh:mm aa",calendar);
                                rtime.setText(time1);
                                Hrs=view.getHour();
                                Min=view.getMinute();
                            }
                        } , Hour,min,is24hourformat);
                        timepicker.show();

                    }
                });
                setdate2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);
                        DatePickerDialog datepicker1 = new DatePickerDialog(
                                billreminder.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String date = day + "/" + month + "/" + year;
                                duedate.setText(date);
                            }
                        }, year, month, day);
                        datepicker1.show();
                    }
                });
                setmonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(billreminder.this,
                                new MonthPickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(int selectedMonth, int selectedYear) {
                                        textmonth.setText(months[selectedMonth]+"/"+selectedYear);
                                    }
                                }, Calendar.YEAR, Calendar.MONTH);
                        builder.setActivatedMonth(Calendar.JANUARY)
                                .setMinYear(1990)
                                .setActivatedYear(2017)
                                .setMaxYear(2050)
                                .setMinMonth(Calendar.JANUARY)
                                .setTitle("Select month")
                                .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
                                .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                                    @Override
                                    public void onMonthChanged(int selectedMonth) { } })
                                .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                                    @Override
                                    public void onYearChanged(int selectedYear) {  } })
                                .build()
                                .show();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(billamount.length()==0){
                            billamount.setError("Field Required");
                        }
                        else{
                            Calendar cal1=Calendar.getInstance();
                            cal1.set(Year,Month,Day,Hrs,Min,00);
                            String billtype=selectedbill;
                            String amount=billamount.getText().toString();
                            String Reminderdate=reminderdate.getText().toString();
                            String Duedate=duedate.getText().toString();
                            String billmon=textmonth.getText().toString();
                            Intent intent=new Intent(billreminder.this, AlarmReciever1.class);
                            intent.putExtra("notificationid",notid);
                            intent.putExtra("Title",billtype);
                            PendingIntent pintent=PendingIntent.getService(billreminder.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            alaram= (AlarmManager) getSystemService(ALARM_SERVICE);
                            long timems=cal1.getTimeInMillis()-(cal1.getTimeInMillis()%60000);
                            alaram.set(AlarmManager.RTC_WAKEUP,timems,pintent);
                            Remind remind=new Remind(billtype,amount,Reminderdate,Duedate,billmon);
                            db.createremind(remind);
                            bottomsheet.dismiss();
                            Intent i = new Intent(billreminder.this, billreminder.class);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            Toast.makeText(billreminder.this,"Bill Reminder Added Successfully",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                bottomsheet.show();

            }

        });
        if(db.Countremind()==0){
            empty.setVisibility(View.VISIBLE);
            emptytext.setVisibility(View.VISIBLE);
        }
        else
        {
          reminds=new ArrayList<>();
          List<Remind>reminds1=db.getremind();
          for(Remind r:reminds1){
              reminds.add(r);
          }
          recycleremind=new Recycleremind(billreminder.this,billreminder.this,reminds);
          remindlist.setAdapter(recycleremind);
          remindlist.setHasFixedSize(true);
          remindlist.setLayoutManager(new LinearLayoutManager(billreminder.this));
          empty.setVisibility(View.GONE);
          emptytext.setVisibility(View.GONE);
        }
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(remindlist);
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
                    Remind remind=reminds.get(position);
                    Remind remind1=new Remind(remind.getId(),remind.getTitle(),remind.getAmount(),remind.getRemind_date(),remind.getDue_date());
                    Intent intent=new Intent(billreminder.this,AlarmReciever1.class);
                    PendingIntent pintent=PendingIntent.getService(billreminder.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pintent);
                    db.Deleteremind(remind1);
                    Intent i = new Intent(billreminder.this, billreminder.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    break;
                case ItemTouchHelper.RIGHT:
                    Remind remind2=reminds.get(position);
                    db.Deleteremind1(remind2.getId());
                    Date c = Calendar.getInstance().getTime();
                    Intent intent1=new Intent(billreminder.this,AlarmReciever1.class);
                    PendingIntent pintent1=PendingIntent.getService(billreminder.this,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager1=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager1.cancel(pintent1);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String date = simpleDateFormat.format(c);
                    Tablelist tablelist=new Tablelist(remind2.getMonth1(),remind2.getTitle(),remind2.getAmount(),date);
                    db.createbhistory(tablelist);
                    Intent i1 = new Intent(billreminder.this, billreminder.class);
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(billreminder.this,R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(billreminder.this,R.color.green))
                    .addSwipeRightActionIcon(R.drawable.done)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedbill=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        AlertDialog.Builder builder=new AlertDialog.Builder(billreminder.this);
        if(db.Countremind()==0) {
            AlertDialog.Builder builder1=new AlertDialog.Builder(billreminder.this);
            builder1.setTitle("List empty");
            builder1.setMessage("The Payment reminder List is Empty");
            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder1.create().show();
        }
        else {
            builder.setTitle("Delete All ?");
            builder.setMessage("Are you sure you want to delete all Payment Reminders ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteremind();
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