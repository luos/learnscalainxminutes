/* 
Scala is an object-functional programming language meaning it supports concepts from OOP and functional programming too. [3]
Runs on the JVM, so you can use anything from java. 
If you don't like or don't wan't to use functional features, you got a better java.

I mostly use it as an OOP language with  sugar from the functional parts like map, lamdba functions and others.

In this tutorial, I write most commands to the scala repl. 
scala> denotes the line I wrote. 

*/

//Declaring variables
// Scala has a powerful type system
//You don't have to specify a variable's type, the compiler knows it [2]
// Just created an immutable Int variable
scala> val ten = 10
ten: Int = 10
// We can't modify it's value, error caught in compile time
scala> ten = 11
<console>:11: error: reassignment to val
       ten = 11
           ^

// Declaring mutable variables with "var" keyword
scala> var thisCanBeChanged = "I'm sure"
thisCanBeChanged: java.lang.String = I'm sure
scala> thisCanBeChanged = "Yep"
thisCanBeChanged: java.lang.String = Yep

// If you like to type, you can specify the type of the variable
scala> val d : Double = 3*3  // We want this to be a double
d: Double = 9.0




 
// Declaring functions
// You don't have to use ; on line endings or . in object.method or () after parameter lists in method calls  if not ambiguous. In my experience the dots and braces are needed in a lot of cases. [1]
def myFunction(param: ParamType) = "Something"
// It is the same as 
def myFunction(param: ParamType) : String = { // After the : there is the return type
	return "Something" // And here is the explicit return keyword, if returning from a function with the keyword we have to specify the return type too
}

// You don't have to specify return type of a method. The compiler can figure it out but it is recommended if longer than a few lines or in some other cases. (Like you want a value to convert to another type with an implicit conversion [4])

def myFunction(param: ParamType) = {
	"Something" // last instruction gets returned
}
// Note: it can bite you if you write something like this, I'm accustomed to this style of returning values in C style languages
def myFunction(param: ParamType) = {
	if (true){
		"This never gets returned"
	}
	"This always gets returned" // this is the last line in the function, you just forgot the else
}

// we can use functions with named and default parameters

scala> def myFunc(firstParam: Int = 0, secondParam: Int = 100) = "Hi " + firstParam +  " " + secondParam
myFunc: (firstParam: Int, secondParam: Int)java.lang.String

scala> myFunc()
res23: java.lang.String = Hi 0 100

scala> myFunc(100)
res24: java.lang.String = Hi 100 100

scala> myFunc(secondParam = 500)
res25: java.lang.String = Hi 0 500

// If you want to create a function to pass to functions like map
theValue => theFunctionBody
// or with multiple arguments
(arg1,arg2) => body

// Everything is a statement
// it means we can save blocks' return values into variables

scala> val ret = if ( true ) "Was True" else "Was False"
ret: java.lang.String = Was True

scala> val thisWorksToo = { val a = 10; val b = 20; a*b; }
thisWorksToo: Int = 200


//Importing packages is almost the same as java:

// Just imported all the packages from java.util 
scala> import java.util._
import java.util._
// "_" (underscore) is the joker character, in imports it means the whole package or object methods (java's static import)
//Defining an object basically means that you create a singleton, same as a class in java with only static methods, usually it has a companion class with the same name that can be instantiated
scala> object MyObject { def hello = "hello" }
defined module MyObject

scala> hello
<console>:11: error: not found: value hello
              hello
              ^

// importing object methods
scala> import MyObject._
import MyObject._

scala> hello
res1: java.lang.String = hello





// Options
// The creators of the language wanted to eliminate null pointer exceptions, so they created a container for values called Option, it can be None if it is empty and it is Some if it contains some not null value
// It is enforcing you to check for null values

scala> val something = Some(10)
something: Some[Int] = Some(10)
// with map we can open the Option, if it contains something we get an Option with the return type of the mapped function, in this case it is Option[String]
// _ is generally a placeholder identifier, it means "anything". In this case it means "the only parameter of this function"
// We are passing a function to map, _ now is the value in the option. 
scala> something.map( _.toString )
res2: Option[java.lang.String] = Some(10) // The result is a new Option containing the mapped function's return value

scala> val nothing = None
nothing: None.type = None
// If it contains nothing we get a None
scala> nothing.map( _.toString )
res3: Option[java.lang.String] = None

// In the end we can fall back to a default value instead of None if we want
scala> nothing.getOrElse(350) // We get what is in the Option or 350 if it is nothing
res13: Int = 350 // Note: it is an Int not an Option

scala> something.getOrElse(500) // we get 10 because it is a Some(10)
res15: Int = 10

// Lists
scala> import scala.collection.immutable.List
import scala.collection.immutable.List
// We created an immutable list, we can not modify it's contents
scala> val list = List(1,2,3,4)
list: List[Int] = List(1, 2, 3, 4)

// As usual in functional languages, we get some methods to transform lists
// Calculating each number's square with map
// we pass a function that converts Integer into another Integer
// Map means: please transform this list into another list with this function applied to each element
scala> list.map(num => num*num) // compiler knows the return type is Int
res6: List[Int] = List(1, 4, 9, 16)

//Passing a previously created function is just as easy

scala> def myMapFunc(num : Int) = "We got: " + num
myMapFunc: (num: Int)java.lang.String

scala> list.map(myMapFunc)
res10: List[java.lang.String] = List(We got: 1, We got: 2, We got: 3, We got: 4)

// We can chain the methods, the original lists never change, they are immutable anyway
scala> list.map(num => num*num).map(myMapFunc)
res11: List[java.lang.String] = List(We got: 1, We got: 4, We got: 9, We got: 16)

// We can filter a list with the method filter, and a function which converts the elems into boolean
// Filter means: please only keep the elements that if we apply this function it returns true
scala> list.filter(num => num > 3) // getting numbers greater than 3
res12: List[Int] = List(4)

// We can divide lists 
scala> val points = List(30,50,32,23,65,99,76,85)
points: List[Int] = List(30, 50, 32, 23, 65, 99, 76, 85)
// We need the bests in the class 
scala> points partition ( _ > 80 )
res58: (List[Int], List[Int]) = (List(99, 85),List(30, 50, 32, 23, 65, 76)) // It is a hard class



// We have foreach too
scala> for(elem <- list){ println(elem) }
1 // these are printed, not returned
2
3
4
// Or we apply a function wich returns Unit. Unit is scala's void
scala> list.foreach( println )
1 // these are printed, not returned
2
3
4

// More about loops
// for, the to and until keywords create ranges
scala> for(i <- 0 to 3 ){ print(i) } // inclusive
0123
scala> for(i <- 0 until 3 ){ print(i) } // exclusive
012
// We can call other functions on ranges
scala> (0 to 1000).sum
res54: Int = 500500
scala> (0 to 1000).contains(500)
res56: Boolean = true





// These need some check
// We can fold lists
// Fold means: please go through each value of this list, give me the return value of the last applied function, return the next value
// We can supply an initial value if we want 
scala> list.foldLeft("initialValue")(
		(elem,currentValue) => // A function with two arguments 
			currentValue + " " + elem.toString + " " // 
		)
res20: java.lang.String = "4 3 2 1 initialValue    " // @Todo: check why it has some spaces at the end

// Fold right
scala> list.foldRight("initialValue")((elem,currentValue) => currentValue + " " + elem.toString + " " )
res21: java.lang.String = "initialValue 4  3  2  1 "



// Pattern matching
// It is a powerful switch without fallthrough

scala> val someList = List(1,2)
someList: List[Int] = List(1, 2)
// We match all lists with 2 elements
scala> someList match { 
	case List(_,_) => "2 Element List";  
	case _ => "Not 2 element list" 
}
res38: java.lang.String = 2 Element List

//We can get the values too
scala> someList match { 
	case List(first,second) => "the first is " + first;  
	case _ => "Nope" 
}
res40: java.lang.String = the first is 1

//Matcihng a list and getting the first element

scala> val otherList = List(1,2,3,4,5,6)
otherList: List[Int] = List(1, 2, 3, 4, 5, 6)
scala> otherList match { 
	case List(first, _*) => "the first is " + first;  // notice the _*, means we don't care what it is don't care how many times
	case _ => "Nope" 
}
res43: java.lang.String = the first is 1
// It matches the one element long list too
scala> val otherList = List(1)
otherList: List[Int] = List(1)
scala> otherList match { 
	case List(first, _*) => "the first is " + first;  
	case _ => "Nope" 
}
res44: java.lang.String = the first is 1




// Scala has maps to. It is the same as the associative array in other languages or Map in java
// We can use any key in the map and we can use Scala's builtin Symbol type

scala> import scala.collection.immutable.Map
import scala.collection.immutable.Map
// Symbols starting with ', they are fancy strings
scala> val myMap = Map('a -> 1, 'b -> 2)
myMap: scala.collection.immutable.Map[Symbol,Int] = Map('a -> 1, 'b -> 2)

// Getting an element from a map, we get an Option
scala> myMap get 'a
res27: Option[Int] = Some(1)
scala> myMap get 'nonExistant
res28: Option[Int] = None

// We can check if it contains an element
scala> myMap contains 'a
res29: Boolean = true

// But we can omit this part and just use options for A Cleaner Code
scala> myMap.get('a).map( _.toString ).getOrElse("Not found") // Remember, we can use map on Options to open
res30: java.lang.String = 1

// We just avoided a case for an exception (NullPointer or ElementNotFound? ) with an Option 
scala> myMap.get('nonExistant).map( _.toString ).getOrElse("Not found")
res31: java.lang.String = Not found

// We can add elements to a map, we get a new Map 
scala> myMap + ('c -> 100)
res32: scala.collection.immutable.Map[Symbol,Int] = Map('a -> 1, 'b -> 2, 'c -> 100)
// myMap not changed, it is immutable
scala> myMap
res33: scala.collection.immutable.Map[Symbol,Int] = Map('a -> 1, 'b -> 2)

// We can foreach on a Map too
scala> for ( (key,value) <- myMap ) println(key +" "+ value)
'a 1
'b 2


// Scala has tuples too
// These are like immutable arrays, but each element has it's own type
scala> val tuple = (3, "John" , true)
tuple: (Int, java.lang.String, Boolean) = (3,John,true)
// We access its content with the ._NUMBER method
// Note: indexing starts with 1
scala> tuple._2 + " is the #" + tuple._1 + " in the competition " 
res34: java.lang.String = "John is the #3 in the competition "

// We can use tuples in a foreach on a Map too
scala> myMap.foreach( e => println(e._1 + " " + e._2) )
'a 1
'b 2


[1] How to use braces and parentheses:  http://stackoverflow.com/questions/4386127/what-is-the-formal-difference-in-scala-between-braces-and-parentheses-and-when
[2] Scala Type System:  http://www.slideshare.net/dgalichet/demystifying-scala-type-system
[3] Wikipedia: http://en.wikipedia.org/wiki/Scala_(programming_language)
[4] Implicit conversions: http://java.dzone.com/articles/scala-implicits-type-classes
Operators
apply
