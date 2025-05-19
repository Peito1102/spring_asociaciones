package com.vasquez.springboot.jpa.springboot_jpa_relationship;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.Address;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.Client;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.entities.Invoice;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.repositories.ClientRepository;
import com.vasquez.springboot.jpa.springboot_jpa_relationship.repositories.InvoiceRepository;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		removeAddress();
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

		client.setAddresses(Arrays.asList(address1, address2));

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
