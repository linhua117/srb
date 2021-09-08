package com.lh.srb.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.lh.common.exception.Assert;
import com.lh.common.exception.BusinessException;
import com.lh.common.result.R;
import com.lh.common.result.ResponseEnum;
import com.lh.srb.core.entity.Dict;
import com.lh.srb.core.pojo.dto.ExcelDictDTO;
import com.lh.srb.core.service.DictService;
import com.sun.deploy.net.URLEncoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @ApiOperation("Excel数据的导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response){

        try {
            // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("mydict", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ExcelDictDTO.class).sheet("数据字典").doWrite(dictService.listDictData());

        } catch (IOException e) {
            //EXPORT_DATA_ERROR(104, "数据导出失败"),
            throw  new BusinessException(ResponseEnum.EXPORT_DATA_ERROR, e);
        }
    }
}
