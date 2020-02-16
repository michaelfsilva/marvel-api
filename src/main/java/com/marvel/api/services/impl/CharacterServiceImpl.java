package com.marvel.api.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marvel.api.model.Character;
import com.marvel.api.repository.CharacterRepository;
import com.marvel.api.services.CharacterService;

@Service
public class CharacterServiceImpl implements CharacterService {

	@Autowired
	private CharacterRepository characterRepository;
	
	@Override
	public List<Character> listAll() {
		return characterRepository.findAll();
	}

	@Override
	public Character listById(Long id) {
//		return characterRepository.findById(id).get();
		return characterRepository.findOne(id);
	}
	
	@Override
	public Character listByName(String name) {
		return characterRepository.findByName(name);
	}

	@Override
	public Character add(@Valid Character character) {
		return characterRepository.save(character);
	}

	@Override
	public Character update(@Valid Character character) {
		return characterRepository.save(character);
	}
	
	@Override
	public Character partialUpdate(Long id, Map<String, Object> updates) {
		Character character = listById(id);
		
		updates.forEach((key, value) -> {
			switch (key) {
			case "name":
				character.setName(value.toString());
				break;

			case "description":
				character.setDescription(value.toString());
				break;

			case "superPowers":
				character.setSuperPowers(value.toString());
				break;
			}
		});
		
		return characterRepository.save(character);
	}

	@Override
	public void remove(Long id) {
//		characterRepository.deleteById(id);
		characterRepository.delete(id);
	}

}
