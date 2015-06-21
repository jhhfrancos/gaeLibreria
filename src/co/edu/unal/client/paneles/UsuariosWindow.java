package co.edu.unal.client.paneles;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.client.services.LoginService;
import co.edu.unal.client.services.LoginServiceAsync;
import co.edu.unal.shared.Usuario;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class UsuariosWindow extends Composite {

	private final FlexTable flexTable = new FlexTable();
	private final FlexTable flexTablePrincipal = new FlexTable();
	private final FlexTable flexTableButtons = new FlexTable();

	private final Label nombreLabel = new Label("Nombre");
	private final Label activoLabel = new Label("Activo");
	private final Label adminLabel = new Label("Administrador");
	private final Label idLabel = new Label("ID");
	private final Label eliminarLabel = new Label("Eliminar");

	private final Button aceptarButton = new Button("Aceptar");
	private final Button recargarButton = new Button("Refrescar");

	private final List<Boolean> adminLista = new ArrayList<Boolean>();
	private final List<Boolean> activoLista = new ArrayList<Boolean>();
	private final List<Boolean> eliminadosLista = new ArrayList<Boolean>();
	private final List<Long> idLista = new ArrayList<Long>();

	private final ScrollPanel scrollPanel = new ScrollPanel();

	private final LoginServiceAsync loginService = GWT
			.create(LoginService.class);

	public UsuariosWindow() {
		initWidget(flexTablePrincipal);

		llenarTabla();

		scrollPanel.setSize("600px", "480px");

		flexTable.setWidget(0, 0, nombreLabel);
		flexTable.setWidget(0, 2, activoLabel);
		flexTable.setWidget(0, 3, adminLabel);
		flexTable.setWidget(0, 1, idLabel);
		flexTable.setWidget(0, 4, eliminarLabel);

		nombreLabel.setSize("120px", "25px");
		activoLabel.setSize("120px", "25px");
		adminLabel.setSize("120px", "25px");
		idLabel.setSize("120px", "25px");
		eliminarLabel.setSize("120px", "25px");

		scrollPanel.add(flexTable);

		flexTableButtons.setWidget(0, 2, this.recargarButton);
		flexTableButtons.setWidget(0, 3, this.aceptarButton);

		flexTablePrincipal.setWidget(0, 0, scrollPanel);
		flexTablePrincipal.setWidget(1, 0, flexTableButtons);

		aceptarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				for (int fila = 1; fila < flexTable.getRowCount(); fila++) {
					activoLista.add(((CheckBox) flexTable.getWidget(fila, 2))
							.getValue());
					adminLista.add(((CheckBox) flexTable.getWidget(fila, 3))
							.getValue());
					eliminadosLista.add(((CheckBox) flexTable
							.getWidget(fila, 4)).getValue());
					idLista.add(Long.parseLong(((Label) flexTable.getWidget(
							fila, 1)).getText()));
				}
				actualizarTabla();
			}
		});

		recargarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				for(int index = 1; index < flexTable.getRowCount(); index++)
				flexTable.removeRow(index);
				llenarTabla();
			}
		});
	}

	private void actualizarTabla() {
		for (int index = 0; index < adminLista.size(); index++) {
			loginService.actualizar(idLista.get(index).longValue(), adminLista
					.get(index).booleanValue(), activoLista.get(index)
					.booleanValue(), new AsyncCallback<Boolean>() {
				public void onSuccess(Boolean result) {
					if(result.booleanValue());
						//Window.alert("Cambios guardados");
					else Window.alert("fail");
				}

				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});

			if (eliminadosLista.get(index).booleanValue()) {
				loginService.eliminarUsuario(idLista.get(index).longValue(),
						new AsyncCallback<Boolean>() {
							public void onSuccess(Boolean result) {
								// Window.alert("Cambios guardados");
							}

							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
							}
						});
			}

		}
	}

	private void llenarTabla() {
		loginService.listarUsuarios(new AsyncCallback<List<Usuario>>() {
			public void onSuccess(List<Usuario> result) {

				for (int index = 0; index < result.size(); index++) {
					Usuario temporal = result.get(index);
					flexTable.setWidget(index + 1, 0, new Label(temporal.getNombre()));
					flexTable.setWidget(index + 1, 1, new Label("" + temporal.getId()));
					flexTable.setWidget(index + 1, 2, new CheckBox());
					if (temporal.activo)
						((CheckBox) flexTable.getWidget(index + 1, 2))
								.setValue(true);
					else
						((CheckBox) flexTable.getWidget(index + 1, 2))
								.setValue(false);
					flexTable.setWidget(index + 1, 3, new CheckBox());
					if (temporal.admin)
						((CheckBox) flexTable.getWidget(index + 1, 3))
								.setValue(true);
					else
						((CheckBox) flexTable.getWidget(index + 1, 3))
								.setValue(false);
					flexTable.setWidget(index + 1, 4, new CheckBox());
				}
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	
}