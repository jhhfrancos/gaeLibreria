package co.edu.unal.client.paneles;

import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.ScrollPanel;

public class ReportesWindow extends Composite implements ClickHandler {

	private final FlexTable flexTable = new FlexTable();
	private final FlexTable flexTablePrincipal = new FlexTable();

	private final Label codigoLabel = new Label("Codigo");
	private final Label tituloLabel = new Label("Titulo");
	private final Label autorLabel = new Label("Autor");
	private final Label cantidadLabel = new Label("Cantidad");
	private final Label costoLabel = new Label("Costo");

	private final ScrollPanel scrollPanel = new ScrollPanel();
	

	private final LibraryServiceAsync libService = GWT
			.create(LibraryService.class);

	private final Button recargarButton = new Button("Refrescar");

	public ReportesWindow() {

		initWidget(flexTablePrincipal);

		scrollPanel.setSize("600px", "400px");

		flexTable.setWidget(0, 0, codigoLabel);
		flexTable.setWidget(0, 1, tituloLabel);
		flexTable.setWidget(0, 2, autorLabel);
		flexTable.setWidget(0, 3, cantidadLabel);
		flexTable.setWidget(0, 4, costoLabel);
		
		codigoLabel.setSize("90px", "25px");
		tituloLabel.setSize("90px", "25px");
		autorLabel.setSize("90px", "25px");
		cantidadLabel.setSize("90px", "25px");
		costoLabel.setSize("90px", "25px");
		
		llenarTabla();

		scrollPanel.add(flexTable);
		flexTablePrincipal.setWidget(0, 0, scrollPanel);
		flexTablePrincipal.setWidget(1, 1, recargarButton);
		
		recargarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				llenarTabla();
			}
		});
	}

	public void onClick(ClickEvent event) {
		if (event.getSource() == null) {
		}
	}
	
	public void masterReset(){
		for(int index = 1; index < flexTable.getRowCount(); index++)
			flexTable.removeRow(index);
	}

	private void llenarTabla() {
		masterReset();
		libService.getDatosReporte(new AsyncCallback<List<ArrayList<String>>>() {
			public void onSuccess(List<ArrayList<String>> result) {
				for (int index = 0; index < result.size(); index++) {
					
					FlexTable temp = new FlexTable();

					temp.setWidget(0, 0, new Label("" + result.get(index).get(3)));
					temp.setWidget(1, 0, new Label("-" + result.get(index).get(4)));

					flexTable.setWidget(index+1, 0, new Label( result.get(index).get(0)));
					flexTable.setWidget(index+1, 1, new Label( result.get(index).get(1)));
					flexTable.setWidget(index+1, 2, new Label( result.get(index).get(2)));
					flexTable.setWidget(index+1, 3, temp);
					flexTable.setWidget(index+1, 4, new Label( result.get(index).get(5)));
				}
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}
}
