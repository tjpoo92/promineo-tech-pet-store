package petstore.service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import petstore.controller.model.PetStoreCustomer;
import petstore.controller.model.PetStoreData;
import petstore.controller.model.PetStoreEmployee;
import petstore.dao.CustomerDao;
import petstore.dao.EmployeeDao;
import petstore.dao.PetStoreDao;
import petstore.entity.Customer;
import petstore.entity.Employee;
import petstore.entity.PetStore;

@Service
public class PetStoreService {

	@Autowired
	private PetStoreDao petStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;

	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);

		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));

	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());

	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		PetStore petStore;

		if (Objects.isNull(petStoreId)) {
			petStore = new PetStore();
		} else {
			petStore = findPetStoreById(petStoreId);
		}
		return petStore;
	}

	private PetStore findPetStoreById(Long petStoreId) {

		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet Store with ID:" + petStoreId + " was not found."));
	}

	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);

		copyEmployeeFields(employee, petStoreEmployee);
		employee.setPetStore(petStore);
		Set<Employee> employees = new HashSet<Employee>();
		employees.add(employee);
		petStore.setEmployees(employees);
		Employee dbEmployee = employeeDao.save(employee);

		return new PetStoreEmployee(dbEmployee);
	}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
		employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());

	}

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		Employee employee;

		if (Objects.isNull(employeeId)) {
			employee = new Employee();
		} else {
			employee = findEmployeeById(petStoreId, employeeId);
		}
		return employee;
	}

	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID: " + employeeId + " was not found."));
		if (employee.getPetStore().getPetStoreId() != petStoreId) {
			throw new IllegalArgumentException();
		}
		return employee;
	}

	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);

		copyCustomerFields(customer, petStoreCustomer);
		Set<PetStore> petStores = new HashSet<PetStore>();
		petStores.add(petStore);
		customer.setPetStores(petStores);
		Set<Customer> customers = new HashSet<Customer>();
		customers.add(customer);
		petStore.setCustomers(customers);
		Customer dbCustomer = customerDao.save(customer);
		
		return new PetStoreCustomer(dbCustomer);

	}

	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
		customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
		customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
	}

	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
		Customer customer;

		if (Objects.isNull(customerId)) {
			customer = new Customer();
		} else {
			customer = findCustomerById(petStoreId, customerId);
		}
		return customer;
	}

	private Customer findCustomerById(Long petStoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID: " + customerId + " was not found."));
		for(PetStore petStore : customer.getPetStores()) {
			if (petStore.getPetStoreId() != petStoreId) {
				throw new IllegalArgumentException();
			}
		}

		return null;
	}

}
