package com.zq.shiroweb;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zq.shiroweb.dto.UserDto;
import com.zq.shiroweb.entity.QSysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Archar on 2018/1/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryDslTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void test() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QSysUser qSysUser = QSysUser.sysUser;
        List<UserDto> result = jpaQueryFactory.select(
                Projections.bean(
                        UserDto.class,
                        qSysUser.username,
                        qSysUser.telephone,
                        qSysUser.mail,
                        qSysUser.dept,
                        qSysUser.status,
                        qSysUser.remark)
        ).from(qSysUser).fetch();
        System.out.println(result);

    }
}

