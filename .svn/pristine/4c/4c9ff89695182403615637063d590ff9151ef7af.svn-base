package com.orong.utils;

/**
 * @Title: NumberConert.java
 * @Description: 12位 数字对应汉字读法
 * @date 2013年7月17日上午10:58:59 引用 引用
 * @author bigsalt 思想简要： 1.数字对应的转换成汉字，这其中不考虑任何情况只是简单的转换：如1000 转换为“一千零百零十零”。
 *         2.然后根据规则进行字符串的替换。
 *         虽然方法思想是非常简单的，并且脱离的现实中我们转换思维定式，但是这个方法却是从我们思维的定式中转化过来的。
 *         中国人读取数字其实是有一定的规律可循的：都是以四位做一个循环，如“一千一百一十一”，“一千一百一十一万”，“一千一百一十一亿”，
 *         因此我们只需解决4位时出现的各种情况并且解决好在两个四位相连接时可能发生的特殊情况，那么数字转换为汉字的问题也就得到了解决。
 */
public class NumberConert {
	protected static final String[] UNITS = { "", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千", };
	protected static final String[] NUMS = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", };

	/**
	 * 数字转换成中文汉字
	 * 
	 * @param value
	 *            转换的数字
	 * @return 返回数字转后的汉字字符串
	 */
	public static String translate(int value) {
		// 转译结果
		String result = "";
		for (int i = String.valueOf(value).length() - 1; i >= 0; i--) {
			int r = (int) (value / Math.pow(10, i));
			result += NUMS[r % 10] + UNITS[i];
		}
		result = result.replaceAll("零[十, 百, 千]", "零");
		result = result.replaceAll("零+", "零");
		result = result.replaceAll("零([万, 亿])", "$1");
		result = result.replaceAll("亿万", "亿"); // 亿万位拼接时发生的特殊情况
		if (result.startsWith("一十"))
			result = result.substring(1);
		if (result.endsWith("零"))
			result = result.substring(0, result.length() - 1);
		return result;
	}
}
