package org.lunifera.example.vaadin.osgi.contentprovider;

public interface ISharkyController {

	/**
	 * Is called to send a left command to sharky.
	 * 
	 * @param intensity
	 *            The intensity how strong sharky will fly to left. An integer
	 *            between 0 and 10.
	 * @return all parameters
	 */
	Parameter foreward(int intensity);

	/**
	 * Is called to send a left command to sharky.
	 * 
	 * @param intensity
	 *            the intensity how strong sharky will fly to left. An integer
	 *            between 0 and 10.
	 * @return all parameters
	 */
	Parameter left(int intensity);

	/**
	 * Is called to send a top command to sharky.
	 * 
	 * @param intensity
	 *            the intensity how strong sharky will fly to top. An integer
	 *            between 0 and 10.
	 * @return all parameters
	 */
	Parameter top(int intensity);

	/**
	 * Is called to send a right command to sharky.
	 * 
	 * @param intensity
	 *            the intensity how strong sharky will fly to right. An integer
	 *            between 0 and 10.
	 * @return all parameters
	 */
	Parameter right(int intensity);

	/**
	 * Is called to send a bottom command to sharky.
	 * 
	 * @param intensity
	 *            the intensity how strong sharky will fly to bottom. An integer
	 *            between 0 and 10.
	 * @return all parameters
	 */
	Parameter bottom(int intensity);

	/**
	 * Stops sharky, tries to balance it horizontally and resets all parameters.
	 * 
	 * @return all parameters
	 */
	Parameter stop();

	/**
	 * Returns all parameters used by sharky.
	 * 
	 * @return
	 */
	Parameter getParameter();

	/**
	 * Provides access to the current operation settings of sharky.
	 */
	public interface Parameter {

		/**
		 * Returns the intensity how strong sharky will fly to top. An integer
		 * between 0 and 10.
		 * 
		 * @return The intensity
		 */
		int getLeft();

		/**
		 * Returns the intensity how strong sharky will fly to top. An integer
		 * between 0 and 10.
		 * 
		 * @return The intensity
		 */
		int getTop();

		/**
		 * Returns the intensity how strong sharky will fly to top. An integer
		 * between 0 and 10.
		 * 
		 * @return The intensity
		 */
		int getRight();

		/**
		 * Returns the intensity how strong sharky will fly to top. An integer
		 * between 0 and 10.
		 * 
		 * @return The intensity
		 */
		int getBottom();

	}

}
