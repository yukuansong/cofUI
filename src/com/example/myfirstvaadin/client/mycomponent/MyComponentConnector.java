package com.example.myfirstvaadin.client.mycomponent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import com.example.myfirstvaadin.MyComponent;
import com.example.myfirstvaadin.client.mycomponent.MyComponentWidget;
import com.example.myfirstvaadin.client.mycomponent.MyComponentServerRpc;
import com.vaadin.client.communication.RpcProxy;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.example.myfirstvaadin.client.mycomponent.MyComponentClientRpc;
import com.example.myfirstvaadin.client.mycomponent.MyComponentState;
import com.vaadin.client.communication.StateChangeEvent;

@Connect(MyComponent.class)
public class MyComponentConnector extends AbstractComponentConnector {

	MyComponentServerRpc rpc = RpcProxy
			.create(MyComponentServerRpc.class, this);
	
	public MyComponentConnector() {
		registerRpc(MyComponentClientRpc.class, new MyComponentClientRpc() {
			public void alert(String message) {
				// TODO Do something useful
				Window.alert(message);
			}
		});

		// TODO ServerRpc usage example, do something useful instead
		getWidget().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				final MouseEventDetails mouseDetails = MouseEventDetailsBuilder
					.buildMouseEventDetails(event.getNativeEvent(),
								getWidget().getElement());
				rpc.clicked(mouseDetails);
			}
		});

	}

	@Override
	protected Widget createWidget() {
		return GWT.create(MyComponentWidget.class);
	}

	@Override
	public MyComponentWidget getWidget() {
		return (MyComponentWidget) super.getWidget();
	}

	@Override
	public MyComponentState getState() {
		return (MyComponentState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);

		// TODO do something useful
		final String text = getState().text;
		getWidget().setText(text);
	}

}

