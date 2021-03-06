package com.qveo.qveoweb.model;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
public class Serie {
    private Integer id;
    private String titulo;
    private Date fechaInicio;
    private String sinopsis;
    private Integer temporadas;
    private Integer capitulos;
    private Collection<Actor> actores;
    private Collection<Director> directores;
    private Collection<Genero> generos;
    private Pais pais;
    private String poster;
    private Collection<Plataforma> plataformas;
    private Collection<Usuario> usuarios;

    public Serie() {
	}
    
	public Serie(String titulo, Date fechaInicio, String sinopsis, Integer temporadas, Integer capitulos,
			Collection<Director> directores, Collection<Genero> generos, Pais pais, Collection<Plataforma> plataformas,
			String poster) {
		this.titulo = titulo;
		this.fechaInicio = fechaInicio;
		this.sinopsis = sinopsis;
		this.temporadas = temporadas;
		this.capitulos = capitulos;
		this.directores = directores;
		this.generos = generos;
		this.pais = pais;
		this.plataformas = plataformas;
		this.poster = poster;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "TITULO")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Basic
    @Column(name = "FECHA_INICIO")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.DATE)
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

   
	public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Basic
    @Column(name = "SINOPSIS")
    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Basic
    @Column(name = "TEMPORADAS")
    public Integer getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(Integer temporadas) {
        this.temporadas = temporadas;
    }

    @Basic
    @Column(name = "CAPITULOS")
    public Integer getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(Integer capitulos) {
        this.capitulos = capitulos;
    }

    @Basic
    @Column(name = "POSTER")
    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @ManyToMany
    @JoinTable(
            name = "actor_serie",
            joinColumns = @JoinColumn(name = "id_serie", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_actor", nullable = false)
    )
    @JsonIgnore
    public Collection<Actor> getActores() {
        return actores;
    }

    public void setActores(Collection<Actor> actores) {
        this.actores = actores;
    }

    @ManyToMany
    @JoinTable(
            name = "director_serie",
            joinColumns = @JoinColumn(name = "id_serie", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_director", nullable = false)
    )
    @JsonIgnore
    public Collection<Director> getDirectores() {
        return directores;
    }

    public void setDirectores(Collection<Director> directores) {
        this.directores = directores;
    }

    @ManyToMany
    @JoinTable(
            name = "genero_serie",
            joinColumns = @JoinColumn(name = "id_serie", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_genero", nullable = false)
    )
    @JsonIgnore
    public Collection<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(Collection<Genero> generos) {
        this.generos = generos;
    }

    @ManyToOne
    @JoinColumn(name = "ID_PAIS", referencedColumnName = "ID", nullable = false)
    @JsonIgnore
    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }


    @ManyToMany
    @JoinTable(
            name = "serie_plataforma",
            joinColumns = @JoinColumn(name = "id_serie", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_plataforma", nullable = false)
    )
    @JsonIgnore
    public Collection<Plataforma> getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(Collection<Plataforma> plataformas) {
        this.plataformas = plataformas;
    }
    
    @ManyToMany(mappedBy = "series")
    @JsonIgnore
    public Collection<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Collection<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String plataformasConcatenadas(){
        return plataformas.stream().map(Plataforma::getNombre).collect(Collectors.joining(", "));
    }
}
