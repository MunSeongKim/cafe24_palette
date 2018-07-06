package com.cafe24.mammoth.oauth2.api;

import java.util.ArrayList;

/**
 * Api 엔티티들의 list에 대한 getter, setter 형식을 고정한다.
 * @author qyuee
 * @param <T>
 * @since 2018=07-06
 */
public interface EntityListStructure<T> {
	void setList(ArrayList<T> list);
	ArrayList<T> getList();
}
