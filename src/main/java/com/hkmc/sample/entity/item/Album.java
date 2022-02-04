package com.hkmc.sample.entity.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("A") //안넣는 경우 기본 값이 나옴
public class Album extends Item{

    private String artist;
    private String etc;
}
