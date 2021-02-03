package com.marvel.api.services;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.marvel.api.model.Character;

public interface CharacterService {

	List<Character> listAll();

	Character listById(Long id);

	List<Character> listByName(String name);
	
	Character add(@Valid Character character);

	Character update(@Valid Character character);
	
	Character partialUpdate(Long id, Map<String, Object> updates);
	
	void remove(Long id);

}
