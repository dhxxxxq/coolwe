package com.example.a17630.mythread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class Main3Activity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem menuItem){

        switch (menuItem.getItemId()){
            case R.id.backup:
                Toast.makeText(this,"You chicked Backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this,"You chicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.always:
                Toast.makeText(this,"You chicked always",Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;
        }
        return true;
    }
}
