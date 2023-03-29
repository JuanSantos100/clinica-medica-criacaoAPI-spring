package minha.api.MinhaAPI.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var dto = agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity cancelarConsulta(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        agenda.cancelar(dados);

        return ResponseEntity.noContent().build();
    }
}
