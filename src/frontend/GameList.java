package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
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
    private final JComboBox<String> cbGames = new JComboBox<>();
    private final JComboBox<String> cbCompleted = new JComboBox<>();
    private final JComboBox<String> cbCategory = new JComboBox<>();
    private final JComboBox<String> cbFilter = new JComboBox<>();
    public static final JTable tblGames = new JTable();
    private final JScrollPane scrTable = new JScrollPane(tblGames);

    public GameList() {
	try {
	    ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/games_list.png"));
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
	gbc.gridx++;
	add(cbCompleted, gbc);
	gbc.gridx++;
	add(cbCategory, gbc);
	gbc.gridx ++;
	add(cbFilter, gbc);
	gbc.gridx = 0;
	gbc.gridy++;
	gbc.gridwidth = 4;
	add(scrTable, gbc);
	
	cbCompleted.addItem("Todos");
	cbCompleted.addItem("Completados");
	cbCompleted.addItem("No completados");
	
	cbFilter.addItem("Nombre");
	cbFilter.addItem("Tiempo");
	cbFilter.addItem("Veces");
	cbFilter.addItem("Categoria");
	cbFilter.addItem("Completado");
	cbFilter.addItem("Puntos");

	ArrayList<String> list = new ArrayList<>();
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
	cbFilter.addActionListener(this);

	setVisible(true);
    }

    private void refreshTable() {
	ModelGames mg = new ModelGames();
	if(cbCompleted.getSelectedItem().toString() != "Todos" && cbGames.getSelectedItem().toString() != "Todos") {
	    cbCompleted.setSelectedItem("Todos");
	} else if(cbCategory.getSelectedItem().toString() != "Todos" && cbGames.getSelectedItem().toString() != "Todos") {
	    cbGames.setSelectedItem("Todos");
	}
	tblGames.setModel(mg.getFilteredGameList(cbGames.getSelectedItem().toString(), cbCompleted.getSelectedItem().toString(), cbCategory.getSelectedItem().toString(), cbFilter.getSelectedItem().toString()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if(e.getSource() == cbCategory || e.getSource() == cbGames || e.getSource() == cbCompleted || e.getSource() == cbFilter) {
	    refreshTable();
	}
    }
}
