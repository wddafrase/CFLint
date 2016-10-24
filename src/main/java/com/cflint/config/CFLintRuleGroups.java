package com.cflint.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

//@XmlRootElement(name = "CFLint-Plugin")
@JsonInclude(Include.NON_NULL)
public class CFLintRuleGroups {

	List<CFLintRuleGroups.RuleSet> rules = new ArrayList<CFLintRuleGroups.RuleSet>();

	public List<CFLintRuleGroups.RuleSet> getRules() {
		return rules;
	}

	@XmlElement(name = "ruleSet")
	public void setRules(final List<CFLintRuleGroups.RuleSet> rules) {
		this.rules = rules;
	}

	public CFLintRuleGroups.RuleSet getGroupByRuleName(final String ruleName) {
		for (final CFLintRuleGroups.RuleSet ruleSet : rules) {
			if (ruleName != null && ruleName.equals(ruleSet.getName())) {
				return ruleSet;
			}
		}
		return null;
	}

	@JsonInclude(Include.NON_NULL)
	public static class RuleSet {

		String name;
		String description;
		List<String> rules = new ArrayList<String>();

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public List<String> getRules() {
			return rules;
		}
	
		@XmlAttribute(name = "name")
		public void setName(final String name) {
			this.name = name;
		}
		@XmlAttribute(name = "description")
		public void setDescription(final String description) {
			this.description = description;
		}
		@XmlElement(name = "rules")
		public void setRules(final List<String> rules) {
			this.rules = rules;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final RuleSet other = (RuleSet) obj;
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}
		public String toString(){
			try {
				return ConfigUtils.marshalJson(this);
			} catch (Exception e) {
				return "";
			}
		}
	}
	
	public String toString(){
		try {
			return ConfigUtils.marshalJson(this);
		} catch (Exception e) {
			return "";
		}
	}
}
