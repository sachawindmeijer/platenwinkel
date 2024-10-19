package com.example.platenwinkel.models;

import com.example.platenwinkel.enumeration.Genre;
import com.example.platenwinkel.helper.PriceCalculator;
import jakarta.persistence.*;

import java.time.LocalDate;


//Beheer van productgegevens (toevoegen, bewerken van beschrijvingen, releasedata).
//Aanpassing van voorraadniveaus.

//Beheer van promoties en aanbiedingen. private List<Sale> sales;
//boolean
// if yes then 25% off

//Upload en beheer van productafbeeldingen (hoesafbeeldingen, vinyl, extra's).
//
//
@Entity//geeft aan dat de class die volgt een entiteit is in de database
@Table(name = "Lp_Product")
public class LpProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artist;
    private String album;
    private String description;
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;
    private int inStock; // dit aanpassen in het klassen diagram

//    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    //private Report inventory;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//     private Promotion promotion;

    private double priceInclVat;
    private double priceEclVat;//ook deze aanpassen

    public LpProduct(Long id, String artist, String album, String description, Genre genre, int inStock, double priceEclVat) {
        this.id = id;
        this.artist = artist;
        this.album = album;
        this.description = description;
        this.genre = genre;
        this.inStock = inStock;
        this.priceEclVat = priceEclVat;
    }
    public LpProduct() {

    }

//    List <Image> images;
    //

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album= album;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public double getPriceInclVat() {
        return priceInclVat;
    }

    public void setPriceInclVat(double priceInclVat) {
        this.priceInclVat = PriceCalculator.calculatePriceInclVat(priceEclVat);
    }

    public double getPriceEclVat() {
        return priceEclVat;
    }

    public void setPriceEclVat(double priceEclVat) {
        this.priceEclVat = priceEclVat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }
}
//