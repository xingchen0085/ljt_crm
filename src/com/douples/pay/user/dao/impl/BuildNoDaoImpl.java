package com.douples.pay.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.douples.pay.common.dao.impl.BaseDaoImpl;
import com.douples.pay.user.dao.BuildNoDao;
import com.douples.pay.user.entity.SeqBuild;

/**
 * 生成编号DAO实现类
 * <p>Title: BuildNoDaoImpl</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
@Repository
public class BuildNoDaoImpl   extends BaseDaoImpl<SeqBuild> implements BuildNoDao {

    @Override
    public String getSeqNextValue(SeqBuild seqBuild) {
        return super.getSqlSession().selectOne(getStatement("getSeqNextValue"),seqBuild);
    }
}
