package minha.api.MinhaAPI.domain.consulta.validacoes.cancelamento;

import minha.api.MinhaAPI.domain.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

    void validar(DadosCancelamentoConsulta dados);
}
