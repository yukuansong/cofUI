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