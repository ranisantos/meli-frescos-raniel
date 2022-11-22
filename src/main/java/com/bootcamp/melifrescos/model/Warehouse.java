package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 120, nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "idRepresentative")
    @JsonIgnoreProperties("warehouse")
    private Representative representative;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnoreProperties("warehouse")
    private List<Sector> sectors;


    @OneToMany(mappedBy = "warehouse")
    @JsonIgnoreProperties("warehouse")
    private List<WithdrawalOrder> withdrawalOrder;
}
