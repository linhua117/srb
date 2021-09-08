package com.lh.srb.core.controller.admin;

import com.lh.common.exception.Assert;
import com.lh.common.exception.BusinessException;
import com.lh.common.result.R;
import com.lh.common.result.ResponseEnum;
import com.lh.srb.core.entity.Dict;
import com.lh.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
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

    @PostMapping()
    @ApiOperation("添加")
    public R addDic(@RequestBody Dict dict){
        Assert.notNull(dict.getName(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        boolean save = dictService.save(dict);
        if (save){
            return R.ok().message("字典添加成功！");
        }else{
            return R.ok().message("字典添加失败！");
        }
    }

    @PutMapping()
    @ApiOperation("修改")
    public R UpdateDic(@RequestBody Dict dict){
        Assert.notNull(dict.getId(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        boolean b = dictService.updateById(dict);
        if (b){
            return R.ok().message("字典修改成功！");
        }else{
            return R.ok().message("字典修改失败！");
        }

    }

    @DeleteMapping("{id}")
    @ApiOperation("删除")
    public R delDic(@PathVariable Long id){
        boolean b = dictService.removeById(id); if (b){
            return R.ok().message("字典删除成功！");
        }else{
            return R.ok().message("字典删除失败！");
        }

    }

    @ApiOperation("Excel批量导入数据字典")
    @PostMapping("/import")
    public R batchImport(
            @ApiParam(value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {

        try {
            InputStream inputStream = file.getInputStream();
            dictService.importData(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            //UPLOAD_ERROR(-103, "文件上传错误"),
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }
}
