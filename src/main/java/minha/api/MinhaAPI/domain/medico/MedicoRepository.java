package minha.api.MinhaAPI.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByAtivoTrue(Pageable paginacao);


    /*
        Ao montar a query, o nome da tabela após o FROM precisa ser o nome da Entidade JPA
        declarada na classe Entidade, cuja anotação é: @Entity(name = "")
     */

    @Query("""
            select m from Medico m
            where m.ativo = 1
            and m.especialidade = :especialidade
            and m.id not in (
                select c.medico.id from Consultas c
                where c.data = :data
            )
            order by rand()
            LIMIT 1
            """)
    Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);
}
