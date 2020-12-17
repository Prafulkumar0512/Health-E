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

public class wateranalysis extends AppCompatActivity {

    BarChart waterchart;
    TextView desc,max,min,avg,total;
    int greatest,smallest,sum=0,average=0,count=0;
    int month ,year;
    private ArrayList<Integer> sample;
    private ArrayList<BarEntry> amount;
    private String months[]={"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    DatabaseHandler db=new DatabaseHandler(wateranalysis.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wateranalysis);
        month= Calendar.getInstance().get(Calendar.MONTH);
        year=Calendar.getInstance().get(Calendar.YEAR);
        desc=findViewById(R.id.billm1);
        max=findViewById(R.id.mbax1);
        min=findViewById(R.id.minbill1);
        waterchart=findViewById(R.id.billchart1);
        total=findViewById(R.id.sum1);
        avg=findViewById(R.id.avgbill1);
        if(month==12){
            year=Calendar.getInstance().get(Calendar.YEAR)-1;
        }

        desc.setText("Water Drank Analysis OF MONTH " + months[month-1]);
        String monthdate1[]={"1/"+ month +"/" +year,"2/"+ month +"/" +year,"3/"+ month +"/" +year
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
        for(int a=0;a<monthdate1.length;a++){
            count1=count1+db.getcwater(monthdate1[a]);
        }
        if (count1!=0){
            sample=new ArrayList<>();
            for(int j=0;j<monthdate1.length;j++)
            {
                sample.add(db.getwater(monthdate1[j]));
            }
            int greatest=sample.get(0);
            int smallest=sample.get(0);
            String gdate1 = null,gdate2 = monthdate1[0];
            for(int k=0;k<sample.size();k++){
                if(greatest<sample.get(k))
                {
                    greatest=sample.get(k);
                    gdate1=monthdate1[k];
                }
                if(smallest!=0 && sample.get(k)!=0) {
                    if (smallest >sample.get(k) ) {
                        smallest = sample.get(k);
                        gdate2 = monthdate1[k];
                    }
                }
                sum=sum+sample.get(k);
                if(sample.get(k)!=0){
                    count=count+1;
                }
            }
            average=sum/count;
            amount=new ArrayList<>();
            for(int i=0;i<monthdate1.length;i++){
                DatabaseHandler db1=new DatabaseHandler(this);
                int val = db1.getwater(String.valueOf(monthdate1[i]));
                int ch=i;
                amount.add(new BarEntry(ch,val));

            }
            max.setText("Maximum water drunk in ml: " + greatest +"ml At " +gdate1);
            min.setText("Minimum water drank in ml: " +smallest+" At " +gdate2);
            avg.setText("Average water drunk in this month in ml : " + average+ "ml");
            total.setText("Total water drank in ml: " + sum+ "ml");
            BarDataSet barDataSet=new BarDataSet(amount,"Amount");
            BarData barData=new BarData(barDataSet);
            waterchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(monthdate1));
            waterchart.setData(barData);
        }
        else {
            Toast.makeText(wateranalysis.this,"There is no data related to last month",Toast.LENGTH_SHORT).show();
        }


    }
}