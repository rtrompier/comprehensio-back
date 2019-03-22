package ch.hcuge.comprehensio.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Note {

	@Id
	private String id;

	private boolean criterion1;
	private boolean criterion2;
	private boolean criterion3;
	private boolean criterion4;
	private boolean criterion5;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Transaction transaction;
}
