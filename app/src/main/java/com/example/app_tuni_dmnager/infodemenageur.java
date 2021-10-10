package com.example.app_tuni_dmnager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class infodemenageur extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infodemenageur);
        Button env_dem_devis = (Button)findViewById(R.id.env_dem_devis);
        Button text_env_msg_dem = (Button)findViewById(R.id.text_env_msg_dem);

        env_dem_devis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i=new Intent(infodemenageur.this,envoyer_demande_devis_dem.class);
                startActivity(i);

            }
        });

        text_env_msg_dem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i=new Intent(infodemenageur.this,envoyer_message.class);
                startActivity(i);

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_home:
                Intent intent = new Intent(this,accueil_tuni_demenager.class);
                this.startActivity(intent);
                break;
            case R.id.menu_profil:
                Intent intent1 = new Intent(this,profil_client.class);
                this.startActivity(intent1);
                break;
            case R.id.menu_decon:
                Intent intent2 = new Intent(this,connexion.class);
                this.startActivity(intent2);
                break;
            case R.id.menu_demenageur:
                Intent intent3=new Intent(this,devis.class);
                this.startActivity(intent3);
                break;
            default:

                return super.onOptionsItemSelected(item);
        }

        return true;

    }

}