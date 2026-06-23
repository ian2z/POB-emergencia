package appswing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public TelaPrincipal() {
        setTitle("Sistema de Emergência - Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("nimbusBase", Theme.PRIMARY_COLOR);
            UIManager.put("nimbusBlueGrey", Theme.BACKGROUND_COLOR);
            UIManager.put("control", Theme.SECONDARY_COLOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Theme.SIDEBAR_COLOR);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel lblTitle = new JLabel("EMERGÊNCIA");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Theme.PRIMARY_COLOR);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitle = new JLabel("Sistema de Gestão");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(Color.LIGHT_GRAY);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidebar.add(lblTitle);
        sidebar.add(lblSubtitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton btnPacientes = createMenuButton("Pacientes");
        JButton btnUpas = createMenuButton("UPAs");
        JButton btnAtendimentos = createMenuButton("Atendimentos");

        sidebar.add(btnPacientes);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnUpas);
        sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        sidebar.add(btnAtendimentos);

        add(sidebar, BorderLayout.WEST);

        // Content Area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Theme.BACKGROUND_COLOR);

        contentPanel.add(new PainelPaciente(), "Pacientes");
        contentPanel.add(new PainelUpa(), "UPAs");
        contentPanel.add(new PainelAtendimento(), "Atendimentos");

        add(contentPanel, BorderLayout.CENTER);

        // Action Listeners
        btnPacientes.addActionListener((ActionEvent e) -> cardLayout.show(contentPanel, "Pacientes"));
        btnUpas.addActionListener((ActionEvent e) -> cardLayout.show(contentPanel, "UPAs"));
        btnAtendimentos.addActionListener((ActionEvent e) -> cardLayout.show(contentPanel, "Atendimentos"));
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(Theme.FONT_SUBTITLE);
        btn.setForeground(Theme.SECONDARY_COLOR);
        btn.setBackground(Theme.SIDEBAR_COLOR.brighter());
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
