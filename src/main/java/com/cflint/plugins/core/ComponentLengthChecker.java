package com.cflint.plugins.core;

import com.cflint.BugInfo;
import com.cflint.BugList;
import com.cflint.plugins.CFLintScannerAdapter;
import com.cflint.plugins.Context;

import cfml.parsing.cfscript.CFExpression;
import cfml.parsing.cfscript.script.CFCompoundStatement;
import cfml.parsing.cfscript.script.CFScriptStatement;
import net.htmlparser.jericho.Element;
import ro.fortsoft.pf4j.Extension;

@Extension
public class ComponentLengthChecker extends CFLintScannerAdapter {
	final int LENGTH_THRESHOLD = 500;
	final String severity = "INFO";

	@Override
	public void expression(final CFExpression expression, final Context context, final BugList bugs) {
	}

	@Override
	public void expression(final CFScriptStatement expression, final Context context, final BugList bugs) {
		if (expression instanceof CFCompoundStatement) {
			final CFCompoundStatement component = (CFCompoundStatement) expression;
			final String decompile = component.Decompile(1);
			final String[] lines = decompile.split("\\n");

			checkSize(context, lines.length, bugs);
		}
	}

	@Override
	public void element(final Element element, final Context context, final BugList bugs) {
		final String elementName = element.getName();

		if (elementName.equals("cfcomponent")) {
			// this includes whitespace-change it
			final int total = element.getAllStartTags().size();

			checkSize(context, total, bugs);
		}
	}

	protected void checkSize(final Context context, final int linesLength, final BugList bugs) {
		final String lengthThreshold = getParameter("length");
		int length = LENGTH_THRESHOLD;

		if (lengthThreshold != null) {
			length = Integer.parseInt(lengthThreshold);
		}

		if (linesLength > length) {
			bugs.add(new BugInfo.BugInfoBuilder().setLine(1).setMessageCode("EXCESSIVE_COMPONENT_LENGTH")
					.setSeverity(severity).setFilename(context.getFilename())
					.setMessage("Component " + context.getComponentName() + " is " + Integer.toString(linesLength)
							+ " lines. Should be less than " + Integer.toString(length) + " lines.")
					.build());
		}
	}
}