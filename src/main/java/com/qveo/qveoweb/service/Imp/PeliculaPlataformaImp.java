package com.qveo.qveoweb.service.Imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qveo.qveoweb.dao.PeliculaPlataformaDao;
import com.qveo.qveoweb.model.Pelicula;
import com.qveo.qveoweb.model.PeliculaPlataforma;
import com.qveo.qveoweb.service.PeliculaPlataformaService;

@Service
public class PeliculaPlataformaImp implements PeliculaPlataformaService{
	
	@Autowired
	PeliculaPlataformaDao peliculaPlataformaDao;
	
	@Override
	public List<PeliculaPlataforma> getAllPeliculasPlataformas() {
		 
		return peliculaPlataformaDao.findAll();
	}

	@Override
	public List<PeliculaPlataforma> obtenerPelicula(Pelicula pelicula) {
		
		return peliculaPlataformaDao.findByPelicula(pelicula);
	}

	@Override
	public List<PeliculaPlataforma> borrarPelicula(Pelicula pelicula) {
		
		return peliculaPlataformaDao.deleteByPelicula(pelicula);
	}

}
