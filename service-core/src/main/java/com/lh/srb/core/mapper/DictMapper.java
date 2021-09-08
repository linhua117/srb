package com.lh.srb.core.mapper;

import com.lh.srb.core.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.srb.core.pojo.dto.ExcelDictDTO;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author linhua
 * @since 2021-09-01
 */
public interface DictMapper extends BaseMapper<Dict> {

    void insertBatch(List<ExcelDictDTO> list);
}
