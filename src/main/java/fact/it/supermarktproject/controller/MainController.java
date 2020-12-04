package fact.it.supermarktproject.controller;
//Ooms Britt r0802207
import fact.it.supermarktproject.model.Afdeling;
import fact.it.supermarktproject.model.Klant;
import fact.it.supermarktproject.model.Personeelslid;
import fact.it.supermarktproject.model.Supermarkt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class MainController {

    private ArrayList<Personeelslid> personeelsleden;
    private ArrayList<Klant> klanten;
    private ArrayList<Supermarkt> supermarkten;
    @PostConstruct
    public void vullenLijsten() {
        vulPersoneelsledenLijst();
        vulSupermarktenLijst();
        vulKlantenLijst();
    }

    /* Codeer hieronder al je verschillende mappings */

@RequestMapping("/1_nieuweKlant")
    public String nieuweKlant(Model model) {
    model.addAttribute("supermarkten", supermarkten);
    return "1_nieuweKlant";
}

@RequestMapping("/2_welkomKlant")
    public String nieuweKlant(Model model, HttpServletRequest request) {
    String voornaam = request.getParameter("voornaam");
    String familienaam = request.getParameter("familienaam");

    String geboortejaarStr = request.getParameter("geboortejaar");
    int geboortejaar = Integer.parseInt(geboortejaarStr);

    int supermarkt = Integer.parseInt(request.getParameter("supermarkt"));

    Supermarkt smarkt = supermarkten.get(supermarkt);
    Klant klant = new Klant(voornaam, familienaam);

    klant.setGeboortejaar(geboortejaar);
    smarkt.registreerKlant(klant);
    klanten.add(klant);
    model.addAttribute("klant", klant);

    return "2_welkomKlant";
}

@RequestMapping("/3_nieuwPersoneel")
    public String nieuwPersoneel(){
    return "3_nieuwPersoneel";
}

@RequestMapping("4_welkomPersoneel")
    public String nieuwPersoneel(Model model, HttpServletRequest request) {
    String voornaam = request.getParameter("voornaam");
    String familienaam = request.getParameter("familienaam");
    String datum = request.getParameter("datum");
    LocalDate inDienst = LocalDate.parse(datum);
    Personeelslid personeel = new Personeelslid(voornaam, familienaam);
    personeel.setInDienstSinds(inDienst);
    personeelsleden.add(personeel);
    model.addAttribute("personeelslid", personeel);

    return "4_welkomPersoneel";
}

@RequestMapping("5_lijstPersoneel")
    public String lijstPersoneel(Model model){
    model.addAttribute("personeelsleden",personeelsleden);
    return "5_lijstPersoneel";
}

@RequestMapping("6_lijstKlant")
    public String lijstKlant(Model model){
    model.addAttribute("klanten",klanten);
    return "6_lijstKlant";
}

@RequestMapping("/7_nieuweSupermarkt")
    public String nieuweSupermarkt(){
    return "7_nieuweSupermarkt";
    }

@RequestMapping("/8_lijstSupermarkt")
    public String lijstSupermarkt(Model model, HttpServletRequest request){
    String nieuweSupermarkt = request.getParameter("nieuweSupermarkt");

    Supermarkt sprmarkt = new Supermarkt(nieuweSupermarkt);
    if (nieuweSupermarkt !=null) {
        supermarkten.add(sprmarkt);
    }
    model.addAttribute("lijstSupermarkt", supermarkten);
    return  "8_lijstSupermarkt";
    }

    @RequestMapping("/9_nieuweAfdeling")
    public String nieuweAfdeling(Model model){
    model.addAttribute("personeel", personeelsleden);
    model.addAttribute("supermarkten", supermarkten);
    return "9_nieuweAfdeling";
    }

    @RequestMapping("/10_lijstAfdeling")
    public String lijstAfdeling(Model model, HttpServletRequest request){
    String naam = request.getParameter("naam");
    int supermarktIndex = Integer.parseInt(request.getParameter("supermarkten"));
    Supermarkt supermarkt = supermarkten.get(supermarktIndex);

    if (naam != null) {
        String foto = request.getParameter("foto");
        boolean gekoeld = Boolean.parseBoolean(request.getParameter("gekoeld"));
        int verantwoordelijkeIndex = Integer.parseInt(request.getParameter("verantwoordelijke"));

        if ((supermarktIndex < 0) && (verantwoordelijkeIndex < 0)){
            model.addAttribute("foutMelding", "Er werd geen verantwoordelijke en geen supermarkt gekozen.");
            return "99_foutMelding";
        }

        if (verantwoordelijkeIndex < 0) {
            model.addAttribute("foutMelding", "Er werd geen verantwoordelijke gekozen.");
            return "99_foutMelding";
        }

        if (supermarktIndex < 0){
            model.addAttribute("foutMelding", "Er werd geen supermarkt gekozen.");
            return "99_foutMelding";
        }

        Personeelslid personeelslid = personeelsleden.get(verantwoordelijkeIndex);
        Afdeling afdeling = new Afdeling(naam);
        afdeling.setFoto(foto);
        afdeling.setVerantwoordelijke(personeelslid);
        afdeling.setGekoeld(gekoeld);
        supermarkt.voegAfdelingToe(afdeling);
    }
    model.addAttribute("supermarkt", supermarkt);
    return "10_lijstAfdeling";
    }

    @RequestMapping("/11_infoAfdeling")
    public String infoAfdeling(Model model, HttpServletRequest request){
    boolean voorwaarde = false;
    String naamAfdeling = request.getParameter("afdeling");
    for (Supermarkt supermarkt: supermarkten){
        if (supermarkt.zoekAfdelingOpNaam(naamAfdeling) != null);
        Afdeling afdeling = supermarkt.zoekAfdelingOpNaam(naamAfdeling);
        model.addAttribute("afdeling", afdeling);
        voorwaarde = true;
    }
    if (voorwaarde == false) {
        model.addAttribute("foutMelding", "Geen afdeling met de naam " + naamAfdeling + " gevonden!");
        return "99_foutMelding";
    }
    return "11_infoAfdeling";
    }

//vul methodes

    private ArrayList<Personeelslid> vulPersoneelsledenLijst() {
        personeelsleden = new ArrayList<>();
        Personeelslid jitse = new Personeelslid("Jitse", "Verhaegen");
        Personeelslid bert = new Personeelslid("Bert", "De Meulenaere");
        Personeelslid sanne = new Personeelslid("Sanne", "Beckers");
        personeelsleden.add(jitse);
        personeelsleden.add(bert);
        personeelsleden.add(sanne);
        return personeelsleden;
    }

    private ArrayList<Klant> vulKlantenLijst() {
        klanten = new ArrayList<>();
        Klant daan = new Klant("Daan", "Mertens");
        daan.setGeboortejaar(2001);
        Klant wim = new Klant("Wim", "Wijns");
        wim.setGeboortejaar(1956);
        Klant gert = new Klant("Gert", "Pauwels");
        gert.setGeboortejaar(1978);
        Klant britt = new Klant("Britt", "Ooms");
        daan.setGeboortejaar(2001);
        klanten.add(daan);
        klanten.add(wim);
        klanten.add(gert);
        klanten.add(britt);
        klanten.get(0).voegToeAanBoodschappenlijst("melk");
        klanten.get(0).voegToeAanBoodschappenlijst("kaas");
        klanten.get(1).voegToeAanBoodschappenlijst("eieren");
        klanten.get(1).voegToeAanBoodschappenlijst("water");
        klanten.get(1).voegToeAanBoodschappenlijst("bloemkool");
        klanten.get(1).voegToeAanBoodschappenlijst("sla");
        klanten.get(2).voegToeAanBoodschappenlijst("tomaten");
        klanten.get(3).voegToeAanBoodschappenlijst("noedels");
        klanten.get(3).voegToeAanBoodschappenlijst("spek");
        return klanten;
    }

    private ArrayList<Supermarkt> vulSupermarktenLijst() {
        supermarkten = new ArrayList<>();
        Supermarkt supermarkt1 = new Supermarkt("Colruyt Geel");
        Supermarkt supermarkt2 = new Supermarkt("Okay Meerhout");
        Supermarkt supermarkt3 = new Supermarkt("Colruyt Herentals");
        Afdeling afdeling1 = new Afdeling("Brood");
        Afdeling afdeling2 = new Afdeling("Groenten");
        afdeling2.setGekoeld(true);
        Afdeling afdeling3 = new Afdeling("Fruit");
        afdeling3.setGekoeld(true);
        Afdeling afdeling4 = new Afdeling("Vlees");
        afdeling4.setGekoeld(true);
        Afdeling afdeling5 = new Afdeling("Dranken");
        Afdeling afdeling6 = new Afdeling("Diepvries");
        afdeling1.setFoto("/img/brood.jpg");
        afdeling2.setFoto("/img/groenten.jpg");
        afdeling3.setFoto("/img/fruit.jpg");
        afdeling1.setVerantwoordelijke(personeelsleden.get(0));
        afdeling2.setVerantwoordelijke(personeelsleden.get(1));
        afdeling3.setVerantwoordelijke(personeelsleden.get(2));
        afdeling4.setVerantwoordelijke(personeelsleden.get(0));
        afdeling5.setVerantwoordelijke(personeelsleden.get(1));
        afdeling6.setVerantwoordelijke(personeelsleden.get(2));

        supermarkt1.voegAfdelingToe(afdeling1);
        supermarkt1.voegAfdelingToe(afdeling2);
        supermarkt2.voegAfdelingToe(afdeling3);
        supermarkt2.voegAfdelingToe(afdeling4);
        supermarkt3.voegAfdelingToe(afdeling5);
        supermarkt3.voegAfdelingToe(afdeling6);
        supermarkten.add(supermarkt1);
        supermarkten.add(supermarkt2);
        supermarkten.add(supermarkt3);
        return supermarkten;
    }
}
