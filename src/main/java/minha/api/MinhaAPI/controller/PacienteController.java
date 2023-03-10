package minha.api.MinhaAPI.controller;

import jakarta.validation.Valid;
import minha.api.MinhaAPI.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("pacientes")
@RestController
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    public void cadastrarPaciente(@RequestBody @Valid DadosCadastroPaciente dados) {

        repository.save(new Paciente(dados));
    }

    @GetMapping
    public Page<DadosListagemPaciente> listarPaciente(@PageableDefault(page = 0, size = 10, sort = {"nome"}) Pageable paginacao) {
        return repository.findAll(paginacao).map(DadosListagemPaciente::new);
    }

    @PutMapping
    @Transactional
    public void atualizarPaciente(@RequestBody @Valid DadosAtualizacaoPaciente dados) {
        var paciente = repository.getReferenceById(dados.id());
        System.out.println(dados.telefone());
        paciente.atualizarInformacoes(dados);
    }
}