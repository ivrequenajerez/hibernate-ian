# Proyecto Hibernate – Sistema de Citas Médicas

Este proyecto implementa un sistema de gestión de citas médicas utilizando **Java** y **Hibernate (JPA)** como capa de persistencia.  
Forma parte de mi experiencia práctica en modelado de bases de datos y uso de ORM en Java, aplicando relaciones entre entidades y operaciones CRUD.

---

## 🚀 Tecnologías utilizadas
- **Java**  
- **Hibernate / JPA**  
- **PostgreSQL** (configurable, adaptable a Oracle)  
- **Maven**  

---

## 📂 Estructura del proyecto
- `actividad_Hibernate/` → Paquete principal con las entidades y lógica CRUD.  
  - `Doctor.java`, `Paciente.java`, `Cita.java`, `Tratamiento.java`, `Usuario.java`, etc.  
- `persistence.xml` → Configuración JPA.  
- `hibernate.cfg.xml` → Configuración de Hibernate.  

Las entidades incluyen ejemplos de relaciones:
- `@OneToMany` y `@ManyToOne` (Doctor ↔ Paciente, Paciente ↔ Cita, etc.).  
- Validaciones con anotaciones JPA.  
