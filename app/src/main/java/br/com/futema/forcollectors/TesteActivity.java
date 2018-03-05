package br.com.futema.forcollectors;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.futema.forcollectors.model.Book;
import br.com.futema.forcollectors.view.adapter.BookAdapter;
import br.com.futema.forcollectors.view.listener.OnItemClickListener;
import br.com.futema.forcollectors.viewmodel.BookModel;

public class TesteActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView rvBoks;
    private BookAdapter adapter;
    private List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        rvBoks = (RecyclerView) findViewById(R.id.rvBooks);

        books = new ArrayList<>();

        ViewModelProviders.of(this)
                .get(BookModel.class)
                .getBooks()
                .observe(this, new Observer<List<Book>>() {
                    @Override
                    public void onChanged(@Nullable List<Book> books) {
                        adapter.setList(books);
                        rvBoks.getAdapter().notifyDataSetChanged();
                    }
                });
        rvBoks.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new BookAdapter(books, deleteClick);
        rvBoks.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // TarefaDialog dialog = new TarefaDialog();
                //dialog.show(getFragmentManager(), "CriarTarefa");
            }
        });

    }

    private OnItemClickListener deleteClick = new OnItemClickListener() {
        @Override
        public void onClick(int position) {
            //
        }
    };

}
