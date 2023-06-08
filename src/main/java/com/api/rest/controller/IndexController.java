package com.api.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.rest.model.Usuario;
import com.api.rest.repository.UsuarioRepository;

@CrossOrigin(origins = {"*"}) // Libera o acesso a todos (valor padrão *)
//@CrossOrigin(origins = {"http://localhost"}) // Define requisições de determinados servidor ou domínio
@RestController
@RequestMapping(value="/usuario")
public class IndexController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping(value="/hello", produces="application/json")
	public ResponseEntity<?> init() {
		return new ResponseEntity<String>("Hello World", HttpStatus.OK);
	}
	
	// Este endpoint, apenas o localhost:5000 pode acessar, nenhum outro
	@CrossOrigin(origins = {"localhost:5000"}) // Libera o acesso a este endpoint, apenas de quem vier do domínio atual
	@GetMapping(value="/test", produces="application/json")
	public ResponseEntity<Usuario> usuario() {
		Usuario user = new Usuario();
		user.setId(1L);
		user.setLogin("admin");
		user.setNome("Administrador");
		user.setSenha("1234");
		usuarioRepository.save(user);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}", produces="application/json")
	public ResponseEntity<Usuario> getUsuario(@PathVariable (value="id") Long id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
	}
	
	@GetMapping(value="/", produces="application/json")
	public ResponseEntity<List<Usuario>> getUsuarios() {
		List<Usuario> list = (List<Usuario>) usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(list, HttpStatus.OK);
	}

	@PostMapping(value="/", produces="application/json")
	public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
		
		for (int pos=0; pos<usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}

	@PutMapping(value="/", produces="application/json")
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {
		for (int pos=0; pos<usuario.getTelefones().size(); pos++) {
			usuario.getTelefones().get(pos).setUsuario(usuario);
		}
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}", produces="application/text")
	public String deleteUsuario(@PathVariable (value="id") Long id) {
		usuarioRepository.deleteById(id);
		return "success!";
	}

}
