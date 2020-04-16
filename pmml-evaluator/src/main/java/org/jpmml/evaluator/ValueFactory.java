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
package org.jpmml.evaluator;

import java.io.Serializable;

abstract
public class ValueFactory<V extends Number> implements Serializable {

	/**
	 * <p>
	 * Creates a value, which is "silently" set to the zero value.
	 * </p>
	 */
	abstract
	public Value<V> newValue();

	/**
	 * <p>
	 * Creates a value, which is "vocally" set to the specified value.
	 * </p>
	 */
	abstract
	public Value<V> newValue(Number value);

	/**
	 * <p>
	 * Creates a value, which is "vocally" set to the specified value.
	 * </p>
	 */
	abstract
	public Value<V> newValue(String value);

	abstract
	public Vector<V> newVector(int capacity);
}