package io.glassdome.widgets.services


import scala.util.Try
import io.glassdome.widgets.models.Widget
import io.glassdome.widgets.services.PostgresWidgetData.mapInstance


trait WidgetData {
  
  def findById(id: Int): Option[Widget]
  
  def list(): Seq[Widget]
  
  def create(w: Widget): Try[Widget]
  
  def update(w: Widget): Try[Widget]
  
  def delete(id: Int): Try[Widget]

  def listWidgetsByUser(userId: Int): Seq[Widget]

}

//trait DBRealations {
//
//  def listWidgetsByUser(userId: Int) = mapInstance{
//    sql"""SELECT * FROM widget WHERE owner = ${userId} """
//  }.list.apply
//}


trait Database[A] {
  
  def findById(id: Int): Option[A]
  
  def list(): Seq[A]
  
  def create(w: A): Try[A]
  
  def update(w: A): Try[A]
  
  def delete(id: Int): Try[A]

}





/* 
 * # Return widgets that are owned by the given user.
 * GET /users/:id/widgets
 * 
 * # List widgets the current user is sharing
 * GET /users/:id/widgets?q=sharedTo   &owner=2&owner=3
 * 
 * # List widgets that are shared with the current user
 * GET /users/:id/widgets?q=sharedFrom(&owner={id}&owner={id})
 * 
 * SELECT w.*
 * FROM widget w
 * WHERE w.id IN (
   * SELECT widget 
   * FROM widget_share 
   * WHERE owner = 2
   * AND shared_with = 1);
 * 
 * GET /users/:id/widgets 
 * 
 * 
 * Widgets that I own
 * Widgets that are shared with me
 * 
 * 
 * 
 * # Share a Widget
 * POST /users/:id/widgets/:id?share="user123"
 * POST /users/:id/widgets/:id?unshare="user123"
 * 
 * GET /users/:id/invites
 * GET /users/:id/invites/:id
 * 
 * POST /users/:id/invites/:id?q=(accept|reject)
 * 
 */


/*
trait AppData {
  
  case class Invite(n: String)
  
  def findWidgetsByOwner(owner: Int)
  
  def findSharedWidgets(sharedWith: Int)
  
  def findSharedWidgetsByOwner(owner: Int, sharedWith: Int)
  
  def findSharedWidget(sharedWith: Int, owner: Int*): Seq[Widget] = {
    
  }
  
  def findInvitesByUser(user: Int, states: String*): Seq[Invite] = {
    
  }
  
  
  def updateInvite(invite: Invite)
  
  def acceptInvite(inviteId: Int): Try[Invite] = {
    // updateInvite(...)
  }
  
  def acceptInvite(invite: Invite): Try[Invite] = {
      
  }
  
  def rejectInvite(inviteId: Int): Try[Invite] = {
    //
  }
}
*/


trait Database2[A] {
  
  
  
  
  def findById(id: Int): Option[A]
  
  def findByCriteria(criteria: Map[String, String]): Seq[A]
  
  def list(): Seq[A]
  
  def create(w: A): Try[A]
  
  def update(w: A): Try[A]
  
  def delete(id: Int): Try[A]

}








