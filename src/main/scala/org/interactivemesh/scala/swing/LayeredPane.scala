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

import javax.swing.{JComponent, JLayeredPane}
import javax.swing.JLayeredPane._
import scala.swing.{Component, LayoutContainer}

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
object LayeredPane {	
  /**
   * The layer of a component in a <code>LayeredPane</code>
   */
  object Layer extends Enumeration {
	type Layer = Value
    val Default = Value(DEFAULT_LAYER.intValue, "JLayeredPane.DEFAULT_LAYER")
    val Palette = Value(PALETTE_LAYER.intValue, "JLayeredPane.PALETTE_LAYER")
    val Modal = Value(MODAL_LAYER.intValue, "JLayeredPane.MODAL_LAYER")
    val Popup = Value(POPUP_LAYER.intValue, "JLayeredPane.POPUP_LAYER")
    val Drag = Value(DRAG_LAYER.intValue, "JLayeredPane.DRAG_LAYERR")
  }
  
  import Layer._
  
  class LayerConstraints(val layer: Int, val position: Int) extends AnyRef {
	  
    def this(layer: Layer, position: Int) = this(layer.id, position)
    def this(layer: Layer) = this(layer.id, 0)
    def this(layer: Int) = this(layer, 0)
    def this() = this(Default)
	  
    override def toString(): String = {
	  "LayerConstraints : Layer=" + layer +", Position=" + position
    }	  	
  }
  
  def getLayer(comp: Component): Int = {
	JLayeredPane.getLayer(comp.peer)
  }
  
  def putLayer(comp: Component, layer: Int): Unit = {
	JLayeredPane.putLayer(comp.peer, layer.intValue)	  
  }
}

/**
 *  @author August Lammersdorf, InteractiveMesh
 *  @version 1.0 - 2010/04/13
 */
class LayeredPane extends Component with LayoutContainer { 
	
  //
  // Component
  //
  
  override lazy val peer: JLayeredPane = new JLayeredPane with SuperMixin
  
  //
  // LayoutContainer
  //
  
  import LayeredPane._
  
  /**
   * The type of component constraints for this container.
   */
  type Constraints = LayerConstraints
  /**
   * Obtains the constraints for the given component from the underlying
   * Swing layout manager.
   */
  protected def constraintsFor(comp: Component): Constraints = {
	new Constraints(peer.getLayer(comp.peer), peer.getPosition(comp.peer))
  }
  /**
   * Checks whether the given constraints are valid. Additionally returns
   * an error string that is only fetched if the constraints aren't valid.
   */
  protected def areValid(c: Constraints): (Boolean, String) = {
	(true, "")
  }
  /**
   * Adds a component with the given constraints to the underlying layout 
   * manager and the component peer.
   */
  protected def add(comp: Component, c: Constraints) {
	peer.add(comp.peer, new java.lang.Integer(c.layer.intValue), c.position.intValue)
  }
  
  /**
   * A map of components to the associated layout constraints.
   * Any element in this map is automatically added to the contents of this 
   * panel. Therefore, specifying the layout of a component via 
   * 
   * layout(myComponent) = myConstraints
   * 
   * also ensures that myComponent is properly add to this container.
   */
/*  def layout: Map[Component, Constraints] = new Map[Component, Constraints] {
	  
    def -= (c: Component): this.type = { _contents -= c; this }
    def += (cl: (Component, Constraints)): this.type = { update(cl._1, cl._2); this }
    
    override def update (c: Component, l: Constraints) {
      val (v, msg) = areValid(l)
      if (!v) throw new IllegalArgumentException(msg) 
      add(c, l)
      this
    }
    
    def get(c: Component) = Swing.toOption(constraintsFor(c)) 
    
    override def size = peer.getComponentCount
    
    def iterator: Iterator[(Component, Constraints)] = 
      peer.getComponents.iterator.map { c => 
        val comp = UIElement.cachedWrapper[Component](c.asInstanceOf[JComponent]) 
        (comp, constraintsFor(comp))
      }
  }
*/  
  
  //
  // LayeredPane
  //

  private val ClientKey = "scala.swingWrapper"
  
  def componentCountInLayer(layer: Int): Int = peer.getComponentCountInLayer(layer.intValue)

  def componentsInLayer(layer: Int): Array[Component] = {
    val awtComps = peer.getComponentsInLayer(layer.intValue)
    if ( (awtComps ne null) && awtComps.length > 0) {
	  val comps = new Array[Component](awtComps.length)  
	  for (i <- 0 until awtComps.length) {
        comps(i) = awtComps(i).asInstanceOf[JComponent].getClientProperty(ClientKey).asInstanceOf[Component]
	  }
	   return comps
    }
    return null
  }
  
  def moveToBack(c: Component): Unit = peer.moveToBack(c.peer)
  
  def moveToFront(c: Component): Unit = peer.moveToFront(c.peer)
  
  
  // More TODO
}
