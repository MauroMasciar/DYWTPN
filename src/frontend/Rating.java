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

import database.ModelGames;

public class Rating extends JInternalFrame implements ActionListener, MouseListener, InternalFrameListener {
    private static final long serialVersionUID = -1072944751022628676L;
    private final JComboBox<String> cbRating = new JComboBox<>();
    private static JList<String> jlistGames = new JList<>();
    private final JScrollPane scrListGame = new JScrollPane(jlistGames);
    private static DefaultListModel<String> modelList = new DefaultListModel<>();
    private final JButton btnEdit = new JButton("Editar");
    private final JButton btnAdd = new JButton("Añadir");

    public Rating() {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/category.png"));
            this.setFrameIcon(icon);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
        }
        setBounds(100, 100, 310, 600);
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
        add(cbRating, gbc);
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
        cbRating.addActionListener(this);
        jlistGames.addMouseListener(this);

        updateData();

        this.addInternalFrameListener(this);
        setVisible(true);
    }

    private void updateGameList() {
        jlistGames.removeAll();
        modelList.clear();

        ModelGames mg = new ModelGames();
        ArrayList<String> listRating = new ArrayList<>();
        listRating = mg.getGamesNameListRating(mg.getRatingIdFromName(cbRating.getSelectedItem().toString()));
        for(int i = 1; i < listRating.size(); i++) {
            modelList.addElement(listRating.get(i));
        }
        jlistGames.setModel(modelList);
        int nGames = listRating.size() - 1;
        setTitle("Rating " + cbRating.getSelectedItem().toString() + " | " + nGames + " juegos");
    }

    private void updateData() {
        ModelGames mg = new ModelGames();
        ArrayList<String> rating = mg.getRatingList();
        cbRating.removeAllItems();
        for(int i = 0; i < rating.size(); i++) {
            cbRating.addItem(rating.get(i));
        }
        updateGameList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnAdd) {
            String cat = JOptionPane.showInputDialog(this, "Ingrese el nombre del rating");
            try {
                if(cat.length() == 0) return;
            } catch(@SuppressWarnings("unused") NullPointerException ex) {
                return;
            }
            if(cat != "") {
                ModelGames mg = new ModelGames();
                int rp = mg.addRating(cat);
                if(rp == 1) {
                    JOptionPane.showMessageDialog(this, "El rating ha sido guardado");
                    updateData();
                } else {
                    JOptionPane.showMessageDialog(this, "Ha habido un error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == btnEdit) {
            String cat = JOptionPane.showInputDialog(this, "Ingrese el nuevo nombre del rating", cbRating.getSelectedItem().toString());
            if(cat == null)
                return;
            if(cat != "") {
                ModelGames mg = new ModelGames();
                int rp = mg.editRating(cbRating.getSelectedItem().toString(), cat);
                if(rp == 1) {
                    JOptionPane.showMessageDialog(this, "El rating ha sido actualizado");
                    updateData();
                } else {
                    JOptionPane.showMessageDialog(this, "Ha habido un error al actualizar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if(e.getSource() == cbRating) {
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
