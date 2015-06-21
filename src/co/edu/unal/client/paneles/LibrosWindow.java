package co.edu.unal.client.paneles;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.client.services.LibraryService;
import co.edu.unal.client.services.LibraryServiceAsync;
import co.edu.unal.shared.FieldVerifier;
import co.edu.unal.shared.Inventario;
import co.edu.unal.shared.Libro;
import co.edu.unal.shared.Usuario;

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

public class LibrosWindow extends Composite{

	private final FlexTable flexTable = new FlexTable();
	private final FlexTable flexTablePrincipal = new FlexTable();

	private final Label headerInfoLabel = new Label("Informacion del libro:");
	private final Label categoriaLabel = new Label("Categoria:");
	private final Label cantidadLabel = new Label("Cantidad:");
	private final Label editorialLabel = new Label("Editorial:");
	private final Label autorLabel = new Label("Autor:");
	private final Label costoLabel = new Label("Costo:");
	private final Label isbnLabel = new Label("ISBN:");
	private final Label tituloLabel = new Label("Titulo:");

	private final TextBox cantidadTextBox = new TextBox();
	private final TextBox editorialTextBox = new TextBox();
	private final TextBox autorTextBox = new TextBox();
	private final TextBox isbnTextBox = new TextBox();
	private final TextBox costoTextBox = new TextBox();
	private final TextBox tituloTextBox = new TextBox();
	private final TextBox categoriaTextBox = new TextBox();

	private Button buscarButton = new Button("Buscar");
	private Button agregarButton = new Button("Agregar");
	private Button volverButton = new Button("Volver");
	private Button guardarCambiosButton = new Button("Confirmar cambios");

	private Usuario usuario;

	List<Integer> cantidad = new ArrayList<Integer>();

	private LibrosListaWindow resultado;

	private final LibraryServiceAsync libService = GWT
			.create(LibraryService.class);

	public LibrosWindow(Usuario usuario) {

		initWidget(flexTablePrincipal);

		this.usuario = usuario;

		flexTable.setWidget(0, 0, headerInfoLabel);
		flexTable.setWidget(1, 0, isbnLabel);
		flexTable.setWidget(1, 1, isbnTextBox);
		flexTable.setWidget(2, 0, tituloLabel);
		flexTable.setWidget(2, 1, tituloTextBox);
		flexTable.setWidget(3, 0, autorLabel);
		flexTable.setWidget(3, 1, autorTextBox);
		flexTable.setWidget(4, 0, categoriaLabel);
		flexTable.setWidget(4, 1, categoriaTextBox);
		flexTable.setWidget(5, 0, editorialLabel);
		flexTable.setWidget(5, 1, editorialTextBox);
		flexTable.setWidget(6, 0, cantidadLabel);
		flexTable.setWidget(6, 1, cantidadTextBox);
		flexTable.setWidget(7, 0, costoLabel);
		flexTable.setWidget(7, 1, costoTextBox);

		flexTable.setWidget(11, 1, buscarButton);
		flexTable.setWidget(11, 2, agregarButton);

		flexTablePrincipal.setWidget(0, 0, flexTable);

		agregarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				agregar();
			}
		});
		buscarButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				buscar();
			}
		});
		volverButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				masterReset();
			}
		});
	}

	public void masterReset() {
		flexTablePrincipal.setWidget(0, 0, flexTable);
		volverButton.setVisible(false);
		guardarCambiosButton.setVisible(false);
		cantidad.clear();
	}

	private void mostrarResultado(List<Libro> result) {

		libService.listarInventario(new AsyncCallback<List<Inventario>>() {
			public void onSuccess(List<Inventario> result) {
				for (Inventario i : result) {
					addCantidad(new Integer(i.getCantidad()));
				}
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});

		Window.alert("Busqueda realizada.");
		resultado = new LibrosListaWindow(result, this.cantidad,
				guardarCambiosButton, usuario.admin);

		limpiarFormulario();

		flexTablePrincipal.setWidget(0, 0, resultado);
		flexTablePrincipal.setWidget(1, 0, guardarCambiosButton);
		flexTablePrincipal.setWidget(1, 1, volverButton);
		volverButton.setVisible(true);
		if (usuario.admin)
			guardarCambiosButton.setVisible(true);
	}

	private void addCantidad(Integer cantidadI) {
		this.cantidad.add(cantidadI);
	}

	protected void buscar() {

		String name;
		String autor;
		String categoria;
		String editorial;

		name = tituloTextBox.getText().toLowerCase();
		autor = autorTextBox.getText().toLowerCase();
		categoria = categoriaTextBox.getText().toLowerCase();
		editorial = editorialTextBox.getText().toLowerCase();

		if (!(name.equals("") || name == null)) {

			libService.listarLibrosN(name, new AsyncCallback<List<Libro>>() {
				public void onSuccess(List<Libro> result) {
					if (result.isEmpty())
						Window.alert("No se encontraron coincidencias.");
					else
						mostrarResultado(result);
				}

				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});
		} else if (!(autor.equals("") ||  autor == null)) {
			libService.listarLibrosA(autor, new AsyncCallback<List<Libro>>() {
				public void onSuccess(List<Libro> result) {
					if (result.isEmpty())
						Window.alert("No se encontraron coincidencias.");
					else
						mostrarResultado(result);
				}

				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});
		} else if (!(categoria.equals("") || categoria == null)) {
			libService.listarLibrosC(categoria,
					new AsyncCallback<List<Libro>>() {
						public void onSuccess(List<Libro> result) {
							if (result.isEmpty())
								Window.alert("No se encontraron coincidencias.");
							else
								mostrarResultado(result);
						}

						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}
					});
		} else if (!(editorial.equals("") || editorial == null)) {
			libService.listarLibrosE(editorial,
					new AsyncCallback<List<Libro>>() {
						public void onSuccess(List<Libro> result) {
							if (result.isEmpty())
								Window.alert("No se encontraron coincidencias.");
							else
								mostrarResultado(result);
						}

						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}
					});
		} else Window.alert("Campos vacios.");

	}// end of saveLibro

	protected void guardar(Libro libro, int cantidad) {
		libService.guardarLibro(libro, cantidad, new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				if (result) {
					limpiarFormulario();
					Window.alert("libro guardado");
				} else
					Window.alert("Error interno");
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}// end of guardar

	protected void agregar() {
		String name;
		long ISBN;
		if (isbnTextBox.getText().equals(""))
			isbnTextBox.setText("0");
		if (costoTextBox.getText().equals(""))
			costoTextBox.setText("0");
		name = tituloTextBox.getText().toLowerCase();
		ISBN = Long.parseLong(isbnTextBox.getText());
		if (usuario.admin) {
			if (FieldVerifier.isValidName(name)
					|| FieldVerifier.isValidISBN(ISBN)) {
				libService.loadLibro(name, ISBN, new AsyncCallback<Libro>() {
					public void onSuccess(Libro result) {
						if (!(result == null)) {
							Window.alert("Ya existe un libro con ese nombre o ISBN, se actualizara la cantidad.");
							Libro libro = result;

							int tempInt = 1;
							try {
								tempInt = Integer.parseInt(cantidadTextBox
										.getText());
							} catch (Exception e) {
								Window.alert("Cantidad invalida.");
							}
							guardar(libro, tempInt);
						} else {
							if (FieldVerifier.isValidName(tituloTextBox
									.getText())
									|| FieldVerifier.isValidISBN(Long
											.parseLong(isbnTextBox.getText()))) {
								Libro libro = new Libro(Long
										.parseLong(isbnTextBox.getText()),
										tituloTextBox.getText().toLowerCase(),
										autorTextBox.getText().toLowerCase(),
										categoriaTextBox.getText()
										.toLowerCase(),
										editorialTextBox.getText()
										.toLowerCase(), Double
										.parseDouble(costoTextBox
												.getText()));
								guardar(libro, Integer.parseInt(cantidadTextBox
										.getText()));
							}
						}
						limpiarFormulario();
					}

					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
			} else {
				Window.alert("ISBN o Titulo invalidos.");
			}
		} else {
			Window.alert("Permisos insuficientes.");
		}
	}// end of agregar

	private void limpiarFormulario() {
		cantidadTextBox.setText("");
		editorialTextBox.setText("");
		autorTextBox.setText("");
		isbnTextBox.setText("");
		costoTextBox.setText("");
		tituloTextBox.setText("");
		categoriaTextBox.setText("");
	}

}
