package com.cdkj.loan.ao;

import java.util.Map;

import com.cdkj.loan.bo.base.Paginable;
import com.cdkj.loan.domain.SYSConfig;

/**
 * 
 * @author: Gejin 
 * @since: 2016年4月17日 下午7:00:47 
 * @history:
 */
public interface ISYSConfigAO {

    String DEFAULT_ORDER_COLUMN = "id";

    public void editSYSConfig(String id, String ckey, String cvalue,
            String updater, String remark);

    public Paginable<SYSConfig> querySYSConfigPage(int start, int limit,
            SYSConfig condition);

    public SYSConfig getSYSConfig(Long id);

    public Map<String, String> getSYSConfigMap(String type);

    public SYSConfig getSYSConfig(String key);

    // 新增车贷期数管理
    public void addSYSConfig(String ckey, String cvalue, String updater,
            String remark);

    // 删除车贷期数管理
    public void dropSYSConfig(Long id);
}
