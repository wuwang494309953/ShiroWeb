package com.zq.shiroweb.dao;

import com.zq.shiroweb.entity.SysDept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Archar on 2018/1/5.
 */
public interface SysDeptDao extends JpaRepository<SysDept, Long> {
    int countByNameEqualsAndAndIdEqualsAndParentIdEquals(String name, Integer id, Integer parentId);

    List<SysDept> findAllByLevelLike(String level);

    SysDept findByIdEquals(Integer id);

    int deleteSysDeptByIdEquals(Integer id);

    int countByParentIdEquals(Integer id);
}
