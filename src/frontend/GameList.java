package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.util.ArrayList;

import database.ModelGames;

public class GameList extends JInternalFrame implements ActionListener {
    private static final long serialVersionUID = -4569846762834010208L;
    private JComboBox<String> cbGames = new JComboBox<String>();
    private JComboBox<String> cbCompleted = new JComboBox<String>();
    public static JTable tblGames = new JTable();
    private JScrollPane scrTable = new JScrollPane(tblGames);
    private JButton btnShow = new JButton("Mostrar");
    public GameList() {
	setTitle("Lista de juegos");
	setBounds(50, 50, 800, 500);
	setClosable(true);
	setResizable(true);
	setLayout(new GridBagLayout());
	
	GridBagConstraints gbc = new GridBagConstraints();	
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.weightx = 1.0;
	gbc.weighty = 1.0;
	gbc.ipadx = 1;
	gbc.ipady = 1;
	gbc.fill = GridBagConstraints.BOTH;
	gbc.gridx = 0;
	gbc.gridy = 0;
	add(cbGames, gbc);
	gbc.gridx ++;
	add(cbCompleted, gbc);
	gbc.gridx ++;
	add(btnShow, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	gbc.gridwidth = 3;
	add(scrTable, gbc);
	
	cbCompleted.addItem("Todos");
	cbCompleted.addItem("Completados");
	cbCompleted.addItem("No completados");
	
	ArrayList<String> listGames = new ArrayList<String>();
	ModelGames mg = new ModelGames();
	listGames = mg.getGamesNameList();
	try {
	    cbGames.addItem("Todos");
	    for (int i = 1; i < listGames.size(); i++) {
		cbGames.addItem(listGames.get(i));
	    }
	} catch (ArrayIndexOutOfBoundsException ex) {
	    ex.printStackTrace();
	}
	
	refreshTable();
	
	cbGames.addActionListener(this);
	cbCompleted.addActionListener(this);
	btnShow.addActionListener(this);
	
	setVisible(true);
    }
    
    public void refreshTable() {
	ModelGames mg = new ModelGames();
	if(cbCompleted.getSelectedItem().toString() != "Todos" && cbGames.getSelectedItem().toString() != "Todos") cbCompleted.setSelectedItem("Todos");
	tblGames.setModel(mg.getFilteredGameList(cbGames.getSelectedItem().toString(), cbCompleted.getSelectedItem().toString()));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnShow || e.getSource() == cbGames || e.getSource() == cbCompleted) {
	    refreshTable();
	}
    }
}
