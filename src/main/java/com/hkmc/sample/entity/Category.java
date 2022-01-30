package com.hkmc.sample.entity;

import com.hkmc.sample.entity.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//계층형 셀프 매핑 예제를 위해서 실무에서 거의 쓰지않음
@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")) //다대다 관계에서는 중간 테이블 객체가 있어야 한다.
    private List<Item> items = new ArrayList<>();

    //셀프로 영방향 연관 관계를 만듬
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
