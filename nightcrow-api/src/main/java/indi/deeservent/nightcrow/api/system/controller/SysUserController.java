package indi.deeservent.nightcrow.api.system.controller;

import cn.hutool.core.util.StrUtil;
import indi.deeservent.nightcrow.api.system.service.SysUserPostService;
import indi.deeservent.nightcrow.api.system.service.SysUserRoleService;
import indi.deeservent.nightcrow.api.system.service.SysUserService;
import indi.deeservent.nightcrow.api.system.vo.SysUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.utils.Result;
import indi.deeservent.nightcrow.api.security.user.SecurityUser;
import indi.deeservent.nightcrow.api.security.user.UserDetail;
import indi.deeservent.nightcrow.api.system.convert.SysUserConvert;
import indi.deeservent.nightcrow.api.system.entity.SysUserEntity;
import indi.deeservent.nightcrow.api.system.query.SysUserQuery;
import indi.deeservent.nightcrow.api.system.vo.SysUserPasswordVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 用户管理
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@RestController
@RequestMapping("sys/user")
@AllArgsConstructor
@Tag(name="用户管理")
public class SysUserController {
    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final SysUserPostService sysUserPostService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("page")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<SysUserVO>> page(@Valid SysUserQuery query){
        PageResult<SysUserVO> page = sysUserService.page(query);

        return Result.ok(page);
    }

    @GetMapping("{id}")
    @Operation(summary = "信息")
    @PreAuthorize("hasAuthority('sys:user:info')")
    public Result<SysUserVO> get(@PathVariable("id") Long id){
        SysUserEntity entity = sysUserService.getById(id);

        SysUserVO vo = SysUserConvert.INSTANCE.convert(entity);

        // 用户角色列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdList(id);
        vo.setRoleIdList(roleIdList);

        // 用户岗位列表
        List<Long> postIdList = sysUserPostService.getPostIdList(id);
        vo.setPostIdList(postIdList);

        return Result.ok(vo);
    }

    @GetMapping("info")
    @Operation(summary = "登录用户")
    public Result<SysUserVO> info(){
        SysUserVO user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());

        return Result.ok(user);
    }

    @PutMapping("password")
    @Operation(summary = "修改密码")
    public Result<String> password(@RequestBody @Valid SysUserPasswordVO vo){
        // 原密码不正确
        UserDetail user = SecurityUser.getUser();
        if(!passwordEncoder.matches(vo.getPassword(), user.getPassword())){
            return Result.error("原密码不正确");
        }

        // 修改密码
        sysUserService.updatePassword(user.getId(), passwordEncoder.encode(vo.getNewPassword()));

        return Result.ok();
    }

    @PostMapping
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result<String> save(@RequestBody @Valid SysUserVO vo){
        // 新增密码不能为空
        if (StrUtil.isBlank(vo.getPassword())){
            Result.error("密码不能为空");
        }

        // 密码加密
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        // 保存
        sysUserService.save(vo);

        return Result.ok();
    }

    @PutMapping
    @Operation(summary = "修改")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result<String> update(@RequestBody @Valid SysUserVO vo){
        // 如果密码不为空，则进行加密处理
        if(StrUtil.isBlank(vo.getPassword())){
            vo.setPassword(null);
        }else{
            vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        }

        sysUserService.update(vo);

        return Result.ok();
    }

    @DeleteMapping
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result<String> delete(@RequestBody List<Long> idList){
        Long userId = SecurityUser.getUserId();
        if(idList.contains(userId)){
            return Result.error("不能删除当前登录用户");
        }

        sysUserService.delete(idList);

        return Result.ok();
    }
}