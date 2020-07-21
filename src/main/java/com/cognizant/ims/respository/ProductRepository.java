package com.cognizant.ims.respository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.ims.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

	Optional<Product> findByName(String name);
}
