package minha.api.MinhaAPI.controller;

import minha.api.MinhaAPI.paciente.DadosCadastroPaciente;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("pacientes")
@RestController
public class PacienteController {

    @PostMapping
    public void cadastrarPaciente(@RequestBody DadosCadastroPaciente dados) {

        System.out.println("Dados recebidos: " + dados);
    }
}
