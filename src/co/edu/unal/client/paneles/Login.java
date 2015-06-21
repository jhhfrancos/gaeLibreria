package co.edu.unal.client.paneles;

import co.edu.unal.client.Libreria;
import co.edu.unal.client.services.LoginService;
import co.edu.unal.client.services.LoginServiceAsync;
import co.edu.unal.shared.FieldVerifier;
import co.edu.unal.shared.Usuario;

import com.google.api.gwt.oauth2.client.AuthRequest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;

public class Login extends Composite implements ClickHandler {

	private final LoginServiceAsync loginService = GWT
			.create(LoginService.class);

	private final FlexTable flexTable = new FlexTable();
	private final FlexTable flexTablePrincipal = new FlexTable();

	private final Label nombreLabel = new Label("Nombre");
	private final Label passwordLabel = new Label("Password");
	private final Label usuarioLabel = new Label();
	private final Label tipoUsuarioLabel = new Label("Rol: Cliente");
	private final TextBox nombreTextBox = new TextBox();
	private final PasswordTextBox passwordTextBox = new PasswordTextBox();

	private Usuario usuario;
	private MenuPrincipal menuPrincipal;
	private Button iniciar = new Button("Iniciar");
	private Button logOutButton = new Button("Finalizar sesion");
	

	
	public Login(MenuPrincipal menuPrincipal) {

		this.usuario = menuPrincipal.usuario;
		this.menuPrincipal = menuPrincipal;
		initWidget(flexTablePrincipal);

		flexTablePrincipal.setWidget(0, 0, flexTable);

		flexTable.setWidget(0, 0, nombreLabel);
		flexTable.setWidget(0, 1, nombreTextBox);
		flexTable.setWidget(1, 0, passwordLabel);
		flexTable.setWidget(1, 1, passwordTextBox);
		flexTable.setWidget(4, 2, iniciar);

		iniciar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				iniciar();
			}
		});


		logOutButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				finalizar();
			}
		});
	}

	public void finalizar() {
		flexTablePrincipal.clear();
		flexTablePrincipal.setWidget(0, 0, flexTable);
		nombreTextBox.setValue("");
		passwordTextBox.setValue("");
		this.usuario.activo = false;
		this.usuario.admin = false;
		menuPrincipal.desactivarTabs();
	}

	public void onClick(ClickEvent event) {
		if (event.getSource() == null) {
		}
	}

	
	protected void iniciar() {
		String name = nombreTextBox.getText();
		String pwd = passwordTextBox.getText();
		
		usuario.setNombre(name);
		usuario.setPassword(pwd);

		loginService.login(usuario, new AsyncCallback<Usuario>() {
			public void onSuccess(Usuario result) {
				if (result.activo) {
					flexTablePrincipal.setWidget(0, 0, usuarioLabel);
					flexTablePrincipal.setWidget(0, 2, tipoUsuarioLabel);
					flexTablePrincipal.setWidget(0, 4, logOutButton);
					usuarioLabel.setText("Usuario activo: "
							+ result.getNombre() + ", ");
					if (result.admin)
						tipoUsuarioLabel.setText("Rol: Administrador");
					else
						tipoUsuarioLabel.setText("Rol: Cliente");

					usuario.activo = result.activo;
					usuario.admin = result.admin;
					menuPrincipal.activarTabs();
					Window.alert("Bienvenido.");
					
				} else
					Window.alert("Usuario invalido.");
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
}
