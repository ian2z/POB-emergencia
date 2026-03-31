package appconsole;

import com.db4o.ObjectContainer;

import modelo.Upa;
import modelo.Paciente;
import modelo.Atendimento;
import util.Util;

public class Cadastrar {

    public Cadastrar(){
        Util.conectar();
        ObjectContainer manager = Util.getManager();

        System.out.println("cadastrando objetos...");
        Paciente paciente;
        Atendimento atendimento;

        //criando Upas
        Upa mangabeira = new Upa("Mangabeira");
        Upa bessa = new Upa("Bessa");
        Upa bancarios = new Upa("Bancários");

        paciente = new Paciente("13567915644", "João Carvalho");
        atendimento = new Atendimento("29-03-2026", paciente, mangabeira);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("45847563216", "Sara Lopes");
        atendimento = new Atendimento("29-03-2026", paciente, mangabeira);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("12036405970", "Enzo Ramos");
        atendimento = new Atendimento("29-03-2026", paciente, mangabeira);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("11111111111", "Fulano da Silva");
        atendimento = new Atendimento("08-02-2026", paciente, mangabeira);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("15654836502", "Antony Soprano");
        atendimento = new Atendimento("10-02-2026", paciente, bessa);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("65896713506", "Maria do Carmo");
        atendimento = new Atendimento("23-01-2026", paciente, mangabeira);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("68725461364", "Helena de Souza");
        atendimento = new Atendimento("01-01-2026", paciente, bessa);
        manager.store(atendimento);
        manager.commit();

        //Realizando teste para paciente com mais de uma passagem em upas diferentes
        Paciente pacienteMario = new Paciente("22222222222", "Mario Castro");
        atendimento = new Atendimento("01-01-2026", pacienteMario, bessa);
        manager.store(atendimento);
        manager.commit();
        //registrando outra passagem na mesma Upa
        atendimento = new Atendimento("02-01-2026", pacienteMario, bessa);
        manager.store(atendimento);
        manager.commit();
        //cadastrando na upa mangabeira, com datas diferentes
        atendimento = new Atendimento("03-01-2026", pacienteMario, mangabeira);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("46851236577", "Sicrano de Torres");
        atendimento = new Atendimento("01-01-2026", paciente, bancarios);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("03694510235", "Julia Alencar");
        atendimento = new Atendimento("11-02-2026", paciente, bancarios);
        manager.store(atendimento);
        manager.commit();

        Util.desconectarBanco();
        System.out.println("finalizando cadastro...");
    }

    public void cadastrar(){

    }

    public static void main(String[] args) {
        new Cadastrar();
    }
}

