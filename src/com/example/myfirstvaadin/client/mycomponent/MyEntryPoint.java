package com.example.myfirstvaadin.client.mycomponent;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class MyEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// Use the custom widget
		
		final MyComponentWidget myWidget = new MyComponentWidget();
		RootPanel.get().add(myWidget);

	}

}
