package appswing;

import requisito.FachadaPaciente;
import modelo.Paciente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class PainelPaciente extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private FachadaPaciente fachada = new FachadaPaciente();

    public PainelPaciente() {
        setLayout(new BorderLayout());
        setBackground(Theme.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JLabel lblTitle = new JLabel("Gerenciar Pacientes");
        lblTitle.setFont(Theme.FONT_TITLE);
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        add(lblTitle, BorderLayout.NORTH);

        // Table
        String[] columns = {"CPF", "Nome"};
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

        JButton btnNovo = new JButton("Novo Paciente");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnDetalhes = new JButton("Ver Detalhes");
        JButton btnAtualizar = new JButton("Atualizar Lista");

        btnNovo.setFont(Theme.FONT_NORMAL);
        btnEditar.setFont(Theme.FONT_NORMAL);
        btnExcluir.setFont(Theme.FONT_NORMAL);
        btnDetalhes.setFont(Theme.FONT_NORMAL);
        btnAtualizar.setFont(Theme.FONT_NORMAL);

        btnPanel.add(btnAtualizar);
        btnPanel.add(btnDetalhes);
        btnPanel.add(btnExcluir);
        btnPanel.add(btnEditar);
        btnPanel.add(btnNovo);

        add(btnPanel, BorderLayout.SOUTH);

        // Listeners
        btnAtualizar.addActionListener(e -> carregarPacientes());
        
        btnNovo.addActionListener(e -> {
            JTextField txtCpf = new JTextField();
            JTextField txtNome = new JTextField();
            JButton btnFoto = new JButton("Escolher Foto...");
            JLabel lblFoto = new JLabel("Nenhuma foto selecionada");
            
            final byte[][] fotoBytes = {null};

            btnFoto.addActionListener(ev -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png"));
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        fotoBytes[0] = java.nio.file.Files.readAllBytes(file.toPath());
                        lblFoto.setText(file.getName());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao ler foto: " + ex.getMessage());
                    }
                }
            });

            Object[] message = {
                "CPF:", txtCpf,
                "Nome:", txtNome,
                "Foto:", btnFoto, lblFoto
            };
            int option = JOptionPane.showConfirmDialog(this, message, "Novo Paciente", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    fachada.cadastrarPaciente(txtCpf.getText(), txtNome.getText(), fotoBytes[0]);
                    JOptionPane.showMessageDialog(this, "Paciente cadastrado com sucesso!");
                    carregarPacientes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExcluir.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String cpf = (String) tableModel.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o paciente " + cpf + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        fachada.excluirPaciente(cpf);
                        carregarPacientes();
                        JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um paciente para excluir.");
            }
        });
        
        btnEditar.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String cpf = (String) tableModel.getValueAt(selectedRow, 0);
                String nomeAtual = (String) tableModel.getValueAt(selectedRow, 1);
                
                JTextField txtNome = new JTextField(nomeAtual);
                JButton btnFoto = new JButton("Escolher Nova Foto...");
                JLabel lblFoto = new JLabel("Manter foto atual");
                
                final byte[][] fotoBytes = {null};

                btnFoto.addActionListener(ev -> {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png"));
                    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        try {
                            fotoBytes[0] = java.nio.file.Files.readAllBytes(file.toPath());
                            lblFoto.setText(file.getName());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Erro ao ler foto: " + ex.getMessage());
                        }
                    }
                });

                Object[] message = {
                    "CPF: " + cpf,
                    "Novo Nome:", txtNome,
                    "Atualizar Foto:", btnFoto, lblFoto
                };
                int option = JOptionPane.showConfirmDialog(this, message, "Editar Paciente", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        fachada.atualizarPaciente(cpf, txtNome.getText(), fotoBytes[0]);
                        JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
                        carregarPacientes();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um paciente para editar.");
            }
        });

        btnDetalhes.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String cpf = (String) tableModel.getValueAt(selectedRow, 0);
                try {
                    Paciente p = fachada.buscarPaciente(cpf);
                    if (p != null) {
                        exibirDetalhesPaciente(p);
                    } else {
                        JOptionPane.showMessageDialog(this, "Paciente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao buscar detalhes: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um paciente para ver detalhes.");
            }
        });

        carregarPacientes();
    }

    private void exibirDetalhesPaciente(Paciente p) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Detalhes do Paciente", true);
        dialog.setLayout(new BorderLayout(15, 15));
        dialog.getContentPane().setBackground(Theme.BACKGROUND_COLOR);

        // Header Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Theme.PRIMARY_COLOR);
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JLabel lblTitle = new JLabel("Ficha do Paciente");
        lblTitle.setFont(Theme.FONT_TITLE);
        lblTitle.setForeground(Theme.SECONDARY_COLOR);
        titlePanel.add(lblTitle);
        dialog.add(titlePanel, BorderLayout.NORTH);

        // Main Panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Theme.BACKGROUND_COLOR);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.BOTH;

        // Photo Panel
        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.setBackground(Theme.BACKGROUND_COLOR);
        photoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.PRIMARY_COLOR, 1), "Foto"));
        photoPanel.setPreferredSize(new Dimension(170, 170));

        JLabel lblFotoContainer = new JLabel();
        lblFotoContainer.setHorizontalAlignment(SwingConstants.CENTER);
        
        byte[] fotoBytes = p.getFoto();
        if (fotoBytes != null && fotoBytes.length > 0) {
            ImageIcon originalIcon = new ImageIcon(fotoBytes);
            Image image = originalIcon.getImage();
            Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblFotoContainer.setIcon(new ImageIcon(scaledImage));
        } else {
            lblFotoContainer.setText("Sem foto");
            lblFotoContainer.setFont(Theme.FONT_NORMAL);
            lblFotoContainer.setForeground(Color.GRAY);
        }
        photoPanel.add(lblFotoContainer, BorderLayout.CENTER);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBackground(Theme.BACKGROUND_COLOR);
        GridBagConstraints gbcInfo = new GridBagConstraints();
        gbcInfo.anchor = GridBagConstraints.WEST;
        gbcInfo.insets = new Insets(5, 5, 5, 5);

        JLabel lblCpfTag = new JLabel("CPF: ");
        lblCpfTag.setFont(Theme.FONT_SUBTITLE);
        lblCpfTag.setForeground(Theme.TEXT_COLOR);
        JLabel lblCpfVal = new JLabel(p.getCpf());
        lblCpfVal.setFont(Theme.FONT_NORMAL);

        JLabel lblNomeTag = new JLabel("Nome: ");
        lblNomeTag.setFont(Theme.FONT_SUBTITLE);
        lblNomeTag.setForeground(Theme.TEXT_COLOR);
        JLabel lblNomeVal = new JLabel(p.getNome());
        lblNomeVal.setFont(Theme.FONT_NORMAL);

        gbcInfo.gridx = 0; gbcInfo.gridy = 0;
        infoPanel.add(lblCpfTag, gbcInfo);
        gbcInfo.gridx = 1;
        infoPanel.add(lblCpfVal, gbcInfo);

        gbcInfo.gridx = 0; gbcInfo.gridy = 1;
        infoPanel.add(lblNomeTag, gbcInfo);
        gbcInfo.gridx = 1;
        infoPanel.add(lblNomeVal, gbcInfo);

        // Attendances Panel
        JPanel atendimentosPanel = new JPanel(new BorderLayout());
        atendimentosPanel.setBackground(Theme.BACKGROUND_COLOR);
        atendimentosPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Theme.PRIMARY_COLOR, 1), "Histórico de Atendimentos"));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        if (p.getAtendimentos() != null && !p.getAtendimentos().isEmpty()) {
            for (modelo.Atendimento a : p.getAtendimentos()) {
                listModel.addElement("Data: " + a.getData() + " | UPA: " + a.getUpa().getNome() + " | Triagem: " + a.getTriagem());
            }
        } else {
            listModel.addElement("Nenhum atendimento registrado.");
        }
        
        JList<String> listAtendimentos = new JList<>(listModel);
        listAtendimentos.setFont(Theme.FONT_NORMAL);
        JScrollPane listScroller = new JScrollPane(listAtendimentos);
        listScroller.setPreferredSize(new Dimension(380, 100));
        atendimentosPanel.add(listScroller, BorderLayout.CENTER);

        // GridBag layout assembly
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridheight = 2;
        gbc.weightx = 0.3; gbc.weighty = 1.0;
        mainPanel.add(photoPanel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridheight = 1;
        gbc.weightx = 0.7; gbc.weighty = 0.3;
        mainPanel.add(infoPanel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.gridheight = 1;
        gbc.weightx = 0.7; gbc.weighty = 0.7;
        mainPanel.add(atendimentosPanel, gbc);

        dialog.add(mainPanel, BorderLayout.CENTER);

        // Footer / Close button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Theme.BACKGROUND_COLOR);
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(Theme.FONT_NORMAL);
        btnFechar.addActionListener(ev -> dialog.dispose());
        footerPanel.add(btnFechar);
        dialog.add(footerPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setSize(620, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void carregarPacientes() {
        tableModel.setRowCount(0);
        try {
            List<Paciente> pacientes = fachada.listarPacientes();
            for (Paciente p : pacientes) {
                tableModel.addRow(new Object[]{p.getCpf(), p.getNome()});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
