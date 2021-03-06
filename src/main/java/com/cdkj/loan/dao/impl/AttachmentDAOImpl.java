package com.cdkj.loan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cdkj.loan.dao.IAttachmentDAO;
import com.cdkj.loan.dao.base.support.AMybatisTemplate;
import com.cdkj.loan.domain.Attachment;

@Repository("attachmentDAOImpl")
public class AttachmentDAOImpl extends AMybatisTemplate implements
        IAttachmentDAO {

    @Override
    public int insert(Attachment data) {
        return super.insert(NAMESPACE.concat("insert_attachment"), data);
    }

    @Override
    public int delete(Attachment data) {
        return 0;
    }

    @Override
    public Attachment select(Attachment condition) {
        return super.select(NAMESPACE.concat("select_attachment"), condition,
            Attachment.class);
    }

    @Override
    public long selectTotalCount(Attachment condition) {
        return super.selectTotalCount(
            NAMESPACE.concat("select_attachment_count"), condition);
    }

    @Override
    public List<Attachment> selectList(Attachment condition) {
        return super.selectList(NAMESPACE.concat("select_attachment"),
            condition, Attachment.class);
    }

    @Override
    public List<Attachment> selectList(Attachment condition, int start,
            int count) {
        return super.selectList(NAMESPACE.concat("select_attachment"), start,
            count, condition, Attachment.class);
    }

    @Override
    public int deleteByBiz(Attachment attachment) {
        return super.delete(NAMESPACE.concat("delete_attachmentByBiz"),
            attachment);
    }

    @Override
    public int deleteAttachment(Attachment data) {
        // TODO Auto-generated method stub
        return super.delete(NAMESPACE.concat("delete_attachment"), data);
    }

    @Override
    public int updateAttachment(Attachment data) {
        // TODO Auto-generated method stub
        return super.update(NAMESPACE.concat("update_attachment"), data);
    }

}
