package com.lh.srb.core.service;

import com.lh.srb.core.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.srb.core.pojo.dto.ExcelDictDTO;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author linhua
 * @since 2021-09-01
 */
public interface DictService extends IService<Dict> {
    List<ExcelDictDTO> listDictData();
    List<Dict> listByParentId(Long parentId);
    void importData(InputStream inputStream);
}
