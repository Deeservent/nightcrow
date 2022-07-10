package indi.deeservent.nightcrow.api.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import indi.deeservent.nightcrow.api.common.exception.FastException;

/**
 * 校验工具类
 *
 * @author Deeservent onion.dzw@icloud.com
 */
public class AssertUtils {

    public static void isBlank(String str, String variable) {
        if (StrUtil.isBlank(str)) {
            throw new FastException(variable + "不能为空");
        }
    }

    public static void isNull(Object object, String variable) {
        if (object == null) {
            throw new FastException(variable + "不能为空");
        }
    }

    public static void isArrayEmpty(Object[] array, String variable) {
        if(ArrayUtil.isEmpty(array)){
            throw new FastException(variable + "不能为空");
        }
    }

}