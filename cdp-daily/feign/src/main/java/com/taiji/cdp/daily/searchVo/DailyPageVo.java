package com.taiji.cdp.daily.searchVo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author SunYi
 */
public class DailyPageVo extends IdVo<String> {

    public DailyPageVo(){}

    @Getter @Setter
    private int page;

    @Getter @Setter
    private int size;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private List<String> ids;
}
