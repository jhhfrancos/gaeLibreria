package co.edu.unal.client.paneles;

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

public class Registro extends Composite {
	private static final String READONLY_SCOPE = "http://127.0.0.1:8888/Libreria.html";

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


	private Button registrar = new Button("Registrar");

	String AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
	String CLIENT_ID = "YOUR_CLIENT_ID"; // available from the APIs console

	AuthRequest req = new AuthRequest(AUTH_URL, CLIENT_ID)
	.withScopes(READONLY_SCOPE); // Can specify multiple scopes here

	public Registro() {


		initWidget(flexTablePrincipal);

		flexTablePrincipal.setWidget(0, 0, flexTable);

		flexTable.setWidget(0, 0, nombreLabel);
		flexTable.setWidget(0, 1, nombreTextBox);
		flexTable.setWidget(1, 0, passwordLabel);
		flexTable.setWidget(1, 1, passwordTextBox);
		flexTable.setWidget(4, 3, registrar);


		registrar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				registrar();
			}
		});

		
	}



	protected void registrar() {
		String name = nombreTextBox.getText();
		String pwd = passwordTextBox.getText();
		Usuario u1 = new Usuario();

		u1.setNombre(name);
		u1.setPassword(pwd);
		u1.activo = true;
		u1.admin = true;

		if (FieldVerifier.isValidName(name) && FieldVerifier.isValidPass(pwd)) {
			loginService.registrar(u1, new AsyncCallback<Boolean>() {
				public void onSuccess(Boolean result) {
					if (result.booleanValue())
						Window.alert("Usuario registrado exitosamente.");
					else
						Window.alert("El usuario ya existe.");
				}

				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});
		} else {
			Window.alert("Nombre o contraseña no validos.");
		}
	}

}
