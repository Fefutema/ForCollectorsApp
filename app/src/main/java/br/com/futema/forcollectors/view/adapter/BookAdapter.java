package br.com.futema.forcollectors.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.futema.forcollectors.R;
import br.com.futema.forcollectors.model.Book;
import br.com.futema.forcollectors.view.listener.OnItemClickListener;

/**
 * Created by felipe on 04/03/18.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;

    private final OnItemClickListener listenerDelete;
    private final OnItemClickListener listenerEdit;

    public BookAdapter(List<Book> books, OnItemClickListener deleteClick, OnItemClickListener editClick) {
        this.books = books;
        this.listenerDelete = deleteClick;
        this.listenerEdit = editClick;
    }

    public void setList(List<Book> books){
        this.books = books;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_book, parent, false);

        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, final int position) {
        Book book = books.get(position);
        holder.txtTitulo.setText(book.getTitulo());
        holder.txtAutor.setText("Autor: " + book.getAutor());
        holder.txtListaDesejo.setText("Lista de desejo? - " + (book.getWishList() ? "Sim": "Não"));
        holder.txtLido.setText("Já foi lido? - " +  (book.getLido() ? "Sim": "Não"));
        holder.txtFavorito.setText("É um de seus favoritos? - " + (book.getFavorito() ? "Sim": "Não"));

        holder.btApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerDelete.onClick(position);
            }
        });

        holder.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerEdit.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtAutor, txtFavorito, txtLido, txtListaDesejo;
        ImageView btApagar, btEditar;

        public BookViewHolder(View v) {
            super(v);

            txtTitulo = v.findViewById(R.id.txtTitulo);
            txtAutor = v.findViewById(R.id.txtAutor);
            txtFavorito = v.findViewById(R.id.txtFavorito);
            txtLido = v.findViewById(R.id.txtLido);
            txtListaDesejo = v.findViewById(R.id.txtListaDesejo);
            btApagar = v.findViewById(R.id.btApagar);
            btEditar = v.findViewById(R.id.btEditar);
        }
    }

    public Book getBook(int position){
        return books.get(position);
    }

}
