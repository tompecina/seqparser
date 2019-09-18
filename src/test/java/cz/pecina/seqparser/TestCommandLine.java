/* TestCommandLine.java
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
 * The source code is available from <https://github.com/tompecina/seqparser>.
 */

package cz.pecina.seqparser;

import junit.framework.TestCase;

public class TestCommandLine extends TestCase {

  public void testToString() {
    assertEquals("CommandLine", new CommandLine().toString());
  }

  public void testParameters() {
    try {
      CommandLine c = new CommandLine();
      Parameter p = new Parameter(new Option("a", "b"));
      c.addParameter(p);
      assertEquals(1, c.getParameters().size());
      assertSame(p, c.getParameters().get(0));
    } catch (ParseException e) {
      fail();
    }
  }

  public void testRemArgs() {
      CommandLine c = new CommandLine();
      String a = "a";
      c.addRemArg(a);
      assertEquals(1, c.getRemArgs().size());
      assertSame(a, c.getRemArgs().get(0));
  }
}
