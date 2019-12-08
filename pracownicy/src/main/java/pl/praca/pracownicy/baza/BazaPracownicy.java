package pl.praca.pracownicy.baza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.praca.pracownicy.model.ModelPracownicy;

@Repository
public interface BazaPracownicy extends JpaRepository<ModelPracownicy, Long>{

}
