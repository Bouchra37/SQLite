package ma.project.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ma.project.sqliteapp.classes.Etudiant;
import ma.project.sqliteapp.services.EtudiantService;

public class MainActivity extends AppCompatActivity {

    private EditText nom;
    private EditText prenom;
    private Button add;

    private  Button delete;

    private EditText id;
    private Button rechercher;
    private TextView res;

    void clear(){
        nom.setText("");
        prenom.setText("");
    }
    void clear2(){
        id.setText("");
        res.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EtudiantService es = new EtudiantService(this);

        nom = (EditText)findViewById(R.id.nom);
        prenom = (EditText)findViewById(R.id.prenom);
        add = (Button)findViewById(R.id.bn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                es.create(new Etudiant(nom.getText().toString(), prenom.getText().toString()));
                clear();
                Toast.makeText(MainActivity.this, "Etudiant ajouté avec succès", Toast.LENGTH_SHORT).show();

                for(Etudiant e : es.findAll()){
                    Log.d(e.getId()+"", e.getNom()+" "+e.getPrenom());
                }
            }
        });

        final EditText idToDelete = (EditText)findViewById(R.id.id);
        delete = (Button)findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int studentIdToDelete = Integer.parseInt(idToDelete.getText().toString());

                Etudiant studentToDelete = es.findById(studentIdToDelete);

                if (studentToDelete != null) {
                    es.delete(studentToDelete);
                    Toast.makeText(MainActivity.this, "Etudiant supprimé avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Suppression", "Étudiant non trouvé pour l'ID : " + studentIdToDelete);
                    Toast.makeText(MainActivity.this, "Aucun étudiant trouvé avec cet ID", Toast.LENGTH_SHORT).show();

                }
                clear2();
            }
        });

        id = (EditText)findViewById(R.id.id);
        rechercher = (Button)findViewById(R.id.load);
        res = (TextView)findViewById(R.id.res);

        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etudiant e = es.findById(Integer.parseInt(id.getText().toString()));
                if(e!=null){
                    res.setText(e.getNom()+" "+e.getPrenom());

                }else {
                    Toast.makeText(MainActivity.this, "Aucun étudiant trouvé avec cet ID", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
