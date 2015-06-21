package co.edu.unal.client.paneles;

import java.io.IOException;

import co.edu.unal.client.services.LoginService;
import co.edu.unal.client.services.LoginServiceAsync;
import co.edu.unal.shared.FieldVerifier;
import co.edu.unal.shared.Usuario;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite; 
import com.gwtext.client.core.EventObject;  
import com.gwtext.client.core.RegionPosition;  
import com.gwtext.client.widgets.Button;  
import com.gwtext.client.widgets.Panel;  
import com.gwtext.client.widgets.TabPanel;  
import com.gwtext.client.widgets.Window;  
import com.gwtext.client.widgets.event.ButtonListenerAdapter;  
import com.gwtext.client.widgets.layout.BorderLayout;  
import com.gwtext.client.widgets.layout.BorderLayoutData;

public class LoginRegistroWindows extends Composite implements ClickHandler {
	private MenuPrincipal menuPrincipal;
	private Panel panel = new Panel();  
	private Registro registroPanel = new Registro();
	private Login loginPanel;
	private LoginGmail loginGmailPanel;
	
	
	public LoginRegistroWindows(MenuPrincipal menuPrincipal){
		initWidget(panel);
		panel.setBorder(true);
		this.menuPrincipal = menuPrincipal;
		loginPanel = new Login(this.menuPrincipal);
		loginGmailPanel = new LoginGmail();//loginGmailPanel = new LoginGmail(this.menuPrincipal);

		//center panel  
		TabPanel tabPanel = new TabPanel();  
		tabPanel.setActiveTab(0);  

		Panel tab1 = new Panel();
		tab1.setTitle("Login");
		tab1.add(loginPanel);  
		tab1.setAutoScroll(true);  
		
		Panel tab3 = new Panel();
		tab3.setTitle("LoginGmail");
		tab3.add(loginGmailPanel);  
		tab3.setAutoScroll(true); 

		Panel tab2 = new Panel();  
		tab2.setTitle("Registro");  
		tab2.add(registroPanel); 
		tab2.setAutoScroll(true);  

		tabPanel.add(tab1);  
		tabPanel.add(tab2);
		tabPanel.add(tab3);

		//west panel  
		Panel navPanel = new Panel();  
		navPanel.setTitle("Instrucciones");  
		navPanel.setWidth(200);  
		navPanel.setCollapsible(true);  
		navPanel.setHtml(getBogusMarkup());

		BorderLayoutData centerData = new BorderLayoutData(RegionPosition.CENTER);  
		centerData.setMargins(3, 0, 3, 3);  

		BorderLayoutData westData = new BorderLayoutData(RegionPosition.WEST);  
		westData.setSplit(true);  
		westData.setMargins(3, 3, 0, 3);  
		westData.setCMargins(3, 3, 3, 3);  

		final Window window = new Window();  
		window.setTitle("LOGIN / REGISTRO");  
		window.setClosable(true);  
		window.setWidth(600);  
		window.setHeight(350);  
		window.setPlain(true);  
		window.setLayout(new BorderLayout());  
		window.add(tabPanel, centerData);  
		window.add(navPanel, westData);  
		window.setCloseAction(Window.HIDE);  

		Button button = new Button("ENTRAR/REGISTRAR");  
		button.addListener(new ButtonListenerAdapter() {  
			public void onClick(Button button, EventObject e) {  
				window.show(button.getId());  
			}  
		});  
		panel.add(button);  

	}
	private static String getBogusMarkup() {  
		return "<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +  
				"Sed metus nibh, sodales a, porta at, vulputate eget, dui.  " +  
				"In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, " +  
				"cursus a, fringilla vel, urna.";  
	}
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}  
}
