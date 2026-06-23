package visual;

import javax.swing.*;
import java.awt.event.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Sistema de Emergência UPA");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenu menuConsultas = new JMenu("Consultas");

        JMenuItem itemPaciente = new JMenuItem("Pacientes");
        JMenuItem itemUpa = new JMenuItem("UPAs");
        JMenuItem itemAtendimento = new JMenuItem("Atendimentos");
        JMenuItem itemConsultas = new JMenuItem("Painel de Consultas");

        menuCadastros.add(itemPaciente);
        menuCadastros.add(itemUpa);
        menuCadastros.add(itemAtendimento);
        menuConsultas.add(itemConsultas);

        menuBar.add(menuCadastros);
        menuBar.add(menuConsultas);
        setJMenuBar(menuBar);

        itemPaciente.addActionListener(e -> new TelaPaciente().setVisible(true));
        itemUpa.addActionListener(e -> new TelaUpa().setVisible(true));
        itemAtendimento.addActionListener(e -> new TelaAtendimento().setVisible(true));
        itemConsultas.addActionListener(e -> new TelaConsultas().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}