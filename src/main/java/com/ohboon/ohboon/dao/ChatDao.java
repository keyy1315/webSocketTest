package com.ohboon.ohboon.dao;

import org.apache.ibatis.session.SqlSession;
import util.MybatisConnectionFactory;

public class ChatDao {
    private SqlSession ss = MybatisConnectionFactory.getSqlSession();

}
