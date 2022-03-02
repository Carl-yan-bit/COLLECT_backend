package com.seiii.backend_511.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PageInfoUtil {
    /**
     * 将PageInfo对象泛型中的PO对象转化为VO对象后返回
     * @param <P> PO类型
     * @param <V> VO类型
     */
    public static <P, V> PageInfo<V> convert(PageInfo<P> pageInfoPO, Class<V> vClass) {
        // 创建Page对象，实际上是一个ArrayList类型的集合
        Page<V> page = new Page<>(pageInfoPO.getPageNum(), pageInfoPO.getPageSize());
        page.setTotal(pageInfoPO.getTotal());
        for (P p : pageInfoPO.getList()) {
            V v = null;
            try {
                v = vClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            BeanUtils.copyProperties(p, v);
            page.add(v);
        }
        return new PageInfo<>(page);
    }
    public static <T> PageInfo<T> ListToPageInfo(List<T> list,int currPage){
        Page<T> page = new Page<>(currPage,CONST.PAGE_SIZE);
        page.setTotal(list.size());
        int start = (currPage-1)*CONST.PAGE_SIZE;
        int end = Math.min(start+CONST.PAGE_SIZE, list.size());
        page.addAll(list.subList(start,end));
        return new PageInfo<>(page);
    }
}
