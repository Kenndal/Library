package logika;

import logika.Ksiazka;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Czytelnik implements Serializable {
    // zmienne podstawowe
    private String imie;
    private String nazwisko;
    private String data_urodzenia;
    private String numer_telefonu;
    private String indeks_czytelnika;
    private Boolean posiadanieKsiazek;
    private Map<String, Ksiazka> wypozyczone_ksiazki = new HashMap<String, Ksiazka>();

    // dodakowe zmienne

    // konstruktor
    public Czytelnik(String imie, String nazwisko, String data_urodzenia, String numer_telefonu) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.data_urodzenia = data_urodzenia;
        this.numer_telefonu = numer_telefonu;
    }

    // gettery
    public Map<String, Ksiazka> getWypozyczone_ksiazki() {
        return wypozyczone_ksiazki;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getData_urodzenia() {
        return data_urodzenia;
    }

    public String getNumer_telefonu() {
        return numer_telefonu;
    }

    public String getIndeks_czytelnika() {
        return indeks_czytelnika;
    }

    public Boolean getPosiadanieKsiazek() {
        return posiadanieKsiazek;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setData_urodzenia(String data_urodzenia) {
        this.data_urodzenia = data_urodzenia;
    }

    public void setNumer_telefonu(String numer_telefonu) {
        this.numer_telefonu = numer_telefonu;
    }

    public void setIndeks_czytelnika(String indeks_czytelnika) {
        this.indeks_czytelnika = indeks_czytelnika;
    }

    public void setPosiadanieKsiazek(Boolean posiadanieKsiazek) {
        this.posiadanieKsiazek = posiadanieKsiazek;
    }

    public void setWypozyczone_ksiazki(Map<String, Ksiazka> wypozyczone_ksiazki) {
        this.wypozyczone_ksiazki = wypozyczone_ksiazki;
    }

    protected void doddaj_indeks_czytelnika(String indeks){
        indeks_czytelnika = indeks;
    }
/*
    // metody (wypozyczenia i zwroty)

    //byle metody
    public void wypozyczeniebyle(Ksiazka ksiazka){
        wypozyczone_ksiazki.put(ksiazka.getNazwa(), ksiazka);
        setPosiadanieKsiazek();
    }
*/

    public void wypozyczenie(String nazwaKsiazki, Ksiazka ksiazka) {
        wypozyczone_ksiazki.put(nazwaKsiazki, ksiazka);
        setPosiadanieKsiazek();
    }

    public void zwrot (String nazwaKsiazki, Ksiazka ksiazka){
        wypozyczone_ksiazki.remove(nazwaKsiazki, ksiazka);
        setPosiadanieKsiazek();
    }

    public void wypisz_stan(){
        for(int i=0; i<getWypozyczone_ksiazki().size(); i++){
            System.out.println(getWypozyczone_ksiazki().get(i).getNazwa()+ " " + getWypozyczone_ksiazki().get(i).getAutor()+ " " + getWypozyczone_ksiazki().get(i).getGatunek()+ " " + getWypozyczone_ksiazki().get(i).getIndeks());
            System.out.print(getWypozyczone_ksiazki().get(i));
        }
    }

    // trzeba sprawdzac po kazdym wypozyczeniu/oddaniu
    public void setPosiadanieKsiazek(){
        if (wypozyczone_ksiazki.isEmpty())
            posiadanieKsiazek = false;
        else
            posiadanieKsiazek = true;
    }

}