package com.example.loginpge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpge.Data.DatabaseHandler;
import com.example.loginpge.model.Data;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class Welcomepg extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Button loginbtn;
    private Button signupbtn;
    private DatabaseHandler db;
    private EditText Fname;
    private EditText Lname;
    private EditText Email;
    private TextView Date;
    private EditText cout;
    private EditText usid;
    private EditText pass;
    private RadioGroup gend;
    private Button sign;
    private RadioButton checkBtn;
    private Button setdate;
    private DatePickerDialog datepicker;
    private Spinner spinner;
    private int age;
    private int year1;
    private String selectcountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        db=new DatabaseHandler(Welcomepg.this);
        loginbtn=findViewById(R.id.login_btn);
        signupbtn=findViewById(R.id.signup_btn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Welcomepg.this, Loginpge.class);
                startActivity(intent);
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.Countinfo()==0){
                    final BottomSheetDialog bottomsheet=new BottomSheetDialog(Welcomepg.this);
                    bottomsheet.setContentView(R.layout.bottomdialog2);
                    bottomsheet.setCanceledOnTouchOutside(true);
                    Fname=bottomsheet.findViewById(R.id.first);
                    Lname=bottomsheet.findViewById(R.id.scd);
                    Email=bottomsheet.findViewById(R.id.thrd);
                    Date=bottomsheet.findViewById(R.id.fort);
                    gend=bottomsheet.findViewById(R.id.radio);
                    usid=bottomsheet.findViewById(R.id.fift);
                    pass=bottomsheet.findViewById(R.id.sixt);
                    sign=bottomsheet.findViewById(R.id.nint);
                    setdate=bottomsheet.findViewById(R.id.dat2);
                    spinner=bottomsheet.findViewById(R.id.droplist);
                    checkBtn=bottomsheet.findViewById(gend.getCheckedRadioButtonId());
                    Locale[] locales = Locale.getAvailableLocales();
                    ArrayList<String> countries = new ArrayList<String>();
                    for (Locale locale : locales) {
                        String country = locale.getDisplayCountry();
                        if (country.trim().length() > 0 && !countries.contains(country)) {
                            countries.add(country);
                        }
                    }
                    Collections.sort(countries);
                    for (String country : countries) {
                        System.out.println(country);
                    }
                    ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(Welcomepg.this,
                            android.R.layout.simple_spinner_item, countries);
                    countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(countryAdapter);
                    spinner.setOnItemSelectedListener(Welcomepg.this);
                    setdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Calendar cal=Calendar.getInstance();
                            int day=cal.get(Calendar.DAY_OF_MONTH);
                            int month=cal.get(Calendar.MONTH);
                            int year=cal.get(Calendar.YEAR);
                            datepicker=new DatePickerDialog(
                                    Welcomepg.this, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    month=month+1;
                                    year1=year;
                                    String date=day + "/" + month + "/" + year;
                                    Date.setText(date);
                                }
                            }, year, month, day);
                            datepicker.show();
                        }
                    });
                    sign.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String firstname=Fname.getText().toString();
                            String lastname=Lname.getText().toString();
                            String EmailId=Email.getText().toString();
                            String DOB=Date.getText().toString();
                            String gender=checkBtn.getText().toString();
                            String country=selectcountry;
                            String Username=usid.getText().toString();
                            String Password=pass.getText().toString();
                            int currentyear=Calendar.getInstance().get(Calendar.YEAR);
                            age=currentyear-year1;

                            if(firstname.length()==0)
                            {
                                Fname.setError("Field Required");
                            }
                            if(lastname.length()==0)
                            {
                                Lname.setError("Field Required");
                            }
                            if(EmailId.length()==0)
                            {
                                Email.setError("Field Required");
                            }
                            if(DOB.length()==0)
                            {
                                Date.setError("Field Required");
                            }
                            if(country.length()==0)
                            {
                                cout.setError("Field Required");
                            }
                            if(Username.length()<=5)
                            {
                                usid.setError("Minimum length 6 maximum length 15 ");
                            }
                            if(Password.length()<=5)
                            {
                                pass.setError("Minimum length 6 maximum length 15 ");
                            }
                            if(Username.length() >=5 && Password.length() >=5){
                                Data data=new Data(firstname,lastname,EmailId,DOB,age,gender,country,Username,Password);
                                db.createdate(data);
                                Toast.makeText(Welcomepg.this, R.string.register,Toast.LENGTH_SHORT).show();
                            }
                            bottomsheet.dismiss();
                        }
                    });
                    bottomsheet.show();
                }
                else{
                    Alertdialog();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectcountry=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void Alertdialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(Welcomepg.this);
        builder.setTitle("Only One User Can Exist in Your Device");
        builder.setMessage("You Already Exists an Account. If you want to Create New Account Delete The Existing Account.Do you want to Delete Existing Account?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deletewremind();
                db.deleterecord();
                db.deleteremind();
                db.deletetodo();
                db.deleteinfo();
                db.deletetable();
                db.deleteall();
                Toast.makeText(Welcomepg.this,"Account Deleted",Toast.LENGTH_SHORT).show();
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