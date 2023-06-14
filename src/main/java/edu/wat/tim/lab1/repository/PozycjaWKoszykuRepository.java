package edu.wat.tim.lab1.repository;
import edu.wat.tim.lab1.model.KoszykEntity;
import edu.wat.tim.lab1.model.PozycjaWKoszyku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PozycjaWKoszykuRepository extends JpaRepository<PozycjaWKoszyku, Long>{
    @Transactional
    public void deleteByArtykulEntityId(Long arytukulId);

    public PozycjaWKoszyku findByArtykulEntityIdAndKoszykEntityId(Long artykulId, Long koszykId);
}
