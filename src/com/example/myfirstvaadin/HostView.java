package com.example.myfirstvaadin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsArea;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;

public class HostView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		String viewParam = null;
		if(event.getParameters() == null 
				|| event.getParameters().isEmpty()){
			System.out.println("the ViewchangeEvent parameters are null or empty!!");
		}
		else {
			viewParam = event.getParameters();
			System.out.println("========="+viewParam);
		}
		setSizeFull();
		createHostDataContent(viewParam);	
//		setHeight(Sizeable.SIZE_UNDEFINED,0);
//		VerticalLayout layout = new VerticalLayout();
		setHeightUndefined();
//		setSizeUndefined();

	}

	private void createHostDataContent(String viewParam){
		
		// Retrieve the data from the database:
		SQLContainer dataContainer = createSQLContainer();
		dataContainer.addContainerFilter(new Compare.Equal("NAME",viewParam));
		Collection<?> itemIDs =dataContainer.getItemIds();
		HostProperty host = new HostProperty();
		for(Object itemID: itemIDs){
			host.setName((String)dataContainer.getContainerProperty(itemID, "name").getValue());
			host.setDescription((String)dataContainer.getContainerProperty(itemID, "description").getValue());
			host.setHostOS((String)dataContainer.getContainerProperty(itemID, "hostos").getValue());
			host.setRam((String)dataContainer.getContainerProperty(itemID, "ram").getValue());
			host.setStorage((String)dataContainer.getContainerProperty(itemID, "storage").getValue());
			host.setProcessor((String)dataContainer.getContainerProperty(itemID, "processor").getValue());
		}
		
		//--------------------------------
		// Use the GridLayout for the page
		GridLayout gridLayout = new GridLayout(2, 2);
		gridLayout.setStyleName("host-gridlayout");
		gridLayout.setCaptionAsHtml(true);
		gridLayout.setHeight("100%");


		// Fill out the first row with the host details in text area
		// Use the Rich TextArea without top and bottom tool bar
		RichTextArea os1Details = new RichTextArea("<h2><b>"+ host.getName()+" Details</b1></h2>");
		os1Details.setCaptionAsHtml(true);
		setTextAreaValues(os1Details, host);
		os1Details.setReadOnly(true);
		gridLayout.addComponent(os1Details);

		// CPU Temperature for OS - 1
		List<Number> data1 =new ArrayList<Number>(Arrays.asList(40, 45,55, 58,53, 49, 45, 38, 40, 49, 55, 60, 53, 50, 45, 42, 49, 52, 56, 50, 45, 40, 45, 50));
		Chart tempChartOS1= getTempPlot(host.getName(),data1, SolidColor.YELLOWGREEN);
		gridLayout.addComponent(tempChartOS1);
		
		// CPU Usage
		Chart cpuChart1 = getCPUPlot(host.getName(),data1, SolidColor.BLUE);
		gridLayout.addComponent(cpuChart1);

		// RAM Usage
		DataSeries dataRam1 = new DataSeries();
		for(int hr = 0; hr < 24; hr++){
			dataRam1.add(new DataSeriesItem("last "+hr +"hour", hr * Math.random()));
		}
		Chart ramChart1 = getRAMPlot(host.getName(), dataRam1);
		gridLayout.addComponent(ramChart1);	
		addComponent(gridLayout);
		
		//--------------------------------
	}
	
	public SQLContainer createSQLContainer() {
		SQLContainer container = null;
		try {
			JDBCConnectionPool pool = new SimpleJDBCConnectionPool("com.mysql.jdbc.Driver",
					"jdbc:mysql://192.168.10.234:3306/cof", "vaadin", "1234");
			TableQuery q1 = new TableQuery("hosts", pool);
			container = new SQLContainer(q1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return container;
	}
	
	private void setTextAreaValues(RichTextArea textArea, HostProperty host) {
		textArea.setValue("<h5><b>General</b></h5>\n" + "<small><ul>\n" + "<li>Name: " + host.getName() + "</li>\n" + "<li>Description: "
				+ host.getDescription()+"</li>\n" + "<li>Host OS: " + host.getHostOS()+"</li>\n" + "<li>System Update Time: " + "47 days 11 hrs 6 minutes</li>\n" + "</ul></small>\n"
				+ "<h5><b>System</b></h5>\n" + "<smalll><ul>\n" + "<li>RAM: " + host.getRam()+" </li>\n" + "<li>Storage: " + host.getStorage()+"\n"
				+ "<li>Processor: " + host.getProcessor()+"\n" + "</ul></small>\n" + "<h5><b>Supervisor</b></h5>\n" + "<small><ul>\n"
				+ "<li>Type: " + "Native </li>\n" + "<li>Name: " + "KVM</li>\n" + "</ul></small>\n"
				+ "<h5><b>Network</b></h5>\n" + "<small><ul>\n" + "<li>Adapter 1: " + "Intel PRO/1000 </li>\n"
				+ "<li>Adapter 2: " + "Intel PRO/10</li>\n" + "</ul></small>\n"

		);
	}
	
	private Chart getCPUPlot(String hostName, List<Number> data, Color color){
		Chart chart = new Chart(ChartType.BAR);
		Configuration conf = chart.getConfiguration();
		conf.setTitle("CPU Usage in last 24 hrs");
		conf.setSubTitle(hostName);
		conf.getLegend().setEnabled(false);
		PlotOptionsBar plotOption = new PlotOptionsBar();
		plotOption.setColor(color);
		conf.setPlotOptions(plotOption);
		// Temperature data
		ListSeries tempSeries = new ListSeries("temp");

		tempSeries.setData(data);
		conf.addSeries(tempSeries);
		XAxis tempX = new XAxis();
		tempX.setTitle("Last 24 hours");
		YAxis tempY = new YAxis();
		tempY.setTitle("CPU Usage Percentage");
		conf.addxAxis(tempX);
		conf.addyAxis(tempY);
		return chart;
	}

	private Chart getTempPlot(String hostName, List<Number> data, Color color){
		
		// CPU Temperature for the given host
		Chart tempChart = new Chart(ChartType.AREA);
		Configuration tempConf = tempChart.getConfiguration();
		tempConf.setTitle("CPU Temperature in last 24 hrs");
		tempConf.setSubTitle(hostName);
		tempConf.getLegend().setEnabled(false);
		PlotOptionsArea tempPlotOption = new PlotOptionsArea();
		tempPlotOption.setColor(color);
		tempPlotOption.setAllowPointSelect(false);
		tempConf.setPlotOptions(tempPlotOption);
		// Temperature data
		ListSeries tempSeries = new ListSeries("temp");

		tempSeries.setData(data);
		tempConf.addSeries(tempSeries);
		XAxis tempX = new XAxis();
		tempX.setTitle("Time in hour");
		YAxis tempY = new YAxis();
		tempY.setTitle("Temperature in F");
		tempConf.addxAxis(tempX);
		tempConf.addyAxis(tempY);
		
		return tempChart;
	}
	
	private Chart getRAMPlot(String hostName, DataSeries data){
		
		// CPU Temperature for OS - 1
		Chart tempChart = new Chart(ChartType.PIE);
		Configuration tempConf = tempChart.getConfiguration();
		tempConf.setTitle("RAM Usage (GB) in last 24 hrs");
		tempConf.setSubTitle(hostName);
		tempConf.getLegend().setEnabled(false);
		PlotOptionsPie plotOption = new PlotOptionsPie();
		plotOption.setInnerSize(0);
		plotOption.setSize("75%");
		plotOption.setCenter("50%", "50%");
		tempConf.setPlotOptions(plotOption);
		// Temperature data
		tempConf.addSeries(data);
		
		return tempChart;
	}
	
}
