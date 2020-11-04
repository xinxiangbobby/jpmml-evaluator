/*
 * Copyright (c) 2016 Villu Ruusmann
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

import java.util.Set;

import org.dmg.pmml.Model;
import org.dmg.pmml.PMML;
import org.dmg.pmml.ResultFeature;

public class ModelEvaluatorFactory extends ModelManagerFactory<ModelEvaluator<?>> {

	protected ModelEvaluatorFactory(){
		super((Class)ModelEvaluator.class);
	}

	public ModelEvaluator<?> newModelEvaluator(PMML pmml, Model model){
		return newModelManager(pmml, model);
	}

	public ModelEvaluator<?> newModelEvaluator(PMML pmml, Model model, Set<ResultFeature> extraResultFeatures){
		return newModelManager(pmml, model, extraResultFeatures);
	}

	static
	public ModelEvaluatorFactory newInstance(){
		return new ModelEvaluatorFactory();
	}
}
