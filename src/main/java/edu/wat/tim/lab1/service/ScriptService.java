package edu.wat.tim.lab1.service;

import edu.wat.tim.lab1.model.*;
import edu.wat.tim.lab1.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ScriptService {
    private final KlientEntityRepository klientEntityRepository;
    private final KoszykEntityRepository koszykEntityRepository;
    private final ArtykulEntityRepository artykulEntityRepository;
    private final PozycjaWKoszykuRepository pozycjaWKoszykuRepository;

    @Autowired
    public ScriptService(KlientEntityRepository klientEntityRepository, KoszykEntityRepository koszykEntityRepository, ArtykulEntityRepository artykulEntityRepository, PozycjaWKoszykuRepository pozycjaWKoszykuRepository) {
        this.klientEntityRepository = klientEntityRepository;
        this.koszykEntityRepository = koszykEntityRepository;
        this.artykulEntityRepository = artykulEntityRepository;
        this.pozycjaWKoszykuRepository = pozycjaWKoszykuRepository;

    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("klientEntityRepository", klientEntityRepository);
            bindings.putMember("koszykEntityRepository", koszykEntityRepository);
            bindings.putMember("artykulEntityRepository", artykulEntityRepository);
            bindings.putMember("listaKoszykaRepository", pozycjaWKoszykuRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }

    }
    public void createKlient(List<KlientEntity> klienci) {
        klientEntityRepository.saveAll(klienci);
    }
    public void dodajProdukt(List<ArtykulEntity> artykul) {

        artykulEntityRepository.saveAll(artykul);
    }

    public List<ArtykulEntity> getAllArtykuly() {
        return artykulEntityRepository.findAll();
    }
}
