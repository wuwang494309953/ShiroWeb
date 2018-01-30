package com.zq.shiroweb.shiro;

import com.google.common.collect.Maps;
import com.zq.shiroweb.dao.SysAclDao;
import com.zq.shiroweb.entity.SysAcl;
import com.zq.shiroweb.entity.SysRole;
import com.zq.shiroweb.exception.SystemException;
import com.zq.shiroweb.service.ShiroPermissionService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Archar on 2018/1/23.
 */
@Service
public class MyShiroService {

    private static final String ROLES = "roles";

    private static final String PERMS = "perms";

    @Autowired
    @Qualifier("shiroFilter")
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private ShiroPermissionService shiroPermissionService;

    /**
     * 动态更新新的权限
     * @param
     */
    public synchronized void updatePermission() {
        Map<String, String> filterMap = shiroPermissionService.initFilterMap();
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();

            // 获取过滤管理器
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager filterManager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            //清空拦截管理器中的存储
            filterManager.getFilterChains().clear();
            /*
            清空拦截工厂中的存储,如果不清空这里,还会把之前的带进去
            ps:如果仅仅是更新的话,可以根据这里的 map 遍历数据修改,重新整理好权限再一起添加
             */
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

            // 相当于新建的 map, 因为已经清空了
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            //把修改后的 map 放进去
            chains.putAll(filterMap);

            //这个相当于是全量添加
            for (Map.Entry<String, String> entry : filterMap.entrySet()) {
                //要拦截的地址
                String url = entry.getKey().trim().replace(" ", "");
                //地址持有的权限
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                //生成拦截
                filterManager.createChain(url, chainDefinition);
            }
        } catch (Exception e) {
            throw new SystemException("Shiro缓存刷新失败");
        }
    }
}
