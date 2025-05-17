package com.vasquez.springboot.jpa.springboot_jpa_relationship;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		manyToOneFindByIdClient();
	}

	public void manyToOne() {
		Client client = new Client("John", "Doe");
		clientRepository.save(client);

		Invoice invoice = new Invoice("compras de oficina", 2000L);
		invoice.setClient(client);

		Invoice invoicedb = invoiceRepository.save(invoice);
		System.out.println(invoicedb);

	}

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
