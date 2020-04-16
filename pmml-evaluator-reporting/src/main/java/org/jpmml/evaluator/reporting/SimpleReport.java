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
package org.jpmml.evaluator.reporting;

import java.util.Collections;
import java.util.List;

import org.jpmml.evaluator.Report;

public class SimpleReport extends Report {

	private Entry entry = null;


	@Override
	public SimpleReport copy(){
		SimpleReport result = new SimpleReport();
		result.setEntry(getEntry());

		return result;
	}

	@Override
	public void add(Entry entry){
		setEntry(entry);
	}

	@Override
	public boolean hasEntries(){
		Entry entry = getEntry();

		return (entry != null);
	}

	@Override
	public List<Entry> getEntries(){
		Entry entry = getEntry();

		if(entry != null){
			return Collections.singletonList(entry);
		}

		return Collections.emptyList();
	}

	public Entry getEntry(){
		return this.entry;
	}

	private void setEntry(Entry entry){
		this.entry = entry;
	}
}