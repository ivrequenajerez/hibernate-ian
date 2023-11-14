/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package actividad_Hibernate;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Ian
 */
public class HibernateAnt {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] argas) {
		try {
			
			// SessionFactory instancia = (SessionFactory)
			// HibernateUtil.buildSessionFactory();
			// Sesiones. Factory crea la sesión conforme al fichero de configuración de
			// hibernate
			// Añadimos tantas clases como utilicemos con addAnnotatedClass
			// Session. Crea la sesión de conexión a la base de datos
			
			SessionFactory instancia = (SessionFactory) new Configuration().configure("hibernate.cfg.xml")
					.addAnnotatedClass(Paciente.class).buildSessionFactory();
			Session session = instancia.openSession();

			Query<Paciente> query = session.createQuery("FROM Paciente", Paciente.class);
			List<Paciente> pacientes = query.getResultList();

			// System.out.println("Número de pacientes: " + pacientes.size());

			/*for (Paciente paciente : pacientes) {
				System.out.println(paciente);
			}*/

			// Operaciones CRUD
			Scanner scanner = new Scanner(System.in);
			
			/*
			// Inserción
			// Solicitar datos del paciente por teclado
			System.out.print("Ingrese el nombre del paciente: ");
			String nombre = scanner.nextLine();

			System.out.print("Ingrese el apellido del paciente: ");
			String apellido = scanner.nextLine();

			System.out.print("Ingrese la dirección del paciente: ");
			String direccion = scanner.nextLine();

			System.out.print("Ingrese el número de teléfono del paciente: ");
			String telefono = scanner.nextLine();

			System.out.print("Ingrese la fecha de nacimiento del paciente (yyyy-MM-dd): ");
			String fechaNacimientoStr = scanner.nextLine();

			// Convertir la cadena de fecha a java.sql.Date
			Date fechaNacimiento = convertirAFechaSQL(fechaNacimientoStr);

			// Crear objeto Paciente con los datos proporcionados
			Paciente paciente = new Paciente(nombre, apellido, direccion, telefono, fechaNacimiento);

			// Solicitar el ID del paciente por teclado
			System.out.print("Ingrese el ID del paciente: ");
			int idPaciente = scanner.nextInt();
			paciente.setIdPaciente(idPaciente);

			// Insertar el paciente en la base de datos
			insertarPaciente(instancia, paciente);
			*/
			
			/*
			// ACTUALIZAR
			// Solicitar ID del paciente a actualizar por teclado
            System.out.print("Ingrese el ID del paciente a actualizar: ");
            int idPacienteActualizar = scanner.nextInt();

            // Consultar y mostrar información actual del paciente
            Paciente pacienteActual = consultarPaciente(instancia, idPacienteActualizar);
            System.out.println("Información actual del paciente:");
            System.out.println(pacienteActual);
			
            // Solicitar nuevos datos del paciente por teclado
            System.out.print("Ingrese el nuevo nombre del paciente: ");
            scanner.nextLine();  // Consumir el salto de línea pendiente
            String nuevoNombre = scanner.nextLine();

            System.out.print("Ingrese el nuevo apellido del paciente: ");
            String nuevoApellido = scanner.nextLine();

            System.out.print("Ingrese la nueva dirección del paciente: ");
            String nuevaDireccion = scanner.nextLine();

            System.out.print("Ingrese el nuevo número de teléfono del paciente: ");
            String nuevoTelefono = scanner.nextLine();

            System.out.print("Ingrese la fecha de nacimiento del paciente (yyyy-MM-dd): ");
			String nuevaFechaNacimientoStr = scanner.nextLine();

			// Convertir la cadena de fecha a java.sql.Date
			Date nuevaFechaNacimiento = convertirAFechaSQL(nuevaFechaNacimientoStr);
			
            // Crear objeto Paciente con los nuevos datos
            Paciente pacienteActualizado = new Paciente(nuevoNombre, nuevoApellido, nuevaDireccion, nuevoTelefono, nuevaFechaNacimiento);
            pacienteActualizado.setIdPaciente(idPacienteActualizar);

            // Actualizar el paciente en la base de datos
            actualizarPaciente(instancia, pacienteActualizado);
			*/
			
			/*
			// BORRAR
            // Solicitar ID del paciente a borrar por teclado
            System.out.print("Ingrese el ID del paciente a borrar: ");
            int idPacienteBorrar = scanner.nextInt();

            // Borrar el paciente en la base de datos
            borrarPaciente(instancia, idPacienteBorrar);
			*/
			
			// CONSULTA
	        // Solicitar el ID del paciente por teclado
	        System.out.print("Ingrese el ID del paciente a consultar: ");
	        int idPaciente = scanner.nextInt();

	        // Consultar el paciente en la base de datos
	        Paciente pacienteConsultado = consultarUnPaciente(instancia, idPaciente);

	        if (pacienteConsultado != null) {
	            //System.out.println("Paciente consultado: " + pacienteConsultado);

	        	// Solicitar los datos para la creación de una nueva cita por teclado
	        	System.out.println("Ingrese la fecha de la cita (formato: YYYY-MM-DD): ");
	        	String fechaCitaString = scanner.next();
	        	Date fechaCita = convertirAFechaSQL(fechaCitaString);

	        	System.out.println("Ingrese la hora de la cita: ");
	        	String horaCita = scanner.next();

	        	System.out.println("Ingrese el motivo de la cita: ");
	        	String motivoCita = scanner.next();

	        	System.out.println("Ingrese el ID del doctor: ");
	        	String idDoctor = scanner.next();

	        	// Crear una instancia de Cita con los datos ingresados
	        	Cita cita = new Cita(fechaCita, horaCita, motivoCita, pacienteConsultado, idDoctor);

	        	// Agregar la cita al paciente (si ya tiene citas, se añadirá a la colección existente)
	        	pacienteConsultado.addCita(cita, session);

	        	// Imprimir información para verificar que la cita se ha agregado correctamente al paciente
	        	//System.out.println("Paciente después de agregar la cita: " + pacienteConsultado);
	        	System.out.println("Citas del Paciente: " + pacienteConsultado.getCitas());

	            // Imprimir información para verificar que la cita se ha agregado correctamente al paciente
	            // System.out.println("Paciente después de agregar la cita: " + pacienteConsultado);
	            System.out.println("Citas del Paciente: " + pacienteConsultado.getCitas());
	        } else {
	            System.out.println("No se encontró un paciente con el ID proporcionado.");
	        }

			session.close();
		} catch (HibernateException he) {
			// Capturamos excepciones de Hibernate y las mostramos
			System.out.println("Error de Hibernate: " + he.getMessage());
			he.printStackTrace();
		} catch (Exception e) {
			// Capturamos cualquier otra excepción y la mostramos
			System.out.println("Error general: " + e.getMessage());
			e.printStackTrace();
		}

	}

	// Funciones-Métodos

	private static void insertarPaciente(SessionFactory sessionFactory, Paciente paciente) {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.save(paciente);
			session.getTransaction().commit();
			System.out.println("Paciente insertado exitosamente.");
		} catch (Exception e) {
			System.err.println("No se puede insertar el paciente. Error: " + e.getMessage());
		}
	}
	
	private static void actualizarPaciente(SessionFactory sessionFactory, Paciente paciente) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(paciente);
            session.getTransaction().commit();
            System.out.println("Paciente actualizado exitosamente.");
        } catch (Exception e) {
            System.err.println("No se puede actualizar el paciente. Error: " + e.getMessage());
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
	            // Borrar el paciente si existe
	            session.delete(paciente);
	            session.getTransaction().commit();
	            System.out.println("Paciente borrado exitosamente.");
	        } else {
	            System.out.println("No se encontró un paciente con el ID proporcionado.");
	        }
	    } catch (Exception e) {
	        System.err.println("No se puede borrar el paciente. Error: " + e.getMessage());
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
	            System.out.println("Paciente consultado exitosamente: ");
	            System.out.println(paciente);
	        } else {
	            System.out.println("Paciente no encontrado con ID: " + idPaciente);
	        }
	    } catch (Exception e) {
	        System.err.println("Error al consultar el paciente. Error: " + e.getMessage());
	    }
	    return paciente;
	}

	

	private static Date convertirAFechaSQL(String fechaStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fechaUtil = sdf.parse(fechaStr);
        return new Date(fechaUtil.getTime());
    }


}
