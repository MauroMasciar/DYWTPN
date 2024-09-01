package frontend;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import database.ModelConfig;
import database.ModelGames;
import database.ModelPlayer;

public class PlayerHistory extends JInternalFrame implements ActionListener, KeyListener, InternalFrameListener {
    private static final long serialVersionUID = 4484286064012240569L;
    private final JComboBox<String> cbGames = new JComboBox<>();
    private final JScrollPane scrTable = new JScrollPane(tbPlayerHistory);
    public static final JTable tbPlayerHistory = new JTable();
    private boolean upd = true;
    private boolean open = true;

    public PlayerHistory() {
        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("gfx/history.png"));
            this.setFrameIcon(icon);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No se ha podido cargar algunos recursos.", "Error en la carga de recursos", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        ModelGames mg = new ModelGames();
        if(mg.getTotalGames() == 0) {
            JOptionPane.showMessageDialog(this, "No tienes juegos en tu biblioteca", "No hay juegos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ModelConfig mc = new ModelConfig();
        setBounds(mc.getBounds_x("History"), mc.getBounds_y("History"), 500, 500);
        setTitle("Historial");
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
        gbc.gridy++;
        add(scrTable, gbc);

        loadHistory();

        cbGames.addActionListener(this);
        tbPlayerHistory.addKeyListener(this);
        
        this.addInternalFrameListener(this);

        setVisible(true);
    }

    public void loadHistory() {
        ArrayList<String> listGames = new ArrayList<>();
        ModelGames mg = new ModelGames();
        listGames = mg.getGamesNameList(true, false);
        cbGames.removeAllItems();
        cbGames.addItem("Todos");

        try {
            for(int i = 1; i < listGames.size(); i++) {
                cbGames.addItem(listGames.get(i));
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        ModelPlayer model = new ModelPlayer();
        tbPlayerHistory.setModel(model.getHistory(cbGames.getSelectedItem().toString()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cbGames) {
            if(upd) {
                ModelPlayer model = new ModelPlayer();
                tbPlayerHistory.setModel(model.getHistory(cbGames.getSelectedItem().toString()));
            }
        }
    }

    public void keyTyped(KeyEvent e) {
        
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_DELETE && open) {
            int re = JOptionPane.showConfirmDialog(this, "¿Desea borrar esta sesión del historial?", "Borrar del historial", JOptionPane.YES_NO_OPTION);
            if(re == 0) {
                ModelGames mg = new ModelGames();
                int pos = (Integer)tbPlayerHistory.getValueAt(tbPlayerHistory.getSelectedRow(), 0);
                mg.deleteItemHistory(pos);
                upd = false;
                loadHistory();
                
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            upd = true;
                            MainUI.loadData(false, false);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                            System.exit(0);
                        }
                    }
                }).start();
            }
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void internalFrameOpened(InternalFrameEvent e) {
    }

    public void internalFrameClosing(InternalFrameEvent e) {
        open = false;
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
