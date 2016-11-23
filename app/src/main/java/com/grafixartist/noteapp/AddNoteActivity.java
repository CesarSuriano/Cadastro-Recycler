package com.grafixartist.noteapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddNoteActivity extends AppCompatActivity {

    Toolbar toolbar;
    FloatingActionButton fab;

    EditText edtNome, edtEmail, edtTelefone, edtGostou, edtSugestoes;

    String nome, email, telefone, gostou, sugestoes;

    boolean editingNote;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        progressDialog = new ProgressDialog(AddNoteActivity.this);

//        toolbar = (Toolbar) findViewById(R.id.addnote_toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_clear_24dp);

        getSupportActionBar().setTitle("Nova avaliação");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        edtNome = (EditText) findViewById(R.id.edt_nome);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtTelefone = (EditText) findViewById(R.id.edt_telefone);
        edtGostou = (EditText) findViewById(R.id.edt_gostou);
        edtSugestoes = (EditText) findViewById(R.id.edt_sugestoes);


        fab = (FloatingActionButton) findViewById(R.id.addnote_fab);


        //  handle intent

//        editingNote = getIntent() != null;
        editingNote = getIntent().getBooleanExtra("isEditing", false);
        if (editingNote) {
            nome = getIntent().getStringExtra("nome");
            email = getIntent().getStringExtra("email");
            telefone = getIntent().getStringExtra("telefone");
            gostou = getIntent().getStringExtra("gostou");
            sugestoes = getIntent().getStringExtra("sugestoes");


            edtNome.setText(nome);
            edtEmail.setText(email);
            edtTelefone.setText(telefone);
            edtGostou.setText(gostou);
            edtSugestoes.setText(sugestoes);


        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Add note to DB

                String novoNome = edtNome.getText().toString();
                String novoEmail = edtEmail.getText().toString();
                String novoTelefone = edtTelefone.getText().toString();
                String novoGostou = edtGostou.getText().toString();
                String novoSugestao = edtSugestoes.getText().toString();


                if(novoNome.equals("") || novoGostou.equals("")){
                    if (novoNome.equals("")){
                        edtNome.setError("Preencha o nome");
                    }

                    if (novoEmail.equals("")){
                        edtGostou.setError("Preencha a avaliação");
                    }

                    Toast.makeText(AddNoteActivity.this, "Preencha os campos com asterisco(*)", Toast.LENGTH_SHORT).show();
                    return;
                }

                /**
                 * TODO: Check if note exists before saving
                 */
                if (!editingNote) {
                    Log.d("Envento", "saving");
                    Evento evento = new Evento(novoNome, novoEmail, novoTelefone, novoGostou, novoSugestao);
                    evento.save();
                } else {
                    Log.d("Evento", "updating");

//                    List<Note> notes = Note.findWithQuery(Note.class, "where title = ?", title);
                    List<Evento> eventos = Evento.find(Evento.class, "nome = ?", nome);
                    if (eventos.size() > 0) {

                        Evento evento = eventos.get(0);
                        Log.d("got note", "note: " + evento.nome);
                        evento.nome = novoNome;
                        evento.email = novoEmail;
                        evento.telefone = novoTelefone;
                        evento.gostou = novoGostou;
                        evento.sugestoes = novoSugestao;

                        evento.save();

                    }

                }

                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Salvando...");
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                finish();
                            }
                        }, 2000);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                finish(); // Finaliza a Activity atual

                break;

            default:
                break;
        }

        return true;
    }
}
