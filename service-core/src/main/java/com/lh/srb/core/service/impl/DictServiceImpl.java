package com.lh.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lh.srb.core.entity.Dict;
import com.lh.srb.core.listener.ExcelDictDTOListener;
import com.lh.srb.core.mapper.DictMapper;
import com.lh.srb.core.pojo.dto.ExcelDictDTO;
import com.lh.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
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
@Slf4j
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
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("importData finished");
    }

    private Boolean hasChildren(Dict dict){
        Integer count = baseMapper.selectCount(new QueryWrapper<Dict>().eq("parent_id", dict.getId()));
        if(count.intValue()>0){
            return true;
        }
        return false;
    }
}
