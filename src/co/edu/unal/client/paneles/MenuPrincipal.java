package co.edu.unal.client.paneles;

import co.edu.unal.shared.Usuario;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.TabPanel;
import com.gwtext.client.widgets.layout.FitLayout;

public class MenuPrincipal extends Composite {

	private Panel librosPanel = new Panel("Libros"); 
	private Panel inventarioPanel = new Panel("Inventario");   
	private Panel reportesPanel = new Panel("Reportes");
	private Panel usuariosPanel = new Panel("Usuarios");
	private TabPanel tabPanel = new TabPanel();
	private InventarioWindow iw;
	private LibrosWindow lw;
	private ReportesWindow rw;
	private UsuariosWindow uw;
	public Usuario usuario;

	public MenuPrincipal(LibrosWindow lw, InventarioWindow iw,
			ReportesWindow rw, UsuariosWindow uw, Usuario usuario) {

		Panel panel = new Panel();  
		panel.setBorder(false);  
		panel.setPaddings(15);  
		panel.setLayout(new FitLayout()); 
		
		this.iw = iw;
		this.lw = lw;
		this.rw = rw;
		this.uw = uw;
		this.usuario = usuario;

		tabPanel.setTabPosition(Position.BOTTOM);  
		tabPanel.setResizeTabs(true);  
		tabPanel.setMinTabWidth(115);  
		tabPanel.setTabWidth(135);  
		tabPanel.setActiveTab(0);
		
		librosPanel.setLayout(new FitLayout());  
		librosPanel.setIconCls("tab-icon");  
		librosPanel.add(lw);  

		inventarioPanel.setLayout(new FitLayout());
		inventarioPanel.setIconCls("tab-icon");
		inventarioPanel.add(iw);

		reportesPanel.setLayout(new FitLayout()); 
		reportesPanel.setIconCls("tab-icon");
		reportesPanel.add(rw);
		
		usuariosPanel.setLayout(new FitLayout()); 
		usuariosPanel.setIconCls("tab-icon");
		usuariosPanel.add(uw);

		tabPanel.add(inventarioPanel);
		tabPanel.add(librosPanel);  
		tabPanel.add(reportesPanel);
		tabPanel.add(usuariosPanel);
		tabPanel.setPixelSize(1331, 589);
		
		//panel.add(tabPanel);
		desactivarTabs();
		initWidget(tabPanel);
	}

	public void desactivarTabs(){
		librosPanel.disable();
		inventarioPanel.disable();
		reportesPanel.disable();
		usuariosPanel.disable();
	}
	
	public void activarTabs(){
		librosPanel.enable();
		inventarioPanel.enable();
		reportesPanel.enable();
		usuariosPanel.enable();
	}


	protected void inventarioWindow() {
		clearWindows();
		if (usuario.admin.booleanValue())
			iw.setVisible(true);
		else
			setLoginWindow();
	}// end of inventariosWindow

	protected void reportesWindow() {
		clearWindows();
		if (usuario.admin.booleanValue())
			rw.setVisible(true);
		else
			setLoginWindow();
	}// end of reportesWindow

	protected void librosWindow() {
		clearWindows();
		if (usuario.activo.booleanValue()) {
			lw.setVisible(true);
			lw.masterReset();
		} else
			setLoginWindow();
	}// end of librosWindow

	protected void usuariosWindow() {
		clearWindows();
		if (usuario.admin.booleanValue())
			uw.setVisible(true);
		else
			setLoginWindow();
	}// end of usuariosWindow

	private void clearWindows() {
		lw.setVisible(false);
		rw.setVisible(false);
		iw.setVisible(false);
		uw.setVisible(false);
	}

	private void setLoginWindow() {
		//lir.setVisible(true);
		Window.alert("Permisos insuficientes.");
	}
}
