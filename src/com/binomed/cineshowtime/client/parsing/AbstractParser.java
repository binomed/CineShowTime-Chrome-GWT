package com.binomed.cineshowtime.client.parsing;

import com.binomed.cineshowtime.client.cst.XmlGramarNearResult;
import com.google.gwt.http.client.URL;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

public class AbstractParser implements XmlGramarNearResult {

	protected static String getString(Node node, String attr) {
		String result = null;
		if (node != null && ((Element) node).getAttribute(attr) != null) {
			result = URL.decode(((Element) node).getAttribute(attr));
		}
		return result;
	}

	protected static double getDouble(Node node, String attr) {
		double result = -1;
		String value = getString(node, attr);
		if (value != null) {
			result = Double.valueOf(value);
		}
		return result;
	}

	protected static long getLong(Node node, String attr) {
		long result = -1;
		String value = getString(node, attr);
		if (value != null) {
			result = Long.valueOf(value);
		}
		return result;
	}

	protected static int getInt(Node node, String attr) {
		int result = -1;
		String value = getString(node, attr);
		if (value != null) {
			result = Integer.valueOf(value);
		}
		return result;
	}

	protected static boolean getBoolean(Node node, String attr) {
		boolean result = false;
		String value = getString(node, attr);
		if (value != null) {
			result = Boolean.valueOf(value);
		}
		return result;
	}

}
