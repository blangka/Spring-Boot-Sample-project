package com.hkmc.sample.entity.item;

import lombok.Getter;

import javax.persistence.*;

@Entity
//연관 관계를 한테이블에 다 떄려 넣는것
//더 많은 옵션은 https://browndwarf.tistory.com/53?category=838021 참고
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") // 연관관계 종류 별로 어떻게 나올지를 설정
@Getter
public abstract class Item { //추상 클래스로 만듬 구현체를 가질꺼여서 상속 관계 매핑 예정이다

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    //공통 속성이다
    private String name;
    private int price;
    private int stockQuantity;
}
