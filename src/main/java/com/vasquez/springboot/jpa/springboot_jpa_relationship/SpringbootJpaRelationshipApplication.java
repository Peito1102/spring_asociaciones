package com.vasquez.springboot.jpa.springboot_jpa_relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.ClientDetails;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.Course;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.Student;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.repositories.ClientDetailsRepository;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.repositories.CourseRepository;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.repositories.StudentRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToManyBiderccionalRemove();
	}

	@Transactional
	public void manyToManyBiderccionalRemove() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Course de java master", "Andres");
		Course course2 = new Course("Course de Spring Boot", "Andres");

		student1.addCourse(course1).addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student1,student2));

		System.out.println(student1);
		System.out.println(student2);	

		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(3L);
		studentOptionalDb.ifPresent(s -> {
			Optional<Course> courseOptionalDb = courseRepository.findOneWithStudents(3L);
			courseOptionalDb.ifPresent(c -> {
				s.removeCourse(c);
				studentRepository.save(s);
				System.out.println(s);
			});
		});
	}

	@Transactional
	public void manyToManyBiderccional() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Course de java master", "Andres");
		Course course2 = new Course("Course de Spring Boot", "Andres");

		student1.addCourse(course1).addCourse(course2);
		student2.addCourse(course2);

		studentRepository.saveAll(List.of(student1,student2));

		System.out.println(student1);
		System.out.println(student2);	
	}

	@Transactional
	public void manyToManyRemoveFind() {
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1,student2));

		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourses(1L);
		studentOptionalDb.ifPresent(s -> {
			Optional<Course> courseOptionalDb = courseRepository.findById(2L);
			courseOptionalDb.ifPresent(c -> {
				s.getCourses().remove(c);
				Student newStudent = studentRepository.save(s);
				System.out.println(newStudent);
			});
		});

	}

	@Transactional
	public void manyToManyFind() {
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1,student2));

		System.out.println(student1);
		System.out.println(student2);	
	}

	@Transactional
	public void manyToMany() {
		Student student1 = new Student("Jano", "Pura");
		Student student2 = new Student("Erba", "Doe");

		Course course1 = new Course("Course de java master", "Andres");
		Course course2 = new Course("Course de Spring Boot", "Andres");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(List.of(student1,student2));

		System.out.println(student1);
		System.out.println(student2);	
	}

	@Transactional
	public void oneToOneBidireccionalFindById() {
		Optional<Client> clientOptional = clientRepository.findOne(1L);
		clientOptional.ifPresent(c -> {
			ClientDetails clientDetails = new ClientDetails(true, 5000);

			c.setClientDetails(clientDetails);
			Client nc = clientRepository.save(c);
		
			System.out.println(nc);
		});
		

	}

	@Transactional
	public void oneToOneBidireccional() {
		Client client = new Client("Erba", "Pura");

		ClientDetails clientDetails = new ClientDetails(true, 5000);

		client.setClientDetails(clientDetails);
		clientRepository.save(client);
		
		System.out.println(client);

	}

	@Transactional
	public void oneToOneFindById() {
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> clientOptional = clientRepository.findOne(2L); //new Client("Erba", "Pura");
		clientOptional.ifPresent(c -> {
			c.setClientDetails(clientDetails);
			clientRepository.save(c);
		
			System.out.println(c);
		});
		
	}

	@Transactional
	public void oneToOne() {
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Erba", "Pura");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);
		
		System.out.println(clientDetails);

	}

	@Transactional
	public void removeInvoiceBidireccionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOne(1L);
		optionalClient.ifPresent(c -> {
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de la oficina", 8000L);

			c.addInvoice(invoice1).addInvoice(invoice2);

			clientRepository.save(c);

			System.out.println(c);
		});
		
		Optional<Client> optionalClientBD = clientRepository.findOne(1L);
		optionalClientBD.ifPresent(c -> {
			Optional<Invoice> optionalInvoice = invoiceRepository.findById(2L);
			optionalInvoice.ifPresent(i -> {
				c.removeInvoice(i);
				clientRepository.save(c);
				System.out.println(c);
			});
		});
		
	}

	@Transactional
	public void oneToManyBidireccionalFindById() {
		Optional<Client> optionalClient = clientRepository.findOneWithInvoices(1L);
		optionalClient.ifPresent(c -> {
			Invoice invoice1 = new Invoice("compras de la casa", 5000L);
			Invoice invoice2 = new Invoice("compras de la oficina", 8000L);

			c.addInvoice(invoice1).addInvoice(invoice2);

			clientRepository.save(c);

			System.out.println(c);
		});
		
		
	}

	@Transactional
	public void oneToManyBidireccional() {
		Client client = new Client("Lizu","Loayza");
		
		Invoice invoice1 = new Invoice("compras de la casa", 5000L);
		Invoice invoice2 = new Invoice("compras de la oficina", 8000L);

		client.addInvoice(invoice1).addInvoice(invoice2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void removeAddressFindById() {
		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9876);

		Set<Address> addresses = new HashSet<>();
		addresses.add(address1);
		addresses.add(address2);
		client.setAddresses(addresses);

		Client clienteDB = clientRepository.save(client);

		System.out.println(clienteDB);

		Optional<Client> optionalClient2 = clientRepository.findOne(2L);
		optionalClient2.ifPresent(c -> {

			clientRepository.save(c);
			System.out.println(c);
		});
		});

	}

	@Transactional
	public void removeAddress() {
		Client client = new Client("Lizu","Loayza");
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9876);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);

		Optional<Client> optionalClient = clientRepository.findById(3L);
		optionalClient.ifPresent(c -> {
			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);
		});
	}

	@Transactional
	public void oneToManyFindById() {

		Optional<Client> optionalClient = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9876);

		Set<Address> addresses = new HashSet<>();
		addresses.add(address1);
		addresses.add(address2);
		client.setAddresses(addresses);

		Client clienteDB = clientRepository.save(client);

		System.out.println(clienteDB);
		});
		
	}
	
	@Transactional
	public void oneToMany() {
		Client client = new Client("Lizu","Loayza");
		Address address1 = new Address("El verjel", 1234);
		Address address2 = new Address("Vasco de Gama", 9876);

		client.getAddresses().add(address1);
		client.getAddresses().add(address2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void manyToOne() {
		Client client = new Client("John", "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);

		Invoice invoicedb = invoiceRepository.save(invoice);
		System.out.println(invoicedb);

	}

	@Transactional
	public void manyToOneFindByIdClient() {
		Optional<Client> optionalClient = clientRepository.findById(1L);
		if (optionalClient.isPresent()) {
			Client client = optionalClient.orElseThrow();
			Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);

		Invoice invoicedb = invoiceRepository.save(invoice);
		System.out.println(invoicedb);
		}

	}

}
