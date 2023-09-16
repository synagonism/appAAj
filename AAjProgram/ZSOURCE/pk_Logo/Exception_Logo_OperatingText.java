/*
 * Exception_Logo_OperatingText.java - Exception which is thrown when
 * an error occures while operating Text.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2006 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Generic Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package pk_Logo;

import javax.swing.JOptionPane;

/**
 * Exception which is thrown when an error occures
 * while operating Text.
 * @modified 2004.09.08
 * @since 2004.09.08 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Exception_Logo_OperatingText
	extends RuntimeException
{
	static final long serialVersionUID = 21L;

	/**
	 * Creates an exception.
	 *
	 * @param message
	 * 		A message describing what went wrong.
	 */
	public Exception_Logo_OperatingText(String message)
	{
		super(message);
		JOptionPane.showMessageDialog(null, message);
	}

}
