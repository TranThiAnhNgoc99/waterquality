package com.ngoctta.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ngoctta.entity.Quality;


public interface QualityRepo extends JpaRepository<Quality, Long>{
	
	//@Param("substanceid")
	@Query("select  f FROM Quality f WHERE f.substance.substance_id = :substance_id AND f.location.location_id = :location_id ORDER BY f.time DESC")
	List<Quality> findTop10BySubstanceidAndLocationid( @Param("substance_id") Long substance_id,@Param("location_id") Long location_id);
	
	@Query("select  f FROM Quality f WHERE  f.location.location_id = :location_id ORDER BY f.time DESC, f.substance.substance_id DESC")
	List<Quality> findTop10ByLocationid(@Param("location_id") Long location_id);
	
}
