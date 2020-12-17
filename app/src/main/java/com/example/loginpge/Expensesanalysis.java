package com.example.loginpge;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpge.Data.DatabaseHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Expensesanalysis extends AppCompatActivity {
    private BarChart chart;
    private ArrayList<Integer> sample;
    private ArrayList<BarEntry> amount;
    private TextView test,test2,test3,average,sum1;
    private int month,year;
    private String months[]={"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    DatabaseHandler db=new DatabaseHandler(Expensesanalysis.this);
    int sum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expensesanalysis);
        test=findViewById(R.id.test);
        test2=findViewById(R.id.test1);
        average=findViewById(R.id.avg);
        test3=findViewById(R.id.test3);
        chart=findViewById(R.id.expensechart);
        sum1=findViewById(R.id.sum);
        month= Calendar.getInstance().get(Calendar.MONTH);
        year=Calendar.getInstance().get(Calendar.YEAR);
        if(month==12){
            year=Calendar.getInstance().get(Calendar.YEAR)-1;
        }

        test.setText("EXPENSE ANALYSIS OF MONTH " + months[month-1]);
       String monthdate[]={"1/"+ month +"/" +year,"2/"+ month +"/" +year,"3/"+ month +"/" +year
       ,"4/"+ month +"/" +year,"5/"+ month +"/" +year,"6/"+ month +"/" +year
       ,"7/"+ month +"/" +year,"8/"+ month +"/" +year,"9/"+ month +"/" +year
       ,"10/"+ month +"/" +year,"11/"+ month +"/" +year,"12/"+ month +"/" +year
       ,"13/"+ month +"/" +year,"14/"+ month +"/" +year,"15/"+ month +"/" +year
       ,"16/"+ month +"/" +year,"17/"+ month +"/" +year,"18/"+ month +"/" +year
               ,"19/"+ month +"/" +year,"20/"+ month +"/" +year,"21/"+ month +"/" +year
               ,"22/"+ month +"/" +year,"23/"+ month +"/" +year,"24/"+ month +"/" +year
       ,"25/"+ month +"/" +year,"26/"+ month +"/" +year,"27/"+ month +"/" +year
       ,"28/"+ month +"/" +year,"29/"+ month +"/" +year,"30/"+ month +"/" +year};
       int count1=0;
       for(int a=0;a<monthdate.length;a++){
           count1=count1+db.getctrans(monthdate[a]);
       }
       if(count1!=0){
           sample=new ArrayList<>();
           for(int j=0;j<monthdate.length;j++)
           {
               sample.add(db.getamount(monthdate[j]));
           }
           int greatest=sample.get(0);
           int smallest=sample.get(0);
           int avg=0,count=0;
           String gdate1 = null,gdate2 = monthdate[0];
           for(int k=0;k<sample.size();k++){
               if(greatest<sample.get(k))
               {
                   greatest=sample.get(k);
                   gdate1=monthdate[k];
               }
               if(smallest!=0 && sample.get(k)!=0) {

                   if (smallest > sample.get(k)) {
                       smallest = sample.get(k);
                       gdate2 = monthdate[k];
                   }
               }
               sum=sum+sample.get(k);
               if(sample.get(k)!=0){
                   count=count+1;
               }
           }
           avg=sum/count;
           amount=new ArrayList<>();
           for(int i=0;i<monthdate.length;i++){
               DatabaseHandler db1=new DatabaseHandler(this);
               int val = db1.getamount(String.valueOf(monthdate[i]));
               int ch=i;
               amount.add(new BarEntry(ch,val));

           }
           test2.setText("Maximum Expenses: ₹" + greatest +" At " +gdate1);
           test3.setText("Minimum Expenses: ₹" +smallest+" At " +gdate2);
           average.setText("Average Expense Carried Out Per Day : ₹" + avg);
           sum1.setText("Total Expense Carried Out This Month: ₹" + sum);
           BarDataSet barDataSet=new BarDataSet(amount,"Amount");
           BarData barData=new BarData(barDataSet);
           chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(monthdate));
           chart.setData(barData);
       }
       else {
           Toast.makeText(Expensesanalysis.this,"There is no data related to last month",Toast.LENGTH_SHORT).show();
       }


    }


}