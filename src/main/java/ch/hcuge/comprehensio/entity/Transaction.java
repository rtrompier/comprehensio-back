package ch.hcuge.comprehensio.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Date startDate;
    private Date endDate;
    private String comment;
    private String price;
    private State state;

    @ManyToOne
    @JoinColumn
    private User caller;

    @ManyToOne
    @JoinColumn
    private User receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Lang fromLang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Lang toLang;
    
    @OneToOne
    @JoinColumn
    private Note note;
    
}
