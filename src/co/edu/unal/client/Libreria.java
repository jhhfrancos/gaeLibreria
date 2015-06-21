package co.edu.unal.client;

import co.edu.unal.client.paneles.InventarioWindow;
import co.edu.unal.client.paneles.LibrosWindow;
import co.edu.unal.client.paneles.LoginRegistroWindows;
import co.edu.unal.client.paneles.MenuPrincipal;
import co.edu.unal.client.paneles.ReportesWindow;
import co.edu.unal.client.paneles.UsuariosWindow;
import co.edu.unal.shared.Usuario;

import com.google.gwt.core.client.EntryPoint;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Viewport;
import com.gwtext.client.widgets.layout.FitLayout;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Libreria implements EntryPoint {
	private Usuario usuario = new Usuario();
	private LibrosWindow         lw = new LibrosWindow(usuario);
	private InventarioWindow     iw = new InventarioWindow();
	private ReportesWindow		 rw = new ReportesWindow();
	private UsuariosWindow		 uw = new UsuariosWindow();
	private LoginRegistroWindows logr;
	
	

	public void onModuleLoad() {  
		Panel panel = new Panel();
		panel.setBorder(false);  
		panel.setPaddings(15);  
		panel.setLayout(new FitLayout());  

		MenuPrincipal menuPrincipal =new MenuPrincipal(lw, iw, rw, uw, usuario);
		
		Panel mainPanel = new Panel();  
		mainPanel.setBorder(true); 
		mainPanel.add(menuPrincipal);
		mainPanel.setPixelSize(1331, 589);
		
		logr = new LoginRegistroWindows(menuPrincipal);
		logr.setPixelSize(250, 25);
		
		panel.add(logr);
		panel.add(mainPanel); 
		
		Viewport viewport = new Viewport(panel); 
	}  

	

}
