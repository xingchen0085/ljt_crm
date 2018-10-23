package com.douples.pay.user.dao;

import com.douples.pay.common.dao.BaseDao;
import com.douples.pay.user.entity.SeqBuild;

/**
 * 生产编号DAO
 * <p>Title: BuildNoDao</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public interface BuildNoDao extends BaseDao<SeqBuild> {

    public String getSeqNextValue(SeqBuild seqBuild);

}
