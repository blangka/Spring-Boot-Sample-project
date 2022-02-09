package com.hkmc.sample.api;

import com.hkmc.sample.model.ReqList;
import com.hkmc.sample.model.ResJson;
import com.hkmc.sample.model.ResPage;
import com.hkmc.sample.model.dto.OrderDto;
import com.hkmc.sample.model.dto.SimpleOrderDto;
import com.hkmc.sample.repo.jpa.OrderSearch;
import com.hkmc.sample.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* 성능 최적화
* */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Api(tags = "주문 API")
public class OrderApiController {

    private final OrderService orderService;

    /*
    * 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
    * - 단점: 지연로딩으로 쿼리 N번 호출
    * */
    @GetMapping("/v1/simple-orders")
    @ApiOperation(value = "ToOne관계 N+1문제 케이스")
    public ResJson<List<SimpleOrderDto>> simpleOrdersV1(){
        List<SimpleOrderDto> result = orderService.simpleOrdersV1();
        return new ResJson<>(result);
    }

    /*
     * 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * */
    @GetMapping("/v2/simple-orders")
    @ApiOperation(value = "ToOne관계 fetch join 결과")
    public ResJson<List<SimpleOrderDto>> simpleOrdersV2(){
        List<SimpleOrderDto> result = orderService.simpleOrdersV2();
        return  new ResJson<>(result);
    }

    /*
     * 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
     * - 단점: 지연로딩으로 쿼리 N번 호출
     * */
    @GetMapping("/v1/orders")
    @ApiOperation(value = "ToMany관계 N+1문제 케이스")
    public ResJson<List<OrderDto>> ordersV1(){
        List<OrderDto> result = orderService.orderV1();
        return new ResJson<>(result);
    }

    /*
     * 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * */
    @GetMapping("/v2/orders")
    @ApiOperation(value = "ToMany관계 Collection fetch join 결과 단점은 페이징 불가능")
    public ResJson<List<OrderDto>> ordersV2(){
        List<OrderDto> result = orderService.orderV2();
        return  new ResJson<>(result);
    }

    /*
     * 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * */
    @GetMapping("/v3/orders")
    @ApiOperation(value = "ToMany관계 Collection fetch join 결과 페이징 하면 컬렉션과 관련된걸 가지고 옴")
    public ResJson<List<OrderDto>> ordersV3(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                            @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<OrderDto> result = orderService.orderV3(offset,limit);
        return  new ResJson<>(result);
    }

    /*
     * 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
     * - fetch join으로 쿼리 1번 호출
     * */
    @PostMapping("/v4/orders")
    @ApiOperation(value = "ToMany관계 Collection fetch join 결과 페이징 하면 컬렉션과 관련된걸 가지고 옴 QueryDsl버전")
    public ResJson<ResPage<OrderDto>> ordersV4(ReqList reqList,
                                                @RequestBody OrderSearch orderSearch){
        ResPage<OrderDto> result = orderService.orderV4(orderSearch,reqList);
        return  new ResJson<>(result);
    }
}
