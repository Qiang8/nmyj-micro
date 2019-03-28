package com.taiji.cdp.base.midAtt.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.taiji.cdp.base.common.enums.FileStatusEnum;
import com.taiji.cdp.base.midAtt.entity.Attachment;
import com.taiji.cdp.base.midAtt.entity.MidAtt;
import com.taiji.cdp.base.midAtt.entity.QMidAtt;
import com.taiji.cdp.base.midAtt.vo.MidAttSaveVo;
import com.taiji.micro.common.repository.BaseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class MidAttRepository extends BaseJpaRepository<MidAtt,String>{

    @Autowired
    AttachmentRepository attachmentRepository;

    @Transactional
    @Override
    public MidAtt save(MidAtt entity){
        String attId = entity.getAttId();
        Assert.hasText(attId,"attId 不能为空字符串");
        MidAtt oldResult = findByAttId(attId);
        if(null!=oldResult){
            return oldResult;
        }
        Attachment attachment = attachmentRepository.findOne(attId);
        Assert.notNull(attachment,"attachment 不能为null");
        entity.setAttDoc(attachment);
        if(!StringUtils.isEmpty(entity.getId())){
            entity.setId(null);
        }
        MidAtt result = super.save(entity);
        result.setAttId(result.getAttDoc().getId());

        attachment.setFileStatus(FileStatusEnum.FORM_SAVE.getCode()); //改变附件对象状态：关联保存
        attachmentRepository.save(attachment);

        return result;
    }

    //避免重复入库,先查询是否存在该记录
    private MidAtt findByAttId(String attId){
        QMidAtt midAtt = QMidAtt.midAtt;
        return findOne(midAtt.attDoc.id.eq(attId));
    }


    /**
     * 通过附件id删除单个中间表对象
     */
    @Transactional
    public void deleteByAttId(String attId){
        Assert.hasText(attId,"attId 不能为空字符串");
        QMidAtt midAtt = QMidAtt.midAtt;
        JPADeleteClause deleteClause = querydslDelete(midAtt);
        deleteClause.where(midAtt.attDoc.id.eq(attId)).execute();

        Attachment attachment = attachmentRepository.findOne(attId);
        Assert.notNull(attachment,"attachment 不能为null");
        attachment.setFileStatus(FileStatusEnum.DELETED.getCode()); //改变附件对象状态：关联保存
        attachmentRepository.save(attachment);

    }

    /**
     * 通过附件ids删除多个中间表对象
     */
    @Transactional
    public void deleteByAttIds(List<String> attIds){
        if(!CollectionUtils.isEmpty(attIds)){
            QMidAtt midAtt = QMidAtt.midAtt;
            JPADeleteClause deleteClause = querydslDelete(midAtt);
            deleteClause.where(midAtt.attDoc.id.in(attIds)).execute();

            List<Attachment> attachments = attachmentRepository.findAll(attIds);
            for(Attachment attachment:attachments){
                attachment.setFileStatus(FileStatusEnum.DELETED.getCode());  //改变附件对象状态：删除
                attachmentRepository.save(attachment);
            }
        }
    }

    /**
     * 根据业务实体id获取所有附件对象
     */
    public List<Attachment> findAttsByEntityId(String entityId){
        Assert.hasText(entityId,"entityId 不能为空字符串");
        QMidAtt midAtt = QMidAtt.midAtt;
        JPQLQuery<MidAtt> query = from(midAtt);
        return query.select(midAtt.attDoc).where(midAtt.entityId.eq(entityId)).orderBy(midAtt.attDoc.createTime.desc()).fetch();
    }

    /**
     * 业务提交保存时调用，保存附件信息
     */
    @Transactional
    public void saveMidAtts(MidAttSaveVo midAttSaveVo){
        String entityId = midAttSaveVo.getEntityId();
        List<String> fileIds = midAttSaveVo.getFileIds();
        List<String> deleteIds = midAttSaveVo.getDeleteIds();
        //保存部分
        List<Attachment> attachments = attachmentRepository.findAll(fileIds);
        if(!CollectionUtils.isEmpty(attachments)){
            for(Attachment att:attachments){
                if(FileStatusEnum.UPLOADED.getCode().equals(att.getFileStatus())){ //新增附件
                    MidAtt midAtt = new MidAtt();
                    midAtt.setAttId(att.getId());
                    midAtt.setEntityId(entityId);
                    this.save(midAtt);
                }//其他即为 已关联或者删除
            }
        }
        //删除部分
        deleteByAttIds(deleteIds);
    }

}
