package com.example.myfirstvaadin.client.mycomponent;

import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.communication.ServerRpc;

public interface MyComponentServerRpc extends ServerRpc {

	// TODO example API
	public void clicked(MouseEventDetails mouseDetails);

}
