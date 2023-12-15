package frontend;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import database.ModelGames;

public class StatisticsTotalHours extends JInternalFrame {
    private JFreeChart barChart = ChartFactory.createBarChart("", "Juegos", "Horas", createData(), PlotOrientation.VERTICAL, true, true, false);

    public StatisticsTotalHours() {
	ModelGames mg = new ModelGames();
	if(mg.getTotalGames() == 0) {
	    JOptionPane.showMessageDialog(this, "No tienes juegos en tu biblioteca", "No hay juegos", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	setTitle("Estadisticas de tiempo de juego");
	setSize(850, 550);
	setClosable(true);
	setResizable(true);
	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	setLayout(new FlowLayout());

	ChartPanel chartPanel = new ChartPanel(barChart);
	chartPanel.setPreferredSize(new Dimension(560, 367) );        
	setContentPane(chartPanel); 
	
	setVisible(true);
    }

    private CategoryDataset createData() {
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	ModelGames mg = new ModelGames();
	ArrayList<String> gameNameList = new ArrayList<>();
	gameNameList = mg.getStatisticsGamesNameList();
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
}
