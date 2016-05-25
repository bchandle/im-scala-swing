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

import java.awt.{Image, Rectangle}
import javax.swing.{Icon, JComponent, JInternalFrame, JPanel}
import javax.swing.event.InternalFrameListener
import scala.swing.{Component, MenuBar, Publisher, RootPanel, UIElement}

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
class InternalFrame extends Component with RootPanel { 
	
  // Component
  
  override lazy val peer: JInternalFrame = new JInternalFrame with SuperMixin {
	  
    // Default lightweight glass pane (getGlassPane returns java.awt.Component !)
	  
	if (this.getGlassPane.isInstanceOf[JComponent]) {
	  this.getGlassPane.asInstanceOf[JComponent].putClientProperty(
	    "scala.swingWrapper", 
	    Component.wrap(this.getGlassPane.asInstanceOf[JComponent])
	  )
	}
	else {
      this.setGlassPane( 
        (new Component {
          override lazy val peer: JPanel = new JPanel with SuperMixin {
	        setOpaque(false)
	        setVisible(false)
          }
        }).peer
      )
	}
  }
  
  // Here no access to 'UIElement.cachedWrapper(...)'
  private val ClientKey = "scala.swingWrapper"
	  
  // Missing in UIElement ?
  def bounds_=(b: (Int, Int, Int, Int)): Unit = peer.setBounds(b._1, b._2, b._3, b._4)
  def location_=(l: (Int, Int)): Unit = peer.setLocation(l._1, l._2)

  // InternalFrame
  
  def closable: Boolean = peer.isClosable
  def closable_=(b: Boolean) { peer.setClosable(b) }
  
  def closed: Boolean = peer.isClosed
  def closed_=(b: Boolean) { peer.setClosed(b) }
  
//  def desktopPane = 
 
  def dispose: Unit = peer.dispose

// setDefaultCloseOperation(int operation) javax.swing.WindowConstants
// setDesktopIcon(JInternalFrame.JDesktopIcon d)
  
  def frameIcon: Icon = peer.getFrameIcon
  def frameIcon_=(i: Icon) { peer.setFrameIcon(i) }
 
  // glass pane is never null 
  def glassPane: Component =
	peer.getGlassPane.asInstanceOf[JComponent].getClientProperty(ClientKey).asInstanceOf[Component]
  def glassPane_=(c: Component) = {
	if (c == null) throw new NullPointerException("GlassPane must not be null !!")
	peer.setGlassPane(c.peer)
  }
	  
  def icon: Boolean = peer.isIcon
  def icon_=(b: Boolean) { peer.setIcon(b) }
  
  def iconifiable: Boolean = peer.isIconifiable
  def iconifiable_=(b: Boolean) { peer.setIconifiable(b) }
  
  def maximizable: Boolean = peer.isMaximizable
  def maximizable_=(b: Boolean) { peer.setMaximizable(b) }
  
  def maximum: Boolean = peer.isMaximum
  def maximum_=(b: Boolean) { peer.setMaximum(b) }
  
  def menuBar: MenuBar = {
	val mb = peer.getJMenuBar
	if (mb == null) MenuBar.NoMenuBar
	else mb.getClientProperty(ClientKey).asInstanceOf[MenuBar] 
  }
  def menuBar_=(m: MenuBar) = peer.setJMenuBar(if(m == MenuBar.NoMenuBar) null else m.peer)
  
//def normalBounds This method is intended for use only by desktop managers. 
  
  def pack: Unit = peer.pack()
  
  def resizable: Boolean = peer.isResizable
  def resizable_=(b: Boolean) { peer.setResizable(b) }
  
  def selected: Boolean = peer.isSelected
  def selected_=(b: Boolean) { peer.setSelected(b) }

  def title: String = peer.getTitle
  def title_=(s: String) = peer.setTitle(s)

  def toBack: Unit = peer.toBack // TODO moveToBack/Front
  def toFront: Unit = peer.toFront
  
  import org.interactivemesh.scala.swing.event._
  
  // InternalFrameEvent, InternalFrameListener
  object frame extends Publisher {
    peer.addInternalFrameListener(new InternalFrameListener {
      def internalFrameActivated(e: javax.swing.event.InternalFrameEvent) { 
        publish(InternalFrameActivated(InternalFrame.this, e.paramString)) 
      }
      def internalFrameClosed(e: javax.swing.event.InternalFrameEvent) { 
        publish(InternalFrameClosed(InternalFrame.this, e.paramString)) 
      }
      def internalFrameClosing(e: javax.swing.event.InternalFrameEvent) { 
        publish(InternalFrameClosing(InternalFrame.this, e.paramString)) 
      }
      def internalFrameDeactivated(e: javax.swing.event.InternalFrameEvent) { 
        publish(InternalFrameDeactivated(InternalFrame.this, e.paramString)) 
      }
      def internalFrameDeiconified(e: javax.swing.event.InternalFrameEvent) { 
        publish(InternalFrameDeiconified(InternalFrame.this, e.paramString)) 
      }
      def internalFrameIconified(e: javax.swing.event.InternalFrameEvent) { 
        publish(InternalFrameIconified(InternalFrame.this, e.paramString)) 
      }
      def internalFrameOpened(e: javax.swing.event.InternalFrameEvent) { 
        publish(InternalFrameOpened(InternalFrame.this, e.paramString)) 
      }
    }) 
  }
  
  /* 
  override def onFirstSubscribe {
    super.onFirstSubscribe
  }*/
  
  // More TODO
}
