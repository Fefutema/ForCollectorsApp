package br.com.futema.forcollectors.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.futema.forcollectors.CadastroUsuarioActivity;
import br.com.futema.forcollectors.MainActivity;
import br.com.futema.forcollectors.R;
import br.com.futema.forcollectors.model.Book;
import br.com.futema.forcollectors.view.adapter.BookAdapter;
import br.com.futema.forcollectors.view.listener.OnItemClickListener;
import br.com.futema.forcollectors.viewmodel.BookModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private FloatingActionButton fab;
    private RecyclerView rvBooks;
    private BookAdapter adapter;
    private List<Book> books;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbReference = database.getReference("Livros");

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        fab = v.findViewById(R.id.fab);
        rvBooks = v.findViewById(R.id.rvBooks);
        books = new ArrayList<>();

        ViewModelProviders.of(this)
                .get(BookModel.class)
                .getBooks()
                .observe(this, new Observer<List<Book>>() {
                    @Override
                    public void onChanged(@Nullable List<Book> books) {
                        adapter.setList(books);
                        rvBooks.getAdapter().notifyDataSetChanged();
                    }
                });

        rvBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(books, deleteClick, editClick);
        rvBooks.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new RegisterBookFragment());
            }
        });

        return v;
    }

    private void changeFragment(Fragment fragment){
        Intent i = new Intent(getActivity(), MainActivity.class);
        i.putExtra("fragment", "registerBook");
        startActivity(i);
        getActivity().finish();

    }

    private OnItemClickListener deleteClick = new OnItemClickListener() {
        @Override
        public void onClick(int position) {
            Query query = dbReference.orderByChild("id").equalTo(adapter.getBook(position).getId());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        data.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    };

    private OnItemClickListener editClick = new OnItemClickListener() {
        @Override
        public void onClick(int position) {
            Intent i = new Intent(getActivity(), MainActivity.class);
            i.putExtra("fragment", "registerBook");
            i.putExtra("obj", adapter.getBook(position));
            startActivity(i);
            getActivity().finish();
        }
    };

}
