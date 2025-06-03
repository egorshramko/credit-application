package com.example.credit.check.checks.factory;

import com.example.credit.check.checks.Check;

public interface CheckFactory {
	Iterable<Check> createChecks();
}
