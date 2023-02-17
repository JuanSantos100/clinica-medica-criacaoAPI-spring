package minha.api.MinhaAPI.paciente;

import minha.api.MinhaAPI.endereco.DadosEndereco;

public record DadosCadastroPaciente(String nome, String email, String telefone, String cpf, DadosEndereco endereco) {
}
