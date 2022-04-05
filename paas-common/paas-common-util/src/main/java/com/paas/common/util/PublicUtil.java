package com.paas.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;

/**
 * @Date 2022/4/4 周一 11:29
 * @Author xu
 * @FileName PublicUtil
 * @Description
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PublicUtil {

    public static boolean isEmpty(Object obj){
        if (obj == null) {
            return true;
        }
        if (obj == "") {
            return true;
        }
        //instanceof操作符的关系表达式操作数的类型必须是一个引用类型或者null类型
        //instanceof 偏向于比较 class之间,instance 和 class 之间
        //回归本源，instanceof 是 Java 中的二元运算符，左边是对象，右边是类；当对象是右边类或子类所创建对象时，返回 true；否则，返回 false。
        if (obj instanceof String) {
            return ((String) obj).length() == 0;
        }else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }else if (obj instanceof Map) {
            return ((Map) obj).size()==0;
        }
        return false;
    }


    public static boolean isNotEmpty(Object obj){
        if (obj == null) {
            return false;
        }
        if (obj == "") {
            return false;
        }
        if (obj instanceof String) {
            return ((String) obj).length() != 0;
        } else if (obj instanceof Collection) {
            return !((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).size() != 0;
        }
        return true;
    }

}
