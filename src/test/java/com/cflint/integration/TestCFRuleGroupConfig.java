package com.cflint.integration;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.cflint.config.CFLintPluginInfo;
import com.cflint.config.CFLintPluginInfo.PluginInfoRule;
import com.cflint.config.CFLintPluginInfo.PluginInfoRule.PluginMessage;
import com.cflint.config.CFLintRuleGroups;
import com.cflint.config.CFLintRuleGroups.RuleSet;
import com.cflint.config.ConfigUtils;


public class TestCFRuleGroupConfig {

	@Test
	public void consistencyCheck(){
		final CFLintPluginInfo pluginInfo = ConfigUtils.loadDefaultPluginInfo();
		final CFLintRuleGroups ruleSets = ConfigUtils.loadDefaultRuleSets();
		System.out.println(ruleSets.getRules());
		ArrayList<String> allMesgs = new ArrayList<String>();
		for(PluginInfoRule rule:pluginInfo.getRules()){
			for(PluginMessage message:rule.getMessages()){
				allMesgs.add(message.getCode());
			}
		}
		ArrayList<String> groupedMesgs = new ArrayList<String>();
		for(RuleSet rule:ruleSets.getRules()){
			groupedMesgs.addAll(rule.getRules());
		}
		ArrayList<String> xgroupedMesgs = new ArrayList<String>(groupedMesgs);
		xgroupedMesgs.removeAll(allMesgs);
		allMesgs.removeAll(groupedMesgs);
		if(xgroupedMesgs.size() > 0 || allMesgs.size()>0){
			System.out.println("-- listed in groups, but not defined in definition ----");
			System.out.println(xgroupedMesgs);
			System.out.println("-- listed in definition, but not defined in groups ----");
			System.out.println(allMesgs);
			Assert.fail("cflint.definition.json and cflint.rulesets.json should agree");
		}
	}
}
