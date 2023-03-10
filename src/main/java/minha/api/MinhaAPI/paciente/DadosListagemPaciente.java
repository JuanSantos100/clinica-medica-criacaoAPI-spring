package minha.api.MinhaAPI.paciente;

import minha.api.MinhaAPI.endereco.Endereco;

public record DadosListagemPaciente(Long id, String nome, String email, String cpf, String telefone, Endereco endereco) {
    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
    }
}
