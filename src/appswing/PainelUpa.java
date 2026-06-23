package appswing;

import fachada.FachadaUpa;
import modelo.Upa;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PainelUpa extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private FachadaUpa fachada = new FachadaUpa();

    public PainelUpa() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JLabel lblTitle = new JLabel("Gerenciar UPAs");
        lblTitle.setFont(Theme.FONT_TITLE);
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        add(lblTitle, BorderLayout.NORTH);

        // Table
        String[] columns = {"ID", "Nome da UPA"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(Theme.FONT_NORMAL);
        table.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Theme.BACKGROUND_COLOR);

        JButton btnNovo = new JButton("Nova UPA");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        btnNovo.setFont(Theme.FONT_NORMAL);
        btnAtualizar.setFont(Theme.FONT_NORMAL);

        btnPanel.add(btnAtualizar);
        btnPanel.add(btnNovo);

        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        btnAtualizar.addActionListener(e -> carregarUpas());
        
        btnNovo.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Digite o nome da UPA:", "Nova UPA", JOptionPane.PLAIN_MESSAGE);
            if (nome != null && !nome.trim().isEmpty()) {
                try {
                    fachada.cadastrarUpa(nome);
                    JOptionPane.showMessageDialog(this, "UPA cadastrada com sucesso!");
                    carregarUpas();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        carregarUpas();
    }

    private void carregarUpas() {
        tableModel.setRowCount(0);
        try {
            List<Upa> upas = fachada.listarUpas();
            for (Upa u : upas) {
                tableModel.addRow(new Object[]{u.getId(), u.getNome()});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
