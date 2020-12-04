package fact.it.supermarktproject.model;
//Ooms Britt r0802207

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Personeelslid extends Persoon {
    private LocalDate inDienstSinds;

    public Personeelslid(String voornaam, String familienaam) {
        super(voornaam, familienaam);
        this.inDienstSinds = LocalDate.now();
    }

    public LocalDate getInDienstSinds() {
        return inDienstSinds;
    }

    public void setInDienstSinds(LocalDate inDienstSinds) {
        this.inDienstSinds = inDienstSinds;
    }

    @Override
    public String toString() {
        LocalDate datum = getInDienstSinds();
        DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String geformateerdeDatum = datum.format(datumFormat);

        return "Personeelslid " + super.toString() + " is in dienst sinds " + geformateerdeDatum;
    }
}
