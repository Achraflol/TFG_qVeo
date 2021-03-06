package com.qveo.qveoweb.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Collection;

@Entity
public class Actor {
	private Integer id;
	private String nombre;
	private String sexo;
	private Pais pais;
	private Collection<Pelicula> peliculas;
	private Collection<Serie> series;
	private String foto;

	public Actor() {
	}

	public Actor(String nombre, String sexo, Pais pais, Collection<Pelicula> peliculas, Collection<Serie> series,
			String foto) {
		this.nombre = nombre;
		this.sexo = sexo;
		this.pais = pais;
		this.peliculas = peliculas;
		this.series = series;
		this.foto = foto;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "NOMBRE")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Basic
	@Column(name = "SEXO")
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@ManyToOne
	@JoinColumn(name = "ID_PAIS", referencedColumnName = "ID", nullable = false)
	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@ManyToMany
	@JoinTable(name = "Actor_Pelicula", joinColumns = @JoinColumn(name = "id_actor", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_pelicula", nullable = false))
	public Collection<Pelicula> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(Collection<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}

	@ManyToMany
	@JoinTable(name = "Actor_Serie", joinColumns = @JoinColumn(name = "id_actor", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_serie", nullable = false))
	public Collection<Serie> getSeries() {
		return series;
	}

	public void setSeries(Collection<Serie> series) {
		this.series = series;
	}

	@Basic
	@Column(name = "FOTO")
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

}
