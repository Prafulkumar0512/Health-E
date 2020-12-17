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

import java.util.ArrayList;
import java.util.Calendar;

public class Billanalysis extends AppCompatActivity {
    private String months[]={"Jan/"+ Calendar.getInstance().get(Calendar.YEAR),"Feb/"+ Calendar.getInstance().get(Calendar.YEAR),"Mar/"+ Calendar.getInstance().get(Calendar.YEAR),"Apr"+ Calendar.getInstance().get(Calendar.YEAR)
            ,"May/"+ Calendar.getInstance().get(Calendar.YEAR),"Jun/"+ Calendar.getInstance().get(Calendar.YEAR),"Jul/"+ Calendar.getInstance().get(Calendar.YEAR)
            ,"Aug/"+ Calendar.getInstance().get(Calendar.YEAR),"Sep/"+ Calendar.getInstance().get(Calendar.YEAR),"Oct/"+ Calendar.getInstance().get(Calendar.YEAR),
            "Nov/"+ Calendar.getInstance().get(Calendar.YEAR),"Dec/"+ Calendar.getInstance().get(Calendar.YEAR)};
    private ArrayList<BarEntry> billamount;
    private TextView title,max,min,average,total;
    private ArrayList<Integer> sample;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billanalysis);
        BarChart billchart=findViewById(R.id.billchart);
        title=findViewById(R.id.billm);
        max=findViewById(R.id.mbax);
        min=findViewById(R.id.minbill);
        average=findViewById(R.id.avgbill);
        total=findViewById(R.id.total);
        DatabaseHandler db=new DatabaseHandler(this);
        int count1=0;
        for(int a=0;a<months.length;a++){
            count1=count1+db.getcbill(months[a]);
        }
        if(count1!=0){
            sample=new ArrayList<>();
            int sum=0,count=0;
            int avg;
            String Year= String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            for(int j=0;j<months.length;j++)
            {

                sample.add(db.getbill(months[j]));
            }
            int greatest=sample.get(0);
            int smallest=sample.get(0);

            String gdate1 = null;
            String sdate = months[0];
            for(int k=0;k<sample.size();k++){
                if(greatest<sample.get(k))
                {
                    greatest=sample.get(k);
                    gdate1=months[k];
                }
                if(smallest!=0 && sample.get(k)!=0) {
                    if (smallest >sample.get(k) ) {
                        smallest = sample.get(k);
                        sdate = months[k];
                    }
                }
                sum=sum+sample.get(k);
                if(sample.get(k)!=0){
                    count=count+1;
                }

            }
            avg=sum/count;
            billamount=new ArrayList<>();
            for(int i=0;i<months.length;i++){
                DatabaseHandler db1=new DatabaseHandler(this);
                int val = db1.getbill(String.valueOf(months[i]));
                billamount.add(new BarEntry(i,val));
            }
            title.setText("Bill Analysis of Year " +Year);
            max.setText("Maximum Bill Amount: ₹" +greatest+ " of Month " + gdate1);
            min.setText("Minimum Bill Amount : ₹" +smallest+" of Month " + sdate);
            average.setText("Average Bill Amount Per Month: ₹" + avg);
            total.setText("Total Expenses Fom Bills: ₹" + sum);
            BarDataSet barDataSet=new BarDataSet(billamount,"Bill-Amount");
            BarData barData=new BarData(barDataSet);
            billchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));
            billchart.setData(barData);
        }
        else{
            Toast.makeText(Billanalysis.this,"There is no data for Analysis ",Toast.LENGTH_SHORT).show();
        }


    }
}