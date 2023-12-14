package com.sesena.gestionecole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    TextView title;
    String titre;
    FrameLayout frame_form;
    Spinner spinner;
    Database db;
    TextView tnom;
    TextView tprenom;
    TextView tage;
    TextView tnie;
    Spinner tclasse;
    int theid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = new Database(this);

        Bundle extras = getIntent().getExtras();
        titre = extras.getString("title");

        title = (TextView)findViewById(R.id.titre2);
        title.setText(titre);

        frame_form = (FrameLayout) findViewById(R.id.frame_form);
        View form = null;

        if(titre.equals("Etudiant")){
            form = getLayoutInflater().inflate(R.layout.add_etudiant, frame_form, false);
            frame_form.addView(form);

            tnom = (TextView) findViewById(R.id.editTextText2);
            tprenom = (TextView) findViewById(R.id.editTextText);
            tage = (TextView) findViewById(R.id.editTextText3);
            tnie = (TextView) findViewById(R.id.editTextText4);
            tclasse = (Spinner) findViewById(R.id.spinner2);

            ArrayList<String> list = new ArrayList<String>();
            ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
            ArrayList<Integer> idarray = new ArrayList<>();
            Cursor classe = db.getData("classe", "*", "id > 0");

            ArrayList<Classe> clas = new ArrayList<>();
            while (classe.moveToNext()){
                idarray.add(Integer.parseInt(classe.getString(0)));
                array.add(classe.getString(1));
            }

            tclasse.setAdapter(array);

            tclasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    theid = idarray.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    theid = 0;
                }
            });

            Button btn = (Button) findViewById(R.id.button2);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom;
                    String prenom;
                    String nie;
                    int age;
                    if ( !tage.getText().toString().trim().equals("") && !tnie.getText().toString().trim().equals("") && !tprenom.getText().toString().trim().equals("") && !tage.getText().toString().trim().equals("") && !tnom.getText().toString().trim().equals("") ){
                        nie = tnie.getText().toString();
                        nom = tnom.getText().toString();
                        prenom = tprenom.getText().toString();
                        age = Integer.parseInt(tage.getText().toString());

                    }else{
                        Toast.makeText(AddActivity.this, "Veuillez completer tout les champs", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int classe = theid;

                    Cursor etudiant = db.getData("etudiant", "*", "nom = '"+nom+"' and prenom = '"+prenom+"' and age = "+age+"");
                    if(etudiant.getCount() > 0){
                        Toast.makeText(AddActivity.this, "Cette Etudiant existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Cursor etudiant2 = db.getData("etudiant", "*", "nie = '"+nie+"'");
                    if(etudiant2.getCount() > 0){
                        Toast.makeText(AddActivity.this, "Cette NIE existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("nom", nom);
                    cv.put("prenom", prenom);
                    cv.put("age", age);
                    cv.put("nie", nie);
                    cv.put("id_classe", classe);
                    db.insertData("etudiant", cv);

                    Toast.makeText(AddActivity.this, "Insertion reussite", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

        }else if(titre.equals("Enseignant")){
            form = getLayoutInflater().inflate(R.layout.add_enseignant, frame_form, false);
            frame_form.addView(form);

            tnom = (TextView) findViewById(R.id.editTextText2);
            tprenom = (TextView) findViewById(R.id.editTextText);

            ArrayList<String> list = new ArrayList<String>();
            ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
            spinner = (Spinner) findViewById(R.id.spinner);

            ArrayList<Integer> arrayID = new ArrayList<>();
            Cursor matiere = db.getData("matiere", "*", "id > 0");
            while (matiere.moveToNext()){
                arrayID.add(Integer.parseInt(matiere.getString(0)));
                array.add(matiere.getString(1));
            }

            spinner.setAdapter(array);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    theid = arrayID.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    theid = 0;
                }
            });

            Button btn = (Button) findViewById(R.id.button2);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom;
                    String prenom;
                    if ( !tprenom.getText().toString().trim().equals("") && !tnom.getText().toString().trim().equals("") ){
                        nom = tnom.getText().toString();
                        prenom = tprenom.getText().toString();

                    }else{
                        Toast.makeText(AddActivity.this, "Veuillez completer tout les champs", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Cursor etudiant = db.getData("enseignant", "*", "nom = '"+nom+"' and prenom = '"+prenom+"'");
                    if(etudiant.getCount() > 0){
                        Toast.makeText(AddActivity.this, "Cette Enseignant existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("nom", nom);
                    cv.put("prenom", prenom);
                    cv.put("id_matiere", theid);
                    db.insertData("enseignant", cv);

                    Toast.makeText(AddActivity.this, "Insertion reussite", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

        }else if(titre.equals("Matiere")){
            form = getLayoutInflater().inflate(R.layout.add_matiere, frame_form, false);
            frame_form.addView(form);

            tnom = (EditText)findViewById(R.id.editTextText2);

            Button btn = (Button) findViewById(R.id.button2);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom = tnom.getText().toString();
                    Cursor matiere = db.getData("matiere", "*", "nom = '"+nom+"'");
                    if (nom.trim().equals("")){
                        Toast.makeText(AddActivity.this, "Veuillez completer tout les champs", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(matiere.getCount() > 0){
                        Toast.makeText(AddActivity.this, "Cette Matière existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("nom", nom);
                    db.insertData("matiere", cv);

                    Toast.makeText(AddActivity.this, "Insertion reussite", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });

        }else if(titre.equals("Classe")){
            form = getLayoutInflater().inflate(R.layout.add_classe, frame_form, false);
            frame_form.addView(form);

            tnom = (EditText) findViewById(R.id.editTextText2);
            Button btn = (Button) findViewById(R.id.button2);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nom = tnom.getText().toString();
                    if (nom.trim().equals("")){
                        Toast.makeText(AddActivity.this, "Veuillez completer tout les champs", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("nom", nom);
                    db.insertData("Classe", cv);

                    Toast.makeText(AddActivity.this, "Classe ajouter avec success", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
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
}