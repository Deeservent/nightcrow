package indi.deeservent.nightcrow.api.system.controller;

import indi.deeservent.nightcrow.api.system.service.SysPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.utils.Result;
import indi.deeservent.nightcrow.api.system.convert.SysPostConvert;
import indi.deeservent.nightcrow.api.system.entity.SysPostEntity;
import indi.deeservent.nightcrow.api.system.query.SysPostQuery;
import indi.deeservent.nightcrow.api.system.vo.SysPostVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
* 岗位管理
*
* @author Deeservent onion.dzw@icloud.com
*/
@RestController
@RequestMapping("sys/post")
@Tag(name="岗位管理")
@AllArgsConstructor
public class SysPostController {
    private final SysPostService sysPostService;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:post:page')")
    public Result<PageResult<SysPostVO>> page(@Valid SysPostQuery query){
        PageResult<SysPostVO> page = sysPostService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    @Operation(summary = "列表")
    public Result<List<SysPostVO>> list(){
        List<SysPostVO> list = sysPostService.getList();

        return Result.ok(list);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:post:info')")
    public Result<SysPostVO> get(@PathVariable("id") Long id){
        SysPostEntity entity = sysPostService.getById(id);

        return Result.ok(SysPostConvert.INSTANCE.convert(entity));
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('sys:post:save')")
    public Result<String> save(@RequestBody SysPostVO vo){
        sysPostService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('sys:post:update')")
    public Result<String> update(@RequestBody @Valid SysPostVO vo){
        sysPostService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('sys:post:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        sysPostService.delete(idList);

        return Result.ok();
    }
}