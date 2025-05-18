package com.vasquez.springboot.jpa.springboot_jpa_relationship;

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
		OneToMany();
	}

	@Transactional
	public void OneToMany() {
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
