package modelo;

import java.util.ArrayList;

public class Upa {
    private String nome;
    private ArrayList<Paciente> lista_atendimento = new ArrayList<Paciente>() ;

    public Upa(String nome, ArrayList<Paciente> lista_atendimento){
        this.nome = nome;
        this.lista_atendimento = lista_atendimento;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }
    public ArrayList<Paciente> getLista_atendimento(){
        return lista_atendimento;
    }
}
