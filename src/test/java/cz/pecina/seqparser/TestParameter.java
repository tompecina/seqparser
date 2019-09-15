/* TestParameter.java
 *
 * Copyright (C) 2015-19, Tomas Pecina <tomas@pecina.cz>
 *
 * This file is part of cz.pecina.pdf, a suite of PDF processing applications.
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

public class TestParameter extends TestCase {

  public void testGetOption() {
    try {
      Option o = new Option("a", "b");
      assertSame(o, new Parameter(o).getOption());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testSubParameters() {
    try {
      Parameter p = new Parameter(new Option("a", "b"));
      SubParameter s1 = new SubParameter("a", ParameterType.String);
      SubParameter s2 = new SubParameter("99", ParameterType.Integer);
      p.addSubParameter(s1);
      p.addSubParameter(s2);
      assertEquals(2, p.getSubParameters().size());
      assertEquals(2, p.getNumSubParameters());
      assertSame(s1, p.getSubParameters().get(0));
      assertSame(s2, p.getSubParameters().get(1));
      assertSame(s1, p.getSubParameter(0));
      assertSame(s2, p.getSubParameter(1));
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testKwSubParameters() {
    try {
      Parameter p = new Parameter(new Option("a", "b"));
      SubParameter s1 = new SubParameter("a", ParameterType.String);
      SubParameter s2 = new SubParameter("99", ParameterType.Integer);
      p.addKwSubParameter("x", s1);
      p.addKwSubParameter("y", s2);
      assertEquals(2, p.getKwSubParameters().size());
      assertEquals(2, p.getNumKwSubParameters());
      assertSame(s1, p.getKwSubParameters().get("x"));
      assertSame(s2, p.getKwSubParameters().get("y"));
      assertSame(s1, p.getKwSubParameter("x"));
      assertSame(s2, p.getKwSubParameter("y"));
      assertTrue(p.hasKwSubParameter("x"));
      assertTrue(p.hasKwSubParameter("y"));
      assertFalse(p.hasKwSubParameter("z"));
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }
}
