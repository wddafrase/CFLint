package com.cflint.plugins;

import java.util.HashMap;
import java.util.Map;

import com.cflint.BugList;

import cfml.parsing.cfscript.CFExpression;
import cfml.parsing.cfscript.script.CFScriptStatement;
import net.htmlparser.jericho.Element;

/**
 * Lint Rule Plugins can extend this adapter instead of implementing all the
 * methods of CFLintScanner (and CFLintStructureListener)
 *
 * @author eberlyrh
 *
 */
public class CFLintScannerAdapter implements CFLintScanner, CFLintStructureListener {

	Map<String, String> params = new HashMap<String, String>();

	public CFLintScannerAdapter() {
		super();
	}

	@Override
	public void expression(final CFExpression expression, final Context context, final BugList bugs) {
	}

	@Override
	public void element(final Element element, final Context context, final BugList bugs) {
	}

	@Override
	public void expression(final CFScriptStatement expression, final Context context, final BugList bugs) {
	}

	@Override
	public void setParameter(final String name, final String value) {
		if (name != null) {
			params.put(name.toLowerCase(), value);
			params.put(name, value);
		}
	}

	public String getParameter(final String name) {
		if (name != null) {
			return params.get(name.toLowerCase());
		}
		return null;
	}

	public int currentLine(final CFExpression expression, final Context context) {
		return expression.getLine() + context.startLine() - 1;
	}

	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * Default implementation does nothing
	 */
	@Override
	public void startFile(final String fileName, final BugList bugs) {
	}

	/**
	 * Default implementation does nothing
	 */
	@Override
	public void endFile(final String fileName, final BugList bugs) {
	}

	/**
	 * Default implementation does nothing
	 */
	@Override
	public void startComponent(final Context context, final BugList bugs) {
	}

	/**
	 * Default implementation does nothing
	 */
	@Override
	public void endComponent(final Context context, final BugList bugs) {
	}

	/**
	 * Default implementation does nothing
	 */
	@Override
	public void startFunction(final Context context, final BugList bugs) {
	}

	/**
	 * Default implementation does nothing
	 */
	@Override
	public void endFunction(final Context context, final BugList bugs) {
	}
}
