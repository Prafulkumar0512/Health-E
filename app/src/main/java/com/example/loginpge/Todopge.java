package com.example.loginpge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.loginpge.Adapter.Recycletask;
import com.example.loginpge.Alarmmanager.AlarmReciever1;
import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Todopge extends AppCompatActivity {
    private RecyclerView recycle1;
    private FloatingActionButton fab1;
    private ImageView empty;
    private TextView emptytext;
    private EditText task;
    private TextView text;
    private Button time;
    private CheckBox check;
    private Button adtask;
    private TextView date;
    private Button setdate;
    private DatePickerDialog datepicker;
    private DatabaseHandler db;
    private ArrayList<Task> tasklist=new ArrayList<>();;
    private Recycletask recycler1;
    private int notid=1;
    private Calendar cal;
    private long isset;
    private TextView test;
    private ImageView alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        fab1=findViewById(R.id.float2);
        recycle1=findViewById(R.id.recycleview1);
        empty=findViewById(R.id.emptyview1);
        emptytext=findViewById(R.id.emtext);
        alarm=findViewById(R.id.alarm);
        db=new DatabaseHandler(Todopge.this);
        Notificationgenerator();
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomsheet1= new BottomSheetDialog(Todopge.this);
                bottomsheet1.setContentView(R.layout.bottomdialog1);
                task=bottomsheet1.findViewById(R.id.edtask);
                text=bottomsheet1.findViewById(R.id.time1);
                time=bottomsheet1.findViewById(R.id.settime);
                check=bottomsheet1.findViewById(R.id.setr);
                adtask=bottomsheet1.findViewById(R.id.adtask);
                date=bottomsheet1.findViewById(R.id.dte);
                check=bottomsheet1.findViewById(R.id.setr);
                setdate=bottomsheet1.findViewById(R.id.setdte);
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar=Calendar.getInstance();
                        int Hour=calendar.get(Calendar.HOUR);
                        final int min=calendar.get(Calendar.MINUTE);
                        boolean is24hourformat= DateFormat.is24HourFormat(Todopge.this);
                        TimePickerDialog timepicker=new TimePickerDialog(Todopge.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                cal=Calendar.getInstance();
                                cal.set(Calendar.HOUR,hour);
                                cal.set(Calendar.MINUTE,minute);
                                CharSequence time1=DateFormat.format("hh:mm aa",cal);
                                text.setText(time1);
                            }
                        } , Hour,min,is24hourformat);
                        timepicker.show();
                    }
                });
                setdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);
                        datepicker = new DatePickerDialog(
                                Todopge.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month + 1;
                                String Date = day + "/" + month + "/" + year;
                                date.setText(Date);
                            }
                        }, year, month, day);
                        datepicker.show();
                    }
                });
                adtask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(check.isChecked()){
                            isset=1;
                            String task1 = task.getText().toString();
                            String Time = text.getText().toString();
                            String date1 = date.getText().toString();
                            Intent intent=new Intent(Todopge.this, AlarmReciever1.class);
                            intent.putExtra("notificationid",notid);
                            intent.putExtra("Title",task1);
                            PendingIntent pintent=PendingIntent.getService(Todopge.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager alaram= (AlarmManager) getSystemService(ALARM_SERVICE);
                            long timems=cal.getTimeInMillis()-(cal.getTimeInMillis()%60000);
                            alaram.set(AlarmManager.RTC_WAKEUP,timems,pintent);
                            Task task2 = new Task(task1, date1, Time);
                            db.createtask(task2);
                            bottomsheet1.dismiss();
                            Intent i = new Intent(Todopge.this, Todopge.class);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            Toast.makeText(Todopge.this, R.string.task,Toast.LENGTH_SHORT).show();

                        }
                        else {
                            String task1 = task.getText().toString();
                            String Time = text.getText().toString();
                            String date1 = date.getText().toString();
                            Task task2 = new Task(task1, date1, Time);
                            db.createtask(task2);
                            bottomsheet1.dismiss();
                            Intent i = new Intent(Todopge.this, Todopge.class);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(i);
                            overridePendingTransition(0, 0);
                            Toast.makeText(Todopge.this, R.string.task,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                bottomsheet1.show();
            }
        });
       if(db.Counttask()==0){
            empty.setVisibility(View.VISIBLE);
            emptytext.setVisibility(View.VISIBLE);
        }
        else{

            List<Task>tasklists=db.gettodo();
            for (Task tas:tasklists){
                tasklist.add(tas);
            }
            recycle1=findViewById(R.id.recycleview1);
            recycler1=new Recycletask(Todopge.this, Todopge.this,tasklist,isset);
            recycle1.setAdapter(recycler1);
            recycle1.setHasFixedSize(true);
            recycle1.setLayoutManager(new LinearLayoutManager(Todopge.this));
            empty.setVisibility(View.GONE);
            emptytext.setVisibility(View.GONE);


        }
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simplecallback);
        itemTouchHelper.attachToRecyclerView(recycle1);

    }
    Task deleted=null;
    Task deleted1=null;
    ItemTouchHelper.SimpleCallback simplecallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            switch (direction){
                case ItemTouchHelper.LEFT:
                    deleted=tasklist.get(position);
                    Task delete=new Task(tasklist.get(position).getId(),tasklist.get(position).getTask(),tasklist.get(position).getDate(),tasklist.get(position).getTime());
                    Intent intent=new Intent(Todopge.this,AlarmReciever1.class);
                    PendingIntent pintent=PendingIntent.getService(Todopge.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pintent);
                    db.Deletetodo(delete);
                    recycler1.notifyItemRemoved(position);
                    Intent i = new Intent(Todopge.this, Todopge.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                    break;
                case ItemTouchHelper.RIGHT:
                    deleted1=tasklist.get(position);
                    Task delete1=new Task(tasklist.get(position).getId(),tasklist.get(position).getTask(),tasklist.get(position).getDate(),tasklist.get(position).getTime());
                    Intent intent1=new Intent(Todopge.this,AlarmReciever1.class);
                    PendingIntent pintent1=PendingIntent.getService(Todopge.this,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager1=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager1.cancel(pintent1);
                    db.Deletetodo1(tasklist.get(position).getId());
                    recycler1.notifyItemRemoved(position);
                    Intent i1 = new Intent(Todopge.this, Todopge.class);
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
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(Todopge.this,R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(Todopge.this,R.color.green))
                    .addSwipeRightActionIcon(R.drawable.done)
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
                Alertbox();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    void Alertbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Todopge.this);
        if (db.Counttask() == 0) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(Todopge.this);
            builder1.setTitle("Empty List");
            builder1.setMessage("The Todo list is Empty");
            builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder1.create().show();
        } else {
            builder.setTitle("Delete All ?");
            builder.setMessage("Are you sure you want to delete all Todo task ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deletetodo();
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
    public void Notificationgenerator(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name="Health-e";
            String description="It's time";
            int importance=NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notification=new NotificationChannel("Health-e",name,importance);
            notification.setDescription(description);

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notification);
        }

    }


}