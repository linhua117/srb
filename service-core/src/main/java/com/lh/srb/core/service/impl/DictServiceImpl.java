package com.lh.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lh.srb.core.entity.Dict;
import com.lh.srb.core.mapper.DictMapper;
import com.lh.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author linhua
 * @since 2021-09-01
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> listByParentId(Long parentId) {
        List<Dict> dictList = baseMapper.selectList(new QueryWrapper<Dict>().eq("parent_id", parentId));
        dictList.forEach(dict -> {
            Boolean t = hasChildren(dict);
            dict.setHasChildren(t);
        });
        return dictList;
    }

    private Boolean hasChildren(Dict dict){
        Integer count = baseMapper.selectCount(new QueryWrapper<Dict>().eq("parent_id", dict.getId()));
        if(count.intValue()>0){
            return true;
        }
        return false;
    }
}
