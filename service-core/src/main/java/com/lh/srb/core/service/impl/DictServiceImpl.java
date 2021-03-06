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
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);
        //创建ExcelDictDTO列表，将Dict列表转换成ExcelDictDTO列表
        ArrayList<ExcelDictDTO> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {

            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {


        List<Dict> dictList = null;
        try {
            dictList= (List<Dict>)redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if(dictList != null){
                log.info("redis数据##################################");
                return dictList;
            }
        }catch (Exception e){
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }

        dictList = baseMapper.selectList(new QueryWrapper<Dict>().eq("parent_id", parentId));
        dictList.forEach(dict -> {
            Boolean t = hasChildren(dict);
            dict.setHasChildren(t);
        });
        try {
            redisTemplate.opsForValue().set("srb:core:dictList:" + parentId,dictList,5, TimeUnit.MINUTES);
        }catch (Exception e){
            log.error("redis服务器异常：" + ExceptionUtils.getStackTrace(e));
        }
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
