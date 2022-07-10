package indi.deeservent.nightcrow.api.system.controller;

import indi.deeservent.nightcrow.api.system.service.SysMenuService;
import indi.deeservent.nightcrow.api.system.service.SysRoleDataScopeService;
import indi.deeservent.nightcrow.api.system.service.SysRoleMenuService;
import indi.deeservent.nightcrow.api.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import indi.deeservent.nightcrow.api.common.page.PageResult;
import indi.deeservent.nightcrow.api.common.utils.Result;
import indi.deeservent.nightcrow.api.security.user.SecurityUser;
import indi.deeservent.nightcrow.api.security.user.UserDetail;
import indi.deeservent.nightcrow.api.system.convert.SysRoleConvert;
import indi.deeservent.nightcrow.api.system.entity.SysRoleEntity;
import indi.deeservent.nightcrow.api.system.vo.SysMenuVO;
import indi.deeservent.nightcrow.api.system.query.SysRoleQuery;
import indi.deeservent.nightcrow.api.system.vo.SysRoleVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色管理
 * 
 * @author Deeservent onion.dzw@icloud.com
 */
@RestController
@RequestMapping("sys/role")
@Tag(name="角色管理")
@AllArgsConstructor
public class SysRoleController {
	private final SysRoleService sysRoleService;
	private final SysRoleMenuService sysRoleMenuService;
	private final SysRoleDataScopeService sysRoleDataScopeService;
	private final SysMenuService sysMenuService;

	@GetMapping("page")
	@Operation(summary = "分页")
	@PreAuthorize("hasAuthority('sys:role:page')")
	public Result<PageResult<SysRoleVO>> page(@Valid SysRoleQuery query){
		PageResult<SysRoleVO> page = sysRoleService.page(query);

		return Result.ok(page);
	}

	@GetMapping("list")
	@Operation(summary = "列表")
	@PreAuthorize("hasAuthority('sys:role:list')")
	public Result<List<SysRoleVO>> list(){
		List<SysRoleVO> list = sysRoleService.getList(new SysRoleQuery());

		return Result.ok(list);
	}

	@GetMapping("{id}")
	@Operation(summary = "信息")
	@PreAuthorize("hasAuthority('sys:role:info')")
	public Result<SysRoleVO> get(@PathVariable("id") Long id){
		SysRoleEntity entity = sysRoleService.getById(id);

		// 转换对象
		SysRoleVO role = SysRoleConvert.INSTANCE.convert(entity);

		// 查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.getMenuIdList(id);
		role.setMenuIdList(menuIdList);

		// 查询角色对应的数据权限
		List<Long> orgIdList = sysRoleDataScopeService.getOrgIdList(id);
		role.setOrgIdList(orgIdList);

		return Result.ok(role);
	}

	@PostMapping
	@Operation(summary = "保存", hidden = true)
	@PreAuthorize("hasAuthority('sys:role:save')")
	public Result<String> save(@RequestBody @Valid SysRoleVO vo){
		sysRoleService.save(vo);

		return Result.ok();
	}

	@PutMapping
	@Operation(summary = "修改")
	@PreAuthorize("hasAuthority('sys:role:update')")
	public Result<String> update(@RequestBody @Valid SysRoleVO vo){
		sysRoleService.update(vo);

		return Result.ok();
	}

	@DeleteMapping
	@Operation(summary = "删除")
	@PreAuthorize("hasAuthority('sys:role:delete')")
	public Result<String> delete(@RequestBody List<Long> idList){
		sysRoleService.delete(idList);

		return Result.ok();
	}

	@GetMapping("menu")
	@Operation(summary = "角色菜单")
	@PreAuthorize("hasAuthority('sys:role:menu')")
	public Result<List<SysMenuVO>> menu(){
		UserDetail user = SecurityUser.getUser();
		List<SysMenuVO> list = sysMenuService.getUserMenuList(user, null);

		return Result.ok(list);
	}
}