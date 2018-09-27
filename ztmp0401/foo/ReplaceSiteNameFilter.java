package com.foo;

/**
 * Filter that replaces all occurrences of the target string with the
 * replacement string. The target and replacement strings are provided as init
 * parameters to the filter in the web.xml file.
 */
public class ReplaceSiteNameFilter extends ModificationFilter {
	
	private boolean isCaseInsensitive = false;

	/** The string that needs replacement. */
	public String getTarget() {
		return getInitParameter("target");
	}

	/** The string that replaces the target. */
	public String getReplacement() {
		return getInitParameter("replacement");
	}

	/**
	 * Returns the init parameter value specified by 'param' or null if it is
	 * not present or an empty string
	 */
	private String getInitParameter(String param) {
		String value = config.getInitParameter(param);
		if ((value == null) || (value.trim().equals(""))) {
			value = null;
		}
		return value;
	}

	/**
	 * Sets whether the search for the target string will be case sensitive.
	 */
	public void setCaseInsensitive(boolean flag) {
		isCaseInsensitive = flag;
	}

	/**
	 * Returns true or false, indicating if the search for the target string is
	 * case sensitive.
	 */
	public boolean isCaseInsensitive() {
		return (isCaseInsensitive);
	}

	/**
	 * Replaces all strings matching the target string with the replacement
	 * string.
	 */
	public String doModification(String orig) {
		if ((getTarget() == null) || (getReplacement() == null)) {
			return (orig);
		} else {
			String target = getTarget();
			if (isCaseInsensitive()) {
				target = "(?i)" + target;
			}
			String replacement = getReplacement();
			return (orig.replaceAll(target, replacement));
		}
	}
}
