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
import javax.swing.BorderFactory;
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

public class Statistics extends JInternalFrame {
	private static final long serialVersionUID = -6616486842226855731L;
	
	public Statistics() {
		ModelGames mg = new ModelGames();
		if(mg.getTotalGames() == 0) {
			JOptionPane.showMessageDialog(this, "No tienes juegos en tu biblioteca", "No hay juegos", JOptionPane.ERROR_MESSAGE);
			return;
		}
		setTitle("Estadísticas");
		setBounds(50, 50, 850, 850);
		setClosable(true);
		setResizable(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.ipadx = 1;
		gbc.ipady = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;

		add(new panelChart(), gbc);
		gbc.gridy ++;
		add(new panelStatistics(), gbc);

		setVisible(true);
	}
}

class panelStatistics extends JPanel {
	private static final long serialVersionUID = -608394120070090678L;
	private JLabel lblName = new JLabel("Nombre: ");
	private JLabel lblRegisterDate = new JLabel("Fecha de registro: ");
	private JLabel lblHoursLastDay = new JLabel("Horas ultimo día: ");
	private JLabel lblHoursLastWeek = new JLabel("Horas ultima semana: ");
	private JLabel lblHoursLastTwoWeek = new JLabel("Horas ultimas dos semanas: ");
	private JLabel lblHoursLastMonth = new JLabel("Horas ultimo mes: ");
	private JLabel lblHoursLastYear = new JLabel("Horas ultimo año: ");
	private JLabel lblHoursTotal = new JLabel("Horas totales: ");
	private JLabel lblTotalInitGames = new JLabel("Total de juegos iniciados: ");
	private JLabel lblTotalCompletedGames = new JLabel("Total de juegos completados: ");
	private JLabel lblTotalPorcentCompletedGames = new JLabel("% de juegos completados: ");
	private JLabel lblTotalSessions = new JLabel("Total de sesiones: ");
	private JLabel lblGameMostPlayedHours = new JLabel("Juego con mas horas: ");
	private JLabel lblGameMostPlayerInit = new JLabel("Juego con mas sesiones: ");
	private JLabel lblGameTopAchievement = new JLabel("Juego con mas hazañas");
	private JLabel lblGameOfYear = new JLabel("Juego del año: ");
	private JLabel lblGameOfMonth = new JLabel("Juego del mes: ");
	private JLabel lblTopLibrary = new JLabel("Biblioteca mas jugada: ");
	private JLabel lblTopPlatform = new JLabel("Plataforma mas jugada: ");
	private JLabel lblTopCategory = new JLabel("Categoria mas jugada: ");
	private JTextField txtName = new JTextField();
	private JTextField txtRegisterDate = new JTextField(10);
	private JTextField txtHoursLastDay = new JTextField();
	private JTextField txtHoursLastWeek = new JTextField();
	private JTextField txtHoursLastTwoWeek = new JTextField();
	private JTextField txtHoursLastMonth = new JTextField();
	private JTextField txtHoursLastYear = new JTextField();
	private JTextField txtHoursTotal = new JTextField();
	private JTextField txtTotalInitGames = new JTextField();
	private JTextField txtTotalCompletedGames = new JTextField();
	private JTextField txtTotalPorcentCompletedGames = new JTextField(10);
	private JTextField txtTotalSessions = new JTextField();
	private JTextField txtGameMostPlayedHours = new JTextField();
	private JTextField txtGameMostPlayerInit = new JTextField();
	private JTextField txtGameTopAchievement = new JTextField();
	private JTextField txtGameOfYear = new JTextField();
	private JTextField txtGameOfMonth = new JTextField();
	private JTextField txtTopLibrary = new JTextField();
	private JTextField txtTopPlatform = new JTextField();
	private JTextField txtTopCategory = new JTextField();

	public panelStatistics() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.ipadx = 1;
		gbc.ipady = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;

		setBorder(BorderFactory.createTitledBorder("Detalles del jugador"));
		setLayout(new GridBagLayout());
		add(lblName, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtName, gbc);
		gbc.gridx = 0;
		gbc.gridy ++;
		gbc.gridwidth = 1;
		add(lblRegisterDate, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtRegisterDate, gbc);
		gbc.gridx = 0;
		gbc.gridy ++;
		gbc.gridwidth = 1;
		add(lblHoursLastDay, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtHoursLastDay, gbc);
		gbc.gridx = 0;
		gbc.gridy ++;
		gbc.gridwidth = 1;
		add(lblHoursLastWeek, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtHoursLastWeek, gbc);
		gbc.gridx = 0;
		gbc.gridy ++;
		gbc.gridwidth = 1;
		add(lblHoursLastTwoWeek, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtHoursLastTwoWeek, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy ++;
		add(lblHoursLastMonth, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtHoursLastMonth, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy ++;
		add(lblHoursLastYear, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtHoursLastYear, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy ++;
		add(lblHoursTotal, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtHoursTotal, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy ++;
		add(lblTotalInitGames, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtTotalInitGames, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy ++;
		add(lblTotalCompletedGames, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtTotalCompletedGames, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		
		// Segunda columna
		gbc.gridx = 3;
		gbc.gridy = 0;
		add(lblTotalPorcentCompletedGames, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtTotalPorcentCompletedGames, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblTotalSessions, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtTotalSessions, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblGameMostPlayedHours, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtGameMostPlayedHours, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblGameMostPlayerInit, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtGameMostPlayerInit, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblGameTopAchievement, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtGameTopAchievement, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblGameOfYear, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtGameOfYear, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblGameOfMonth, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtGameOfMonth, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblTopLibrary, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtTopLibrary, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblTopPlatform, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtTopPlatform, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 3;
		gbc.gridy ++;
		add(lblTopCategory, gbc);
		gbc.gridx ++;
		gbc.gridwidth = 2;
		add(txtTopCategory, gbc);

		txtName.setEditable(false);
		txtRegisterDate.setEditable(false);
		txtHoursLastDay.setEditable(false);
		txtHoursLastWeek.setEditable(false);
		txtHoursLastTwoWeek.setEditable(false);
		txtHoursLastMonth.setEditable(false);
		txtHoursLastYear.setEditable(false);
		txtHoursTotal.setEditable(false);
		txtTotalInitGames.setEditable(false);
		txtTotalCompletedGames.setEditable(false);
		txtTotalPorcentCompletedGames.setEditable(false);
		txtTotalSessions.setEditable(false);
		txtGameMostPlayedHours.setEditable(false);
		txtGameMostPlayerInit.setEditable(false);
		txtGameTopAchievement.setEditable(false);
		txtGameOfYear.setEditable(false);
		txtGameOfMonth.setEditable(false);
		txtTopLibrary.setEditable(false);
		txtTopPlatform.setEditable(false);
		txtTopCategory.setEditable(false);
	}
}

class panelChart extends JPanel implements ActionListener, ItemListener {
	private static final long serialVersionUID = 6454818822235578388L;
	private final JLabel lblDateInit = new JLabel("Inicio");
	private final JLabel lblDateEnd = new JLabel("Fin");
	private final JTextField txtDateInit = new JTextField();
	private final JTextField txtDateEnd = new JTextField();
	private JFreeChart barChart = null;
	private JComboBox<String> cbEsts = new JComboBox<>();    
	private final DateChooser dcDateInit = new DateChooser();
	private final DateChooser dcDateEnd = new DateChooser();
	private JCheckBox chkbFiltered = new JCheckBox("Filtrar", true);
	private final JPanel pnlOpt = new JPanel();
	private final JPanel pnlChart = new JPanel();
	private ChartPanel chartPanel;

	public panelChart() {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Estadísticas"));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.ipadx = 1;
		gbc.ipady = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		pnlOpt.add(cbEsts);
		pnlOpt.add(lblDateInit);
		pnlOpt.add(txtDateInit);
		pnlOpt.add(lblDateEnd);
		pnlOpt.add(txtDateEnd);
		pnlOpt.add(chkbFiltered);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(pnlOpt, gbc);
		gbc.gridy ++;
		add(pnlChart, gbc);
		gbc.gridy ++;
		
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
	}
	
	private void loadData(int opt, boolean reload) {
		barChart = null;
		if(reload) pnlChart.remove(chartPanel);
		chartPanel = null;

		if(opt == 0) {
			barChart = ChartFactory.createBarChart("", "Juegos", "Sesiones", createDataTotalSessions(), PlotOrientation.VERTICAL, false, true, false);
		} else if(opt == 1) {
			barChart = ChartFactory.createBarChart("", "Juegos", "Horas", createDataTotalHours(), PlotOrientation.VERTICAL, false, true, false);
		}

		chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new Dimension(560, 367));
		pnlChart.add(chartPanel);

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
