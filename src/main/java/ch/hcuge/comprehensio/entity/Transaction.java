package ch.hcuge.comprehensio.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Date startDate;
    private Date endDate;
    private String note;
    private String comment;
    private String price;

    @ManyToOne
    @JoinColumn
    private User caller;

    @ManyToOne
    @JoinColumn
    private User receiver;

    @ManyToOne
    @JoinColumn
    private Lang fromLang;

    @ManyToOne
    @JoinColumn
    private Lang toLang;
}
