package com.sesena.gestionecole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import java.util.zip.Inflater;

public class Modifier extends AppCompatActivity {

    String titre;
    TextView title;
    FrameLayout frame;
    int id;
    EditText nom;
    EditText prenom;
    EditText age;
    EditText nie;
    Spinner classe, matiere;
    int theid;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        Bundle extras = getIntent().getExtras();
        titre = extras.getString("title");
        id = Integer.parseInt( extras.getString("id_") );
        db = new Database(this);

        title = (TextView)findViewById(R.id.titre3);
        title.setText(titre);
        frame = (FrameLayout) findViewById(R.id.frame);

        LayoutInflater inflater = LayoutInflater.from(this);;

        if(titre.equals("Etudiants")){
            View form = inflater.inflate(R.layout.modifier_etudiant, frame, false);
            Cursor data = db.getData("etudiant", "*", "id = "+id);
            nom = (EditText) form.findViewById(R.id.editTextText2);
            prenom = (EditText) form.findViewById(R.id.editTextText);
            age = (EditText) form.findViewById(R.id.editTextText3);
            nie = (EditText) form.findViewById(R.id.editTextText4);
            classe = (Spinner) form.findViewById(R.id.spinner2);

            Cursor spinner = db.getData("classe", "*", "id > 0");
            ArrayList<String> list = new ArrayList<String>();

            while(spinner.moveToNext()){
                list.add(spinner.getString(1));
            }
            ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
            classe.setAdapter(array);
            while (data.moveToNext()){
                nom.setText(data.getString(1));
                prenom.setText(data.getString(2));
                age.setText(data.getString(3));
                nie.setText(data.getString(4));
            }

            ArrayList<Integer> idclasse = new ArrayList<>();
            Cursor iclasse = db.getData("classe", "*", "id > 0");
            while (iclasse.moveToNext()){
                idclasse.add(Integer.parseInt(iclasse.getString(0)));
            }

            classe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    theid = idclasse.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    theid = 0;
                }
            });

            Button modifier = (Button) form.findViewById(R.id.button2);
            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String snom = nom.getText().toString();
                    String sprenom = prenom.getText().toString();
                    String sage = age.getText().toString();
                    String snie = nie.getText().toString();

                    Cursor etudiant = db.getData("etudiant", "*", "nom = '"+snom+"' and prenom = '"+sprenom+"' and age = "+sage+" and id != "+id);
                    if(etudiant.getCount() > 0){
                        Toast.makeText(Modifier.this, "Cette Etudiant existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Cursor etudiant2 = db.getData("etudiant", "*", "nie = '"+snie+"' and id != "+id);
                    if(etudiant2.getCount() > 0){
                        Toast.makeText(Modifier.this, "Cette NIE existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("nom", snom);
                    cv.put("prenom", sprenom);
                    cv.put("age", sage);
                    cv.put("nie", snie);
                    cv.put("id_classe", theid);

                    db.updateData("etudiant", cv, "id = "+id);
                    Toast.makeText(Modifier.this, "Modification terminer", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

            frame.addView(form);

        }else if(titre.equals("Enseignants")){
            View form = inflater.inflate(R.layout.modifier_enseignant, frame, false);
            Cursor data = db.getData("enseignant", "*", "id = "+id);
            nom = (EditText) form.findViewById(R.id.editTextText2);
            prenom = (EditText) form.findViewById(R.id.editTextText);
            matiere = (Spinner) form.findViewById(R.id.spinner);

            Cursor spinner = db.getData("matiere", "*", "id > 0");
            ArrayList<String> list = new ArrayList<String>();

            while(spinner.moveToNext()){
                list.add(spinner.getString(1));
            }
            ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
            matiere.setAdapter(array);
            while (data.moveToNext()){
                nom.setText(data.getString(1));
                prenom.setText(data.getString(2));
            }

            ArrayList<Integer> idmat = new ArrayList<>();
            Cursor imat = db.getData("matiere", "*", "id > 0");
            while (imat.moveToNext()){
                idmat.add(Integer.parseInt(imat.getString(0)));
            }

            matiere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    theid = idmat.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    theid = 0;
                }
            });

            Button modifier = (Button) form.findViewById(R.id.button2);
            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String snom = nom.getText().toString();
                    String sprenom = prenom.getText().toString();


                    Cursor en = db.getData("enseignant", "*", "nom = '"+snom+"' and prenom = '"+sprenom+"' and id != "+id);
                    if(en.getCount() > 0){
                        Toast.makeText(Modifier.this, "Cette Enseignant existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("nom", snom);
                    cv.put("prenom", sprenom);
                    cv.put("id_matiere", theid);

                    db.updateData("enseignant", cv, "id = "+id);
                    Toast.makeText(Modifier.this, "Modification terminer", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

            frame.addView(form);

        }else if(titre.equals("Classe")){
            View form = inflater.inflate(R.layout.modifier_classe, frame, false);
            Cursor data = db.getData("classe", "*", "id = "+id);
            nom = (EditText) form.findViewById(R.id.editTextText2);

            while (data.moveToNext()){
                nom.setText(data.getString(1));
            }

            Button modifier = (Button) form.findViewById(R.id.button2);
            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String snom = nom.getText().toString();

                    Cursor en = db.getData("classe", "*", "nom = '"+snom+"' and id != "+id);
                    if(en.getCount() > 0){
                        Toast.makeText(Modifier.this, "Cette Classe existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("nom", snom);

                    db.updateData("classe", cv, "id = "+id);
                    Toast.makeText(Modifier.this, "Modification terminer", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

            frame.addView(form);

        }else if(titre.equals("Matieres")){
            View form = inflater.inflate(R.layout.modifier_matiere, frame, false);

            Cursor data = db.getData("matiere", "*", "id = "+id);
            nom = (EditText) form.findViewById(R.id.editTextText2);

            while (data.moveToNext()){
                nom.setText(data.getString(1));
            }

            Button modifier = (Button) form.findViewById(R.id.button2);
            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String snom = nom.getText().toString();

                    Cursor en = db.getData("matiere", "*", "nom = '"+snom+"' and id != "+id);
                    if(en.getCount() > 0){
                        Toast.makeText(Modifier.this, "Cette Matiere existe déjà", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ContentValues cv = new ContentValues();
                    cv.put("nom", snom);

                    db.updateData("matiere", cv, "id = "+id);
                    Toast.makeText(Modifier.this, "Modification terminer", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

            frame.addView(form);

        }
    }

    public void back(View view){ finish(); }
}