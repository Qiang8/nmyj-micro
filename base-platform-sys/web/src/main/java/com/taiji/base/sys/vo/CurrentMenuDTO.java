package com.taiji.base.sys.vo;

import com.taiji.micro.common.utils.ITreeNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * <p>Title:CurrentMenuDTO.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/3 10:27</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
public class CurrentMenuDTO implements ITreeNode<CurrentMenuDTO> {

    @Getter@Setter
    private String id;

    @Getter@Setter
    private String path;

    @Getter@Setter
    private String component;

    @Getter@Setter
    private String redirect;

    @Getter@Setter
    private String name;

    @Getter@Setter
    private MetaDTO meta;

    @Getter@Setter
    private String parentId;

    @Getter@Setter
    private Integer orders;

    @Getter@Setter
    private Boolean hidden;

    @Getter@Setter
    private Boolean alwaysShow;

    @Getter@Setter
    List<CurrentMenuDTO> children;

    public CurrentMenuDTO(){

    }

    public CurrentMenuDTO(MenuVo v){
       this.id = v.getId();
       this.path = v.getPath();
       this.component = v.getComponent();
       this.redirect = v.getRedirect();
       this.name = v.getRouterName();
       this.parentId = v.getParentId();
       this.orders = v.getOrders();
       this.hidden = v.getHidden();
       this.alwaysShow = v.getAlwaysShow();
       MetaDTO meta = new MetaDTO();
       meta.setTitle(v.getMenuName());
       meta.setIcon(v.getIcon());
       this.setMeta(meta);
    }
}
