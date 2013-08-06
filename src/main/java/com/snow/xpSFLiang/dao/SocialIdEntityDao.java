package com.snow.xpSFLiang.dao;

import com.snow.xpSFLiang.entity.SocialIdEntity;


public class SocialIdEntityDao extends BaseHibernateDao<SocialIdEntity> {

    public SocialIdEntity getSocialdentity(Long user_id) {
        SocialIdEntity socialdentity = (SocialIdEntity) daoHelper.findFirst("from SocialIdEntity u where u.user_id = ? ", user_id);
        return socialdentity;
    }
}
