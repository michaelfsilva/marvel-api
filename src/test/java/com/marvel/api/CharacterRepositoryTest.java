package com.marvel.api;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.marvel.api.model.Character;
import com.marvel.api.repository.CharacterRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CharacterRepositoryTest {
	@Autowired
    private CharacterRepository characterRepository;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createShouldPersistData() {
        Character character = new Character("Bucky", "Capitain's old friend", "Strength, Steel arm");
        this.characterRepository.save(character);
        assertThat(character.getId()).isNotNull();
        assertThat(character.getName()).isEqualTo("Bucky");
        assertThat(character.getDescription()).isEqualTo("Capitain's old friend");
        assertThat(character.getSuperPowers()).isEqualTo("Strength, Steel arm");
    }

    @Test
    public void deleteShouldRemoveData() {
    	Character character = new Character("Bucky", "Capitain's old friend", "Strength, Steel arm");
        this.characterRepository.save(character);
        characterRepository.delete(character);
        assertThat(characterRepository.findOne(character.getId())).isNull();
    }

    @Test
    public void updateShouldChangeAndPersistData() {
    	Character character = new Character("Bucky", "Capitain's old friend", "Strength, Steel arm");
        this.characterRepository.save(character);
        character.setName("Bucky222");
        character.setDescription("Capitain's best friend");
        this.characterRepository.save(character);
        character = this.characterRepository.findOne(character.getId());
        assertThat(character.getName()).isEqualTo("Bucky222");
        assertThat(character.getDescription()).isEqualTo("Capitain's best friend");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
    	Character character = new Character("Bucky", "Capitain's old friend", "Strength, Steel arm");
        this.characterRepository.save(character);
        Character returnCharacter = characterRepository.findByNameIgnoreCaseContaining("bucky");
        assertThat(returnCharacter.getName()).isEqualTo("Bucky");
    }

    @Test
    public void createWhenNameIsNullShouldThrowConstraintViolationException() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("Name attribute is mandatory");
        this.characterRepository.save(new Character());
    }
}
