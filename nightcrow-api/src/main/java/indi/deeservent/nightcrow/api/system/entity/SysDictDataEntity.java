package indi.deeservent.nightcrow.api.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import indi.deeservent.nightcrow.api.common.entity.BaseEntity;

/**
 * 数据字典
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_dict_data")
public class SysDictDataEntity extends BaseEntity {
	/**
	 * 字典类型ID
	 */
	private Long dictTypeId;
	/**
	 * 字典标签
	 */
	private String dictLabel;
	/**
	 * 字典值
	 */
	private String dictValue;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 排序
	 */
	private Integer sort;
}