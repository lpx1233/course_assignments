package recfun

object Main {
  def main(args: Array[String]) {
    // println("Pascal's Triangle")
    // for (row <- 0 to 10) {
    //   for (col <- 0 to row)
    //     print(pascal(col, row) + " ")
    //   println()
    // }
    println("Parentheses Balancing")
    println(balance("())(".toList))
    // println(balance("(just an example".toList))
    // println("Parentheses Balancing")
    // println(countChange(4, List(1, 2)))
    // println(countChange(4, List(1, 2, 3)))
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (c < 0 || c >= r + 1) 0
      else if (r == 0) 1
      else pascal(c, r - 1) + pascal(c - 1, r - 1)
    }

  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def balanced(chars: List[Char], open: Int): Boolean = {
        if (chars.isEmpty) open == 0
        else {
          if (chars.head == '(') balanced(chars.tail, open + 1)
          else if (chars.head == ')') {
            if (open <= 0) false
            else balanced(chars.tail, open - 1)
          }
          else balanced(chars.tail, open)
        }
      }
      balanced(chars, 0)
    }

  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if (money == 0) 1
      else if (money > 0 && !coins.isEmpty)
        countChange(money - coins.head, coins) + countChange(money, coins.tail)
      else 0
    }
}
