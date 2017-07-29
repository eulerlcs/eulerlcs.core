package com.github.eulerlcs.regularexpression;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

// http://www.cnblogs.com/playing/archive/2011/03/15/1984943.html

public class UtilsTest {

	/**
	 * 多行模式-1
	 */
	@Test
	public void test01_01() {
		String t1 = Utils.readAllFromResouce("01.txt");

		// 默认 单行模式
		Pattern p1 = Pattern.compile("^def$");
		Matcher m1 = p1.matcher(t1);

		if (m1.find()) {
			System.out.println("found!");
		} else {
			System.out.println("not found!");
		}
	}

	/**
	 * <pre>
	 * 多行模式-2 
	 *  (?m)		Pattern.MULTILINE
	 * </pre>
	 */
	@Test
	public void test01_02() {
		String t1 = Utils.readAllFromResouce("01.txt");

		// 多行模式 写法一
		// Pattern p1 = Pattern.compile("(?m)^def$");
		// 多行模式 写法二
		Pattern p1 = Pattern.compile("^def$", Pattern.MULTILINE);

		Matcher m1 = p1.matcher(t1);

		if (m1.find()) {
			System.out.println("found!");
		} else {
			System.out.println("not found!");
		}
	}

	/**
	 * flag设定和(?X)的等价关系
	 * 
	 * <pre>
	 *  (?m)		Pattern.MULTILINE
	 *  (?i)		Pattern.CASE_INSENSITIVE
	 *  (?u)		Pattern.UNICODE_CASE
	 *  (?s)		Pattern.DOTALL
	 *  (?d)		Pattern.UNIX_LINES
	 *  (?x)		Pattern.COMMENTS
	 * </pre>
	 */

	/**
	 * <pre>
	 * ascii大小写
	 *  (?i)		Pattern.CASE_INSENSITIVE
	 * </pre>
	 */
	@Test
	public void test02_01() {
		String t1 = "abc AbC aCd ａｂｃ ＡＢｃ 2343";
		String r1 = "abc";

		// 默认 区分大小写
		// Pattern p1 = Pattern.compile(r1);

		// 忽略ascii大小，写法一
		Pattern p1 = Pattern.compile("(?i)abc");

		// 忽略ascii大小，写法而
		// Pattern p1 = Pattern.compile(r1, Pattern.CASE_INSENSITIVE );

		Matcher m1 = p1.matcher(t1);

		while (m1.find()) {
			System.out.println(m1.group());
		}
	}

	/**
	 * <pre>
	 * unicode大小写
	 *  (?u)		Pattern.UNICODE_CASE
	 * </pre>
	 */
	@Test
	public void test03_01() {
		String t1 = "abc AbC aCd ａｂｃ ＡＢｃ 2343";
		String r1 = "ａｂｃ";// 日文输入法下，全角abc,也就是宽字体

		// 默认 区分大小写只适用于ascii
		// Pattern p1 = Pattern.compile((?i)ａｂｃ);

		// 忽略ascii大小，写法一
		Pattern p1 = Pattern.compile("(?iu)ａｂｃ");

		// 忽略ascii大小，写法而
		// Pattern p1 = Pattern.compile(r1, Pattern.UNICODE_CASE);

		Matcher m1 = p1.matcher(t1);

		while (m1.find()) {
			System.out.println(m1.group());
		}
	}

	/** 通过设定标志位忽略大小写 */
	@Test
	public void test03_02() {
		String t1 = "abc AbC aCd\nABCD 2343";
		String r1 = "(?i)(?m)abc";
		Pattern p1 = Pattern.compile(r1);
		Matcher m1 = p1.matcher(t1);

		while (m1.find()) {
			System.out.println(m1.group());
		}
	}

	@Test
	public void test04_01_dotall() {
		Pattern p = null;
		Matcher m = null;

		String text1 = "width height";
		String text2 = "width\nheight";
		// Pattern p = Pattern.compile("(?s)width.height");
		p = Pattern.compile("width.height", Pattern.DOTALL);

		m = p.matcher(text1);
		boolean result1 = m.find();
		if (result1) {
			System.out.println("text1 found");
		} else {
			System.out.println("text1 not found");
		}

		m = p.matcher(text2);
		boolean result2 = m.find();
		if (result2) {
			System.out.println("text2 found");
		} else {
			System.out.println("text2 not found");
		}
	}

	@Test
	public void test05_01_canon_eq() {
		Pattern p = null;
		Matcher m = null;

		// 法语小写 à
		String text1 = "a\u0300";
		p = Pattern.compile("\u00E0", Pattern.CANON_EQ);

		m = p.matcher(text1);
		boolean result1 = m.find();
		if (result1) {
			System.out.println("text1 found");
		} else {
			System.out.println("text1 not found");
		}

		p = Pattern.compile("\u00E5");
		m = p.matcher(text1);
		boolean result2 = m.find();
		if (result2) {
			System.out.println("text2 found");
		} else {
			System.out.println("text2 not found");
		}
	}

	/**
	 * group
	 * 
	 * <pre>
	 * group(0):正则表达式的匹配值 
	 * group(1):第一个子串
	 * </pre>
	 */
	@Test
	public void test05_01() {
		Pattern p = Pattern.compile("([a-z]+)-(\\d+)");
		Matcher m = p.matcher("type x-235, type y-3, type zw-465");

		while (m.find()) {
			for (int i = 0; i < m.groupCount() + 1; i++) {
				System.out.println("group(" + i + ")=" + m.group(i));
			}
			System.out.println("---------------------");
		}
	}

	/**
	 * 字符串分割的例子
	 */
	@Test
	public void test05_02() {
		String abc = "a///b/c";

		// 分割后的数组中包含空字符
		String[] array1 = abc.split("/");
		for (String str : array1) {
			System.out.println(str);
		}

		System.out.println("---------------------");

		// 分割后的数组中取出了空字符
		String[] array2 = abc.split("/+");
		for (String str : array2) {
			System.out.println(str);
		}
	}

	/**
	 * 替换
	 */
	@Test
	public void test06_01() {
		String str = "Orange is 100yuan, Banana is 180 yuan.";
		String regex = "\\d+\\s*yuan";
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(str);
		System.out.println(m.find());
		String result = m.replaceFirst("_$0_");

		System.out.println(result);
	}

	/**
	 * 替换
	 */
	@Test
	public void test06_02() {
		String str = "Orange is 100yuan, Banana is 180 yuan.";
		String regex = "(\\d)\\s*(yuan)";

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);

		String result = m.replaceAll("$2_$1");

		System.out.println(result);
	}

	/**
	 * 命名分组，替换
	 */
	@Test
	public void test06_03() {
		String pathfFilename = "aa/notepad.exe";

		String regex = "^.+/(?<filename>.+)$";
		String replacement = "${filename}";

		String filename = pathfFilename.replaceFirst(regex, replacement);
		System.out.println(filename);
	}

	/**
	 * 从文本中读取多行数据后，建议先把回车符删掉
	 */
	@Test
	public void test07_01() {
		String t1 = Utils.readAllFromResouce("07.txt");
		System.out.println("--orignal text start--");
		System.out.print(t1);
		System.out.println("--orignal text  end --");

		// 统一换行符
		String ret1 = t1.replaceAll("(\r\n)|\r", "\n");
		System.out.println("--统一换行符 start--");
		System.out.print(ret1);
		System.out.println("--统一换行符   end --");

		// 行单位前后trim
		String ret2 = ret1.replaceAll("(?m)^\\s*(.*?)\\s*$", "$1");
		System.out.println("--行单位前后trim start--");
		System.out.println(ret2);
		System.out.println("--行单位前后trim   end --");

		assertFalse(ret2.equals(t1));
	}

	@Test
	public void test01_04_Zz() {
		Pattern p = null;
		Matcher m = null;
		boolean result1 = false;
		boolean result2 = false;
		boolean result3 = false;

		String text1 = "abc def";
		String text2 = "def abc";
		String text3 = "def abc\n";

		p = Pattern.compile("abc\\z");

		m = p.matcher(text1);
		result1 = m.find();

		m = p.matcher(text2);
		result2 = m.find();

		m = p.matcher(text3);
		result3 = m.find();

		p = Pattern.compile("abc\\Z");

		m = p.matcher(text1);
		result1 = m.find();

		m = p.matcher(text2);
		result2 = m.find();

		m = p.matcher(text3);
		result3 = m.find();

		assertFalse(result1);
		assertTrue(result2);
		assertTrue(result3);
	}

	@Test
	public void test_foreignFullName() {
		String text = Utils.readAllFromResouce("foreignFullName.txt");

		Pattern p = Pattern.compile("^[A-Z]+,[A-Z]+$", Pattern.MULTILINE);
		Matcher m = p.matcher(text);
		while (m.find()) {
			System.out.println(m.group());
		}
	}

	/**
	 * Matcher.group的例子：匹配 字母-数字
	 * 
	 * <pre>
	 * group(0):正则表达式的匹配值
	 * </pre>
	 */
	@Test
	public void test05_00() {
		Pattern p = Pattern.compile("([a-z]+)-(\\d+)");
		Matcher m = p.matcher("type x-235, type y-3, type zw-465");

		while (m.find()) {
			System.out.println(m.group());
		}
	}

	/**
	 * 如何用正则表达式求模板字符串替换前后的键值对<br>
	 * 
	 * @see <a href=
	 *      "https://github.com/eulerlcs/coding2017.eulerlcs/wiki/%E5%A6%82%E4%BD%95%E7%94%A8%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F%E6%B1%82%E6%A8%A1%E6%9D%BF%E5%AD%97%E7%AC%A6%E4%B8%B2%E6%9B%BF%E6%8D%A2%E5%89%8D%E5%90%8E%E7%9A%84%E9%94%AE%E5%80%BC%E5%AF%B9">如何用正则表达式求模板字符串替换前后的键值对</a>
	 */
	@Test
	public void test_template_string() {
		String tmpl = null;
		String text = null;

		// test data 1
		tmpl = "【工银信用卡】于${startTime}至${endTime}申办奋斗卡，无年费，赢郎平签名排球！详情${link}";
		text = "【工银信用卡】于昨天至今天申办奋斗卡，无年费，赢郎平签名排球！详情没有";

		// test data 2
		tmpl = "a${startTime}b${endTime}";
		text = "abbc";

		// test data 3
		tmpl = "(${startTime}至${endTime}";
		text = "(昨天至今天";

		System.out.println("模板字符串：" + tmpl);
		System.out.println("文本字符串：" + text);
		System.out.println();

		// 模板字符串中变量的正则表达式
		String keyRegex = "\\$\\{.*?\\}";

		// 找出模板字符串中变量
		List<String> keyList = new ArrayList<>();
		{
			Pattern p = Pattern.compile(keyRegex);
			Matcher m = p.matcher(tmpl);

			while (m.find()) {
				keyList.add(m.group());
			}
		}

		// 找出文本字符串中替换值集合
		List<String> valueList = new ArrayList<>();
		{
			// **关键想法** 把模板字符串改装成正则表达式

			// ** 难点**
			// 如果字符串中有元字符，改装后的正则表达式会有语法错误。
			// 例子：如果模板字符串中仅存在一个（，该装后的正则表达式会有语法错误。
			// 所以在每一个字符前都加\,这样（就变成\（,变成匹配（，符合原意。
			// 但是如果模板字符串中仅存在一个t，变成了\t,变成了匹配tab键，又出现了错误。
			// 结合以上的说明，我们要有选择的在字符前加\, 我们在非数字字母的字符前加\
			String tmplEscape = tmpl.replaceAll("([^\\w])", "\\\\$1");

			// 在原始模板字符串中的占位符中的非数字字母的字符前，
			// 已经被加\，所以占位符的正则表达式也要做相应的编辑
			String keyRegexEscape = "\\\\\\$\\\\\\{.*?\\\\\\}";

			String tmplRegex = "^" + tmplEscape.replaceAll(keyRegexEscape, "(.*?)") + "$";
			System.out.println("模板字符串改装后的正则表达式：" + tmplRegex);
			System.out.println();

			Pattern p = Pattern.compile(tmplRegex);
			Matcher m = p.matcher(text);
			if (m.find()) {
				for (int i = 1; i <= m.groupCount(); i++) {
					valueList.add(m.group(i));
				}
			}
		}

		// 输出结果
		if (valueList.isEmpty()) {
			System.out.println("the text file format is not correct");
		} else {
			for (int i = 0; i < keyList.size(); i++) {
				System.out.println(keyList.get(i) + "\t\t" + valueList.get(i));
			}
		}
	}

	@Test
	public void test_BradyWZH() {
		String tempStr = null;
		String textStr = null;

		tempStr = "【工银信用卡】于${startTime}至${endTime}申办奋斗卡，无年费，赢郎平签名排球！详情${link}";
		textStr = "【工银信用卡】于昨天至今天申办奋斗卡，无年费，赢郎平签名排球！详情没有";

		tempStr = "a${startTime}b${endTime}";
		textStr = "abbc";

		tempStr = "(${startTime}至${endTime}";
		textStr = "(昨天至今天";

		tempStr = "a${startTime}b${endTime}";
		textStr = "abbc";

		// 模板字符串中占位符号（变量）的正则表达式
		String keyRegex = "\\$\\{.+?\\}";

		// 找出所有的占位符
		List<String> keyList = new ArrayList<>();
		{
			Pattern p = Pattern.compile(keyRegex);
			Matcher m = p.matcher(tempStr);

			while (m.find()) {
				keyList.add(m.group());
			}
		}

		// 找出一个字符串：模板字符串中不包含它，文本字符串中也不包含它
		String onlyStr = null;
		{
			// 相当于是一个唯一的符号，也可用其他替代
			Long currentTime = System.currentTimeMillis();
			// 必须保证字符串中不能存在
			while (tempStr.contains(currentTime.toString())) {
				currentTime++;
			}

			onlyStr = currentTime.toString();
		}

		// 找出模板字符串中，用占位符分割的字符串列表，也就是固定字符串的列表
		List<String> literalList = new ArrayList<>();
		{
			String temp = tempStr.replaceAll(keyRegex, onlyStr);

			String[] tempArray = temp.split(onlyStr);
			for (int i = 0; i < tempArray.length; i++) {
				if (i == 1 || i == tempArray.length - 1) {
					if ("".equals(tempArray[i])) {
						continue;
					}
				}

				literalList.add(tempArray[i]);
			}
		}

		// 找出文本字符串中替换值列表
		List<String> valueList = new ArrayList<>();
		{
			String text = textStr;
			for (String literal : literalList) {
				text = text.replaceFirst(literal, onlyStr);
			}

			String valueArray[] = text.split(onlyStr);
			for (int i = 0; i < valueArray.length; i++) {
				if (i == 1 || i == valueArray.length - 1) {
					if ("".equals(valueArray[i])) {
						continue;
					}
				}

				valueList.add(valueArray[i]);
			}
		}

		// 输出结果
		if (valueList.isEmpty()) {
			System.out.println("the text file format is not correct");
		} else {
			for (int i = 0; i < keyList.size(); i++) {
				System.out.println(keyList.get(i) + "\t\t" + valueList.get(i));
			}
		}
	}
}
