package com.hkmc.sample.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") //안넣는 경우 기본 값이 나옴
public class Album extends Item{

    private String artist;
    private String etc;
}
