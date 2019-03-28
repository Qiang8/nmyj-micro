package com.taiji.cdp.daily.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 17:00
 **/
public class IssueVo  extends BaseVo<String> {

    @Getter
    @Setter
    @Length(max = 200,message = "专刊标题字段最大长度200")
    private String title;

    @Getter
    @Setter
    @Length(max = 1,message = "专刊状态（0未发布，1已发布）字段最大长度1")
    private String status;

    @Getter
    @Setter
    @Length(max = 1,message = "删除标志最大长度1（删除标记（0未删除，1已删除））")
    private String delFlag;
}