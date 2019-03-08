package com.sam.webtasks.basictools;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sam.webtasks.client.SessionInfo;
import com.sam.webtasks.iotask2.IOtask2BlockContext;

public class Finish {
	public static void Run() {
		final Label goodbyeText = new Label("You have now finished the experiment. Thank you for taking part. You earned a total of " + IOtask2BlockContext.getMoneyString());
		
		RootPanel.get().add(goodbyeText);
	}
}
