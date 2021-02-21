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
@Table(name = "products")
public class Cancel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	private String item_name;
        private String size;
	private LocalDate dor;
        private String price;
        private String other_desc;
        private LocalDate created_at;
        private LocalDate time_updated;
    
        

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemName() {
		return item_name;
	}

	public void setItemName(String item_name) {
		this.item_name = item_name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
        
        public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
        
	
	public LocalDate getDor() {
		return dor;
	}

	public void setDor(LocalDate dor) {
		this.dor = dor;
	}
        
        public String getOtherDesc() {
		return other_desc;
	}

	public void setOtherDesc(String other_desc) {
		this.other_desc = other_desc;
	}
        
        public LocalDate getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(LocalDate created_at) {
		this.created_at = created_at;
	}
        
        public LocalDate getTimeUpdated() {
		return time_updated;
	}

	public void setTimeUpdated(LocalDate time_updated) {
		this.time_updated = time_updated;
	}
        
	

	@Override
	public String toString() {
		return "Cancel [id=" + id + ", itemName=" + item_name + ", size=" + size + ", price=" + price + ", dor=" + dor + ", otherDesc=" + other_desc + ", createdAt=" + created_at + ", timeUpdated=" + time_updated +"]";
	}

	
}
