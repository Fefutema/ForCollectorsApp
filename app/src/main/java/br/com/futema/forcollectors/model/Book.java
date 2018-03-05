package br.com.futema.forcollectors.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Book implements Parcelable{

    private String id;

    private String userId;

    private String titulo;

    private String autor;

    private String descricao;

    private Boolean isLido;

    private Boolean isFavorito;

    private Boolean isWishList;

    public Book(){

    }

    public Book(String id, String userId, String titulo, String autor, String descricao, Boolean isLido, Boolean isFavorito, Boolean isWishList) {
        this.id = id;
        this.userId = userId;
        this.titulo = titulo;
        this.autor = autor;
        this.descricao = descricao;
        this.isLido = isLido;
        this.isFavorito = isFavorito;
        this.isWishList = isWishList;
    }


    protected Book(Parcel in) {
        id = in.readString();
        userId = in.readString();
        titulo = in.readString();
        autor = in.readString();
        descricao = in.readString();
        byte tmpIsLido = in.readByte();
        isLido = tmpIsLido == 0 ? null : tmpIsLido == 1;
        byte tmpIsFavorito = in.readByte();
        isFavorito = tmpIsFavorito == 0 ? null : tmpIsFavorito == 1;
        byte tmpIsWishList = in.readByte();
        isWishList = tmpIsWishList == 0 ? null : tmpIsWishList == 1;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getLido() {
        return isLido;
    }

    public void setLido(Boolean lido) {
        isLido = lido;
    }

    public Boolean getFavorito() {
        return isFavorito;
    }

    public void setFavorito(Boolean favorito) {
        isFavorito = favorito;
    }

    public Boolean getWishList() {
        return isWishList;
    }

    public void setWishList(Boolean wishList) {
        isWishList = wishList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(userId);
        parcel.writeString(titulo);
        parcel.writeString(autor);
        parcel.writeString(descricao);
        parcel.writeByte((byte) (isLido == null ? 0 : isLido ? 1 : 2));
        parcel.writeByte((byte) (isFavorito == null ? 0 : isFavorito ? 1 : 2));
        parcel.writeByte((byte) (isWishList == null ? 0 : isWishList ? 1 : 2));
    }
}
