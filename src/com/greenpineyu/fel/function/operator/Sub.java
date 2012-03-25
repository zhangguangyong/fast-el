package com.greenpineyu.fel.function.operator;

import java.util.List;

import com.greenpineyu.fel.common.NumberUtil;
import com.greenpineyu.fel.common.ReflectUtil;
import com.greenpineyu.fel.compile.FelMethod;
import com.greenpineyu.fel.compile.SourceBuilder;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.exception.EvalException;
import com.greenpineyu.fel.parser.FelNode;

public class Sub extends StableFunction {

	private static Sub instance;

	public static Sub getInstance() {
		return instance;
	}

	static {
		instance = new Sub();
	}

	private void appendArg(StringBuilder sb, SourceBuilder argMethod,FelContext ctx,FelNode node) {
		Class<?> t = argMethod.returnType(ctx, node);
		sb.append("(");
		if (ReflectUtil.isPrimitiveOrWrapNumber(t)) {
			// 数值型和字符型时，直接添加
			sb.append(argMethod.source(ctx, node));
		} else if (CharSequence.class.isAssignableFrom(t)) {
			// FIXME 处理1-"1"的
		}
		sb.append(")");
	}

	public FelMethod toMethod(FelNode node, FelContext ctx) {
		List<FelNode> children = node.getChildren();
		StringBuilder sb = new StringBuilder();
		FelNode right = null;
		if (children.size() == 2) {
			FelNode left = children.get(0);
			SourceBuilder lm = left.toMethod(ctx);
			appendArg(sb, lm,ctx,left);
			right = children.get(1);
		} else if (children.size() == 1) {
			right = children.get(0);
		}
		sb.append("-");
		SourceBuilder rm = right.toMethod(ctx);
		appendArg(sb, rm,ctx,right);
		FelMethod m = new FelMethod(Number.class, sb.toString());
		return m;
	}

	public String getName() {
		return "-";
	}

	public Object call(FelNode node, FelContext context) {
		List<FelNode> children = node.getChildren();
		if (children.size() == 2) {
			FelNode left = children.get(0);
			Object leftValue = left.eval(context);
			FelNode right = children.get(1);
			Object rightValue = right.eval(context);
			if (leftValue instanceof Number && rightValue instanceof Number) {
				double l = NumberUtil.toDouble(leftValue);
				double r = NumberUtil.toDouble(rightValue);
				return NumberUtil.parseNumber(l - r);
//				if (NumberUtil.isFloatingPoint(left)
//						|| NumberUtil.isFloatingPoint(right)) {
//				}
//				return NumberUtil.parseNumber(((Number) leftValue).longValue()
//						- ((Number) rightValue).longValue());
			}
			throw new EvalException("执行减法出错，参数必须是数值型");
		}
		if (children.size() == 1) {
			FelNode right = children.get(0);
			Object rightValue = right.eval(context);
			if (rightValue instanceof Number) {
				if (NumberUtil.isFloatingPoint(rightValue)) {
					return NumberUtil.toDouble(rightValue) * -1;
				}
				return NumberUtil.parseNumber(((Number) rightValue).longValue() * -1);
			}
			throw new EvalException("执行减法出错，参数必须是数值型");
		}
		throw new EvalException("执行减法出错，参数长度必须是1或2");
	}
	
	public static void main(String[] args) {
		int a = -(1);
		System.out.println(a);
	}

}