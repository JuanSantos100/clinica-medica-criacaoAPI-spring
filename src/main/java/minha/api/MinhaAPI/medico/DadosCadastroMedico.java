package minha.api.MinhaAPI.medico;

import minha.api.MinhaAPI.endereco.DadosEndereco;

public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade, DadosEndereco endereco) {
}
