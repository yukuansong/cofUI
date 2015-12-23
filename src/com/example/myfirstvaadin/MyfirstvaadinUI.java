package com.example.myfirstvaadin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Random;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.d3Gauge.Gauge;
import org.vaadin.addons.d3Gauge.GaugeConfig;
import org.vaadin.addons.d3Gauge.GaugeStyle;

import com.github.wolfie.refresher.Refresher;

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
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.vaadin.addon.charts.Sparkline;;

@SuppressWarnings("serial")
@Theme("myfirstvaadin")
@Push(PushMode.AUTOMATIC)
public class MyfirstvaadinUI extends UI {
	
	
	private Random random = new Random();
	private Integer coreNum=0;
	
	SQLContainer container = null;
	
	private Gauge gaugeITOS1Ram;
	private Gauge gaugeITOS1Disk;
	private Gauge gaugeCassandra1Ram;
	private Gauge gaugeCassandra1Disk;
	
	@Override
	protected void init(VaadinRequest request) {

        final TabSheet sheet = new TabSheet();
        sheet.setSizeFull();
        sheet.addStyleName(ValoTheme.TABSHEET_FRAMED);

        setContent(sheet);

        // Home page
        TabSheet.Tab tabHome = sheet.addTab(getHomeContent(), "Node Status");
        tabHome.setIcon(FontAwesome.HOME);


        // Data Page
        TabSheet.Tab tabDataTable = sheet.addTab(getDataInTableContent(), "Node Status Data");
        tabDataTable.setIcon(FontAwesome.HOME); 
        
        //Host (Physical Hardware) Status
        TabSheet.Tab tabHost = sheet.addTab(getHostContent(), "Host status");
        
		
    
        // Help Page
        TabSheet.Tab tabHelp = sheet.addTab(getHelpContent(), "Help Page");
        tabHelp.setIcon(FontAwesome.HOME);
  
//        Feet the UI with the latest database data
//        new FeederThread().start();
        
//        startUIUpdate();

	}

	class FeederThread extends Thread {
		
		@Override
		public void run(){
			try {
				while(true){
					Thread.sleep(1000);
					
					// Implement access method
					access(new Runnable(){
						@Override
						public void run(){
//							container.refresh();
							updateGauges();
						}
					});
				}
				
			}	catch (InterruptedException e)	{
				e.printStackTrace();
			}
		}

	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyfirstvaadinUI.class, widgetset = "com.example.myfirstvaadin.MyfirstvaadinWidgetset")
	public static class Servlet extends VaadinServlet {
	}
	

	public SQLContainer createSQLContainer(){
		SQLContainer localContainer =null;
		try {
		JDBCConnectionPool pool = new SimpleJDBCConnectionPool("oracle.jdbc.driver.OracleDriver",
				"jdbc:oracle:thin:@10.10.1.17:1521:MNP11G", "MNP","snapjabeye");
		
		//specify the SQL generator
		OracleGenerator og = new OracleGenerator();
		TableQuery qr = new TableQuery("TESTTABLE", pool, og);
		qr.setVersionColumn("NAME");
		
		localContainer = new SQLContainer(qr);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return localContainer;
	}
	

    private Gauge getStyleGauge(GaugeStyle style, String subType) {


        GaugeConfig config = new GaugeConfig();
        config.setStyle(style.toString());
        config.setTransitionDuration(1500);
        Gauge gauge = new Gauge(subType,random.nextInt(101),150,config);
//        gauge.setWidth("110px");gauge.setHeight("110px");
        return gauge;

    }
    
    // create the Host page
    private Component getHostContent(){
    	VerticalLayout content = new VerticalLayout();
    	content.setMargin(true);
    	content.setSpacing(true);
    	
    	
    	
    	// starting point of the page
    	// Use the GridLayout for the page
    	GridLayout gridLayout = new GridLayout(3,4);
    	gridLayout.setStyleName("host-gridlayout");
    	
    	// Fill out the first row with the host details in text area
    	TextArea os1Details = new TextArea("OS-1 Details");
    	os1Details.setValue("This will be filled out with databinding jkj  adjkjkf kdfj dfjk dfkajdfk jaa jfkd jfkj dfa dfa fkajdfkjakdfjkajfk j j dkfjkd kajkdj ajdfk dfkjkfa djfkd jfjfdkj a jfkjdkjkadjfk afkdjkj");
    	gridLayout.addComponent(os1Details);
    	
    	TextArea os2Details = new TextArea("OS-2 Details");
    	os2Details.setValue("This will be filled out with databinding");
    	gridLayout.addComponent(os2Details);
    	
    	TextArea os3Details = new TextArea("OS-3 Details");
    	os3Details.setValue("This will be filled out with databinding");
    	gridLayout.addComponent(os3Details);
    	
    	BasicBar chart  = new BasicBar();
    	gridLayout.addComponent(chart.getChart());
    	
    	content.addComponent(gridLayout);
    	
    	return content;
    }
    private Component getHomeContent() {

        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.setSpacing(true);
        
        //Testing only
//        Label labelPoweroff = new Label();
//        labelPoweroff.setCaption("PowerOff");
//        labelPoweroff.setIcon(new ThemeResource("icons/status_busy.png"));
//        content.addComponent(labelPoweroff);

//        Label header = new Label("Node Status");
//        header.addStyleName(ValoTheme.LABEL_H2);
//        header.addStyleName(ValoTheme.LABEL_COLORED);
//        header.addStyleName("center");
//        content.addComponent(header);
//        content.setComponentAlignment(header, Alignment.MIDDLE_CENTER);

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

        ///----------------------Test Area------------------------

        //------
        HorizontalLayout row = new HorizontalLayout();
        row.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        row.setSpacing(true);
//        row.setWidth("600px");
        content.addComponent(row);

        // Create the custom layout and set it as a component in
        // the current layout
        Panel panelITOS1 = new Panel("ITOS - 1");
        CustomLayout gaugeLayout = new CustomLayout("gaugeLayout");
//        gaugeLayout.setSizeUndefined();
        panelITOS1.setContent(gaugeLayout);
//        panelITOS1.setSizeUndefined();
//        panelITOS1.setWidth("350px");
        
        panelITOS1.setIcon(FontAwesome.DASHBOARD);
        gaugeITOS1Ram = getStyleGauge(GaugeStyle.STYLE_DEFAULT, "RAM");
        gaugeITOS1Disk = getStyleGauge(GaugeStyle.STYLE_DEFAULT, "DISK");
        gaugeLayout.addComponent(gaugeITOS1Ram, "gaugeITOS1Ram");
        gaugeLayout.addComponent(gaugeITOS1Disk, "gaugeITOS1Disk");
        row.addComponent(panelITOS1);

        Panel panelCassadra1 = new Panel("CASSANDRA - 1");
        CustomLayout gaugeLayout2 = new CustomLayout("gaugeLayout");
//        gaugeLayout2.setSizeUndefined();
        panelCassadra1.setContent(gaugeLayout2);
//        panelCassadra1.setSizeUndefined();
//        panelCassadra1.setWidth("350px");
        
        panelCassadra1.setIcon(FontAwesome.DASHBOARD);
        gaugeCassandra1Ram = getStyleGauge(GaugeStyle.STYLE_LIGHT, "RAM");
        gaugeCassandra1Disk = getStyleGauge(GaugeStyle.STYLE_LIGHT, "DISK");
        gaugeLayout2.addComponent(gaugeCassandra1Ram, "gaugeITOS1Ram");
        gaugeLayout2.addComponent(gaugeCassandra1Disk, "gaugeITOS1Disk");
        row.addComponent(panelCassadra1);
        return content;
    }
    
    private Component getDataInTableContent(){

        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.setSpacing(true);
        
		Grid grid = new Grid();

//		container = createSQLContainer();
//		grid.setContainerDataSource(container);
        
		content.addComponent(grid);
	
    	return content;
    }
    
    private Component getHelpContent(){
    	
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
    	//Return a list with all ID's 
    	Collection<?> itemIDs = container.getItemIds();
    	for(Object itemID: itemIDs){
    		property = container.getContainerProperty(itemID, "AGE");
    		System.out.println("The age is: "+ property.getValue());
 
    	}
//        darkGauge.setValue(random.nextInt(101));
//        lightGauge.setValue(random.nextInt(101));
    	BigDecimal gaugeValue = (BigDecimal) property.getValue();
        gaugeITOS1Ram.setValue(random.nextInt(101) + gaugeValue.intValue());
        gaugeITOS1Disk.setValue(random.nextInt(101) + gaugeValue.intValue());
        gaugeCassandra1Ram.setValue(random.nextInt(101) + gaugeValue.intValue());
        gaugeCassandra1Disk.setValue(random.nextInt(101) + gaugeValue.intValue());
//        valoGauge.setValue(random.nextInt(101));
//        avgGauge.setValue(random.nextInt(45) +5);
    }
    
	public class MyBean implements Serializable {
		
		public MyBean(){
			
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
	
	public BeanItemContainer<MyBean> createBeanContainer(){
		
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