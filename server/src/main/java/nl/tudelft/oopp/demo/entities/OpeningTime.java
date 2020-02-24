package nl.tudelft.oopp.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "openingTimes")
public class OpeningTime {

    @Id
    @Column(name = "facilityName")
    private String facility;

}
