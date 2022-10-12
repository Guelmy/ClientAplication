package com.company.clientapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.company.clientapp.adapter.AdapterClient;
import com.company.clientapp.models.AddressVo;
import com.company.clientapp.models.CLientVo;
import com.company.clientapp.sqlmanager.AdminSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> data;
    ArrayList<CLientVo> clientList;
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        clientList = new ArrayList<>();
        recycler = (RecyclerView)findViewById(R.id.id_recycle);
        recycler.setLayoutManager((new LinearLayoutManager(this)));

        GetClient();

        AdapterClient adapter = new AdapterClient(clientList);
        recycler.setAdapter(adapter);
    }


    @SuppressLint("Range")
    private void GetClient(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

//        Cursor field = db.rawQuery("SELECT c.name, a.name FROM Client as c INNER JOIN Address as a on c.id = a.client_id ", null);

        Cursor field = db.rawQuery("SELECT name, id FROM Client", null);
        while (field.moveToNext()){
           try{
               String username = field.getString(field.getColumnIndex("name"));
               int userId = field.getInt(field.getColumnIndex("id"));

               Cursor fieldAdress = db.rawQuery("SELECT name FROM Address where client_id="+userId, null);
               List<AddressVo> addressVos = new ArrayList<>();
               while (fieldAdress.moveToNext()){
                   String adressName = fieldAdress.getString(field.getColumnIndex("name"));
                   addressVos.add(new AddressVo(adressName));
                }

               clientList.add(new CLientVo(username, addressVos, userId));
           }catch (Exception e){
               System.err.println(e.getMessage());
           }
        }

        db.close();
    }

    public void AddUser(View view){
        Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
        startActivity(intent);
    }
}