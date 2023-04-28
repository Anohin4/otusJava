package ru.otus.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity

@Table(name = "phone")
public class Phone implements Cloneable {

    @Id
    @SequenceGenerator(name = "phone_gen", sequenceName = "phone_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_gen")
    @Column(name = "id")
    private Long phoneId;
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;


    public Phone(Long id, String number, Client client) {
        this.phoneId = id;
        this.number = number;
        this.client = client;
    }

    public Phone(Long id, String number) {
        this.phoneId = id;
        this.number = number;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + phoneId +
                ", number='" + number + '\'' +
                '}';
    }

    public Phone clone() {
        return new Phone(this.phoneId, this.number);
    }


}
