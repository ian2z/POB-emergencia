package appconsole;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import util.Util;

public class Listar {

    public Listar(){
        Util.conectar();
        EntityManager manager = Util.getManager();

        System.out.println("\n---listagem de pacientes:");
        //usamos TypedQuery passando uma string SQL-like (JPQL)
        TypedQuery<Paciente> qPacientes = manager.createQuery("SELECT p FROM Paciente p", Paciente.class);
        List<Paciente> pacientes = qPacientes.getResultList();
        for(Paciente p: pacientes){
            System.out.println(p);
        }

        System.out.println("\n---listagem de atendimentos:");
        TypedQuery<Atendimento> qAtendimentos = manager.createQuery("SELECT a FROM Atendimento a", Atendimento.class);
        List<Atendimento> atendimentos = qAtendimentos.getResultList();
        for(Atendimento a: atendimentos){
            System.out.println(a);
        }

        System.out.println("\n---listagem de UPAs:");
        TypedQuery<Upa> qUpas = manager.createQuery("SELECT u FROM Upa u", Upa.class);
        List<Upa> upas = qUpas.getResultList();
        for(Upa u: upas){
            System.out.println(u);
        }

        Util.desconectar();
    }

    public static void main(String[] args) {
        new Listar();
    }
}