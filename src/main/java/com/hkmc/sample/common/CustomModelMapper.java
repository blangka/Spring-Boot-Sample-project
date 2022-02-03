package com.hkmc.sample.common;

import org.modelmapper.ModelMapper;

/**
 * ModelMapper는 기본적으로 source class가 null인 경우에 throw excption을 발생시킨다.
 * 그래서 해당 ModelMapper의 map 메소드를 오버라이드 해서, 기본 오브젝트로 생성 되도록 처리
 */

public class CustomModelMapper extends ModelMapper {

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        Object tmpSource = source;
        if(source == null){
            tmpSource = new Object();	// 기본 생성자로 생성 처리
        }

        return super.map(tmpSource, destinationType);
    }
}
