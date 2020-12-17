package com.example.loginpge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loginpge.Data.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Loginpge extends AppCompatActivity {
    private Button button;
    private EditText logid;
    private EditText logpss;
    private DatabaseHandler db;
    private Button change;
    void configureBackButton() {
        ImageView backButton = (ImageView) findViewById(R.id.back_button_login);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        configureBackButton();
        db=new DatabaseHandler(Loginpge.this);
        button=findViewById(R.id.btn);
        logid=findViewById(R.id.edt1);
        logpss=findViewById(R.id.edt2);
        change=findViewById(R.id.changepass);

       change.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         final EditText user, newpass;
                                         Button resetpass;
                                         final BottomSheetDialog bottomSheet = new BottomSheetDialog(Loginpge.this);
                                         bottomSheet.setContentView(R.layout.bottomdialog5);
                                         bottomSheet.setCanceledOnTouchOutside(true);
                                         user = bottomSheet.findViewById(R.id.resetuser);
                                         newpass = bottomSheet.findViewById(R.id.newpass);
                                         resetpass = bottomSheet.findViewById(R.id.resetnew);
                                         resetpass.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 String User = user.getText().toString();
                                                 String Pass = newpass.getText().toString();
                                                 if (user.length() == 0) {
                                                     user.setError("Field Required");
                                                 }
                                                 if (newpass.length() == 0) {
                                                     newpass.setError("Field Required");
                                                 }
                                                 if (newpass.length() != 0 && user.length() != 0) {
                                                     db.updatepass(User, Pass);
                                                     Toast.makeText(Loginpge.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                                                     bottomSheet.dismiss();
                                                 }

                                             }
                                         });
                                         bottomSheet.show();
                                     }
                                 });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid=logid.getText().toString();
                String password=logpss.getText().toString();
                if(userid.length()==0)
                {
                    logid.setError("Field Required");
                }
                if(password.length()==0)
                {
                    logpss.setError("Field Required");
                }
                if(db.checkuser(userid,password)){
                    
                    Intent connect=new Intent(Loginpge.this, Dashboard.class);
                    connect.putExtra("id",userid);
                    startActivity(connect);
                    Toast.makeText(Loginpge.this, R.string.Login,Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(Loginpge.this, R.string.invalid,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}