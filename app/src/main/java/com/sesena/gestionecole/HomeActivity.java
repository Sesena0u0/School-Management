package com.sesena.gestionecole;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
public class HomeActivity extends AppCompatActivity {

    Database db;
    TextView et;
    TextView en;
    TextView cl;
    TextView ma;

    ImageView img_classe, img_etudiant, img_professor, img_matiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        img_classe = findViewById(R.id.img_classe);
        Glide.with(this)
                .asGif()
                .load(R.mipmap.school)
                .into(img_classe);

        img_etudiant = findViewById(R.id.img_etudiant);
        Glide.with(this)
                .asGif()
                .load(R.mipmap.student)
                .into(img_etudiant);

        img_professor = findViewById(R.id.img_enseignant);
        Glide.with(this)
                .asGif()
                .load(R.mipmap.professor)
                .into(img_professor);

        img_matiere = findViewById(R.id.img_matiere);
        Glide.with(this)
                .asGif()
                .load(R.mipmap.book)
                .into(img_matiere);

        setnbrall();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data.getExtras().getString("actualiser").equals("true") ){
            setnbrall();
        }
    }

    private void setnbrall(){
        db = new Database(this);

        Cursor etudiants = db.getData("etudiant", "*", "id > 0");
        Cursor enseignants = db.getData("enseignant", "*", "id > 0");
        Cursor classe = db.getData("classe", "*", "id > 0");
        Cursor matieres = db.getData("matiere", "*", "id > 0");

        et = (TextView) findViewById(R.id.nbr_etudiants);
        en = (TextView) findViewById(R.id.nbr_enseignants);
        cl = (TextView) findViewById(R.id.nbr_classe);
        ma = (TextView) findViewById(R.id.nbr_matieres);

        et.setText(Integer.toString( etudiants.getCount() ));
        en.setText(Integer.toString( enseignants.getCount() ));
        cl.setText(Integer.toString( classe.getCount() ));
        ma.setText(Integer.toString( matieres.getCount() ));
    }

    public void open_etudiants(View view){
        Intent etudiants = new Intent(getApplicationContext(), ListActivity.class);
        etudiants.putExtra("title", "Etudiants");
        startActivityForResult(etudiants, RESULT_FIRST_USER);
    }

    public void open_classe(View view){
        Intent classe = new Intent(getApplicationContext(), ListActivity.class);
        classe.putExtra("title", "Classe");
        startActivityForResult(classe, RESULT_FIRST_USER);
    }

    public void open_enseignants(View view){
        Intent enseignants = new Intent(getApplicationContext(), ListActivity.class);
        enseignants.putExtra("title", "Enseignants");
        startActivityForResult(enseignants, RESULT_FIRST_USER);
    }

    public void open_matiere(View view){
        Intent matiere = new Intent(getApplicationContext(), ListActivity.class);
        matiere.putExtra("title", "Matiere");
        startActivityForResult(matiere, RESULT_FIRST_USER);
    }

}