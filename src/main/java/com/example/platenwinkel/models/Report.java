package com.example.platenwinkel.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Report {
// - ( product voorraadniveaus, verkopen, administratie voor de owner van de website)


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

        @OneToOne
    @JoinColumn(name = "lpproduct_id")
    private LpProduct lpProduct;

    private int voorraadAantal;

    private int verkochtAantal;

    private LocalDateTime rapportDatum;

    public Report() {}

    public Report(LpProduct lpProduct, int voorraadAantal, int verkochtAantal, LocalDateTime rapportDatum) {
        this.lpProduct = lpProduct;
        this.voorraadAantal = voorraadAantal;
        this.verkochtAantal = verkochtAantal;
        this.rapportDatum = rapportDatum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LpProduct getLpProduct() {
        return lpProduct;
    }

    public void setLpProduct(LpProduct lpProduct) {
        this.lpProduct = lpProduct;
    }

    public int getVoorraadAantal() {
        return voorraadAantal;
    }

    public void setVoorraadAantal(int voorraadAantal) {
        this.voorraadAantal = voorraadAantal;
    }

    public int getVerkochtAantal() {
        return verkochtAantal;
    }

    public void setVerkochtAantal(int verkochtAantal) {
        this.verkochtAantal = verkochtAantal;
    }

    public LocalDateTime getRapportDatum() {
        return rapportDatum;
    }

    public void setRapportDatum(LocalDateTime rapportDatum) {
        this.rapportDatum = rapportDatum;
    }
}
