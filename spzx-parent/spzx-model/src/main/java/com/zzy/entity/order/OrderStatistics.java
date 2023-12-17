package com.zzy.entity.order;

import com.zzy.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatistics extends BaseEntity {

    private Date orderDate;
    private BigDecimal totalAmount;
    private Integer totalNum;

}
