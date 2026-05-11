package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import modelo.Atendimento;
import util.Util;

public class Alterar {

    public Alterar() {
        Util.conectar();
        EntityManager manager = Util.getManager();

        System.out.println("Removendo um relacionamento (Atendimento)...");

        try {
            manager.getTransaction().begin();

            //buscamos um atendimento específico (o primeiro atendimento do paciente joao)
            TypedQuery<Atendimento> query = manager.createQuery(
                    "SELECT a FROM Atendimento a WHERE a.paciente.cpf = :cpf", Atendimento.class);
            query.setParameter("cpf", "13567915644");
            query.setMaxResults(1); //pegar apenas o primeiro que achar

            Atendimento atendimento = query.getResultStream().findFirst().orElse(null);

            if (atendimento != null) {
                //removendo as listas da memoria
                atendimento.getPaciente().remover(atendimento);
                atendimento.getUpa().remover(atendimento);

                //removendo atendimento do BD
                manager.remove(atendimento);
                System.out.println("Atendimento removido com sucesso!");
            } else {
                System.out.println("Atendimento não encontrado.");
            }

            manager.getTransaction().commit();

        } catch (Exception e) {
            manager.getTransaction().rollback();
            System.out.println("Erro ao alterar: " + e.getMessage());
        } finally {
            Util.desconectar();
        }
    }

    public static void main(String[] args) {
        new Alterar();
    }
}