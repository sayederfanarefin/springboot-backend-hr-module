# springboot-backend-hr-module

# Exercise 02 -- Object-Oriented Design

### Submission Deadline: November 18, 3 pm!
In the second assignment, you will get in touch with two more advanced programming concepts: **Recursion** and **object-oriented programming**.

For solving the tasks, this repository contains:

* *three different classes* in the `src` folder of this repository.
These classes contain already the skeleton/template for solving the particular tasks (e.g., empty methods).
**IMPORTANT: Make use of these classes for your solution. Also, do not change the method signature on your own (there is a good reason for the provided signatures).Otherwise, we will account for a malus (i.e., reducing your points even if the solution is basically correct)!**
* a `test` folder, containing the tests for the implementation. These tests are implemented by the instructor and can be used by you to improve your code. Also, these tests are kind of initial sanity check for assessing the assignment.
**Run the tests to check whether your solutions works correctly. But do not touch/change them!**
* some more files needed for handling the project in Eclipse (or for proper push/pull the repository). See the slides for more details. In general, the other files **should not be changed or deleted, if you do not really know what you are doing**!!

In the following, you will find the particular tasks to be solved.

## Task 2-01: Factorials (to be solved in file `Main.java`)
A special sequence of numbers are the factorials, which may be defined recursively as:
```
fac (x) = if (x = 0) then 1
          else n * fac(x-1)
          fi
```
<table border="0">
  <tr>
   <td>
    Please write a recursive function:
     
```public static BigInteger facRec (int n)```
for calculating the n-th factorial. Use your main method to output the first 15 factorials.

 </td>

<td>2 Points</td>
  </tr>
<tr>
  <td>
            
   Using the example of `facRec(5)`, demonstrate the computation of the recursive function calls step-by step (in the `main` method).
   HINT: To provide the results of each coputational step, you can use comments within the source file (e.g., in the method, mentioned above).
    
  </td>
  <td>1 Point</td>
</tr>
<tr>
  <td>
  Please write a iterative function:

  ```public static BigInteger facIter (int n)```
 for calculating the factorials in an iterative fashion.
  </td>
  <td>1 Point</td>
</tr>
<tr>
  <td> 
            
   Test both functions within the `main` method by calculating the factorial of **n = 23**. 
   While doing this, count (and display) the number of loop iterations needed (or the number of recursive function calls, respectively).
  
  </td>
  <td>1 Point</td>
</tr>
</table>

## Task 2-02: Class Clock (to be solved in class `Clock.java`)

<table border="0">
<tr>
  <td>

Time is usually represented by an *hour number (between 0 and 23)* and a *minute number (between 0 and 59)*.
Write a class `Clock` which represents time and which is able to *create, to add and to print* time objects.
The class should include:

</td>

<td></td>
  </tr>
<tr>
  <td>

The attributes: 

`hour` and `minute` (both of type `int`), as well as the getter methods `getMin()` and `getH()`
          
 </td>
  <td>1 Point</td>
</tr>
<tr>
  <td>

The constructors: 

Three constructors should be provided for creating the clock objects 
1. taking *hour* and *minute* information
2. taking *minute* information *only*, and 
3. taking information of the form `hour:min` as a string

</td>
  <td>1 Point</td>
</tr>
<tr>
  <td>

The methods (applying overloading):

 ```public Clock add (int min)``` and
 ```public Clock add (Clock c)```
 
for adding some amount of time to a clock object.
          
</td>
  <td>2 Points</td>
</tr>
<tr>
  <td>

The method
`public String toString()`
for printing the time in string representation of the following format: *“hh:mm”* (please mind leading zeros).

</td>
  <td>1 Point</td>
</tr>
<tr>
  <td>

Test your class `Clock` within your `main` method. 
To this end, verify your functions by creating several different Clock objects and testing them.

**Note:** Take care of normalizing your time information. Hours need to lie between 0 and 23, minutes between 0 and 59. 
When adding time to a Clock object, a new Clock object should be returned *without* altering the current one – meaning the current time/clock object should *not* be changed.


</td>
  <td>1 Point</td>
</tr>
</table>

## Task 2-03: Apples and Oranges (to be solved in class `Food.java`)

You sometimes hear people saying: “You shouldn’t compare apples to oranges!“ - but why not? 
This is what we will do in this task. 
Just for completeness, we will add cookies to the mix. 
The class `Food` will represent all of these things (i.e., each particular food is a separate object of this class).
Each object (e.g. some single cookie) has its specific amount of calories.

Make yourself familiar with the *Java Interface `Comparable`*. Using this interface we may compare and sort our food (which is stored within some `array a`) according to its calories using the standard algorithm `java.util.Arrays.sort(a)`.


<table border="0">
<tr>
   <td>
          
Please implement the following methods (as given by the `Food`class template:


```public String toString()```

which returns a string representation of the food in the form of *“name:calories”*.
___

```public int compareTo(Food o)```

which should compare an object of type `Food` with the object, passed as parameter of the method, in the following way:
The length of its name multiplied with its calories should be the *comparison criterion*.
For more information on this method, see the documentation of the `Comparable` interface in Java.
___

```public static Food[] createSortedRandomList()```

which returns an array of random food objects.
The length of the array should be random *between 0 and 100*.
The random food objects may be created from a given list of possible values for names and calories (created by you).
___

```public int getCalories()```
returning the calories of a particular Food object.


</td>

<td>4 Points</td>
  </tr>
<tr>
  <td align="right">Total Points</td>
    <td>15</td>
  </tr>  
</table>
