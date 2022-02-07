package com.hkmc.sample.entity.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("M")
@Getter
public class Movie extends Item{

    private String director;
    private String actor;
}
