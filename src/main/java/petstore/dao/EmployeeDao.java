package petstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import petstore.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
