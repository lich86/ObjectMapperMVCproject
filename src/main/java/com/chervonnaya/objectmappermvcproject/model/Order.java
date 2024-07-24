package com.chervonnaya.objectmappermvcproject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    Customer customer;

    @Embedded
    Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_product",
        joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    @JsonBackReference
    List<Product> products;

    @Column(name="sum")
    private BigDecimal sum;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;


    public enum Status {
        VIEWED,
        REJECTED,
        IN_PROGRESS,
        PENDING,
        READY_FOR_DISPATCH,
        DISPATCHED,
        DELIVERED,
        INVOICED,
        PARTIALLY_PAID,
        PAID,
        RETURNED,
        CLOSED
    }
}
