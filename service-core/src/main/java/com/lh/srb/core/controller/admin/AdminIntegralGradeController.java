package com.lh.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.common.result.R;
import com.lh.srb.core.entity.IntegralGrade;
import com.lh.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/admin/core/integralGrade")
@Api(tags = "积分等级管理")
public class AdminIntegralGradeController {
    @Resource
    private IntegralGradeService integralGradeService;

    @GetMapping("/list")
    @ApiOperation("积分等级列表744545")
    public R lisAll(@RequestParam(name = "size",required = true) Integer size, @RequestParam(name = "current",required = true) Integer current,BigDecimal borrowAmount) {
        LambdaQueryWrapper<IntegralGrade> integralGradeQueryWrapper = new LambdaQueryWrapper<>();
        integralGradeQueryWrapper.orderByDesc(IntegralGrade::getId).le(borrowAmount != null,IntegralGrade::getBorrowAmount,borrowAmount);
        Page<IntegralGrade> integralGradePage = new Page<>(current,size);
        Page<IntegralGrade> page = integralGradeService.page(integralGradePage, integralGradeQueryWrapper);
        //List<IntegralGrade> list = page.getRecords();
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("list",page.getRecords());
        stringObjectHashMap.put("total",page.getTotal());
        stringObjectHashMap.put("current",page.getCurrent());
        stringObjectHashMap.put("size",page.getSize());
        return R.ok().data(stringObjectHashMap).message("获取列表成功");
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation("积分等级删除")
    public R delIntegralGrade(@ApiParam("idss") @PathVariable Long id) {
        boolean b = integralGradeService.removeById(id);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
       // return integralGradeService.removeById(id);
    }

    @PutMapping("/update")
    @ApiOperation("积分等级修改")
    public R updateIntegralGrade(@RequestBody IntegralGrade integralGrade) {
        boolean b = integralGradeService.updateById(integralGrade);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @PostMapping("/add")
    @ApiOperation("积分等级添加")
    public R addIntegralGrade(@RequestBody IntegralGrade integralGrade) {
        boolean b = integralGradeService.save(integralGrade);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }
    @ApiOperation("根据id查积分等级")
    @GetMapping("/get/{id}")
    public R getByIdIntegralGrade(@PathVariable Long id){
        IntegralGrade integralGrade = integralGradeService.getById(id);
        return R.ok().data("record",integralGrade);

    }
}
