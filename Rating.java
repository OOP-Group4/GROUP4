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
public class Rating {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String first_name;
	private String last_name;
        private String middle_name;
        private String address;
	private LocalDate dor;
        private String service;
        private String late_customer;
        private String payment_time;
        private LocalDate created_at;
        private LocalDate time_updated;
        private String cancel;
        private String reason;
        private String rating;

	
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
        
        public String getMiddleName() {
		return middle_name;
	}

	public void setMiddleName(String middle_name) {
		this.middle_name = middle_name;
	}
        
        public String getService() {
		return service;
	}
        
	public LocalDate getDor() {
		return dor;
	}

	public void setDor(LocalDate dor) {
		this.dor = dor;
	}
        
        public String getCustomerRating() {
		return rating;
	}

	public void setCustomerRating(String rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", firstName=" + first_name + ", lastName=" + last_name + ", middleName=" + middle_name + ", address=" + address + ", dor=" + dor + ", service=" + service + ", lateCustomer=" + late_customer + ", cancel=" + cancel + ", reason=" + reason + ", paymentTime=" + payment_time + ", createdAt=" + created_at + ", timeUpdated=" + time_updated +"]";
	}

	
}
