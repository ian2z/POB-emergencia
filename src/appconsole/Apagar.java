package appconsole;

import java.util.List;
import com.db4o.ObjectContainer;
import com.db4o.query.Query;
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import util.Util;

public class Apagar {
    protected ObjectContainer manager;

    public Apagar() {
        try {
            manager = Util.conectar();

            // Apagar o paciente de CPF informado
            String cpfAlvo = "13183543400";

            // 1. Localizar o paciente
            Query q = manager.query();
            q.constrain(Paciente.class);
            q.descend("cpf").constrain(cpfAlvo);
            List<Paciente> resultados = q.execute();

            if (resultados.size() > 0) {
                Paciente p = resultados.get(0);

                System.out.println("Apagando paciente: " + p.getNome());

                // 2. Tratar os relacionamentos para evitar inconsistência
                // Precisamos avisar às UPAs que esses atendimentos não existem mais
                for (Atendimento a : p.getAtendimentos()) {
                    Upa u = a.getUpa();
                    u.remover(a); // Remove o atendimento da lista da UPA
                    manager.store(u); // Atualiza a UPA no banco
                    manager.delete(a); // Apaga o objeto atendimento
                }

                // 3. Apagar o paciente de fato
                manager.delete(p);
                manager.commit(); // Confirma a transação
                System.out.println("Paciente e seus atendimentos foram apagados com sucesso.");
            } else {
                System.out.println("Paciente não encontrado.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            Util.desconectarBanco();
        }
    }

    public static void main(String[] args) {
        new Apagar();
    }
}