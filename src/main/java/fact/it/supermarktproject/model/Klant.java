package fact.it.supermarktproject.model;
//Ooms Britt r0802207

import java.util.ArrayList;

public class Klant extends Persoon{
    private ArrayList<String> boodschappenlijst = new ArrayList<>();
    private int klantenkaartnr;

    public Klant(String voornaam, String familienaam) {
        super(voornaam, familienaam);
        this.klantenkaartnr = -1;
    }

    public int getKlantenkaartnr() {
        return klantenkaartnr;
    }

    public void setKlantenkaartnr(int klantenkaartnr) {
        this.klantenkaartnr = klantenkaartnr;
    }

    public ArrayList<String> getBoodschappenlijst() {
        return boodschappenlijst;
    }

    public boolean voegToeAanBoodschappenlijst (String product){
        if (boodschappenlijst.size() < 5){
            boodschappenlijst.add(product);
            return true;
        }
        else {
            return false;
        }

    }

    public int getAantalOpBoodschappenlijst(){
        return boodschappenlijst.size();
    }

    @Override
    public String toString() {
        return "Klant " + super.toString() + " met klantenkaartnr " + klantenkaartnr;
    }
}
