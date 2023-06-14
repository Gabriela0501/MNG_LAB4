package edu.wat.tim.lab1.service;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.wat.tim.lab1.repository.ArtykulEntityRepository;
import edu.wat.tim.lab1.repository.KlientEntityRepository;

@Service
@Slf4j
public class ScriptService {
    private final ArtykulEntityRepository artykulEntityRepository;
    private final KlientEntityRepository klientEntityRepository;

    @Autowired
    public ScriptService(ArtykulEntityRepository artykulRepository, KlientEntityRepository klientRepository) {
        this.artykulEntityRepository = artykulRepository;
        this.klientEntityRepository = klientRepository;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("artykulRepository", artykulEntityRepository);
            bindings.putMember("klientRepository", klientEntityRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }
}
