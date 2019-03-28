package com.taiji.cdp.cmd.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 舆情调控单新增vo
 */
public class commandSaveVo {

    public commandSaveVo() {
    }

    /**
     * 舆情调控单信息
     */
    @Getter
    @Setter
    private CommandParamVo command;

    /**
     * 接受组织机构id集合
     */
    @Getter
    @Setter
    private List<String> orgIds;
}
