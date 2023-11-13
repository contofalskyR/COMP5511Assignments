from contextlib import nullcontext


class Node:
    def __init__(self, num:int, val=None, left=None,right=None):
        self.val = val
        self.num = num
        self.left = left
        self.right = right

        if self.val==None:
            self.key = "("+str(self.num)+")"
        else:
            self.key = str(self.num)+"|"+self.val

    def __str__(self):
        if self.val==None:
            return f"{self.num}"
        else:
            return f"{self.val} | {self.num}"
        
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

def makeBST(data: list[int], start:int,end:int)->Node:
    if start>=end:
        return None
    
    median = (start+end)//2
    root = Node(data[median])
    root.left = makeBST(data, start,median-1)
    root.right = makeBST(data,median+1,end)
    return root

def findVal(val:int,root:Node)->Node:
    curr = root
    while(curr):
        print(curr.num)
        if curr.num==val:
            return curr
        if(curr.num>val):
            curr = curr.left
        elif(curr.num<val):
            curr = curr.right


data = [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,22,25,27,30,31,40,45,50,60]
root = makeBST(data,0,len(data))
root.display()
print(findVal(21,root))




