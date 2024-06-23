package frontend;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import database.ModelGames;

public class Wishlist extends JInternalFrame implements ActionListener {
    private JList<String> jlGames = new JList<>();
    private JScrollPane scrLGames = new JScrollPane(jlGames);
    private DefaultListModel<String> modelList = new DefaultListModel<>();
    private JButton btnNew = new JButton("Nuevo");
    private JButton btnEdit = new JButton("Editar");
    private JButton btnDelete = new JButton("Borrar");
    
    public Wishlist() {
        setTitle("Lista de deseos");
        setBounds(100, 100, 300, 230);
        setClosable(true);
        setResizable(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 4.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrLGames, gbc);
        gbc.gridy += 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weighty = 1.0;
        add(btnNew, gbc);
        gbc.gridx ++;
        add(btnEdit, gbc);
        gbc.gridx ++;
        add(btnDelete, gbc);
        
        btnNew.addActionListener(this);
        
        updateList();
        
        setVisible(true);
    }
    
    private void updateList() {
        jlGames.removeAll();
        modelList.clear();
        ModelGames mg = new ModelGames();
        ArrayList<String> listGames = new ArrayList<>();
        listGames = mg.getWishlist();
        
        for(int i = 1; i < listGames.size(); i++) {
            modelList.addElement(listGames.get(i));
        }
        jlGames.setModel(modelList);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnNew) {
            String name = JOptionPane.showInputDialog(this, "Ingrese el nombre del juego");
            if(name == null || name.length() == 0) return;
            ModelGames mg = new ModelGames();
            mg.newWish(name);
            updateList();
        }
    }
}
