/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2009-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 2009-2010 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.netbeans.test.xml.schema.general.dtd;

import java.util.Calendar;

public class data
{
  private static String sDay = ( ( Calendar.getInstance( ).get( Calendar.DAY_OF_MONTH ) < 10 ) ? "0" : "" ) + Calendar.getInstance( ).get( Calendar.DAY_OF_MONTH );
  private static String sMonth = ( ( ( Calendar.getInstance( ).get( Calendar.MONTH ) + 1 ) < 10 ) ? "0" : "" ) + ( Calendar.getInstance( ).get( Calendar.MONTH ) + 1 );
  private static String sYear = ( ( Calendar.getInstance( ).get( Calendar.YEAR ) < 10 ) ? "0" : "" ) + Calendar.getInstance( ).get( Calendar.YEAR );

  public static String sIdealDocumentation =

"<!DOCTYPEhtmlPUBLIC\"-//W3C//DTDXHTML1.0Strict//EN\"\"http://www.w3"
+ ".org/TR/xhtml1/DTD/xhtml1-strict.dtd\"><html><head><title>DTDGrammarDocumentation"
+ "</title><metahttp-equiv=\"Content-Type\"content=\"text/xhtml;charset=UTF-8\"/></head"
+ "><!--Generatedon" + sDay + "." + sMonth + "." + sYear + "byNetBeansXMLmodule.--><body><hr/><h2>ElementIndex</h2"
+ "><ul><li><tt><ahref=\"#ns0:USPrice\">ns0:USPrice</a></tt></li><li><tt><ahref=\"#ns0"
+ ":billTo\">ns0:billTo</a></tt></li><li><tt><ahref=\"#ns0:city\">ns0:city</a></tt></l"
+ "i><li><tt><ahref=\"#ns0:comment\">ns0:comment</a></tt></li><li><tt><ahref=\"#ns0:it"
+ "em\">ns0:item</a></tt></li><li><tt><ahref=\"#ns0:items\">ns0:items</a></tt></li><li"
+ "><tt><ahref=\"#ns0:name\">ns0:name</a></tt></li><li><tt><ahref=\"#ns0:productName\">"
+ "ns0:productName</a></tt></li><li><tt><ahref=\"#ns0:purchaseOrder\">ns0:purchaseOrd"
+ "er</a></tt></li><li><tt><ahref=\"#ns0:quantity\">ns0:quantity</a></tt></li><li><tt"
+ "><ahref=\"#ns0:shipDate\">ns0:shipDate</a></tt></li><li><tt><ahref=\"#ns0:shipTo\">n"
+ "s0:shipTo</a></tt></li><li><tt><ahref=\"#ns0:state\">ns0:state</a></tt></li><li><t"
+ "t><ahref=\"#ns0:street\">ns0:street</a></tt></li><li><tt><ahref=\"#ns0:zip\">ns0:zip"
+ "</a></tt></li></ul><hr/><h2>ElementDetails</h2><hr/><h2><aname=\"ns0:purchaseOrde"
+ "r\"></a>ns0:purchaseOrder</h2><div>PutyourDTDDoccommenthere.</div><p><b>DeclaredA"
+ "ttributes</b></p><ul><li><tt>#IMPLIEDCDATAxsi:schemaLocation</tt></li><li><tt>#I"
+ "MPLIEDCDATAxmlns:ns0</tt></li><li><tt>#IMPLIEDCDATAxmlns:xsi</tt></li><li><tt>#I"
+ "MPLIEDCDATAorderDate</tt></li></ul><p><b>ElementContentModel</b></p><p><tt>(<ahr"
+ "ef=\"#ns0:shipTo\">ns0:shipTo</a>&nbsp;|<ahref=\"#ns0:comment\">ns0:comment</a>&nbsp"
+ ";|<ahref=\"#ns0:billTo\">ns0:billTo</a>&nbsp;|<ahref=\"#ns0:items\">ns0:items</a>)*<"
+ "/tt></p><p><b>Referencedby</b></p><p><tt></tt></p><hr/><h2><aname=\"ns0:shipTo\"><"
+ "/a>ns0:shipTo</h2><div>PutyourDTDDoccommenthere.</div><p><b>DeclaredAttributes</"
+ "b></p><ul><li><tt>#IMPLIEDCDATAcountry</tt></li></ul><p><b>ElementContentModel</"
+ "b></p><p><tt>(<ahref=\"#ns0:city\">ns0:city</a>&nbsp;|<ahref=\"#ns0:name\">ns0:name<"
+ "/a>&nbsp;|<ahref=\"#ns0:state\">ns0:state</a>&nbsp;|<ahref=\"#ns0:street\">ns0:stree"
+ "t</a>&nbsp;|<ahref=\"#ns0:zip\">ns0:zip</a>)*</tt></p><p><b>Referencedby</b></p><p"
+ "><tt><ahref=\"#ns0:purchaseOrder\">ns0:purchaseOrder</a></tt></p><hr/><h2><aname=\""
+ "ns0:name\"></a>ns0:name</h2><div>PutyourDTDDoccommenthere.</div><p><b>ElementCont"
+ "entModel</b></p><p><tt>EMPTY</tt></p><p><b>Referencedby</b></p><p><tt><ahref=\"#n"
+ "s0:shipTo\">ns0:shipTo</a>,<ahref=\"#ns0:billTo\">ns0:billTo</a></tt></p><hr/><h2><"
+ "aname=\"ns0:street\"></a>ns0:street</h2><div>PutyourDTDDoccommenthere.</div><p><b>"
+ "ElementContentModel</b></p><p><tt>EMPTY</tt></p><p><b>Referencedby</b></p><p><tt"
+ "><ahref=\"#ns0:shipTo\">ns0:shipTo</a>,<ahref=\"#ns0:billTo\">ns0:billTo</a></tt></p"
+ "><hr/><h2><aname=\"ns0:city\"></a>ns0:city</h2><div>PutyourDTDDoccommenthere.</div"
+ "><p><b>ElementContentModel</b></p><p><tt>EMPTY</tt></p><p><b>Referencedby</b></p"
+ "><p><tt><ahref=\"#ns0:shipTo\">ns0:shipTo</a>,<ahref=\"#ns0:billTo\">ns0:billTo</a><"
+ "/tt></p><hr/><h2><aname=\"ns0:state\"></a>ns0:state</h2><div>PutyourDTDDoccommenth"
+ "ere.</div><p><b>ElementContentModel</b></p><p><tt>EMPTY</tt></p><p><b>Referenced"
+ "by</b></p><p><tt><ahref=\"#ns0:shipTo\">ns0:shipTo</a>,<ahref=\"#ns0:billTo\">ns0:bi"
+ "llTo</a></tt></p><hr/><h2><aname=\"ns0:zip\"></a>ns0:zip</h2><div>PutyourDTDDoccom"
+ "menthere.</div><p><b>ElementContentModel</b></p><p><tt>EMPTY</tt></p><p><b>Refer"
+ "encedby</b></p><p><tt><ahref=\"#ns0:shipTo\">ns0:shipTo</a>,<ahref=\"#ns0:billTo\">n"
+ "s0:billTo</a></tt></p><hr/><h2><aname=\"ns0:billTo\"></a>ns0:billTo</h2><div>Putyo"
+ "urDTDDoccommenthere.</div><p><b>DeclaredAttributes</b></p><ul><li><tt>#IMPLIEDCD"
+ "ATAcountry</tt></li></ul><p><b>ElementContentModel</b></p><p><tt>(<ahref=\"#ns0:c"
+ "ity\">ns0:city</a>&nbsp;|<ahref=\"#ns0:name\">ns0:name</a>&nbsp;|<ahref=\"#ns0:state"
+ "\">ns0:state</a>&nbsp;|<ahref=\"#ns0:street\">ns0:street</a>&nbsp;|<ahref=\"#ns0:zip"
+ "\">ns0:zip</a>)*</tt></p><p><b>Referencedby</b></p><p><tt><ahref=\"#ns0:purchaseOr"
+ "der\">ns0:purchaseOrder</a></tt></p><hr/><h2><aname=\"ns0:comment\"></a>ns0:comment"
+ "</h2><div>PutyourDTDDoccommenthere.</div><p><b>ElementContentModel</b></p><p><tt"
+ ">EMPTY</tt></p><p><b>Referencedby</b></p><p><tt><ahref=\"#ns0:purchaseOrder\">ns0:"
+ "purchaseOrder</a>,<ahref=\"#ns0:item\">ns0:item</a></tt></p><hr/><h2><aname=\"ns0:i"
+ "tems\"></a>ns0:items</h2><div>PutyourDTDDoccommenthere.</div><p><b>ElementContent"
+ "Model</b></p><p><tt>(<ahref=\"#ns0:item\">ns0:item</a>)*</tt></p><p><b>Referencedb"
+ "y</b></p><p><tt><ahref=\"#ns0:purchaseOrder\">ns0:purchaseOrder</a></tt></p><hr/><"
+ "h2><aname=\"ns0:item\"></a>ns0:item</h2><div>PutyourDTDDoccommenthere.</div><p><b>"
+ "DeclaredAttributes</b></p><ul><li><tt>#IMPLIEDCDATApartNum</tt></li></ul><p><b>E"
+ "lementContentModel</b></p><p><tt>(<ahref=\"#ns0:shipDate\">ns0:shipDate</a>&nbsp;|"
+ "<ahref=\"#ns0:comment\">ns0:comment</a>&nbsp;|<ahref=\"#ns0:USPrice\">ns0:USPrice</a"
+ ">&nbsp;|<ahref=\"#ns0:quantity\">ns0:quantity</a>&nbsp;|<ahref=\"#ns0:productName\">"
+ "ns0:productName</a>)*</tt></p><p><b>Referencedby</b></p><p><tt><ahref=\"#ns0:item"
+ "s\">ns0:items</a></tt></p><hr/><h2><aname=\"ns0:productName\"></a>ns0:productName</"
+ "h2><div>PutyourDTDDoccommenthere.</div><p><b>ElementContentModel</b></p><p><tt>E"
+ "MPTY</tt></p><p><b>Referencedby</b></p><p><tt><ahref=\"#ns0:item\">ns0:item</a></t"
+ "t></p><hr/><h2><aname=\"ns0:quantity\"></a>ns0:quantity</h2><div>PutyourDTDDoccomm"
+ "enthere.</div><p><b>ElementContentModel</b></p><p><tt>EMPTY</tt></p><p><b>Refere"
+ "ncedby</b></p><p><tt><ahref=\"#ns0:item\">ns0:item</a></tt></p><hr/><h2><aname=\"ns"
+ "0:USPrice\"></a>ns0:USPrice</h2><div>PutyourDTDDoccommenthere.</div><p><b>Element"
+ "ContentModel</b></p><p><tt>EMPTY</tt></p><p><b>Referencedby</b></p><p><tt><ahref"
+ "=\"#ns0:item\">ns0:item</a></tt></p><hr/><h2><aname=\"ns0:shipDate\"></a>ns0:shipDat"
+ "e</h2><div>PutyourDTDDoccommenthere.</div><p><b>ElementContentModel</b></p><p><t"
+ "t>EMPTY</tt></p><p><b>Referencedby</b></p><p><tt><ahref=\"#ns0:item\">ns0:item</a>"
+ "</tt></p></body></html>";

}
