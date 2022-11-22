package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "WithdrawalOrder")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "idBuyer")
    private Buyer buyer;

    @ManyToOne
    @JoinColumn(name = "idWarehouse")
    private Warehouse warehouse;

    public WithdrawalOrder(LocalDateTime date, Warehouse warehouse, Buyer buyer){
        this.date = date;
        this.warehouse = warehouse;
        this.buyer = buyer;
    }
}
