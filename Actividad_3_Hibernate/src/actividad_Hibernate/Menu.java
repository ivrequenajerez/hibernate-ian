package actividad_Hibernate;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Menu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private SessionFactory instancia;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SessionFactory instancia = new Configuration().configure("hibernate.cfg.xml")
							.addAnnotatedClass(Paciente.class).buildSessionFactory();
					Menu frame = new Menu(instancia);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Menu(SessionFactory instancia) {
		this.instancia = instancia;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(155, 11, 89, 23);
		contentPane.add(btnConectar);

		btnConectar.addActionListener(e -> {
			if (realizarConexion()) {
				JOptionPane.showMessageDialog(null, "Conectado exitosamente");
			} else {
				JOptionPane.showMessageDialog(null, "No se ha podido realizar la conexión", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton btnInsertar = new JButton("Insertar P");
		btnInsertar.setBounds(10, 66, 89, 23);
		contentPane.add(btnInsertar);

		btnInsertar.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar datos del paciente por teclado
				String nombre = JOptionPane.showInputDialog("Ingrese el nombre del paciente:");
				String apellidos = JOptionPane.showInputDialog("Ingrese el apellido del paciente:");
				String direccion = JOptionPane.showInputDialog("Ingrese la dirección del paciente:");
				String telefono = JOptionPane.showInputDialog("Ingrese el número de teléfono del paciente:");
				String fechaNacimientoStr = JOptionPane
						.showInputDialog("Ingrese la fecha de nacimiento del paciente (yyyy-MM-dd):");

				try {
					// Convertir la cadena de fecha a java.sql.Date
					java.sql.Date fechaNacimiento = convertirAFechaSQL(fechaNacimientoStr);

					// Crear objeto Paciente con los datos proporcionados
					Paciente paciente = new Paciente(nombre, apellidos, direccion, telefono, fechaNacimiento);

					// Insertar el paciente en la base de datos
					insertarPaciente(instancia, paciente);

					JOptionPane.showMessageDialog(null, "Paciente insertado exitosamente.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error al insertar el paciente. Error: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton btnBorrarCita = new JButton("Borrar Cita");
		btnBorrarCita.setBounds(182, 117, 110, 23);
		contentPane.add(btnBorrarCita);

		btnBorrarCita.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar ID de la cita a borrar por teclado
				int idCitaBorrar = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la cita a borrar:"));

				// Llamar al método borrarCita con la lógica actualizada
				borrarCita(instancia, idCitaBorrar);
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton btnActualizar = new JButton("Actualizar P");
		btnActualizar.setBounds(95, 66, 89, 23);
		contentPane.add(btnActualizar);

		btnActualizar.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar ID del paciente a actualizar por teclado
				int idPacienteActualizar = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente a actualizar:"));

				// Consultar y mostrar información actual del paciente
				Paciente pacienteActual = consultarPaciente(instancia, idPacienteActualizar);

				if (pacienteActual != null) {
					JOptionPane.showMessageDialog(null,
							"Información actual del paciente:\n" + pacienteActual.toString());

					// Crear una copia del paciente original
					Paciente pacienteOriginal = new Paciente(pacienteActual.getNombre(), pacienteActual.getApellidos(),
							pacienteActual.getDirección(), pacienteActual.getTeléfono(),
							pacienteActual.getUltimaConsulta());

					// Solicitar cambios en los campos
					while (true) {
						String[] opciones = { "Nombre", "Apellidos", "Dirección", "Teléfono", "Fecha de Nacimiento",
								"Terminar" };

						int opcionElegida = JOptionPane.showOptionDialog(null, "Seleccione el campo a actualizar:",
								"Actualizar Paciente", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
								opciones, opciones[0]);

						if (opcionElegida == 5) {
							// Terminar la actualización
							break;
						}

						// Solicitar nuevo valor para el campo seleccionado
						String nuevoValor = JOptionPane.showInputDialog("Ingrese el nuevo valor:");

						// Actualizar el campo correspondiente
						switch (opcionElegida) {
						case 0:
							pacienteActual.setNombre(nuevoValor);
							break;
						case 1:
							pacienteActual.setApellidos(nuevoValor);
							break;
						case 2:
							pacienteActual.setDirección(nuevoValor);
							break;
						case 3:
							pacienteActual.setTeléfono(nuevoValor);
							break;
						case 4:
							try {
								// Convertir la cadena de fecha a java.sql.Date
								java.sql.Date nuevaFechaNacimiento = convertirAFechaSQL(nuevoValor);
								pacienteActual.setUltimaConsulta(nuevaFechaNacimiento);
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null,
										"Error al convertir la fecha. Asegúrese de usar el formato correcto (yyyy-MM-dd).",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
							break;
						}
					}

					// Actualizar el paciente en la base de datos
					actualizarPaciente(instancia, pacienteActual);

					JOptionPane.showMessageDialog(null, "Paciente actualizado exitosamente.");
				} else {
					JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		// ...
		JButton btnBorrar = new JButton(" Borrar P");
		btnBorrar.setBounds(182, 66, 89, 23);
		contentPane.add(btnBorrar);

		JButton btnConsultar = new JButton("Consultar P");
		btnConsultar.setBounds(268, 66, 89, 23);
		contentPane.add(btnConsultar);

		JButton btnCrearCita = new JButton("Crear Cita");
		btnCrearCita.setBounds(10, 117, 89, 23);
		contentPane.add(btnCrearCita);

		JButton btnConsultarCita = new JButton("Consultar C");
		btnConsultarCita.setBounds(281, 117, 89, 23);
		contentPane.add(btnConsultarCita);

		btnConsultarCita.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar ID de la cita a consultar por teclado
				int idCitaConsultar = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la cita a consultar:"));

				// Consultar la cita en la base de datos
				Cita citaConsultada = consultarCita(instancia, idCitaConsultar);

				if (citaConsultada != null) {
					JOptionPane.showMessageDialog(null, "Información actual de la cita:\n" + citaConsultada.toString());
				} else {
					JOptionPane.showMessageDialog(null, "No se encontró una cita con el ID proporcionado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnCrearCita.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar ID del paciente para crear la cita
				int idPacienteCrearCita = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente para crear la cita:"));

				// Consultar el paciente en la base de datos
				Paciente pacienteConsultado = consultarUnPaciente(instancia, idPacienteCrearCita);

				if (pacienteConsultado != null) {
					// Solicitar los datos para la creación de una nueva cita por teclado
					String fechaCitaString = JOptionPane
							.showInputDialog("Ingrese la fecha de la cita (formato: YYYY-MM-DD):");
					Date fechaCita = convertirAFechaSQL(fechaCitaString);

					String horaCita = JOptionPane.showInputDialog("Ingrese la hora de la cita:");
					String motivoCita = JOptionPane.showInputDialog("Ingrese el motivo de la cita:");
					String idDoctor = JOptionPane.showInputDialog("Ingrese el ID del doctor:");

					// Crear una instancia de Cita con los datos ingresados
					Cita cita = new Cita(fechaCita, horaCita, motivoCita, pacienteConsultado, idDoctor);

					// Agregar la cita al paciente
					pacienteConsultado.addCita(cita, instancia.openSession());

					// Mostrar información sobre la cita
					JOptionPane.showMessageDialog(null, "Cita creada exitosamente:\n" + cita.toString());
				} else {
					JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		JButton btnActualizarCita = new JButton("Actualizar C");
		btnActualizarCita.setBounds(95, 117, 89, 23);
		contentPane.add(btnActualizarCita);

		btnActualizarCita.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar ID de la cita a actualizar por teclado
				int idCitaActualizar = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la cita a actualizar:"));

				// Consultar y mostrar información actual de la cita
				Cita citaActual = consultarCita(instancia, idCitaActualizar);

				if (citaActual != null) {
					JOptionPane.showMessageDialog(null, "Información actual de la cita:\n" + citaActual.toString());

					// Solicitar cambios en los campos
					while (true) {
						String[] opciones = { "Fecha", "Hora", "Motivo", "ID Doctor", "Terminar" };

						int opcionElegida = JOptionPane.showOptionDialog(null, "Seleccione el campo a actualizar:",
								"Actualizar Cita", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
								opciones, opciones[0]);

						if (opcionElegida == 4) {
							// Terminar la actualización
							break;
						}

						// Solicitar nuevo valor para el campo seleccionado
						String nuevoValor = JOptionPane.showInputDialog("Ingrese el nuevo valor:");

						// Actualizar el campo correspondiente
						switch (opcionElegida) {
						case 0:
							try {
								// Convertir la cadena de fecha a java.util.Date
								Date nuevaFecha = convertirAFechaSQL(nuevoValor);
								citaActual.setFecha(nuevaFecha);
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(null,
										"Error al convertir la fecha. Asegúrese de usar el formato correcto (yyyy-MM-dd).",
										"Error", JOptionPane.ERROR_MESSAGE);
							}
							break;
						case 1:
							citaActual.setHora(nuevoValor);
							break;
						case 2:
							citaActual.setMotivo(nuevoValor);
							break;
						case 3:
							citaActual.setIdDoctor(nuevoValor);
							break;
						}
					}

					// Actualizar la cita en la base de datos
					actualizarCita(instancia, citaActual);

					JOptionPane.showMessageDialog(null, "Cita actualizada exitosamente.");
				} else {
					JOptionPane.showMessageDialog(null, "No se encontró una cita con el ID proporcionado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnBorrar.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar ID del paciente a borrar por teclado
				int idPacienteBorrar = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID del paciente a borrar:"));

				// Llamar al método borrarPaciente con la lógica actualizada
				borrarPaciente(instancia, idPacienteBorrar);
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		JButton btnConsultarEspecialidad = new JButton("Consultar Especialidad");
		btnConsultarEspecialidad.setBounds(196, 178, 89, 25);
		contentPane.add(btnConsultarEspecialidad);

		btnConsultarEspecialidad.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar ID de la especialidad a consultar por teclado
				int idEspecialidadConsultar = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la especialidad a consultar:"));

				// Llamar a la función de consulta de especialidad
				consultarEspecialidad(instancia, idEspecialidadConsultar);
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton btnCrearEspecialidad = new JButton("Crear Especialidad");
		btnCrearEspecialidad.setBounds(10, 178, 101, 24);
		contentPane.add(btnCrearEspecialidad);

		btnCrearEspecialidad.addActionListener(e -> {
			if (instancia != null) {
				// Llamar al método crearEspecialidad con la lógica actualizada
				crearEspecialidad(instancia);
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton btnActualizarEspecialidad = new JButton("Actualizar Especialidad");
		btnActualizarEspecialidad.setBounds(108, 179, 81, 23);
		contentPane.add(btnActualizarEspecialidad);

		btnActualizarEspecialidad.addActionListener(e -> {
			if (instancia != null) {
				// Solicitar ID de la especialidad a actualizar por teclado
				int idEspecialidadActualizar = Integer
						.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la especialidad a actualizar:"));

				// Llamar al método actualizarEspecialidad con la lógica actualizada
				actualizarEspecialidad(instancia, idEspecialidadActualizar);
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		JButton btnBorrarEspecialidad = new JButton("Borrar Especialidad");
		btnBorrarEspecialidad.setBounds(281, 179, 89, 23);
		contentPane.add(btnBorrarEspecialidad);

		btnBorrarEspecialidad.addActionListener(e -> {
			if (instancia != null) {
				try {
					// Solicitar ID de la especialidad a borrar por teclado
					int idEspecialidadBorrar = Integer
							.parseInt(JOptionPane.showInputDialog("Ingrese el ID de la especialidad a borrar:"));

					// Llamar al método borrarEspecialidad con la lógica actualizada
					borrarEspecialidad(instancia, idEspecialidadBorrar);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Ingrese un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		// ...

	}

	private boolean realizarConexion() {
		try {
			if (instancia == null) {
				// Configuración de Hibernate y conexión
				Configuration configuration = new Configuration().configure("hibernate.cfg.xml")
						.addAnnotatedClass(Paciente.class);
				instancia = configuration.buildSessionFactory();
			}

			// Sesiones. Factory crea la sesión conforme al fichero de configuración de
			// hibernate
			// Añadimos tantas clases como utilicemos con addAnnotatedClass
			// Session. Crea la sesión de conexión a la base de datos
			Session session = instancia.openSession();

			// Realizar acciones adicionales si es necesario...

			// Cerrar la sesión al finalizar
			session.close();

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			// Manejo de excepciones o errores de conexión
			return false;
		}
	}

	private static void insertarPaciente(SessionFactory sessionFactory, Paciente paciente) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.save(paciente);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void actualizarPaciente(SessionFactory sessionFactory, Paciente paciente) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.update(paciente);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Paciente consultarPaciente(SessionFactory sessionFactory, int idPaciente) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Paciente.class, idPaciente);
		} catch (Exception e) {
			System.err.println("Error al consultar el paciente. Error: " + e.getMessage());
			return null;
		}
	}

	private static void borrarPaciente(SessionFactory sessionFactory, int idPaciente) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			// Obtener el paciente por ID
			Paciente paciente = session.get(Paciente.class, idPaciente);

			if (paciente != null) {
				JOptionPane.showMessageDialog(null, "Información actual del paciente:\n" + paciente.toString());

				// Crear una copia del paciente original
				Paciente pacienteOriginal = new Paciente(paciente.getNombre(), paciente.getApellidos(),
						paciente.getDirección(), paciente.getTeléfono(), paciente.getUltimaConsulta());

				// Solicitar campos a borrar
				JPanel panel = new JPanel();
				JCheckBox chkNombre = new JCheckBox("Nombre");
				JCheckBox chkApellidos = new JCheckBox("Apellidos");
				JCheckBox chkDireccion = new JCheckBox("Dirección");
				JCheckBox chkTelefono = new JCheckBox("Teléfono");
				JCheckBox chkUltimaConsulta = new JCheckBox("Ultima Consulta");

				panel.add(chkNombre);
				panel.add(chkApellidos);
				panel.add(chkDireccion);
				panel.add(chkTelefono);
				panel.add(chkUltimaConsulta);

				int result = JOptionPane.showConfirmDialog(null, panel, "Seleccione campos a borrar",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					// Borrar los campos seleccionados
					if (chkNombre.isSelected()) {
						paciente.setNombre("");
					}
					if (chkApellidos.isSelected()) {
						paciente.setApellidos("");
					}
					if (chkDireccion.isSelected()) {
						paciente.setDirección("");
					}
					if (chkTelefono.isSelected()) {
						paciente.setTeléfono("");
					}
					if (chkUltimaConsulta.isSelected()) {
						paciente.setUltimaConsulta(null);
					}

					// Actualizar el paciente en la base de datos
					session.update(paciente);
					session.getTransaction().commit();

					JOptionPane.showMessageDialog(null, "Campos borrados exitosamente.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			System.err.println("No se puede borrar el paciente. Error: " + e.getMessage());
		}
	}

	private static Cita consultarCita(SessionFactory sessionFactory, int idCita) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Cita.class, idCita);
		} catch (Exception e) {
			System.err.println("Error al consultar la cita. Error: " + e.getMessage());
			return null;
		}
	}

	private static Paciente consultarUnPaciente(SessionFactory sessionFactory, int idPaciente) {
		Paciente paciente = null;
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			paciente = session.get(Paciente.class, idPaciente);

			// Forzar la carga de la colección antes de cerrar la sesión
			if (paciente != null) {
				paciente.getCitas().size();
			}

			session.getTransaction().commit();

			if (paciente != null) {
				JOptionPane.showMessageDialog(null, "Paciente consultado exitosamente:\n" + paciente.toString());
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró un paciente con el ID proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al consultar el paciente. Error: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return paciente;
	}

	private static void actualizarCita(SessionFactory sessionFactory, Cita cita) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.update(cita);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void borrarCita(SessionFactory sessionFactory, int idCita) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			// Obtener la cita por ID
			Cita cita = session.get(Cita.class, idCita);

			if (cita != null) {
				JOptionPane.showMessageDialog(null, "Información actual de la cita:\n" + cita.toString());

				// Borrar la cita en la base de datos
				session.delete(cita);
				session.getTransaction().commit();

				JOptionPane.showMessageDialog(null, "Cita borrada exitosamente.");
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró una cita con el ID proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			System.err.println("No se puede borrar la cita. Error: " + e.getMessage());
		}
	}

	private static void insertarEspecialidad(SessionFactory sessionFactory, Especialidad especialidad) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.save(especialidad);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Doctor consultarDoctor(SessionFactory sessionFactory, int idDoctor) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(Doctor.class, idDoctor);
		} catch (Exception e) {
			System.err.println("Error al consultar el doctor. Error: " + e.getMessage());
			return null;
		}
	}

	private static void actualizarEspecialidad(SessionFactory sessionFactory, int idEspecialidad) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			// Obtener la especialidad por ID
			Especialidad especialidadActual = session.get(Especialidad.class, idEspecialidad);

			if (especialidadActual != null) {
				JOptionPane.showMessageDialog(null,
						"Información actual de la especialidad:\n" + especialidadActual.toString());

				// Solicitar cambios en los campos
				while (true) {
					String[] opciones = { "Tipo", "Descripción", "Terminar" };

					int opcionElegida = JOptionPane.showOptionDialog(null, "Seleccione el campo a actualizar:",
							"Actualizar Especialidad", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
							opciones, opciones[0]);

					if (opcionElegida == 2) {
						// Terminar la actualización
						break;
					}

					// Solicitar nuevo valor para el campo seleccionado
					String nuevoValor = JOptionPane.showInputDialog("Ingrese el nuevo valor:");

					// Actualizar el campo correspondiente
					switch (opcionElegida) {
					case 0:
						especialidadActual.setTipo(nuevoValor);
						break;
					case 1:
						especialidadActual.setDescripcion(nuevoValor);
						break;
					}
				}

				// Actualizar la especialidad en la base de datos
				session.update(especialidadActual);
				session.getTransaction().commit();

				JOptionPane.showMessageDialog(null, "Especialidad actualizada exitosamente.");
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró una especialidad con el ID proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se puede actualizar la especialidad. Error: " + e.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void borrarEspecialidad(SessionFactory sessionFactory, int idEspecialidad) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			// Obtener la especialidad por ID
			Especialidad especialidad = session.get(Especialidad.class, idEspecialidad);

			if (especialidad != null) {
				JOptionPane.showMessageDialog(null,
						"Información actual de la especialidad:\n" + especialidad.toString());

				// Crear una copia de la especialidad original
				Especialidad especialidadOriginal = new Especialidad(especialidad.getTipo(),
						especialidad.getDescripcion());

				// Confirmar la eliminación
				int confirmacion = JOptionPane.showConfirmDialog(null,
						"¿Está seguro de que desea borrar la especialidad?", "Confirmar Borrado",
						JOptionPane.YES_NO_OPTION);

				if (confirmacion == JOptionPane.YES_OPTION) {
					// Borrar la especialidad en la base de datos
					session.delete(especialidad);
					session.getTransaction().commit();

					JOptionPane.showMessageDialog(null, "Especialidad borrada exitosamente.");
				} else {
					JOptionPane.showMessageDialog(null, "Operación de borrado cancelada.");
					session.getTransaction().rollback(); // Deshacer la transacción si se cancela
				}
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró una especialidad con el ID proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al borrar la especialidad.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private static void consultarEspecialidad(SessionFactory sessionFactory, int idEspecialidad) {
		try (Session session = sessionFactory.openSession()) {
			// Obtener la especialidad por ID
			Especialidad especialidadConsultada = session.get(Especialidad.class, idEspecialidad);

			if (especialidadConsultada != null) {
				// Mostrar información de la especialidad
				JOptionPane.showMessageDialog(null,
						"Información de la especialidad:\n" + especialidadConsultada.toString());
			} else {
				JOptionPane.showMessageDialog(null, "No se encontró una especialidad con el ID proporcionado.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error al consultar la especialidad.", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private static void crearEspecialidad(SessionFactory sessionFactory) {
		if (sessionFactory != null) {
			try {
				// Solicitar datos para la creación de una nueva especialidad por teclado
				String tipo = JOptionPane.showInputDialog("Ingrese el tipo de especialidad:");
				String descripcion = JOptionPane.showInputDialog("Ingrese la descripción de la especialidad:");

				// Solicitar el ID del doctor para asociar con la especialidad
				int idDoctor = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del doctor asociado:"));

				// Consultar el doctor en la base de datos
				Doctor doctorAsociado = consultarDoctor(sessionFactory, idDoctor);

				if (doctorAsociado != null) {
					// Crear una instancia de Especialidad con los datos ingresados
					Especialidad especialidad = new Especialidad(tipo, descripcion);

					// Asociar la especialidad con el doctor
					doctorAsociado.setEspecialidad(especialidad);

					// Insertar la especialidad en la base de datos
					insertarEspecialidad(sessionFactory, especialidad);

					JOptionPane.showMessageDialog(null,
							"Especialidad creada exitosamente:\n" + especialidad.toString());
				} else {
					JOptionPane.showMessageDialog(null, "No se encontró un doctor con el ID proporcionado.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error al crear la especialidad. Error: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "La conexión no está establecida", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static java.sql.Date convertirAFechaSQL(String fechaStr) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date fechaUtil = sdf.parse(fechaStr);
			return new java.sql.Date(fechaUtil.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
