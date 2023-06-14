package edu.wat.tim.lab1.service;

import edu.wat.tim.lab1.model.KlientEntity;
import edu.wat.tim.lab1.model.KoszykEntity;
import edu.wat.tim.lab1.model.ArtykulEntity;
import edu.wat.tim.lab1.model.PozycjaWKoszyku;
import edu.wat.tim.lab1.repository.ArtykulEntityRepository;
import edu.wat.tim.lab1.repository.KlientEntityRepository;
import edu.wat.tim.lab1.repository.KoszykEntityRepository;
import edu.wat.tim.lab1.repository.PozycjaWKoszykuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final KlientEntityRepository klientEntityRepository;
    private final KoszykEntityRepository koszykEntityRepository;
    private final ArtykulEntityRepository artykulEntityRepository;
    private final PozycjaWKoszykuRepository pozycjaWKoszykuRepository;

    public KlientEntity createKlient(KlientEntity klient) {
        return klientEntityRepository.save(klient);
    }

    public List<KlientEntity> getAllKlienci() {
        return klientEntityRepository.findAll();
    }

    public KoszykEntity createKoszyk(KoszykEntity koszyk, Long klientId) {
        KlientEntity klient = klientEntityRepository.findById(klientId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono klienta o id " + klientId));
        koszyk.setKlientEntity(klient);
        return koszykEntityRepository.save(koszyk);
    }

    public List<ArtykulEntity> searchArtykulyByNazwa(String nazwa) {
        return artykulEntityRepository.findByNazwa(nazwa);
    }
    public ArtykulEntity dodajProdukt(ArtykulEntity artykul) {
        return artykulEntityRepository.save(artykul);
    }

    public ArtykulEntity dodajProduktDoKoszyka(ArtykulEntity artykul, Long koszykId, Integer iloscProduktu) {
        PozycjaWKoszyku pozycja = new PozycjaWKoszyku();
        KoszykEntity koszyk = koszykEntityRepository.findById(koszykId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono koszyka o id " + koszykId));
        artykul = artykulEntityRepository.save(artykul);
        pozycja.setKoszykEntity(koszyk);
        pozycja.setArtykulEntity(artykul);
        pozycja.setIloscProduktu(iloscProduktu);
        koszyk.getPozycjaWKoszykuList().add(pozycja);
        pozycjaWKoszykuRepository.save(pozycja);
        return artykul;
    }

    public void usunProduktZKoszyka(Long artykulId) {
        ArtykulEntity artykul = artykulEntityRepository.findById(artykulId)
                        .orElseThrow(() -> new RuntimeException("Nie znaleziono koszyka o id " + artykulId));
        pozycjaWKoszykuRepository.deleteByArtykulEntityId(artykulId);
        artykulEntityRepository.deleteById(artykulId);
    }

    public PozycjaWKoszyku zmienLiczbeProduktowWKoszyku(Long koszykId, Long artykulId, Integer iloscProduktu) {
        ArtykulEntity artykul = artykulEntityRepository.findById(artykulId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono koszyka o id " + artykulId));
        KoszykEntity koszyk = koszykEntityRepository.findById(koszykId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono koszyka o id " + koszykId));
        PozycjaWKoszyku pozycja = pozycjaWKoszykuRepository.findByArtykulEntityIdAndKoszykEntityId(artykulId,koszykId);
        if(iloscProduktu < 1){
            throw new RuntimeException("Podano liczbe mniejsza niz 1");
        }
        pozycja.setIloscProduktu(iloscProduktu);
         return pozycjaWKoszykuRepository.save(pozycja);
    }
}