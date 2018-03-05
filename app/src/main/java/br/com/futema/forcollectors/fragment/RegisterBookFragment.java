package br.com.futema.forcollectors.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.futema.forcollectors.CadastroUsuarioActivity;
import br.com.futema.forcollectors.LoginActivity;
import br.com.futema.forcollectors.MainActivity;
import br.com.futema.forcollectors.R;
import br.com.futema.forcollectors.model.Book;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterBookFragment extends Fragment {

    private EditText etTitulo, etAutor, etDescricao;
    private CheckBox chkLido, chkQuero, chkFavorito;
    private Button btnSalvar;
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private FirebaseAuth auth;
    private String userID;
    private String bookID = null;

    public RegisterBookFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        dbReference = database.getReference("Livros");

        auth = FirebaseAuth.getInstance();
        userID = auth.getCurrentUser().getUid();

        Bundle data = getActivity().getIntent().getExtras();

        Book bookToEdit = null;
        if(data != null){
            bookToEdit = data.getParcelable("obj");
        }

        View view = inflater.inflate(R.layout.fragment_register_book, container, false);

        etTitulo = view.findViewById(R.id.etTitulo);
        etAutor = view.findViewById(R.id.etAutor);
        etDescricao = view.findViewById(R.id.etDescricao);
        chkLido = view.findViewById(R.id.chkLido);
        chkQuero = view.findViewById(R.id.chkQuero);
        chkFavorito = view.findViewById(R.id.chkFavorito);
        btnSalvar = view.findViewById(R.id.btnSalvar);

        if(bookToEdit != null){
            etTitulo.setText(bookToEdit.getTitulo());
            etAutor.setText(bookToEdit.getAutor());
            etDescricao.setText(bookToEdit.getDescricao());
            chkLido.setChecked(bookToEdit.getLido());
            chkQuero.setChecked(bookToEdit.getWishList());
            chkFavorito.setChecked(bookToEdit.getFavorito());
            bookID = bookToEdit.getId();
        }


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = etTitulo.getText().toString();
                String autor = etAutor.getText().toString();
                String descricao = etDescricao.getText().toString();

                if(titulo == null || titulo.isEmpty()){
                    Toast.makeText(getActivity(), "O titulo deve ser preenchido", Toast.LENGTH_SHORT).show();
                }else if(autor == null || autor.isEmpty()){
                    Toast.makeText(getActivity(), "O autor deve ser preenchido", Toast.LENGTH_SHORT).show();
                }else if(descricao == null || descricao.isEmpty()){
                    Toast.makeText(getActivity(), "A descrição deve ser preenchida", Toast.LENGTH_SHORT).show();
                }else {
                    String id = null;
                    if(bookID != null){
                        id = bookID;
                    }else {
                        id = dbReference.push().getKey();
                    }


                    Book livro = new Book();
                    livro.setId(id);
                    livro.setUserId(userID);
                    livro.setTitulo(etTitulo.getText().toString());
                    livro.setAutor(etAutor.getText().toString());
                    livro.setDescricao(etDescricao.getText().toString());
                    livro.setLido(chkLido.isChecked());
                    livro.setFavorito(chkFavorito.isChecked());
                    livro.setWishList(chkQuero.isChecked());

                    dbReference.child(id).setValue(livro);

                    Toast.makeText(getActivity(), "Livro salvo com sucesso", Toast.LENGTH_SHORT).show();

                    clearFields();
                    changeFragment(new ListFragment());
                }

            }
        });

        return view;
    }

    private void clearFields(){
        etTitulo.setText("");
        etAutor.setText("");
        etDescricao.setText("");
        chkLido.setChecked(false);
        chkQuero.setChecked(false);
        chkFavorito.setChecked(false);
    }

    private void changeFragment(Fragment fragment){
        Intent i = new Intent(getActivity(), MainActivity.class);
        i.putExtra("fragment", "listBooks");
        startActivity(i);
        getActivity().finish();

    }

}
