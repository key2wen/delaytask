package com.key.commons.ibatis;

import com.key.commons.lang.enums.ValueEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Iterator;

public class ValueEnumTypeHandler<E extends Enum<E> & ValueEnum> extends BaseTypeHandler<E> {

	private Class<E> type;

	public ValueEnumTypeHandler(Class<E> type) {
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		if (jdbcType == null) {
			ps.setInt(i, parameter.getValue());
		} else {
			ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
		}

	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int i = rs.getInt(columnName);
		EnumSet<E> es = EnumSet.allOf(type);
		Iterator<E> it = es.iterator();
		while (it.hasNext()) {
			E e = it.next();
			if (e.getValue() == i) {
				return e;
			}
		}
		return null;
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
		EnumSet<E> es = EnumSet.allOf(type);
		Iterator<E> it = es.iterator();
		while (it.hasNext()) {
			E e = it.next();
			if (e.getValue() == i) {
				return e;
			}
		}
		return null;
	}

	@Override
	public E getNullableResult(ResultSet rs, int i) throws SQLException {
		EnumSet<E> es = EnumSet.allOf(type);
		Iterator<E> it = es.iterator();
		while (it.hasNext()) {
			E e = it.next();
			if (e.getValue() == i) {
				return e;
			}
		}

		return null;
	}

}
