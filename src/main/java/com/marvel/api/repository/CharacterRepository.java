package com.marvel.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marvel.api.model.Character;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
	/**
	 * Find character by Name
	 * 
	 * @param name
	 * @return character
	 */
	List<Character> findByNameIgnoreCaseContaining(String name);
}
