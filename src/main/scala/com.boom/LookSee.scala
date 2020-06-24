package com.boom

import scala.jdk.CollectionConverters._
import scala.quoted._
import scala.tasty.Reflection

opaque type TypeSymbol = String 

object LookSee:

  inline def diveInto[T]: String = ${ diveIntoImpl[T]() }

  def diveIntoImpl[T]()(implicit qctx: QuoteContext, ttype: scala.quoted.Type[T]): Expr[String] = 
    import qctx.tasty.{_, given _}
    Expr( unwindType(qctx.tasty)(typeOf[T]) )

  def unwindType(reflect: Reflection)(aType: reflect.Type): String =
    import reflect.{_, given _}

    aType match {
      case AppliedType(t,tob) =>
        val syms = getTypeParameters(reflect)(t.classSymbol.get)
        println("Syms : "+syms)
        val syms2 = getTypeParameters(reflect)(t.classSymbol.get)
        println("Syms2: "+syms2)
      case _ =>
    }
    "OK!"

  def getTypeParameters(reflect: scala.tasty.Reflection)(symbol: reflect.Symbol): List[TypeSymbol] = 
    println("=== Here in "+symbol)
    println("    Primary: "+symbol.primaryConstructor)
    val z = symbol.primaryConstructor.paramSymss
    println("    Const: "+z)
    println("    Size: "+ z.knownSize)
    println("    Nonempty? " +z.nonEmpty)
    println("    Head: "+z.head)
    z match {
      case Nil => 
        println("(Nil 1)")
        Nil
      case p if p.nonEmpty  => 
        println("P: "+p)
        p.head.filter(_.isType).map(_.name.asInstanceOf[TypeSymbol])
      case _   => 
        println("(Nil 2)")
        Nil
    }