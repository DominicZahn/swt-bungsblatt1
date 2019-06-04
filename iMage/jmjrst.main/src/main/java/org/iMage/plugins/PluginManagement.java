package org.iMage.plugins;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ServiceLoader;

/**
 * Knows all available plug-ins and is responsible for using the service loader
 * API to detect them.
 *
 * @author Dominik Fuchss
 */
public final class PluginManagement {

	/**
	 * No constructor for utility class.
	 */
	private PluginManagement() {
		throw new IllegalAccessError();
	}

	/**
	 * Return an {@link Iterable} Object with all available {@link PluginForJmjrst
	 * PluginForJmjrsts} sorted according to the length of their class names
	 * (shortest first).
	 *
	 * @return an {@link Iterable} Object containing all available plug-ins
	 *         alphabetically sorted according to their class names.
	 */
	public static Iterable<PluginForJmjrst> getPlugins() {
		ArrayList<PluginForJmjrst> pluginList = new ArrayList<PluginForJmjrst>();
		ServiceLoader<PluginForJmjrst> serviceLoader = ServiceLoader.load(PluginForJmjrst.class);
		for (PluginForJmjrst plugin : serviceLoader) {
			pluginList.add(plugin);
		}
		pluginList.sort(new Comparator<PluginForJmjrst>() {
			@Override
			public int compare(PluginForJmjrst pfj1, PluginForJmjrst pfj2) {
				return pfj1.compareTo(pfj2);
			}
		});
		return pluginList;
	}
}
