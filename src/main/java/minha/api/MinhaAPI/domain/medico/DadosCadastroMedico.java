package minha.api.MinhaAPI.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import minha.api.MinhaAPI.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        /*
            NotBlank = Verifica se um campo está vindo como nulo
            e se não está vindo em branco. Precisa conter texto.
         */
        @NotBlank
        String nome,
        @NotBlank
        @Email
        String email,

        @NotBlank
        String telefone,


        /*
            O @Pattern verifica um padrão estabelicido para aquele campo
            No nosso caso usamos uma expressão regular para indicar o seguinte:

            \\d = somente dígitos
            {4,6} = De 4 a 6 Caracteres
         */
        @Pattern(regexp = "\\d{4,6}")
        @NotBlank
        String crm,

         @NotNull
        Especialidade especialidade,

        @NotNull
        @Valid
        DadosEndereco endereco) {
}
