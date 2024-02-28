package frontend;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import backend.Utils;
import database.ModelGames;

public class AddSessionGame extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = -7862927860955738026L;
    private final JLabel lblCbGame = new JLabel("Juego:");
    private final JLabel lblTime = new JLabel("Tiempo (Minutos): ");
    private final JLabel lblDate = new JLabel("Fecha:");
    private final JComboBox<String> cbGame = new JComboBox<>();
    private final JSpinner spinTime = new JSpinner();
    private final SpinnerNumberModel spnModelTime = new SpinnerNumberModel();
    private final JTextField txtDate = new JTextField(30);
    private final JButton btnAdd = new JButton("Añadir");
    private final ModelGames mg = new ModelGames();

    public AddSessionGame() {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/new_session.png"));
            this.setFrameIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
        }
        ModelGames mg = new ModelGames();
        if(mg.getTotalGames() == 0) {
            JOptionPane.showMessageDialog(this, "No tienes juegos en tu biblioteca", "No hay juegos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        setTitle("Añadir nueva sesion");
        setBounds(100, 80, 430, 150);
        setClosable(true);
        setResizable(true);
        setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblCbGame, gbc);
        gbc.gridx++;
        panel.add(cbGame, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(lblTime, gbc);
        gbc.gridx++;
        panel.add(spinTime, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(lblDate, gbc);
        gbc.gridx++;
        panel.add(txtDate, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(btnAdd, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panel, gbc);

        spnModelTime.setMinimum(0);
        spinTime.setModel(spnModelTime);
        txtDate.setText(Utils.getFormattedDateTime());

        spinTime.setToolTipText("El tiempo es en minutos");
        txtDate.setToolTipText("El formato es YYYY-MM-DD HH:MM:SS");

        btnAdd.addActionListener(this);

        ArrayList<String> gameList = new ArrayList<>();
        gameList = mg.getGamesNameList(false, false);
        for (int i = 1; i < gameList.size(); i++) {
            cbGame.addItem(gameList.get(i));
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAdd) {
            if((Integer) spinTime.getValue() == 0) {
                JOptionPane.showMessageDialog(this, "Debes especificar cuanto tiempo jugaste", "Faltan datos", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int minsPlayed = (Integer) spinTime.getValue();
            int gameId = mg.getIdFromGameName(cbGame.getSelectedItem().toString());
            if(mg.addSessionGame(gameId, cbGame.getSelectedItem().toString(), minsPlayed, txtDate.getText()) == 1) {
                ModelGames mg = new ModelGames();
                mg.setLastPlayed(gameId);
                mg.newSession(gameId);
                MainUI.loadData(false);                
                this.dispose();
                JOptionPane.showMessageDialog(this, "La sesion de juego se ha agregado satisfactoriamente", "Sesion añadida", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "La sesion de juego no se ha podido agregar. Verifica que todos los datos sean correctos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
