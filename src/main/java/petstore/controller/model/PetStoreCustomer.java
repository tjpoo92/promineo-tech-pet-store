package petstore.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import petstore.entity.Customer;

@Data
@NoArgsConstructor
public class PetStoreCustomer {
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
	public PetStoreCustomer(Customer customer) {
		this.customerId = customer.getCustomerId();
		this.customerFirstName = customer.getCustomerFirstName();
		this.customerLastName = customer.getCustomerLastName();
		this.customerEmail = customer.getCustomerEmail();
	}

}
