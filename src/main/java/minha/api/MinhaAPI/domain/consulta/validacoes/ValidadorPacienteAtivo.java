package minha.api.MinhaAPI.domain.consulta.validacoes;

import minha.api.MinhaAPI.domain.ValidacaoException;
import minha.api.MinhaAPI.domain.consulta.DadosAgendamentoConsulta;
import minha.api.MinhaAPI.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoDeConsulta {
    @Autowired
    private PacienteRepository pacienteRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var pacienteEstaAtivo = pacienteRepository.findAtivoById(dados.idPaciente());
        if(!pacienteEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada para paciente excluído");
        }
    }
}
