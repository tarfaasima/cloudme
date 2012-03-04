package org.cloudme.notepad.jsp.tags;

import lombok.val;

public class Tags {

	/**
	 * Joins the provided items to a {@link String} by calling
	 * {@link Object#toString()} on each item. <tt>null</tt> items are ignored.
	 * 
	 * @param input
	 *            The items.
	 * @param before
	 *            The {@link String} that is concatenated before each item.
	 * @param after
	 *            The {@link String} that is concatenated after each item.
	 * @param between
	 *            The {@link String} that is concatenated between items.
	 * @return A concatenated {@link String}.
	 * @throws NullPointerException
	 *             if <tt>input</tt> is <tt>null</tt>
	 */
	public static String join(Iterable<?> input, String before, String after, String between) {
		val sb = new StringBuilder();
		if (input != null) {
			boolean isFirst = true;
			for (val object : input) {
				if (object != null) {
					if (isFirst) {
						isFirst = false;
					} else {
						sb.append(between);
					}
					sb.append(before);
					sb.append(object.toString());
					sb.append(after);
				}
			}
		}
		return sb.toString();
	}

	public static String escapeHtml(String str) {
        return new HtmlFormatter().format(str);
	}
}
