package com.oopclass.breadapp.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cancellations")
public class Cancellation {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String fullName;
	private String reason;
        private String product;
	private LocalDate doc;

        public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
        
        public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public LocalDate getDoc() {
		return doc;
	}

	public void setDoc(LocalDate doc) {
		this.doc = doc;
	}
        
        
        
        
	@Override
	public String toString() {
		return "Cancellation [id =" +id+ ", fullName=" + fullName + ", reason=" + reason + ", product=" + product + ", doc=" + doc + "]";
	}

	
}
