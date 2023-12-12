package petstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import petstore.entity.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
