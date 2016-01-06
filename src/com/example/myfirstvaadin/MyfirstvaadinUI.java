package com.example.myfirstvaadin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Random;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.d3Gauge.Gauge;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.util.sqlcontainer.query.generator.OracleGenerator;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;;

@SuppressWarnings("serial")
@Theme("myfirstvaadin")
@Push(PushMode.AUTOMATIC)
public class MyfirstvaadinUI extends UI {

	private Random random = new Random();
	private Integer coreNum = 0;

	SQLContainer container = null;

	private Gauge gaugeITOS1Ram;
	private Gauge gaugeITOS1Disk;
	private Gauge gaugeCassandra1Ram;
	private Gauge gaugeCassandra1Disk;

	private Component getTestingContent(){
		VerticalLayout content = new VerticalLayout();
//		System.out.println(UI.getCurrent().getClass().getSimpleName());
//		System.out.println(Page.getCurrent().getWindowName());
//		
//		getPage().setTitle("Navigation Example");
//		
//		// Create a navigator to control the views
//		Navigator nav = new Navigator(getUI(), this);
//		// Create and register the views
//		nav.addView("", new StartView());
//		nav.addView("main", new MainView());
//		getUI().setNavigator(nav);
		return content;
	}
	@Override
	protected void init(VaadinRequest request) {
		
		
		getPage().setTitle("Navigation Example");
		
		// Create a navigator to control the views
		Navigator nav = new Navigator(getUI(), this);
		// Create and register the views
		nav.addView("", new StartView());
		nav.addView("main", new MainView());
		nav.addView("host", new HostView());
		getUI().setNavigator(nav);
		
//		final TabSheet sheet = new TabSheet();
//		sheet.setSizeFull();
//		sheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
//
//		setContent(sheet);
//
//		// Home page
//		TabSheet.Tab tabHome = sheet.addTab(getHomeContent(), "VM Instance Status");
//		tabHome.setIcon(FontAwesome.HOME);
//
//		// Data Page
//		TabSheet.Tab tabDataTable = sheet.addTab(getDataInTableContent(), "VM Instance Status Data");
//		tabDataTable.setIcon(FontAwesome.HOME);
//
//		// Host (Physical Hardware) Status
//		TabSheet.Tab tabHost = sheet.addTab(getHostContent(), "Hardware Monitor Displays");
//
//		// Help Page
//		TabSheet.Tab tabHelp = sheet.addTab(getHelpContent(), "Help Page");
//		tabHelp.setIcon(FontAwesome.HOME);
//		
//		// Testing page
//		TabSheet.Tab tabTesting = sheet.addTab(getTestingContent(), "Testing Page");

		// Feet the UI with the latest database data
		// new FeederThread().start();

		// startUIUpdate();


	}

	class FeederThread extends Thread {

		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(1000);

					// Implement access method
					access(new Runnable() {
						@Override
						public void run() {
							// container.refresh();
							updateGauges();
						}
					});
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyfirstvaadinUI.class, widgetset = "com.example.myfirstvaadin.widgetset.MyfirstvaadinWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	public SQLContainer createSQLContainer() {
		SQLContainer localContainer = null;
		try {
			JDBCConnectionPool pool = new SimpleJDBCConnectionPool("oracle.jdbc.driver.OracleDriver",
					"jdbc:oracle:thin:@10.10.1.17:1521:MNP11G", "MNP", "snapjabeye");

			// specify the SQL generator
			OracleGenerator og = new OracleGenerator();
			TableQuery qr = new TableQuery("TESTTABLE", pool, og);
			qr.setVersionColumn("NAME");

			localContainer = new SQLContainer(qr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return localContainer;
	}

//	private Gauge getStyleGauge(GaugeStyle style, String subType) {
//
//		GaugeConfig config = new GaugeConfig();
//		config.setStyle(style.toString());
//		config.setTransitionDuration(1500);
//		Gauge gauge = new Gauge(subType, random.nextInt(101), 200, config);
//		// gauge.setWidth("110px");gauge.setHeight("110px");
//		return gauge;
//
//	}

//	private void setTextAreaValues(RichTextArea textArea, HostProperty host) {
//		textArea.setValue("<h5><b>General</b></h5>\n" + "<small><ul>\n" + "<li>Name: " + host.getName() + "</li>\n" + "<li>Description: "
//				+ "Post description here </li>\n" + "<li>Host OS: " + "CentOS</li>\n" + "<li>System Update Time: " + "47 days 11 hrs 6 minutes</li>\n" + "</ul></small>\n"
//				+ "<h5><b>System</b></h5>\n" + "<smalll><ul>\n" + "<li>RAM: " + "16GB </li>\n" + "<li>Storage: " + "500GB\n"
//				+ "<li>Processor: " + "Intel i7</li>\n" + "</ul></small>\n" + "<h5><b>SuperVisor</b></h5>\n" + "<small><ul>\n"
//				+ "<li>Type: " + "Native </li>\n" + "<li>Name: " + "KVM</li>\n" + "</ul></small>\n"
//				+ "<h5><b>Network</b></h5>\n" + "<small><ul>\n" + "<li>Adapter 1: " + "Intel PRO/1000 </li>\n"
//				+ "<li>Adapter 2: " + "Intel PRO/10</li>\n" + "</ul></small>\n"
//
//		);
//	}
//	
//	private Chart getCPUPlot(String hostName, List<Number> data, Color color){
//		Chart chart = new Chart(ChartType.BAR);
//		Configuration conf = chart.getConfiguration();
//		conf.setTitle("CPU Usage in last 24 hrs");
//		conf.setSubTitle(hostName);
//		conf.getLegend().setEnabled(false);
//		PlotOptionsBar plotOption = new PlotOptionsBar();
//		plotOption.setColor(color);
//		conf.setPlotOptions(plotOption);
//		// Temperature data
//		ListSeries tempSeries = new ListSeries("temp");
//
//		tempSeries.setData(data);
//		conf.addSeries(tempSeries);
//		XAxis tempX = new XAxis();
//		tempX.setTitle("Last 24 hours");
//		YAxis tempY = new YAxis();
//		tempY.setTitle("CPU Usage Percentage");
//		conf.addxAxis(tempX);
//		conf.addyAxis(tempY);
//		return chart;
//	}
//
//	private Chart getTempPlot(String hostName, List<Number> data, Color color){
//		
//		// CPU Temperature for OS - 1
//		Chart tempChart = new Chart(ChartType.AREA);
//		Configuration tempConf = tempChart.getConfiguration();
//		tempConf.setTitle("CPU Temperature in last 24 hrs");
//		tempConf.setSubTitle(hostName);
//		tempConf.getLegend().setEnabled(false);
//		PlotOptionsArea tempPlotOption = new PlotOptionsArea();
//		tempPlotOption.setColor(color);
//		tempPlotOption.setAllowPointSelect(false);
//		tempConf.setPlotOptions(tempPlotOption);
//		// Temperature data
//		ListSeries tempSeries = new ListSeries("temp");
//
//		tempSeries.setData(data);
//		tempConf.addSeries(tempSeries);
//		XAxis tempX = new XAxis();
//		tempX.setTitle("Time in hour");
//		YAxis tempY = new YAxis();
//		tempY.setTitle("Temperature in F");
//		tempConf.addxAxis(tempX);
//		tempConf.addyAxis(tempY);
//		
//		return tempChart;
//	}
//	
//	private Chart getRAMPlot(String hostName, DataSeries data){
//		
//		// CPU Temperature for OS - 1
//		Chart tempChart = new Chart(ChartType.PIE);
//		Configuration tempConf = tempChart.getConfiguration();
//		tempConf.setTitle("RAM Usage (GB) in last 24 hrs");
//		tempConf.setSubTitle(hostName);
//		tempConf.getLegend().setEnabled(false);
//		PlotOptionsPie plotOption = new PlotOptionsPie();
//		plotOption.setInnerSize(0);
//		plotOption.setSize("75%");
//		plotOption.setCenter("50%", "50%");
//		tempConf.setPlotOptions(plotOption);
//		// Temperature data
//		tempConf.addSeries(data);
//		
//		return tempChart;
//	}
	// create the Host page
//	private Component getHostContent() {
//		VerticalLayout content = new VerticalLayout();
//		content.setMargin(true);
//		content.setSpacing(true);
//
//		// starting point of the page
//		// Use the GridLayout for the page
//		GridLayout gridLayout = new GridLayout(3, 4);
//		gridLayout.setStyleName("host-gridlayout");
//		gridLayout.setCaptionAsHtml(true);
////		gridLayout.setCaption("<H1 align='center'>                 Physical Host Details</H1>");
//		gridLayout.setHeight("100%");
//
//		// Fill out the first row with the host details in text area
//		// Use the Rich TextArea without top and bottom tool bar
//		RichTextArea os1Details = new RichTextArea("<h2><b>OS-1 Details</b1></h2>");
//		os1Details.setCaptionAsHtml(true);
//		HostProperty host001 = new HostProperty();
//		host001.setName("OS-1");
//		setTextAreaValues(os1Details, host001);
//		os1Details.setReadOnly(true);
//		gridLayout.addComponent(os1Details);
//
//		RichTextArea os2Details = new RichTextArea("<h2><b>OS-2 Details</b1></h2>");
//		os2Details.setCaptionAsHtml(true);
//		HostProperty host002 = new HostProperty();
//		host002.setName("OS-2");
//		setTextAreaValues(os2Details, host002);
//		os2Details.setReadOnly(true);
//		gridLayout.addComponent(os2Details);
//
//		RichTextArea os3Details = new RichTextArea("<h2><b>OS-3 Details</b1></h2>");
//		os3Details.setCaptionAsHtml(true);
//		HostProperty host003 = new HostProperty();
//		host003.setName("OS-3");
//		setTextAreaValues(os3Details, host003);
//		os3Details.setReadOnly(true);
//		gridLayout.addComponent(os3Details);
//
////		Label mylabel = new Label("This text has a lot of styles");
////		mylabel.addStyleName("mystyle");
////		//
////		gridLayout.addComponent(mylabel);
//
//		// CPU Temperature for OS - 1
//		List<Number> data1 =new ArrayList<Number>(Arrays.asList(40, 45,55, 58,53, 49, 45, 38, 40, 49, 55, 60, 53, 50, 45, 42, 49, 52, 56, 50, 45, 40, 45, 50));
//		Chart tempChartOS1= getTempPlot("OS-1",data1, SolidColor.YELLOWGREEN);
//		gridLayout.addComponent(tempChartOS1);
//
//		List<Number> data2 =new ArrayList<Number>(Arrays.asList(40, 45,55, 58,53, 49, 45, 38, 40, 49, 55, 60, 53, 50, 45, 42, 49, 52, 56, 50, 45, 40, 45, 50));
//		Chart tempChartOS2= getTempPlot("OS-2",data2, SolidColor.CORNFLOWERBLUE);
//		gridLayout.addComponent(tempChartOS2);
//
//		List<Number> data3 =new ArrayList<Number>(Arrays.asList(40, 45,55, 58,53, 49, 45, 38, 40, 49, 55, 60, 53, 50, 45, 42, 49, 52, 56, 50, 45, 40, 45, 50));
//		Chart tempChartOS3= getTempPlot("OS-3",data3, SolidColor.LIGHTGREEN);
//		gridLayout.addComponent(tempChartOS3);
//		
//		// CPU Usage
//		Chart cpuChart1 = getCPUPlot("OS-1",data1, SolidColor.BLUE);
//		gridLayout.addComponent(cpuChart1);
//		Chart cpuChart2 = getCPUPlot("OS-2",data2, SolidColor.RED);
//		gridLayout.addComponent(cpuChart2);
//		Chart cpuChart3 = getCPUPlot("OS-3",data3, SolidColor.DARKORCHID);
//		gridLayout.addComponent(cpuChart3);
//		
//		// RAM Usage
//		DataSeries dataRam1 = new DataSeries();
//		for(int hr = 0; hr < 24; hr++){
//			dataRam1.add(new DataSeriesItem("last "+hr +"hour", hr * Math.random()));
//		}
//		Chart ramChart1 = getRAMPlot("OS-1", dataRam1);
//		gridLayout.addComponent(ramChart1);
//
//		DataSeries dataRam2 = new DataSeries();
//		for(int hr = 0; hr < 24; hr++){
//			dataRam2.add(new DataSeriesItem("last "+hr +"hour", hr * Math.random()));
//		}
//		Chart ramChart2 = getRAMPlot("OS-2", dataRam2);
//		gridLayout.addComponent(ramChart2);
//		
//		DataSeries dataRam3 = new DataSeries();
//		for(int hr = 0; hr < 24; hr++){
//			dataRam3.add(new DataSeriesItem("last "+hr +"hour", hr * Math.random()));
//		}
//		Chart ramChart3 = getRAMPlot("OS-3", dataRam3);
//		gridLayout.addComponent(ramChart3);
//		
//		
//		content.addComponent(gridLayout);
//
//		return content;
//	}

//	private Component getHomeContent() {
//
//		VerticalLayout content = new VerticalLayout();
//		content.setMargin(true);
//		content.setSpacing(true);
//
//		// Testing only
//		// Label labelPoweroff = new Label();
//		// labelPoweroff.setCaption("PowerOff");
//		// labelPoweroff.setIcon(new ThemeResource("icons/status_busy.png"));
//		// content.addComponent(labelPoweroff);
//
//		// Label header = new Label("Node Status");
//		// header.addStyleName(ValoTheme.LABEL_H2);
//		// header.addStyleName(ValoTheme.LABEL_COLORED);
//		// header.addStyleName("center");
//		// content.addComponent(header);
//		// content.setComponentAlignment(header, Alignment.MIDDLE_CENTER);
//
//		// panel for summary info
//		Panel panelSummary = new Panel();
//		CustomLayout layoutSummary = new CustomLayout("summaryLayout");
//		panelSummary.setContent(layoutSummary);
//
//		Label instantName = new Label("admin");
//		layoutSummary.addComponent(instantName, "instantName");
//
//		Label labelPoweroff = new Label();
//		labelPoweroff.setCaption("PowerOff");
//		labelPoweroff.setIcon(new ThemeResource("icons/status_busy.png"));
//		labelPoweroff.setSizeUndefined();
//		layoutSummary.addComponent(labelPoweroff, "status");
//		content.addComponent(panelSummary);
//
//		Label uptime = new Label("212");
//		layoutSummary.addComponent(uptime, "upTime");
//		content.addComponent(panelSummary);
//
//		Label unit = new Label("Minutes");
//		layoutSummary.addComponent(unit, "unit");
//		content.addComponent(panelSummary);
//
//		// second row -------
//		Label instantName2 = new Label("cassandra-1");
//		layoutSummary.addComponent(instantName2, "instantName2");
//
//		Label labelRunning = new Label();
//		labelRunning.setCaption("Running");
//		labelRunning.setIcon(new ThemeResource("icons/status.png"));
//		labelRunning.setSizeUndefined();
//		layoutSummary.addComponent(labelRunning, "status2");
//		content.addComponent(panelSummary);
//
//		Label uptime2 = new Label("249");
//		layoutSummary.addComponent(uptime2, "upTime2");
//		content.addComponent(panelSummary);
//
//		Label unit2 = new Label("hr");
//		layoutSummary.addComponent(unit2, "unit2");
//		content.addComponent(panelSummary);
//		gaugeITOS1Ram = getStyleGauge(GaugeStyle.STYLE_DEFAULT, "Testing");
//
//		/// ----------------------Test Area------------------------
//
//		// ------
//		HorizontalLayout row = new HorizontalLayout();
//		row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
//		row.setSpacing(true);
//		// row.setWidth("600px");
//		content.addComponent(row);
//
//		// Create the custom layout and set it as a component in
//		// the current layout
//		Panel panelITOS1 = new Panel("ITOS - 1");
//		CustomLayout gaugeLayout = new CustomLayout("gaugeLayout");
//		// gaugeLayout.setSizeUndefined();
//		panelITOS1.setContent(gaugeLayout);
//		// panelITOS1.setSizeUndefined();
//		// panelITOS1.setWidth("350px");
//
//		panelITOS1.setIcon(FontAwesome.DASHBOARD);
//		gaugeITOS1Ram = getStyleGauge(GaugeStyle.STYLE_DEFAULT, "RAM");
//		gaugeITOS1Disk = getStyleGauge(GaugeStyle.STYLE_DEFAULT, "DISK");
//		gaugeLayout.addComponent(gaugeITOS1Ram, "gaugeITOS1Ram");
//		gaugeLayout.addComponent(gaugeITOS1Disk, "gaugeITOS1Disk");
//		row.addComponent(panelITOS1);
//
//		Panel panelCassadra1 = new Panel("CASSANDRA - 1");
//		CustomLayout gaugeLayout2 = new CustomLayout("gaugeLayout");
//		// gaugeLayout2.setSizeUndefined();
//		panelCassadra1.setContent(gaugeLayout2);
//		// panelCassadra1.setSizeUndefined();
//		// panelCassadra1.setWidth("350px");
//
//		panelCassadra1.setIcon(FontAwesome.DASHBOARD);
//		gaugeCassandra1Ram = getStyleGauge(GaugeStyle.STYLE_LIGHT, "RAM");
//		gaugeCassandra1Disk = getStyleGauge(GaugeStyle.STYLE_LIGHT, "DISK");
//		gaugeLayout2.addComponent(gaugeCassandra1Ram, "gaugeITOS1Ram");
//		gaugeLayout2.addComponent(gaugeCassandra1Disk, "gaugeITOS1Disk");
//		row.addComponent(panelCassadra1);
//		return content;
//	}

	private Component getDataInTableContent() {

		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);

		Grid grid = new Grid();

		// container = createSQLContainer();
		// grid.setContainerDataSource(container);

		content.addComponent(grid);

		return content;
	}

	private Component getHelpContent() {

		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);

		Label label = new Label();

		label.setCaption("This is the help page!!!");
		content.addComponent(label);

		return content;
	}

	private void updateGauges() {

		Property property = null;
		// Return a list with all ID's
		Collection<?> itemIDs = container.getItemIds();
		for (Object itemID : itemIDs) {
			property = container.getContainerProperty(itemID, "AGE");
			System.out.println("The age is: " + property.getValue());

		}
		// darkGauge.setValue(random.nextInt(101));
		// lightGauge.setValue(random.nextInt(101));
		BigDecimal gaugeValue = (BigDecimal) property.getValue();
		gaugeITOS1Ram.setValue(random.nextInt(101) + gaugeValue.intValue());
		gaugeITOS1Disk.setValue(random.nextInt(101) + gaugeValue.intValue());
		gaugeCassandra1Ram.setValue(random.nextInt(101) + gaugeValue.intValue());
		gaugeCassandra1Disk.setValue(random.nextInt(101) + gaugeValue.intValue());
		// valoGauge.setValue(random.nextInt(101));
		// avgGauge.setValue(random.nextInt(45) +5);
	}

	public class MyBean implements Serializable {

		public MyBean() {

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		private String name;
		private String address;
		private int age;
	}

	public BeanItemContainer<MyBean> createBeanContainer() {

		BeanItemContainer<MyBean> container = new BeanItemContainer<MyBean>(MyBean.class);

		MyBean myBean = new MyBean();
		myBean.setName("yukuan");
		myBean.setAddress("14409 autumn crest road");
		myBean.setAge(30);
		container.addBean(myBean);

		MyBean myBean2 = new MyBean();
		myBean2.setName("Mike");
		myBean2.setAddress("14409 autumn crest road");
		myBean2.setAge(40);
		container.addBean(myBean2);
		return container;
	}
}