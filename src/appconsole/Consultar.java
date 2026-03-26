package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Atendimento;
import modelo.Paciente;
import util.Util;

public class Consultar {
    protected ObjectContainer manager;

    public Consultar() {
        try {
            manager = Util.conectarBanco();

            System.out.println("--- Atendimentos na data 20/10/2023 ---");
            List<Atendimento> res1 = atendimentosNaData("20/10/2023");
            for (Atendimento a : res1) System.out.println(a);

            System.out.println("\n--- Atendimentos do paciente CPF 123 ---");
            List<Atendimento> res2 = atendimentosDoPaciente("123");
            for (Atendimento a : res2) System.out.println(a);

            System.out.println("\n--- Pacientes com mais de 2 atendimentos na UPA Central ---");
            List<Paciente> res3 = pacientesComMuitosAtendimentosNaUpa("Central", 2);
            for (Paciente p : res3) System.out.println(p);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            Util.desconectarBanco();
        }
    }

    // 1. Quais os atendimentos na data X
    public List<Atendimento> atendimentosNaData(String data) {
        Query q = manager.query();
        q.constrain(Atendimento.class);
        q.descend("data").constrain(data);
        return q.execute();
    }

    // 2. Quais os atendimentos que possuem paciente de cpf X
    public List<Atendimento> atendimentosDoPaciente(String cpf) {
        Query q = manager.query();
        q.constrain(Atendimento.class);
        q.descend("paciente").descend("cpf").constrain(cpf);
        return q.execute();
    }

    // 3. Quais os pacientes que tem mais de N atendimentos na upa X
    public List<Paciente> pacientesComMuitosAtendimentosNaUpa(String nomeUpa, int n) {
        Query q = manager.query();
        q.constrain(Paciente.class);

        // Filtra pacientes que possuem atendimentos na UPA X
        q.descend("atendimentos").descend("upa").descend("nome").constrain(nomeUpa);

        List<Paciente> resultados = q.execute();

        // SODA não faz contagem de tamanho de lista nativamente na restrição,
        // então filtramos o resultado pelo tamanho da lista de atendimentos do paciente
        // que coincidem com a UPA informada.
        return resultados.stream().filter(p -> {
            long contagem = p.getAtendimentos().stream()
                    .filter(at -> at.getUpa().getNome().equals(nomeUpa))
                    .count();
            return contagem > n;
        }).toList();
    }

    public static void main(String[] args) {
        new Consultar();
    }
}