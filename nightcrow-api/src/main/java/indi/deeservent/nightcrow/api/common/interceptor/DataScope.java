package indi.deeservent.nightcrow.api.common.interceptor;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 数据范围
 *
 * @author Deeservent onion.dzw@icloud.com
 */
@Data
@AllArgsConstructor
public class DataScope {
    private String sqlFilter;

}