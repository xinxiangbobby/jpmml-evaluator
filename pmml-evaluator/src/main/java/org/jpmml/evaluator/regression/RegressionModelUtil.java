/*
 * Copyright (c) 2017 Villu Ruusmann
 *
 * This file is part of JPMML-Evaluator
 *
 * JPMML-Evaluator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-Evaluator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-Evaluator.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpmml.evaluator.regression;

import java.util.Iterator;

import org.dmg.pmml.regression.RegressionModel;
import org.jpmml.evaluator.Numbers;
import org.jpmml.evaluator.Value;
import org.jpmml.evaluator.ValueMap;
import org.jpmml.evaluator.ValueUtil;

public class RegressionModelUtil {

	private RegressionModelUtil(){
	}

	static
	public <K, V extends Number> ValueMap<K, V> computeBinomialProbabilities(RegressionModel.NormalizationMethod normalizationMethod, ValueMap<K, V> values){

		if(values.size() != 2){
			throw new IllegalArgumentException();
		}

		Iterator<Value<V>> valueIt = values.iterator();

		Value<V> firstValue = valueIt.next();

		// The probability of the first category is calculated
		normalizeBinaryLogisticClassificationResult(normalizationMethod, firstValue);

		Value<V> secondValue = valueIt.next();

		// The probability of the second category is obtained by subtracting the probability of the first category from 1.0
		secondValue.residual(firstValue);

		return values;
	}

	static
	public <K, V extends Number> ValueMap<K, V> computeMultinomialProbabilities(RegressionModel.NormalizationMethod normalizationMethod, ValueMap<K, V> values){

		if(values.size() < 2){
			throw new IllegalArgumentException();
		}

		switch(normalizationMethod){
			case NONE:
				{
					Value<V> sum = null;

					Iterator<Value<V>> valueIt = values.iterator();
					for(int i = 0, max = values.size() - 1; i < max; i++){
						Value<V> value = valueIt.next();

						if(sum == null){
							sum = value.copy();
						} else

						{
							sum.add(value);
						}
					}

					Value<V> lastValue = valueIt.next();

					lastValue.residual(sum);
				}
				break;
			// XXX: Non-standard behaviour
			case LOGIT:
				{
					for(Value<V> value : values){
						value.inverseLogit();
					}
				}
				// Falls through
			case SIMPLEMAX:
				{
					ValueUtil.normalizeSimpleMax(values);
				}
				break;
			case SOFTMAX:
				{
					ValueUtil.normalizeSoftMax(values);
				}
				break;
			default:
				throw new IllegalArgumentException();
		}

		return values;
	}

	static
	public <K, V extends Number> ValueMap<K, V> computeOrdinalProbabilities(RegressionModel.NormalizationMethod normalizationMethod, ValueMap<K, V> values){

		if(values.size() < 2){
			throw new IllegalArgumentException();
		}

		switch(normalizationMethod){
			case NONE:
			case LOGIT:
			case PROBIT:
			case CLOGLOG:
			case LOGLOG:
			case CAUCHIT:
				{
					Value<V> sum = null;

					Iterator<Value<V>> valueIt = values.iterator();
					for(int i = 0, max = values.size() - 1; i < max; i++){
						Value<V> value = valueIt.next();

						normalizeBinaryLogisticClassificationResult(normalizationMethod, value);

						if(sum == null){
							sum = value.copy();
						} else

						{
							value.subtract(sum);

							sum.add(value);
						}
					}

					Value<V> lastValue = valueIt.next();

					lastValue.residual(sum);
				}
				break;
			default:
				throw new IllegalArgumentException();
		}

		return values;
	}

	static
	public <V extends Number> Value<V> normalizeRegressionResult(RegressionModel.NormalizationMethod normalizationMethod, Value<V> value){

		switch(normalizationMethod){
			case NONE:
				return value;
			case SOFTMAX:
			case LOGIT:
				return value.inverseLogit();
			case EXP:
				return value.exp();
			case PROBIT:
				return value.inverseProbit();
			case CLOGLOG:
				return value.inverseCloglog();
			case LOGLOG:
				return value.inverseLoglog();
			case CAUCHIT:
				return value.inverseCauchit();
			default:
				throw new IllegalArgumentException();
		}
	}

	static
	public <V extends Number> Value<V> normalizeBinaryLogisticClassificationResult(RegressionModel.NormalizationMethod normalizationMethod, Value<V> value){

		switch(normalizationMethod){
			case NONE:
				return value.restrict(Numbers.DOUBLE_ZERO, Numbers.DOUBLE_ONE);
			case LOGIT:
				return value.inverseLogit();
			case PROBIT:
				return value.inverseProbit();
			case CLOGLOG:
				return value.inverseCloglog();
			case LOGLOG:
				return value.inverseLoglog();
			case CAUCHIT:
				return value.inverseCauchit();
			default:
				throw new IllegalArgumentException();
		}
	}
}