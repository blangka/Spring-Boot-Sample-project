package com.hkmc.sample.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "목록 응답(페이징)")
public class ResPage<T> {

  @ApiModelProperty(notes = "목록", required = true)
  private List<T> data;

  @ApiModelProperty(notes = "현재 페이지 번호", required = true)
  private long page;

  @ApiModelProperty(notes = "한 페이지의 목록 갯수", required = true)
  private long size;

  @ApiModelProperty(notes = "목록 전체 갯수", required = true)
  private long count;

  @ApiModelProperty(notes = "전체 페이지수", hidden = true)
  private long pageCnt = 0L;

  public ResPage() {
  }

  public ResPage(long page, long count) {
    this.page = page;
    setCount(count);
  }

  public ResPage(List<T> data, long page, long count) {
    this.data = data;
    this.page = page;
    this.size = 10;
    this.count = count;
    if (count > 0L) {
      long t = (int) Math.floor((double) count / size);
      this.pageCnt = (count % size == 0) ? t : t + 1;
    }
  }

  public ResPage(List<T> data, long page, long size, long count) {
    this.data = data;
    this.page = page;
    this.size = size;
    this.count = count;
    if (size > 0L && count > 0L) {
      long t = (int) Math.floor((double) count / size);
      this.pageCnt = (count % size == 0) ? t : t + 1;
    }
  }

  public void setCount(long count) {
    if (size == 0L) {
      return;
    }
    this.count = count;
    long t = (int) Math.floor(count / size);
    pageCnt = (count % size == 0) ? t : t + 1;
  }

  public void setSize(long size) {
    this.size = size;
    if (count > 0) {
      long t = (int) Math.floor(count / size);
      pageCnt = (count % size == 0) ? t : t + 1;
    }
  }
}