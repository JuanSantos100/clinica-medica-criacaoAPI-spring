package minha.api.MinhaAPI.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import minha.api.MinhaAPI.domain.medico.DadosCadastroMedico;
import minha.api.MinhaAPI.domain.medico.DadosListagemMedico;
import minha.api.MinhaAPI.domain.medico.Medico;
import minha.api.MinhaAPI.domain.medico.MedicoRepository;
import minha.api.MinhaAPI.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    /*
    Por ser uma operação de escrita
    prescisamos adicionar a annotation
    @Trnasactional para declararmos que terá uma
    transação no banco
     */
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarMedico(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
        var medico = new Medico(dados);
        repository.save(medico);

        /*
            Ao realizar a criação de um objeto, preciso passar na resposta 3 coisas:
                1 - Devolver o cabeçalho Location com a URI
                2 - No corpo da resposta é necessário devolver o objeto recem criado
                3 - Devolver o código 201 - Created
         */
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }


    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort={"nome"}) Pageable paginacao) {
        var page =  repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new); //Realizando a conversão de um objeto Medico para DadosListagemMedico
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarMedico(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); //Criamos um novo DTO porque não é ideal passar uma entidade JPA diretamente no controller, utilizamos DTOs para isso.
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirMedico(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{id}")
    public ResponseEntity listagemPorId(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);

        if(medico.getAtivo() != true) {
            return ResponseEntity.notFound().build();
        } else if (medico.getId() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }



}
