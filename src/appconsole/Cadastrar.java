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
        Upa upa;

        paciente = new Paciente("13183543400", "João");
        upa = new Upa("Mangabeira");
        atendimento = new Atendimento(1, "29-03-2026", paciente, upa);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("11111111111", "Fulano da Silva");
        upa = new Upa("Mangabeira");
        atendimento = new Atendimento(2, "08-02-2026", paciente, upa);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("15654836502", "Antony Soprano");
        upa = new Upa("Bessa");
        atendimento = new Atendimento(3, "10-02-2026", paciente, upa);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("65896713506", "Maria do Carmo");
        upa = new Upa("Mangabeira");
        atendimento = new Atendimento(4, "23-01-2026", paciente, upa);
        manager.store(atendimento);
        manager.commit();

        paciente = new Paciente("68725461364", "Helena de Souza");
        upa = new Upa("Bessa");
        atendimento = new Atendimento(5, "01-01-2026", paciente, upa);
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
