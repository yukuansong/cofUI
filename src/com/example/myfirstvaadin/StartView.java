package com.example.myfirstvaadin;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class StartView extends VerticalLayout implements View {
	
	public StartView() {
		setSizeFull();
		
		Button btn = new Button("Go to Main View", 
				new Button.ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						getUI().getNavigator().navigateTo("main");
						
					}
				});
		addComponent(btn);
		setComponentAlignment(btn,Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Welcome to the Animal Farm");

	}

}
