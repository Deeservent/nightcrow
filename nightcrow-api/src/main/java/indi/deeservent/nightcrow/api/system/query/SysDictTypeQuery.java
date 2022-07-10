package indi.deeservent.nightcrow.api.system.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import indi.deeservent.nightcrow.api.common.query.Query;

/**
 * 字典类型
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "字典类型查询")
public class SysDictTypeQuery extends Query {
    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典名称")
    private String dictName;

}
