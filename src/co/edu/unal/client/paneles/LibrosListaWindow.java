package co.edu.unal.client.paneles;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.client.services.LibraryService;
import co.edu.unal.client.services.LibraryServiceAsync;
import co.edu.unal.shared.Libro;

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

public class LibrosListaWindow extends Composite {
	private final FlexTable flexTable = new FlexTable();

	private final Label isbnLabel = new Label("ISBN");
	private final Label tituloLabel = new Label("Titulo");
	private final Label autorLabel = new Label("Autor");
	private final Label categoriaLabel = new Label("Categoria");
	private final Label editorialLabel = new Label("Editorial");
	private final Label cantidadLabel = new Label("Cantidad");
	private final Label costoLabel = new Label("Costo");
	private final Label eliminarLabel = new Label("Eliminar");

	private List<Libro> librosEliminados = new ArrayList<Libro>();
	private List<Libro> resultado;

	private final ScrollPanel scrollPanel = new ScrollPanel();

	private final LibraryServiceAsync libService = GWT
			.create(LibraryService.class);

	public LibrosListaWindow(List<Libro> resultado, List<Integer> cantidad,
			Button guardarCambiosButton, boolean admin) {
		initWidget(scrollPanel);

		this.resultado = resultado;

		scrollPanel.setSize("600px", "480px");

		flexTable.setWidget(0, 0, isbnLabel);
		flexTable.setWidget(0, 1, tituloLabel);
		flexTable.setWidget(0, 2, autorLabel);
		flexTable.setWidget(0, 3, categoriaLabel);
		flexTable.setWidget(0, 4, editorialLabel);
		flexTable.setWidget(0, 5, cantidadLabel);
		flexTable.setWidget(0, 6, costoLabel);

		/*isbnLabel.setSize("95px", "25px");
		tituloLabel.setSize("95px", "25px");
		autorLabel.setSize("95px", "25px");
		categoriaLabel.setSize("95px", "25px");
		editorialLabel.setSize("95px", "25px");
		cantidadLabel.setSize("95px", "25px");
		costoLabel.setSize("95px", "25px");*/

		if (admin)
			flexTable.setWidget(0, 7, eliminarLabel);

		// flexTable.setSize("600px", "400px");

		for (int index = 0; index < resultado.size(); index++) {
			flexTable.setWidget(index + 1, 0, new Label(resultado.get(index)
					.getIsbn() + ""));
			flexTable.setWidget(index + 1, 1, new Label(resultado.get(index)
					.getTitulo()));
			flexTable.setWidget(index + 1, 2, new Label(resultado.get(index)
					.getNombreAutor()));
			flexTable.setWidget(index + 1, 3, new Label(resultado.get(index)
					.getCategoria()));
			flexTable.setWidget(index + 1, 4, new Label(resultado.get(index)
					.getEditorial()));
			flexTable.setWidget(index + 1, 5, new Label(cantidad.get(index)
					+ ""));
			flexTable.setWidget(index + 1, 6, new Label(resultado.get(index)
					.getCosto() + ""));
			if (admin)
				flexTable.setWidget(index + 1, 7, new CheckBox());
		}
		scrollPanel.add(flexTable);

		guardarCambiosButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int counter = 0;
				for (int fila = 1; fila < flexTable.getRowCount(); fila++) {
					if (((CheckBox) flexTable.getWidget(fila, 7)).getValue()) {
						agregarEliminado(fila);
						counter++;
					}
				}
				if (counter == 0)
					Window.alert("No hay cambios por confirmar.");
				else {
					while (librosEliminados.isEmpty())
						;
					remover();
				}
			}
		});
	}

	private void agregarEliminado(int fila) {
		this.librosEliminados.add(this.resultado.get(fila - 1));
	}


	protected void remover() {
		if (!librosEliminados.isEmpty()) {
			for (Libro l : librosEliminados) {
				libService.borrarLibro(l, new AsyncCallback<Boolean>() {
					public void onSuccess(Boolean result) {
						if (!result.booleanValue())
							Window.alert("Ocurrio un error.");
					}

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
			}
			Window.alert("Libros Eliminados");
			librosEliminados.clear();
		}
	}// end of remover
}