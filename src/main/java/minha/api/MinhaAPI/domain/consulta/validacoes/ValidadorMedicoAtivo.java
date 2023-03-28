package minha.api.MinhaAPI.domain.consulta.validacoes;

import minha.api.MinhaAPI.domain.ValidacaoException;
import minha.api.MinhaAPI.domain.consulta.DadosAgendamentoConsulta;
import minha.api.MinhaAPI.domain.medico.MedicoRepository;

public class ValidadorMedicoAtivo {

    private MedicoRepository medicoRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        //Escolha do médico é opcional
        if(dados.idMedico() == null) {
            return;
        }

        var medicoEstaAtivo = medicoRepository.findAtivoById(dados.idMedico());
        if(!medicoEstaAtivo) {
            throw new ValidacaoException("Consulta não pode ser agendada com médico inativo do sistema.");
        }

    }
}
