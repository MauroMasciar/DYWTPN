package frontend;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import com.raven.datechooser.DateChooser;

import database.ModelGames;

public class Statistics extends JInternalFrame implements ActionListener, ItemListener {
	private static final long serialVersionUID = -6616486842226855731L;
	private final JLabel lblDateInit = new JLabel("Inicio");
    private final JLabel lblDateEnd = new JLabel("Fin");
    private final JTextField txtDateInit = new JTextField();
    private final JTextField txtDateEnd = new JTextField();    
    private JFreeChart barChart = null;
    private JComboBox<String> cbEsts = new JComboBox<>();    
    private final DateChooser dcDateInit = new DateChooser();
    private final DateChooser dcDateEnd = new DateChooser();
    private JCheckBox chkbFiltered = new JCheckBox("Filtrar", true);
    private final JPanel panelOpt = new JPanel();
    private final JPanel panelChart = new JPanel();
    private ChartPanel chartPanel;
    public Statistics() {
        ModelGames mg = new ModelGames();
        if(mg.getTotalGames() == 0) {
            JOptionPane.showMessageDialog(this, "No tienes juegos en tu biblioteca", "No hay juegos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        setTitle("Estadísticas");
        setBounds(50, 50, 850, 550);
        setClosable(true);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.ipadx = 1;
        gbc.ipady = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelOpt.add(cbEsts, gbc);
        gbc.gridx ++;
        panelOpt.add(lblDateInit, gbc);
        gbc.gridx ++;
        panelOpt.add(txtDateInit, gbc);
        gbc.gridx ++;
        panelOpt.add(lblDateEnd, gbc);
        gbc.gridx ++;
        panelOpt.add(txtDateEnd, gbc);
        gbc.gridx ++;
        panelOpt.add(chkbFiltered, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(panelOpt, gbc);
        gbc.gridy ++;
        add(panelChart, gbc);
        
        dcDateInit.hidePopup();
        dcDateInit.setDateFormat("yyyy-MM-dd");
        dcDateInit.setTextRefernce(txtDateInit);        
        dcDateEnd.hidePopup();
        dcDateEnd.setDateFormat("yyyy-MM-dd");
        dcDateEnd.setTextRefernce(txtDateEnd);
        
        cbEsts.addItem("Sesiones");
        cbEsts.addItem("Tiempo de juego");
        
        txtDateEnd.addActionListener(this);
        txtDateInit.addActionListener(this);
        chkbFiltered.addActionListener(this);
        cbEsts.addItemListener(this);
        
        loadData(cbEsts.getSelectedIndex(), false);
        chkbFiltered.setSelected(true);

        setVisible(true);
    }
    
    private void loadData(int opt, boolean reload) {
        barChart = null;
        if(reload) panelChart.remove(chartPanel);
        chartPanel = null;
        
        if(opt == 0) {
            barChart = ChartFactory.createBarChart("", "Juegos", "Sesiones", createDataTotalSessions(), PlotOrientation.VERTICAL, false, true, false);
        } else if(opt == 1) {
            barChart = ChartFactory.createBarChart("", "Juegos", "Horas", createDataTotalHours(), PlotOrientation.VERTICAL, false, true, false);
        }
        
        chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(560, 367));
        panelChart.add(chartPanel);
        
        revalidate();
    }

    private CategoryDataset createDataTotalSessions() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ModelGames mg = new ModelGames();
        ArrayList<String> gameNameList = new ArrayList<>();
        gameNameList = mg.getStatisticsGamesNameList(chkbFiltered.isSelected());
        int id, count;
        for(int i = 0; i < gameNameList.size(); i++) {
            id = mg.getIdFromGameName(gameNameList.get(i));
            count = mg.getPlayCount(id);
            if(count > 0) {
                dataset.addValue(count, gameNameList.get(i), "");
            }
        }
        return dataset; 
    }
    
    private CategoryDataset createDataTotalHours() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ModelGames mg = new ModelGames();
        ArrayList<String> gameNameList = new ArrayList<>();
        gameNameList = mg.getStatisticsGamesNameList(chkbFiltered.isSelected());
        int id, count;
        for(int i = 0; i < gameNameList.size(); i++) {
            id = mg.getIdFromGameName(gameNameList.get(i));
            count = mg.getMinsPlayed(id) / 60;
            if(count > 0) {
                dataset.addValue(count, gameNameList.get(i), "");
            }
        }
        return dataset; 
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == txtDateInit) {
            dcDateInit.showPopup();
        } else if(e.getSource() == txtDateEnd) {
            dcDateEnd.showPopup();
        } else if(e.getSource() == chkbFiltered) {
            loadData(cbEsts.getSelectedIndex(), true);
        }
    }

    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == cbEsts) {
            loadData(cbEsts.getSelectedIndex(), true);
        }
    }
}
