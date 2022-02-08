package com.hkmc.sample.entity.item;

import com.hkmc.sample.common.error.ApiException;
import com.hkmc.sample.common.error.ExceptionEnum;
import com.hkmc.sample.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder //상속 관계에서 builder를 위해
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

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    /**
     * 재고 증가
     */
    public void addStock(int stockQuantity){
        this.stockQuantity += stockQuantity;
    }

    /**
     * 재고 감소
     */
    public void removeStock(int stockQuantity){
        int restStrock = this.stockQuantity - stockQuantity;
        if (restStrock < 0) {
            throw new ApiException(ExceptionEnum.NOT_ENOUGH_STOCK);
        }
        this.stockQuantity = restStrock;
    }

    public void changeItem(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
