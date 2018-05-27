package flinkOcl.buildEngine;

import configuration.CTType;
import flinkOcl.buildEngine.tupleVariables.VarDefinition;
import flinkOcl.buildEngine.tupleVariables.VarDefinitions;

import java.util.Collection;


@FunctionalInterface
public interface ITupleVariableAdder
{
	void addTo(Collection<VarDefinition> pResult, CTType pType, int pIndex);
}
