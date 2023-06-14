package edu.wat.tim.lab1.controller;

import edu.wat.tim.lab1.model.ArtykulEntity;
import edu.wat.tim.lab1.model.KlientEntity;
import edu.wat.tim.lab1.model.KoszykEntity;
import edu.wat.tim.lab1.model.PozycjaWKoszyku;
import edu.wat.tim.lab1.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class SampleRestController {

    private final SampleService sampleService;

    @GetMapping("/echo")
    public String echo(String value){return value;}

    @GetMapping("/echo/{value}")
    public String echoPath(@PathVariable String value){return value;}

    @PostMapping("/klient")
    public ResponseEntity<KlientEntity> createKlient(@RequestBody KlientEntity klient) {
        KlientEntity zapiszKlient = sampleService.createKlient(klient);
        return new ResponseEntity<>(zapiszKlient, HttpStatus.CREATED);
    }

    @GetMapping("/klienci")
    public ResponseEntity<List<KlientEntity>> getAllKlienci() {
        List<KlientEntity> klienci = sampleService.getAllKlienci();
        return new ResponseEntity<>(klienci, HttpStatus.OK);
    }

    @PostMapping("/klienci/{klientId}/koszyk")
    public ResponseEntity<KoszykEntity> createKoszyk(@PathVariable(value = "klientId") Long klientId,
                                                     @RequestBody KoszykEntity koszyk) {
        KoszykEntity zapisanyKoszyk = sampleService.createKoszyk(koszyk, klientId);
        return new ResponseEntity<>(zapisanyKoszyk, HttpStatus.CREATED);
    }

    @GetMapping("/artykuly")
    public ResponseEntity<List<ArtykulEntity>> searchArtykulyByNazwa(@RequestParam(value = "nazwa") String nazwa) {
        List<ArtykulEntity> artykuly = sampleService.searchArtykulyByNazwa(nazwa);
        return new ResponseEntity<>(artykuly, HttpStatus.OK);
    }

    @PostMapping("/artykul")
    public ResponseEntity<ArtykulEntity> createProdukt (@RequestBody ArtykulEntity artykul) {
        ArtykulEntity savedProdukt = sampleService.dodajProdukt (artykul);
        return new ResponseEntity<>(savedProdukt, HttpStatus.CREATED);
    }

    @PostMapping("/koszyk/{koszykId}/artykul/{iloscProduktu}")
    public ResponseEntity<ArtykulEntity> dodajProduktDoKoszyka(@PathVariable(value = "koszykId") Long koszykId,
                                                   @PathVariable(value = "iloscProduktu") Integer iloscProduktu,
                                                   @RequestBody ArtykulEntity artykul) {
        ArtykulEntity zapisany = sampleService.dodajProduktDoKoszyka(artykul, koszykId, iloscProduktu);
        return new ResponseEntity<>(zapisany, HttpStatus.OK);
    }

    @DeleteMapping("/artykul/{artykulId}")
    public ResponseEntity<?> usunProduktZKoszyka(@PathVariable(value = "artykulId") Long artykulId) {
        sampleService.usunProduktZKoszyka(artykulId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/koszyk/{koszykId}/produkt/{produktId}")
    public ResponseEntity<PozycjaWKoszyku> zmienLiczbeProduktowWKoszyku(@PathVariable(value = "koszykId") Long koszykId,
                                                                        @PathVariable(value = "produktId") Long artykulId,
                                                                        @RequestParam(value = "iloscProduktu") Integer iloscProduktu) {

        return new ResponseEntity<>(sampleService.zmienLiczbeProduktowWKoszyku(koszykId, artykulId, iloscProduktu), HttpStatus.OK);
    }
}
