package cz.cvut.fel.filters;

import java.util.List;

import cz.cvut.fel.managers.Interpreter;
import cz.cvut.fel.models.periodic.App;

public interface Filter {
	/**
	 * Filter list of possible applications for screen output
	 * in {@link Interpreter}.
	 * 
	 * Instead of filtering applications with a loop 
	 * we could use something like this:
	 * http://code.google.com/p/lambdaj/
	 * 
	 * @param apps - List of applications to filter
	 * 
	 * @return filtered list of applications
	 */
	public List<App> filter(List<App> apps);
}
