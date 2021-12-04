package com.ngoctta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quality")
public class Quality  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quality_id")
	private Long quality_id;
	
	@Column(name = "data")
	private Float data;
	
	@Column(name = "time")
	private Long time;
	
	@ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Location location;
	
	@ManyToOne
    @JoinColumn(name = "substance_id", referencedColumnName = "substance_id")
	@EqualsAndHashCode.Exclude
    @ToString.Exclude
	private Substance substance;
}
