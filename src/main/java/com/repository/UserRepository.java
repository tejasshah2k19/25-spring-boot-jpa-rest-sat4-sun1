package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.UserEntity;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{

	Optional<UserEntity> findByEmailAndPassword(String email,String password);
	Optional<UserEntity> findByToken(String token);
	Optional<UserEntity> findByEmail(String email);
	
}
