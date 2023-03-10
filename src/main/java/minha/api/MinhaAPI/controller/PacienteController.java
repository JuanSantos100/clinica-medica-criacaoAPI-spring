package minha.api.MinhaAPI.controller;

import jakarta.validation.Valid;
import minha.api.MinhaAPI.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("pacientes")
@RestController
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public ResponseEntity cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder) {
        var dadosPaciente = new Paciente(dados);
        repository.save(dadosPaciente);

        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(dadosPaciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(dadosPaciente));

    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> listarPaciente(@PageableDefault(page = 0, size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findByAtivoTrue(paginacao).map(DadosListagemPaciente::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarPaciente(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirPaciente(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);
        paciente.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity listagemPorId(@PathVariable Long id) {
        var paciente = repository.getReferenceById(id);

        if (paciente.getAtivo() != true) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}