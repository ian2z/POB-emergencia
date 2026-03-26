package util;

import java.util.Properties;
import javax.swing.JOptionPane;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

// IMPORTANTE: Importe suas classes de modelo
import modelo.Atendimento;
import modelo.Paciente;
import modelo.Upa;

public class Util {
    private static ObjectContainer manager;

    public static ObjectContainer conectarBanco() {
        if (manager != null)
            return manager;

        try {
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();

            // Configuração de Cascata para o Tema EMERGÊNCIA
            // Paciente
            config.common().objectClass(Paciente.class).cascadeOnUpdate(true);
            config.common().objectClass(Paciente.class).cascadeOnActivate(true);
            config.common().objectClass(Paciente.class).cascadeOnDelete(false);

            // Upa
            config.common().objectClass(Upa.class).cascadeOnUpdate(true);
            config.common().objectClass(Upa.class).cascadeOnActivate(true);
            config.common().objectClass(Upa.class).cascadeOnDelete(false);

            // Atendimento
            config.common().objectClass(Atendimento.class).cascadeOnUpdate(true);
            config.common().objectClass(Atendimento.class).cascadeOnActivate(true);
            config.common().objectClass(Atendimento.class).cascadeOnDelete(false);

            // Abre o arquivo local
            manager = Db4oEmbedded.openFile(config, "banco.db4o");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir banco: " + e.getMessage());
            System.exit(0);
        }
        return manager;
    }

    public static void desconectarBanco() {
        if (manager != null) {
            manager.close();
            manager = null;
        }
    }
}