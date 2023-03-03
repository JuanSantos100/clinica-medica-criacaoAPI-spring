package minha.api.MinhaAPI.controller;

import jakarta.validation.Valid;
import minha.api.MinhaAPI.endereco.Endereco;
import minha.api.MinhaAPI.medico.DadosCadastroMedico;
import minha.api.MinhaAPI.medico.DadosListagemMedico;
import minha.api.MinhaAPI.medico.Medico;
import minha.api.MinhaAPI.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;
    @PostMapping
    @Transactional
    /*
    Por ser uma operação de escrita
    prescisamos adicionar a annotation
    @Trnasactional para declararmos que terá uma
    transação no banco
     */
    public void cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados) {
        repository.save(new Medico(dados));

    }

    @GetMapping
    public List<DadosListagemMedico> listar() {
        return repository.findAll().stream().map(DadosListagemMedico::new).toList(); //Realizando a conversão de um objeto Medico para DadosListagemMedico
    }



}
