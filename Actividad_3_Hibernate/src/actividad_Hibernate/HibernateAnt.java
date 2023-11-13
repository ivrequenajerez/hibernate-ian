/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package actividad_Hibernate;

import java.util.Iterator;
import java.util.List;

import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Usuario
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

			System.out.println("Número de pacientes: " + pacientes.size());

			for (Paciente paciente : pacientes) {
				System.out.println(paciente);
			}

			// Operaciones CRUD
			// Inserción
			insertarPaciente(instancia, new Paciente("NombrePaciente", "ApellidoPaciente", "123456789"));

			// Actualización
			actualizarPaciente(instancia, 1, "NuevoNombre", "NuevoApellido", "987654321");

			// Borrado
			borrarPaciente(instancia, 2);

			// Consulta
			consultarPacientes(instancia);

			/*
			 * //Creamos objetos. No pasamos un id lo recupera hibernate Customer c = new
			 * Customer("Antonio","Lopez",500);
			 * 
			 * //Transacciones. Insertamos/Guardamos objeto en la tabla
			 * session.beginTransaction(); session.save(c);
			 * 
			 * //Actualizamos un objeto con update. Tenemos que incluir id Customer c2 = new
			 * Customer(2,"UPDATE","UPDATE",300); session.update(c2);
			 * 
			 * //Eliminamos el objecto c3. Elimina en base al id Customer c3 = new
			 * Customer("Pepe","Perez",300); session.save(c3); //Podemos borrar solo con el
			 * id del objeto (setID) session.delete(c3);
			 * 
			 * session.getTransaction().commit(); //También permite rollback()
			 * System.out.println("Registro insertado/actualizado/borrado");
			 * 
			 * //------ HQL -------- //Consulta 1 String hql = "FROM Customer";
			 * Query<Customer> consulta = session.createQuery(hql,Customer.class);
			 * List<Customer> results = consulta.getResultList(); for(Customer cust :
			 * results){ System.out.println(cust); }
			 * 
			 * //Consulta 2 //String hql = "FROM Customer WHERE id=:id"; Query<Customer>
			 * consulta2 = session.createQuery("FROM Customer WHERE id=:id",Customer.class);
			 * consulta2.setParameter("id",1); Customer customer =
			 * consulta2.getSingleResult(); List<Customer> results2 =
			 * consulta2.getResultList(); System.out.println(customer); //try .. catch
			 * (NoResultException e)
			 */

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
	
	// Métodos
	
	private static void insertarPaciente(SessionFactory sessionFactory, Paciente paciente) throws IllegalStateException, SystemException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = (Transaction) session.beginTransaction();
            session.save(paciente);
            transaction.commit();
            System.out.println("Paciente insertado: " + paciente);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void actualizarPaciente(SessionFactory sessionFactory, int pacienteId, String nuevoNombre, String nuevoApellido, String nuevoTelefono) throws IllegalStateException, SystemException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = (Transaction) session.beginTransaction();
            Paciente paciente = session.get(Paciente.class, pacienteId);

            if (paciente != null) {
                paciente.setNombre(nuevoNombre);
                paciente.setApellidos(nuevoApellido);
                paciente.setTeléfono(nuevoTelefono);
                session.update(paciente);
                transaction.commit();
                System.out.println("Paciente actualizado: " + paciente);
            } else {
                System.out.println("No se encontró el paciente con ID: " + pacienteId);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void borrarPaciente(SessionFactory sessionFactory, int pacienteId) throws IllegalStateException, SystemException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = (Transaction) session.beginTransaction();
            Paciente paciente = session.get(Paciente.class, pacienteId);

            if (paciente != null) {
                session.delete(paciente);
                transaction.commit();
                System.out.println("Paciente borrado: " + paciente);
            } else {
                System.out.println("No se encontró el paciente con ID: " + pacienteId);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    private static void consultarPacientes(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Query<Paciente> query = session.createQuery("FROM Paciente", Paciente.class);
            List<Paciente> pacientes = query.getResultList();

            System.out.println("Listado de Pacientes:");
            for (Paciente paciente : pacientes) {
                System.out.println(paciente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
