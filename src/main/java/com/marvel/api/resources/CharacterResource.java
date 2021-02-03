package com.marvel.api.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marvel.api.model.Character;
import com.marvel.api.responses.Response;
import com.marvel.api.services.CharacterService;

@RestController
@RequestMapping("/v1/characters")
public class CharacterResource {
	@Autowired
	private CharacterService characterService;
	
	@GetMapping
	public ResponseEntity<Response<List<Character>>> listAll() {
		List<Character> characters = characterService.listAll();
		
		if (characters.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return ResponseEntity.ok(new Response<List<Character>>(characters));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Response<Character>> getById(@PathVariable Long id) {
		Character character = this.characterService.listById(id);
		
		if (character == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(new Response<Character>(character));
	}
	
	@GetMapping("/findByName/{name}")
	public ResponseEntity<Response<List<Character>>> getByName(@PathVariable String name) {
		List<Character> character = this.characterService.listByName(name);
		
		if (character == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(new Response<List<Character>>(character));
	}
		
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Response<Character>> add(@Valid @RequestBody Character character, BindingResult result) {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Character>(erros));
		}
		
		return new ResponseEntity<>(new Response<Character>(this.characterService.add(character)), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<Character>> update(@PathVariable Long id, @Valid @RequestBody Character character, BindingResult result) {
		if (result.hasErrors()) {
			List<String> erros = new ArrayList<String>();
			result.getAllErrors().forEach(erro -> erros.add(erro.getDefaultMessage()));
			return ResponseEntity.badRequest().body(new Response<Character>(erros));
		}
		
		if (this.characterService.listById(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} 
		
		character.setId(id);
		return ResponseEntity.ok(new Response<Character>(this.characterService.update(character)));
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Response<Character>> patchUpdate(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
		if (this.characterService.listById(id) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return ResponseEntity.ok(new Response<Character>(this.characterService.partialUpdate(id, updates)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<Integer>> remove(@PathVariable Long id) {
		if (this.characterService.listById(id) == null) {
			return new ResponseEntity<>(new Response<Integer>("No character found for id: " + id), HttpStatus.NOT_FOUND);
		} 
		
		characterService.remove(id);
		return ResponseEntity.ok(new Response<Integer>("Character deleted!"));
	}
	
}
