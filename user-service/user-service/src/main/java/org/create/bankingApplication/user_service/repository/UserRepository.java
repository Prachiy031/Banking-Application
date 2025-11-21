package org.create.bankingApplication.user_service.repository;

import java.util.Optional;

import org.create.bankingApplication.user_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
  Repository helps to do CRUD operations with default methods 
  available in spring data jpa which contains queries for 
  doing CRUD operations
  
  can also declare customized methods to access data by different columns/fields other than present primary key
  
  Mention table name (Entity name) from which you have to do crud and data type of primary key in table
 */
public interface UserRepository extends JpaRepository<User, Long>{
	
	/*
	 * find user using authentication id otherwise empty {Optional.empty() if User is null(not found)}
	 */
	Optional<User> findUserByAuthenticationId(String authenticationId);

}
