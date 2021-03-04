package com.oopclass.breadapp.models;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "installations")
public class Installation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)

    private long id;
    private LocalDate created_at;
    private LocalDate updated_at;
    private String services;
    private String mop;
    private String down_payment;
    private LocalDate dor;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getMOP() {
        return mop;
    }

    public void setMOP(String mop) {
        this.mop = mop;
    }

    public String getDownPayment() {
        return down_payment;
    }

    public void setDownPayment(String down_payment) {
        this.down_payment = down_payment;
    }

    public LocalDate getDOR() {
        return dor;
    }

    public void setDOR(LocalDate dor) {
        this.dor = dor;
    }
   
    @Override
    public String toString() {
        return "Installations [id=" + id + ", created=" + created_at + ", timeUpdate=" + updated_at + ", services=" + services + ", Mode of Payment=" + mop + ", Down Payment=" + down_payment + ", dor=" + dor + "]";
    }

}
