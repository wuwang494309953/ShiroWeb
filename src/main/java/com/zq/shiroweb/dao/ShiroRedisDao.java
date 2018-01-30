package com.zq.shiroweb.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by Archar on 2018/1/17.
 */
public class ShiroRedisDao extends AbstractSessionDAO {

    private static final int EXPIRE_TIME = 600;

    @Autowired
    private RedisTemplate<String, Object> template;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);

        template.opsForValue().set(
                session.getId().toString(),
                session,
                EXPIRE_TIME,
                TimeUnit.SECONDS
        );
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        Session session = (Session) template.opsForValue().get(serializable.toString());
        if (session != null) {
            template.opsForValue().set(
                    session.getId().toString(),
                    session,
                    EXPIRE_TIME,
                    TimeUnit.SECONDS
            );
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        template.opsForValue().set(
                session.getId().toString(),
                session,
                EXPIRE_TIME,
                TimeUnit.SECONDS
        );
    }

    @Override
    public void delete(Session session) {
        template.delete(session.getId().toString());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return null;
    }
}
