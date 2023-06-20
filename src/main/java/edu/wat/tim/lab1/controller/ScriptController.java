package edu.wat.tim.lab1.controller;

import edu.wat.tim.lab1.model.ArtykulEntity;
import edu.wat.tim.lab1.model.KlientEntity;
import edu.wat.tim.lab1.model.KoszykEntity;
import edu.wat.tim.lab1.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/script")
public class ScriptController {
    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PostMapping("/add-artykul")
    public ResponseEntity<String> createProdukt (@RequestBody List<ArtykulEntity> artykul) {
        scriptService.dodajProdukt(artykul);
        return ResponseEntity.ok("Artykul zostal dodany pomyślnie.");
    }
    @PostMapping("/add-klienci")
    public ResponseEntity<String> createKlient(@RequestBody List<KlientEntity> klienci) {
        scriptService.createKlient(klienci);
        return ResponseEntity.ok("Klienci dodani pomyślnie.");
    }

    @PutMapping()
    public ResponseEntity<String> execScript(@RequestBody String script) {
        return new ResponseEntity<>(scriptService.exec(script), HttpStatus.OK) ;
    }
}
