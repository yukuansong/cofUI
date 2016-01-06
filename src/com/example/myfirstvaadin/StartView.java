package com.example.myfirstvaadin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.vaadin.addons.d3Gauge.Gauge;
import org.vaadin.addons.d3Gauge.GaugeConfig;
import org.vaadin.addons.d3Gauge.GaugeStyle;

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
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class StartView extends TabSheet implements View {
	
	private Random random = new Random();
	private Integer coreNum = 0;

	private Gauge gaugeITOS1Ram;
	private Gauge gaugeITOS1Disk;
	private Gauge gaugeCassandra1Ram;
	private Gauge gaugeCassandra1Disk;
	
	public StartView() {
		setSizeFull();
		addStyleName(ValoTheme.TABSHEET_FRAMED);
		
//		addTab(getLogoutContent(), "Logout Page!");
	}

	public Component getHelpContent() {
		VerticalLayout content = new VerticalLayout();
		content.addComponent(new Label("this is the help page!"));
		return content;

	}
	public Component getLogoutContent() {
		VerticalLayout content = new VerticalLayout();
		content.addComponent(new Label("this is the Logout page!"));
		return content;

	}

	public Component getTestingContent() {
		VerticalLayout content = new VerticalLayout();
	
		ComboBox select = new ComboBox("Select a host for details");
		select.setWidthUndefined();
		SQLContainer container = createSQLContainer();
		Collection<?> itemIDs =container.getItemIds();
		List<HostProperty> hosts = new ArrayList<HostProperty>();

		for(Object itemID: itemIDs){
			HostProperty host = new HostProperty();
			host.setName((String)container.getContainerProperty(itemID, "name").getValue());
			host.setRam((String)container.getContainerProperty(itemID, "ram").getValue());
			host.setStorage((String)container.getContainerProperty(itemID, "storage").getValue());
			host.setProcessor((String)container.getContainerProperty(itemID, "processor").getValue());
			hosts.add(host);
			
			Property property = container.getContainerProperty(itemID, "name");
			if (property == null){
				System.out.println("Could not find the column========");
			} else{
			select.addItem(container.getContainerProperty(itemID, "name").getValue());
			}
		}
		select.setInputPrompt("No host selected");
		select.setWidth(25.0f, Unit.PERCENTAGE);
		select.addValueChangeListener(new ValueChangeListener(){
			public void valueChange(ValueChangeEvent event){
				Notification.show("selected "+ event.getProperty().getValue());
				UI.getCurrent().getNavigator().navigateTo("host/"+event.getProperty().getValue());
			}
		});
		content.addComponent(select);
		content.setComponentAlignment(select,Alignment.TOP_RIGHT);
		
		// Use the GridLayout for the page
		GridLayout gridLayout = new GridLayout(3, 9);
		gridLayout.setStyleName("host-gridlayout");
		gridLayout.setCaptionAsHtml(true);
		gridLayout.setHeight("100%");
		for(HostProperty host: hosts){
			RichTextArea os1Details = new RichTextArea("<h2><b>"+ host.getName()+" Details</b1></h2>");
			os1Details.setCaptionAsHtml(true);
			setHostSummary(os1Details, host);
			os1Details.setReadOnly(true);
			gridLayout.addComponent(os1Details);
			
		}
		content.addComponent(gridLayout);
		
		return content;
	}
	
	private void setHostSummary(RichTextArea textArea, HostProperty host){
		textArea.setValue("<h5><b>General</b></h5>\n" + "<small><ul>\n" + "<li>Name: " + host.getName() + "</li>\n" + "<li>RAM: "
				+ host.getRam()+"</li>\n" + "<li>Storage: " + host.getStorage()+"</li>\n" + "<li>CPU Processor: " + host.getProcessor()+"</li>\n" + "</ul></small>\n"
				
//				+ "<h5><b>System</b></h5>\n" + "<smalll><ul>\n" + "<li>RAM: " + host.getRam()+" </li>\n" + "<li>Storage: " + host.getStorage()+"\n"
//				+ "<li>Processor: " + host.getProcessor()+"\n" + "</ul></small>\n" + "<h5><b>Supervisor</b></h5>\n" + "<small><ul>\n"
//				+ "<li>Type: " + "Native </li>\n" + "<li>Name: " + "KVM</li>\n" + "</ul></small>\n"
//				+ "<h5><b>Network</b></h5>\n" + "<small><ul>\n" + "<li>Adapter 1: " + "Intel PRO/1000 </li>\n"
//				+ "<li>Adapter 2: " + "Intel PRO/10</li>\n" + "</ul></small>\n"

		);
	}

	@Override
	public void enter(ViewChangeEvent event) {

		setSizeFull();
		addStyleName(ValoTheme.TABSHEET_FRAMED);

		// Home page
		TabSheet.Tab instanceTab = addTab(getHomeContent(), "VM Instance Status");
		instanceTab.setIcon(FontAwesome.HOME);
//
//		// Data Page
//		addTab(getDataInTableContent(), "VM Instance Status Data");
//
		// Host (Physical Hardware) Status
		TabSheet.Tab tabHost = addTab(getHostContent(), "Hardware Monitor Displays");
		tabHost.setIcon(FontAwesome.HOME);

		// Help Page
		TabSheet.Tab tabHelp = addTab(getHelpContent(), "Help Page");
		tabHelp.setIcon(FontAwesome.HOME);
		
		// Testing page
		TabSheet.Tab tabTesting = addTab(getTestingContent(), "Testing Page");
	}
	private Component getHomeContent() {

		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);

		// panel for summary info
		Panel panelSummary = new Panel();
		CustomLayout layoutSummary = new CustomLayout("summaryLayout");
		panelSummary.setContent(layoutSummary);

		Label instantName = new Label("admin");
		layoutSummary.addComponent(instantName, "instantName");

		Label labelPoweroff = new Label();
		labelPoweroff.setCaption("PowerOff");
		labelPoweroff.setIcon(new ThemeResource("icons/status_busy.png"));
		labelPoweroff.setSizeUndefined();
		layoutSummary.addComponent(labelPoweroff, "status");
		content.addComponent(panelSummary);

		Label uptime = new Label("212");
		layoutSummary.addComponent(uptime, "upTime");
		content.addComponent(panelSummary);

		Label unit = new Label("Minutes");
		layoutSummary.addComponent(unit, "unit");
		content.addComponent(panelSummary);

		// second row -------
		Label instantName2 = new Label("cassandra-1");
		layoutSummary.addComponent(instantName2, "instantName2");

		Label labelRunning = new Label();
		labelRunning.setCaption("Running");
		labelRunning.setIcon(new ThemeResource("icons/status.png"));
		labelRunning.setSizeUndefined();
		layoutSummary.addComponent(labelRunning, "status2");
		content.addComponent(panelSummary);

		Label uptime2 = new Label("249");
		layoutSummary.addComponent(uptime2, "upTime2");
		content.addComponent(panelSummary);

		Label unit2 = new Label("hr");
		layoutSummary.addComponent(unit2, "unit2");
		content.addComponent(panelSummary);
		gaugeITOS1Ram = getStyleGauge(GaugeStyle.STYLE_DEFAULT, "Testing");

		/// ----------------------Test Area------------------------

		// ------
		HorizontalLayout row = new HorizontalLayout();
		row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		row.setSpacing(true);
		// row.setWidth("600px");
		content.addComponent(row);

		// Create the custom layout and set it as a component in
		// the current layout
		Panel panelITOS1 = new Panel("ITOS - 1");
		CustomLayout gaugeLayout = new CustomLayout("gaugeLayout");
		// gaugeLayout.setSizeUndefined();
		panelITOS1.setContent(gaugeLayout);
		// panelITOS1.setSizeUndefined();
		// panelITOS1.setWidth("350px");

		panelITOS1.setIcon(FontAwesome.DASHBOARD);
		gaugeITOS1Ram = getStyleGauge(GaugeStyle.STYLE_DEFAULT, "RAM");
		gaugeITOS1Disk = getStyleGauge(GaugeStyle.STYLE_DEFAULT, "DISK");
		gaugeLayout.addComponent(gaugeITOS1Ram, "gaugeITOS1Ram");
		gaugeLayout.addComponent(gaugeITOS1Disk, "gaugeITOS1Disk");
		row.addComponent(panelITOS1);

		Panel panelCassadra1 = new Panel("CASSANDRA - 1");
		CustomLayout gaugeLayout2 = new CustomLayout("gaugeLayout");
		// gaugeLayout2.setSizeUndefined();
		panelCassadra1.setContent(gaugeLayout2);
		// panelCassadra1.setSizeUndefined();
		// panelCassadra1.setWidth("350px");

		panelCassadra1.setIcon(FontAwesome.DASHBOARD);
		gaugeCassandra1Ram = getStyleGauge(GaugeStyle.STYLE_LIGHT, "RAM");
		gaugeCassandra1Disk = getStyleGauge(GaugeStyle.STYLE_LIGHT, "DISK");
		gaugeLayout2.addComponent(gaugeCassandra1Ram, "gaugeITOS1Ram");
		gaugeLayout2.addComponent(gaugeCassandra1Disk, "gaugeITOS1Disk");
		row.addComponent(panelCassadra1);
		return content;
	}
	
	private Gauge getStyleGauge(GaugeStyle style, String subType) {

		GaugeConfig config = new GaugeConfig();
		config.setStyle(style.toString());
		config.setTransitionDuration(1500);
		Gauge gauge = new Gauge(subType, random.nextInt(101), 200, config);
		// gauge.setWidth("110px");gauge.setHeight("110px");
		return gauge;

	}
	
	private Component getHostContent() {
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);

		// starting point of the page
		// Use the GridLayout for the page
		GridLayout gridLayout = new GridLayout(3, 4);
		gridLayout.setStyleName("host-gridlayout");
		gridLayout.setCaptionAsHtml(true);
//		gridLayout.setCaption("<H1 align='center'>                 Physical Host Details</H1>");
		gridLayout.setHeight("100%");

		// Fill out the first row with the host details in text area
		// Use the Rich TextArea without top and bottom tool bar
		RichTextArea os1Details = new RichTextArea("<h2><b>OS-1 Details</b1></h2>");
		os1Details.setCaptionAsHtml(true);
		HostProperty host001 = new HostProperty();
		host001.setName("OS-1");
		setTextAreaValues(os1Details, host001);
		os1Details.setReadOnly(true);
		gridLayout.addComponent(os1Details);

		RichTextArea os2Details = new RichTextArea("<h2><b>OS-2 Details</b1></h2>");
		os2Details.setCaptionAsHtml(true);
		HostProperty host002 = new HostProperty();
		host002.setName("OS-2");
		setTextAreaValues(os2Details, host002);
		os2Details.setReadOnly(true);
		gridLayout.addComponent(os2Details);

		RichTextArea os3Details = new RichTextArea("<h2><b>OS-3 Details</b1></h2>");
		os3Details.setCaptionAsHtml(true);
		HostProperty host003 = new HostProperty();
		host003.setName("OS-3");
		setTextAreaValues(os3Details, host003);
		os3Details.setReadOnly(true);
		gridLayout.addComponent(os3Details);

//		Label mylabel = new Label("This text has a lot of styles");
//		mylabel.addStyleName("mystyle");
//		//
//		gridLayout.addComponent(mylabel);

		// CPU Temperature for OS - 1
		List<Number> data1 =new ArrayList<Number>(Arrays.asList(40, 45,55, 58,53, 49, 45, 38, 40, 49, 55, 60, 53, 50, 45, 42, 49, 52, 56, 50, 45, 40, 45, 50));
		Chart tempChartOS1= getTempPlot("OS-1",data1, SolidColor.YELLOWGREEN);
		gridLayout.addComponent(tempChartOS1);

		List<Number> data2 =new ArrayList<Number>(Arrays.asList(40, 45,55, 58,53, 49, 45, 38, 40, 49, 55, 60, 53, 50, 45, 42, 49, 52, 56, 50, 45, 40, 45, 50));
		Chart tempChartOS2= getTempPlot("OS-2",data2, SolidColor.CORNFLOWERBLUE);
		gridLayout.addComponent(tempChartOS2);

		List<Number> data3 =new ArrayList<Number>(Arrays.asList(40, 45,55, 58,53, 49, 45, 38, 40, 49, 55, 60, 53, 50, 45, 42, 49, 52, 56, 50, 45, 40, 45, 50));
		Chart tempChartOS3= getTempPlot("OS-3",data3, SolidColor.LIGHTGREEN);
		gridLayout.addComponent(tempChartOS3);
		
		// CPU Usage
		Chart cpuChart1 = getCPUPlot("OS-1",data1, SolidColor.BLUE);
		gridLayout.addComponent(cpuChart1);
		Chart cpuChart2 = getCPUPlot("OS-2",data2, SolidColor.RED);
		gridLayout.addComponent(cpuChart2);
		Chart cpuChart3 = getCPUPlot("OS-3",data3, SolidColor.DARKORCHID);
		gridLayout.addComponent(cpuChart3);
		
		// RAM Usage
		DataSeries dataRam1 = new DataSeries();
		for(int hr = 0; hr < 24; hr++){
			dataRam1.add(new DataSeriesItem("last "+hr +"hour", hr * Math.random()));
		}
		Chart ramChart1 = getRAMPlot("OS-1", dataRam1);
		gridLayout.addComponent(ramChart1);

		DataSeries dataRam2 = new DataSeries();
		for(int hr = 0; hr < 24; hr++){
			dataRam2.add(new DataSeriesItem("last "+hr +"hour", hr * Math.random()));
		}
		Chart ramChart2 = getRAMPlot("OS-2", dataRam2);
		gridLayout.addComponent(ramChart2);
		
		DataSeries dataRam3 = new DataSeries();
		for(int hr = 0; hr < 24; hr++){
			dataRam3.add(new DataSeriesItem("last "+hr +"hour", hr * Math.random()));
		}
		Chart ramChart3 = getRAMPlot("OS-3", dataRam3);
		gridLayout.addComponent(ramChart3);
		
		
		content.addComponent(gridLayout);

		return content;
	}

	private void setTextAreaValues(RichTextArea textArea, HostProperty host) {
		textArea.setValue("<h5><b>General</b></h5>\n" + "<small><ul>\n" + "<li>Name: " + host.getName() + "</li>\n" + "<li>Description: "
				+ "Post description here </li>\n" + "<li>Host OS: " + "CentOS</li>\n" + "<li>System Update Time: " + "47 days 11 hrs 6 minutes</li>\n" + "</ul></small>\n"
				+ "<h5><b>System</b></h5>\n" + "<smalll><ul>\n" + "<li>RAM: " + "16GB </li>\n" + "<li>Storage: " + "500GB\n"
				+ "<li>Processor: " + "Intel i7</li>\n" + "</ul></small>\n" + "<h5><b>SuperVisor</b></h5>\n" + "<small><ul>\n"
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
		
		// CPU Temperature for OS - 1
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
}
