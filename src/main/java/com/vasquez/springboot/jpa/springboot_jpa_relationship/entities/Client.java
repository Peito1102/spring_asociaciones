package com.vasquez.springboot.jpa.springboot_jpa_relationship.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //cascada es para que se cree, elimine, actualice en conjunto con los correos
    //@JoinColumn(name = "client_id")
    @JoinTable(name = "tbl_clientes_to_direcciones",
        joinColumns = @JoinColumn(name="id_cliente"),
        inverseJoinColumns = @JoinColumn(name="id_direcciones"),
        uniqueConstraints = @UniqueConstraint(columnNames = "id_direcciones"))
    private Set<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private Set<Invoice> invoices;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    ClientDetails clientDetails;

    public Client(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
    }
    public Client() {
        this.addresses = new HashSet<>();
        this.invoices = new HashSet<>();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public Set<Address> getAddresses() {
        return addresses;
    }
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
    public Set<Invoice> getInvoices() {
        return invoices;
    }
    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
    public ClientDetails getClientDetails() {
        return clientDetails;
    }
    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
        clientDetails.setClient(this);
    }

    public void removeClientDetails(ClientDetails clientDetails) {
        clientDetails.setClient(null);
        this.clientDetails = null;
    }

    public Client addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setClient(this);
        return this;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", lastname=" + lastname + ", addresses=" + addresses
                + ", invoices=" + invoices + ", detalles cliente=" + clientDetails + "}";
    }
    
    public void removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
	    invoice.setClient(null);
    }
}
