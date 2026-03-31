package appconsole;

import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Atendimento;
import modelo.Paciente;
import util.Util;

public class Alterar {

    public Alterar() {
        Util.conectar();
        ObjectContainer manager = Util.getManager();

        System.out.println("\n=======================================================");
        System.out.println("Alteração: Remover relacionamento entre Paciente e Atendimento");
        System.out.println("=======================================================\n");

        String cpfBusca = "22222222222";

        Query q = manager.query();
        q.constrain(Paciente.class);
        q.descend("cpf").constrain(cpfBusca);
        List<Paciente> resultados = q.execute();

        if (!resultados.isEmpty()) {
            Paciente paciente = resultados.getFirst();
            System.out.println("Paciente encontrado: " + paciente.getNome());
            System.out.println("Total de atendimentos antes da remocao: " + paciente.getAtendimentos().size());

            // 2. Os atendimentos do Mario receberam os IDs 8, 9 e 10.
            // Remover o primeiro atendimento dele (ID 8)
            Atendimento atendimentoAlvo = paciente.localizar(8);

            if (atendimentoAlvo != null) {

                // Desfaz o relacionamento do lado do paciente
                paciente.remover(atendimentoAlvo);
                manager.store(paciente);
                System.out.println("-> Relacionamento removido da lista do paciente.");

                // Desfaz o relacionamento do lado da UPA
                if (atendimentoAlvo.getUpa() != null) {
                    atendimentoAlvo.getUpa().remover(atendimentoAlvo);
                    manager.store(atendimentoAlvo.getUpa());
                }

                // O atendimento 8 não pertence mais ao Mario. Logo, deve sumir do banco.
                manager.delete(atendimentoAlvo);
                System.out.println("-> Atendimento ID 8 apagado por ser um objeto órfão.");

                manager.commit();

                System.out.println("\nOperação concluída com sucesso!");
                System.out.println("Total de atendimentos do Mario AGORA: " + paciente.getAtendimentos().size());

            } else {
                System.out.println("O paciente Mario não possui um atendimento com o ID 8.");
            }
        } else {
            System.out.println("Paciente Mario não encontrado no banco.");
        }

        Util.desconectarBanco();
        System.out.println("\n=======================================================");
    }

    public static void main(String[] args) {
        new Alterar();
    }
}