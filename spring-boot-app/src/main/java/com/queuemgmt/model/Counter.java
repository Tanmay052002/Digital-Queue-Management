package com.queuemgmt.model;

import jakarta.persistence.*;

// COUNTER table - belongs to an office, handles services
@Entity
@Table(name = "queue_counter") // "counter" alone clashes with H2 reserved word
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "office_id")
    private Long officeId;

    @Column(name = "counter_number")
    private int counterNumber;

    // simple hardcoded capacity, in a real system this would come from a schedule table
    @Column(name = "max_slots")
    private int maxSlots = 5;

    public Counter() {
    }

    public Counter(Long officeId, int counterNumber) {
        this.officeId = officeId;
        this.counterNumber = counterNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public int getCounterNumber() {
        return counterNumber;
    }

    public void setCounterNumber(int counterNumber) {
        this.counterNumber = counterNumber;
    }

    public int getMaxSlots() {
        return maxSlots;
    }

    public void setMaxSlots(int maxSlots) {
        this.maxSlots = maxSlots;
    }
}
