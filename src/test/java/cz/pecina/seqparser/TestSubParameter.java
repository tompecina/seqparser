/* TestSubParameter.java
 *
 * Copyright (C) 2019, Tomas Pecina <tomas@pecina.cz>
 *
 * This file is part of cz.pecina.seqparser, a sequential command-line parser.
 *
 * This application is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * The source code is available from <https://github.com/tompecina/pdf>.
 */

package cz.pecina.seqparser;

import junit.framework.TestCase;

public class TestSubParameter extends TestCase {

  public void testToString() {
    try {
      assertEquals("SubParameter", new SubParameter("", ParameterType.String).toString());
    } catch (ParseException e) {
      fail();
    }
  }

  public void testSubParameter() {
    try {
      new SubParameter(null, ParameterType.String);
      fail("null string");
    } catch (ParseException expected) { }
    try {
      new SubParameter("a", ParameterType.Integer);
      fail("invalid string");
    } catch (ParseException expected) { }
    try {
      new SubParameter("42", ParameterType.Integer);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetSubOption() {
    try {
      assertSame(ParameterType.String, new SubParameter("a", ParameterType.String).getSubOption());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testIsEmpty() {
    try {
      assertTrue(new SubParameter("", ParameterType.String).isEmpty());
      assertFalse(new SubParameter(" ", ParameterType.String).isEmpty());
      assertFalse(new SubParameter("0", ParameterType.String).isEmpty());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetAsString() {
    try {
      assertEquals("a", new SubParameter("a", ParameterType.String).getAsString());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetAsInt() {
    try {
      assertEquals(-18, new SubParameter("-18", ParameterType.Integer).getAsInt());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetAsFloat() {
    try {
      assertEquals(4.5f, new SubParameter("4.5", ParameterType.Float).getAsFloat());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetAsDouble() {
    try {
      assertEquals(-18.5, new SubParameter("-18.5", ParameterType.Double).getAsDouble());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }
}
