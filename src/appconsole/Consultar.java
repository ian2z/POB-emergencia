package appconsole;

import java.util.List;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import requisito.FachadaAtendimento;
import requisito.FachadaPaciente;
import requisito.FachadaUpa;

public class Consultar {

    public Consultar() {
        FachadaAtendimento fachadaAtendimento = new FachadaAtendimento();
        FachadaPaciente fachadaPaciente = new FachadaPaciente();
        FachadaUpa fachadaUpa = new FachadaUpa();

        System.out.println("\n=======================================================");

        // 1) Buscando atendimentos na data: 01-01-2026
        System.out.println("1) Buscando atendimentos na data: 01-01-2026");
        List<Atendimento> atendimentosData = fachadaAtendimento.consultarAtendimentosPorData("01-01-2026");
        for (Atendimento a : atendimentosData) {
            System.out.println(a);
        }

        System.out.println("\n=======================================================");

        // 2) Buscando atendimentos do paciente com CPF: 22222222222
        String cpfBusca = "22222222222"; // Ex: Mario Castro
        System.out.println("2) Buscando atendimentos do paciente com CPF: " + cpfBusca);
        List<Atendimento> atendimentosCpf = fachadaAtendimento.consultarAtendimentosPorCpfPaciente(cpfBusca);
        for (Atendimento a : atendimentosCpf) {
            System.out.println(a);
        }

        System.out.println("\n=======================================================");

        // 2b) Buscando UPAs que o paciente com CPF X foi atendido
        System.out.println("2b) Buscando UPAs que o paciente com CPF " + cpfBusca + " foi atendido");
        List<Upa> upasPaciente = fachadaUpa.consultarUpasPorCpfPaciente(cpfBusca);
        for (Upa u : upasPaciente) {
            System.out.println(u);
        }

        System.out.println("\n=======================================================");

        // 3) Pacientes com mais de 1 atendimentos na UPA: Bessa
        long minAtendimentos = 1L;
        String nomeUpaBusca = "Bessa";
        System.out.println("3) Pacientes com mais de " + minAtendimentos + " atendimentos na UPA: " + nomeUpaBusca);
        List<Paciente> pacientesLimite = fachadaPaciente.consultarPacientesComMaisAtendimentos(nomeUpaBusca, minAtendimentos);
        for (Paciente p : pacientesLimite) {
            System.out.println(p);
        }

        System.out.println("\n=======================================================");

        // 4) Busca de atendimentos por palavra-chave na triagem
        String palavraChave = "fratura";
        System.out.println("4) Busca de atendimentos por palavra-chave na triagem: " + palavraChave);
        List<Atendimento> atendimentosTriagem = fachadaAtendimento.consultarAtendimentosPorTriagem(palavraChave);
        for (Atendimento a : atendimentosTriagem) {
            System.out.println("Data: " + a.getData() + " | Sintoma: " + a.getTriagem() + " | Paciente: " + a.getPaciente().getNome());
        }

        System.out.println("\n=======================================================");

        // 5) Pacientes que frequentaram MÚLTIPLAS UPAs diferentes
        System.out.println("5) Pacientes que frequentaram MÚLTIPLAS UPAs diferentes");
        List<Paciente> pacientesMultiplas = fachadaPaciente.consultarPacientesMultiplasUpas();
        for (Paciente p : pacientesMultiplas) {
            System.out.println("Alerta: O paciente " + p.getNome() + " visitou mais de uma UPA diferente!");
        }

        System.out.println("\n=======================================================");

        // 6) Ranking de Lotação das UPAs
        System.out.println("6) Ranking de Lotação das UPAs");
        List<Object[]> rankingLotacao = fachadaUpa.obterRankingLotacao();
        for (Object[] linha : rankingLotacao) {
            String nomeUpa = (String) linha[0];
            Long totalAtendimentos = (Long) linha[1];
            System.out.println("UPA " + nomeUpa + " tem " + totalAtendimentos + " atendimentos registrados.");
        }

        System.out.println("\n=======================================================");
    }

    public static void main(String[] args) {
        new Consultar();
    }
}