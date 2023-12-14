package com.sesena.gestionecole;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    TextView title;
    ListView list_view;
    String titre;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle extras = getIntent().getExtras();
        titre = extras.getString("title");

        db = new Database(this);

        open();

        title = (TextView)findViewById(R.id.titre);
        title.setText(titre);

    }

    public void open(){
        if(titre.equals("Etudiants")){
            etudiants();
        }else if(titre.equals("Enseignants")){
            enseignants();
        }else if(titre.equals("Matiere")){
            matiere();
        }else if(titre.equals("Classe")){
            classe();
        }
    }

    public void back(View view){
        this.finish();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("actualiser", "true");
        setResult(RESULT_OK, data);
        super.finish();
    }

    private void etudiants(){
        ArrayList<Etudiant> data = new ArrayList<Etudiant>();

        Cursor etudiant = db.getData("etudiant", "*", "id > 0");
        while (etudiant.moveToNext()){
            Etudiant sesena = new Etudiant(Integer.parseInt(etudiant.getString(0)), etudiant.getString(1), etudiant.getString(2), Integer.parseInt(etudiant.getString(3)), etudiant.getString(4), Integer.parseInt(etudiant.getString(5)));
            data.add(sesena);
        }

        list_view = (ListView) findViewById(R.id.list);
        Adapter_cust<Etudiant> adapter = new Adapter_cust<Etudiant>(getApplicationContext(), "Etudiant", data);
        list_view.setAdapter(adapter);

    }

    private void enseignants(){
        ArrayList<Enseignant> data = new ArrayList<Enseignant>();

        Cursor enseignant = db.getData("enseignant", "*", "id > 0");
        while (enseignant.moveToNext()){
            Enseignant sesena = new Enseignant(Integer.parseInt(enseignant.getString(0)), enseignant.getString(1), enseignant.getString(2), Integer.parseInt(enseignant.getString(3)));
            data.add(sesena);
        }

        list_view = (ListView) findViewById(R.id.list);
        Adapter_cust<Enseignant> adapter = new Adapter_cust<Enseignant>(getApplicationContext(), "Enseignant", data);
        list_view.setAdapter(adapter);

    }

    private void classe(){
        ArrayList<Classe> data = new ArrayList<Classe>();

        Cursor classe = db.getData("classe", "*", "id > 0");
        while (classe.moveToNext()){
            Classe sesena = new Classe(Integer.parseInt(classe.getString(0)), classe.getString(1) );
            data.add(sesena);
        }

        list_view = (ListView) findViewById(R.id.list);
        Adapter_cust<Classe> adapter = new Adapter_cust<Classe>(getApplicationContext(), "Classe", data);
        list_view.setAdapter(adapter);

    }
    private void matiere(){
        ArrayList<Matiere> data = new ArrayList<Matiere>();

        Cursor matiere = db.getData("matiere", "*", "id > 0");
        while (matiere.moveToNext()){
            Matiere sesena = new Matiere(Integer.parseInt(matiere.getString(0)), matiere.getString(1) );
            data.add(sesena);
        }

        list_view = (ListView) findViewById(R.id.list);
        Adapter_cust<Matiere> adapter = new Adapter_cust<Matiere>(getApplicationContext(), "Matiere", data);
        list_view.setAdapter(adapter);

    }

    public void add(View view){
        Intent add = new Intent(getApplicationContext(), AddActivity.class);
        if(titre.equals("Etudiants")){
            add.putExtra("title", "Etudiant");
        }else if(titre.equals("Enseignants")){
            add.putExtra("title", "Enseignant");
        }else if(titre.equals("Matiere")){
            add.putExtra("title", "Matiere");
        }else if(titre.equals("Classe")){
            add.putExtra("title", "Classe");
        }

        startActivityForResult(add, RESULT_FIRST_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data.getExtras().getString("actualiser").equals("true") ){
            open();
        }
    }
}