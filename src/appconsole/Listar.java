package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;
import util.Util;

public class Listar {

    public Listar(){
        Util.conectar();
        ObjectContainer manager = Util.getManager();

        Query q;

        System.out.println("\n---listagem de pacientes:");
        q = manager.query();
        q.constrain(Paciente.class);
        List<Paciente> pacientes = q.execute();
        for(Paciente a: pacientes){
            System.out.println(a);
        }


        System.out.println("\n---listagem de atendimentos:");
        q = manager.query();
        q.constrain(Atendimento.class);
        List<Atendimento> atendimentos = q.execute();
        for(Atendimento m: atendimentos){
            System.out.println(m);
        }


        System.out.println("\n---listagem de UPAs:");
        q = manager.query();
        q.constrain(Upa.class);
        List<Upa> upas = q.execute();
        for(Upa m: upas){
            System.out.println(m);
        }
        Util.desconectarBanco();

        System.out.println("\n aviso: feche sempre o plugin OME antes de executar aplicação");
    }



    //=================================================
    public static void main(String[] args) {
        new Listar();
    }
}

