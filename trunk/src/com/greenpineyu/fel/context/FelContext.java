package com.greenpineyu.fel.context;

import com.greenpineyu.fel.common.Callback;
import com.greenpineyu.fel.function.Function;

public interface FelContext {

	/**
	 * 顶级上下文名称
	 */
	String ROOT_CONTEXT_NAME = "FelContext";

	/**
	 * Gets the value of a variable.
	 * @param name the variable's name
	 * @return the value
	 */
	Object get(Object name);

	/**
	 * Sets the value of a variable.
	 * @param name the variable's name
	 * @param value the variable's value
	 */
	void set(String name, Object value);

	/**
	 * Checks whether a variable is defined in this context.
	 * <p>A variable may be defined with a null value; this method checks whether the
	 * value is null or if the variable is undefined.</p>
	 * @param name the variable's name
	 * @return true if it exists, false otherwise
	 */
	boolean has(String name);

	/**
	 * 获取parentContext
	 * @return
	 */
	FelContext getParent();

	/**
	 * 设置parentContext
	 * @param context
	 */
	void setParent(FelContext context);

	Function getFunction(String name);

	void addFunction(Function name);

	void setCallback(Callback cb);

	Callback getCallback();

	/**
	 * 是否启用callback设置
	 * @return
	 */
	boolean isCbEnabled();

	/**
	 * 设置是否启用callback设置
	 * @param cbEnabled
	 */
	void setCbEnabled(boolean cbEnabled);

}