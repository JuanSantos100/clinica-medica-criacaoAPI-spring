package minha.api.MinhaAPI.domain.medico;

import minha.api.MinhaAPI.domain.consulta.Consulta;
import minha.api.MinhaAPI.domain.endereco.DadosEndereco;
import minha.api.MinhaAPI.domain.paciente.DadosCadastroPaciente;
import minha.api.MinhaAPI.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.assertj.core.api.Assertions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Não substituir a configuração do Banco de dados
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager testEntityManager;
    @Test
    @DisplayName("""
           Deveria devolver null quando único médico cadastrado
           não está disponível na data. 
           """)
    void escolherMedicoAleatorioLivreNaDataCenario1() {

        /*
        Considerar o teste sempre para próxima segunda-feira às 10:00
         */
        //Given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        //Cadastro de dados prévios para o teste
        var medico = cadastrarMedico("Medico", "medico@gmail.com", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente teste 1", "pacienteteste1@gmail.com", "07276542312");
        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //When ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        //Then ou Assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("""
           Deveria devolver médico quando ele estiver disponível na data
           """)
    void escolherMedicoAleatorioLivreNaDataCenario2() {

        /*
        Considerar o teste sempre para próxima segunda-feira às 10:00
         */
        //Given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico = cadastrarMedico("Medico", "medico@gmail.com", "123456", Especialidade.CARDIOLOGIA);


        //When ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        //Then ou assert
        assertThat(medicoLivre).isEqualTo(medico);
    }

    @Test
    @DisplayName("""
            Deveria trazer null quando o único médico do resultado estiver inativo
            """)
    void escolherMedicoAleatorioLivreNaDataCenario3() {

        /*
        Considerar o teste sempre para próxima segunda-feira às 10:00
         */
        //Given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        //Cadastro de dados prévios para o teste
        cadastrarMedicoInativo("Medico", "medico@gmail.com", "123456", Especialidade.ORTOPEDIA);

        //When ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.ORTOPEDIA, proximaSegundaAs10);
        //Then ou Assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("""
           Deveria devolver médico quando tiver 4 médicos, 2 inativos e 1 ocupado na data.
           """)
    void escolherMedicoAleatorioLivreNaDataCenario4() {

        /*
        Considerar o teste sempre para próxima segunda-feira às 10:00
         */
        //Given ou arrange
        var proximaSegundaAs10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        //Cadastro de dados prévios para o teste
        var medicoLivreNaData = cadastrarMedico("Medico1", "medico1@gmail.com", "123456", Especialidade.CARDIOLOGIA);
        var medicoOcupado = cadastrarMedico("Medico2", "medico2@gmail.com", "654321", Especialidade.CARDIOLOGIA);
        cadastrarMedicoInativo("Medico", "medico3@gmail.com", "263741", Especialidade.CARDIOLOGIA);
        cadastrarMedicoInativo("Medico", "medico4@gmail.com", "273816", Especialidade.CARDIOLOGIA);

        var paciente = cadastrarPaciente("Paciente teste 1", "pacienteteste1@gmail.com", "07276542312");

        cadastrarConsulta(medicoOcupado, paciente, proximaSegundaAs10);

        //When ou act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);
        //Then ou Assert
        System.out.println(medicoLivre.getNome() + " + " + medicoLivre.getEmail());
        assertThat(medicoLivre).isEqualTo(medicoLivreNaData);
    }



    //Métodos de carga no banco de dados

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        testEntityManager.persist(new Consulta(null, medico, paciente, data, null));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosCadastroMedico(nome, email, crm, especialidade));
        testEntityManager.persist(medico);
        return medico;
    }

    private Medico cadastrarMedicoInativo(String nome, String email, String crm, Especialidade especialidade) {
        var medico = new Medico(dadosCadastroMedico(nome, email, crm, especialidade));
        medico.excluir();
        testEntityManager.persist(medico);
        return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        testEntityManager.persist(paciente);
        return paciente;
    }
    private DadosCadastroMedico dadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "73980761524",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "73988776655",
                "01987624356",
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}