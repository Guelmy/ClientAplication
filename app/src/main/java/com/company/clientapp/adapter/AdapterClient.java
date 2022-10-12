package com.company.clientapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.clientapp.ListUserActivity;
import com.company.clientapp.sqlmanager.AdminSQLiteOpenHelper;
import com.company.clientapp.R;
import com.company.clientapp.UpdateUserActivity;
import com.company.clientapp.models.AddressVo;
import com.company.clientapp.models.CLientVo;

import java.util.ArrayList;
import java.util.List;

public class AdapterClient extends RecyclerView.Adapter<AdapterClient.ViewHolderClient> {
    ArrayList<CLientVo> clientList;
    List<AddressVo> addressVos;
    private Dialog alertdialog;
    int id;

    public AdapterClient(ArrayList<CLientVo> clientList) {
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public AdapterClient.ViewHolderClient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderClient(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterClient.ViewHolderClient holder, int position) {
        addressVos = clientList.get(position).getAddress();
        holder.name.setText(clientList.get(position).getName());
        String allAddress = "";
        for(AddressVo addressVo : addressVos){
            allAddress += " \n" +addressVo.getName();
        }
        holder.adddress.setText(allAddress);
        id = clientList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class ViewHolderClient extends RecyclerView.ViewHolder {
        TextView name, adddress;

        public ViewHolderClient(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.id_txt_name_user);
            adddress = (TextView)itemView.findViewById(R.id.id_txt_address_user);

            //Button Update
            itemView.findViewById(R.id.id_btn_update_user).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), UpdateUserActivity.class);
//                    Toast.makeText(itemView.getContext(), " ss"+id, Toast.LENGTH_SHORT).show();
//                    intent.putExtra("id", id);
                    intent.putExtra("id", String.valueOf(id));
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("address", adddress.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });

            //Button Delete
            itemView.findViewById(R.id.id_btn_delete_user).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialogDelete(view, name.getText().toString(), id);
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void ShowDialogDelete(View view, String name, final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        LayoutInflater li = LayoutInflater.from(view.getContext());
        view = li.inflate(R.layout.activity_delete_user, null);
        TextView username = view.findViewById(R.id.id_txt_confirm_name_user);
        username.setText("¿Eliminar al usuario ".concat(name).concat("?"));
        View finalView = view;

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                DeleteClient(String.valueOf(id), finalView);
                Toast.makeText(finalView.getContext(),"Eliminado",Toast.LENGTH_LONG).show();
            }
        })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setView(view);
        alertdialog = builder.create();
        alertdialog.show();
    }

    private void DeleteClient(String id, View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(view.getContext(), "Administration", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        if(!id.isEmpty()){
            int count = db.delete("Client", "id="+id, null);
            db.close();
            if(count == 1){
                Toast.makeText(view.getContext(), "Eliminado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), ListUserActivity.class);
                view.getContext().startActivity(intent);
            }else{
                Toast.makeText(view.getContext(), "Error al elminar", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(view.getContext(), "Volver a intentar", Toast.LENGTH_LONG).show();
        }
    }
}
