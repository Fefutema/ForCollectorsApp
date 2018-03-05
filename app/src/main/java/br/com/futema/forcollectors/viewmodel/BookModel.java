package br.com.futema.forcollectors.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import br.com.futema.forcollectors.model.Book;

/**
 * Created by felipe on 04/03/18.
 */

public class BookModel extends AndroidViewModel{

    private MutableLiveData<List<Book>> books = new MutableLiveData<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private String userID;

    public BookModel(Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("Livros");
        userID = firebaseAuth.getCurrentUser().getUid();
        carregaDados();

    }

    public LiveData<List<Book>> getBooks(){
        return books;
    }

    private void carregaDados() {

        Query query = dbReference.orderByChild("userId").equalTo(userID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    List<Book> bs = new ArrayList<>();
                    for(DataSnapshot book : dataSnapshot.getChildren()){
                        Book b = new Book(
                                String.valueOf(book.child("id").getValue()),
                                String.valueOf(book.child("userId").getValue()),
                                String.valueOf(book.child("titulo").getValue()),
                                String.valueOf(book.child("autor").getValue()),
                                String.valueOf(book.child("descricao").getValue()),
                                (Boolean) book.child("lido").getValue(),
                                (Boolean)book.child("favorito").getValue(),
                                (Boolean)book.child("wishList").getValue()
                        );

                        bs.add(b);
                    }
                    books.setValue(bs);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Erro", "Erro ao ler livro do firebase", databaseError.toException());
            }
        });

    }

}
