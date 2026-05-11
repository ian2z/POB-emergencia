package appconsole;

import jakarta.persistence.EntityManager;

import modelo.Upa;
import modelo.Paciente;
import modelo.Atendimento;
import util.Util;

public class Cadastrar {

    public Cadastrar(){
        Util.conectar();
        //mudando de ObjectContainer para EntityManager
        EntityManager manager = Util.getManager();

        System.out.println("cadastrando objetos...");

        //abrindo a transação antes de fazer alterações no banco
        manager.getTransaction().begin();

        try {
            //criando persistencias de UPA primeiro, para gerar os IDs
            Upa mangabeira = new Upa("Mangabeira");
            manager.persist(mangabeira);

            Upa bessa = new Upa("Bessa");
            manager.persist(bessa);

            Upa bancarios = new Upa("Bancários");
            manager.persist(bancarios);

            //criando Paciente e Atendimento
            Paciente p1 = new Paciente("13567915644", "João Carvalho");
            manager.persist(p1);
            Atendimento a1 = new Atendimento("29-03-2026", p1, mangabeira);
            manager.persist(a1);

            Paciente p2 = new Paciente("45847563216", "Sara Lopes");
            manager.persist(p2);
            Atendimento a2 = new Atendimento("29-03-2026", p2, mangabeira);
            manager.persist(a2);

            //exemplo Mário com mais de uma visita
            Paciente mario = new Paciente("22222222222", "Mario Castro");
            manager.persist(mario);

            Atendimento aMario1 = new Atendimento("01-01-2026", mario, bessa);
            manager.persist(aMario1);
            Atendimento aMario2 = new Atendimento("02-01-2026", mario, bessa);
            manager.persist(aMario2);
            Atendimento aMario3 = new Atendimento("03-01-2026", mario, mangabeira);
            manager.persist(aMario3);

            //finalizando transação e salvando com commit
            manager.getTransaction().commit();
            System.out.println("Cadastro finalizado com sucesso!");

        } catch (Exception e) {
            //voltando tudo, em caso de erro
            manager.getTransaction().rollback();
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        } finally {
            Util.desconectar();
        }
    }

    public static void main(String[] args) {
        new Cadastrar();
    }
}