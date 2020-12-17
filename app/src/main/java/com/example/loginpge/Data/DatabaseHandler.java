package com.example.loginpge.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.loginpge.R;
import com.example.loginpge.Util;
import com.example.loginpge.model.Data;
import com.example.loginpge.model.Remind;
import com.example.loginpge.model.Tablelist;
import com.example.loginpge.model.Task;
import com.example.loginpge.model.Transaction;
import com.example.loginpge.model.Waterremainder;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context context;
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.version);
        this.context=context;
    }
    public String Table1="CREATE TABLE " +  Util.TABLE_NAME + " ( " +
            Util.KEY_ID + 	" INTEGER PRIMARY KEY, " +
            Util.KEY_FNAME +" TEXT, "+
            Util.KEY_LNAME  +" TEXT, "+
            Util.KEY_EMAIL	+" TEXT, "+
            Util.KEY_DATE +" TEXT, "+
            Util.KEY_AGE +" INTEGER, "+
            Util.KEY_GENDER +" TEXT, "+
            Util.KEY_COUNTRY +" TEXT,"+
            Util.KEY_USERID +" TEXT,"+
            Util.KEY_PASCODE +" TEXT," +
            Util.KEY_ANYDISEASE1+" TEXT,"+
            Util.KEY_ANYDISEASE2+" TEXT,"+
            Util.KEY_ANYDISEASE3+" TEXT )";
    public String Table2="CREATE TABLE " + Util.TABLE_NAME1 + " ( " +
            Util.KEY_iD + " INTEGER PRIMARY KEY ," +
            Util.KEY_TITLE + " TEXT ," +
            Util.KEY_AMOUNT + " TEXT ,"+
            Util.KEY_dATE + " TEXT )";
    public String Table3="CREATE TABLE " + Util.TABLE_NAME2 + " ( " +
            Util.KEY_ID1 + " INTEGER PRIMARY KEY , " +
            Util.KEY_TASK + " TEXT , " +
            Util.KEY_DATE1 + " TEXT , " +
            Util.KEY_TIME + " TEXT )";
    public String Table4="CREATE TABLE " + Util.TABLE_NAME3 +" ( " +
            Util.KEY_ID2 + " INTEGER PRIMARY KEY , " +
            Util.KEY_TITLE1 + " TEXT ," +
            Util.KEY_AMOUNT1 + " TEXT ," +
            Util.KEY_RDATE + " TEXT ," +
            Util.KEY_DDATE + " TEXT, " +
            Util.KEY_BILLMONTH +" TEXT )";
    public String Table5="CREATE TABLE " + Util.TABLE_NAME4 + " ( " +
            Util.KEY_ID3+" INTEGER PRIMARY KEY , " +
            Util.KEY_MONTH+" TEXT, " +
            Util.KEY_TITLE2 + " TEXT, " +
            Util.KEY_AMOUNT2 + " TEXT, " +
            Util.KEY_DATE2 + " TEXT )";
    public String Table6="CREATE TABLE " + Util.TABLE_NAME5 +" ( " +
            Util.KEY_ID4+ " INTEGER PRIMARY KEY , " +
            Util.KEY_TEXT +" TEXT , " +
            Util.KEY_WTIME + " TEXT , " +
            Util.KEY_WDATE+ " TEXT , " +
            Util.KEY_WATERML + " INTEGER ) ";
    public String Table7="CREATE TABLE " + Util.TABLE_NAME6 +" ( " +
            Util.KEY_ID5+ " INTEGER PRIMARY KEY , " +
            Util.KEY_TEXT1 +" TEXT , " +
            Util.KEY_WTIME1 + " TEXT , " +
            Util.KEY_WDATE1 + " TEXT , " +
            Util.KEY_WATERML1 + " INTEGER ) ";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table1);
        db.execSQL(Table2);
        db.execSQL(Table3);
        db.execSQL(Table4);
        db.execSQL(Table5);
        db.execSQL(Table6);
        db.execSQL(Table7);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + Util.TABLE_NAME1);
        db.execSQL(" DROP TABLE IF EXISTS " + Util.TABLE_NAME2);
        db.execSQL(" DROP TABLE IF EXISTS " + Util.TABLE_NAME3);
        db.execSQL(" DROP TABLE IF EXISTS " + Util.TABLE_NAME4);
        db.execSQL(" DROP TABLE IF EXISTS " + Util.TABLE_NAME5);
        db.execSQL(" DROP TABLE IF EXISTS " + Util.TABLE_NAME6);
        onCreate(db);
    }
    public void createdate(Data data){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_FNAME,data.getFirstname());
        content.put(Util.KEY_LNAME,data.getLastname());
        content.put(Util.KEY_EMAIL,data.getEmail());
        content.put(Util.KEY_DATE,data.getDate());
        content.put(Util.KEY_AGE,data.getAge());
        content.put(Util.KEY_GENDER,data.getGender());
        content.put(Util.KEY_COUNTRY,data.getCountry());
        content.put(Util.KEY_USERID,data.getUserid());
        content.put(Util.KEY_PASCODE,data.getPascode());
        content.put(Util.KEY_ANYDISEASE1," ");
        content.put(Util.KEY_ANYDISEASE2," ");
        content.put(Util.KEY_ANYDISEASE3," ");
        db.insert(Util.TABLE_NAME,null,content);
        db.close();
    }

    public void Create(Transaction transaction){
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues trans=new ContentValues();
        trans.put(Util.KEY_TITLE,transaction.getTitle());
        trans.put(Util.KEY_AMOUNT,transaction.getAmount());
        trans.put(Util.KEY_dATE,transaction.getDate());
        db1.insert(Util.TABLE_NAME1,null,trans);
        db1.close();
    }
   public void createtask(Task task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues todo=new ContentValues();
        todo.put(Util.KEY_TASK,task.getTask());
        todo.put(Util.KEY_DATE1,task.getDate());
        todo.put(Util.KEY_TIME,task.getTime());
        db.insert(Util.TABLE_NAME2,null,todo);
        db.close();
    }

    public void createremind(Remind remind){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_TITLE1,remind.getTitle());
        content.put(Util.KEY_AMOUNT1,remind.getAmount());
        content.put(Util.KEY_RDATE,remind.getRemind_date());
        content.put(Util.KEY_DDATE,remind.getDue_date());
        content.put(Util.KEY_BILLMONTH,remind.getMonth1());
        db.insert(Util.TABLE_NAME3,null,content);
        db.close();
    }

    public void createbhistory(Tablelist tablelist){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_MONTH,tablelist.getMonth());
        content.put(Util.KEY_TITLE2,tablelist.getBilltitle());
        content.put(Util.KEY_AMOUNT2,tablelist.getAmount());
        content.put(Util.KEY_DATE2,tablelist.getDate());
        db.insert(Util.TABLE_NAME4,null,content);
        db.close();
    }

    public void createwremainder(Waterremainder waterremainder){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_TEXT,waterremainder.getText());
        content.put(Util.KEY_WTIME,waterremainder.getTime());
        content.put(Util.KEY_WDATE,waterremainder.getDate());
        content.put(Util.KEY_WATERML,waterremainder.getWaterml());
        db.insert(Util.TABLE_NAME5,null,content);
        db.close();
    }

    public void createwrecord(Waterremainder waterremainder){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_TEXT1,waterremainder.getText());
        content.put(Util.KEY_WTIME1,waterremainder.getTime());
        content.put(Util.KEY_WDATE1,waterremainder.getDate());
        content.put(Util.KEY_WATERML1,waterremainder.getWaterml());
        db.insert(Util.TABLE_NAME6,null,content);
        db.close();
    }



    public boolean checkuser(String User,String Pass){
        String command="SELECT COUNT(*) FROM " + Util.TABLE_NAME + " WHERE " + Util.KEY_USERID + "= '" + User + "' AND " + Util.KEY_PASCODE + "='"+ Pass + "'";
        SQLiteStatement statement= getReadableDatabase().compileStatement(command);
        long l=statement.simpleQueryForLong();
        statement.close();

        if(l==1)
            return true;
        else
            return false;
        
    }

    public boolean checkpass(String Pass){
        String command="SELECT COUNT(*) FROM " + Util.TABLE_NAME + " WHERE " + Util.KEY_PASCODE + "='"+ Pass + "'";
        SQLiteStatement statement= getReadableDatabase().compileStatement(command);
        long l=statement.simpleQueryForLong();
        statement.close();

        if(l==1)
            return true;
        else
            return false;

    }

    public int updatedata(Transaction transaction){
        SQLiteDatabase db1=this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(Util.KEY_TITLE,transaction.getTitle());
        value.put(Util.KEY_AMOUNT,transaction.getAmount());
        value.put(Util.KEY_dATE,transaction.getDate());
        return db1.update(Util.TABLE_NAME1,value,Util.KEY_iD + " = ?",new String[]{String.valueOf(transaction.getId())});

    }
    public int getamount(String date){
        SQLiteDatabase db=this.getWritableDatabase();
        int sum=0;
        Cursor cursor=db.rawQuery("SELECT SUM( " + Util.KEY_AMOUNT + " ) as dTotal FROM " + Util.TABLE_NAME1 + " WHERE "+ Util.KEY_dATE + " ='" + date + "'",null);
        if(cursor.moveToNext()){
            sum=cursor.getInt(cursor.getColumnIndex("dTotal"));
        }
        return sum;
    }


    public int getwater(String date){
        SQLiteDatabase db=this.getWritableDatabase();
        int sum=0;
        Cursor cursor=db.rawQuery("SELECT SUM( " + Util.KEY_WATERML1 + " ) as wTotal FROM " + Util.TABLE_NAME6 + " WHERE "+ Util.KEY_WDATE1 + " ='" + date + "'",null);
        if(cursor.moveToNext()){
            sum=cursor.getInt(cursor.getColumnIndex("wTotal"));
        }
        return sum;
    }
    public int getbill(String month){
        SQLiteDatabase db=this.getWritableDatabase();
        int sum=0;
        Cursor cursor=db.rawQuery("SELECT SUM( " + Util.KEY_AMOUNT2 + " ) as bTotal FROM " + Util.TABLE_NAME4 + " WHERE "+ Util.KEY_MONTH + " ='" + month + "'",null);
        if(cursor.moveToNext()){
            sum=cursor.getInt(cursor.getColumnIndex("bTotal"));
        }
        return sum;
    }

    public int getcbill(String month){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT " + Util.KEY_AMOUNT2 + " FROM " + Util.TABLE_NAME4 + " WHERE "+ Util.KEY_MONTH + " ='" + month + "'",null);
        return cursor.getCount();
    }

    public int getctrans(String date){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT " + Util.KEY_AMOUNT + " FROM " + Util.TABLE_NAME1 + " WHERE "+ Util.KEY_dATE + " ='" + date + "'",null);
        return cursor.getCount();
    }

    public int getcwater(String date){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT " + Util.KEY_WATERML1 + " FROM " + Util.TABLE_NAME6 + " WHERE "+ Util.KEY_WDATE1 + " ='" + date + "'",null);
        return cursor.getCount();
    }

    public int updateinfo(Data data){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_FNAME,data.getFirstname());
        content.put(Util.KEY_LNAME,data.getLastname());
        content.put(Util.KEY_EMAIL,data.getEmail());
        content.put(Util.KEY_DATE,data.getDate());
        content.put(Util.KEY_AGE,data.getAge());
        content.put(Util.KEY_COUNTRY,data.getCountry());
        content.put(Util.KEY_ANYDISEASE1,data.getAnydisease1());
        content.put(Util.KEY_ANYDISEASE2,data.getAnydisease2());
        content.put(Util.KEY_ANYDISEASE3,data.getAnydisease3());
        return db.update(Util.TABLE_NAME,content,Util.KEY_iD + " = ?",new String[]{String.valueOf(data.getId())});

    }
    public int updatetodo(Task task){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(Util.KEY_TASK,task.getTask());
        value.put(Util.KEY_DATE1,task.getDate());
        value.put(Util.KEY_TIME,task.getTime());
        return db.update(Util.TABLE_NAME2,value,Util.KEY_ID + " = ?",new String[]{String.valueOf(task.getId())});
    }

    public int updateremind(Remind remind){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_TITLE1,remind.getTitle());
        content.put(Util.KEY_AMOUNT1,remind.getAmount());
        content.put(Util.KEY_RDATE,remind.getRemind_date());
        content.put(Util.KEY_DDATE,remind.getDue_date());
        content.put(Util.KEY_BILLMONTH,remind.getMonth1());
        return db.update(Util.TABLE_NAME3,content,Util.KEY_ID2 +" = ?" ,new String[]{String.valueOf(remind.getId())});

    }
    public int updatewremaind(Waterremainder waterremainder){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_TEXT,waterremainder.getText());
        content.put(Util.KEY_WTIME,waterremainder.getTime());
        content.put(Util.KEY_WDATE,waterremainder.getDate());
        content.put(Util.KEY_WATERML,waterremainder.getWaterml());
        return db.update(Util.TABLE_NAME5,content,Util.KEY_ID4+" = ? ",new String[]{String.valueOf(waterremainder.getId())});
    }

    public void updatepass(String user,String pass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(Util.KEY_PASCODE,pass);
        db.update(Util.TABLE_NAME,content,Util.KEY_USERID+" =? ",new String[]{user});
        db.close();
    }



    public List<Data> getinfo(){
        List<Data> infolist=new ArrayList<>();
        SQLiteDatabase db1=this.getReadableDatabase();
        Cursor c1=db1.rawQuery(" SELECT * FROM " + Util.TABLE_NAME , null);
        if(c1.moveToFirst()){
            do{
                Data data1=new Data();
                data1.setId(Integer.parseInt(c1.getString(0)));
                data1.setFirstname(c1.getString(1));
                data1.setLastname(c1.getString(2));
                data1.setEmail(c1.getString(3));
                data1.setDate(c1.getString(4));
                data1.setAge(Integer.parseInt(c1.getString(5)));
                data1.setGender(c1.getString(6));
                data1.setCountry(c1.getString(7));
                data1.setUserid(c1.getString(8));
                data1.setPascode(c1.getString(9));
                data1.setAnydisease1(c1.getString(10));
                data1.setAnydisease2(c1.getString(11));
                data1.setAnydisease3(c1.getString(12));

                infolist.add(data1);
            }while (c1.moveToNext());
        }
        return infolist;
    }

   public List<Transaction> gettrans(){
        List<Transaction> translist=new ArrayList<>();
        SQLiteDatabase db1=this.getReadableDatabase();
        Cursor c1=db1.rawQuery(" SELECT * FROM " + Util.TABLE_NAME1 , null);
        if(c1.moveToFirst()){
            do{
                Transaction trans1=new Transaction();
                trans1.setId(Integer.parseInt(c1.getString(0)));
                trans1.setTitle(c1.getString(1));
                trans1.setAmount(c1.getString(2));
                trans1.setDate(c1.getString(3));

                translist.add(trans1);
            }
            while (c1.moveToNext());
        }
        return translist;
    }
    public List<Task> gettodo(){
        List<Task> todo=new ArrayList<>();
        SQLiteDatabase db1=this.getReadableDatabase();
        Cursor c1=db1.rawQuery(" SELECT * FROM " + Util.TABLE_NAME2 , null);
        if(c1.moveToFirst()){
            do{
                Task task=new Task();
                task.setId(Integer.parseInt(c1.getString(0)));
                task.setTask(c1.getString(1));
                task.setDate(c1.getString(2));
                task.setTime(c1.getString(3));
                todo.add(task);
            }
            while (c1.moveToNext());
        }
        return todo;
    }

    public List<Remind> getremind(){
        List<Remind> remind=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c1=db.rawQuery(" SELECT * FROM " + Util.TABLE_NAME3 , null);
        if(c1.moveToFirst()){
            do{
                Remind remind1=new Remind();
                remind1.setId((Integer.parseInt(c1.getString(0))));
                remind1.setTitle(c1.getString(1));
                remind1.setAmount(c1.getString(2));
                remind1.setRemind_date(c1.getString(3));
                remind1.setDue_date(c1.getString(4));
                remind1.setMonth1(c1.getString(5));
                remind.add(remind1);
            }while (c1.moveToNext());
        }
        return remind;
    }

    public List<Tablelist> gettable(){
        List<Tablelist> tablelists=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c1=db.rawQuery(" SELECT * FROM " + Util.TABLE_NAME4 , null);
        if(c1.moveToFirst()){
            do{
                Tablelist tablelist=new Tablelist();
                tablelist.setId(c1.getString(0));
                tablelist.setMonth(c1.getString(1));
                tablelist.setBilltitle(c1.getString(2));
                tablelist.setAmount(c1.getString(3));
                tablelist.setDate(c1.getString(4));
                tablelists.add(tablelist);
            }while (c1.moveToNext());
        }
        return tablelists;
    }

    public List<Waterremainder> getwremaind(){
        List<Waterremainder> waterremainders=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c1=db.rawQuery(" SELECT * FROM " + Util.TABLE_NAME5 , null);
        if(c1.moveToNext()){
            do{
                Waterremainder waterremainder=new Waterremainder();
                waterremainder.setId(Integer.parseInt(c1.getString(0)));
                waterremainder.setText(c1.getString(1));
                waterremainder.setTime(c1.getString(2));
                waterremainder.setDate(c1.getString(3));
                waterremainder.setWaterml(Integer.parseInt(c1.getString(4)));
                waterremainders.add(waterremainder);
            }while (c1.moveToNext());
        }
        return waterremainders;

    }

    public int Count(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(" SELECT * FROM " + Util.TABLE_NAME1,null);

        return cursor.getCount();
    }
    public int Counttask(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + Util.TABLE_NAME2,null);
        return cursor.getCount();
    }
    public int Countremind(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + Util.TABLE_NAME3,null);
        return cursor.getCount();
    }
    public int Counttable(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + Util.TABLE_NAME4,null);
        return cursor.getCount();
    }
    public int Countwremind(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + Util.TABLE_NAME5,null);
        return cursor.getCount();
    }
    public int  Countinfo(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM " + Util.TABLE_NAME,null);
        return cursor.getCount();
    }


    public void Deletedata(Transaction transaction){
        SQLiteDatabase db=this.getWritableDatabase();
        long l=db.delete(Util.TABLE_NAME1,Util.KEY_iD + "=?", new String[]{String.valueOf(transaction.getId())});
        if (l==-1){

            Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, R.string.delete,Toast.LENGTH_SHORT).show();
        }
    }

    public void Deletedata1(int p){
        SQLiteDatabase db=this.getWritableDatabase();
        long l=db.delete(Util.TABLE_NAME1,Util.KEY_iD + "=?", new String[]{String.valueOf(p)});
        if (l==-1){

            Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, R.string.delete,Toast.LENGTH_SHORT).show();
        }
    }
    public void Deletetodo(Task task)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long l=db.delete(Util.TABLE_NAME2,Util.KEY_ID1 + "=?", new String[]{String.valueOf(task.getId())});
        if (l==-1){

            Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, R.string.delete1,Toast.LENGTH_SHORT).show();
        }
    }

    public void Deletetodo1(int position)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long l=db.delete(Util.TABLE_NAME2,Util.KEY_ID1 + "=?", new String[]{String.valueOf(position)});
        if (l==-1){

            Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Task Completed",Toast.LENGTH_SHORT).show();
        }
    }

    public void Deleteremind1(int position)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long l=db.delete(Util.TABLE_NAME3,Util.KEY_ID2 + "=?", new String[]{String.valueOf(position)});
        if (l==-1){

            Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Bill Paid",Toast.LENGTH_SHORT).show();
        }
    }

    public void Deleteremind(Remind remind)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long l=db.delete(Util.TABLE_NAME3,Util.KEY_ID2 + "=?", new String[]{String.valueOf(remind.getId())});
        if (l==-1){

            Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, R.string.delete1,Toast.LENGTH_SHORT).show();
        }
    }

    public void Deletewremind1(int position)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long l=db.delete(Util.TABLE_NAME5,Util.KEY_ID4 + "=?", new String[]{String.valueOf(position)});
        if (l==-1){

            Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Good Job",Toast.LENGTH_SHORT).show();
        }
    }

    public void Deletewremind(Waterremainder waterremainder)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        long l=db.delete(Util.TABLE_NAME5,Util.KEY_ID4 + "=?", new String[]{String.valueOf(waterremainder.getId())});
        if (l==-1){

            Toast.makeText(context,"Failed to Delete",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Good job",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteall(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Util.TABLE_NAME1);

    }
    public void deletetodo(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Util.TABLE_NAME2);
    }
    public void deleteinfo(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Util.TABLE_NAME);
    }

    public void deleteremind(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Util.TABLE_NAME3);
    }
    public void deletetable(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Util.TABLE_NAME4);
    }
    public void deletewremind(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Util.TABLE_NAME5);
    }
    public void deleterecord(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + Util.TABLE_NAME6);
    }

    public int totalamount(){
        SQLiteDatabase db=this.getReadableDatabase();
        int sum=0;
        Cursor cursor=db.rawQuery("SELECT SUM ( " + Util.KEY_AMOUNT + " ) as Total FROM " + Util.TABLE_NAME1,null);
        if(cursor.moveToNext()){
            sum=cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }

}
