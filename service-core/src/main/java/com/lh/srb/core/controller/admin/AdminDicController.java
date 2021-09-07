package com.lh.srb.core.controller.admin;

import com.lh.common.result.R;
import com.lh.srb.core.entity.Dict;
import com.lh.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Api(tags = "系统设置/数据字典")
@RestController
@RequestMapping("/admin/sys/dic")
public class AdminDicController {
    @Resource
    public DictService dictService;

    @GetMapping("{parentId}")
    @ApiOperation("列表查询")
    public R getDicList(@PathVariable Long parentId){
        List<Dict> dictList = dictService.listByParentId(parentId);
        return R.ok().data("list",dictList);
    }
}
