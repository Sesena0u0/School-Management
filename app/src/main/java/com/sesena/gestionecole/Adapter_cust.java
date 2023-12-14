package com.sesena.gestionecole;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;

import org.w3c.dom.Element;

import java.util.ArrayList;

public class Adapter_cust<E> extends ArrayAdapter<E> {
    Database db;
    private ArrayList<E> data;
    Context mcontext;
    String use;

    public Adapter_cust(Context context, String use, ArrayList<E> data){
        super(context, R.layout.list_etudiant, data);
        this.data = data;
        this.mcontext = context;
        this.use = use;
    }
    public static class Elements {

        TextView id;
        TextView nom;
        TextView nie;
        TextView matiere;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View result;
        Elements element;
        db = new Database(mcontext);

        if (convertView == null) {

            element = new Elements();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if(this.use.equals("Etudiant")) {
                convertView = inflater.inflate(R.layout.list_etudiant, parent, false);
            }else if(this.use.equals("Enseignant")) {
                convertView = inflater.inflate(R.layout.list_enseignants, parent, false);
            }else if(this.use.equals("Classe")) {
                convertView = inflater.inflate(R.layout.list_classe, parent, false);
            }else if(this.use.equals("Matiere")) {
                convertView = inflater.inflate(R.layout.list_matiere, parent, false);
            }

            element.nie = (TextView) convertView.findViewById(R.id.nie) ;
            element.nom = (TextView) convertView.findViewById(R.id.nom) ;
            element.id = (TextView) convertView.findViewById(R.id.id) ;

            result = convertView;

            convertView.setTag(element);
        } else {
            element = (Elements) convertView.getTag();
            result = convertView;
        }

        ArrayList<Integer> listID = new ArrayList<Integer>();

        if(this.use.equals("Etudiant")){
            element.nie.setText( ((Etudiant)data.get(position)).getNie() );
            element.nom.setText( ((Etudiant)data.get(position)).getNom() );
            element.id.setText( Integer.toString(((Etudiant)data.get(position)).getId()) );
        } else if (this.use.equals("Enseignant")) {
            element.nom.setText( ((Enseignant)data.get(position)).getNom());
            Cursor ma = db.getData("matiere", "*", "id = "+((Enseignant)data.get(position)).getId_matiere());
            String matiere = "";
            while (ma.moveToNext()){
                matiere = ma.getString(1);
            }
            element.matiere = (TextView) convertView.findViewById(R.id.matiere) ;
            element.matiere.setText( matiere );
        } else if (this.use.equals("Matiere")) {
            element.nom.setText( ((Matiere)data.get(position)).getNom());
        } else if (this.use.equals("Classe")) {
            element.nom.setText( ((Classe)data.get(position)).getNom());
        }

        Button delete = (Button) convertView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerts = new AlertDialog.Builder(parent.getContext());
                if(use.equals("Etudiant")){
                    alerts.setMessage("Supprimer l'Etudiant "+((Etudiant)data.get(position)).getNom()).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.deleteData("etudiant", "id = "+((Etudiant)data.get(position)).getId());
                                    Toast.makeText(mcontext, "Etudiant "+((Etudiant)data.get(position)).getNom()+" supprimer", Toast.LENGTH_SHORT).show();

                                }
                            }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    alerts.create().show();

                } else if (use.equals("Enseignant")) {

                    alerts.setMessage("Supprimer l'Enseignant "+((Enseignant)data.get(position)).getNom()).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteData("enseignant", "id = "+((Enseignant)data.get(position)).getId());
                            Toast.makeText(mcontext, "Enseignant "+((Enseignant)data.get(position)).getNom()+" supprimer", Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    alerts.create().show();

                } else if (use.equals("Matiere")) {

                    alerts.setMessage("Supprimer Matiere "+((Matiere)data.get(position)).getNom()).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteData("matiere", "id = "+((Matiere)data.get(position)).getId());
                            Toast.makeText(mcontext, "Matiere "+((Matiere)data.get(position)).getNom()+" supprimer", Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    alerts.create().show();

                } else if (use.equals("Classe")) {

                    alerts.setMessage("Supprimer Classe "+((Classe)data.get(position)).getNom()).setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteData("classe", "id = "+((Classe)data.get(position)).getId());
                            Toast.makeText(mcontext, "Classe "+((Classe)data.get(position)).getNom()+" supprimer", Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    alerts.create().show();

                }
            }
        });

        Button edit = (Button) convertView.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(use.equals("Etudiant")){
                    Intent et = new Intent(parent.getContext(), Modifier.class);
                    et.putExtra("title", "Etudiants");
                    et.putExtra("id_", Integer.toString(((Etudiant) data.get(position)).getId()) );
                    parent.getContext().startActivity(et);
                } else if (use.equals("Enseignant")) {
                    Intent et = new Intent(parent.getContext(), Modifier.class);
                    et.putExtra("title", "Enseignants");
                    et.putExtra("id_", Integer.toString(((Enseignant) data.get(position)).getId()) );
                    parent.getContext().startActivity(et);
                } else if (use.equals("Matiere")) {
                    Intent et = new Intent(parent.getContext(), Modifier.class);
                    et.putExtra("title", "Matieres");
                    et.putExtra("id_", Integer.toString(((Matiere) data.get(position)).getId()) );
                    parent.getContext().startActivity(et);
                } else if (use.equals("Classe")) {
                    Intent et = new Intent(parent.getContext(), Modifier.class);
                    et.putExtra("title", "Classe");
                    et.putExtra("id_", Integer.toString(((Classe) data.get(position)).getId()) );
                    parent.getContext().startActivity(et);
                }
            }
        });

        return convertView;
    }

}
