package com.nexse.serial.network;

/**
 * An instance of a class implementing this interface has to be passed to the
 * constructor of {@link Network}. It will be used by {@link Network} to
 * forward received messages, write to a log and take action when the connection
 * is closed.
 *
 * @see Network#Network(int, Network_iface, int)
 *
 * @author Raphael Blatter (raphael@blatter.sg)
 */
public interface Network_iface {
	/**
	 * Is called to write connection information to the log. The information can
	 * either be ignored, directed to stdout or written out to a specialized
	 * field or file in the program.
	 *
	 * @param id
	 *            The <b>int</b> passed to
	 *            {@link Network#Network(int, Network_iface, int)} in the
	 *            constructor. It can be used to identify which instance (which
	 *            connection) a message comes from, when several instances of
	 *            {@link Network} are connected to the same instance of a
	 *            class implementing this interface.
	 * @param text
	 *            The text to be written into the log in human readable form.
	 *            Corresponds to information about the connection or ports.
	 */
	public void writeLog(int id, String text);

	/**
	 * Is called when sequence of bytes are received over the Serial interface.
	 * It sends the bytes (as <b>int</b>s between 0 and 255) between the two
	 * {@link Network#divider}s passed via the constructor of
	 * {@link Network} (
	 * {@link Network#Network(int, Network_iface, int)}), without the
	 * {@link Network#divider}s. Messages are only forwarded using this
	 * function, once a {@link Network#divider} has been recognized in the
	 * incoming stream.
	 *
	 * @param id
	 *            The <b>int</b> passed to
	 *            {@link Network#Network(int, Network_iface, int)} in the
	 *            constructor. It can be used to identify which instance a
	 *            message comes from, when several instances of
	 *            {@link Network} are connected to the same instance of a
	 *            class implementing this interface.
	 * @param numBytes
	 *            Number of valid bytes contained in the message
	 * @param message
	 *            Message received over the Serial interface. The complete array
	 *            of bytes (as <b>int</b>s between 0 and 255) between
	 *            {@link Network#divider} is sent (without
	 *            {@link Network#divider}s).
	 */
	public void parseInput(int id, int numBytes, int[] message);

	/**
	 * Is called when the network has been disconnected. This call can e.g. be
	 * used to show the connection status in a GUI or inform the user using
	 * other means.
	 *
	 * @param id
	 *            {@link Network#id} of the corresponding
	 *            {@link Network} instance (see {@link Network#id}).
	 */
	public void networkDisconnected(int id);
}
