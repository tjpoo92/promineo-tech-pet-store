package petstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import petstore.entity.PetStore;

public interface PetStoreDao extends JpaRepository<PetStore, Long> {
	
}
