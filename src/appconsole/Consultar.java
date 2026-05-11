package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import modelo.Atendimento;
import modelo.Paciente;
import util.Util;

public class Consultar {

    public Consultar() {
        Util.conectar();
        EntityManager manager = Util.getManager();

        System.out.println("\n=======================================================");

        //quais os atendimentos na data X
        String dataBusca = "01-01-2026";
        System.out.println("1) Buscando atendimentos na data: " + dataBusca);
        TypedQuery<Atendimento> q1 = manager.createQuery(
                "SELECT a FROM Atendimento a WHERE a.data = :data", Atendimento.class);
        q1.setParameter("data", dataBusca);

        for (Atendimento a : q1.getResultList()) {
            System.out.println(a);
        }

        System.out.println("\n=======================================================");

        //quais os atendimentos que possuem paciente de cpf X
        String cpfBusca = "22222222222"; // Ex: Mario Castro
        System.out.println("2) Buscando atendimentos do paciente com CPF: " + cpfBusca);
        TypedQuery<Atendimento> q2 = manager.createQuery(
                "SELECT a FROM Atendimento a WHERE a.paciente.cpf = :cpf", Atendimento.class);
        q2.setParameter("cpf", cpfBusca);

        for (Atendimento a : q2.getResultList()) {
            System.out.println(a);
        }

        System.out.println("\n=======================================================");

        //quais os pacientes que tem mais de N atendimentos na upa X
        long minAtendimentos = 1L; //count no JPA retorna Long, então usamos 1L
        String nomeUpaBusca = "Bessa";
        System.out.println("3) Pacientes com mais de " + minAtendimentos + " atendimentos na UPA: " + nomeUpaBusca);

        //faz join, agrupa por paciente e conta os atendimentos
        TypedQuery<Paciente> q3 = manager.createQuery(
                "SELECT p FROM Paciente p JOIN p.atendimentos a " +
                        "WHERE a.upa.nome = :nomeUpa " +
                        "GROUP BY p " +
                        "HAVING COUNT(a) > :limite", Paciente.class);

        q3.setParameter("nomeUpa", nomeUpaBusca);
        q3.setParameter("limite", minAtendimentos);

        for (Paciente p : q3.getResultList()) {
            System.out.println(p);
        }

        System.out.println("\n=======================================================");

        Util.desconectar();
    }

    public static void main(String[] args) {
        new Consultar();
    }
}