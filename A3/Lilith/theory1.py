from xml.dom import Node


class Node:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
        self.key = str(self.value)


    def display(self):
        lines, *_ = self._display_aux()
        for line in lines:
            print(line)

    def _display_aux(self):
        """Returns list of strings, width, height, and horizontal coordinate of the root."""
        # No child.
        if self.right is None and self.left is None:
            line = "%s" % self.key
            width = len(line)
            height = 1
            middle = width // 2
            return [line], width, height, middle
        # Only left child.
        if self.right is None:
            lines, n, p, x = self.left._display_aux()
            s = "%s" % self.key
            u = len(s)
            first_line = (x + 1) * " " + (n - x - 1) * "_" + s
            second_line = x * " " + "/" + (n - x - 1 + u) * " "
            shifted_lines = [line + u * " " for line in lines]
            return [first_line, second_line] + shifted_lines, n + u, p + 2, n + u // 2
        # Only right child.
        if self.left is None:
            lines, n, p, x = self.right._display_aux()
            s = "%s" % self.key
            u = len(s)
            first_line = s + x * "_" + (n - x) * " "
            second_line = (u + x) * " " + "\\" + (n - x - 1) * " "
            shifted_lines = [u * " " + line for line in lines]
            return [first_line, second_line] + shifted_lines, n + u, p + 2, u // 2
        # Two children.
        left, n, p, x = self.left._display_aux()
        right, m, q, y = self.right._display_aux()
        s = "%s" % self.key
        u = len(s)
        first_line = (x + 1) * " " + (n - x - 1) * "_" + s + y * "_" + (m - y) * " "
        second_line = (
            x * " " + "/" + (n - x - 1 + u + y) * " " + "\\" + (m - y - 1) * " "
        )
        if p < q:
            left += [n * " "] * (q - p)
        elif q < p:
            right += [m * " "] * (p - q)
        zipped_lines = zip(left, right)
        lines = [first_line, second_line] + [a + u * " " + b for a, b in zipped_lines]
        return lines, n + m + u, max(p, q) + 2, n + u // 2

def combine_trees(T1, T2, wasLeft):
    if T1 is None:
        return T2
    elif T2 is None:
        return T

    # Ensure the root of T is the minimum element between the roots of T1 and T2
    if T1.value < T2.value:
        T = Node(T1.value)
        if(wasLeft):
            T.right = combine_trees(T1.right, T2)
            was
        else:
            T.left = combine_trees(T1.left, T2)
            wasLeft = True
    else:
        T = Node(T2.value)
        T.left = combine_trees(T1, T2.left)
        T.right = combine_trees(T1, T2.right)

    return T


T1 = Node(1);
T1.left = Node(5)
T1.right = Node(7)
T1.left.left = Node(9)
T1.left.right = Node(8)

T2 = Node(-2)
T2.left = Node(5)
T2.right = Node(0)
T2.right.left = Node(3)
T2.right.right = Node(4)

T1.display();
T2.display();

T = combine_trees(T1,T2)

T.display();
# Example usage:
# T1 and T2 are instances of TreeNode representing binary trees
# T = combine_trees(T1, T2)