package com.example.myfirstvaadin;

import java.sql.SQLException;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.util.sqlcontainer.query.generator.OracleGenerator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MainView extends TabSheet implements View {
	
	public MainView(){
		setSizeFull();
		addStyleName(ValoTheme.TABSHEET_FRAMED);
	}
	


	private Component getTestingContent(String hostName) {
		VerticalLayout content = new VerticalLayout();
		Label label = new Label("We are at the view " +hostName+" now!!!!!!");
		content.addComponent(label);
		
		ComboBox select = new ComboBox("Please select a host for detailed display:");
		select.addItems("OS-1","OS-2", "OS-3", "OS-4");
		select.setInputPrompt("No host currently selected");
		select.setWidth(25.0f, Unit.PERCENTAGE);
		select.addValueChangeListener(new ValueChangeListener(){
			public void valueChange(ValueChangeEvent event){
				Notification.show("selected "+ event.getProperty().getValue());
				UI.getCurrent().getNavigator().navigateTo("host");
			}
		});
		content.addComponent(select);
		return content;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() == null 
				|| event.getParameters().isEmpty()){
			System.out.println("the ViewchangeEvent parameters are null or empty!!");
		}
		else {
			System.out.println("========="+event.getParameters());
		}
		
		// Testing page
		addTab(getTestingContent(event.getParameters()), "Testing Page 2");	
		addTab(new StartView().getHelpContent(), "Help Page");
		addTab(new StartView().getLogoutContent(), "Logout Page");
//		addTab(new StartView().getTestingContent(), "Home");

	}

}
