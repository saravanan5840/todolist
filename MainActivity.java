package com.example.ex3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etuid,etpwd;
    Button blogin,bcancel,bregistration;
    SQLiteDatabase db;
    String un,pw;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etuid=(EditText)findViewById(R.id.editTextUID);
        etpwd=(EditText)findViewById(R.id.editTextPassword);
        blogin=(Button)findViewById(R.id.loginbutton);
        bcancel=(Button)findViewById(R.id.cancelbutton);
        bregistration=(Button)findViewById(R.id.buttonreg);


        try{
            db= openOrCreateDatabase("logindb", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS logintable(username VARCHAR,password VARCHAR);");
            c=db.rawQuery("SELECT * FROM logintable", null);
            //db.execSQL("delete from logintable");

        }catch(Exception e){
            Toast.makeText(getBaseContext(), "Error:"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

        if(!c.moveToFirst())
        {

            db.close();
            Intent i= new Intent(getBaseContext(),NewUser.class);
            startActivity(i);


        }else
        {
            try
            {
                  un=c.getString(0);
                  pw=c.getString(1);
            }catch (Exception e)
            {
                Toast.makeText(getBaseContext(), "Error:"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }

        blogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {   c.moveToFirst();
                un=c.getString(0);
                pw=c.getString(1);
                if( etuid.getText().toString().equals(un) && etpwd.getText().toString().equals(pw))
                {
                    Intent i= new Intent(getBaseContext(),HomePage.class);
                    startActivity(i);
                }
                else
                {
                    while(c.moveToNext())
                    {
                        un=c.getString(0);
                        pw=c.getString(1);
                        if( etuid.getText().toString().equals(un) && etpwd.getText().toString().equals(pw))
                        {
                            Intent i= new Intent(getBaseContext(),HomePage.class);
                            startActivity(i);
                            break;
                        }
                    }
                    Toast.makeText(getBaseContext(),"Invalid User Name or Password! Try Again!" ,Toast.LENGTH_LONG).show();
                }

            }
        });

        bcancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {   finishActivity(0);
                System.exit(0);

            }
        });
        bregistration.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getBaseContext(),NewUser.class);
                startActivity(i);

            }
        });
    }
}
