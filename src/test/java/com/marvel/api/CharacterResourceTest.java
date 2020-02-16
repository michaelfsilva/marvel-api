package com.marvel.api;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.DELETE;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.marvel.api.model.Character;
import com.marvel.api.repository.CharacterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CharacterResourceTest {
	@Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @MockBean // Mockito simular o repositorio e não altera os dados do banco, utiliza dados falsos
    private CharacterRepository characterRepository;
    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthorization("test", "marvel");
        }
    }
    
    @Before
    public void setup() {
    	Character character = new Character(1L, "Bucky", "Capitain's old friend", "Strength, Steel arm");
        BDDMockito.when(characterRepository.findOne(character.getId())).thenReturn(character);
    }
    
    /**
     * Test security roles
     */
    @Test
    public void listCharactersWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
        System.out.println(port);
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/characters/", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void listCharactersWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
        List<Character> characters = asList(new Character(1L, "Bucky", "Capitain's old friend", "Strength, Steel arm"),
                new Character(2L, "Peter", "Spider-man", "Smart and fast"));
        BDDMockito.when(characterRepository.findAll()).thenReturn(characters);
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/characters/", String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getCharactersByIdWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
        ResponseEntity<Character> response = restTemplate.getForEntity("/v1/characters/{id}", Character.class, 1L);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getCharactersByIdWhenUsernameAndPasswordAreCorrectAndCharacterDoesNotExistShouldReturnStatusCode404() {
        ResponseEntity<Character> response = restTemplate.getForEntity("/v1/characters/{id}", Character.class, -1);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
    
    @Test
    public void deleteWhenCharacterNotExistsShouldReturnStatusCode404() {
        BDDMockito.doNothing().when(characterRepository).delete(1L);
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/admin/characters/{id}", DELETE, null, String.class, -1);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(404);
    }
    
    @Test
    public void deleteWhenCharacterExistsShouldReturnStatusCode200() {
        BDDMockito.doNothing().when(characterRepository).delete(1L);
        ResponseEntity<String> exchange = restTemplate.exchange("/v1/characters/{id}", DELETE, null, String.class, 1L);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }
    
    @Test
    public void createWhenNameIsNullShouldReturnStatusCode400BadRequest() throws Exception{
    	Character character = new Character(3L, null, "test", "test");
        BDDMockito.when(characterRepository.save(character)).thenReturn(character);
        ResponseEntity<String> response = restTemplate.postForEntity("/v1/characters/", character, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(400);
        Assertions.assertThat(response.getBody()).contains("message", "Name attribute is mandatory");
    }
    
    @Test
    public void createShouldPersistDataAndReturnStatusCode201() throws Exception{
    	Character character = new Character(3L, "Tony Stark", "The man behind the armor", "Billionaire, genius, playboy, philanthropist");
    	BDDMockito.when(characterRepository.save(character)).thenReturn(character);
        ResponseEntity<String> response = restTemplate.postForEntity("/v1/characters/", character, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(201);
        Assertions.assertThat(response.getBody()).isNotNull();
    }
}
