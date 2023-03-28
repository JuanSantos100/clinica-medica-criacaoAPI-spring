package minha.api.MinhaAPI.controller;


import jakarta.validation.Valid;
import minha.api.MinhaAPI.domain.consulta.AgendaDeConsultas;
import minha.api.MinhaAPI.domain.consulta.DadosAgendamentoConsulta;
import minha.api.MinhaAPI.domain.consulta.DadosCancelamentoConsulta;
import minha.api.MinhaAPI.domain.consulta.DadosDetalhamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        agenda.agendar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoConsulta(null, null, null, null));
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);

        return ResponseEntity.noContent().build();
    }
}
