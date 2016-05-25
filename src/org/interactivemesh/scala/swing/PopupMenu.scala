package org.interactivemesh.scala.swing
/*

License (following the Scala license)

Copyright (c) 2010
August Lammersdorf, InteractiveMesh e.K.
Kolomanstrasse 2a, 85737 Ismaning, Germany / Munich Area
www.InteractiveMesh.com/org
 
All rights reserved.

Permission to use, copy, modify, and distribute this software in source
or binary form for any purpose with or without fee is hereby granted,
provided that the following conditions are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.

 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.

 3. Neither the name of the copyright holder nor the names of its contributors
    may be used to endorse or promote products derived from this
    software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
SUCH DAMAGE.

*/

import javax.swing.{JComponent, JPopupMenu}
import scala.swing.{Component, SequentialContainer}
import scala.collection.mutable.Buffer

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
object PopupMenu {
  def defaultLightWeightPopupEnabled: Boolean = JPopupMenu.getDefaultLightWeightPopupEnabled
  def defaultLightWeightPopupEnabled_=(b: Boolean) = JPopupMenu.setDefaultLightWeightPopupEnabled(b)
}

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
class PopupMenu extends Component with SequentialContainer.Wrapper {
  override lazy val peer: JPopupMenu = new JPopupMenu with SuperMixin 
  
  override val contents = new PopupMenuContent
  
  protected class PopupMenuContent extends Content {
    def addSeparator() = peer.addSeparator
  }
    
  private val ClientKey = "scala.swingWrapper"

  def borderPainted: Boolean = peer.isBorderPainted
  def borderPainted_=(b: Boolean) = peer.setBorderPainted(b)
  
  def invoker: Component = 
    peer.getInvoker.asInstanceOf[JComponent].getClientProperty(ClientKey).asInstanceOf[Component]
  def invoker_=(c: Component) = peer.setInvoker(if(c eq null) null else c.peer)

  def label: String = peer.getLabel
  def label_=(s: String) = peer.setLabel(s)

  def lightWeightPopupEnabled: Boolean = peer.isLightWeightPopupEnabled
  def lightWeightPopupEnabled_=(b: Boolean) = peer.setLightWeightPopupEnabled(b)
  
  // Missing in UIElement ?
  def location_=(l: (Int, Int)) = peer.setLocation(l._1, l._2)
  
  def show(invoker: Component, x: Int, y: Int) = peer.show(invoker.peer, x, y)
  
  // More TODO
}
