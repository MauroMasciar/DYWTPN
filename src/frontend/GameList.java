package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
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
    private JComboBox<String> cbCategory = new JComboBox<String>();
    public static JTable tblGames = new JTable();
    private JScrollPane scrTable = new JScrollPane(tblGames);
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
	add(cbCategory, gbc);
	gbc.gridx = 0;
	gbc.gridy ++;
	gbc.gridwidth = 3;
	add(scrTable, gbc);
	
	cbCompleted.addItem("Todos");
	cbCompleted.addItem("Completados");
	cbCompleted.addItem("No completados");
	
	ArrayList<String> list = new ArrayList<String>();
	ModelGames mg = new ModelGames();
	list = mg.getGamesNameList(true);
	try {
	    cbGames.addItem("Todos");
	    for (int i = 1; i < list.size(); i++) {
		cbGames.addItem(list.get(i));
	    }
	} catch (ArrayIndexOutOfBoundsException ex) {
	    ex.printStackTrace();
	}
	
	list.clear();
	list = mg.getCategoryList();
	try {
	    cbCategory.addItem("Todos");
	    for (int i = 1; i < list.size(); i++) {
		cbCategory.addItem(list.get(i));
	    }
	} catch (ArrayIndexOutOfBoundsException ex) {
	    ex.printStackTrace();
	}	
	
	refreshTable();
	
	cbGames.addActionListener(this);
	cbCompleted.addActionListener(this);
	cbCategory.addActionListener(this);
	
	setVisible(true);
    }
    
    public void refreshTable() {
	ModelGames mg = new ModelGames();
	if(cbCompleted.getSelectedItem().toString() != "Todos" && cbGames.getSelectedItem().toString() != "Todos") cbCompleted.setSelectedItem("Todos");
	tblGames.setModel(mg.getFilteredGameList(cbGames.getSelectedItem().toString(), cbCompleted.getSelectedItem().toString(), cbCategory.getSelectedItem().toString()));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == cbCategory || e.getSource() == cbGames || e.getSource() == cbCompleted) {
	    refreshTable();
	}
    }
}
