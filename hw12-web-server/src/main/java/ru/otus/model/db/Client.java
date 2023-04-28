package ru.otus.model.db;


import static java.util.Objects.nonNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_address")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.EAGER)
    private List<Phone> phone = new ArrayList<>();


    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        if (nonNull(address)) {
            this.address.setOwner(this);
        }
        if (nonNull(phone)) {
            this.phone.forEach(it -> it.setClient(this));
        }
    }

    public Client(String name, Address address, List<Phone> phone) {
        this.id = null;
        this.name = name;
        this.address = address;
        this.phone = phone;
        if (nonNull(address)) {
            this.address.setOwner(this);
        }
        if (nonNull(phone)) {
            this.phone.forEach(it -> it.setClient(this));
        }
    }

    @Override
    public Client clone() {
        return new Client(this.id,
                this.name,
                address.clone(),
                phone.stream()
                        .map(Phone::clone)
                        .toList());
    }

}
