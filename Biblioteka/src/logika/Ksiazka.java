package logika;

import wyjatki.DodawanieException;
import wyjatki.StanException;

import java.io.Serializable;
import java.util.Objects;

public class Ksiazka implements Serializable {
    // podstatwowe zmienne
    private String nazwa;
    private String gatunek;
    private String autor;
    private boolean stan;
    private String indeks_ksiazki;  // nie dostaje na początku, dopiero przy daniu na pólkę

// zmienne dodatkowe

    // konstruktor książki
    public Ksiazka(String nazwa, String gatunek, String autor, boolean stan) throws DodawanieException {
        if(!Objects.equals(nazwa, "") && !Objects.equals(autor, "") && !Objects.equals(gatunek, "") ) {
            this.nazwa = nazwa;
            this.gatunek = gatunek;
            this.autor = autor;
            this.stan = stan;
        }
        else
            throw new DodawanieException("Wypełnij wszystkie pola!");
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

    public Boolean isStan() {
        return stan;
    }
    public void isStanCheck() throws StanException{
        if(!stan){
            throw new StanException("Książka jest już wypożyczona!");
        }
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
        if(stan) {
           stan =false;
        }
    }

    public void zwrot(){
        stan = true;
    }

    public String isStanString(){
        if(!stan){
            return "Wypożyczona";
        }
        else
            return "Dostępna";
    }
}