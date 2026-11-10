package com.pbo.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "operasi_aritmatika")
public class Operasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double bil1;

    @Column(nullable = false, length = 5)
    private String opt = "+"; // Default operator

    @Column(nullable = false)
    private Double bil2;

    // --- Getter & Setter Database ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getBil1() { return bil1; }
    public void setBil1(Double bil1) { this.bil1 = bil1; }

    public String getOpt() { return opt; }
    public void setOpt(String opt) { this.opt = opt; }

    public Double getBil2() { return bil2; }
    public void setBil2(Double bil2) { this.bil2 = bil2; }

    // --- PERHITUNGAN OTOMATIS (TIDAK MASUK DATABASE) ---
    @Transient // Anotasi ini mencegah Spring Boot membuat kolom result di DB
    public Double getResult() {
        if (bil1 == null || bil2 == null || opt == null) return null;
        
        switch (opt) {
            case "+": return bil1 + bil2;
            case "-": return bil1 - bil2;
            case "*": return bil1 * bil2;
            case "/": return (bil2 != 0) ? (bil1 / bil2) : 0.0; // Hindari error dibagi 0
            default: return 0.0;
        }
    }
}