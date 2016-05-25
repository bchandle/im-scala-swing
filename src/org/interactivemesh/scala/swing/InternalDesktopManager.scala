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

import javax.swing.{DefaultDesktopManager, DesktopManager, JComponent, JInternalFrame}
import scala.swing.Component


/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
object InternalDesktopManager {
  // Wraps an abritrary desktop manager
  def wrap(dm: DesktopManager): InternalDesktopManager = {	  
    new InternalDesktopManager { val managerPeer = dm }
  }
}

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
trait InternalDesktopManager {
	
  // Wrapped interface javax.swing.DesktopManager
  val managerPeer: DesktopManager 
  
  // Fully implemented 
  
  def activateFrame(f: InternalFrame) = managerPeer.activateFrame(f.peer )

  def beginDraggingFrame(c: Component) = managerPeer.beginDraggingFrame(c.peer)

  def beginResizingFrame(c: Component) = managerPeer.beginResizingFrame(c.peer, 0)

  def closeFrame(f: InternalFrame) = managerPeer.closeFrame(f.peer)

  def deactivateFrame(f: InternalFrame) = managerPeer.deactivateFrame(f.peer)

  def deiconifyFrame(f: InternalFrame) = managerPeer.deiconifyFrame(f.peer)

  def dragFrame(c: Component, newX: Int, newY: Int) = managerPeer.dragFrame(c.peer, newX, newY)

  def endDraggingFrame(c: Component) = managerPeer.endDraggingFrame(c.peer)

  def endResizingFrame(c: Component) = managerPeer.endResizingFrame(c.peer)

  def iconifyFrame(f: InternalFrame ) = managerPeer.iconifyFrame(f.peer)

  def maximizeFrame(f: InternalFrame) = managerPeer.maximizeFrame(f.peer)

  def minimizeFrame(f: InternalFrame) = managerPeer.minimizeFrame(f.peer)

  def openFrame(f: InternalFrame) = managerPeer.openFrame(f.peer)

  def resizeFrame(c: Component, newX: Int, newY: Int, newWidth: Int, newHeight: Int) = 
    managerPeer.resizeFrame(c.peer, newX, newY, newWidth, newHeight)

  def setBoundsForFrame(c: Component, newX: Int, newY: Int, newWidth: Int, newHeight: Int) = 
    managerPeer.setBoundsForFrame(c.peer, newX, newY, newWidth, newHeight)
}

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
class InternalDefaultDesktopManager extends InternalDesktopManager { outer =>
	
  /**
   * Wrapped instance javax.swing.DefaultDesktopManager
   */
  val managerPeer: DefaultDesktopManager = new DefaultDesktopManager with SuperMixin
    
  // Here no access to 'UIElement.cachedWrapper(...)'
  private val ClientKey = "scala.swingWrapper"
	  
  /**
   * This trait is used to redirect certain calls from the peer to the wrapper 
   * and back. Useful to expose methods that can be customized by overriding.
   */
  protected trait SuperMixin extends DefaultDesktopManager {
	  
    override def activateFrame(f: JInternalFrame) {
      outer.activateFrame(f.getClientProperty(ClientKey).asInstanceOf[InternalFrame])
    }   
    def __super__activateFrame(f: InternalFrame) {
      super.activateFrame(f.peer)
    }
    
    override def beginDraggingFrame(c: JComponent) {
      outer.beginDraggingFrame(c.getClientProperty(ClientKey).asInstanceOf[Component])
    }
    def __super__beginDraggingFrame(c: Component) {
      super.beginDraggingFrame(c.peer)
    }
    
    override def beginResizingFrame(c: JComponent, i: Int) {
      outer.beginResizingFrame(c.getClientProperty(ClientKey).asInstanceOf[Component])
    }
    def __super__beginResizingFrame(c: Component) {
      super.beginResizingFrame(c.peer, 0)
    }

    override def closeFrame(f: JInternalFrame) {
      outer.closeFrame(f.getClientProperty(ClientKey).asInstanceOf[InternalFrame])
    }   
    def __super__closeFrame(f: InternalFrame) {
      super.closeFrame(f.peer)
    }
    
    override def deactivateFrame(f: JInternalFrame) {
      outer.deactivateFrame(f.getClientProperty(ClientKey).asInstanceOf[InternalFrame])
    }   
    def __super__deactivateFrame(f: InternalFrame) {
      super.deactivateFrame(f.peer)
    }
    
    override def deiconifyFrame(f: JInternalFrame) {
      outer.deiconifyFrame(f.getClientProperty(ClientKey).asInstanceOf[InternalFrame])
    }   
    def __super__deiconifyFrame(f: InternalFrame) {
      super.deiconifyFrame(f.peer)
    }
    
    override def dragFrame(c: JComponent, newX: Int, newY: Int) {
      outer.dragFrame(c.getClientProperty(ClientKey).asInstanceOf[InternalFrame], newX, newY)
    }   
    def __super__dragFrame(c: Component, newX: Int, newY: Int) {
      super.dragFrame(c.peer, newX, newY)
    }
    
    override def endDraggingFrame(c: JComponent) {
      outer.endDraggingFrame(c.getClientProperty(ClientKey).asInstanceOf[Component])
    }
    def __super__endDraggingFrame(c: Component) {
      super.endDraggingFrame(c.peer)
    }

    override def endResizingFrame(c: JComponent) {
      outer.endResizingFrame(c.getClientProperty(ClientKey).asInstanceOf[Component])
    }
    def __super__endResizingFrame(c: Component) {
      super.endResizingFrame(c.peer)
    }
    
    override def iconifyFrame(f: JInternalFrame) {
      outer.iconifyFrame(f.getClientProperty(ClientKey).asInstanceOf[InternalFrame])
    }   
    def __super__iconifyFrame(f: InternalFrame) {
      super.iconifyFrame(f.peer)
    }
    
    override def maximizeFrame(f: JInternalFrame) {
      outer.maximizeFrame(f.getClientProperty(ClientKey).asInstanceOf[InternalFrame])
    }   
    def __super__maximizeFrame(f: InternalFrame) {
      super.maximizeFrame(f.peer)
    }
    
    override def minimizeFrame(f: JInternalFrame) {
      outer.minimizeFrame(f.getClientProperty(ClientKey).asInstanceOf[InternalFrame])
    }   
    def __super__minimizeFrame(f: InternalFrame) {
      super.minimizeFrame(f.peer)
    }
    
    override def openFrame(f: JInternalFrame) {
      outer.openFrame(f.getClientProperty(ClientKey).asInstanceOf[InternalFrame])
    }   
    def __super__openFrame(f: InternalFrame) {
      super.openFrame(f.peer)
    }

    override def resizeFrame(c: JComponent, newX: Int, newY: Int, newWidth: Int, newHeight: Int) {
      outer.resizeFrame(c.getClientProperty(ClientKey).asInstanceOf[Component], newX, newY, newWidth, newHeight)
    }
    def __super__resizeFrame(c: Component, newX: Int, newY: Int, newWidth: Int, newHeight: Int) {
      super.resizeFrame(c.peer, newX, newY, newWidth, newHeight)
    }
    
    override def setBoundsForFrame(c: JComponent, newX: Int, newY: Int, newWidth: Int, newHeight: Int) {
      outer.setBoundsForFrame(c.getClientProperty(ClientKey).asInstanceOf[Component], newX, newY, newWidth, newHeight)
    }
    def __super__setBoundsForFrame(c: Component, newX: Int, newY: Int, newWidth: Int, newHeight: Int) {
      super.setBoundsForFrame(c.peer, newX, newY, newWidth, newHeight)
    }
  }
  
  override def activateFrame(f: InternalFrame) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__activateFrame(f)
      case _ => super.activateFrame(f)
    }
  }
  
  override def beginDraggingFrame(c: Component) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__beginDraggingFrame(c)
      case _ => super.beginDraggingFrame(c)
    }
  }
 
  override def beginResizingFrame(c: Component) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__beginResizingFrame(c)
      case _ => super.beginResizingFrame(c)
    }
  }

  override def closeFrame(f: InternalFrame) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__closeFrame(f)
      case _ => super.closeFrame(f)
    }
  }
  
  override def deactivateFrame(f: InternalFrame) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__deactivateFrame(f)
      case _ => super.deactivateFrame(f)
    }
  }
  
  override def deiconifyFrame(f: InternalFrame) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__deiconifyFrame(f)
      case _ => super.deiconifyFrame(f)
    }
  }
  
  override def dragFrame(c: Component, newX: Int, newY: Int) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__dragFrame(c, newX, newY)
      case _ => super.dragFrame(c, newX, newY)
    }
  }
  
  override def endDraggingFrame(c: Component) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__endDraggingFrame(c)
      case _ => super.endDraggingFrame(c)
    }
  }

  override def endResizingFrame(c: Component) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__endResizingFrame(c)
      case _ => super.endResizingFrame(c)
    }
  }

  override def iconifyFrame(f: InternalFrame) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__iconifyFrame(f)
      case _ => super.iconifyFrame(f)
    }
  }
  
  override def maximizeFrame(f: InternalFrame) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__maximizeFrame(f)
      case _ => super.maximizeFrame(f)
    }
  }
  
  override def minimizeFrame(f: InternalFrame) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__minimizeFrame(f)
      case _ => super.minimizeFrame(f)
    }
  }
  
  override def openFrame(f: InternalFrame) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__openFrame(f)
      case _ => super.openFrame(f)
    }
  }

  override def resizeFrame(c: Component, newX: Int, newY: Int, newWidth: Int, newHeight: Int) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__resizeFrame(c, newX, newY, newWidth, newHeight)
      case _ => super.resizeFrame(c, newX, newY, newWidth, newHeight)
    }
  }
  
  override def setBoundsForFrame(c: Component, newX: Int, newY: Int, newWidth: Int, newHeight: Int) {
	managerPeer match {
      case sm: SuperMixin => sm.__super__setBoundsForFrame(c, newX, newY, newWidth, newHeight)
      case _ => super.setBoundsForFrame(c, newX, newY, newWidth, newHeight)
    }
  }
}

