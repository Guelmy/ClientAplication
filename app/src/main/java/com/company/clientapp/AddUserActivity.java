package com.company.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.company.clientapp.models.AddressVo;
import com.company.clientapp.models.CLientVo;
import com.company.clientapp.sqlmanager.AdminSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AddUserActivity extends AppCompatActivity {
    AdminSQLiteOpenHelper admin;

    //Add edit text
    ArrayList<String> text;
    LinearLayout list;
    Context context;
    ArrayList<CLientVo> clientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);

        text = new ArrayList<>();
        list = (LinearLayout)findViewById(R.id.id_list);
        context = this;
        clientList = new ArrayList<>();
    }

    public void AddView(View view){
        View view1 = getLayoutInflater().inflate(R.layout.row_add_address,null ,false);

        list.addView(view1);
    }

    public void Save(View view){
        EditText name = (EditText)findViewById(R.id.id_et_name_update);

        List<AddressVo> addresssvo = new ArrayList<>();

        for (int i = 0; i < list.getChildCount(); i++){
            View child = list.getChildAt(i);
            EditText address = (EditText)child.findViewById(R.id.id_et_address);
            addresssvo.add(new AddressVo(address.getText().toString()));
        }

        SQLiteDatabase db = admin.getWritableDatabase();
            ContentValues record = new ContentValues();
            record.put("name", name.getText().toString());
            db.insert("Client", null, record);
            db.close();

        int lasId = GetLastId();
        SaveAddress(lasId, addresssvo);
    }

    private void SaveAddress(int ClientId, List<AddressVo> addressVos){
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues record = new ContentValues();

        for(AddressVo address : addressVos){
            record.put("name", address.getName());
            record.put("client_id", String.valueOf(ClientId));
            db.insert("Address", null, record);
        }

        db.close();

        Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), ListUserActivity.class);
        startActivity(intent);
    }

    private int GetLastId(){
        int id = 1;
        SQLiteDatabase db = admin.getWritableDatabase();

        Cursor field = db.rawQuery("SELECT id FROM Client ORDER BY id DESC LIMIt 1", null);
        if(field.moveToFirst()){
            id = Integer.parseInt(field.getString(0));
        }
        db.close();

        return id;
    }
}