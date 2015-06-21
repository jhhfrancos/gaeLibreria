package co.edu.unal.client.paneles;

import java.util.List;

import co.edu.unal.client.services.LibraryService;
import co.edu.unal.client.services.LibraryServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class InventarioWindow extends Composite implements ClickHandler {
	private final FlexTable flexTable = new FlexTable();

	private final Label headerInfoLabel = new Label("Informacion del libro:");
	private final Label headerMovLabel = new Label("Movimiento:");
	private final Label cantidadLabel = new Label("Cantidad:");
	private final Label salidasLabel = new Label("Salidas:");
	private final Label entradasLabel = new Label("Entradas:");
	private final Label costoLabel = new Label("Costo:");
	private final Label isbnLabel = new Label("ISBN:");
	private final Label tituloLabel = new Label("Titulo:");

	private final TextBox cantidadTextBox = new TextBox();
	private final TextBox salidasTextBox = new TextBox();
	private final TextBox entradasTextBox = new TextBox();
	private final TextBox isbnTextBox = new TextBox();
	private final TextBox costoTextBox = new TextBox();
	private final TextBox tituloTextBox = new TextBox();

	private Button aceptarButton = new Button("Consultar");

	private final LibraryServiceAsync libService = GWT
			.create(LibraryService.class);

	public InventarioWindow() {
		initWidget(flexTable);

		flexTable.setWidget(0, 0, headerInfoLabel);
		flexTable.setWidget(1, 0, isbnLabel);
		flexTable.setWidget(1, 1, isbnTextBox);
		flexTable.setWidget(2, 0, tituloLabel);
		flexTable.setWidget(2, 1, tituloTextBox);
		tituloTextBox.setEnabled(false);

		flexTable.setWidget(3, 0, headerMovLabel);
		flexTable.setWidget(4, 0, cantidadLabel);
		flexTable.setWidget(4, 1, cantidadTextBox);
		cantidadTextBox.setEnabled(false);
		
		flexTable.setWidget(5, 0, salidasLabel);
		flexTable.setWidget(5, 1, salidasTextBox);
		salidasTextBox.setEnabled(false);
		
		flexTable.setWidget(6, 0, entradasLabel);
		flexTable.setWidget(6, 1, entradasTextBox);
		entradasTextBox.setEnabled(false);
		
		flexTable.setWidget(7, 0, costoLabel);
		flexTable.setWidget(7, 1, costoTextBox);
		costoTextBox.setEnabled(false);

		flexTable.setWidget(11, 4, aceptarButton);

		aceptarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				llenarFormulario();
			}
		});
	}

	protected void llenarFormulario() {

		long isbn = 0;

		try {
			isbn = Long.parseLong(this.isbnTextBox.getValue());
			libService.getDatosInventario(isbn,
					new AsyncCallback<List<String>>() {
						public void onSuccess(List<String> result) {
							tituloTextBox.setText(result.get(1));
							cantidadTextBox.setText(result.get(2));
							salidasTextBox.setText(result.get(3));
							entradasTextBox.setText(result.get(4));
							costoTextBox.setText(result.get(5));
						}

						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}
					});
		} catch (Exception e) {
			Window.alert("ISBN invalido.");
		}
	}

	public void onClick(ClickEvent event) {
		if (event.getSource() == null) {
		}
	}
}
