package flink.ocl.build.engine;

import configuration.CTType;
import flink.ocl.build.engine.tuple.variables.VarDefinition;

import java.util.Collection;


@FunctionalInterface
public interface ITupleVariableAdder
{
	void addTo(Collection<VarDefinition> pResult, CTType pType, int pIndex);
}
