package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import modelo.Paciente;
import util.Util;

public class Apagar {

    public Apagar() {
        Util.conectar();
        EntityManager manager = Util.getManager();

        System.out.println("Apagando um objeto com relacionamentos (Paciente)...");

        try {
            manager.getTransaction().begin();

            //buscando paciente pelo CPF
            TypedQuery<Paciente> query = manager.createQuery(
                    "SELECT p FROM Paciente p WHERE p.cpf = :cpf", Paciente.class);
            query.setParameter("cpf", "45847563216"); // CPF da Sara Lopes, por exemplo

            Paciente paciente = query.getResultStream().findFirst().orElse(null);

            if (paciente != null) {
                //apagamos o paciente, os atendimentos relacionados a ele serao excluidos em modo cascata
                manager.remove(paciente);
                System.out.println("Paciente e seus atendimentos vinculados foram apagados com sucesso!");
            } else {
                System.out.println("Paciente não encontrado.");
            }

            manager.getTransaction().commit();

        } catch (Exception e) {
            manager.getTransaction().rollback();
            System.out.println("Erro ao apagar: " + e.getMessage());
        } finally {
            Util.desconectar();
        }
    }

    public static void main(String[] args) {
        new Apagar();
    }
}