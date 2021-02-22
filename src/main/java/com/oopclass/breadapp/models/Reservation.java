package com.oopclass.breadapp.models;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Entity
@Table(name = "reservations")
public class Reservation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String first_name;
	private String last_name;
        private String email;
        private String address;
	private LocalDate dor;
        private String contact;
        private LocalDate created_at;
        private LocalDate updated_at;
        
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
        
        public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
        
        public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public LocalDate getDor() {
		return dor;
	}

	public void setDor(LocalDate dor) {
		this.dor = dor;
	}
        
        public String getContactNumber() {
		return contact;
	}

	public void setContactNumber(String contact) {
		this.contact = contact;
	}
        
        public LocalDate getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(LocalDate created_at) {
		this.created_at = created_at;
	}
        
        public LocalDate getUpdatedAt() {
		return updated_at;
	}

	public void setUpdatedAt(LocalDate updated_at) {
		this.updated_at = updated_at;
	}
       
        
	

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", firstName=" + first_name + ", lastName=" + last_name + ", email=" + email + ", address=" + address + ", dor=" + dor + ", contact=" + contact + ", createdAt=" + created_at + ", timeUpdated=" + updated_at +"]";
	}

	
}
