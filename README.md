# JavaCalculator
A calculator made in Java w/ the Swing application framework. Learn how it's made [here](https://jcurtis.io/tutorials/creating-a-calculator).
The calculator is capable of solving problems such as `1 + 2 * (3 + 4) / 6.6 * 1.999`.

### How it Works
It uses RPN (Reverse Polish Notation) to solve problems. It starts by converting the input into RPN, then it calculates it using RPN instead of standard notation. There's two reasons why I chose this approach over solving it in standard notation. Firstly, standard notation is far more difficult to solve, as order of operations is highly important to the outcome. RPN requires no order of operations, making it far simpler to impliment. Secondly, RPN uses less resources to calculate, leading to a faster execution time.

### Preview
![image](https://github.com/jcurtis06/JavaCalculator/assets/77545656/346b5402-5dd4-4d6d-a7f3-66c51bdf7ae3)
