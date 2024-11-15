package org.acme.hibernate.orm.panache;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import java.util.List;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;

@Entity
@Cacheable
public class Product extends PanacheEntity {

	public Long id;	
    public String name;
	public String description;   
	public Double price;
	public Integer quantity;

    public Product() {
    }

    public Product(Long id, String name, String description, Double price, Integer quantity) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}

    public static Uni<Product>  checkStockAvailability(Long id,Integer quantity){
    	
    	return find("id = :id and quantity > :quantity", Parameters.with("id", id).and("quantity", quantity)).firstResult();
    }


}
