package frontend;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import raven.datetime.component.time.TimePicker;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.raven.datechooser.DateChooser;
import database.ModelConfig;
import database.ModelGames;

public class AddSessionGame extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = -7862927860955738026L;
    private final JLabel lblGame = new JLabel(" Juego:");
    private final JLabel lblTime = new JLabel(" Minutos:");
    private final JLabel lblDate = new JLabel(" Fecha:");
    private final JLabel lblHour = new JLabel(" Hora:");
    private final JFormattedTextField txtTime = new JFormattedTextField();
    private final JTextField txtDate = new JTextField();
    private final DateChooser dcDate = new DateChooser();
    private TimePicker timePicker = new TimePicker();
    private final JComboBox<String> cbGame = new JComboBox<>();
    private final JSpinner spinTime = new JSpinner();
    private final SpinnerNumberModel spnModelTime = new SpinnerNumberModel();
    private final FlatButton btnAdd = new FlatButton();
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
        setBounds(100, 80, 400, 170);
        setClosable(true);
        setResizable(true);
        setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        btnAdd.setText("Añadir");
        timePicker.set24HourView(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipadx = 1;
        gbc.ipady = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblGame, gbc);
        gbc.gridx++;
        panel.add(cbGame, gbc);
        gbc.gridx = 0;
        gbc.gridy ++;
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
        panel.add(lblHour, gbc);
        gbc.gridx++;
        panel.add(txtTime, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(btnAdd, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panel, gbc);
        
        spnModelTime.setMinimum(0);
        spinTime.setModel(spnModelTime);
        spinTime.setToolTipText("El tiempo es en minutos");
        
        dcDate.hidePopup();
        dcDate.setDateFormat("yyyy-MM-dd");
        dcDate.setTextRefernce(txtDate);
        timePicker.setEditor(txtTime);

        txtDate.addActionListener(this);
        btnAdd.addActionListener(this);
               
        ModelConfig mc = new ModelConfig();
        
        ArrayList<String> gameList = new ArrayList<>();
        gameList = mg.getGamesNameList(false, mc.getOrderByDateSession());
        for(int i = 1; i < gameList.size(); i++) {
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
            /*if(mg.addSessionGame(gameId, cbGame.getSelectedItem().toString(), minsPlayed, txtDate.getText()) == 1) {
                ModelGames mg = new ModelGames();
                mg.newSession(gameId, minsPlayed*60);
                this.dispose();
                JOptionPane.showMessageDialog(this, "La sesión de juego se ha agregado satisfactoriamente", "Sesión añadida", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "La sesión de juego no se ha podido agregar. Verifica que todos los datos sean correctos", "Error", JOptionPane.ERROR_MESSAGE);
            }*/
            String date = txtDate.getText() + " " + timePicker.getSelectedTime() + ":00";
            mg.saveSession(gameId, minsPlayed*60, date);
            JOptionPane.showMessageDialog(this, "La sesión de juego se ha agregado satisfactoriamente", "Sesión añadida", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else if(e.getSource() == txtDate) {
            dcDate.showPopup();
        }
    }
}
