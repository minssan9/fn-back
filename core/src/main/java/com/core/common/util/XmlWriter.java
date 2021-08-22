package com.core.common.util;

import java.io.IOException;
import java.io.Writer;

public class XmlWriter {

	private final Writer _out;

	public XmlWriter(Writer out) {

		_out = out;
	}

	public void beginSheet() throws IOException {
		_out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		_out.write("<labels>\n");
	}

	public void endSheet() throws IOException {
		_out.write("</labels>");
	}

	public void beginSheet(String labelNm, String labelPrinter, String qty) throws IOException {
		_out.write("<?xml version=\"1.0\" standalone=\"no\"?>\n");
		_out.write("<labels _FORMAT=\""+labelNm+"\"");
		_out.write(" _PRINTERNAME=\""+labelPrinter+"\" _QUANTITY=\""+qty+"\">\n");
	}

	/**
	 * Insert a new row
	 *
	 * @param rownum
	 *            0-based row number
	 */
	public void insertRow(int rownum) throws IOException {
		//_out.write("<row r=\"" + (rownum + 1) + "\">\n");
		_out.write("  <label>\n");
	}

	public void insertRow2D(int rownum,String lQty) throws IOException {
		_out.write("  <label _FORMAT='SMC_PH_2D' _QUANTITY='"+lQty+"'>\n");
	}

	/**
	 * Insert row end marker
	 */
	public void endRow() throws IOException {
		//_out.write("</row>\n");
		_out.write("  </label>\n");
	}

	public void createCell(int columnIndex, String value, int styleIndex)
			throws IOException {
		//String ref = new CellReference(_rownum, columnIndex).formatAsString();
		//ref = ref.replaceAll("\\$", "");
		if(value == null)
			value = "";
		_out.write("<c r=\"" + "ss" + "\" t=\"inlineStr\"");
		if (styleIndex != -1)
			_out.write(" s=\"" + styleIndex + "\"");
		_out.write(">");
		_out.write("<is><t><![CDATA[" + value + "]]></t></is>");
		_out.write("</c>");

	}

	public void createCell(int columnIndex, String value) throws IOException {
		createCell(columnIndex, value, -1);
	}

	public void createCell(String key, String value)
			throws IOException {
		if(value == null)
			value = "";
		_out.write("    <variable name=\"" + key.toUpperCase() + "\">");
		_out.write("<![CDATA[" + value + "]]>");
		//_out.write("" + value +"");
		_out.write("</variable>\n");

	}

	public void createCell2D(String key, String value)
			throws IOException {
		if(value == null)
			value = "";

		if(key.equals("lQty")) return;

		_out.write("    <variable name=\"" + key.replace("aDBar", "ADBar") + "\">");
		_out.write("<![CDATA[" + value + "]]>");
		//_out.write("" + value +"");
		_out.write("</variable>\n");

	}

}
