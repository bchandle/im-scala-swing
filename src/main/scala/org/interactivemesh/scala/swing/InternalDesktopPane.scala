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

import javax.swing.JDesktopPane

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
object InternalDesktopPane {	
  
  import JDesktopPane._
	
  object DragMode extends Enumeration {
    type DragMode = Value
    val Live = Value(LIVE_DRAG_MODE, "JDesktopPane.LIVE_DRAG_MODE")
    val Outline = Value(OUTLINE_DRAG_MODE, "JDesktopPane.OUTLINE_DRAG_MODE")
  }
  
  import DragMode._
  
  private def wrapDragMode(i: Int): DragMode = i match {
    case LIVE_DRAG_MODE => Live
    case OUTLINE_DRAG_MODE => Outline
  }
}

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
class InternalDesktopPane extends LayeredPane {
	
  import InternalDesktopPane._
  
  private var _desktopManager: InternalDesktopManager = 
	InternalDesktopManager.wrap(peer.getDesktopManager)
	
  //
  // LayeredPane  
  //
  
  override lazy val peer: JDesktopPane = new JDesktopPane with SuperMixin

  import DragMode._

  def dragMode: DragMode = wrapDragMode(peer.getDragMode)
  def dragMode_=(dm: DragMode) = peer.setDragMode(dm.id)
  
  def desktopManager: InternalDesktopManager = _desktopManager
  def desktopManager_=(idm: InternalDesktopManager) {
	  peer.setDesktopManager(idm.managerPeer)
	  _desktopManager = idm
  }
  
// More TODO
}
