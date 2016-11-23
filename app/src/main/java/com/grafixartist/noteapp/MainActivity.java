package com.grafixartist.noteapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab;
    Toolbar toolbar;

    NotesAdapter adapter;
    List<Evento> eventos = new ArrayList<>();

    long initialCount;

    int modifyPos = -1;

    BancoController crud;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Main", "onCreate");

        crud = new BancoController(getBaseContext());
        progressDialog = new ProgressDialog(MainActivity.this);

//        toolbar = (Toolbar)findViewById(R.id.toolbar);
//
//        toolbar.setTitle("Evento Cultural");
//        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.main_list);
        fab = (FloatingActionButton) findViewById(R.id.fab);

//        StaggeredGridLayoutManager gridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initialCount = Evento.count(Evento.class);

        if (savedInstanceState != null)
            modifyPos = savedInstanceState.getInt("modify");


        if (initialCount >= 0) {

            eventos = Evento.listAll(Evento.class);

            adapter = new NotesAdapter(MainActivity.this, eventos);
            recyclerView.setAdapter(adapter);

            if (eventos.isEmpty())
                Snackbar.make(recyclerView, "Nenhuma avaliação adicionada", Snackbar.LENGTH_LONG).show();

        }

        // tinting FAB icon
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//
//            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_add_24dp);
//            drawable = DrawableCompat.wrap(drawable);
//            DrawableCompat.setTint(drawable, Color.WHITE);
//            DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
//
//            fab.setImageDrawable(drawable);
//
//        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(i);

            }
        });


        // Handling swipe to delete





//        List<Note> notes = Note.findWithQuery(Note.class, "Select title from Note where title = ?", "%note%");
//        if (notes.size() > 0)
//            Log.d("Notes", "note: " + notes.get(0).title);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("modify", modifyPos);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        modifyPos = savedInstanceState.getInt("modify");
    }

    @Override
    protected void onResume() {
        super.onResume();

//        final long newCount = Evento.count(Evento.class);
//
//        if (newCount > initialCount) {
//            // A note is added
//            Log.d("Main", "Adding new note");
//
//            // Just load the last added note (new)
//            Evento note = Evento.last(Evento.class);
//
//            eventos.add(note);
//            adapter.notifyItemInserted((int) newCount);
//
//            initialCount = newCount;
//        }
//
//        if (modifyPos != -1) {
//            eventos.set(modifyPos, Evento.listAll(Evento.class).get(modifyPos));
//            adapter.notifyItemChanged(modifyPos);
//        }

    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateFormat(long date) {
        return new SimpleDateFormat("dd MMM yyyy").format(new Date(date));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deletar_tudo) {


            AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
            dlg.setTitle("Deseja remover todas as avaliações?");
            dlg.setMessage("Se fizer isso irá perder todas as avaliações cadastradas.");
            dlg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dlg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Excluindo...");
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            }, 2000);

                    crud.deletaTudo();
                    eventos = Evento.listAll(Evento.class);

                    adapter = new NotesAdapter(MainActivity.this, eventos);
                    recyclerView.setAdapter(adapter);
                }
            });
            dlg.show();


//            eventos = Evento.listAll(Evento.class);
//
//            adapter = new NotesAdapter(MainActivity.this, eventos);
//            recyclerView.setAdapter(adapter);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventos = Evento.listAll(Evento.class);

        int numRegistros = eventos.size();

        Toast.makeText(this, "Total de avaliações "+ numRegistros, Toast.LENGTH_LONG).show();

        adapter = new NotesAdapter(MainActivity.this, eventos);
        recyclerView.setAdapter(adapter);


        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            int i = 0;

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                //Remove swiped item from list and notify the RecyclerView

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("Deseja remover esta avaliação?");
                dlg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eventos = Evento.listAll(Evento.class);

                        adapter = new NotesAdapter(MainActivity.this, eventos);
                        recyclerView.setAdapter(adapter);
                    }
                });
                dlg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final int position = viewHolder.getAdapterPosition();
                        final Evento evento = eventos.get(viewHolder.getAdapterPosition());
                        eventos.remove(viewHolder.getAdapterPosition());
                        adapter.notifyItemRemoved(position);
                        evento.delete();
                        initialCount -= 1;

                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Excluindo...");
                        progressDialog.show();

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        progressDialog.dismiss();
                                    }
                                }, 2000);
                    }
                });
                dlg.show();
            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.SetOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.d("Main", "click");

                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                i.putExtra("isEditing", true);
                i.putExtra("nome", eventos.get(position).nome);
                i.putExtra("gostou", eventos.get(position).gostou);
                i.putExtra("telefone", eventos.get(position).telefone);
                i.putExtra("email", eventos.get(position).email);

                i.putExtra("sugestoes", eventos.get(position).sugestoes);

                modifyPos = position;

                startActivity(i);
            }
        });
    }
}
