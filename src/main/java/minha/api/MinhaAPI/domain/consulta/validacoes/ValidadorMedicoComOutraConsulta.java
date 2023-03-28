package minha.api.MinhaAPI.domain.consulta.validacoes;

import minha.api.MinhaAPI.domain.ValidacaoException;
import minha.api.MinhaAPI.domain.consulta.ConsultaRepository;
import minha.api.MinhaAPI.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorMedicoComOutraConsulta {

    private ConsultaRepository consultaRepository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuiOutraConsultaNoMesmoHorario = consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if(medicoPossuiOutraConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
        }
    }
}
