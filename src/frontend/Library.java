package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.JComboBox;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import backend.Utils;
import database.ModelGames;

public class Library extends JInternalFrame implements ActionListener, MouseListener, InternalFrameListener {
    private static final long serialVersionUID = -1072944751022628676L;
    private final JComboBox<String> cbLibrary = new JComboBox<>();
    private static JList<String> jlistGames = new JList<>();
    private final JScrollPane scrListGame = new JScrollPane(jlistGames);
    private static DefaultListModel<String> modelList = new DefaultListModel<>();
    private final JButton btnEdit = new JButton("Editar");
    private final JButton btnAdd = new JButton("Añadir");

    public Library() {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/library.png"));
            this.setFrameIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
        }
        setBounds(100, 100, 330, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setClosable(true);
        setResizable(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.ipadx = 20;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(cbLibrary, gbc);
        gbc.gridx++;
        add(btnEdit, gbc);
        gbc.gridx++;
        add(btnAdd, gbc);
        gbc.gridx = 0;
        gbc.gridy+=2;
        gbc.gridheight = 3;
        gbc.gridwidth = 3;
        gbc.weightx = 3.0;
        gbc.weighty = 3.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrListGame, gbc);

        btnAdd.addActionListener(this);
        btnEdit.addActionListener(this);
        cbLibrary.addActionListener(this);
        jlistGames.addMouseListener(this);

        updateData();

        setVisible(true);
    }
    
    private void updateGameList() {
        jlistGames.removeAll();
        modelList.clear();
        
        ModelGames mg = new ModelGames();
        int library_id = mg.getLibraryIdFromName((cbLibrary.getSelectedItem().toString()));
        ArrayList<String> listLibrary = new ArrayList<>();
        listLibrary = mg.getGamesNameListLibrary(library_id);
        for(int i = 1; i < listLibrary.size(); i++) {
            modelList.addElement(listLibrary.get(i) + " (" + Utils.getTotalHoursFromSeconds(mg.getSecondsPlayed(mg.getIdFromGameName(listLibrary.get(i))), true) + ")");
        }
        jlistGames.setModel(modelList);
        int nGames = listLibrary.size() - 1;
        int time_played = mg.getLibraryTimePlayed(library_id);
        setTitle(cbLibrary.getSelectedItem().toString() + " | " + nGames + " juegos | " + Utils.getTotalHoursFromSeconds(time_played, true));
    }

    private void updateData() {
        ModelGames mg = new ModelGames();
        ArrayList<String> library = mg.getLibraryList();
        cbLibrary.removeAllItems();
        for (int i = 0; i < library.size(); i++) {
            cbLibrary.addItem(library.get(i));
        }
        updateGameList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAdd) {
            String cat = JOptionPane.showInputDialog(this, "Ingrese el nombre de la biblioteca");
            if(cat.length() == 0) return;
            if(cat != "") {
                ModelGames mg = new ModelGames();
                int rp = mg.addLibrary(cat);
                if (rp == 1) {
                    JOptionPane.showMessageDialog(this, "La biblioteca ha sido guardada");
                    updateData();
                } else {
                    JOptionPane.showMessageDialog(this, "Ha habido un error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == btnEdit) {
            String cat = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre de la biblioteca", cbLibrary.getSelectedItem().toString());
            if(cat == null)
                return;
            if(cat != "") {
                ModelGames mg = new ModelGames();
                int rp = mg.editLibrary(cbLibrary.getSelectedItem().toString(), cat);
                if(rp == 1) {
                    JOptionPane.showMessageDialog(this, "La biblioteca ha sido actualizada");
                    updateData();
                } else {
                    JOptionPane.showMessageDialog(this, "Ha habido un error al actualizar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == cbLibrary) {
            updateGameList();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == jlistGames && e.getClickCount() == 2) {
            ModelGames mg = new ModelGames();
            MainWindow.j.add(new EditGame(mg.getIdFromGameName(jlistGames.getSelectedValue())));
            MainWindow.j.repaint();
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void internalFrameOpened(InternalFrameEvent e) {
    }

    public void internalFrameClosing(InternalFrameEvent e) {
        jlistGames.removeMouseListener(this);
    }

    public void internalFrameClosed(InternalFrameEvent e) {
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameActivated(InternalFrameEvent e) {
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}
