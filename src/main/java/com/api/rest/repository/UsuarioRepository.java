package com.api.rest.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.rest.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
}
