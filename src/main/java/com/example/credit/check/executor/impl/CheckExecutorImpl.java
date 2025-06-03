package com.example.credit.check.executor.impl;

import org.springframework.stereotype.Component;

import com.example.credit.check.checks.Check;
import com.example.credit.check.checks.factory.CheckFactory;
import com.example.credit.check.data.CheckResult;
import com.example.credit.check.executor.CheckExecutor;

import java.util.List;
import java.util.ArrayList;

@Component
public class CheckExecutorImpl implements CheckExecutor {
	
	@Override
	public Iterable<CheckResult> executeChecks(CheckFactory checkFactory) {
		
		List<CheckResult> results = new ArrayList<>();
		
		Iterable<Check> checks = checkFactory.createChecks();
		for (Check check : checks) {
			CheckResult currentCheckResult = check.check();
			results.add(currentCheckResult);
		}
		
		return results;
	}
	
}
