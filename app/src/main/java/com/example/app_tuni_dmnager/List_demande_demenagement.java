package com.example.app_tuni_dmnager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_tuni_dmnager.Adapter.Demande_demenagement_Adapter;
import com.example.app_tuni_dmnager.BD.MyDatabaseHelper;
import com.example.app_tuni_dmnager.Model.DEMANDE_DEVIS;
import com.example.app_tuni_dmnager.Model.Demande_Demenagement;
import com.example.app_tuni_dmnager.Model.Devis;

public class List_demande_demenagement extends AppCompatActivity {
    MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_demande_demenagement);

        ListView listdemandedemenagement = (ListView) findViewById(R.id.dem);
        db = new MyDatabaseHelper(List_demande_demenagement.this);
        db = MyDatabaseHelper.instanceOfDatabase(this);
        int clientid= getSharedPreferences("id",MODE_PRIVATE).getInt("id1",0);
        db.demandedemenagementListArray(clientid);

        Demande_demenagement_Adapter demandedemAdapter = new Demande_demenagement_Adapter(getApplicationContext(), Demande_Demenagement.DemandeDemenagementArrayList);
        listdemandedemenagement.setAdapter(demandedemAdapter);

        listdemandedemenagement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(List_demande_demenagement.this);
                builder.setMessage("Are you sure to delete ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   Demande_Demenagement dv = Demande_Demenagement.DemandeDemenagementArrayList.get(position);
                        int sid = dv.getId();
                        int result = db.deletedemandedemanagement(sid);


                            Toast.makeText(List_demande_demenagement.this, "Demande demenagement deleted", Toast.LENGTH_SHORT).show();
                            Demande_Demenagement.DemandeDemenagementArrayList.remove(dv);
                            demandedemAdapter.notifyDataSetChanged();

                    }
                });

                builder.setNegativeButton("No", null);
                builder.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_home:
                Intent intent = new Intent(this, accueil_tuni_demenager.class);
                this.startActivity(intent);
                finish();
                return true;
            case R.id.menu_profil:
                Intent intent1 = new Intent(this, profil_client.class);
                this.startActivity(intent1);
                finish();
                return true;
            case R.id.menu_decon:
                Intent intent2 = new Intent(this, connexion.class);
                this.startActivity(intent2);
                finish();
                return true;
            case R.id.menu_demande_devis:
                Intent intent3 = new Intent(this, liste_demande_devis_demenagement.class);
                this.startActivity(intent3);
                finish();
                return true;
            case R.id.menu_message:
                Intent intent4 = new Intent(this, listmessage.class);
                this.startActivity(intent4);
                finish();
                return true;
            case R.id.menu_demandedem:
                Intent intent6 = new Intent(this, List_demande_demenagement.class);
                this.startActivity(intent6);
                finish();
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }


    }}
