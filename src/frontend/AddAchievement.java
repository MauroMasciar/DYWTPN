package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import backend.Validations;
import database.ModelConfig;
import database.ModelGames;
import database.ModelPlayer;

public class AddAchievement extends JInternalFrame implements ActionListener {
    private JPanel pnl = new JPanel();
    private final JLabel lblGame = new JLabel("Juego: ");
    private final JLabel lblAchiev = new JLabel("Hazaña: ");
    private JComboBox<String> cbGame = new JComboBox<>();
    private JTextField txtAchiev = new JTextField(10);
    private JButton btnAdd = new JButton("Añadir");
    
    public AddAchievement() {
        setTitle("Añadir nueva hazaña");
        setBounds(20, 20, 400, 170);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setClosable(true);
        setResizable(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        pnl.setLayout(new GridBagLayout());
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipadx = 10;
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnl.add(lblGame, gbc);
        gbc.gridx++;
        pnl.add(cbGame, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        pnl.add(lblAchiev, gbc);
        gbc.gridx++;
        pnl.add(txtAchiev, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        pnl.add(btnAdd, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 0;
        gbc.gridx = 0;
        add(pnl);
        
        btnAdd.addActionListener(this);
        
        loadGamesList();
        
        setVisible(true);
    }
    
    private void loadGamesList() {
        ModelGames mg = new ModelGames();
        ModelConfig mc = new ModelConfig();
        ArrayList<String> gameList = new ArrayList<>();
        gameList = mg.getGamesNameList(false, mc.getOrderByDateAchiev(), false);
        for(int i = 1; i < gameList.size(); i++) {
            cbGame.addItem(gameList.get(i));
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAdd) {
            if(Validations.isEmpty(txtAchiev)) {
                JOptionPane.showMessageDialog(this, "Debes introducir un texto", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ModelGames mg = new ModelGames();
            int gameId = mg.getIdFromGameName(cbGame.getSelectedItem().toString());
            
            ModelPlayer mp = new ModelPlayer();
            mp.saveAchievement(txtAchiev.getText(), mg.getNameFromId(gameId), gameId);
            MainUI.loadAchievs();
            JOptionPane.showMessageDialog(this, "Hazaña añadida", "OK", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }
}
