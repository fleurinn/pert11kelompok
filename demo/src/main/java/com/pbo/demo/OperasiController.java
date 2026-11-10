package com.pbo.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/kalkulator")
public class OperasiController {

    @Autowired
    private OperasiRepository operasiRepository;

    @GetMapping
    public String index(Model model, @RequestParam(required = false) Long loadId) {
        List<Operasi> listOperasi = operasiRepository.findAll();
        model.addAttribute("listOperasi", listOperasi);

        // Jika ada id yang dikirim (saat edit atau baru selesai hitung), tampilkan di form
        if (loadId != null) {
            Operasi operasi = operasiRepository.findById(loadId).orElse(new Operasi());
            model.addAttribute("operasi", operasi);
        } else {
            model.addAttribute("operasi", new Operasi()); // Form kosong
        }
        
        return "kalkulator"; // Load file kalkulator.html
    }

    @PostMapping("/simpan")
    public String simpanData(@ModelAttribute("operasi") Operasi operasi) {
        // Simpan bil1, opt, bil2 ke database
        Operasi saved = operasiRepository.save(operasi);
        
        // Redirect ulang halamannya dengan membawa ID, agar form tetap terisi & result muncul
        return "redirect:/kalkulator?loadId=" + saved.getId();
    }

    @GetMapping("/hapus/{id}")
    public String hapusData(@PathVariable("id") Long id) {
        operasiRepository.deleteById(id);
        return "redirect:/kalkulator";
    }
}