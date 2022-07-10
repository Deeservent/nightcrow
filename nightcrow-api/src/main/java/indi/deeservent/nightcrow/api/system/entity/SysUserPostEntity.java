package indi.deeservent.nightcrow.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import indi.deeservent.nightcrow.api.common.entity.BaseEntity;

/**
 * 用户岗位关系
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("sys_user_post")
public class SysUserPostEntity extends BaseEntity {
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	* 岗位ID
	*/
	private Long postId;
}