package com.example.credit.check.executor;

import com.example.credit.check.checks.factory.CheckFactory;
import com.example.credit.check.data.CheckResult;

public interface CheckExecutor {
	
	Iterable<CheckResult> executeChecks(CheckFactory checkFactory);
	
}
