package logika;

import java.io.Serializable;

public class Ksiazka implements Serializable {
    // podstatwowe zmienne
    private String nazwa;
    private String gatunek;
    private String autor;
    private boolean stan;
    private String indeks_ksiazki;  // nie dostaje na początku, dopiero przy daniu na pólkę

// zmienne dodatkowe

    // konstruktor książki
    public Ksiazka(String nazwa, String gatunek, String autor, boolean stan) {
        this.nazwa = nazwa;
        this.gatunek = gatunek;
        this.autor = autor;
        this.stan = stan;

    }

    // gettery do zmiennych podstawowych
    public String getNazwa() {
        return nazwa;
    }

    public String getGatunek() {
        return gatunek;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isStan() {
        return stan;
    }

    public String getIndeks() {
        return indeks_ksiazki.toString();
    }

    // gettery do zmiennych dodtakowych

    // ewentualne metody

    protected void doddaj_indeks_ksiazki(String indeks){
        indeks_ksiazki=indeks;
    }

    public void wypozyczenie(){
        stan = false;
    }

    public void zwrot(){
        stan = true;
    }
}