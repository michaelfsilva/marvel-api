package com.marvel.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Character {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Name attribute is mandatory")
	private String name;
	private String description;
	private String superPowers;
//	private List<String> movies;
//	private List<String> series;
	
	public Character() {}

	public Character(String name, String description, String superPowers) {
		super();
		this.name = name;
		this.description = description;
		this.superPowers = superPowers;
	}

	public Character(Long id, String name, String description, String superPowers) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.superPowers = superPowers;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSuperPowers() {
		return superPowers;
	}
	
	public void setSuperPowers(String superPowers) {
		this.superPowers = superPowers;
	}
	
//	public List<String> getMovies() {
//		return movies;
//	}
//	
//	public void setMovies(List<String> movies) {
//		this.movies = movies;
//	}
//	
//	public List<String> getSeries() {
//		return series;
//	}
//	
//	public void setSeries(List<String> series) {
//		this.series = series;
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Character other = (Character) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
//	@Override
//	public String toString() {
//		return "Character [id=" + id + ", name=" + name + ", description=" + description + ", modified=" + modified
//				+ ", superPowers=" + superPowers + ", movies=" + movies + ", series=" + series + "]";
//	}
}
