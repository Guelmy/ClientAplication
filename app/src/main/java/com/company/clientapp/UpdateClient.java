package com.company.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

public class UpdateClient extends AppCompatActivity {
    Bundle bund;
    String id;
    String name;
    String address;

    AdminSQLiteOpenHelper admin;

    //Add edit text
    ArrayList<String> text;
    LinearLayout list;
    Context context;
    ArrayList<CLientVo> clientList;
    EditText et_name;
    EditText address_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_client);

        bund = getIntent().getExtras();
        id = bund.getString("id");
        name = bund.getString("name");
        address = bund.getString("address");

//        setContentView(R.layout.activity_add_user);

        admin = new AdminSQLiteOpenHelper(this, "Administration", null, 1);

        text = new ArrayList<>();
        list = (LinearLayout)findViewById(R.id.id_list);
        context = this;
        clientList = new ArrayList<>();

        et_name = (EditText)findViewById(R.id.id_et_name_update);
        address_et = (EditText)findViewById(R.id.id_et_address);
        et_name.setText(name);
        address_et.setText(address);
    }

    public void UpdateUser(View view){
        UpdateClient(id);
    }

    private void UpdateClient(String id){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Administration",  null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues record = new ContentValues();
        record.put("name,", name);

        int count = db.update("Client", record, "id=".concat(id), null);
        db.close();

        if(count == 1){
            Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_LONG).show();
//            String[] listAddress = address.split("\n ");
//            List<AddressVo> addresssvo = new ArrayList<>();
//
//            for (int i = 0; i < list.getChildCount(); i++){
//                View child = list.getChildAt(i);
//                EditText address = (EditText)child.findViewById(R.id.id_et_address);
//                addresssvo.add(new AddressVo(address.getText().toString()));
//            }
//            UpdateAddress(id, listAddress, addresssvo);
        }else {
            Toast.makeText(getApplicationContext(), "Error al actualizar", Toast.LENGTH_LONG).show();
        }
    }

    private void UpdateAddress(String ClientId, String[] addressVos, List<AddressVo> addres){
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues record = new ContentValues();

        for(String address : addressVos){
            record.put("name", address);
            record.put("client_id", String.valueOf(ClientId));
            db.insert("Address", null, record);
        }

        for(AddressVo address : addres){
            record.put("name", address.getName());
            record.put("client_id", String.valueOf(ClientId));
            db.insert("Address", null, record);
        }


        db.close();

        Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), ListUserActivity.class);
        startActivity(intent);
    }

    public void AddView(View view){
        View view1 = getLayoutInflater().inflate(R.layout.row_add_address,null ,false);

        list.addView(view1);
    }
}